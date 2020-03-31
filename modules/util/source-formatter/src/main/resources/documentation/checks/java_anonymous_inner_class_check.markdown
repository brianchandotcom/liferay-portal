## JavaAnonymousInnerClassCheck

Avoid duplicate variable/parameter name in Anonymous Inner Class. Although the
code will compile without problems, it is confusing and can easily lead to bugs.

### Example

Incorrect:

```java
public static void deletePath(Path path) throws IOException {
    Files.walkFileTree(
        path,
        new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(
                    Path path, BasicFileAttributes basicFileAttributes)
                throws IOException {

                Files.delete(path);

                return FileVisitResult.CONTINUE;
            }

        });
}
```

Correct:

```java
public static void deletePath(Path path) throws IOException {
    Files.walkFileTree(
        path,
        new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(
                    Path filePath, BasicFileAttributes basicFileAttributes)
                throws IOException {

                Files.delete(filePath);

                return FileVisitResult.CONTINUE;
            }

        });
}
```