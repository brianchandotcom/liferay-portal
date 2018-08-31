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
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:field-wrapper:cssClass"));
java.util.Map data = (java.util.Map)request.getAttribute("aui:field-wrapper:data");
boolean disabled = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:field-wrapper:disabled")));
boolean first = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:field-wrapper:first")));
java.lang.String helpMessage = GetterUtil.getString((java.lang.String)request.getAttribute("aui:field-wrapper:helpMessage"));
boolean inlineField = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:field-wrapper:inlineField")));
java.lang.String inlineLabel = GetterUtil.getString((java.lang.String)request.getAttribute("aui:field-wrapper:inlineLabel"));
java.lang.String label = GetterUtil.getString((java.lang.String)request.getAttribute("aui:field-wrapper:label"));
boolean last = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:field-wrapper:last")));
boolean localizeLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:field-wrapper:localizeLabel")), true);
java.lang.String name = GetterUtil.getString((java.lang.String)request.getAttribute("aui:field-wrapper:name"));
boolean required = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:field-wrapper:required")));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:field-wrapper:dynamicAttributes");
%>

<%@ include file="/html/taglib/aui/field_wrapper/init-ext.jspf" %>