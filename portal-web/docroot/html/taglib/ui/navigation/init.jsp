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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.theme.NavItem" %>

<%
String bulletStyle = StringUtil.toLowerCase(((String)request.getAttribute("liferay-ui:navigation:bulletStyle")));
String displayStyle = GetterUtil.getString((String)request.getAttribute("liferay-ui:navigation:displayStyle"));
long displayStyleGroupId = GetterUtil.getLong(request.getAttribute("liferay-ui:navigation:displayStyleGroupId"));
String headerType = (String)request.getAttribute("liferay-ui:navigation:headerType");
String includedLayouts = (String)request.getAttribute("liferay-ui:navigation:includedLayouts");
List<NavItem> navItems = (List)request.getAttribute("liferay-ui:navigation:navItems");
boolean nestedChildren = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:navigation:nestedChildren"));
boolean preview = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:navigation:preview"));
int rootLayoutLevel = (Integer)request.getAttribute("liferay-ui:navigation:rootLayoutLevel");
String rootLayoutType = (String)request.getAttribute("liferay-ui:navigation:rootLayoutType");
%>