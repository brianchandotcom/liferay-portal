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
String actionJsp = (String)request.getAttribute("liferay-frontend:card:actionJsp");
ServletContext actionJspServletContext = (ServletContext)request.getAttribute("liferay-frontend:card:actionJspServletContext");
String checkboxCSSClass = (String)request.getAttribute("liferay-frontend:card:checkboxCSSClass");
String checkboxId = (String)request.getAttribute("liferay-frontend:card:checkboxId");
String checkboxName = (String)request.getAttribute("liferay-frontend:card:checkboxName");
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-frontend:card:data");
String footer = (String)request.getAttribute("liferay-frontend:card:footer");
String header = (String)request.getAttribute("liferay-frontend:card:header");
String image = (String)request.getAttribute("liferay-frontend:card:image");
String imageCSSClass = (String)request.getAttribute("liferay-frontend:card:imageCSSClass");
boolean showCheckbox = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:card:showCheckbox"));
String smallImageCSSClass = GetterUtil.getString(request.getAttribute("liferay-frontend:card:smallImageCSSClass"));
String smallImageUrl = GetterUtil.getString(request.getAttribute("liferay-frontend:card:smallImageUrl"));
String subtitle = (String)request.getAttribute("liferay-frontend:card:subtitle");
String title = (String)request.getAttribute("liferay-frontend:card:title");
String url = (String)request.getAttribute("liferay-frontend:card:url");
%>