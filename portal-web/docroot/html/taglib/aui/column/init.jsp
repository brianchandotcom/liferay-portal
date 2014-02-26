<%--
/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
int columnWidth = GetterUtil.getInteger(String.valueOf(request.getAttribute("aui:column:columnWidth")));
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:column:cssClass"));
boolean first = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:column:first")));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:column:id"));
boolean last = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:column:last")));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:column:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:column:scopedAttributes");
%>


<%@ include file="/html/taglib/aui/column/init-ext.jspf" %>