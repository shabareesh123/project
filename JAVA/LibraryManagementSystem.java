import java.util.*;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Available: " + (isAvailable ? "Yes" : "No");
    }
}

class Library {
    private List<Book> books;
    private Map<String, Book> borrowedBooks;

    public Library() {
        books = new ArrayList<>();
        borrowedBooks = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean borrowBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && book.isAvailable()) {
                book.setAvailable(false);
                borrowedBooks.put(isbn, book);
                System.out.println("Successfully borrowed: " + book.getTitle());
                return true;
            }
        }
        System.out.println("Sorry, this book is either not available or already borrowed.");
        return false;
    }

    public boolean returnBook(String isbn) {
        if (borrowedBooks.containsKey(isbn)) {
            Book book = borrowedBooks.get(isbn);
            book.setAvailable(true);
            borrowedBooks.remove(isbn);
            System.out.println("Successfully returned: " + book.getTitle());
            return true;
        }
        System.out.println("This book was not borrowed from this library.");
        return false;
    }

    public void displayAvailableBooks() {
        System.out.println("Available books in the library:");
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println(book);
            }
        }
    }

    public void displayBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books.");
            return;
        }
        System.out.println("Currently borrowed books:");
        for (Book book : borrowedBooks.values()) {
            System.out.println(book);
        }
    }
}

interface User {
    void viewBooks(Library library);
    void borrowBook(Library library, String isbn);
    void returnBook(Library library, String isbn);
}

class RegularUser implements User {
    private String name;

    public RegularUser(String name) {
        this.name = name;
    }

    @Override
    public void viewBooks(Library library) {
        library.displayAvailableBooks();
    }

    @Override
    public void borrowBook(Library library, String isbn) {
        library.borrowBook(isbn);
    }

    @Override
    public void returnBook(Library library, String isbn) {
        library.returnBook(isbn);
    }

    public String getName() {
        return name;
    }
}

class Admin implements User {
    private String name;

    public Admin(String name) {
        this.name = name;
    }

    @Override
    public void viewBooks(Library library) {
        library.displayAvailableBooks();
    }

    @Override
    public void borrowBook(Library library, String isbn) {
        library.borrowBook(isbn);
    }

    @Override
    public void returnBook(Library library, String isbn) {
        library.returnBook(isbn);
    }

    public void addBookToLibrary(Library library, Book book) {
        library.addBook(book);
        System.out.println("Book added to the library: " + book.getTitle());
    }

    public String getName() {
        return name;
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        Admin admin = new Admin("Admin1");
        RegularUser user = new RegularUser("User1");

        // Admin adds books to the library
        admin.addBookToLibrary(library, new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565"));
        admin.addBookToLibrary(library, new Book("1984", "George Orwell", "9780451524935"));
        admin.addBookToLibrary(library, new Book("To Kill a Mockingbird", "Harper Lee", "9780061120084"));
        admin.addBookToLibrary(library, new Book("Moby-Dick", "Herman Melville", "9781853260087"));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nWelcome to the Library Management System");
            System.out.println("1. View available books");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. View borrowed books");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the newline

            switch (choice) {
                case 1:
                    user.viewBooks(library);
                    break;

                case 2:
                    System.out.print("Enter the ISBN of the book you want to borrow: ");
                    String borrowIsbn = scanner.nextLine();
                    user.borrowBook(library, borrowIsbn);
                    break;

                case 3:
                    System.out.print("Enter the ISBN of the book you want to return: ");
                    String returnIsbn = scanner.nextLine();
                    user.returnBook(library, returnIsbn);
                    break;

                case 4:
                    user.viewBooks(library);
                    library.displayBorrowedBooks();
                    break;

                case 5:
                    System.out.println("Exiting the system...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}

