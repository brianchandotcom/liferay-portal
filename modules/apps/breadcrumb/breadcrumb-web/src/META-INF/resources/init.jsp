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

<%@ page import="com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry" %><%@
page import="com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbUtil" %><%@
page import="com.liferay.portal.kernel.template.TemplateHandler" %><%@
page import="com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PropsValues" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Arrays" %><%@
page import="java.util.List" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
String portletResource = ParamUtil.getString(request, "portletResource");

String displayStyle = PrefsParamUtil.getString(portletPreferences, renderRequest, "displayStyle", PropsValues.BREADCRUMB_DISPLAY_STYLE_DEFAULT);
long displayStyleGroupId = GetterUtil.getLong(portletPreferences.getValue("displayStyleGroupId", null), themeDisplay.getScopeGroupId());
boolean showCurrentGroup = PrefsParamUtil.getBoolean(portletPreferences, renderRequest, "showCurrentGroup", true);
boolean showGuestGroup = PrefsParamUtil.getBoolean(portletPreferences, renderRequest, "showGuestGroup", PropsValues.BREADCRUMB_SHOW_GUEST_GROUP);
boolean showLayout = PrefsParamUtil.getBoolean(portletPreferences, renderRequest, "showLayout", true);
boolean showParentGroups = PrefsParamUtil.getBoolean(portletPreferences, renderRequest, "showParentGroups", PropsValues.BREADCRUMB_SHOW_PARENT_GROUPS);
boolean showPortletBreadcrumb = PrefsParamUtil.getBoolean(portletPreferences, renderRequest, "showPortletBreadcrumb", true);
%>

<%@ include file="/init-ext.jsp" %>