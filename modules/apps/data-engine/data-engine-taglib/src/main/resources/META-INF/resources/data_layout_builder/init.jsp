<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

--%>

<%@ include file="/init.jsp" %>

<%
String componentId = GetterUtil.getString((String)request.getAttribute("liferay-data-engine:data-layout-builder:componentId"));
String contentType = GetterUtil.getString((String)request.getAttribute("liferay-data-engine:data-layout-builder:contentType"));
Long dataDefinitionId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-builder:dataDefinitionId")));
Long dataLayoutId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-builder:dataLayoutId")));
boolean displayFieldName = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-builder:displayFieldName")));
String fieldSetContentType = GetterUtil.getString((String)request.getAttribute("liferay-data-engine:data-layout-builder:fieldSetContentType"));
Long groupId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-builder:groupId")));
boolean localizable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-builder:localizable")));
jakarta.servlet.ServletContext moduleServletContext = (jakarta.servlet.ServletContext)request.getAttribute("liferay-data-engine:data-layout-builder:moduleServletContext");
String submitButtonId = GetterUtil.getString((String)request.getAttribute("liferay-data-engine:data-layout-builder:submitButtonId"));
%>

<%@ include file="/data_layout_builder/init-ext.jspf" %>