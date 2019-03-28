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
GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration = (GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration)request.getAttribute(GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.class.getName());
%>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/document_library_asset_auto_tagger_google_cloud_natural_language" />

<aui:input id="classification-endpoint-enabled" label="classification-endpoint-enabled" name='<%= PortalSettingsGCloudNaturalLanguageAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "classificationEndpointEnabled" %>' type="checkbox" value="<%= gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.classificationEndpointEnabled() %>" />

<aui:input label="confidence" name='<%= PortalSettingsGCloudNaturalLanguageAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "confidence" %>' value="<%= gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.confidence() %>" />

<aui:input id="entity-endpoint-enabled" label="entity-endpoint-enabled" name='<%= PortalSettingsGCloudNaturalLanguageAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "entityEndpointEnabled" %>' type="checkbox" value="<%= gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.entityEndpointEnabled() %>" />

<aui:input label="salience" name='<%= PortalSettingsGCloudNaturalLanguageAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "salience" %>' value="<%= gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.salience() %>" />

<aui:input helpMessage='<%= LanguageUtil.format(resourceBundle, "api-key-description", new String[] {GCloudNaturalLanguageAssetAutoTagProviderConstants.API_KEY_DOCS_URL}, false) %>' id="api-key" label="api-key" name='<%= PortalSettingsGCloudNaturalLanguageAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "apiKey" %>' value="<%= gCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration.apiKey() %>" />