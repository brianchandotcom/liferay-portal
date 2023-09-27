<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Portlet portlet = (Portlet)row.getObject();
%>

<portlet:actionURL name="/layout_admin/delete_orphan_portlets" var="deleteOrphanPortletsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="portletId" value="<%= portlet.getPortletId() %>" />
	<portlet:param name="selPlid" value="<%= String.valueOf(layoutsAdminDisplayContext.getSelPlid()) %>" />
</portlet:actionURL>

<clay:link
	aria-label='<%= LanguageUtil.get(request, "delete") %>'
	cssClass="lfr-portal-tooltip"
	href="<%= deleteOrphanPortletsURL %>"
	icon="trash"
	monospaced="<%= true %>"
	title='<%= LanguageUtil.get(request, "delete") %>'
/>