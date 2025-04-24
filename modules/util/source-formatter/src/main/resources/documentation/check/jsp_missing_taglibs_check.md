## JSPMissingTaglibsCheck

When using a taglib, the corresponding library needs to be defined.

### Example:

```
<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>

<liferay-frontend:defineObjects />
```