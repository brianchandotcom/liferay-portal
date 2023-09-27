<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
boolean closeButton = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-frontend:sidebar-panel:closeButton")));
String resourceURL = (String)request.getAttribute("liferay-frontend:sidebar-panel:resourceURL");
String searchContainerId = (String)request.getAttribute("liferay-frontend:sidebar-panel:searchContainerId");
String title = (String)request.getAttribute("liferay-frontend:sidebar-panel:title");
%>