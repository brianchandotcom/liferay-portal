<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<div class="lfr-portlet-toolbar">
	<liferay-portlet:renderURL varImpl="assignMembersURL">
		<liferay-portlet:param name="struts_action" value="/sites_admin/edit_site_assignments" />
		<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
	</liferay-portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-members") ? "current" : StringPool.BLANK %>">
		<a href="<%= assignMembersURL %>"><liferay-ui:message key="view-members" /></a>
	</span>

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
		<liferay-ui:icon-menu align="left" cssClass='<%= "lfr-toolbar-button " + (toolbarItem.equals("add-role") ? "current" : StringPool.BLANK) %>' direction="down" extended="<%= false %>" icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>' message="add-members">

			<%
			assignMembersURL.setParameter("tabs1", "users");
			assignMembersURL.setParameter("tabs2", "available");
			%>

			<liferay-ui:icon
				image="user_icon"
				message="user"
				method="get"
				url="<%= assignMembersURL.toString() %>"
			/>

			<%
			assignMembersURL.setParameter("tabs1", "organizations");
			assignMembersURL.setParameter("tabs2", "available");
			%>

			<liferay-ui:icon
				image="organization_icon"
				message="organization"
				method="get"
				url="<%= assignMembersURL.toString() %>"
			/>

			<%
			assignMembersURL.setParameter("tabs1", "user-groups");
			assignMembersURL.setParameter("tabs2", "available");
			%>

			<liferay-ui:icon
				image="group"
				message="user-group"
				method="get"
				url="<%= assignMembersURL.toString() %>"
			/>
		</liferay-ui:icon-menu>
	</c:if>

	<c:if test="<%= permissionChecker.isGroupOwner(group.getGroupId()) || GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_USER_ROLES) %>">
		<liferay-ui:icon-menu align="left" cssClass='<%= "lfr-toolbar-button " + (toolbarItem.equals("assign-user-roles") ? "current" : StringPool.BLANK) %>' direction="down" extended="<%= false %>" icon='<%= themeDisplay.getPathThemeImages() + "/common/assign_user_roles.png" %>' message="add-site-roles-to">
			<portlet:renderURL var="assignUserRolesURL">
				<portlet:param name="struts_action" value="/sites_admin/edit_user_roles" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				image="user_icon"
				message="users"
				url="<%= assignUserRolesURL %>"
			/>

			<portlet:renderURL var="assignUserGroupRolesURL">
				<portlet:param name="struts_action" value="/sites_admin/edit_user_group_roles" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<liferay-ui:icon
				image="group"
				message="user-groups"
				url="<%= assignUserGroupRolesURL %>"
			/>
		</liferay-ui:icon-menu>
	</c:if>

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.MANAGE_TEAMS) %>">
		<portlet:renderURL var="manageTeamsURL">
			<portlet:param name="struts_action" value="/sites_admin/view_teams" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button teams-button <%= toolbarItem.equals("view-teams") ? "current" : StringPool.BLANK %>">
			<a href="<%= manageTeamsURL %>"><liferay-ui:message key="view-teams" /></a>
		</span>
	</c:if>

	<c:if test="<%= group.getType() == GroupConstants.TYPE_SITE_RESTRICTED %>">
		<portlet:renderURL var="viewMembershipRequestsURL">
			<portlet:param name="struts_action" value="/sites_admin/view_membership_requests" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button membership-requests-button <%= toolbarItem.equals("view-membership-requests") ? "current" : StringPool.BLANK %>">
			<a href="<%= viewMembershipRequestsURL %>"><liferay-ui:message key="view-membership-requests" /></a>
		</span>
	</c:if>
</div>