<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
double latitude = GetterUtil.getDouble(request.getAttribute("liferay-map:map:latitude"));
double longitude = GetterUtil.getDouble(request.getAttribute("liferay-map:map:longitude"));
String name = (String)request.getAttribute("liferay-map:map:name");
MapProvider mapProvider = (MapProvider)request.getAttribute("liferay-map:map:mapProvider");

name = AUIUtil.getNamespace(liferayPortletRequest, liferayPortletResponse) + name;
%>