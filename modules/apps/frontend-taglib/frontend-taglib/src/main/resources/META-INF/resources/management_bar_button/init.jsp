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
boolean active = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:management-bar-button:active"));
String cssClass = (String)request.getAttribute("liferay-frontend:management-bar-button:cssClass");
Map<String, Object> data = (Map<String, Object>)request.getAttribute("liferay-frontend:management-bar-button:data");
boolean disabled = GetterUtil.getBoolean(request.getAttribute("liferay-frontend:management-bar-button:disabled"));
String href = (String)request.getAttribute("liferay-frontend:management-bar-button:href");
String icon = (String)request.getAttribute("liferay-frontend:management-bar-button:icon");
String iconCssClass = (String)request.getAttribute("liferay-frontend:management-bar-button:iconCssClass");
String id = (String)request.getAttribute("liferay-frontend:management-bar-button:id");
String label = (String)request.getAttribute("liferay-frontend:management-bar-button:label");

cssClass = "btn btn-secondary lfr-portal-tooltip " + cssClass;

if (active) {
	cssClass = "active " + cssClass;
}

if (disabled) {
	cssClass = "disabled " + cssClass;
}

if (Validator.isNotNull(icon)) {
	iconCssClass = StringPool.BLANK;
}

if (Validator.isNull(data.get("title"))) {
	data.put("title", LanguageUtil.get(request, label));
}

String labelCssClass = "sr-only";

if (Validator.isNull(icon) && Validator.isNull(iconCssClass)) {
	labelCssClass = StringPool.BLANK;
}
%>