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

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String defaultBulletStyle = GetterUtil.getString(themeDisplay.getThemeSetting("bullet-style"), "dots");

String bulletStyle = PrefsParamUtil.getString(preferences, renderRequest, "bulletStyle", defaultBulletStyle);

String displayStyle = PrefsParamUtil.getString(preferences, renderRequest, "displayStyle", "relative-with-breadcrumb");

String headerType = PrefsParamUtil.getString(preferences, renderRequest, "headerType", "root-layout");

String rootLayoutType = PrefsParamUtil.getString(preferences, renderRequest, "rootLayoutType", "absolute");
int rootLayoutLevel = PrefsParamUtil.getInteger(preferences, renderRequest, "rootLayoutLevel", 1);

String includedLayouts = PrefsParamUtil.getString(preferences, renderRequest, "includedLayouts", "current");

boolean nestedChildren = PrefsParamUtil.getBoolean(preferences, renderRequest, "nestedChildren", true);
%>

<%@ include file="/html/portlet/navigation/init-ext.jsp" %>