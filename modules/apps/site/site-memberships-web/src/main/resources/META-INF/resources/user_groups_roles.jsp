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
UserGroupRolesDisplayContext userGroupRolesDisplayContext = new UserGroupRolesDisplayContext(request, renderRequest, renderResponse);
%>

<clay:navigation-bar
	items="<%= siteMembershipsDisplayContext.getSiteRolesNavigationItems() %>"
/>

<clay:management-toolbar
	clearResultsURL="<%= userGroupRolesDisplayContext.getClearResultsURL() %>"
	componentId="userGroupGroupRoleRoleManagementToolbar"
	disabled="<%= userGroupRolesDisplayContext.isDisabledManagementBar() %>"
	filterItems="<%= userGroupRolesDisplayContext.getFilterDropdownItems() %>"
	searchActionURL="<%= userGroupRolesDisplayContext.getSearchActionURL() %>"
	searchContainerId="userGroupGroupRoleRole"
	searchFormName="searchFm"
	showSearch="<%= userGroupRolesDisplayContext.isShowSearch() %>"
	sortingOrder="<%= userGroupRolesDisplayContext.getOrderByType() %>"
	sortingURL="<%= userGroupRolesDisplayContext.getSortingURL() %>"
	totalItems="<%= userGroupRolesDisplayContext.getTotalItems() %>"
	viewTypes="<%= userGroupRolesDisplayContext.getViewTypeItems() %>"
/>

<aui:form cssClass="container-fluid-1280 portlet-site-memberships-assign-site-roles" name="fm">
	<liferay-ui:search-container
		id="userGroupGroupRoleRole"
		searchContainer="<%= userGroupRolesDisplayContext.getRoleSearchSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Role"
			keyProperty="roleId"
			modelVar="role"
		>

			<%
			String displayStyle = userGroupRolesDisplayContext.getDisplayStyle();
			%>

			<%@ include file="/role_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= userGroupRolesDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />userGroupGroupRoleRole');

	searchContainer.on(
		'rowToggled',
		function(event) {
			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(userGroupRolesDisplayContext.getEventName()) %>',
				{
					data: event.elements.allSelectedElements.getDOMNodes()
				}
			);
		}
	);
</aui:script>