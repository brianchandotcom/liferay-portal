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
String displayStyle = portalPreferences.getValue(SiteMembershipsPortletKeys.SITE_MEMBERSHIPS_ADMIN, "display-style", "icon");
String navigation = ParamUtil.getString(request, "navigation", "all");
String orderByCol = ParamUtil.getString(request, "orderByCol", "first-name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");
long roleId = ParamUtil.getLong(request, "roleId");

Role role = null;

if (roleId > 0) {
	role = RoleLocalServiceUtil.fetchRole(roleId);
}

PortletURL viewUsersURL = renderResponse.createRenderURL();

viewUsersURL.setParameter("mvcPath", "/view.jsp");
viewUsersURL.setParameter("tabs1", "users");
viewUsersURL.setParameter("redirect", currentURL);
viewUsersURL.setParameter("groupId", String.valueOf(siteMembershipsDisplayContext.getGroupId()));

if (role != null) {
	viewUsersURL.setParameter("roleId", String.valueOf(roleId));
}

UserSearch userSearch = new UserSearch(renderRequest, PortletURLUtil.clone(viewUsersURL, renderResponse));

userSearch.setEmptyResultsMessage("no-user-was-found-that-is-a-direct-member-of-this-site");

RowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);

UserSearchTerms searchTerms = (UserSearchTerms)userSearch.getSearchTerms();

boolean hasAssignMembersPermission = GroupPermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroupId(), ActionKeys.ASSIGN_MEMBERS);

if (!searchTerms.isSearch() && hasAssignMembersPermission) {
	userSearch.setEmptyResultsMessageCssClass("taglib-empty-result-message-header-has-plus-btn");
}

LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

userParams.put("inherit", Boolean.TRUE);
userParams.put("usersGroups", Long.valueOf(siteMembershipsDisplayContext.getGroupId()));

if (role != null) {
	userParams.put("userGroupRole", new Long[] {Long.valueOf(siteMembershipsDisplayContext.getGroupId()), Long.valueOf(roleId)});
}

int usersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams);

userSearch.setTotal(usersCount);

List<User> users = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), searchTerms.getStatus(), userParams, userSearch.getStart(), userSearch.getEnd(), userSearch.getOrderByComparator());

userSearch.setResults(users);
%>

<liferay-util:include page="/navigation_bar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchEnabled" value="<%= String.valueOf((usersCount > 0) || searchTerms.isSearch()) %>" />
</liferay-util:include>

<liferay-frontend:management-bar
	disabled='<%= (usersCount <= 0) && Objects.equals(navigation, "all") %>'
	includeCheckBox="<%= true %>"
	searchContainerId="users"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<liferay-portlet:actionURL name="changeDisplayStyle" varImpl="changeDisplayStyleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= changeDisplayStyleURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>

		<%
		String label = null;

		if (Objects.equals(navigation, "roles") && (role != null)) {
			label = LanguageUtil.get(request, "roles") + StringPool.COLON + StringPool.SPACE + HtmlUtil.escape(role.getTitle(themeDisplay.getLocale()));
		}
		%>

		<liferay-frontend:management-bar-navigation
			label="<%= label %>"
		>

			<%
			PortletURL viewAllURL = PortletURLUtil.clone(viewUsersURL, renderResponse);

			viewAllURL.setParameter("navigation", "all");
			viewAllURL.setParameter("roleId", "0");
			%>

			<liferay-frontend:management-bar-filter-item active='<%= Objects.equals(navigation, "all") %>' label="all" url="<%= viewAllURL.toString() %>" />

			<liferay-frontend:management-bar-filter-item active='<%= Objects.equals(navigation, "roles") %>' id="roles" label="roles" url="javascript:;" />
		</liferay-frontend:management-bar-navigation>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"first-name", "screen-name"} %>'
			portletURL="<%= PortletURLUtil.clone(viewUsersURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.ASSIGN_USER_ROLES) %>">
			<liferay-frontend:management-bar-button href="javascript:;" icon="users" id="selectSiteRole" label="assign-site-roles" />
		</c:if>

		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedUsers" label="remove-membership" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

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
			<aui:input name="addUserIds" type="hidden" />

			<liferay-ui:membership-policy-error />

			<liferay-ui:search-container
				id="users"
				rowChecker="<%= rowChecker %>"
				searchContainer="<%= userSearch %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.User"
					escapedModel="<%= true %>"
					keyProperty="userId"
					modelVar="user2"
					rowIdProperty="screenName"
				>

					<%
					boolean selectUsers = false;
					%>

					<%@ include file="/user_columns.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
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

<c:if test="<%= hasAssignMembersPermission %>">
	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item id="selectUsers" title='<%= LanguageUtil.get(request, "assign-users") %>' url="javascript:;" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script use="liferay-item-selector-dialog">
	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />deleteSelectedUsers').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm(form);
			}
		}
	);

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.ASSIGN_USER_ROLES) %>">
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
		);
	</c:if>

	$('#<portlet:namespace />selectUsers').on(
		'click',
		function(event) {
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
	);

	<portlet:renderURL var="viewRoleURL">
		<portlet:param name="mvcPath" value="/view.jsp" />
		<portlet:param name="tabs1" value="users" />
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
					uri: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/select_site_role.jsp" /></portlet:renderURL>'
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