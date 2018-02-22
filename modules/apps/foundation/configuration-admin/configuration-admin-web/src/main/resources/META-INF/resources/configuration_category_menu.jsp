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
ConfigurationModel configurationModel = (ConfigurationModel)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_MODEL);

if (configurationModel == null) {
	configurationModel = (ConfigurationModel)request.getAttribute(ConfigurationAdminWebKeys.FACTORY_CONFIGURATION_MODEL);
}

ConfigurationCategoryMenuDisplay configurationCategoryMenuDisplay = (ConfigurationCategoryMenuDisplay)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_CATEGORY_MENU_DISPLAY);
ResourceBundleLoaderProvider resourceBundleLoaderProvider = (ResourceBundleLoaderProvider)request.getAttribute(ConfigurationAdminWebKeys.RESOURCE_BUNDLE_LOADER_PROVIDER);

String id = PortalUtil.generateRandomKey(request, "configuration_category_menu.jsp");

ResourceBundleLoader resourceBundleLoader = resourceBundleLoaderProvider.getResourceBundleLoader(configurationModel.getBundleSymbolicName());

ResourceBundle componentResourceBundle = resourceBundleLoader.loadResourceBundle(PortalUtil.getLocale(request));

String configurationModelName = (componentResourceBundle != null) ? LanguageUtil.get(componentResourceBundle, configurationModel.getName()) : configurationModel.getName();
%>

<nav class="menubar menubar-transparent menubar-vertical-expand-md">
	<a aria-controls="<%= id %>" aria-expanded="false" class="menubar-toggler" data-toggle="collapse" href="#<%= id %>" role="button">
		<liferay-ui:message key="<%= configurationModelName %>" />

		<aui:icon image="caret-bottom" markupView="lexicon" />
	</a>

	<div class="collapse menubar-collapse" id="<%= id %>">
		<ul class="nav nav-nested">

			<%
			for (ConfigurationScopeDisplay configurationScopeDisplay : configurationCategoryMenuDisplay.getConfigurationScopeDisplays()) {
				if (configurationScopeDisplay.isEmpty()) {
					continue;
				}

				List<ConfigurationModel> configurationModels = configurationScopeDisplay.getConfigurationModels();
			%>

				<li class="nav-item">
					<a class="nav-link text-uppercase">
						<liferay-ui:message key='<%= "scope." + configurationScopeDisplay.getScope() %>' />
					</a>

					<div>
						<ul class="nav nav-stacked">

							<%
							for (ConfigurationModel curConfigurationModel : configurationModels) {
								ResourceBundleLoader curResourceBundleLoader = resourceBundleLoaderProvider.getResourceBundleLoader(curConfigurationModel.getBundleSymbolicName());

								ResourceBundle curComponentResourceBundle = curResourceBundleLoader.loadResourceBundle(PortalUtil.getLocale(request));

								String curConfigurationModelName = (curComponentResourceBundle != null) ? LanguageUtil.get(curComponentResourceBundle, curConfigurationModel.getName()) : curConfigurationModel.getName();

								String cssClass = configurationModel.equals(curConfigurationModel) ? "active nav-link": "nav-link";
							%>

								<li class="nav-item">
									<portlet:renderURL var="editURL">
										<portlet:param name="mvcRenderCommandName" value="/edit_configuration" />
										<portlet:param name="factoryPid" value="<%= curConfigurationModel.getFactoryPid() %>" />
										<portlet:param name="pid" value="<%= curConfigurationModel.getID() %>" />
									</portlet:renderURL>

									<portlet:renderURL var="viewFactoryInstancesURL">
										<portlet:param name="mvcRenderCommandName" value="/view_factory_instances" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="factoryPid" value="<%= curConfigurationModel.getFactoryPid() %>" />
									</portlet:renderURL>

									<c:choose>
										<c:when test="<%= curConfigurationModel.isFactory() && !curConfigurationModel.isCompanyFactory() %>">
											<aui:a cssClass="<%= cssClass %>" href="<%= viewFactoryInstancesURL %>"><%= curConfigurationModelName %></aui:a>
										</c:when>
										<c:otherwise>
											<aui:a cssClass="<%= cssClass %>" href="<%= editURL %>"><%= curConfigurationModelName %></aui:a>
										</c:otherwise>
									</c:choose>
								</li>

							<%
							}
							%>

						</ul>
					</div>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</nav>