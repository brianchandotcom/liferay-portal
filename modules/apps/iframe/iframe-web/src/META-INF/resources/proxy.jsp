<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<String> hiddenVariablesList = ListUtil.toList(StringUtil.split(iFrameDisplayContext.getHiddenVariables(), CharPool.SEMICOLON));

hiddenVariablesList.addAll(iframeVariables);
%>

<html dir="<liferay-ui:message key="lang.dir" />">

<head>
	<meta content="no-cache" http-equiv="Cache-Control" />
	<meta content="no-cache" http-equiv="Pragma" />
	<meta content="0" http-equiv="Expires" />
</head>

<body onLoad="setTimeout('document.fm.submit()', 100);">

<form action="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getSrc()) %>" method="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getFormMethod()) %>" name="fm">

<%
for (String hiddenVariable : hiddenVariablesList) {
	String hiddenKey = StringPool.BLANK;
	String hiddenValue = StringPool.BLANK;

	int pos = hiddenVariable.indexOf(StringPool.EQUAL);

	if (pos != -1) {
		hiddenKey = hiddenVariable.substring(0, pos);
		hiddenValue = hiddenVariable.substring(pos + 1);
	}
%>

	<input name="<%= HtmlUtil.escapeAttribute(hiddenKey) %>" type="hidden" value="<%= HtmlUtil.escapeAttribute(hiddenValue) %>" />

<%
}

if (Validator.isNull(iFrameDisplayContext.getUserNameField())) {
	int pos = userName.indexOf(StringPool.EQUAL);

	if (pos != -1) {
		String fieldValuePair = userName;

		String userNameField = fieldValuePair.substring(0, pos);
		userName = fieldValuePair.substring(pos + 1);

		portletPreferences.setValue("userName", userName);
		portletPreferences.setValue("userNameField", userNameField);

		portletPreferences.store();
	}
}

if (Validator.isNotNull(iFrameDisplayContext.getUserNameField())) {
	userName = IFrameUtil.getUserName(renderRequest, userName);
}
%>

<input name="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getUserNameField()) %>" type="hidden" value="<%= HtmlUtil.escapeAttribute(userName) %>" />

<%
if (Validator.isNull(iFrameDisplayContext.getPasswordField())) {
	int pos = password.indexOf(StringPool.EQUAL);

	if (pos != -1) {
		String fieldValuePair = password;

		String passwordField = fieldValuePair.substring(0, pos);
		password = fieldValuePair.substring(pos + 1);

		portletPreferences.setValue("password", password);
		portletPreferences.setValue("passwordField", passwordField);

		portletPreferences.store();
	}
}

if (Validator.isNotNull(iFrameDisplayContext.getPasswordField())) {
	password = IFrameUtil.getPassword(renderRequest, password);
}
%>

<input name="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getPasswordField()) %>" type="hidden" value="<%= HtmlUtil.escapeAttribute(password) %>" />

</form>

</body>

</html>