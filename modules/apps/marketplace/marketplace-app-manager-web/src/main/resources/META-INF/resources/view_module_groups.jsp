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
String app = ParamUtil.getString(request, "app");

AppDisplay appDisplay = null;

List<Bundle> bundles = BundleManagerUtil.getBundles();

if (Validator.isNumber(app)) {
	appDisplay = AppDisplayFactoryUtil.getAppDisplay(bundles, Long.parseLong(app));
}

if (appDisplay == null) {
	appDisplay = AppDisplayFactoryUtil.getAppDisplay(bundles, app);
}

String state = ParamUtil.getString(request, "state", "all-statuses");

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_module_groups.jsp");
portletURL.setParameter("app", app);
portletURL.setParameter("state", state);
portletURL.setParameter("orderByType", orderByType);

portletDisplay.setShowBackIcon(true);

PortletURL backURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");

portletDisplay.setURLBack(backURL.toString());

renderResponse.setTitle(appDisplay.getTitle());

MarketplaceAppManagerUtil.addPortletBreadcrumbEntry(appDisplay, request, renderResponse);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewURL">
			<portlet:param name="mvcPath" value="/view_module_groups.jsp" />
		</portlet:renderURL>

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
		id="moduleGroupDisplays"
		iteratorURL="<%= portletURL %>"
	>
		<liferay-ui:search-container-results>

			<%
			List<ModuleGroupDisplay> moduleGroupDisplays = ModuleGroupDisplayFactoryUtil.getModuleGroupDisplays(appDisplay, BundleStateConstants.getState(state));

			moduleGroupDisplays = ListUtil.sort(moduleGroupDisplays, new ModuleGroupDisplayComparator(orderByType));

			int end = searchContainer.getEnd();

			if (end > moduleGroupDisplays.size()) {
				end = moduleGroupDisplays.size();
			}

			searchContainer.setResults(moduleGroupDisplays.subList(searchContainer.getStart(), end));

			searchContainer.setTotal(moduleGroupDisplays.size());
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.marketplace.app.manager.web.util.ModuleGroupDisplay"
			modelVar="moduleGroupDisplay"
		>
			<%@ include file="/module_group_display_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
	</liferay-ui:search-container>
</div>