## JavaForLoopCheck

When possible, use an Enhanced For-Loop.

### Arrays

```java
long[] userIds = getUserIds();

for (long userId : userIds) {
    deleteUser(userId);
}
```

Instead of

```java
long[] userIds = getUserIds();

for (int i = 0; i < userIds.length; i++) {
    deleteUser(userIds[i]);
}
```

### Lists

```java
List<Long> userIds = getUserIds();

for (long userId : userIds) {
    deleteUser(userId);
}
```

Instead of

```java
List<Long> userIds = getUserIds();

for (int i = 0; i < userIds.size(); i++) {
    deleteUser(userIds.get(i));
}
```