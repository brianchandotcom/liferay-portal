<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<liferay-util:include page="/wiki/top_links.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/wiki/page_tabs.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="history" />
</liferay-util:include>

<liferay-util:include page="/wiki/page_tabs_history.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs3" value="versions" />
</liferay-util:include>

<liferay-util:include page="/wiki/page_iterator.jsp" servletContext="<%= application %>">
	<liferay-util:param name="navigation" value="history" />
</liferay-util:include>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "versions"), null);
%>