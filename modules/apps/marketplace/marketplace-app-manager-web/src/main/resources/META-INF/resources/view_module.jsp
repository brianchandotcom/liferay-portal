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
String moduleGroup = ParamUtil.getString(request, "moduleGroup");

ViewModuleManagementToolbarDisplayContext
	viewModuleManagementToolbarDisplayContext = new ViewModuleManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request);

AppDisplay appDisplay = viewModuleManagementToolbarDisplayContext.getAppDisplay();
Bundle bundle = viewModuleManagementToolbarDisplayContext.getBundle();
ModuleGroupDisplay moduleGroupDisplay = viewModuleManagementToolbarDisplayContext.getModuleGroupDisplay();
String pluginType = viewModuleManagementToolbarDisplayContext.getPluginType();
SearchContainer searchContainer = viewModuleManagementToolbarDisplayContext.getSearchContainer();

PortletURL backURL = renderResponse.createRenderURL();

if (Validator.isNull(app)) {
	backURL.setParameter("mvcPath", "/view.jsp");
}
else {
	backURL.setParameter("mvcPath", "/view_modules.jsp");
	backURL.setParameter("app", app);
	backURL.setParameter("moduleGroup", moduleGroup);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL.toString());

Dictionary<String, String> headers = bundle.getHeaders();

String bundleName = GetterUtil.getString(headers.get(BundleConstants.BUNDLE_NAME));

renderResponse.setTitle(bundleName);

if (Validator.isNull(app)) {
	PortletURL viewURL = renderResponse.createRenderURL();

	viewURL.setParameter("mvcPath", "/view.jsp");

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "app-manager"), viewURL.toString());

	PortalUtil.addPortletBreadcrumbEntry(request, bundleName, null);
}
else {
	MarketplaceAppManagerUtil.addPortletBreadcrumbEntry(appDisplay, moduleGroupDisplay, bundle, request, renderResponse);
}
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= appManagerDisplayContext.getModuleNavigationItems() %>"
/>

<clay:management-toolbar
	searchActionURL="<%= viewModuleManagementToolbarDisplayContext.getSearchActionURL() %>"
	searchContainerId="plugins"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showSearch="<%= true %>"
	sortingOrder="<%= searchContainer.getOrderByType() %>"
	sortingURL="<%= viewModuleManagementToolbarDisplayContext.getSortingURL() %>"
/>

<div class="container-fluid-1280">
	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-ui:search-container
		id="plugins"
		searchContainer="<%= searchContainer %>"
		var="pluginSearch"
	>
		<liferay-ui:search-container-row
			className="org.osgi.framework.ServiceReference"
			modelVar="serviceReference"
		>
			<liferay-ui:search-container-column-text>
				<c:choose>
					<c:when test='<%= pluginType.equals("portlets") %>'>
						<liferay-util:include page="/icon.jsp" servletContext="<%= application %>">
							<liferay-util:param name="iconURL" value='<%= PortalUtil.getPathContext(request) + "/images/icons.svg#portlets" %>' />
						</liferay-util:include>
					</c:when>
					<c:otherwise>
						<liferay-util:include page="/icon.jsp" servletContext="<%= application %>">
							<liferay-util:param name="iconURL" value='<%= PortalUtil.getPathContext(request) + "/images/icons.svg#components" %>' />
						</liferay-util:include>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>

				<%
				String description = StringPool.BLANK;
				String name = StringPool.BLANK;

				if (pluginType.equals("portlets")) {
					name = MarketplaceAppManagerUtil.getSearchContainerFieldText(serviceReference.getProperty("javax.portlet.display-name"));

					String modulePortletDescription = MarketplaceAppManagerUtil.getSearchContainerFieldText(serviceReference.getProperty("javax.portlet.description"));
					String modulePortletName = MarketplaceAppManagerUtil.getSearchContainerFieldText(serviceReference.getProperty("javax.portlet.name"));

					if (Validator.isNotNull(modulePortletDescription)) {
						description = modulePortletName + " - " + modulePortletDescription;
					}
					else {
						description = modulePortletName;
					}
				}
				else {
					name = MarketplaceAppManagerUtil.getSearchContainerFieldText(serviceReference.getProperty("component.name"));
				}
				%>

				<h5>
					<%= name %>
				</h5>

				<h6 class="text-default">
					<%= description %>
				</h6>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>