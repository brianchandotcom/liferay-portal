<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

@generated
--%>

<%@ include file="/html/taglib/taglib-init.jsp" %>

<%
java.lang.String ariaLabel = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav:ariaLabel"));
java.lang.String ariaRole = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav:ariaRole"));
boolean collapsible = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:nav:collapsible")));
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav:cssClass"));
java.lang.String icon = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav:icon"));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav:id"));
com.liferay.portal.kernel.dao.search.SearchContainer<?> searchContainer = (com.liferay.portal.kernel.dao.search.SearchContainer<?>)request.getAttribute("aui:nav:searchContainer");
boolean useNamespace = GetterUtil.getBoolean(String.valueOf(request.getAttribute("aui:nav:useNamespace")), true);
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:nav:dynamicAttributes");
%>

<%@ include file="/html/taglib/aui/nav/init-ext.jspf" %>