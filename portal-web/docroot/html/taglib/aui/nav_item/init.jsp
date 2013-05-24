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
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:nav-item:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:nav-item:scopedAttributes");

Map<String, Object> _options = new HashMap<String, Object>();

if ((scopedAttributes != null) && !scopedAttributes.isEmpty()) {
	_options.putAll(scopedAttributes);
}

if ((dynamicAttributes != null) && !dynamicAttributes.isEmpty()) {
	_options.putAll(dynamicAttributes);
}

java.lang.String anchorCssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-item:anchorCssClass"));
java.util.Map anchorData = (java.util.Map)request.getAttribute("aui:nav-item:anchorData");
java.lang.String anchorId = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-item:anchorId"));
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-item:cssClass"));
java.util.Map data = (java.util.Map)request.getAttribute("aui:nav-item:data");
boolean dropdown = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:nav-item:dropdown")));
java.lang.String href = GetterUtil.getString((java.lang.Object)request.getAttribute("aui:nav-item:href"), "javascript:void(0);");
java.lang.String iconClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-item:iconClass"));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-item:id"));
java.lang.String label = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-item:label"));
boolean selected = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:nav-item:selected")));
java.lang.String title = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-item:title"));
boolean wrapDropDownMenu = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:nav-item:wrapDropDownMenu")), true);

_updateOptions(_options, "anchorCssClass", anchorCssClass);
_updateOptions(_options, "anchorData", anchorData);
_updateOptions(_options, "anchorId", anchorId);
_updateOptions(_options, "cssClass", cssClass);
_updateOptions(_options, "data", data);
_updateOptions(_options, "dropdown", dropdown);
_updateOptions(_options, "href", href);
_updateOptions(_options, "iconClass", iconClass);
_updateOptions(_options, "id", id);
_updateOptions(_options, "label", label);
_updateOptions(_options, "selected", selected);
_updateOptions(_options, "title", title);
_updateOptions(_options, "wrapDropDownMenu", wrapDropDownMenu);
%>

<%@ include file="/html/taglib/aui/nav_item/init-ext.jspf" %>

<%!
private static final String _NAMESPACE = "aui:nav-item:";
%>