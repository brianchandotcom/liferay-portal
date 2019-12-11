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
	displayStyle = portalPreferences.getValue(UserGroupsAdminPortletKeys.USER_GROUPS_ADMIN, "users-display-style", "list");
}
else {
	portalPreferences.setValue(UserGroupsAdminPortletKeys.USER_GROUPS_ADMIN, "users-display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectUsers");

List<NavigationItem> navigationItems = new ArrayList<>();

NavigationItem entriesNavigationItem = new NavigationItem();

entriesNavigationItem.setActive(true);
entriesNavigationItem.setHref(StringPool.BLANK);
entriesNavigationItem.setLabel(LanguageUtil.get(request, "users"));

navigationItems.add(entriesNavigationItem);
%>

<clay:navigation-bar
	navigationItems="<%= navigationItems %>"
/>

<%
EditUserGroupAssignmentsManagementToolbarDisplayContext editUserGroupAssignmentsManagementToolbarDisplayContext = new EditUserGroupAssignmentsManagementToolbarDisplayContext(request, renderRequest, renderResponse, displayStyle, "/select_user_group_users.jsp");

LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

if (filterManageableOrganizations) {
	userParams.put("usersOrgsTree", user.getOrganizations());
}

SearchContainer searchContainer = editUserGroupAssignmentsManagementToolbarDisplayContext.getSearchContainer(userParams);
%>

<clay:management-toolbar
	clearResultsURL="<%= editUserGroupAssignmentsManagementToolbarDisplayContext.getClearResultsURL() %>"
	filterDropdownItems="<%= editUserGroupAssignmentsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= searchContainer.getTotal() %>"
	searchActionURL="<%= editUserGroupAssignmentsManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="selectUsers"
	searchFormName="searchFm"
	selectable="<%= true %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= editUserGroupAssignmentsManagementToolbarDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= editUserGroupAssignmentsManagementToolbarDisplayContext.getViewTypeItems() %>"
/>

<aui:form cssClass="container-fluid-1280" method="post" name="fm">
	<liferay-ui:search-container
		id="selectUsers"
		searchContainer="<%= searchContainer %>"
		var="userSearchContainer"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>

			<%
			boolean showActions = false;
			%>

			<%@ include file="/user_search_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />selectUsers'
	);

	searchContainer.on('rowToggled', function(event) {
		var selectedItems = event.elements.allSelectedElements;

		var data = [];

		if (selectedItems.size() > 0) {
			data = selectedItems.attr('value');
		}

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(eventName) %>',
			{
				data: data.join(',')
			}
		);
	});
</aui:script>