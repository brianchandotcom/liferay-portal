<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
User selectedUser = userDisplayContext.getSelectedUser();
%>

<liferay-ui:message key="use-the-username-and-password-below-when-authenticating-your-webdav-client" />

<br /><br />

<aui:input label="web-dav-username" name="<%= StringUtil.randomId() %>" type="resource" value="<%= selectedUser.getUserId() %>" />

<aui:input id="webDAVPassword" label="web-dav-password" name="<%= StringUtil.randomId() %>" type="resource" value='<%= renderRequest.getParameter("webDAVPassword") %>' />