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

Role role = userGroupsDisplayContext.getRole();
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= siteMembershipsDisplayContext.getViewNavigationItems() %>"
/>

<clay:management-toolbar
	displayContext="<%= new UserGroupsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, userGroupsDisplayContext) %>"
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

<aui:script use="liferay-item-selector-dialog">
	var form = $(document.<portlet:namespace />fm);

	var deleteSelectedUserGroups = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm(form);
		}
	};

	var removeUserGroupSiteRole, selectSiteRole;

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.ASSIGN_USER_ROLES) %>">
		<c:if test="<%= role != null %>">
			removeUserGroupSiteRole = function() {
				if (confirm('<liferay-ui:message arguments="<%= role.getTitle(themeDisplay.getLocale()) %>" key="are-you-sure-you-want-to-remove-x-role-to-selected-user-groups" translateArguments="<%= false %>" />')) {
					submitForm(form, '<portlet:actionURL name="removeUserGroupSiteRole" />');
				}
			};
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

								submitForm(form, '<portlet:actionURL name="editUserGroupsSiteRoles"><portlet:param name="tabs1" value="user-groups" /></portlet:actionURL>');
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
				eventName: '<portlet:namespace />selectUserGroups',
				on: {
					selectedItemChange: function(event) {
						var selectedItem = event.newVal;

						if (selectedItem) {
							var addGroupUserGroupsFm = $(document.<portlet:namespace />addGroupUserGroupsFm);

							addGroupUserGroupsFm.append(selectedItem);

							submitForm(addGroupUserGroupsFm);
						}
					}
				},
				'strings.add': '<liferay-ui:message key="done" />',
				title: '<liferay-ui:message key="assign-user-groups-to-this-site" />',
				url: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_user_groups.jsp" /></portlet:renderURL>'
			}
		);

		itemSelectorDialog.open();
	}

	<portlet:renderURL var="viewRoleURL">
		<portlet:param name="mvcPath" value="/view.jsp" />
		<portlet:param name="tabs1" value="user-groups" />
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
		'deleteSelectedUserGroups': deleteSelectedUserGroups,
		'removeUserGroupSiteRole': removeUserGroupSiteRole,
		'selectRoles': selectRoles,
		'selectSiteRole': selectSiteRole
	};

	Liferay.componentReady('userGroupsManagementToolbar').then(
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