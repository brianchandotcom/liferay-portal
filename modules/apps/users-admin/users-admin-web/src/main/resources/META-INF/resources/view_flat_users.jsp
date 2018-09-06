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
String backURL = GetterUtil.getString(request.getAttribute("view.jsp-backURL"));
int status = GetterUtil.getInteger(request.getAttribute("view.jsp-status"));
String usersListView = GetterUtil.getString(request.getAttribute("view.jsp-usersListView"));
String viewUsersRedirect = GetterUtil.getString(request.getAttribute("view.jsp-viewUsersRedirect"));

if (!ParamUtil.getBoolean(renderRequest, "advancedSearch")) {
	currentURLObj.setParameter("status", String.valueOf(status));
}

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(UsersAdminPortletKeys.USERS_ADMIN, "display-style", "list");
}
else {
	portalPreferences.setValue(UsersAdminPortletKeys.USERS_ADMIN, "display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

String navigation = ParamUtil.getString(request, "navigation", "active");
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all-users");

ViewUsersManagementToolbarDisplayContext viewUsersManagementToolbarDisplayContext = new ViewUsersManagementToolbarDisplayContext(request, renderRequest, renderResponse, displayStyle, navigation, status);

SearchContainer searchContainer = viewUsersManagementToolbarDisplayContext.getSearchContainer();

if (navigation.equals("active")) {
	status = WorkflowConstants.STATUS_APPROVED;
}
else if (navigation.equals("inactive")) {
	status = WorkflowConstants.STATUS_INACTIVE;
}

request.setAttribute(UsersAdminWebKeys.STATUS, status);

PortletURL portletURL = viewUsersManagementToolbarDisplayContext.getPortletURL();

portletURL.setParameter("status", String.valueOf(status));

boolean showDeleteButton = viewUsersManagementToolbarDisplayContext.isShowDeleteButton();
boolean showRestoreButton = viewUsersManagementToolbarDisplayContext.isShowRestoreButton();
%>

<clay:management-toolbar
	actionDropdownItems="<%= viewUsersManagementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= viewUsersManagementToolbarDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= viewUsersManagementToolbarDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= viewUsersManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= searchContainer.getTotal() %>"
	searchActionURL="<%= viewUsersManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="users"
	searchFormName="searchFm"
	selectable="<%= true %>"
	showCreationMenu="<%= viewUsersManagementToolbarDisplayContext.showCreationMenu() %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= viewUsersManagementToolbarDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= viewUsersManagementToolbarDisplayContext.getViewTypeItems() %>"
/>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "search();" %>'>
	<liferay-portlet:renderURLParams varImpl="portletURL" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="toolbarItem" type="hidden" value="<%= toolbarItem %>" />
	<aui:input name="usersListView" type="hidden" value="<%= usersListView %>" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

	<liferay-ui:error exception="<%= RequiredUserException.class %>" message="you-cannot-delete-or-deactivate-yourself" />

	<c:if test="<%= Validator.isNotNull(viewUsersRedirect) %>">
		<aui:input name="viewUsersRedirect" type="hidden" value="<%= viewUsersRedirect %>" />
	</c:if>

	<liferay-ui:search-container
		cssClass="users-search-container"
		id="users"
		searchContainer="<%= searchContainer %>"
		var="userSearchContainer"
	>
		<aui:input disabled="<%= true %>" name="usersRedirect" type="hidden" value="<%= currentURLObj.toString() %>" />
		<aui:input name="deleteUserIds" type="hidden" />
		<aui:input name="status" type="hidden" value="<%= status %>" />

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.User"
			escapedModel="<%= true %>"
			keyProperty="userId"
			modelVar="user2"
			rowIdProperty="screenName"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="mvcRenderCommandName" value="/users_admin/edit_user" />
				<portlet:param name="redirect" value="<%= userSearchContainer.getIteratorURL().toString() %>" />
				<portlet:param name="p_u_i_d" value="<%= String.valueOf(user2.getUserId()) %>" />
			</liferay-portlet:renderURL>

			<%
			if (!UserPermissionUtil.contains(permissionChecker, user2.getUserId(), ActionKeys.UPDATE)) {
				rowURL = null;
			}
			%>

			<%@ include file="/user/search_columns.jspf" %>
		</liferay-ui:search-container-row>

		<%
		List<User> results = searchContainer.getResults();

		showDeleteButton = !results.isEmpty() && showDeleteButton;
		showRestoreButton = !results.isEmpty() && showRestoreButton;
		%>

		<%
		if (!showDeleteButton && !showRestoreButton) {
			userSearchContainer.setRowChecker(null);
		}
		%>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>