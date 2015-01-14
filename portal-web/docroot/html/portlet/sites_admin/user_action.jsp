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

User user2 = (User)row.getObject();

Group group = (Group)row.getParameter("group");

boolean organizationUser = GetterUtil.getBoolean(row.getParameter("organizationUser"));
boolean userGroupUser = GetterUtil.getBoolean(row.getParameter("userGroupUser"));
%>

<liferay-ui:icon-menu showWhenSingleIcon="<%= true %>">
	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_USER_ROLES) %>">
		<portlet:renderURL var="assignURL">
			<portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" />
			<portlet:param name="tabs1" value="users" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="p_u_i_d" value="<%= String.valueOf(user2.getUserId()) %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="assign_user_roles"
			message="assign-site-roles"
			url="<%= assignURL %>"
		/>
	</c:if>

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) && !(organizationUser || userGroupUser) && !SiteMembershipPolicyUtil.isMembershipProtected(permissionChecker, user2.getUserId(), group.getGroupId()) && !SiteMembershipPolicyUtil.isMembershipRequired(user2.getUserId(), group.getGroupId()) %>">
		<portlet:actionURL var="removeURL">
			<portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" />
			<portlet:param name="<%= Constants.CMD %>" value="group_users" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="removeUserIds" value="<%= String.valueOf(user2.getUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			image="unassign_user"
			message="remove-membership"
			url="<%= removeURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>