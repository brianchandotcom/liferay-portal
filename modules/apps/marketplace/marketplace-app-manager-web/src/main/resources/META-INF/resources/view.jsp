<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewAppsManagerManagementToolbarDisplayContext viewAppsManagerManagementToolbarDisplayContext = new ViewAppsManagerManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "app-manager"), null);
%>

<portlet:renderURL var="viewURL" />

<clay:management-toolbar
	clearResultsURL="<%= viewAppsManagerManagementToolbarDisplayContext.getClearResultsURL() %>"
	filterDropdownItems="<%= viewAppsManagerManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	filterLabelItems="<%= viewAppsManagerManagementToolbarDisplayContext.getFilterLabelItems() %>"
	itemsTotal="<%= viewAppsManagerManagementToolbarDisplayContext.getItemsTotal() %>"
	orderDropdownItems="<%= viewAppsManagerManagementToolbarDisplayContext.getOrderDropdownItems() %>"
	searchActionURL="<%= viewAppsManagerManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="appDisplays"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
	sortingOrder="<%= viewAppsManagerManagementToolbarDisplayContext.getSortingOrder() %>"
	sortingURL="<%= viewAppsManagerManagementToolbarDisplayContext.getSortingURL() %>"
/>

<clay:container-fluid>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, false, false, false, false, true) %>"
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
</clay:container-fluid>