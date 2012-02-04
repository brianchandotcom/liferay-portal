<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.portlet.asset.model.AssetRendererFactory" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

long classNameId = PrefsParamUtil.getLong(preferences, request, "classNameId");
String displayStyle = PrefsParamUtil.getString(preferences, request, "displayStyle", "cloud");
int maxAssetTags = PrefsParamUtil.getInteger(preferences, request, "maxAssetTags", 10);
boolean showAssetCount = PrefsParamUtil.getBoolean(preferences, request, "showAssetCount");
boolean showZeroAssetCount = PrefsParamUtil.getBoolean(preferences, request, "showZeroAssetCount");
%>

<%@ include file="/html/portlet/asset_tags_navigation/init-ext.jsp" %>