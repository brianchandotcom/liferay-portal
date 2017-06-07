## Chaining

When possible, we should declare new variables instead of chaining.

### Example

Incorrect formatting:

```java
String lowerCaseName = UserLocalServiceUtil.getUserById(12345).getFirstName().toLowerCase();
```

Correct formatting:

```java
User user = UserLocalServiceUtil.getUserById(12345);

String name = user.getFirstName();

String lowerCaseName = name.toLowerCase();
```

### Exceptions

We do allow chaining on Mockito methods in our test classes and on variables
that are a builder, optional or stream. In these cases we apply the following
formatting rules:

```java
Stream<User> usersStream = users.stream();

usersStream.filter(
    relations -> isFieldIncluded(types, relation.getName())
).filter(
    relation -> representorManager.isPresent(relation.getType())
).filter(
    relation -> {
        Creator creator = relation.getCreator();

        Optional<V> relationResource = creator.apply(resource);
    }
);
```

or

```java
Mockito.doReturn(
    summary
).when(
    _indexer
).getSummary(
    (Document)Matchers.any(), Matchers.anyString(),
    (PortletRequest)Matchers.isNull(),
    (PortletResponse)Matchers.isNull()
);
```