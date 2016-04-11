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

<html dir="<liferay-ui:message key="lang.dir" />">
	<head>
		<meta content="no-cache" http-equiv="Cache-Control" />
		<meta content="no-cache" http-equiv="Pragma" />
		<meta content="0" http-equiv="Expires" />
	</head>

	<body onLoad="setTimeout('document.fm.submit()', 100);">
		<form action="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.src()) %>" method="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getFormMethod()) %>" name="fm">

			<%
			for (KeyValuePair hiddenVariableKVP : iFrameDisplayContext.getHiddenVariableKVPs()) {
			%>

				<input name="<%= HtmlUtil.escapeAttribute(hiddenVariableKVP.getKey()) %>" type="hidden" value="<%= HtmlUtil.escapeAttribute(hiddenVariableKVP.getValue()) %>" />

			<%
			}
			%>

			<input name="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.userNameField()) %>" type="hidden" value="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getUserName()) %>" />

			<input name="<%= HtmlUtil.escapeAttribute(iFramePortletInstanceConfiguration.passwordField()) %>" type="hidden" value="<%= HtmlUtil.escapeAttribute(iFrameDisplayContext.getPassword()) %>" />
		</form>
	</body>
</html>