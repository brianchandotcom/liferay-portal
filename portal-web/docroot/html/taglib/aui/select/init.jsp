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
java.lang.Object bean = (java.lang.Object)request.getAttribute("aui:select:bean");
java.lang.String bodyContent = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:bodyContent"));
boolean changesContext = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:select:changesContext")));
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:cssClass"));
java.util.Map<java.lang.String, java.lang.Object> data = (java.util.Map<java.lang.String, java.lang.Object>)request.getAttribute("aui:select:data");
boolean disabled = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:select:disabled")));
java.lang.String field = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:field"));
boolean first = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:select:first")));
java.lang.String helpMessage = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:helpMessage"));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:id"));
boolean ignoreRequestValue = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:select:ignoreRequestValue")));
boolean inlineField = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:select:inlineField")));
java.lang.String inlineLabel = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:inlineLabel"));
java.lang.String label = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:label"));
boolean last = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:select:last")));
java.lang.String listType = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:listType"));
java.lang.String listTypeFieldName = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:listTypeFieldName"));
boolean localizeLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:select:localizeLabel")), true);
java.lang.Class<?> model = (java.lang.Class<?>)request.getAttribute("aui:select:model");
boolean multiple = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:select:multiple")));
java.lang.String name = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:name"));
java.lang.String onChange = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:onChange"));
java.lang.String onClick = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:onClick"));
java.lang.String prefix = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:prefix"));
boolean required = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:select:required")));
boolean showEmptyOption = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:select:showEmptyOption")));
boolean showRequiredLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:select:showRequiredLabel")), true);
java.lang.String suffix = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:suffix"));
java.lang.String title = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:title"));
boolean useNamespace = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:select:useNamespace")), true);
java.lang.Object value = (java.lang.Object)request.getAttribute("aui:select:value");
java.lang.String wrapperCssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:select:wrapperCssClass"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:select:dynamicAttributes");
%>

<%@ include file="/html/taglib/aui/select/init-ext.jspf" %>