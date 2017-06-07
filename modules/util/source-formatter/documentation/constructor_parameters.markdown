## Constructor parameters

When assinging values to global variables in a constructor, the variables that
are assigned with values from the constructor parameters should come first and
follow the order as they appear in the signature of the constructor.

The other variables should come last.

### Example

```java
public Person(
     String firstName, String middleName, String lastName, Date birthDate,
     String gender) {

    _firstName = firstName;
    _middleName = middleName;
    _lastName = lastName;
    _gender = gender;

    _age = getAge(birthDay);
    _fullName = getFullName(firstName, middleName, lastName);
}
```