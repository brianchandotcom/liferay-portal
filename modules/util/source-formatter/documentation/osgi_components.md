## OSGi Components

OSGi Components should add references to the services instead of calling the
`*ServiceUtil` class to avoid accidentally creating a circular dependency.

When the OSGi Component and the service Spring bean are within the same module,
we do not need to worry about this.

### Example

Incorrect:

```java
import com.liferay.portal.service.UserLocalServiceUtil;

...

public User getUser(long userId) {
    return UserLocalServiceUtil.getUserById(userId);
}
```

Correct:

```java
import com.liferay.portal.service.UserLocalService;

import org.osgi.service.component.annotations.Reference;

...

public User getUser(long userId) {
    return _userLocalService.getUserById(userId);
}

@Reference(unbind = "-")
protected void setUserLocalService(UserLocalService userLocalService) {
    _userLocalService = userLocalService;
}

private UserLocalService _userLocalService;
```

For better performance, we should also use the service reference for other
`*Util` classes.

### Example

Incorrect:

```java
import com.liferay.portal.kernel.util.PortalUtil;

...

public String getPathModule() {
    return PortalUtil.getPathModule();
}
```

Correct:

```java
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Reference;

...

public String getPathModule() {
    return _portal.getPathModule();
}

@Reference
private Portal _portal;
```