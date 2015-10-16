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
String actionJsp = (String)request.getAttribute("liferay-frontend:vertical-card:actionJsp");
ServletContext actionJspServletContext = (ServletContext)request.getAttribute("liferay-frontend:vertical-card:actionJspServletContext");
boolean backgroundImage = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:vertical-card:backgroundImage"));
boolean checkboxChecked = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:vertical-card:checkboxChecked"));
String checkboxCSSClass = (String)request.getAttribute("liferay-frontend:vertical-card:checkboxCSSClass");
Map<String, Object> checkboxData = (Map<String, Object>)request.getAttribute("liferay-frontend:vertical-card:checkboxData");
boolean checkboxDisabled = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:vertical-card:checkboxDisabled"));
String checkboxId = (String)request.getAttribute("liferay-frontend:vertical-card:checkboxId");
String checkboxName = (String)request.getAttribute("liferay-frontend:vertical-card:checkboxName");
String checkboxValue = (String)request.getAttribute("liferay-frontend:vertical-card:checkboxValue");
String cssClass = (String)request.getAttribute("liferay-frontend:vertical-card:cssClass");
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-frontend:vertical-card:data");
String footer = (String)request.getAttribute("liferay-frontend:vertical-card:footer");
String header = (String)request.getAttribute("liferay-frontend:vertical-card:header");
String imageCSSClass = (String)request.getAttribute("liferay-frontend:vertical-card:imageCSSClass");
String imageUrl = (String)request.getAttribute("liferay-frontend:vertical-card:imageUrl");
ResultRow resultRow = (ResultRow)request.getAttribute("liferay-frontend:vertical-card:resultRow");
RowChecker rowChecker = (RowChecker)request.getAttribute("liferay-frontend:vertical-card:rowChecker");
boolean showCheckbox = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:vertical-card:showCheckbox"));
String smallImageCSSClass = GetterUtil.getString(request.getAttribute("liferay-frontend:vertical-card:smallImageCSSClass"));
String smallImageUrl = GetterUtil.getString(request.getAttribute("liferay-frontend:vertical-card:smallImageUrl"));
String subtitle = (String)request.getAttribute("liferay-frontend:vertical-card:subtitle");
String title = (String)request.getAttribute("liferay-frontend:vertical-card:title");
String url = (String)request.getAttribute("liferay-frontend:vertical-card:url");
%>