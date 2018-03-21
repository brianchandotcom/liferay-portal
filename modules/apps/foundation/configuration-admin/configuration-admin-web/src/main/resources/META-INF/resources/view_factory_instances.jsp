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
String redirect = ParamUtil.getString(request, "redirect");

PortletURL portletURL = renderResponse.createRenderURL();

if (Validator.isNull(redirect)) {
	redirect = portletURL.toString();
}

ConfigurationModelIterator configurationModelIterator = (ConfigurationModelIterator)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_MODEL_ITERATOR);
ConfigurationModel configurationModel = (ConfigurationModel)request.getAttribute(ConfigurationAdminWebKeys.FACTORY_CONFIGURATION_MODEL);

ResourceBundleLoaderProvider resourceBundleLoaderProvider = (ResourceBundleLoaderProvider)request.getAttribute(ConfigurationAdminWebKeys.RESOURCE_BUNDLE_LOADER_PROVIDER);

ResourceBundleLoader resourceBundleLoader = resourceBundleLoaderProvider.getResourceBundleLoader(configurationModel.getBundleSymbolicName());

ResourceBundle componentResourceBundle = resourceBundleLoader.loadResourceBundle(PortalUtil.getLocale(request));

String factoryConfigurationModelName = (componentResourceBundle != null) ? LanguageUtil.get(componentResourceBundle, configurationModel.getName()) : configurationModel.getName();

String categoryDisplayName = LanguageUtil.get(request, "category." + configurationModel.getCategory());

PortletURL homeURL = renderResponse.createRenderURL();

PortalUtil.addPortletBreadcrumbEntry(request, portletDisplay.getPortletDisplayName(), homeURL.toString());

PortletURL viewCategoryURL = renderResponse.createRenderURL();

viewCategoryURL.setParameter("mvcRenderCommandName", "/view_category");
viewCategoryURL.setParameter("configurationCategory", configurationModel.getCategory());

PortalUtil.addPortletBreadcrumbEntry(request, categoryDisplayName, viewCategoryURL.toString());
PortalUtil.addPortletBreadcrumbEntry(request, factoryConfigurationModelName, null);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(portletURL.toString());

renderResponse.setTitle(LanguageUtil.get(request, "category." + configurationModel.getCategory()));
%>

<div class="container-fluid container-fluid-max-xl">
	<div class="col-12">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>
	</div>
</div>

<div class="container-fluid container-fluid-max-xl">
	<div class="row">
		<div class="col-md-3">
			<liferay-util:include page="/configuration_category_menu.jsp" servletContext="<%= application %>" />
		</div>

		<div class="col-md-9">
			<div class="sheet sheet-lg">
				<div class="autofit-row">
					<div class="autofit-col">
						<h2><%= factoryConfigurationModelName %></h2>
					</div>

					<c:if test="<%= configurationModelIterator.getTotal() > 0 %>">
						<div class="autofit-col">
							<liferay-ui:icon-menu
								cssClass="float-right"
								direction="right"
								markupView="lexicon"
								showWhenSingleIcon="<%= true %>"
							>
								<portlet:resourceURL id="export" var="exportEntriesURL">
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
								</portlet:resourceURL>

								<liferay-ui:icon
									message="export-entries"
									method="get"
									url="<%= exportEntriesURL %>"
								/>
							</liferay-ui:icon-menu>
						</div>
					</c:if>
				</div>

				<h3 class="autofit-row sheet-subtitle">
					<span class="autofit-col autofit-col-expand">
						<span class="heading-text">
							<liferay-ui:message key="configuration-entries" />
						</span>
					</span>
					<span class="autofit-col">
						<span class="heading-end">
							<portlet:renderURL var="createFactoryConfigURL">
								<portlet:param name="mvcRenderCommandName" value="/edit_configuration" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="factoryPid" value="<%= configurationModel.getID() %>" />
							</portlet:renderURL>

							<a class="btn btn-secondary btn-sm" href="<%= createFactoryConfigURL %>"><liferay-ui:message key="add" /></a>
						</span>
					</span>
				</h3>

				<liferay-ui:search-container
					emptyResultsMessage='<%= LanguageUtil.format(request, "no-entries-for-x-have-been-added-yet", factoryConfigurationModelName) %>'
					total="<%= configurationModelIterator.getTotal() %>"
				>
					<liferay-ui:search-container-results
						results="<%= configurationModelIterator.getResults(searchContainer.getStart(), searchContainer.getEnd()) %>"
					/>

					<liferay-ui:search-container-row
						className="com.liferay.configuration.admin.web.internal.model.ConfigurationModel"
						keyProperty="ID"
						modelVar="curConfigurationModel"
					>
						<portlet:renderURL var="editFactoryInstanceURL">
							<portlet:param name="mvcRenderCommandName" value="/edit_configuration" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="factoryPid" value="<%= curConfigurationModel.getFactoryPid() %>" />
							<portlet:param name="pid" value="<%= curConfigurationModel.getID() %>" />
						</portlet:renderURL>

						<%
						String columnLabel = "entry";

						String labelAttribute = curConfigurationModel.getLabelAttribute();

						if (labelAttribute != null) {
							AttributeDefinition attributeDefinition = curConfigurationModel.getExtendedAttributeDefinition(labelAttribute);

							if (attributeDefinition != null) {
								columnLabel = attributeDefinition.getName();
							}
						}
						%>

						<liferay-ui:search-container-column-text
							name="<%= columnLabel %>"
						>
							<aui:a href="<%= editFactoryInstanceURL %>"><strong><%= curConfigurationModel.getLabel() %></strong></aui:a>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							align="right"
							cssClass="entry-action"
							name=""
						>
							<liferay-ui:icon-menu
								direction="down"
								markupView="lexicon"
								showWhenSingleIcon="<%= true %>"
							>
								<liferay-ui:icon
									message="edit"
									method="post"
									url="<%= editFactoryInstanceURL %>"
								/>

								<c:if test="<%= curConfigurationModel.hasConfiguration() %>">
									<portlet:actionURL name="deleteConfiguration" var="deleteConfigActionURL">
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="factoryPid" value="<%= curConfigurationModel.getFactoryPid() %>" />
										<portlet:param name="pid" value="<%= curConfigurationModel.getID() %>" />
									</portlet:actionURL>

									<liferay-ui:icon
										message="delete"
										method="post"
										url="<%= deleteConfigActionURL %>"
									/>

									<portlet:resourceURL id="export" var="exportURL">
										<portlet:param name="factoryPid" value="<%= curConfigurationModel.getFactoryPid() %>" />
										<portlet:param name="pid" value="<%= curConfigurationModel.getID() %>" />
									</portlet:resourceURL>

									<liferay-ui:icon
										message="export"
										method="get"
										url="<%= exportURL %>"
									/>
								</c:if>
							</liferay-ui:icon-menu>
						</liferay-ui:search-container-column-text>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator
						markupView="lexicon"
					/>
				</liferay-ui:search-container>
			</div>
		</div>
	</div>
</div>