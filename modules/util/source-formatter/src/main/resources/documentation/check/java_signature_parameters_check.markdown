## JavaSignatureParametersCheck

Parameters in methods or constructors of the following types should be sorted
alphabetically:

`HttpServletRequest`, `HttpServletResponse`, `LiferayPortletRequest`,
`LiferayPortletResponse`, `PortletRequest`, `PortletResponse`, `RenderRequest`,
and `RenderResponse`

### Example

Incorrect:

```java
public DisplayPageDisplayContext(
    RenderRequest renderRequest, RenderResponse renderResponse,
    HttpServletRequest httpServletRequest) {

    _renderRequest = renderRequest;
    _renderResponse = renderResponse;
    _httpServletRequest = httpServletRequest;
}
```

Correct:

```java
public DisplayPageDisplayContext(
    HttpServletRequest httpServletRequest, RenderRequest renderRequest,
    RenderResponse renderResponse) {

    _httpServletRequest = httpServletRequest;
    _renderRequest = renderRequest;
    _renderResponse = renderResponse;
}
```