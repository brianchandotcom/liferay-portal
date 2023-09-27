<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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