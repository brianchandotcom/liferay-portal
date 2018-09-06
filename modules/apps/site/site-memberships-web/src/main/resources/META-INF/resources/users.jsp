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

Role role = usersDisplayContext.getRole();
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= siteMembershipsDisplayContext.getViewNavigationItems() %>"
/>

<clay:management-toolbar
	actionDropdownItems="<%= usersDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= usersDisplayContext.getClearResultsURL() %>"
	componentId="usersManagementToolbar"
	disabled="<%= usersDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= usersDisplayContext.getFilterDropdownItems() %>"
	infoPanelId="infoPanelId"
	itemsTotal="<%= usersDisplayContext.getTotalItems() %>"
	searchActionURL="<%= usersDisplayContext.getSearchActionURL() %>"
	searchContainerId="users"
	searchFormName="searchFm"
	showCreationMenu="<%= GroupPermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>"
	showInfoButton="<%= true %>"
	showSearch="<%= usersDisplayContext.isShowSearch() %>"
	sortingOrder="<%= usersDisplayContext.getOrderByType() %>"
	sortingURL="<%= usersDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= usersDisplayContext.getViewTypeItems() %>"
/>

<liferay-ui:error embed="<%= false %>" exception="<%= RequiredUserException.class %>" message="one-or-more-users-were-not-removed-since-they-belong-to-a-user-group" />

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/user/info_panel" var="sidebarPanelURL">
		<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
	</liferay-portlet:resourceURL>

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="users"
	>
		<liferay-util:include page="/user_info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<portlet:actionURL name="deleteGroupUsers" var="deleteGroupUsersURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:actionURL>

		<aui:form action="<%= deleteGroupUsersURL %>" cssClass="portlet-site-memberships-users" method="post" name="fm">
			<aui:input name="tabs1" type="hidden" value="users" />
			<aui:input name="navigation" type="hidden" value="<%= usersDisplayContext.getNavigation() %>" />
			<aui:input name="addUserIds" type="hidden" />
			<aui:input name="roleId" type="hidden" value="<%= (role != null) ? role.getRoleId() : 0 %>" />

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
					%>

					<%@ include file="/user_columns.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= usersDisplayContext.getDisplayStyle() %>"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</div>
</div>

<portlet:actionURL name="addGroupUsers" var="addGroupUsersURL" />

<aui:form action="<%= addGroupUsersURL %>" cssClass="hide" method="post" name="addGroupUsersFm">
	<aui:input name="tabs1" type="hidden" value="users" />
</aui:form>

<portlet:actionURL name="editUserGroupRole" var="editUserGroupRoleURL" />

<aui:form action="<%= editUserGroupRoleURL %>" cssClass="hide" method="post" name="editUserGroupRoleFm">
	<aui:input name="tabs1" type="hidden" value="users" />
	<aui:input name="p_u_i_d" type="hidden" />
</aui:form>

<aui:script use="liferay-item-selector-dialog">
	var form = $(document.<portlet:namespace />fm);

	var deleteSelectedUsers = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm(form);
		}
	};

	var removeUserSiteRole, selectSiteRole;

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.ASSIGN_USER_ROLES) %>">
		<c:if test="<%= role != null %>">
			removeUserSiteRole = function() {
				if (confirm('<liferay-ui:message arguments="<%= role.getTitle(themeDisplay.getLocale()) %>" key="are-you-sure-you-want-to-remove-x-role-to-selected-users" translateArguments="<%= false %>" />')) {
					submitForm(form, '<portlet:actionURL name="removeUserSiteRole" />');
				}
			}
		</c:if>

		<portlet:renderURL var="selectSiteRoleURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/site_roles.jsp" />
			<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
		</portlet:renderURL>

		selectSiteRole = function() {
			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<portlet:namespace />selectSiteRole',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								form.append(selectedItem);

								submitForm(form, '<portlet:actionURL name="editUsersSiteRoles" />');
							}
						}
					},
					'strings.add': '<liferay-ui:message key="done" />',
					title: '<liferay-ui:message key="assign-site-roles" />',
					url: '<%= selectSiteRoleURL %>'
				}
			);

			itemSelectorDialog.open();
		}
	</c:if>

	function handleAddClick(event) {
		event.preventDefault();

		var itemSelectorDialog = new A.LiferayItemSelectorDialog(
			{
				eventName: '<portlet:namespace />selectUsers',
				on: {
					selectedItemChange: function(event) {
						var selectedItem = event.newVal;

						if (selectedItem) {
							var addGroupUsersFm = $(document.<portlet:namespace />addGroupUsersFm);

							addGroupUsersFm.append(selectedItem);

							submitForm(addGroupUsersFm);
						}
					}
				},
				'strings.add': '<liferay-ui:message key="done" />',
				title: '<liferay-ui:message key="assign-users-to-this-site" />',
				url: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_users.jsp" /></portlet:renderURL>'
			}
		);

		itemSelectorDialog.open();
	}

	<portlet:renderURL var="viewRoleURL">
		<portlet:param name="mvcPath" value="/view.jsp" />
		<portlet:param name="tabs1" value="users" />
		<portlet:param name="navigation" value="roles" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
	</portlet:renderURL>

	var selectRoles = function() {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					destroyOnHide: true,
					modal: true
				},
				eventName: '<portlet:namespace />selectSiteRole',
				title: '<liferay-ui:message key="select-site-role" />',
				uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_site_role.jsp" /><portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" /></portlet:renderURL>'
			},
			function(event) {
				var uri = '<%= viewRoleURL %>';

				uri = Liferay.Util.addParams('<portlet:namespace />roleId=' + event.id, uri);

				location.href = uri;
			}
		);
	};

	var ACTIONS = {
		'deleteSelectedUsers': deleteSelectedUsers,
		'removeUserSiteRole': removeUserSiteRole,
		'selectRoles': selectRoles,
		'selectSiteRole': selectSiteRole
	};

	Liferay.componentReady('usersManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on('creationButtonClicked', handleAddClick);

			managementToolbar.on(
				['actionItemClicked', 'filterItemClicked'],
				function(event) {
					var itemData = event.data.item.data;

					if (itemData && itemData.action && ACTIONS[itemData.action]) {
						ACTIONS[itemData.action]();
					}
				}
			);
		}
	);
</aui:script>