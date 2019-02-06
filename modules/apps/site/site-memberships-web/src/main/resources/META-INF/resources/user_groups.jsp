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
UserGroupsDisplayContext userGroupsDisplayContext = new UserGroupsDisplayContext(request, renderRequest, renderResponse);

UserGroupsManagementToolbarDisplayContext userGroupsManagementToolbarDisplayContext = new UserGroupsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, userGroupsDisplayContext);

Role role = userGroupsDisplayContext.getRole();
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= siteMembershipsDisplayContext.getViewNavigationItems() %>"
/>

<clay:management-toolbar
	displayContext="<%= userGroupsManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="deleteGroupUserGroups" var="deleteGroupUserGroupsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteGroupUserGroupsURL %>" cssClass="container-fluid-1280 portlet-site-memberships-user-groups" name="fm">
	<aui:input name="tabs1" type="hidden" value="user-groups" />
	<aui:input name="navigation" type="hidden" value="<%= userGroupsDisplayContext.getNavigation() %>" />
	<aui:input name="roleId" type="hidden" value="<%= (role != null) ? role.getRoleId() : 0 %>" />

	<liferay-ui:search-container
		id="userGroups"
		searchContainer="<%= userGroupsDisplayContext.getUserGroupSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.UserGroup"
			escapedModel="<%= true %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			String displayStyle = userGroupsDisplayContext.getDisplayStyle();

			boolean selectUserGroup = false;
			%>

			<%@ include file="/user_group_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= userGroupsDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<portlet:actionURL name="addGroupUserGroups" var="addGroupUserGroupsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= addGroupUserGroupsURL %>" cssClass="hide" name="addGroupUserGroupsFm">
	<aui:input name="tabs1" type="hidden" value="user-groups" />
</aui:form>

<portlet:actionURL name="editUserGroupGroupRole" var="editUserGroupGroupRoleURL" />

<aui:form action="<%= editUserGroupGroupRoleURL %>" cssClass="hide" name="editUserGroupGroupRoleFm">
	<aui:input name="tabs1" type="hidden" value="user-groups" />
	<aui:input name="userGroupId" type="hidden" />
</aui:form>

<liferay-frontend:component
	componentId="<%= userGroupsManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	context="<%= userGroupsManagementToolbarDisplayContext.getComponentContext() %>"
	module="js/UserGroupsManagementToolbarDefaultEventHandler.es"
/>