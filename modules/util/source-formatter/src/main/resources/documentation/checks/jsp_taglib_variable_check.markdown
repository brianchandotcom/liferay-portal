## JSPTaglibVariableCheck

Variables that have a value that contains both quotes **and** apostrophes and is
used as value for an attribute in a tag, should have a name starting with
`taglib`.

### Example

Incorrect:

```java
<%
String onClickSaveAndContinue = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveAndContinue');";
%>

<aui:button
    onClick="<%= onClickSaveAndContinue %>"
    type="submit"
    value="save-and-continue"
/>
```

Correct:

```java
<%
String taglibOnClickSaveAndContinue = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveAndContinue');";
%>

<aui:button
    onClick="<%= taglibOnClickSaveAndContinue %>"
    type="submit"
    value="save-and-continue"
/>
```
---

Variables that does **not** have a value that contains both quotes **and**
apostrophes, should be inlined if it is used only once.

### Example

Incorrect:

```java
<%
String taglibURL = DLURLHelperUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK, false, true);
%>

<liferay-ui:icon
    message="<%= fileEntry.getTitle() %>"
    url="<%= taglibURL %>"
/>
```

Correct:

```java
<liferay-ui:icon
    message="<%= fileEntry.getTitle() %>"
    url="<%= DLURLHelperUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK, false, true) %>"
/>
```