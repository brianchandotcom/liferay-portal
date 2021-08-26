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
List<String> activeLanguageIds = (List<String>)request.getAttribute("aui:input:activeLanguageIds");
boolean adminMode = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:adminMode")));
boolean autoFocus = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:autoFocus")));
boolean autoSize = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:autoSize")));
java.lang.Object bean = (java.lang.Object)request.getAttribute("aui:input:bean");
java.lang.String buttonIconOff = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:buttonIconOff"));
java.lang.String buttonIconOn = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:buttonIconOn"));
boolean changesContext = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:changesContext")));
boolean checked = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:checked")));
long classPK = GetterUtil.getLong(String.valueOf(request.getAttribute("aui:input:classPK")));
long classTypePK = GetterUtil.getLong(String.valueOf(request.getAttribute("aui:input:classTypePK")), -1);
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:cssClass"));
java.util.Map data = (java.util.Map)request.getAttribute("aui:input:data");
java.lang.String dateTogglerCheckboxLabel = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:dateTogglerCheckboxLabel"));
java.lang.String defaultLanguageId = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:defaultLanguageId"));
boolean disabled = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:disabled")));
java.lang.String field = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:field"));
java.lang.String fieldParam = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:fieldParam"));
boolean first = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:first")));
java.lang.String formName = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:formName"));
java.lang.String helpMessage = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:helpMessage"));
java.lang.String helpTextCssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:helpTextCssClass"), "input-group-addon");
java.lang.String iconOff = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:iconOff"));
java.lang.String iconOn = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:iconOn"));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:id"));
boolean ignoreRequestValue = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:ignoreRequestValue")));
boolean inlineField = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:inlineField")));
java.lang.String inlineLabel = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:inlineLabel"));
java.lang.String label = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:label"));
java.lang.String labelCssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:labelCssClass"));
java.lang.String languageId = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:languageId"));
boolean last = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:last")));
boolean localized = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:localized")));
boolean localizeLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:localizeLabel")), true);
java.lang.Number max = GetterUtil.getNumber(String.valueOf(request.getAttribute("aui:input:max")), null);
java.lang.Number min = GetterUtil.getNumber(String.valueOf(request.getAttribute("aui:input:min")), null);
java.lang.Class<?> model = (java.lang.Class<?>)request.getAttribute("aui:input:model");
boolean multiple = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:multiple")));
java.lang.String name = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:name"));
java.lang.String onChange = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:onChange"));
java.lang.String onClick = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:onClick"));
java.lang.String placeholder = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:placeholder"));
java.lang.String prefix = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:prefix"));
boolean required = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:required")));
boolean resizable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:resizable")));
java.lang.String selectedLanguageId = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:selectedLanguageId"));
boolean showRequiredLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:showRequiredLabel")), true);
java.lang.String suffix = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:suffix"));
java.lang.String title = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:title"));
java.lang.String type = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:type"));
boolean useNamespace = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:useNamespace")), true);
java.lang.Object value = (java.lang.Object)request.getAttribute("aui:input:value");
boolean wrappedField = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:wrappedField")));
java.lang.String wrapperCssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:wrapperCssClass"));
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:input:dynamicAttributes");
%>

<%@ include file="/html/taglib/aui/input/init-ext.jspf" %>