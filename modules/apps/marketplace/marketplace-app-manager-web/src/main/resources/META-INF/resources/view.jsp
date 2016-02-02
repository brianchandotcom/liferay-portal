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
String category = ParamUtil.getString(request, "category", "all-categories");
String state = ParamUtil.getString(request, "state", "all-statuses");

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

List<App> apps = AppLocalServiceUtil.getApps(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
List<Bundle> bundles = BundleManagerUtil.getBundles();

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("category", category);
portletURL.setParameter("state", state);
portletURL.setParameter("orderByType", orderByType);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "app-manager"), null);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewURL" />

		<aui:nav-item
			href="<%= viewURL %>"
			label="apps"
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
		<liferay-frontend:management-bar-navigation
			navigationKeys="<%= MarketplaceAppManagerUtil.getCategories(apps, bundles) %>"
			navigationParam="category"
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>

		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all-statuses", BundleStateConstants.ACTIVE_LABEL, BundleStateConstants.RESOLVED_LABEL, BundleStateConstants.INSTALLED_LABEL} %>'
			navigationParam="state"
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>

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
		emptyResultsMessage="no-apps-were-found"
		id="appDisplays"
		iteratorURL="<%= portletURL %>"
	>
		<liferay-ui:search-container-results>

			<%
			if (category.equals("all-categories")) {
				category = StringPool.BLANK;
			}

			List<AppDisplay> appDisplays = AppDisplayFactoryUtil.getAppDisplays(bundles, category, BundleStateConstants.getState(state));

			appDisplays = ListUtil.sort(appDisplays, new AppDisplayComparator(orderByType));

			int end = searchContainer.getEnd();

			if (end > appDisplays.size()) {
				end = appDisplays.size();
			}

			searchContainer.setResults(appDisplays.subList(searchContainer.getStart(), end));

			searchContainer.setTotal(appDisplays.size());
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.marketplace.app.manager.web.util.AppDisplay"
			modelVar="appDisplay"
		>
			<%@ include file="/app_display_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" resultRowSplitter="<%= new MarketplaceAppManagerResultRowSplitter() %>" />
	</liferay-ui:search-container>
</div>