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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.liferay.navigation.web.configuration.NavigationWebConfigurationUtil" %><%@
page import="com.liferay.navigation.web.configuration.NavigationWebConfigurationValues" %><%@
page import="com.liferay.portal.kernel.configuration.Filter" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
String portletResource = ParamUtil.getString(request, "portletResource");

String bulletStyle = PrefsParamUtil.getString(portletPreferences, renderRequest, "bulletStyle", GetterUtil.getString(themeDisplay.getThemeSetting("bullet-style"), "dots"));
String displayStyle = PrefsParamUtil.getString(portletPreferences, renderRequest, "displayStyle", NavigationWebConfigurationValues.DISPLAY_STYLE_DEFAULT);
String headerType = PrefsParamUtil.getString(portletPreferences, renderRequest, "headerType", "root-layout");
String includedLayouts = PrefsParamUtil.getString(portletPreferences, renderRequest, "includedLayouts", "current");
boolean nestedChildren = PrefsParamUtil.getBoolean(portletPreferences, renderRequest, "nestedChildren", true);
boolean preview = PrefsParamUtil.getBoolean(portletPreferences, renderRequest, "preview");
int rootLayoutLevel = PrefsParamUtil.getInteger(portletPreferences, renderRequest, "rootLayoutLevel", 1);
String rootLayoutType = PrefsParamUtil.getString(portletPreferences, renderRequest, "rootLayoutType", "absolute");
%>

<%@ include file="/init-ext.jsp" %>