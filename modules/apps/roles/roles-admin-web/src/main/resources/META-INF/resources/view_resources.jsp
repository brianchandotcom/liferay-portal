<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String cmd = ParamUtil.getString(request, Constants.CMD);

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleServiceUtil.fetchRole(roleId);

String portletResource = ParamUtil.getString(request, "portletResource");

request.setAttribute("edit_role_permissions.jsp-portletResource", portletResource);

request.setAttribute("edit_role_permissions.jsp-role", role);
%>

<c:choose>
	<c:when test="<%= cmd.equals(Constants.EDIT) %>">
		<liferay-util:include page="/edit_role_permissions_form.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/edit_role_permissions_summary.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>