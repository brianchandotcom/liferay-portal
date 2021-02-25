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
	displayStyle = portalPreferences.getValue(RolesAdminPortletKeys.ROLES_ADMIN, "displayStyle", "descriptive");
}
else {
	portalPreferences.setValue(RolesAdminPortletKeys.ROLES_ADMIN, "displayStyle", displayStyle);

	request.setAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
}

ViewRolesManagementToolbarDisplayContext viewRolesManagementToolbarDisplayContext = new ViewRolesManagementToolbarDisplayContext(request, renderRequest, renderResponse, displayStyle);

SearchContainer<Role> searchContainer = viewRolesManagementToolbarDisplayContext.getSearchContainer();

PortletURL portletURL = viewRolesManagementToolbarDisplayContext.getPortletURL();
%>

<clay:navigation-bar
	navigationItems="<%= roleDisplayContext.getViewRoleNavigationItems(liferayPortletResponse, portletURL) %>"
/>

<portlet:actionURL name="deleteRoles" var="deleteRolesURL">
	<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
</portlet:actionURL>

<clay:management-toolbar
	actionDropdownItems="<%= viewRolesManagementToolbarDisplayContext.getActionDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteRolesURL", deleteRolesURL.toString()
		).build()
	%>'
	clearResultsURL="<%= viewRolesManagementToolbarDisplayContext.getClearResultsURL() %>"
	componentId="viewRolesManagementToolbar"
	creationMenu="<%= viewRolesManagementToolbarDisplayContext.getCreationMenu() %>"
	filterDropdownItems="<%= viewRolesManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	itemsTotal="<%= searchContainer.getTotal() %>"
	propsTransformer="js/ViewRolesManagementToolbarPropsTransformer"
	searchActionURL="<%= viewRolesManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="roleSearch"
	searchFormName="searchFm"
	selectable="<%= true %>"
	showCreationMenu="<%= viewRolesManagementToolbarDisplayContext.showCreationMenu() %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= viewRolesManagementToolbarDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= viewRolesManagementToolbarDisplayContext.getViewTypeItems() %>"
/>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid container-fluid-max-xl container-form-view" method="get" name="fm">
	<liferay-ui:error exception="<%= RequiredRoleException.class %>" message="you-cannot-delete-a-system-role" />

	<aui:input name="deleteRoleIds" type="hidden" />

	<liferay-ui:search-container
		id="roleSearch"
		searchContainer="<%= searchContainer %>"
		var="roleSearchContainer"
	>
		<aui:input name="rolesRedirect" type="hidden" value="<%= portletURL.toString() %>" />

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Role"
			keyProperty="roleId"
			modelVar="role"
		>

			<%
			PortletURL rowURL = null;

			if (RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.UPDATE)) {
				rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/edit_role.jsp");
				rowURL.setParameter("tabs1", "details");

				PortletURL searchContainerPortletURL = roleSearchContainer.getIteratorURL();

				rowURL.setParameter("backURL", searchContainerPortletURL.toString());

				rowURL.setParameter("roleId", String.valueOf(role.getRoleId()));
			}
			%>

			<%@ include file="/search_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>