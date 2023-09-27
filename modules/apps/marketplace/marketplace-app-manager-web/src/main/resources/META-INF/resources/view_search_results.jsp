<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = PortalUtil.escapeRedirect(ParamUtil.getString(request, "redirect"));

if (Validator.isNull(redirect)) {
	redirect = String.valueOf(renderResponse.createRenderURL());
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "app-manager"), String.valueOf(renderResponse.createRenderURL()));
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "search-results"), null);
%>

<portlet:renderURL var="viewURL" />

<%
AppManagerSearchResultsManagementToolbarDisplayContext appManagerSearchResultsManagementToolbarDisplayContext = new AppManagerSearchResultsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

SearchContainer<Object> searchContainer = appManagerSearchResultsManagementToolbarDisplayContext.getSearchContainer();
%>

<clay:management-toolbar
	clearResultsURL="<%= redirect %>"
	itemsTotal="<%= searchContainer.getTotal() %>"
	searchActionURL="<%= appManagerSearchResultsManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="appDisplays"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= appManagerSearchResultsManagementToolbarDisplayContext.getSortingURL() %>"
/>

<clay:container-fluid>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, false, false, false, false, true) %>"
	/>

	<liferay-ui:search-container
		id="appDisplays"
		searchContainer="<%= searchContainer %>"
		var="appDisplaySearch"
	>
		<liferay-ui:search-container-row
			className="Object"
			modelVar="result"
		>
			<c:choose>
				<c:when test="<%= result instanceof AppDisplay %>">

					<%
					AppDisplay appDisplay = (AppDisplay)result;
					%>

					<%@ include file="/app_display_columns.jspf" %>
				</c:when>
				<c:when test="<%= result instanceof Bundle %>">

					<%
					Bundle bundle = (Bundle)result;

					String app = StringPool.BLANK;
					%>

					<%@ include file="/bundle_columns.jspf" %>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
			resultRowSplitter="<%= new MarketplaceAppManagerResultRowSplitter() %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>