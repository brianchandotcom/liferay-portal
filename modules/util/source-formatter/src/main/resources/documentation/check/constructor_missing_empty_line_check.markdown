## ConstructorMissingEmptyLineCheck

When assigning values to global variables in a constructor, first assign the
parameter values (in the order of the constructor signature). After that, assign
to other variables.

### Example

Incorrect:

```
public User(String firstName, String middleName, String lastName) {
    _firstName = firstName;
    _lastName = lastName;
    _middleName = middleName;
    _userId = StringUtil.randomString();
}
```

Correct:

```
public User(String firstName, String middleName, String lastName) {
    _firstName = firstName;
    _middleName = middleName;
    _lastName = lastName;

    _userId = StringUtil.randomString();
}
```