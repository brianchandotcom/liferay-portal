<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:col:cssClass"));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:col:id"));
int offset = GetterUtil.getInteger(String.valueOf(request.getAttribute("aui:col:offset")));
int offsetWidth = GetterUtil.getInteger(String.valueOf(request.getAttribute("aui:col:offsetWidth")));
int span = GetterUtil.getInteger(String.valueOf(request.getAttribute("aui:col:span")), 12);
int width = GetterUtil.getInteger(String.valueOf(request.getAttribute("aui:col:width")));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:col:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:col:scopedAttributes");
%>

<%@ include file="/html/taglib/aui/col/init-ext.jspf" %>