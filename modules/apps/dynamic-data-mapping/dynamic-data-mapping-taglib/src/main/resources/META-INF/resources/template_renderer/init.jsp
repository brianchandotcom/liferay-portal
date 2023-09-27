<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

@generated
--%>

<%@ include file="/init.jsp" %>

<%
java.lang.String className = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:template-renderer:className"));
java.util.Map<java.lang.String, java.lang.Object> contextObjects = (java.util.Map<java.lang.String, java.lang.Object>)request.getAttribute("liferay-ddm:template-renderer:contextObjects");
java.lang.String displayStyle = GetterUtil.getString((java.lang.String)request.getAttribute("liferay-ddm:template-renderer:displayStyle"));
long displayStyleGroupId = GetterUtil.getLong(String.valueOf(request.getAttribute("liferay-ddm:template-renderer:displayStyleGroupId")));
java.util.List<?> entries = (java.util.List<?>)request.getAttribute("liferay-ddm:template-renderer:entries");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("liferay-ddm:template-renderer:dynamicAttributes");
%>

<%@ include file="/template_renderer/init-ext.jspf" %>