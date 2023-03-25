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
java.lang.String cssClass = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-bar-search:cssClass"));
java.lang.String file = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-bar-search:file"));
java.lang.String id = GetterUtil.getString((java.lang.String)request.getAttribute("aui:nav-bar-search:id"));
com.liferay.portal.kernel.dao.search.SearchContainer<?> searchContainer = (com.liferay.portal.kernel.dao.search.SearchContainer<?>)request.getAttribute("aui:nav-bar-search:searchContainer");
Map<String, Object> dynamicAttributes = (Map<String, Object>)request.getAttribute("aui:nav-bar-search:dynamicAttributes");
%>

<%@ include file="/html/taglib/aui/nav_bar_search/init-ext.jspf" %>