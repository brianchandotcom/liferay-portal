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
String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(UserGroupsAdminPortletKeys.USER_GROUPS_ADMIN, "display-style", "list");
}
else {
	portalPreferences.setValue(UserGroupsAdminPortletKeys.USER_GROUPS_ADMIN, "display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "user-groups"), null);
%>

<liferay-ui:error exception="<%= RequiredUserGroupException.class %>" message="you-cannot-delete-user-groups-that-have-users" />

<%
List<NavigationItem> navigationItems = new ArrayList<>();

NavigationItem entriesNavigationItem = new NavigationItem();

entriesNavigationItem.setActive(true);
entriesNavigationItem.setHref(StringPool.BLANK);
entriesNavigationItem.setLabel(LanguageUtil.get(request, "user-groups"));

navigationItems.add(entriesNavigationItem);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= navigationItems %>"
/>

<%
ViewUserGroupsManagementToolbarDisplayContext viewUserGroupsManagementToolbarDisplayContext = new ViewUserGroupsManagementToolbarDisplayContext(request, renderRequest, renderResponse, displayStyle);

SearchContainer searchContainer = viewUserGroupsManagementToolbarDisplayContext.getSearchContainer();

PortletURL portletURL = viewUserGroupsManagementToolbarDisplayContext.getPortletURL();
%>

<clay:management-toolbar
	actionDropdownItems="<%= viewUserGroupsManagementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= viewUserGroupsManagementToolbarDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= viewUserGroupsManagementToolbarDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= viewUserGroupsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= searchContainer.getTotal() %>"
	searchActionURL="<%= viewUserGroupsManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="userGroups"
	searchFormName="searchFm"
	selectable="<%= true %>"
	showCreationMenu="<%= viewUserGroupsManagementToolbarDisplayContext.showCreationMenu() %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= viewUserGroupsManagementToolbarDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= viewUserGroupsManagementToolbarDisplayContext.getViewTypeItems() %>"
/>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
	<aui:input name="deleteUserGroupIds" type="hidden" />

	<div id="breadcrumb">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showPortletBreadcrumb="<%= true %>"
		/>
	</div>

	<%@ include file="/view_flat_user_groups.jspf" %>
</aui:form>

<aui:script>
	window.<portlet:namespace />deleteUserGroups = function() {
		<portlet:namespace />doDeleteUserGroup(
			'<%= UserGroup.class.getName() %>',
			Liferay.Util.listCheckedExcept(
				document.<portlet:namespace />fm,
				'<portlet:namespace />allRowIds'
			)
		);
	};

	window.<portlet:namespace />doDeleteUserGroup = function(className, ids) {
		var status = <%= WorkflowConstants.STATUS_INACTIVE %>;

		<portlet:namespace />getUsersCount(className, ids, status, function(
			responseData
		) {
			var count = parseInt(responseData, 10);

			if (count > 0) {
				status = <%= WorkflowConstants.STATUS_APPROVED %>;

				<portlet:namespace />getUsersCount(className, ids, status, function(
					responseData
				) {
					count = parseInt(responseData, 10);

					if (count > 0) {
						if (
							confirm(
								'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
							)
						) {
							<portlet:namespace />doDeleteUserGroups(ids);
						}
					} else {
						var message;

						if (ids && ids.toString().split(',').length > 1) {
							message =
								'<%= UnicodeLanguageUtil.get(request, "one-or-more-user-groups-are-associated-with-deactivated-users.-do-you-want-to-proceed-with-deleting-the-selected-user-groups-by-automatically-unassociating-the-deactivated-users") %>';
						} else {
							message =
								'<%= UnicodeLanguageUtil.get(request, "the-selected-user-group-is-associated-with-deactivated-users.-do-you-want-to-proceed-with-deleting-the-selected-user-group-by-automatically-unassociating-the-deactivated-users") %>';
						}

						if (confirm(message)) {
							<portlet:namespace />doDeleteUserGroups(ids);
						}
					}
				});
			} else if (
				confirm(
					'<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>'
				)
			) {
				<portlet:namespace />doDeleteUserGroups(ids);
			}
		});
	};

	function <portlet:namespace />doDeleteUserGroups(userGroupIds) {
		var form = document.<portlet:namespace />fm;

		var p_p_lifecycle = form.p_p_lifecycle;

		if (p_p_lifecycle) {
			p_p_lifecycle.value = '1';
		}

		<portlet:renderURL var="userGroupsRenderURL">
			<portlet:param name="mvcPath" value="/view.jsp" />
		</portlet:renderURL>

		Liferay.Util.postForm(form, {
			data: {
				deleteUserGroupIds: userGroupIds,
				redirect: '<%= userGroupsRenderURL %>'
			},
			url: '<portlet:actionURL name="deleteUserGroups" />'
		});
	}

	<liferay-portlet:resourceURL id="/users_admin/get_users_count" portletName="<%= UsersAdminPortletKeys.USERS_ADMIN %>" var="getUsersCountResourceURL" />

	function <portlet:namespace />getUsersCount(className, ids, status, callback) {
		const url = new URL(
			'<%= getUsersCountResourceURL %>',
			window.location.origin
		);

		url.searchParams.set('className', className);
		url.searchParams.set('ids', ids);
		url.searchParams.set('status', status);

		Liferay.Util.fetch(url.toString())
			.then(function(response) {
				return response.text();
			})
			.then(function(response) {
				callback(response);
			});
	}
</aui:script>