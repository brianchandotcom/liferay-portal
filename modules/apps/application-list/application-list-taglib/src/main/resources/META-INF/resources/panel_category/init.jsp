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
boolean active = GetterUtil.getBoolean(request.getAttribute("liferay-application-list:panel-category:active"));
String id = (String)request.getAttribute("liferay-application-list:panel-category:id");
int notificationsCount = GetterUtil.getInteger(request.getAttribute("liferay-application-list:panel-category:notificationsCount"));
List<PanelApp> panelApps = (List<PanelApp>)request.getAttribute("liferay-application-list:panel-category:panelApps");
PanelCategory panelCategory = (PanelCategory)request.getAttribute("liferay-application-list:panel-category:panelCategory");
boolean showHeader = GetterUtil.getBoolean(request.getAttribute("liferay-application-list:panel-category:showHeader"));
%>