<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Group scopeGroup = themeDisplay.getScopeGroup();
%>

<c:choose>
	<c:when test='<%= Objects.equals(layoutPageTemplatesAdminDisplayContext.getTabs1(), "display-page-templates") %>'>
		<liferay-util:include page="/view_display_pages.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= Objects.equals(layoutPageTemplatesAdminDisplayContext.getTabs1(), "master-layouts") %>'>
		<liferay-util:include page="/view_master_layouts.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= Objects.equals(layoutPageTemplatesAdminDisplayContext.getTabs1(), "page-templates") && scopeGroup.isCompany() %>'>
		<liferay-util:include page="/view_layout_prototypes.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= Objects.equals(layoutPageTemplatesAdminDisplayContext.getTabs1(), "page-templates") && !scopeGroup.isCompany() %>'>
		<liferay-util:include page="/view_layout_page_template_collections.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>