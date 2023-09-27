<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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