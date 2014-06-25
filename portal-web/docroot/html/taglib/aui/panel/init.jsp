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
boolean collapsed = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:panel:collapsed")));
boolean collapsible = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:panel:collapsible")));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:panel:id"));
java.lang.String label = GetterUtil.getString((java.lang.String)request.getAttribute("aui:panel:label"));
boolean localizeLabel = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:panel:localizeLabel")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:panel:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("aui:panel:scopedAttributes");
%>

<%@ include file="/html/taglib/aui/panel/init-ext.jspf" %>