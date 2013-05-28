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
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:input:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:input:scopedAttributes");

Map<String, Object> _options = new HashMap<String, Object>();

if ((scopedAttributes != null) && !scopedAttributes.isEmpty()) {
	_options.putAll(scopedAttributes);
}

if ((dynamicAttributes != null) && !dynamicAttributes.isEmpty()) {
	_options.putAll(dynamicAttributes);
}

boolean autoSize = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:autoSize")));
java.lang.Object bean = (java.lang.Object)request.getAttribute("aui:input:bean");
java.lang.String bootstrapHelpTextClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:bootstrapHelpTextClass"));
boolean changesContext = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:changesContext")));
boolean checked = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:checked")));
long classPK = GetterUtil.getLong(String.valueOf(request.getAttribute("aui:input:classPK")));
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
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:id"));
boolean ignoreRequestValue = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:ignoreRequestValue")));
boolean inlineField = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:inlineField")));
java.lang.String inlineLabel = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:inlineLabel"));
java.lang.String label = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:label"));
java.lang.String languageId = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:languageId"));
boolean last = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:last")));
java.lang.Class<?> model = (java.lang.Class<?>)request.getAttribute("aui:input:model");
boolean multiple = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:multiple")));
java.lang.String name = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:name"));
java.lang.String onChange = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:onChange"));
java.lang.String onClick = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:onClick"));
java.lang.String placeholder = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:placeholder"));
java.lang.String prefix = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:prefix"));
boolean required = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:required")));
boolean resizable = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:resizable")));
boolean showRequiredLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:showRequiredLabel")), true);
java.lang.String suffix = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:suffix"));
java.lang.String title = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:title"));
java.lang.String type = GetterUtil.getString((java.lang.String)request.getAttribute("aui:input:type"));
boolean useNamespace = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:input:useNamespace")), true);
java.lang.Object value = (java.lang.Object)request.getAttribute("aui:input:value");

_updateOptions(_options, "autoSize", autoSize);
_updateOptions(_options, "bean", bean);
_updateOptions(_options, "bootstrapHelpTextClass", bootstrapHelpTextClass);
_updateOptions(_options, "changesContext", changesContext);
_updateOptions(_options, "checked", checked);
_updateOptions(_options, "classPK", classPK);
_updateOptions(_options, "cssClass", cssClass);
_updateOptions(_options, "data", data);
_updateOptions(_options, "dateTogglerCheckboxLabel", dateTogglerCheckboxLabel);
_updateOptions(_options, "defaultLanguageId", defaultLanguageId);
_updateOptions(_options, "disabled", disabled);
_updateOptions(_options, "field", field);
_updateOptions(_options, "fieldParam", fieldParam);
_updateOptions(_options, "first", first);
_updateOptions(_options, "formName", formName);
_updateOptions(_options, "helpMessage", helpMessage);
_updateOptions(_options, "id", id);
_updateOptions(_options, "ignoreRequestValue", ignoreRequestValue);
_updateOptions(_options, "inlineField", inlineField);
_updateOptions(_options, "inlineLabel", inlineLabel);
_updateOptions(_options, "label", label);
_updateOptions(_options, "languageId", languageId);
_updateOptions(_options, "last", last);
_updateOptions(_options, "model", model);
_updateOptions(_options, "multiple", multiple);
_updateOptions(_options, "name", name);
_updateOptions(_options, "onChange", onChange);
_updateOptions(_options, "onClick", onClick);
_updateOptions(_options, "placeholder", placeholder);
_updateOptions(_options, "prefix", prefix);
_updateOptions(_options, "required", required);
_updateOptions(_options, "resizable", resizable);
_updateOptions(_options, "showRequiredLabel", showRequiredLabel);
_updateOptions(_options, "suffix", suffix);
_updateOptions(_options, "title", title);
_updateOptions(_options, "type", type);
_updateOptions(_options, "useNamespace", useNamespace);
_updateOptions(_options, "value", value);
%>

<%@ include file="/html/taglib/aui/input/init-ext.jspf" %>

<%!
private static final String _NAMESPACE = "aui:input:";
%>