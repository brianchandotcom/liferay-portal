## JavaServiceUtilCheck

Do not make calls to `portal-kernel/.../*ServiceUtil` from a '`*ServiceImpl`
class. Instead create a reference via `service.xml`.

### Example

Incorrect:

In `UserLocalServiceImpl.java`

```java
Group group = GroupLocalServiceUtil.getUserGroup(
    user.getCompanyId(), user.getUserId());
```

Correct:

In `service.xml`

```
<entity local-service="true" name="User" remote-service="true" uuid="true">

    ...

    <!-- References -->

    <reference entity="Group" package-path="com.liferay.portal" />
</entity>
```

In `UserLocalServiceImpl.java`

```java
Group group = groupLocalService.getUserGroup(
    user.getCompanyId(), user.getUserId());
```