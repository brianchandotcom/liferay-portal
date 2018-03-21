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

ConfigurationModel configurationModel = (ConfigurationModel)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_MODEL);
String ddmFormHTML = (String)request.getAttribute(ConfigurationAdminWebKeys.CONFIGURATION_MODEL_FORM_HTML);
ResourceBundleLoaderProvider resourceBundleLoaderProvider = (ResourceBundleLoaderProvider)request.getAttribute(ConfigurationAdminWebKeys.RESOURCE_BUNDLE_LOADER_PROVIDER);

ResourceBundleLoader resourceBundleLoader = resourceBundleLoaderProvider.getResourceBundleLoader(configurationModel.getBundleSymbolicName());

ResourceBundle componentResourceBundle = resourceBundleLoader.loadResourceBundle(PortalUtil.getLocale(request));

String bindRedirectURL = currentURL;

if (configurationModel.isFactory()) {
	PortletURL viewFactoryInstancesURL = renderResponse.createRenderURL();

	viewFactoryInstancesURL.setParameter("factoryPid", configurationModel.getFactoryPid());
	viewFactoryInstancesURL.setParameter("mvcRenderCommandName", "/view_factory_instances");

	bindRedirectURL = viewFactoryInstancesURL.toString();
}

String categoryDisplayName = LanguageUtil.get(request, "category." + configurationModel.getCategory());

PortletURL homeURL = renderResponse.createRenderURL();

PortalUtil.addPortletBreadcrumbEntry(request, portletDisplay.getPortletDisplayName(), homeURL.toString());

PortletURL viewCategoryURL = renderResponse.createRenderURL();

viewCategoryURL.setParameter("mvcRenderCommandName", "/view_category");
viewCategoryURL.setParameter("configurationCategory", configurationModel.getCategory());

PortalUtil.addPortletBreadcrumbEntry(request, categoryDisplayName, viewCategoryURL.toString());

String configurationModelName = (componentResourceBundle != null) ? LanguageUtil.get(componentResourceBundle, configurationModel.getName()) : configurationModel.getName();

if (configurationModel.isFactory() && !configurationModel.isCompanyFactory()) {
	PortletURL viewFactoryInstances = renderResponse.createRenderURL();

	viewFactoryInstances.setParameter("mvcRenderCommandName", "/view_factory_instances");
	viewFactoryInstances.setParameter("factoryPid", configurationModel.getFactoryPid());

	PortalUtil.addPortletBreadcrumbEntry(request, configurationModelName, viewFactoryInstances.toString());

	if (configurationModel.hasConfiguration()) {
		PortalUtil.addPortletBreadcrumbEntry(request, configurationModel.getLabel(), null);
	}
	else {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add"), null);
	}
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, configurationModelName, null);
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(portletURL.toString());

renderResponse.setTitle(categoryDisplayName);
%>

<liferay-ui:error exception="<%= ConfigurationModelListenerException.class %>">

	<%
	ConfigurationModelListenerException cmle = (ConfigurationModelListenerException)errorException;
	%>

	<liferay-ui:message key="<%= cmle.causeMessage %>" localizeKey="<%= false %>" />
</liferay-ui:error>

<portlet:actionURL name="bindConfiguration" var="bindConfigurationActionURL" />
<portlet:actionURL name="deleteConfiguration" var="deleteConfigurationActionURL" />

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
				<aui:form action="<%= bindConfigurationActionURL %>" method="post" name="fm">
					<aui:input name="redirect" type="hidden" value="<%= bindRedirectURL %>" />
					<aui:input name="factoryPid" type="hidden" value="<%= configurationModel.getFactoryPid() %>" />
					<aui:input name="pid" type="hidden" value="<%= configurationModel.getID() %>" />

					<h2>
						<%= (componentResourceBundle != null) ? LanguageUtil.get(componentResourceBundle, configurationModel.getName()) : configurationModel.getName() %>

						<c:if test="<%= configurationModel.hasConfiguration() %>">
							<liferay-ui:icon-menu
								cssClass="float-right"
								direction="right"
								markupView="lexicon"
								showWhenSingleIcon="<%= true %>"
							>
								<c:choose>
									<c:when test="<%= configurationModel.isFactory() && !configurationModel.isCompanyFactory() %>">
										<portlet:actionURL name="deleteConfiguration" var="deleteConfigActionURL">
											<portlet:param name="redirect" value="<%= currentURL %>" />
											<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
											<portlet:param name="pid" value="<%= configurationModel.getID() %>" />
										</portlet:actionURL>

										<liferay-ui:icon
											message="delete"
											method="post"
											url="<%= deleteConfigActionURL %>"
										/>
									</c:when>
									<c:otherwise>
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
									</c:otherwise>
								</c:choose>

								<portlet:resourceURL id="export" var="exportURL">
									<portlet:param name="factoryPid" value="<%= configurationModel.getFactoryPid() %>" />
									<portlet:param name="pid" value="<%= configurationModel.getID() %>" />
								</portlet:resourceURL>

								<liferay-ui:icon
									message="export"
									method="get"
									url="<%= exportURL %>"
								/>
							</liferay-ui:icon-menu>
						</c:if>
					</h2>

					<c:if test="<%= !configurationModel.hasConfiguration() %>">
						<aui:alert closeable="<%= false %>" id="errorAlert" type="info">
							<liferay-ui:message key="this-configuration-was-not-saved-yet" />
						</aui:alert>
					</c:if>

					<%
					String configurationModelDescription = (componentResourceBundle != null) ? LanguageUtil.get(componentResourceBundle, configurationModel.getDescription()) : configurationModel.getDescription();
					%>

					<c:if test="<%= !Validator.isBlank(configurationModelDescription) %>">
						<p class="text-default">
							<strong><%= configurationModelDescription %></strong>
						</p>
					</c:if>

					<%= ddmFormHTML %>

					<aui:button-row>
						<c:choose>
							<c:when test="<%= configurationModel.hasConfiguration() %>">
								<aui:button name="update" type="submit" value="update" />
							</c:when>
							<c:otherwise>
								<aui:button name="save" type="submit" value="save" />
							</c:otherwise>
						</c:choose>

						<aui:button href="<%= redirect %>" name="cancel" type="cancel" />
					</aui:button-row>
				</aui:form>
			</div>
		</div>
	</div>
</div>