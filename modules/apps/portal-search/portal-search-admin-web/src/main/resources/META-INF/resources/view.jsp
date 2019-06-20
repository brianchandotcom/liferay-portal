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
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.admin.web.internal.display.context.SearchAdminDisplayContext" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
SearchAdminDisplayContext searchAdminDisplayContext = (SearchAdminDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String selectedTab = searchAdminDisplayContext.getSelectedTab();
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= searchAdminDisplayContext.getNavigationItemList() %>"
/>

<c:choose>
	<c:when test='<%= selectedTab.equals("index-actions") %>'>
		<liferay-util:include page="/index_actions.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= selectedTab.equals("field-mappings") %>'>
		<liferay-util:include page="/field_mappings.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>