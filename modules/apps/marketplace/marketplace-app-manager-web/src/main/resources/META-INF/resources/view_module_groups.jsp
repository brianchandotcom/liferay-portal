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
ViewModuleGroupsManagementToolbarDisplayContext
	viewModuleGroupsManagementToolbarDisplayContext = new ViewModuleGroupsManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request);

AppDisplay appDisplay = viewModuleGroupsManagementToolbarDisplayContext.getAppDisplay();

SearchContainer searchContainer = viewModuleGroupsManagementToolbarDisplayContext.getSearchContainer();

PortletURL backURL = renderResponse.createRenderURL();

backURL.setParameter("mvcPath", "/view.jsp");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle(appDisplay.getTitle());

MarketplaceAppManagerUtil.addPortletBreadcrumbEntry(appDisplay, request, renderResponse);
%>

<portlet:renderURL var="viewURL">
	<portlet:param name="mvcPath" value="/view_module_groups.jsp" />
</portlet:renderURL>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= appManagerDisplayContext.getNavigationItems(viewURL, "apps") %>'
/>

<clay:management-toolbar
	filterDropdownItems="<%= viewModuleGroupsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	searchActionURL="<%= viewModuleGroupsManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="moduleGroupDisplays"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= viewModuleGroupsManagementToolbarDisplayContext.getSortingURL() %>"
/>

<div class="container-fluid-1280">
	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-ui:search-container
		id="moduleGroupDisplays"
		searchContainer="<%= searchContainer %>"
		var="moduleGroupDisplaySearch"
	>
		<liferay-ui:search-container-row
			className="com.liferay.marketplace.app.manager.web.internal.util.ModuleGroupDisplay"
			modelVar="moduleGroupDisplay"
		>
			<%@ include file="/module_group_display_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>