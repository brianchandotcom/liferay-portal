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
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:workflow-status:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:workflow-status:scopedAttributes");

Map<String, Object> _options = new HashMap<String, Object>();

if ((scopedAttributes != null) && !scopedAttributes.isEmpty()) {
	_options.putAll(scopedAttributes);
}

if ((dynamicAttributes != null) && !dynamicAttributes.isEmpty()) {
	_options.putAll(dynamicAttributes);
}

java.lang.Object bean = (java.lang.Object)request.getAttribute("aui:workflow-status:bean");
java.lang.String helpMessage = GetterUtil.getString((java.lang.String)request.getAttribute("aui:workflow-status:helpMessage"));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:workflow-status:id"));
java.lang.Class<?> model = (java.lang.Class<?>)request.getAttribute("aui:workflow-status:model");
boolean showIcon = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:workflow-status:showIcon")), true);
boolean showLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:workflow-status:showLabel")), true);
java.lang.Integer status = GetterUtil.getInteger(String.valueOf(request.getAttribute("aui:workflow-status:status")));
java.lang.String statusMessage = GetterUtil.getString((java.lang.String)request.getAttribute("aui:workflow-status:statusMessage"));
java.lang.String version = GetterUtil.getString((java.lang.String)request.getAttribute("aui:workflow-status:version"));

_updateOptions(_options, "bean", bean);
_updateOptions(_options, "helpMessage", helpMessage);
_updateOptions(_options, "id", id);
_updateOptions(_options, "model", model);
_updateOptions(_options, "showIcon", showIcon);
_updateOptions(_options, "showLabel", showLabel);
_updateOptions(_options, "status", status);
_updateOptions(_options, "statusMessage", statusMessage);
_updateOptions(_options, "version", version);
%>

<%@ include file="/html/taglib/aui/workflow_status/init-ext.jspf" %>

<%!
private static final String _NAMESPACE = "aui:workflow-status:";
%>