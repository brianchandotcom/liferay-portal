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