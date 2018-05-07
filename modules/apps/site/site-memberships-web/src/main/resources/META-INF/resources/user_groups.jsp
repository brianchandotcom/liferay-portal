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
	items="<%= siteMembershipsDisplayContext.getViewNavigationItems() %>"
/>

<liferay-frontend:management-bar
	disabled="<%= userGroupsDisplayContext.isDisabledManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="userGroups"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-portlet:actionURL name="changeDisplayStyle" varImpl="changeDisplayStyleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= changeDisplayStyleURL %>"
			selectedDisplayStyle="<%= userGroupsDisplayContext.getDisplayStyle() %>"
		/>

		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					id="selectUserGroups"
					title='<%= LanguageUtil.get(request, "assign-user-groups") %>'
					url="javascript:;"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>

		<%
		String label = null;

		if (Objects.equals(userGroupsDisplayContext.getNavigation(), "roles") && (role != null)) {
			label = LanguageUtil.get(request, "roles") + StringPool.COLON + StringPool.SPACE + HtmlUtil.escape(role.getTitle(themeDisplay.getLocale()));
		}
		%>

		<liferay-frontend:management-bar-navigation
			label="<%= label %>"
		>

			<%
			PortletURL viewAllURL = userGroupsDisplayContext.getPortletURL();

			viewAllURL.setParameter("navigation", "all");
			viewAllURL.setParameter("roleId", "0");
			%>

			<liferay-frontend:management-bar-filter-item
				active='<%= Objects.equals(userGroupsDisplayContext.getNavigation(), "all") %>'
				label="all"
				url="<%= viewAllURL.toString() %>"
			/>

			<liferay-frontend:management-bar-filter-item
				active='<%= Objects.equals(userGroupsDisplayContext.getNavigation(), "roles") %>'
				id="roles"
				label="roles"
				url="javascript:;"
			/>
		</liferay-frontend:management-bar-navigation>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= userGroupsDisplayContext.getOrderByCol() %>"
			orderByType="<%= userGroupsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name", "description"} %>'
			portletURL="<%= userGroupsDisplayContext.getPortletURL() %>"
		/>

		<c:if test="<%= userGroupsDisplayContext.isShowSearch() %>">
			<li>
				<aui:form action="<%= siteMembershipsDisplayContext.getPortletURL() %>" name="searchFm">
					<liferay-ui:input-search
						autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>"
						markupView="lexicon"
					/>
				</aui:form>
			</li>
		</c:if>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.ASSIGN_USER_ROLES) %>">
			<liferay-frontend:management-bar-button
				href="javascript:;"
				icon="add-role"
				id="selectSiteRole"
				label="assign-site-roles"
			/>

			<c:if test="<%= role != null %>">

				<%
				String label = LanguageUtil.format(request, "remove-site-role-x", role.getTitle(themeDisplay.getLocale()), false);
				%>

				<liferay-frontend:management-bar-button
					href="javascript:;"
					icon="remove-role"
					id="removeUserGroupSiteRole"
					label="<%= label %>"
				/>
			</c:if>
		</c:if>

		<liferay-frontend:management-bar-button
			href="javascript:;"
			icon="trash"
			id="deleteSelectedUserGroups"
			label="remove-membership"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

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

<portlet:actionURL name="addGroupUserGroups" var="addGroupUserGroupsURL" />

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

	$('#<portlet:namespace />deleteSelectedUserGroups').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm(form);
			}
		}
	);

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.ASSIGN_USER_ROLES) %>">
		<c:if test="<%= role != null %>">
			$('#<portlet:namespace />removeUserGroupSiteRole').on(
				'click',
				function() {
					if (confirm('<liferay-ui:message arguments="<%= role.getTitle(themeDisplay.getLocale()) %>" key="are-you-sure-you-want-to-remove-x-role-to-selected-user-groups" translateArguments="<%= false %>" />')) {
						submitForm(form, '<portlet:actionURL name="removeUserGroupSiteRole" />');
					}
				}
			);
		</c:if>

		<portlet:renderURL var="selectSiteRoleURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/site_roles.jsp" />
			<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
		</portlet:renderURL>

		$('#<portlet:namespace />selectSiteRole').on(
			'click',
			function() {
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
		);
	</c:if>

	$('#<portlet:namespace />selectUserGroups').on(
		'click',
		function(event) {
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
	);

	<portlet:renderURL var="viewRoleURL">
		<portlet:param name="mvcPath" value="/view.jsp" />
		<portlet:param name="tabs1" value="user-groups" />
		<portlet:param name="navigation" value="roles" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(siteMembershipsDisplayContext.getGroupId()) %>" />
	</portlet:renderURL>

	$('#<portlet:namespace />roles').on(
		'click',
		function(event) {
			event.preventDefault();

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
		}
	);
</aui:script>