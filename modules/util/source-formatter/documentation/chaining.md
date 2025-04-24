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

Chaining on variables is allowed as long as the variable type is one of the
following types:

`*Builder`, `Column`, `CompletableFuture`, `*Consumer`, `ContactsEngineClient`,
`Dataset`, `Description`, `DoubleStream`, `ExecuteActionRequest`,
`FormsEventDatasetFilter`, `Filter`, `FormsEventDatasetFilter`, `*Function`,
`HttpSecurity`, `InOrder`, `IntSTream`, `JSONArray`, `JSONObject`, `JsonPath`,
`LongStream`, `*Mapper`, `Optional`, `PowerMockitoStubber`, `Predicate`,
`RequestSpecification`, `Response`, `Stream`, `Try`,
`UpdateFieldPropertyRequest`, `ValidatableResponse` or `WebTarget`

#### Example

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

Chaining on classes directly is allowed for the following classes:

`ActionSemantics`, `*Builder`, `Assertions`, `Awaitility`, `*Consumer`,
`DoubleStream`, `EasyMock`, `Either`, `Filter`,
`FormNavigatorEntryConfigurationRetriever`, `GenericUtil`, `IntStream`,
`JSONUtil`, `List`, `LongStream`, `MemberMatcher`, `Mockito`, `Optional`,
`PowerMockito`, `Response`, `RestAssured`, `Stream`, `Try` or `Validation`

#### Examples

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

```java
String gender = Try.fromFallible(
    user::isMale
).map(
    male -> male ? "male" : "female"
).orElse(
    null
);
```