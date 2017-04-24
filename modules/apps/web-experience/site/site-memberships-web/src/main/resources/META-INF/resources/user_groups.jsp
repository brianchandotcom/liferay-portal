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
String orderByCol = ParamUtil.getString(request, "orderByCol", "name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");
long roleId = ParamUtil.getLong(request, "roleId");

Role role = null;

if (roleId > 0) {
	role = RoleLocalServiceUtil.fetchRole(roleId);
}

PortletURL viewUserGroupsURL = renderResponse.createRenderURL();

viewUserGroupsURL.setParameter("mvcPath", "/view.jsp");
viewUserGroupsURL.setParameter("tabs1", "user-groups");
viewUserGroupsURL.setParameter("redirect", currentURL);
viewUserGroupsURL.setParameter("groupId", String.valueOf(siteMembershipsDisplayContext.getGroupId()));

if (role != null) {
	viewUserGroupsURL.setParameter("roleId", String.valueOf(roleId));
}

UserGroupSearch userGroupSearch = new UserGroupSearch(renderRequest, PortletURLUtil.clone(viewUserGroupsURL, renderResponse));

userGroupSearch.setEmptyResultsMessage("no-user-group-was-found-that-is-a-member-of-this-site");

RowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);

UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)userGroupSearch.getSearchTerms();

boolean hasAssignMembersPermission = GroupPermissionUtil.contains(permissionChecker, siteMembershipsDisplayContext.getGroupId(), ActionKeys.ASSIGN_MEMBERS);

if (!searchTerms.isSearch() && hasAssignMembersPermission) {
	userGroupSearch.setEmptyResultsMessageCssClass("taglib-empty-result-message-header-has-plus-btn");
}

LinkedHashMap<String, Object> userGroupParams = new LinkedHashMap<String, Object>();

userGroupParams.put(UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_GROUPS, Long.valueOf(siteMembershipsDisplayContext.getGroupId()));

if (role != null) {
	userGroupParams.put(UserGroupFinderConstants.PARAM_KEY_USER_GROUP_GROUP_ROLE, new Long[] {Long.valueOf(roleId), Long.valueOf(siteMembershipsDisplayContext.getGroupId())});
}

int userGroupsCount = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams);

userGroupSearch.setTotal(userGroupsCount);

List<UserGroup> userGroups = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), userGroupParams, userGroupSearch.getStart(), userGroupSearch.getEnd(), userGroupSearch.getOrderByComparator());

userGroupSearch.setResults(userGroups);
%>

<liferay-util:include page="/navigation_bar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="searchEnabled" value="<%= String.valueOf((userGroupsCount > 0) || searchTerms.isSearch()) %>" />
</liferay-util:include>

<liferay-frontend:management-bar
	disabled='<%= (userGroupsCount <= 0) && Objects.equals(navigation, "all") %>'
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
			PortletURL viewAllURL = PortletURLUtil.clone(viewUserGroupsURL, renderResponse);

			viewAllURL.setParameter("navigation", "all");
			viewAllURL.setParameter("roleId", "0");
			%>

			<liferay-frontend:management-bar-filter-item active='<%= Objects.equals(navigation, "all") %>' label="all" url="<%= viewAllURL.toString() %>" />

			<liferay-frontend:management-bar-filter-item active='<%= Objects.equals(navigation, "roles") %>' id="roles" label="roles" url="javascript:;" />
		</liferay-frontend:management-bar-navigation>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"name", "description"} %>'
			portletURL="<%= PortletURLUtil.clone(viewUserGroupsURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.ASSIGN_USER_ROLES) %>">
			<liferay-frontend:management-bar-button href="javascript:;" icon="add-role" id="selectSiteRole" label="assign-site-roles" />

			<c:if test="<%= role != null %>">

				<%
				String label = LanguageUtil.format(request, "remove-site-role-x", role.getTitle(themeDisplay.getLocale()), false);
				%>

				<liferay-frontend:management-bar-button href="javascript:;" icon="remove-role" id="removeUserGroupSiteRole" label="<%= label %>" />
			</c:if>
		</c:if>

		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedUserGroups" label="remove-membership" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="deleteGroupUserGroups" var="deleteGroupUserGroupsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteGroupUserGroupsURL %>" cssClass="container-fluid-1280 portlet-site-memberships-user-groups" name="fm">
	<aui:input name="tabs1" type="hidden" value="user-groups" />
	<aui:input name="navigation" type="hidden" value="<%= navigation %>" />
	<aui:input name="roleId" type="hidden" value="<%= roleId %>" />

	<liferay-ui:search-container
		id="userGroups"
		rowChecker="<%= rowChecker %>"
		searchContainer="<%= userGroupSearch %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.UserGroup"
			escapedModel="<%= true %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>

			<%
			boolean selectUserGroup = false;
			%>

			<%@ include file="/user_group_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
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

<c:if test="<%= hasAssignMembersPermission %>">
	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item id="selectUserGroups" title='<%= LanguageUtil.get(request, "assign-user-groups") %>' url="javascript:;" />
	</liferay-frontend:add-menu>
</c:if>

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