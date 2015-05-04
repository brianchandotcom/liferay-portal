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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-members");

long groupId = ParamUtil.getLong(request, "groupId", themeDisplay.getSiteGroupId());

Group group = GroupLocalServiceUtil.getGroup(groupId);
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ASSIGN_MEMBERS) %>">
			<aui:nav-item dropdown="<%= true %>" iconCssClass="icon-plus" label="add-members" selected='<%= toolbarItem.equals("add-members") %>'>
				<liferay-portlet:renderURL varImpl="assignMembersURL">
					<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
				</liferay-portlet:renderURL>

				<%
				assignMembersURL.setParameter("tabs1", "users");
				assignMembersURL.setParameter("tabs2", "available");
				%>

				<aui:nav-item href="<%= assignMembersURL.toString() %>" iconCssClass="icon-user" label="user" />

				<%
				assignMembersURL.setParameter("tabs1", "organizations");
				assignMembersURL.setParameter("tabs2", "available");
				%>

				<aui:nav-item href="<%= assignMembersURL.toString() %>" iconCssClass="icon-globe" label="organization" />

				<%
				assignMembersURL.setParameter("tabs1", "user-groups");
				assignMembersURL.setParameter("tabs2", "available");
				%>

				<aui:nav-item href="<%= assignMembersURL.toString() %>" iconCssClass="icon-globe" label="user-group" />
			</aui:nav-item>
		</c:if>

		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ASSIGN_USER_ROLES) %>">
			<aui:nav-item dropdown="<%= true %>" iconCssClass="icon-plus" label="add-site-roles-to" selected='<%= toolbarItem.equals("assign-user-roles") %>'>

				<%
				PortletURL assignUserRolesURL = PortletProviderUtil.getPortletURL(request, UserGroupRole.class.getName(), PortletProvider.Action.EDIT);

				assignUserRolesURL.setParameter("className", User.class.getName());
				assignUserRolesURL.setWindowState(LiferayWindowState.POP_UP);
				%>

				<aui:nav-item href="<%= assignUserRolesURL %>" iconCssClass="icon-user" label="users" useDialog="<%= true %>" />

				<%
				PortletURL assignUserGroupRolesURL = PortletProviderUtil.getPortletURL(request, UserGroupRole.class.getName(), PortletProvider.Action.EDIT);

				assignUserGroupRolesURL.setParameter("className", UserGroup.class.getName());
				assignUserGroupRolesURL.setWindowState(LiferayWindowState.POP_UP);
				%>

				<aui:nav-item href="<%= assignUserGroupRolesURL %>" iconCssClass="icon-globe" label="user-groups" useDialog="<%= true %>" />
			</aui:nav-item>
		</c:if>

		<c:if test="<%= group.getType() == GroupConstants.TYPE_SITE_RESTRICTED %>">
			<portlet:renderURL var="viewMembershipRequestsURL">
				<portlet:param name="mvcPath" value="/view_membership_requests.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= viewMembershipRequestsURL %>" label="view-membership-requests" selected='<%= toolbarItem.equals("view-membership-requests") %>' />
		</c:if>
	</aui:nav>
</aui:nav-bar>