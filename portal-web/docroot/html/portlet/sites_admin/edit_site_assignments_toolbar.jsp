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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-members");

Group group = (Group)request.getAttribute(WebKeys.GROUP);

if (group == null) {
	long groupId = ParamUtil.getLong(request, "groupId");

	group = GroupServiceUtil.getGroup(groupId);
}
%>

<liferay-portlet:renderURL varImpl="assignMembersURL">
	<liferay-portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" />
	<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
</liferay-portlet:renderURL>

<aui:nav-bar>
	<aui:nav>
		<aui:nav-item href="<%= assignMembersURL %>" label="view-members" selected='<%= toolbarItem.equals("view-members") %>' />

		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
			<aui:nav-item dropdown="<%= true %>" iconClass="icon-plus" label="add-members" selected='<%= toolbarItem.equals("add-members") %>'>

				<%
				assignMembersURL.setParameter("tabs1", "users");
				assignMembersURL.setParameter("tabs2", "available");
				%>

				<aui:nav-item href="<%= assignMembersURL %>" iconClass="icon-user" label="user" />

				<%
				assignMembersURL.setParameter("tabs1", "organizations");
				assignMembersURL.setParameter("tabs2", "available");
				%>

				<aui:nav-item href="<%= assignMembersURL %>" iconClass="icon-globe" label="organization" />

				<%
				assignMembersURL.setParameter("tabs1", "user-groups");
				assignMembersURL.setParameter("tabs2", "available");
				%>

				<aui:nav-item href="<%= assignMembersURL %>" iconClass="icon-globe" label="user-group" />
			</aui:nav-item>
		</c:if>

		<c:if test="<%= permissionChecker.isGroupOwner(group.getGroupId()) || GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_USER_ROLES) %>">
			<aui:nav-item dropdown="<%= true %>" iconClass="icon-plus" label="add-site-roles-to" selected='<%= toolbarItem.equals("assign-user-roles") %>'>
				<portlet:renderURL var="assignUserRolesURL">
					<portlet:param name="struts_action" value="/sites_admin/edit_user_roles" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:renderURL>

				<aui:nav-item href="<%= assignUserRolesURL %>" iconClass="icon-user" label="users" />

				<portlet:renderURL var="assignUserGroupRolesURL">
					<portlet:param name="struts_action" value="/sites_admin/edit_user_group_roles" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
				</portlet:renderURL>

				<aui:nav-item href="<%= assignUserGroupRolesURL %>" iconClass="icon-globe" label="user-groups" />
			</aui:nav-item>
		</c:if>

		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.MANAGE_TEAMS) %>">
			<portlet:renderURL var="manageTeamsURL">
				<portlet:param name="struts_action" value="/sites_admin/view_teams" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= manageTeamsURL %>" label="view-teams" selected='<%= toolbarItem.equals("view-teams") %>' />
		</c:if>

		<c:if test="<%= group.getType() == GroupConstants.TYPE_SITE_RESTRICTED %>">
			<portlet:renderURL var="viewMembershipRequestsURL">
				<portlet:param name="struts_action" value="/sites_admin/view_membership_requests" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= viewMembershipRequestsURL %>" label="view-membership-requests" selected='<%= toolbarItem.equals("view-membership-requests") %>' />
		</c:if>
	</aui:nav>
</aui:nav-bar>