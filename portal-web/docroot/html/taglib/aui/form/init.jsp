<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:form:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:form:scopedAttributes");
CustomAttributes customAttributes = (CustomAttributes)request.getAttribute("aui:form:customAttributes");

Map<String, Object> _options = new HashMap<String, Object>();

if ((scopedAttributes != null) && !scopedAttributes.isEmpty()) {
	_options.putAll(scopedAttributes);
}

if ((dynamicAttributes != null) && !dynamicAttributes.isEmpty()) {
	_options.putAll(dynamicAttributes);
}

java.lang.String action = GetterUtil.getString((java.lang.String)request.getAttribute("aui:form:action"));
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:form:cssClass"));
boolean escapeXml = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:form:escapeXml")), true);
boolean inlineLabels = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:form:inlineLabels")));
java.lang.String name = GetterUtil.getString((java.lang.String)request.getAttribute("aui:form:name"), "fm");
java.lang.String onSubmit = GetterUtil.getString((java.lang.String)request.getAttribute("aui:form:onSubmit"));
boolean useNamespace = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:form:useNamespace")), true);

_updateOptions(_options, "action", action);
_updateOptions(_options, "cssClass", cssClass);
_updateOptions(_options, "escapeXml", escapeXml);
_updateOptions(_options, "inlineLabels", inlineLabels);
_updateOptions(_options, "name", name);
_updateOptions(_options, "onSubmit", onSubmit);
_updateOptions(_options, "useNamespace", useNamespace);
%>

<%@ include file="/html/taglib/aui/form/init-ext.jspf" %>

<%!
private static final String _NAMESPACE = "aui:form:";
%>