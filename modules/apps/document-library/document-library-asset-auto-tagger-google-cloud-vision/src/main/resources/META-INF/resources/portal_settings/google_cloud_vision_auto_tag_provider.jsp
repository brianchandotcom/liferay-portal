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
GCloudVisionAssetAutoTagProviderCompanyConfiguration gCloudVisionAssetAutoTagProviderCompanyConfiguration = (GCloudVisionAssetAutoTagProviderCompanyConfiguration)request.getAttribute(GCloudVisionAssetAutoTagProviderCompanyConfiguration.class.getName());
%>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/document_library_asset_auto_tagger_google_cloud_vision" />

<aui:input id="enabled" label="enabled" name='<%= PortalSettingsGCloudVisionAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "enabled" %>' type="checkbox" value="<%= gCloudVisionAssetAutoTagProviderCompanyConfiguration.enabled() %>" />

<aui:input helpMessage='<%= LanguageUtil.format(resourceBundle, "api-key-description", new String[] {GCloudVisionAssetAutoTagProviderConstants.API_KEY_DOCS_URL}, false) %>' id="api-key" label="api-key" name='<%= PortalSettingsGCloudVisionAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "apiKey" %>' value="<%= gCloudVisionAssetAutoTagProviderCompanyConfiguration.apiKey() %>" />