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

String moduleGroup = ParamUtil.getString(request, "moduleGroup");

ModuleGroupDisplay moduleGroupDisplay = null;

if (Validator.isNotNull(moduleGroup)) {
	moduleGroupDisplay = ModuleGroupDisplayFactoryUtil.getModuleGroupDisplay(appDisplay, moduleGroup);
}

String symbolicName = ParamUtil.getString(request, "symbolicName");
String version = ParamUtil.getString(request, "version");

Bundle bundle = BundleManagerUtil.getBundle(symbolicName, version);

BundleContext bundleContext = bundle.getBundleContext();

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_module_plugins.jsp");
portletURL.setParameter("app", app);
portletURL.setParameter("moduleGroup", moduleGroup);
portletURL.setParameter("symbolicName", bundle.getSymbolicName());
portletURL.setParameter("version", bundle.getVersion().toString());

MarketplaceAppManagerUtil.addPortletBreadcrumbEntry(appDisplay, moduleGroupDisplay, bundle, request, renderResponse);
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewURL">
			<portlet:param name="mvcPath" value="/view_module_plugins.jsp" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewURL %>"
			label="portlets"
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
			Collection<ServiceReference<Portlet>> serviceReferences = bundleContext.getServiceReferences(Portlet.class, "(service.bundleid=" + bundle.getBundleId() + ")");

			List<ServiceReference<Portlet>> serviceReferenceList = new ArrayList<ServiceReference<Portlet>>(serviceReferences);

			int end = searchContainer.getEnd();

			if (end > serviceReferences.size()) {
				end = serviceReferences.size();
			}

			searchContainer.setResults(serviceReferenceList.subList(searchContainer.getStart(), end));

			searchContainer.setTotal(serviceReferences.size());
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="ServiceReference<Portlet>"
			modelVar="serviceReference"
		>
			<liferay-ui:search-container-column-text>
				<liferay-util:include page="/icon.jsp" servletContext="<%= application %>">
					<liferay-util:param name="iconURL" value='<%= PortalUtil.getPathContext(request) + "/images/icons.svg#portlets" %>' />
				</liferay-util:include>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text colspan="<%= 2 %>">
				<h5>
					<%= serviceReference.getProperty("javax.portlet.display-name") %>
				</h5>

				<h6 class="text-default">

					<%
					String portletDescription = (String)serviceReference.getProperty("javax.portlet.name");

					if (Validator.isNotNull(serviceReference.getProperty("javax.portlet.description"))) {
						portletDescription += " - " + serviceReference.getProperty("javax.portlet.description");
					}
					%>

					<%= portletDescription %>
				</h6>

				<div class="additional-info text-default">
					<div class="additional-info-item">
						<strong>
							<liferay-ui:message key="version" />:
						</strong>

						<%= version %>
					</div>
				</div>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
	</liferay-ui:search-container>
</div>