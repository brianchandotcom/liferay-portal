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
String cssClass = (String)request.getAttribute("liferay-frontend:management-bar-sidenav-toggler-button:cssClass");
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-frontend:management-bar-sidenav-toggler-button:data");
boolean disabled = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:management-bar-sidenav-toggler-button:disabled"));
String href = (String)request.getAttribute("liferay-frontend:management-bar-sidenav-toggler-button:href");
String icon = (String)request.getAttribute("liferay-frontend:management-bar-sidenav-toggler-button:icon");
String iconCssClass = (String)request.getAttribute("liferay-frontend:management-bar-sidenav-toggler-button:iconCssClass");
String id = (String)request.getAttribute("liferay-frontend:management-bar-sidenav-toggler-button:id");
String label = (String)request.getAttribute("liferay-frontend:management-bar-sidenav-toggler-button:label");
String position = (String)request.getAttribute("liferay-frontend:management-bar-sidenav-toggler-button:position");
String type = (String)request.getAttribute("liferay-frontend:management-bar-sidenav-toggler-button:type");
String typeMobile = (String)request.getAttribute("liferay-frontend:management-bar-sidenav-toggler-button:typeMobile");
String width = (String)request.getAttribute("liferay-frontend:management-bar-sidenav-toggler-button:width");

String sidenavId = liferayPortletResponse.getNamespace() + "infoPanelId";

if (Validator.isNull(href)) {
	href = "#" + sidenavId;
}

if (Validator.isNull(position)) {
	position = "right";
}

if (Validator.isNull(type)) {
	type = "relative";
}

if (Validator.isNull(typeMobile)) {
	typeMobile = "fixed";
}

if (Validator.isNull(width)) {
	width = "320px";
}
%>