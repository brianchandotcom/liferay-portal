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
OpenNPLDocumentAssetAutoTagProviderCompanyConfiguration openNPLDocumentAssetAutoTagProviderCompanyConfiguration = (OpenNPLDocumentAssetAutoTagProviderCompanyConfiguration)request.getAttribute(OpenNPLDocumentAssetAutoTagProviderCompanyConfiguration.class.getName());
%>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/document_library_asset_auto_tagger_opennlp" />

<aui:input label="enable-opennlp-image-auto-tagging-on-this-instance" name='<%= PortalSettingsOpenNLPDocumentAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "enabled" %>' type="checkbox" value="<%= openNPLDocumentAssetAutoTagProviderCompanyConfiguration.enabled() %>" />

<aui:input label="confidence-threshold" name='<%= PortalSettingsOpenNLPDocumentAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "confidenceThreshold" %>' value="<%= openNPLDocumentAssetAutoTagProviderCompanyConfiguration.confidenceThreshold() %>" />