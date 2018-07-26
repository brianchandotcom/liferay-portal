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
AssetAutoTaggerConfiguration assetAutoTaggerConfiguration = (AssetAutoTaggerConfiguration)request.getAttribute(AssetAutoTaggerConfiguration.class.getName());
%>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="<%= PortalSettingsAssetAutoTaggerConstants.ACTION_NAME %>" />

<aui:input helpMessage="instance-asset-auto-tagging-help" id="enabled" label="enabled" name='<%= PortalSettingsAssetAutoTaggerConstants.FORM_PARAMETER_NAMESPACE + "enabled" %>' type="checkbox" value="<%= assetAutoTaggerConfiguration.isEnabled() %>" />

<aui:input id="maximum-number-of-tags-per-asset" label="maximum-number-of-tags-per-asset" name='<%= PortalSettingsAssetAutoTaggerConstants.FORM_PARAMETER_NAMESPACE + "getMaximumNumberOfTagsPerAsset" %>' type="number" value="<%= assetAutoTaggerConfiguration.getMaximumNumberOfTagsPerAsset() %>" />