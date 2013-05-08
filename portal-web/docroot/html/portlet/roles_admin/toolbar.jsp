<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/roles_admin/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all");
%>

<aui:nav-bar>
	<aui:nav>
		<portlet:renderURL var="viewRolesURL">
			<portlet:param name="struts_action" value="/roles_admin/view" />
		</portlet:renderURL>

		<aui:nav-item href="<%= viewRolesURL %>" label="view-all" selected='<%= toolbarItem.equals("view-all") %>' />

		<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_ROLE) %>">
			<liferay-portlet:renderURL varImpl="addRoleURL">
				<portlet:param name="struts_action" value="/roles_admin/edit_role" />
				<portlet:param name="redirect" value="<%= viewRolesURL %>" />
			</liferay-portlet:renderURL>

			<aui:nav-item dropdown="<%= true %>" label="add" selected='<%= toolbarItem.equals("add") %>'>

				<%
				addRoleURL.setParameter("type", String.valueOf(RoleConstants.TYPE_REGULAR));
				%>

				<aui:nav-item href="<%= addRoleURL.toString() %>" label="regular-role" />

				<%
				addRoleURL.setParameter("type", String.valueOf(RoleConstants.TYPE_SITE));
				%>

				<aui:nav-item href="<%= addRoleURL.toString() %>" label="site-role" />

				<%
				addRoleURL.setParameter("type", String.valueOf(RoleConstants.TYPE_ORGANIZATION));
				%>

				<aui:nav-item href="<%= addRoleURL.toString() %>" label="organization-role" />
			</aui:nav-item>
		</c:if>
	</aui:nav>
</aui:nav-bar>