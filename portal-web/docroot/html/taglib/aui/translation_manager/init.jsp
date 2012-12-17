<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:translation-manager:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:translation-manager:scopedAttributes");

Map<String, Object> _options = new HashMap<String, Object>();

if ((scopedAttributes != null) && !scopedAttributes.isEmpty()) {
	_options.putAll(scopedAttributes);
}

if ((dynamicAttributes != null) && !dynamicAttributes.isEmpty()) {
	_options.putAll(dynamicAttributes);
}

java.util.Locale[] availableLocales = (java.util.Locale[])request.getAttribute("aui:translation-manager:availableLocales");
java.lang.String defaultLanguageId = GetterUtil.getString((java.lang.String)request.getAttribute("aui:translation-manager:defaultLanguageId"));
java.lang.String editingLanguageId = GetterUtil.getString((java.lang.String)request.getAttribute("aui:translation-manager:editingLanguageId"));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:translation-manager:id"));
boolean initialize = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:translation-manager:initialize")), true);
boolean readOnly = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:translation-manager:readOnly")));

_updateOptions(_options, "availableLocales", availableLocales);
_updateOptions(_options, "defaultLanguageId", defaultLanguageId);
_updateOptions(_options, "editingLanguageId", editingLanguageId);
_updateOptions(_options, "id", id);
_updateOptions(_options, "initialize", initialize);
_updateOptions(_options, "readOnly", readOnly);
%>

<%@ include file="/html/taglib/aui/translation_manager/init-ext.jspf" %>

<%!
private static final String _NAMESPACE = "aui:translation-manager:";
%>