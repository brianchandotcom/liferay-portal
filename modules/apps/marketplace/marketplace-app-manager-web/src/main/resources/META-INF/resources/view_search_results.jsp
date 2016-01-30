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
String keywords = ParamUtil.getString(request, "keywords");

String category = ParamUtil.getString(request, "category", "all-categories");
String state = ParamUtil.getString(request, "state", "all-statuses");

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

List<App> apps = AppLocalServiceUtil.getApps(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_search_results.jsp");
portletURL.setParameter("category", category);
portletURL.setParameter("state", state);
portletURL.setParameter("orderByType", orderByType);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "app-manager"), String.valueOf(renderResponse.createRenderURL()));
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "search-results"), null);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewURL" />

		<aui:nav-item
			href="<%= viewURL %>"
			label="search"
			selected="<%= true %>"
		/>
	</aui:nav>

	<aui:nav-bar-search>
		<liferay-portlet:renderURL varImpl="searchURL">
			<portlet:param name="mvcPath" value="/view_search_results.jsp" />
		</liferay-portlet:renderURL>

		<aui:form action="<%= searchURL.toString() %>" method="get" name="fm1">
			<liferay-portlet:renderURLParams varImpl="searchURL" />

			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<liferay-frontend:management-bar
	searchContainerId="appDisplays"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			selectedDisplayStyle="descriptive"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="title"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"title"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-ui:search-container
		emptyResultsMessage="no-results-were-found"
		id="appDisplays"
		iteratorURL="<%= portletURL %>"
	>
		<liferay-ui:search-container-results>

			<%
			List<Bundle> bundles = BundleManagerUtil.getBundles();

			results = MarketplaceAppManagerSearchUtil.getResults(bundles, keywords);

			results = ListUtil.sort(results, new MarketplaceAppManagerComparator(orderByType));

			int end = searchContainer.getEnd();

			if (end > results.size()) {
				end = results.size();
			}

			searchContainer.setResults(results.subList(searchContainer.getStart(), end));

			searchContainer.setTotal(results.size());
			%>

		</liferay-ui:search-container-results>

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
				<c:when test="<%= result instanceof ModuleGroupDisplay %>">

					<%
					ModuleGroupDisplay moduleGroupDisplay = (ModuleGroupDisplay)result;
					%>

					<%@ include file="/module_group_display_columns.jspf" %>
				</c:when>
				<c:when test="<%= result instanceof Bundle %>">

					<%
					Bundle bundle = (Bundle)result;

					String app = StringPool.BLANK;
					String moduleGroup = StringPool.BLANK;
					%>

					<%@ include file="/bundle_columns.jspf" %>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" resultRowSplitter="<%= new MarketplaceAppManagerResultRowSplitter() %>" />
	</liferay-ui:search-container>
</div>