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
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:nav-bar-search:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:nav-bar-search:scopedAttributes");

Map<String, Object> _options = new HashMap<String, Object>();

if ((scopedAttributes != null) && !scopedAttributes.isEmpty()) {
	_options.putAll(scopedAttributes);
}

if ((dynamicAttributes != null) && !dynamicAttributes.isEmpty()) {
	_options.putAll(dynamicAttributes);
}

java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-bar-search:cssClass"));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-bar-search:id"));
java.lang.String file = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-bar-search:file"));
com.liferay.portal.kernel.dao.search.SearchContainer searchContainer = (com.liferay.portal.kernel.dao.search.SearchContainer)request.getAttribute("aui:nav-bar-search:searchContainer");

_updateOptions(_options, "cssClass", cssClass);
_updateOptions(_options, "id", id);
_updateOptions(_options, "file", file);
_updateOptions(_options, "searchContainer", searchContainer);
%>

<%@ include file="/html/taglib/aui/nav_bar_search/init-ext.jspf" %>

<%!
private static final String _NAMESPACE = "aui:nav-bar-search:";
%>