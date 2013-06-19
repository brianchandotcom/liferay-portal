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

<%@ page import="com.liferay.taglib.ui.SitesDirectoryTag" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

String displayStyle = PrefsParamUtil.getString(portletPreferences, renderRequest, "displayStyle", "descriptive");
String sites = PrefsParamUtil.getString(portletPreferences, renderRequest, "sites", SitesDirectoryTag.SITES_TOP_LEVEL);
%>

<%@ include file="/html/portlet/sites_directory/init-ext.jsp" %>