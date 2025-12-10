import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Ambassador {
    private int id;
    private String name;
    private String role;
    private int trainingSessionsAttended;

    public Ambassador(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.trainingSessionsAttended = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getTrainingSessionsAttended() {
        return trainingSessionsAttended;
    }

    public void incrementTrainingSessions() {
        this.trainingSessionsAttended++;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Role: " + role
                + ", Training sessions attended: " + trainingSessionsAttended;
    }
}

class Task {
    private int id;
    private String name;
    private String deadline;
    private String status;
    private Ambassador assignee;

    public Task(int id, String name, String deadline, Ambassador assignee) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.assignee = assignee;
        this.status = "Not Started";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Ambassador getAssignee() {
        return assignee;
    }

    @Override
    public String toString() {
        return "Task ID: " + id + ", Name: " + name + ", Deadline: " + deadline
                + ", Status: " + status
                + ", Assignee: " + (assignee != null ? assignee.getName() : "None");
    }
}

class Project {
    private int id;
    private String name;
    private List<Task> taskList;

    public Project(int id, String name) {
        this.id = id;
        this.name = name;
        this.taskList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public Task findTaskById(int taskId) {
        for (Task t : taskList) {
            if (t.getId() == taskId) {
                return t;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Project ID: " + id + ", Name: " + name + ", Number of tasks: " + taskList.size();
    }
}

class TrainingSession {
    private int id;
    private String date;
    private String topic;
    private List<Ambassador> participants;

    public TrainingSession(int id, String date, String topic) {
        this.id = id;
        this.date = date;
        this.topic = topic;
        this.participants = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTopic() {
        return topic;
    }

    public List<Ambassador> getParticipants() {
        return participants;
    }

    public boolean hasParticipated(Ambassador a) {
        for (Ambassador am : participants) {
            if (am.getId() == a.getId()) {
                return true;
            }
        }
        return false;
    }

    public void addParticipant(Ambassador a) {
        if (!hasParticipated(a)) {
            participants.add(a);
            a.incrementTrainingSessions();
        }
    }

    @Override
    public String toString() {
        return "Session " + id + " - Date: " + date + ", Topic: " + topic
                + ", Number of participants: " + participants.size();
    }
}

public class TaskTribeApp {
    private static List<Ambassador> ambassadorList = new ArrayList<>();
    private static List<Project> projectList = new ArrayList<>();
    private static List<TrainingSession> sessionList = new ArrayList<>();

    private static int ambassadorIdCounter = 1;
    private static int projectIdCounter = 1;
    private static int taskIdCounter = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        createDefaultTrainingSchedule();

        int choice;
        do {
            System.out.println("==== TASKTRIBE AMBASSADOR MANAGER ====");
            System.out.println("1. Manage Ambassador");
            System.out.println("2. Manage Projects and Tasks");
            System.out.println("3. Manage 4-Day Training Schedule");
            System.out.println("4. Statistics Report");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            choice = readInteger(sc);

            switch (choice) {
                case 1:
                    ambassadorMenu(sc);
                    break;
                case 2:
                    projectMenu(sc);
                    break;
                case 3:
                    trainingMenu(sc);
                    break;
                case 4:
                    reportMenu(sc);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);

        sc.close();
    }

    private static int readInteger(Scanner sc) {
        while (true) {
            try {
                String line = sc.nextLine();
                return Integer.parseInt(line.trim());
            } catch (Exception e) {
                System.out.print("Please enter a number: ");
            }
        }
    }

    // ===== Ambassador Management =====

    private static void ambassadorMenu(Scanner sc) {
        int c;
        do {
            System.out.println("--- Ambassador Management ---");
            System.out.println("1. Add ambassador");
            System.out.println("2. Edit ambassador information");
            System.out.println("3. Delete ambassador");
            System.out.println("4. View ambassador list");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            c = readInteger(sc);

            switch (c) {
                case 1:
                    addAmbassador(sc);
                    break;
                case 2:
                    editAmbassador(sc);
                    break;
                case 3:
                    deleteAmbassador(sc);
                    break;
                case 4:
                    displayAmbassadorList();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (c != 0);
    }

    private static void addAmbassador(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter role (Project Lead / Content Creator / Community Promoter): ");
        String role = sc.nextLine();

        Ambassador a = new Ambassador(ambassadorIdCounter++, name, role);
        ambassadorList.add(a);
        System.out.println("Added ambassador with ID: " + a.getId());
    }

    private static void displayAmbassadorList() {
        if (ambassadorList.isEmpty()) {
            System.out.println("No ambassadors yet.");
            return;
        }
        System.out.println("Ambassador list:");
        for (Ambassador a : ambassadorList) {
            System.out.println(a);
        }
    }

    private static Ambassador findAmbassadorById(int id) {
        for (Ambassador a : ambassadorList) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    private static void editAmbassador(Scanner sc) {
        displayAmbassadorList();
        System.out.print("Enter ambassador ID to edit: ");
        int id = readInteger(sc);
        Ambassador a = findAmbassadorById(id);
        if (a == null) {
            System.out.println("Ambassador not found.");
            return;
        }
        System.out.print("Enter new name (leave blank to keep current): ");
        String name = sc.nextLine();
        if (!name.trim().isEmpty()) {
            a.setName(name);
        }
        System.out.print("Enter new role (leave blank to keep current): ");
        String role = sc.nextLine();
        if (!role.trim().isEmpty()) {
            a.setRole(role);
        }
        System.out.println("Information updated.");
    }

    private static void deleteAmbassador(Scanner sc) {
        displayAmbassadorList();
        System.out.print("Enter ambassador ID to delete: ");
        int id = readInteger(sc);
        Ambassador a = findAmbassadorById(id);
        if (a == null) {
            System.out.println("Ambassador not found.");
            return;
        }
        ambassadorList.remove(a);
        System.out.println("Ambassador deleted.");
    }

    // ===== Project and Task Management =====

    private static void projectMenu(Scanner sc) {
        int c;
        do {
            System.out.println("--- Project and Task Management ---");
            System.out.println("1. Create new project");
            System.out.println("2. View project list");
            System.out.println("3. Add task to project");
            System.out.println("4. Update task status");
            System.out.println("5. View tasks of a project");
            System.out.println("6. View tasks by ambassador");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            c = readInteger(sc);

            switch (c) {
                case 1:
                    createProject(sc);
                    break;
                case 2:
                    displayProjectList();
                    break;
                case 3:
                    addTaskToProject(sc);
                    break;
                case 4:
                    updateTaskStatus(sc);
                    break;
                case 5:
                    viewTasksByProject(sc);
                    break;
                case 6:
                    viewTasksByAmbassador(sc);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (c != 0);
    }

    private static void createProject(Scanner sc) {
        System.out.print("Enter project name: ");
        String name = sc.nextLine();
        Project p = new Project(projectIdCounter++, name);
        projectList.add(p);
        System.out.println("Created project with ID: " + p.getId());
    }

    private static void displayProjectList() {
        if (projectList.isEmpty()) {
            System.out.println("No projects yet.");
            return;
        }
        System.out.println("Project list:");
        for (Project p : projectList) {
            System.out.println(p);
        }
    }

    private static Project findProjectById(int id) {
        for (Project p : projectList) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private static void addTaskToProject(Scanner sc) {
        displayProjectList();
        System.out.print("Enter project ID: ");
        int pid = readInteger(sc);
        Project p = findProjectById(pid);
        if (p == null) {
            System.out.println("Project not found.");
            return;
        }

        System.out.print("Enter task name: ");
        String taskName = sc.nextLine();
        System.out.print("Enter deadline (e.g., 12/30/2025): ");
        String deadline = sc.nextLine();

        if (ambassadorList.isEmpty()) {
            System.out.println("No ambassadors yet, please add ambassadors first.");
            return;
        }
        displayAmbassadorList();
        System.out.print("Select assignee ambassador ID: ");
        int aid = readInteger(sc);
        Ambassador a = findAmbassadorById(aid);
        if (a == null) {
            System.out.println("Ambassador not found.");
            return;
        }

        Task t = new Task(taskIdCounter++, taskName, deadline, a);
        p.addTask(t);
        System.out.println("Added task with ID: " + t.getId() + " to project " + p.getName());
    }

    private static void updateTaskStatus(Scanner sc) {
        displayProjectList();
        System.out.print("Enter project ID containing the task: ");
        int pid = readInteger(sc);
        Project p = findProjectById(pid);
        if (p == null) {
            System.out.println("Project not found.");
            return;
        }

        if (p.getTaskList().isEmpty()) {
            System.out.println("Project has no tasks yet.");
            return;
        }

        for (Task t : p.getTaskList()) {
            System.out.println(t);
        }

        System.out.print("Enter task ID to update: ");
        int tid = readInteger(sc);
        Task t = p.findTaskById(tid);
        if (t == null) {
            System.out.println("Task not found.");
            return;
        }

        System.out.println("Select new status:");
        System.out.println("1. Not Started");
        System.out.println("2. In Progress");
        System.out.println("3. Completed");
        System.out.print("Choose: ");
        int st = readInteger(sc);
        switch (st) {
            case 1:
                t.setStatus("Not Started");
                break;
            case 2:
                t.setStatus("In Progress");
                break;
            case 3:
                t.setStatus("Completed");
                break;
            default:
                System.out.println("Invalid choice, keeping previous status.");
                return;
        }
        System.out.println("Task status updated.");
    }

    private static void viewTasksByProject(Scanner sc) {
        displayProjectList();
        System.out.print("Enter project ID: ");
        int pid = readInteger(sc);
        Project p = findProjectById(pid);
        if (p == null) {
            System.out.println("Project not found.");
            return;
        }

        if (p.getTaskList().isEmpty()) {
            System.out.println("Project has no tasks yet.");
            return;
        }

        System.out.println("Task list for project " + p.getName() + ":");
        for (Task t : p.getTaskList()) {
            System.out.println(t);
        }
    }

    private static void viewTasksByAmbassador(Scanner sc) {
        if (ambassadorList.isEmpty()) {
            System.out.println("No ambassadors yet.");
            return;
        }
        displayAmbassadorList();
        System.out.print("Enter ambassador ID: ");
        int aid = readInteger(sc);
        Ambassador a = findAmbassadorById(aid);
        if (a == null) {
            System.out.println("Ambassador not found.");
            return;
        }

        System.out.println("Task list for " + a.getName() + ":");
        boolean hasTask = false;
        for (Project p : projectList) {
            for (Task t : p.getTaskList()) {
                if (t.getAssignee() != null && t.getAssignee().getId() == a.getId()) {
                    System.out.println("[" + p.getName() + "] " + t);
                    hasTask = true;
                }
            }
        }
        if (!hasTask) {
            System.out.println("No tasks assigned to this ambassador yet.");
        }
    }

    // ===== Training Schedule Management =====

    private static void createDefaultTrainingSchedule() {
        sessionList.add(new TrainingSession(1, "Day 1", "Introduction to TaskTribe, goal of 1000 users"));
        sessionList.add(new TrainingSession(2, "Day 2", "Content creation and social media sharing skills"));
        sessionList.add(new TrainingSession(3, "Day 3", "Community skills, building study groups"));
        sessionList.add(new TrainingSession(4, "Day 4", "Summary, 90-day action plan"));
    }

    private static void trainingMenu(Scanner sc) {
        int c;
        do {
            System.out.println("--- 4-Day Training Schedule Management ---");
            System.out.println("1. View training schedule");
            System.out.println("2. Mark ambassador attendance for training session");
            System.out.println("3. View ambassadors who haven't attended all 4 sessions");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            c = readInteger(sc);

            switch (c) {
                case 1:
                    viewTrainingSchedule();
                    break;
                case 2:
                    markTrainingAttendance(sc);
                    break;
                case 3:
                    viewAmbassadorsMissingTraining();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (c != 0);
    }

    private static void viewTrainingSchedule() {
        System.out.println("4-day training schedule:");
        for (TrainingSession s : sessionList) {
            System.out.println(s);
        }
    }

    private static TrainingSession findSessionById(int id) {
        for (TrainingSession s : sessionList) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    private static void markTrainingAttendance(Scanner sc) {
        if (ambassadorList.isEmpty()) {
            System.out.println("No ambassadors yet, please add first.");
            return;
        }
        viewTrainingSchedule();
        System.out.print("Select training session ID: ");
        int sid = readInteger(sc);
        TrainingSession s = findSessionById(sid);
        if (s == null) {
            System.out.println("Training session not found.");
            return;
        }

        displayAmbassadorList();
        System.out.print("Select participating ambassador ID: ");
        int aid = readInteger(sc);
        Ambassador a = findAmbassadorById(aid);
        if (a == null) {
            System.out.println("Ambassador not found.");
            return;
        }

        if (s.hasParticipated(a)) {
            System.out.println("This ambassador has already been recorded as attending this session.");
        } else {
            s.addParticipant(a);
            System.out.println("Attendance recorded.");
        }
    }

    private static void viewAmbassadorsMissingTraining() {
        if (ambassadorList.isEmpty()) {
            System.out.println("No ambassadors yet.");
            return;
        }
        System.out.println("List of ambassadors who haven't attended all 4 sessions:");
        boolean found = false;
        for (Ambassador a : ambassadorList) {
            if (a.getTrainingSessionsAttended() < 4) {
                System.out.println(a);
                found = true;
            }
        }
        if (!found) {
            System.out.println("All ambassadors have attended all 4 sessions.");
        }
    }

    // ===== Statistics Report =====

    private static void reportMenu(Scanner sc) {
        int c;
        do {
            System.out.println("--- Statistics Report ---");
            System.out.println("1. Statistics of completed tasks / total");
            System.out.println("2. List of ambassadors with no completed tasks");
            System.out.println("3. List of ambassadors who haven't attended all 4 training sessions");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            c = readInteger(sc);

            switch (c) {
                case 1:
                    taskStatistics();
                    break;
                case 2:
                    ambassadorsWithoutCompletedTasks();
                    break;
                case 3:
                    viewAmbassadorsMissingTraining();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (c != 0);
    }

    private static void taskStatistics() {
        int total = 0;
        int completed = 0;
        for (Project p : projectList) {
            for (Task t : p.getTaskList()) {
                total++;
                if ("Completed".equalsIgnoreCase(t.getStatus())) {
                    completed++;
                }
            }
        }
        System.out.println("Total number of tasks: " + total);
        System.out.println("Number of completed tasks: " + completed);
        if (total > 0) {
            double percentage = (double) completed * 100.0 / total;
            System.out.printf("Completion rate: %.2f%%\n", percentage);
        }
    }

    private static void ambassadorsWithoutCompletedTasks() {
        if (ambassadorList.isEmpty()) {
            System.out.println("No ambassadors yet.");
            return;
        }
        System.out.println("List of ambassadors with no completed tasks:");
        boolean found = false;
        for (Ambassador a : ambassadorList) {
            int completedTaskCount = 0;
            for (Project p : projectList) {
                for (Task t : p.getTaskList()) {
                    if (t.getAssignee() != null
                            && t.getAssignee().getId() == a.getId()
                            && "Completed".equalsIgnoreCase(t.getStatus())) {
                        completedTaskCount++;
                    }
                }
            }
            if (completedTaskCount == 0) {
                System.out.println(a);
                found = true;
            }
        }
        if (!found) {
            System.out.println("All ambassadors have at least one completed task.");
        }
    }
}
