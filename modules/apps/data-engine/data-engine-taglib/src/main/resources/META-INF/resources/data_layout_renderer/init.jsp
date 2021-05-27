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
java.lang.String containerId = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-data-engine:data-layout-renderer:containerId"));
java.lang.Long dataDefinitionId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:dataDefinitionId")));
java.lang.Long dataLayoutId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:dataLayoutId")));
java.lang.Long dataRecordId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:dataRecordId")));
java.util.Map<java.lang.String, java.lang.Object> dataRecordValues = (java.util.Map<java.lang.String, java.lang.Object>)request.getAttribute("liferay-data-engine:data-layout-renderer:dataRecordValues");
java.lang.String defaultLanguageId = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-data-engine:data-layout-renderer:defaultLanguageId"));
java.lang.String languageId = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-data-engine:data-layout-renderer:languageId"));
java.lang.String namespace = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-data-engine:data-layout-renderer:namespace"));
boolean persistDefaultValues = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:persistDefaultValues")));
boolean persisted = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:persisted")));
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:readOnly")));
boolean submittable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("liferay-data-engine:data-layout-renderer:submittable")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-data-engine:data-layout-renderer:dynamicAttributes");
%>

<%@ include file="/data_layout_renderer/init-ext.jspf" %>