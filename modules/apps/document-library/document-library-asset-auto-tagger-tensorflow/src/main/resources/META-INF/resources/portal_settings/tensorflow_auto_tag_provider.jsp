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
TensorFlowImageAssetAutoTagProviderCompanyConfiguration tensorFlowImageAssetAutoTagProviderCompanyConfiguration = (TensorFlowImageAssetAutoTagProviderCompanyConfiguration)request.getAttribute(TensorFlowImageAssetAutoTagProviderCompanyConfiguration.class.getName());
%>

<aui:alert closeable="<%= false %>" type="info">
	<liferay-ui:message key="tensorflow-auto-tag-provider-configuration-description" />
</aui:alert>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/document_library_asset_auto_tagger_tensorflow" />

<aui:input label="enable-tensorflow-image-auto-tagging-on-this-instance" name='<%= PortalSettingsTensorflowAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "enabled" %>' type="checkbox" value="<%= tensorFlowImageAssetAutoTagProviderCompanyConfiguration.enabled() %>" />

<aui:input label="confidence-threshold" name='<%= PortalSettingsTensorflowAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "confidenceThreshold" %>' value="<%= tensorFlowImageAssetAutoTagProviderCompanyConfiguration.confidenceThreshold() %>" />