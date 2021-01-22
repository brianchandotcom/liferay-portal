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
UsersDisplayContext usersDisplayContext = new UsersDisplayContext(request, renderRequest, renderResponse);

UsersManagementToolbarDisplayContext usersManagementToolbarDisplayContext = new UsersManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, usersDisplayContext);

Role role = usersDisplayContext.getRole();
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= siteMembershipsDisplayContext.getViewNavigationItems() %>"
/>

<clay:management-toolbar-v2
	displayContext="<%= usersManagementToolbarDisplayContext %>"
/>

<liferay-ui:error embed="<%= false %>" exception="<%= RequiredUserException.class %>" message="one-or-more-users-were-not-removed-since-they-belong-to-a-user-group" />

<div class="closed sidenav-container sidenav-right" id="<%= liferayPortletResponse.getNamespace() + "infoPanelId" %>">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/site_memberships/users_info_panel" var="sidebarPanelURL">
		<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
	</liferay-portlet:resourceURL>

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="users"
	>
		<liferay-util:include page="/user_info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<clay:container-fluid>
			<portlet:actionURL name="deleteGroupUsers" var="deleteGroupUsersURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:actionURL>

			<aui:form action="<%= deleteGroupUsersURL %>" cssClass="portlet-site-memberships-users" method="post" name="fm">
				<aui:input name="tabs1" type="hidden" value="users" />
				<aui:input name="navigation" type="hidden" value="<%= usersDisplayContext.getNavigation() %>" />
				<aui:input name="addUserIds" type="hidden" />
				<aui:input name="roleId" type="hidden" value="<%= (role != null) ? role.getRoleId() : 0 %>" />

				<liferay-ui:breadcrumb
					showLayout="<%= false %>"
				/>

				<liferay-ui:membership-policy-error />

				<liferay-ui:search-container
					id="users"
					searchContainer="<%= usersDisplayContext.getUserSearchContainer() %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.portal.kernel.model.User"
						escapedModel="<%= true %>"
						keyProperty="userId"
						modelVar="user2"
						rowIdProperty="screenName"
					>

						<%
						String displayStyle = usersDisplayContext.getDisplayStyle();

						boolean selectUsers = false;

						row.setData(
							HashMapBuilder.<String, Object>put(
								"actions", usersManagementToolbarDisplayContext.getAvailableActions(user2)
							).build());
						%>

						<%@ include file="/user_columns.jspf" %>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						displayStyle="<%= usersDisplayContext.getDisplayStyle() %>"
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</aui:form>
		</clay:container-fluid>
	</div>
</div>

<portlet:actionURL name="addGroupUsers" var="addGroupUsersURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= addGroupUsersURL %>" cssClass="hide" method="post" name="addGroupUsersFm">
	<aui:input name="tabs1" type="hidden" value="users" />
</aui:form>

<aui:form cssClass="hide" method="post" name="editUserGroupRoleFm">
	<aui:input name="tabs1" type="hidden" value="users" />
</aui:form>

<liferay-frontend:component
	componentId="<%= usersManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/UsersManagementToolbarDefaultEventHandler.es"
/>

<liferay-frontend:component
	componentId="<%= SiteMembershipWebKeys.USER_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/UserDropdownDefaultEventHandler.es"
/>