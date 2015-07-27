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

<%@ include file="/taglib/common/taglib-init.jsp" %>

<%
java.lang.String className = GetterUtil.getString((java.lang.String)request.getAttribute("ddm:template-renderer:className"));
java.util.Map<java.lang.String, java.lang.Object> contextObjects = (java.util.Map<java.lang.String, java.lang.Object>)request.getAttribute("ddm:template-renderer:contextObjects");
java.lang.String displayStyle = GetterUtil.getString((java.lang.String)request.getAttribute("ddm:template-renderer:displayStyle"));
long displayStyleGroupId = GetterUtil.getLong(String.valueOf(request.getAttribute("ddm:template-renderer:displayStyleGroupId")));
java.util.List<?> entries = (java.util.List<?>)request.getAttribute("ddm:template-renderer:entries");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("ddm:template-renderer:dynamicAttributes");
Map<String, Object> scopedAttributes = (Map<String, Object>)request.getAttribute("ddm:template-renderer:scopedAttributes");
%>

<%@ include file="/taglib/ui/template_renderer/init-ext.jspf" %>