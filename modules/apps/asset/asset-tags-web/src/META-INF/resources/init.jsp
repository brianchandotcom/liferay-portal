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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.template.TemplateHandler" %><%@
page import="com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %><%@
page import="com.liferay.portal.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.portlet.asset.model.AssetRendererFactory" %><%@
page import="com.liferay.portlet.asset.model.AssetTag" %><%@
page import="com.liferay.portlet.asset.service.AssetTagServiceUtil" %><%@
page import="com.liferay.portlet.asset.util.comparator.AssetRendererFactoryTypeNameComparator" %><%@
page import="com.liferay.portlet.asset.util.comparator.AssetTagCountComparator" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
long classNameId = PrefsParamUtil.getLong(portletPreferences, request, "classNameId");
String displayStyle = PrefsParamUtil.getString(portletPreferences, request, "displayStyle", "cloud");
long displayStyleGroupId = PrefsParamUtil.getLong(portletPreferences, request, "displayStyleGroupId", themeDisplay.getScopeGroupId());
int maxAssetTags = PrefsParamUtil.getInteger(portletPreferences, request, "maxAssetTags", 10);
boolean showAssetCount = PrefsParamUtil.getBoolean(portletPreferences, request, "showAssetCount");
boolean showZeroAssetCount = PrefsParamUtil.getBoolean(portletPreferences, request, "showZeroAssetCount");
%>

<%@ include file="/init-ext.jsp" %>