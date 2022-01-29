## JavaUnsafeCastingCheck

To avoid ClassCastExceptions, we want to use

`PortalUtil.getLiferayPortletRequest(renderRequest)` or
`PortalUtil.getLiferayPortletResponse(renderResponse)`.

### Example

Incorrect:

```java
LiferayPortletRequest liferayPortletRequest =
	(LiferayPortletRequest)portletRequest;

LiferayPortletResponse liferayPortletResponse =
	(LiferayPortletResponse)portletResponse;
```

Correct:

```java
LiferayPortletRequest liferayPortletRequest =
	PortalUtil.getLiferayPortletRequest(portletRequest);

LiferayPortletResponse liferayPortletResponse =
	PortalUtil.getLiferayPortletResponse(portletResponse);
```