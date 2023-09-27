<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String app = ParamUtil.getString(request, "app");

ViewModulesManagementToolbarDisplayContext viewModulesManagementToolbarDisplayContext = new ViewModulesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

AppDisplay appDisplay = viewModulesManagementToolbarDisplayContext.getAppDisplay();

SearchContainer<Object> searchContainer = viewModulesManagementToolbarDisplayContext.getSearchContainer();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(
	PortletURLBuilder.createRenderURL(
		renderResponse
	).setMVCPath(
		"/view.jsp"
	).buildString());

renderResponse.setTitle(appDisplay.getDisplayTitle());

MarketplaceAppManagerUtil.addPortletBreadcrumbEntry(appDisplay, request, renderResponse);
%>

<portlet:renderURL var="viewURL">
	<portlet:param name="mvcPath" value="/view_modules.jsp" />
	<portlet:param name="app" value="<%= app %>" />
</portlet:renderURL>

<clay:management-toolbar
	filterDropdownItems="<%= viewModulesManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	orderDropdownItems="<%= viewModulesManagementToolbarDisplayContext.getOrderDropdownItems() %>"
	searchActionURL="<%= viewModulesManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="bundles"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= viewModulesManagementToolbarDisplayContext.getSortingURL() %>"
/>

<clay:container-fluid>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, false, false, false, false, true) %>"
	/>

	<liferay-ui:search-container
		id="bundles"
		searchContainer="<%= searchContainer %>"
		var="bundleSearch"
	>
		<liferay-ui:search-container-row
			className="org.osgi.framework.Bundle"
			modelVar="bundle"
		>
			<%@ include file="/bundle_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>