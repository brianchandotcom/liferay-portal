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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

UserGroup userGroup = (UserGroup)row.getObject();

Group group = (Group)row.getParameter("group");
%>

<liferay-ui:icon-menu>
	<c:if test="<%= permissionChecker.isGroupOwner(group.getGroupId()) || GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ASSIGN_USER_ROLES) %>">
		<portlet:renderURL var="assignURL">
			<portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" />
			<portlet:param name="tabs1" value="user-groups" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="userGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="assign_user_group_roles"
			message="assign-site-roles"
			url="<%= assignURL %>"
		/>
	</c:if>

	<c:if test="<%= permissionChecker.isGroupOwner(group.getGroupId()) || GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ASSIGN_MEMBERS) %>">
		<portlet:actionURL var="removeURL">
			<portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" />
			<portlet:param name="<%= Constants.CMD %>" value="group_user_groups" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="removeUserGroupIds" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			image="unassign_user_group"
			message="remove-membership"
			url="<%= removeURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>