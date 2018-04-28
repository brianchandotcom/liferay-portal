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
String searchContainerId = ParamUtil.getString(request, "searchContainerId");
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= siteAdminDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	actionItems="<%= siteAdminDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= siteAdminDisplayContext.getClearResultsURL() %>"
	componentId="siteAdminWebManagementToolbar"
	creationMenu="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_COMMUNITY) ? siteAdminDisplayContext.getCreationMenu() : null %>"
	filterItems="<%= siteAdminDisplayContext.getFilterDropdownItems() %>"
	infoPanelId="infoPanelId"
	searchActionURL="<%= siteAdminDisplayContext.getSearchActionURL() %>"
	searchContainerId="<%= searchContainerId %>"
	searchFormName="searchFm"
	showInfoButton="<%= true %>"
	sortingOrder="<%= siteAdminDisplayContext.getOrderByType() %>"
	sortingURL="<%= siteAdminDisplayContext.getSortingURL() %>"
	totalItems="<%= siteAdminDisplayContext.getTotalItems() %>"
	viewTypes="<%= siteAdminDisplayContext.getViewTypeItems() %>"
/>

<aui:script>
	window.<portlet:namespace />deleteSites = function() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
			submitForm(AUI.$(document.<portlet:namespace />fm));
		}
	}
</aui:script>