## Lambda

When a lambda expression contains a single statement, it can be simplified:

```java
methodsMap.forEach((methodType, methods) -> Collections.sort(methods));
```

Instead of

```java
methodsMap.forEach(
    (methodType, methods) -> {
        Collections.sort(methods);
    });
```
___

When the statement is a `return` statement, we can do the following:

```java
expectedRolesStream.filter(role -> !excludedRoleNames.contains(role.getName()));
```

Instead of

```java
expectedRolesStream.filter(
    role -> {
        return !excludedRoleNames.contains(role.getName());
    });
```