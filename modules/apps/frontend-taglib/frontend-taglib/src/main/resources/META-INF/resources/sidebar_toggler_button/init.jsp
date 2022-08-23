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
String cssClass = (String)request.getAttribute("liferay-frontend:sidebar-toggler-button:cssClass");
String icon = (String)request.getAttribute("liferay-frontend:sidebar-toggler-button:icon");
String label = (String)request.getAttribute("liferay-frontend:sidebar-toggler-button:label");
String sidenavId = (String)request.getAttribute("liferay-frontend:sidebar-toggler-button:sidenavId");
String typeMobile = (String)request.getAttribute("liferay-frontend:sidebar-toggler-button:typeMobile");

if (Validator.isNull(cssClass)) {
	cssClass = "btn btn-secondary";
}

if (Validator.isNull(sidenavId)) {
	sidenavId = liferayPortletResponse.getNamespace() + "infoPanelId";
}

if (Validator.isNull(typeMobile)) {
	typeMobile = "fixed";
}
%>