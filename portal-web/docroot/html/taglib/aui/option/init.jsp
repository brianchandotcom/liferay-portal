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
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:option:cssClass"));
java.util.Map<java.lang.String, java.lang.Object> data = (java.util.Map<java.lang.String, java.lang.Object>)request.getAttribute("aui:option:data");
boolean disabled = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:option:disabled")));
java.lang.Object label = (java.lang.Object)request.getAttribute("aui:option:label");
boolean localizeLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:option:localizeLabel")), true);
boolean selected = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:option:selected")));
java.lang.String style = GetterUtil.getString((java.lang.String)request.getAttribute("aui:option:style"));
java.lang.Object value = (java.lang.Object)request.getAttribute("aui:option:value");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:option:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:option:scopedAttributes");
%>

<%@ include file="/html/taglib/aui/option/init-ext.jspf" %>