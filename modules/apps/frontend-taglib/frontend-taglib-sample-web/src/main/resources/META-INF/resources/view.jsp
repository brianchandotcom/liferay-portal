<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
final String navigation = ParamUtil.getString(request, "navigation", "search-iterator");
%>

<clay:navigation-bar
	navigationItems="<%= sampleDisplayContext.getNavigationItems() %>"
/>

<c:choose>
	<c:when test='<%= navigation.equals("search-iterator") %>'>
		<liferay-util:include page="/partials/search_iterator.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/partials/search_paginator.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>