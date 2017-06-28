## Casting to LiferayPortletRequest

To avoid ClassCastExceptions, we want to use

```PortalUtil.getLiferayPortletRequest(renderRequest)```

### Example

Incorrect:

```java
LiferayPortletRequest liferayPortletRequest =
	(LiferayPortletRequest)portletRequest;
```

Correct:

```java
LiferayPortletRequest liferayPortletRequest =
	PortalUtil.getLiferayPortletRequest(portletRequest);
```