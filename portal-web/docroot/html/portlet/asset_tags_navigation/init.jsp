<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portlet.asset.util.comparator.AssetTagCountComparator" %>

<%
long classNameId = PrefsParamUtil.getLong(portletPreferences, request, "classNameId");
String displayStyle = PrefsParamUtil.getString(portletPreferences, request, "displayStyle", "cloud");
long displayStyleGroupId = PrefsParamUtil.getLong(portletPreferences, request, "displayStyleGroupId", themeDisplay.getScopeGroupId());
int maxAssetTags = PrefsParamUtil.getInteger(portletPreferences, request, "maxAssetTags", 10);
boolean showAssetCount = PrefsParamUtil.getBoolean(portletPreferences, request, "showAssetCount");
boolean showZeroAssetCount = PrefsParamUtil.getBoolean(portletPreferences, request, "showZeroAssetCount");
%>

<%@ include file="/html/portlet/asset_tags_navigation/init-ext.jsp" %>