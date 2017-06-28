## Casting to LiferayPortletResponse

To avoid ClassCastExceptions, we want to use

```PortalUtil.getLiferayPortletResponse(renderResponse)```

### Example

Incorrect:

```java
LiferayPortletResponse liferayPortletResponse =
	(LiferayPortletResponse)portletResponse;
```

Correct:

```java
LiferayPortletResponse liferayPortletResponse =
	PortalUtil.getLiferayPortletResponse(portletResponse);
```