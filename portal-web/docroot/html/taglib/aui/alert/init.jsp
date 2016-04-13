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

<%@ include file="/html/taglib/taglib-init.jsp" %>

<%
boolean animated = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:alert:animated")));
boolean closeable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:alert:closeable")), true);
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:alert:cssClass"));
boolean destroyOnHide = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:alert:destroyOnHide")));
java.lang.Number duration = GetterUtil.getNumber(String.valueOf(request.getAttribute("aui:alert:duration")), 0.15);
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:alert:id"));
java.lang.String type = GetterUtil.getString((java.lang.String)request.getAttribute("aui:alert:type"), "info");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:alert:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:alert:scopedAttributes");
%>

<%@ include file="/html/taglib/aui/alert/init-ext.jspf" %>