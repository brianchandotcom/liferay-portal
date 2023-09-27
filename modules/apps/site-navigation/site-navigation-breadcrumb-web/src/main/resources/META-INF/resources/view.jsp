<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
List<BreadcrumbEntry> breadcrumbEntries = siteNavigationBreadcrumbDisplayContext.getBreadcrumbEntries();
%>

<nav aria-label="<%= HtmlUtil.escapeAttribute(portletDisplay.getTitle()) %>" id="<portlet:namespace />breadcrumbs-defaultScreen">
	<c:if test="<%= !breadcrumbEntries.isEmpty() %>">
		<%= siteNavigationBreadcrumbDisplayContext.renderDDMTemplate() %>
	</c:if>
</nav>