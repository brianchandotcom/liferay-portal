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

String pluginType = ParamUtil.getString(request, "pluginType", "components");

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_module.jsp");
portletURL.setParameter("app", app);
portletURL.setParameter("moduleGroup", moduleGroup);
portletURL.setParameter("symbolicName", bundle.getSymbolicName());
portletURL.setParameter("version", String.valueOf(bundle.getVersion()));
portletURL.setParameter("pluginType", pluginType);
portletURL.setParameter("orderByType", orderByType);

portletDisplay.setShowBackIcon(true);

PortletURL backURL = renderResponse.createRenderURL();

if (Validator.isNull(app)) {
	backURL.setParameter("mvcPath", "/view.jsp");
}
else {
	backURL.setParameter("mvcPath", "/view_modules.jsp");
	backURL.setParameter("app", app);
	backURL.setParameter("moduleGroup", moduleGroup);
}

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

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewModuleComponentsURL">
			<portlet:param name="mvcPath" value="/view_module.jsp" />
			<portlet:param name="app" value="<%= app %>" />
			<portlet:param name="moduleGroup" value="<%= moduleGroup %>" />
			<portlet:param name="symbolicName" value="<%= bundle.getSymbolicName() %>" />
			<portlet:param name="version" value="<%= bundle.getVersion().toString() %>" />
			<portlet:param name="pluginType" value="components" />
			<portlet:param name="orderByType" value="<%= orderByType %>" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewModuleComponentsURL %>"
			label="components"
			selected='<%= pluginType.equals("components") %>'
		/>

		<portlet:renderURL var="viewModulePortletsURL">
			<portlet:param name="mvcPath" value="/view_module.jsp" />
			<portlet:param name="app" value="<%= app %>" />
			<portlet:param name="moduleGroup" value="<%= moduleGroup %>" />
			<portlet:param name="symbolicName" value="<%= bundle.getSymbolicName() %>" />
			<portlet:param name="version" value="<%= bundle.getVersion().toString() %>" />
			<portlet:param name="pluginType" value="portlets" />
			<portlet:param name="orderByType" value="<%= orderByType %>" />
		</portlet:renderURL>

		<aui:nav-item
			href="<%= viewModulePortletsURL %>"
			label="portlets"
			selected='<%= pluginType.equals("portlets") %>'
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
	searchContainerId="components"
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

	<%
	String emptyResultsMessage = "no-portlets-were-found";

	if (pluginType.equals("components")) {
		emptyResultsMessage = "no-components-were-found";
	}
	%>

	<liferay-ui:search-container
		emptyResultsMessage="<%= emptyResultsMessage %>"
		iteratorURL="<%= portletURL %>"
	>
		<liferay-ui:search-container-results>

			<%
			BundleContext bundleContext = bundle.getBundleContext();

			List<ServiceReference<?>> serviceReferences = Collections.<ServiceReference<?>>emptyList();

			if (pluginType.equals("portlets")) {
				Collection<ServiceReference<Portlet>> serviceReferenceCollection = bundleContext.getServiceReferences(Portlet.class, "(service.bundleid=" + bundle.getBundleId() + ")");

				serviceReferences = new ArrayList<ServiceReference<?>>(serviceReferenceCollection);

				serviceReferences = ListUtil.sort(serviceReferences, new ModuleServiceReferenceComparator("javax.portlet.display-name", orderByType));
			}
			else {
				ServiceReference<?>[] serviceReferenceArray = (ServiceReference<?>[])bundleContext.getServiceReferences((String)null, "(&(service.bundleid=" + bundle.getBundleId() + ")(component.id=*))");

				serviceReferences = ListUtil.toList(serviceReferenceArray);

				serviceReferences = ListUtil.sort(serviceReferences, new ModuleServiceReferenceComparator("component.name", orderByType));
			}

			int end = searchContainer.getEnd();

			if (end > serviceReferences.size()) {
				end = serviceReferences.size();
			}

			searchContainer.setResults(serviceReferences.subList(searchContainer.getStart(), end));

			searchContainer.setTotal(serviceReferences.size());
			%>

		</liferay-ui:search-container-results>

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

			<liferay-ui:search-container-column-text colspan="<%= 2 %>">

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

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
	</liferay-ui:search-container>
</div>