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
String redirect = renderRequest.getParameter("redirect");

ConfigurationModelIterator configurationModelIterator = (ConfigurationModelIterator)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_MODEL_ITERATOR);
ConfigurationModelRetriever configurationModelRetriever = (ConfigurationModelRetriever)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_MODEL_RETRIEVER);
ResourceBundleLoaderProvider resourceBundleLoaderProvider = (ResourceBundleLoaderProvider)request.getAttribute(ConfigurationAdminWebKeys.RESOURCE_BUNDLE_LOADER_PROVIDER);

PortletURL portletURL = renderResponse.createRenderURL();

if (redirect == null) {
	redirect = portletURL.toString();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(request, "search-results"));
%>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<li>
			<portlet:renderURL var="redirectURL" />

			<portlet:renderURL var="searchURL">
				<portlet:param name="mvcRenderCommandName" value="/search" />
				<portlet:param name="redirect" value="<%= redirectURL %>" />
			</portlet:renderURL>

			<aui:form action="<%= searchURL %>" name="searchFm">
				<liferay-ui:input-search
					autoFocus="<%= true %>"
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid container-fluid-max-xl container-view">
	<liferay-ui:search-container
		emptyResultsMessage="no-configurations-were-found"
		iteratorURL="<%= portletURL %>"
		total="<%= configurationModelIterator.getTotal() %>"
	>
		<liferay-ui:search-container-results
			results="<%= configurationModelIterator.getResults(searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.configuration.admin.web.internal.model.ConfigurationModel"
			keyProperty="ID"
			modelVar="configurationModel"
		>
			<portlet:renderURL var="editURL">
				<portlet:param name="mvcRenderCommandName" value="/edit_configuration" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
				<portlet:param name="pid" value="<%= configurationModel.getID() %>" />
			</portlet:renderURL>

			<portlet:renderURL var="viewFactoryInstancesURL">
				<portlet:param name="mvcRenderCommandName" value="/view_factory_instances" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
			</portlet:renderURL>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
			>

				<%
				ResourceBundleLoader resourceBundleLoader = resourceBundleLoaderProvider.getResourceBundleLoader(configurationModel.getBundleSymbolicName());

				ResourceBundle componentResourceBundle = resourceBundleLoader.loadResourceBundle(PortalUtil.getLocale(request));

				String configurationModelName = (componentResourceBundle != null) ? LanguageUtil.get(componentResourceBundle, configurationModel.getName()) : configurationModel.getName();
				%>

				<c:choose>
					<c:when test="<%= configurationModel.isFactory() && !configurationModel.isCompanyFactory() %>">
						<aui:a href="<%= viewFactoryInstancesURL %>"><strong><%= configurationModelName %></strong></aui:a>
					</c:when>
					<c:otherwise>
						<aui:a href="<%= editURL %>"><strong><%= configurationModelName %></strong></aui:a>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="category"
			>

				<%
				ConfigurationCategory configurationCategory = configurationModelRetriever.getConfigurationCategory(configurationModel.getCategory());

				String categorySection = null;
				String category = null;

				if (configurationCategory != null) {
					categorySection = LanguageUtil.get(request, "category-section." + configurationCategory.getCategorySection());
					category = LanguageUtil.get(request, "category." + configurationCategory.getCategoryKey());
				}
				else {
					categorySection = LanguageUtil.get(request, "other");
					category = configurationModel.getCategory();
				}
				%>

				<liferay-ui:message key="<%= categorySection %>" /> &gt; <liferay-ui:message key="<%= category %>" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="scope"
			>
				<c:choose>
					<c:when test="<%= ExtendedObjectClassDefinition.Scope.COMPANY.equals(configurationModel.getScope()) %>">
						<liferay-ui:message key="default-settings-for-all-instances" />
					</c:when>
					<c:when test="<%= ExtendedObjectClassDefinition.Scope.GROUP.equals(configurationModel.getScope()) %>">
						<liferay-ui:message key="default-configuration-for-all-sites" />
					</c:when>
					<c:when test="<%= ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE.equals(configurationModel.getScope()) %>">
						<liferay-ui:message key="default-configuration-for-application" />
					</c:when>
					<c:when test="<%= ExtendedObjectClassDefinition.Scope.SYSTEM.equals(configurationModel.getScope()) %>">
						<liferay-ui:message key="system" />
					</c:when>
					<c:otherwise>
						-
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name=""
			>
				<liferay-ui:icon-menu
					direction="right"
					markupView="lexicon"
					showWhenSingleIcon="<%= true %>"
				>
					<c:choose>
						<c:when test="<%= configurationModel.isFactory() && !configurationModel.isCompanyFactory() %>">
							<liferay-ui:icon
								message="edit"
								method="post"
								url="<%= viewFactoryInstancesURL %>"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:icon
								message="edit"
								method="post"
								url="<%= editURL %>"
							/>

							<c:if test="<%= configurationModel.hasConfiguration() %>">
								<portlet:actionURL name="deleteConfiguration" var="deleteConfigActionURL">
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
									<portlet:param name="pid" value="<%= configurationModel.getID() %>" />
								</portlet:actionURL>

								<liferay-ui:icon
									message="reset-default-values"
									method="post"
									url="<%= deleteConfigActionURL %>"
								/>

								<portlet:resourceURL id="export" var="exportURL">
									<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
									<portlet:param name="pid" value="<%= configurationModel.getID() %>" />
								</portlet:resourceURL>

								<liferay-ui:icon
									message="export"
									method="get"
									url="<%= exportURL %>"
								/>
							</c:if>
						</c:otherwise>
					</c:choose>
				</liferay-ui:icon-menu>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>