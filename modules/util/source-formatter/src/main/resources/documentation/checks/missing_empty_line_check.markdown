## MissingEmptyLineCheck

When multiple variable definitions are grouped, but only the last variable(s)
are used by the following statement, there should be an empty line before the
last variable(s).

```java
String s1 = "Hello";
String s2 = "World";

String s3 = "Hello World";

if (reverse) {
    s3 = "World Hello";
}
```

Instead of

```java
String s1 = "Hello";
String s2 = "World";
String s3 = "Hello World";

if (reverse) {
    s3 = "World Hello";
}
```