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
portletURL.setParameter("state", state);

MarketplaceAppManagerUtil.addPortletBreadcrumbEntry(appDisplay, request, renderResponse);
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewURL">
			<portlet:param name="mvcPath" value="/view_module_groups.jsp" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewURL %>"
			label="module-groups"
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

	<liferay-ui:search-container>
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
			<liferay-ui:search-container-column-text colspan="<%= 2 %>">
				<h5>
					<a href="<%= HtmlUtil.escapeHREF(moduleGroupDisplay.getDisplayURL(renderResponse)) %>">
						<%= moduleGroupDisplay.getTitle() %>
					</a>
				</h5>

				<h6 class="text-default">
					<%= moduleGroupDisplay.getDescription() %>
				</h6>

				<div class="additional-info text-default">
					<div class="additional-info-item">
						<strong>
							<liferay-ui:message key="version" />:
						</strong>

						<%= moduleGroupDisplay.getVersion() %>
					</div>

					<div class="additional-info-item">
						<strong>
							<liferay-ui:message key="status" />:
						</strong>

						<liferay-ui:message key="<%= BundleStateConstants.getLabel(moduleGroupDisplay.getState()) %>" />
					</div>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				path="/module_group_display_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
	</liferay-ui:search-container>
</div>