## Persistence Update

Reduce stale references in service code from persistence updates.

Update calls should be reassigned to the model when the model is used
afterwards, in case the the hibernate session has a different model than what is
passed.
See https://docs.jboss.org/hibernate/orm/3.5/javadocs/org/hibernate/Session.html#merge(java.lang.Object)

This can cause squashed updates or single threaded MVCC failures.

### Example

Incorrect:

```
User user = userPersistence.findByPrimaryKey(userId);

user.setFirstName(updatedFirstName);
user.setFirstName(updatedLastName);

userPersistence.update(user);

printUserInformation(user);
```

Correct:

```
User user = userPersistence.findByPrimaryKey(userId);

user.setFirstName(updatedFirstName);
user.setFirstName(updatedLastName);

user = userPersistence.update(user);

printUserInformation(user);
```