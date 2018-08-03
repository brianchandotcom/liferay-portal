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
TensorFlowImageAssetAutoTagProviderCompanyConfiguration
	tensorFlowImageAssetAutoTagProviderCompanyConfiguration = (TensorFlowImageAssetAutoTagProviderCompanyConfiguration)request.getAttribute(TensorFlowImageAssetAutoTagProviderCompanyConfiguration.class.getName());
%>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="<%= PortalSettingsTensorflowAssetAutoTagProviderConstants.ACTION_NAME %>" />

<aui:input id="enabled" label="enabled" name='<%= PortalSettingsTensorflowAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "enabled" %>' type="checkbox" value="<%= tensorFlowImageAssetAutoTagProviderCompanyConfiguration.enabled() %>" />

<aui:input id="confidence-threshold" label="confidence-threshold" name='<%= PortalSettingsTensorflowAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "confidenceThreshold" %>' value="<%= tensorFlowImageAssetAutoTagProviderCompanyConfiguration.confidenceThreshold() %>" />