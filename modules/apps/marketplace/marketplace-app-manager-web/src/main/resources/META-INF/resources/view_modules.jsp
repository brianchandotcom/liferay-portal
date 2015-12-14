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

List<Bundle> allBundles = BundleManagerUtil.getBundles();

if (Validator.isNumber(app)) {
	appDisplay = AppDisplayFactoryUtil.getAppDisplay(allBundles, Long.parseLong(app));
}

if (appDisplay == null) {
	appDisplay = AppDisplayFactoryUtil.getAppDisplay(allBundles, app);
}

String moduleGroup = ParamUtil.getString(request, "moduleGroup");

ModuleGroupDisplay moduleGroupDisplay = null;

if (Validator.isNotNull(moduleGroup)) {
	moduleGroupDisplay = ModuleGroupDisplayFactoryUtil.getModuleGroupDisplay(appDisplay, moduleGroup);
}

String state = ParamUtil.getString(request, "state", "all-statuses");

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_modules.jsp");
portletURL.setParameter("app", app);
portletURL.setParameter("moduleGroup", moduleGroup);
portletURL.setParameter("state", state);

MarketplaceAppManagerUtil.addPortletBreadcrumbEntry(appDisplay, moduleGroupDisplay, request, renderResponse);
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewURL">
			<portlet:param name="mvcPath" value="/view_modules.jsp" />
			<portlet:param name="app" value="<%= app %>" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewURL %>"
			label="modules"
			selected="<%= true %>"
		/>
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar
	searchContainerId="appDisplays"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="descriptive"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all-statuses", BundleStateConstants.ACTIVE_LABEL, BundleStateConstants.RESOLVED_LABEL, BundleStateConstants.INSTALLED_LABEL} %>'
			navigationParam="state"
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="title"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"title"} %>'
			portletURL="<%= portletURL %>"
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
		id="bundles"
		iteratorURL="<%= portletURL %>"
	>
		<liferay-ui:search-container-results>

			<%
			List<Bundle> bundles = null;

			if (moduleGroupDisplay != null) {
				bundles = moduleGroupDisplay.getBundles();
			}
			else {
				bundles = appDisplay.getBundles();
			}

			BundleUtil.filterBundles(bundles, BundleStateConstants.getState(state));

			bundles = ListUtil.sort(bundles, new BundleComparator(orderByType));

			int end = searchContainer.getEnd();

			if (end > bundles.size()) {
				end = bundles.size();
			}

			searchContainer.setResults(bundles.subList(searchContainer.getStart(), end));

			searchContainer.setTotal(bundles.size());
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="org.osgi.framework.Bundle"
			modelVar="bundle"
		>

			<%
			Dictionary<String, String> headers = bundle.getHeaders();

			String bundleName = GetterUtil.getString(headers.get(BundleConstants.BUNDLE_NAME));
			String bundleDescription = GetterUtil.getString(headers.get(BundleConstants.BUNDLE_DESCRIPTION));
			%>

			<liferay-ui:search-container-column-text colspan="<%= 2 %>">
				<h5>
					<%= bundleName %>
				</h5>

				<h6 class="text-default">
					<%= bundleDescription %>
				</h6>

				<h6 class="text-default">
					<%= bundle.getSymbolicName() %>
				</h6>

				<div class="additional-info text-default">
					<div class="additional-info-item">
						<strong>
							<liferay-ui:message key="version" />:
						</strong>

						<%= String.valueOf(bundle.getVersion()) %>
					</div>

					<div class="additional-info-item">
						<strong>
							<liferay-ui:message key="status" />:
						</strong>

						<liferay-ui:message key="<%= BundleStateConstants.getLabel(bundle.getState()) %>" />
					</div>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				path="/bundle_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
	</liferay-ui:search-container>
</div>