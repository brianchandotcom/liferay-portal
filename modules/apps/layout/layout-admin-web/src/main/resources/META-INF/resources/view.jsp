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

<%@ include file="/init.jsp" %>

<%
Group scopeGroup = themeDisplay.getScopeGroup();

portletDisplay.setShowStagingIcon(false);
%>

<c:choose>
	<c:when test='<%= Objects.equals(layoutsAdminDisplayContext.getTabs1(), "display-page-templates") %>'>
		<liferay-util:include page="/view_display_pages.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= Objects.equals(layoutsAdminDisplayContext.getTabs1(), "pages") %>'>
		<liferay-util:include page="/view_layouts.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= Objects.equals(layoutsAdminDisplayContext.getTabs1(), "page-templates") && !scopeGroup.isCompany() %>'>
		<liferay-util:include page="/view_layout_page_template_collections.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= Objects.equals(layoutsAdminDisplayContext.getTabs1(), "page-templates") && scopeGroup.isCompany() %>'>
		<liferay-util:include page="/view_layout_prototypes.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>