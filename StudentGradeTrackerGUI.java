import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Student {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }
}

public class StudentGradeTrackerGUI extends JFrame {

    private JTextField nameField, marksField;
    private JTextArea outputArea;
    private ArrayList<Student> students = new ArrayList<>();

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new GridLayout(3,2,10,10));

        top.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        top.add(nameField);

        top.add(new JLabel("Marks:"));
        marksField = new JTextField();
        top.add(marksField);

        JButton addButton = new JButton("Add Student");
        JButton reportButton = new JButton("Show Report");

        top.add(addButton);
        top.add(reportButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        addButton.addActionListener(e -> addStudent());
        reportButton.addActionListener(e -> showReport());

        setVisible(true);
    }

    private void addStudent() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter student name.");
            return;
        }

        try {
            int marks = Integer.parseInt(marksField.getText().trim());

            if (marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100.");
                return;
            }

            students.add(new Student(name, marks));

            JOptionPane.showMessageDialog(this, "Student Added Successfully!");

            nameField.setText("");
            marksField.setText("");
            nameField.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid marks.");
        }
    }

    private String getGrade(int marks) {
        if (marks >= 90) return "A";
        if (marks >= 75) return "B";
        if (marks >= 60) return "C";
        if (marks >= 40) return "D";
        return "F";
    }

    private void showReport() {
        if (students.isEmpty()) {
            outputArea.setText("No student records found.");
            return;
        }

        int total = 0;
        Student highest = students.get(0);
        Student lowest = students.get(0);

        StringBuilder sb = new StringBuilder();

        sb.append("============== STUDENT REPORT ==============\n\n");
        sb.append(String.format("%-15s %-8s %-8s %-8s\n",
                "Name", "Marks", "Grade", "Result"));
        sb.append("-------------------------------------------------------\n");

        for (Student s : students) {

            String grade = getGrade(s.marks);
            String result = s.marks >= 40 ? "Pass" : "Fail";

            sb.append(String.format("%-15s %-8d %-8s %-8s\n",
                    s.name, s.marks, grade, result));

            total += s.marks;

            if (s.marks > highest.marks)
                highest = s;

            if (s.marks < lowest.marks)
                lowest = s;
        }

        double average = (double) total / students.size();

        sb.append("\n---------------------------------------------\n");
        sb.append(String.format("Average Marks : %.2f\n", average));
        sb.append("Highest Marks : " + highest.marks + " (" + highest.name + ")\n");
        sb.append("Lowest Marks  : " + lowest.marks + " (" + lowest.name + ")\n");

        outputArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGradeTrackerGUI::new);
    }
}
