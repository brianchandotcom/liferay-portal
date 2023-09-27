## LambdaCheck

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

___

Use a method reference to simpilfy a lambda expression when possible, we can do the following:

```java
NoticeableFuture<User> userNoticeableFuture =
	_noticeableExecutorService.submit((Callable<User>)UserTestUtil::addUser);
```

Instead of

```java
NoticeableFuture<User> userNoticeableFuture =
	_noticeableExecutorService.submit(() -> UserTestUtil.addUser());
```