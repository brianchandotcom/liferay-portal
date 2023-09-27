<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

@generated
--%>

<%@ include file="/html/taglib/taglib-init.jsp" %>

<%
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-bar-search:cssClass"));
java.lang.String file = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-bar-search:file"));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-bar-search:id"));
com.liferay.portal.kernel.dao.search.SearchContainer<?> searchContainer = (com.liferay.portal.kernel.dao.search.SearchContainer<?>)request.getAttribute("aui:nav-bar-search:searchContainer");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:nav-bar-search:dynamicAttributes");
%>

<%@ include file="/html/taglib/aui/nav_bar_search/init-ext.jspf" %>