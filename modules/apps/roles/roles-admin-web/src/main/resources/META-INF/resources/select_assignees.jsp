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
String tabs2 = ParamUtil.getString(request, "tabs2", "users");

String redirect = ParamUtil.getString(request, "redirect");

long roleId = ParamUtil.getLong(request, "roleId");

Role role = RoleServiceUtil.fetchRole(roleId);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(RolesAdminPortletKeys.ROLES_ADMIN, "assignees-display-style", "list");
}
else {
	portalPreferences.setValue(RolesAdminPortletKeys.ROLES_ADMIN, "assignees-display-style", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectAssignees");

EditRoleAssignmentsManagementToolbarDisplayContext editRoleAssignmentsManagementToolbarDisplayContext = new EditRoleAssignmentsManagementToolbarDisplayContext(request, renderRequest, renderResponse, displayStyle, "available");

SearchContainer searchContainer = editRoleAssignmentsManagementToolbarDisplayContext.getSearchContainer();

PortletURL portletURL = editRoleAssignmentsManagementToolbarDisplayContext.getPortletURL();
%>

<clay:navigation-bar
	navigationItems="<%= roleDisplayContext.getSelectAssigneesNavigationItems(portletURL) %>"
/>

<clay:management-toolbar
	clearResultsURL="<%= editRoleAssignmentsManagementToolbarDisplayContext.getClearResultsURL() %>"
	filterDropdownItems="<%= editRoleAssignmentsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= searchContainer.getTotal() %>"
	searchActionURL="<%= editRoleAssignmentsManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="assigneesSearch"
	searchFormName="searchFm"
	selectable="<%= true %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= editRoleAssignmentsManagementToolbarDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= editRoleAssignmentsManagementToolbarDisplayContext.getViewTypeItems() %>"
/>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid container-fluid-max-xl container-form-lg" method="post" name="fm">
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="tabs3" type="hidden" value="available" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="roleId" type="hidden" value="<%= role.getRoleId() %>" />

	<%
	request.setAttribute("edit_role_assignments.jsp-displayStyle", displayStyle);
	request.setAttribute("edit_role_assignments.jsp-searchContainer", searchContainer);
	%>

	<c:choose>
		<c:when test='<%= tabs2.equals("users") %>'>
			<liferay-util:include page="/edit_role_assignments_users.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("sites") %>'>
			<liferay-util:include page="/edit_role_assignments_sites.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("organizations") %>'>
			<liferay-util:include page="/edit_role_assignments_organizations.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= tabs2.equals("user-groups") %>'>
			<liferay-util:include page="/edit_role_assignments_user_groups.jsp" servletContext="<%= application %>" />
		</c:when>
	</c:choose>
</aui:form>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />assigneesSearch'
	);

	searchContainer.on('rowToggled', function(event) {
		var nodes = event.elements.currentPageSelectedElements.getDOMNodes();

		var <portlet:namespace />assigneeIds = nodes.map(function(node) {
			return node.value;
		});

		var result = {};

		if (<portlet:namespace />assigneeIds.length > 0) {
			result = {
				data: {
					type: '<%= HtmlUtil.escapeJS(tabs2) %>',
					value: <portlet:namespace />assigneeIds.join(',')
				}
			};
		}

		Liferay.Util.getOpener().Liferay.fire(
			'<%= HtmlUtil.escapeJS(eventName) %>',
			result
		);
	});
</aui:script>