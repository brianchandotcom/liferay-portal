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
String actionJsp = (String)request.getAttribute("liferay-frontend:horizontal-card:actionJsp");
ServletContext actionJspServletContext = (ServletContext)request.getAttribute("liferay-frontend:horizontal-card:actionJspServletContext");
boolean checkboxChecked = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:horizontal-card:checkboxChecked"));
String checkboxCSSClass = (String)request.getAttribute("liferay-frontend:horizontal-card:checkboxCSSClass");
Map<String, Object> checkboxData = (Map<String, Object>)request.getAttribute("liferay-frontend:horizontal-card:checkboxData");
boolean checkboxDisabled = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:horizontal-card:checkboxDisabled"));
String checkboxId = (String)request.getAttribute("liferay-frontend:horizontal-card:checkboxId");
String checkboxName = (String)request.getAttribute("liferay-frontend:horizontal-card:checkboxName");
String checkboxValue = (String)request.getAttribute("liferay-frontend:horizontal-card:checkboxValue");
String cssClass = (String)request.getAttribute("liferay-frontend:horizontal-card:cssClass");
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-frontend:horizontal-card:data");
String icon = (String)request.getAttribute("liferay-frontend:horizontal-card:icon");
String imageCSSClass = (String)request.getAttribute("liferay-frontend:horizontal-card:imageCSSClass");
String imageUrl = (String)request.getAttribute("liferay-frontend:horizontal-card:imageUrl");
ResultRow resultRow = (ResultRow)request.getAttribute("liferay-frontend:horizontal-card:resultRow");
RowChecker rowChecker = (RowChecker)request.getAttribute("liferay-frontend:horizontal-card:rowChecker");
boolean showCheckbox = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:horizontal-card:showCheckbox"));
String text = (String)request.getAttribute("liferay-frontend:horizontal-card:text");
String url = (String)request.getAttribute("liferay-frontend:horizontal-card:url");
%>