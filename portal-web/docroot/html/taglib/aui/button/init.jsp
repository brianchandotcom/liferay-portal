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
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:button:cssClass"));
java.util.Map data = (java.util.Map)request.getAttribute("aui:button:data");
boolean disabled = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:button:disabled")));
java.lang.String href = GetterUtil.getString((java.lang.String)request.getAttribute("aui:button:href"));
java.lang.String icon = GetterUtil.getString((java.lang.String)request.getAttribute("aui:button:icon"));
java.lang.String iconAlign = GetterUtil.getString((java.lang.String)request.getAttribute("aui:button:iconAlign"), "left");
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:button:id"));
java.lang.String name = GetterUtil.getString((java.lang.String)request.getAttribute("aui:button:name"));
java.lang.String onClick = GetterUtil.getString((java.lang.String)request.getAttribute("aui:button:onClick"));
java.lang.Object primary = (java.lang.Object)request.getAttribute("aui:button:primary");
java.lang.String type = GetterUtil.getString((java.lang.String)request.getAttribute("aui:button:type"), "button");
boolean useDialog = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:button:useDialog")));
boolean useNamespace = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:button:useNamespace")), true);
java.lang.String value = GetterUtil.getString((java.lang.String)request.getAttribute("aui:button:value"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:button:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:button:scopedAttributes");
%>

<%@ include file="/html/taglib/aui/button/init-ext.jspf" %>