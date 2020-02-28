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

@generated
--%>

<%@ include file="/init.jsp" %>

<%
java.lang.String componentId = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-data-engine:data-layout-builder:componentId"));
java.lang.String contentType = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-data-engine:data-layout-builder:contentType"));
java.lang.Long dataDefinitionId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-builder:dataDefinitionId")));
java.lang.Long dataLayoutId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-builder:dataLayoutId")));
java.lang.Long groupId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-builder:groupId")));
boolean localizable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-builder:localizable")));
java.lang.String namespace = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-data-engine:data-layout-builder:namespace"));
java.lang.Object scopes = (java.lang.Object)request.getAttribute("liferay-data-engine:data-layout-builder:scopes");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-data-engine:data-layout-builder:dynamicAttributes");
%>

<%@ include file="/data_layout_builder/init-ext.jspf" %>