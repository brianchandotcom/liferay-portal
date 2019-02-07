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
ViewAppsManagerManagementToolbarDisplayContext viewAppsManagerManagementToolbarDisplayContext = new ViewAppsManagerManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "app-manager"), null);
%>

<portlet:renderURL var="viewURL" />

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= appManagerDisplayContext.getNavigationItems(viewURL, "apps") %>'
/>

<clay:management-toolbar
	clearResultsURL="<%= viewAppsManagerManagementToolbarDisplayContext.getClearResultsURL() %>"
	filterDropdownItems="<%= viewAppsManagerManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	filterLabelItems="<%= viewAppsManagerManagementToolbarDisplayContext.getFilterLabelItems() %>"
	itemsTotal="<%= viewAppsManagerManagementToolbarDisplayContext.getItemsTotal() %>"
	searchActionURL="<%= viewAppsManagerManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="appDisplays"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
	sortingOrder="<%= viewAppsManagerManagementToolbarDisplayContext.getSortingOrder() %>"
	sortingURL="<%= viewAppsManagerManagementToolbarDisplayContext.getSortingURL() %>"
/>

<div class="container-fluid container-fluid-max-xl">
	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-ui:search-container
		id="appDisplays"
		searchContainer="<%= viewAppsManagerManagementToolbarDisplayContext.getSearchContainer() %>"
		var="appDisplaySearch"
	>
		<liferay-ui:search-container-row
			className="com.liferay.marketplace.app.manager.web.internal.util.AppDisplay"
			modelVar="appDisplay"
		>
			<%@ include file="/app_display_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
			resultRowSplitter="<%= new MarketplaceAppManagerResultRowSplitter() %>"
		/>
	</liferay-ui:search-container>
</div>