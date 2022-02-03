## GenericTypeCheck

- Using generics is important. It provides **compile-time type checking** and
removes the risk of getting `ClassCastExption` during runtime. It also removes
the need to typecast the object.

### Example:

The following code compiles without problems but will throw a
`ClassCastException`:

```java
List userList = new ArrayList();

userList.add(OrganzationLocalService.getOrganization(123));

for (Object object : userList) {
    _doSomething((User)object);
}
```

The following code will not compile:

```java
List<User> userList = new ArrayList();

userList.add(OrganzationLocalService.getOrganization(123));

for (User user : userList) {
    _doSomething(user);
}
```

- Using generics also prevents having bad implementation like the following:

```java
SearchContainer searchContainer = new SearchContainer();

OrderByComparator<User> orderByComparator = _getUserOrderByComparator();

searchContainer.setOrderByComparator(orderByComparator);

List<Organization> results = _getOrganizations();

searchContainer.setResults(results);
```

---

For more information on how to use Generics, see
https://docs.oracle.com/javase/tutorial/java/generics/index.html