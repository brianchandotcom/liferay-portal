## JSPMethodCallsCheck

Use type `LiferayPortletResponse` to call `getNamespace()` instead of type
`RenderResponse` or `PortletResponse`.

#### Example

Incorrect:

`<portlet:param name="scroll" value='<%= renderResponse.getNamespace() + "discussionContainer" %>' />`

Correct:

`<portlet:param name="scroll" value='<%= liferayPortletResponse.getNamespace() + "discussionContainer" %>' />`