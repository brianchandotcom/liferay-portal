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
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:component:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:component:scopedAttributes");
CustomAttributes customAttributes = (CustomAttributes)request.getAttribute("aui:component:customAttributes");

Map<String, Object> _options = new HashMap<String, Object>();

if ((scopedAttributes != null) && !scopedAttributes.isEmpty()) {
	_options.putAll(scopedAttributes);
}

if ((dynamicAttributes != null) && !dynamicAttributes.isEmpty()) {
	_options.putAll(dynamicAttributes);
}

boolean defineVar = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:component:defineVar")), true);
java.lang.String excludeAttributes = GetterUtil.getString((java.lang.String)request.getAttribute("aui:component:excludeAttributes"));
java.lang.String javaScriptAttributes = GetterUtil.getString((java.lang.String)request.getAttribute("aui:component:javaScriptAttributes"));
java.lang.String module = GetterUtil.getString((java.lang.String)request.getAttribute("aui:component:module"));
java.lang.String name = GetterUtil.getString((java.lang.String)request.getAttribute("aui:component:name"));
java.util.Map<java.lang.String, java.lang.Object> options = (java.util.Map<java.lang.String, java.lang.Object>)request.getAttribute("aui:component:options");
java.lang.String scriptPosition = GetterUtil.getString((java.lang.String)request.getAttribute("aui:component:scriptPosition"));
javax.servlet.jsp.JspContext tagPageContext = (javax.servlet.jsp.JspContext)request.getAttribute("aui:component:tagPageContext");
boolean useJavaScript = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:component:useJavaScript")), true);
java.lang.String var = GetterUtil.getString((java.lang.String)request.getAttribute("aui:component:var"));

_updateOptions(_options, "defineVar", defineVar);
_updateOptions(_options, "excludeAttributes", excludeAttributes);
_updateOptions(_options, "javaScriptAttributes", javaScriptAttributes);
_updateOptions(_options, "module", module);
_updateOptions(_options, "name", name);
_updateOptions(_options, "options", options);
_updateOptions(_options, "scriptPosition", scriptPosition);
_updateOptions(_options, "tagPageContext", tagPageContext);
_updateOptions(_options, "useJavaScript", useJavaScript);
_updateOptions(_options, "var", var);
%>

<%@ include file="/html/taglib/aui/component/init-ext.jspf" %>

<%!
private static final String _NAMESPACE = "aui:component:";
%>