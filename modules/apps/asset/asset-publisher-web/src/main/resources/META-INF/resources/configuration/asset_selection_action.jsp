<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SearchContainer<?> searchContainer = (SearchContainer<?>)request.getAttribute("liferay-ui:search:searchContainer");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

int assetEntryOrder = searchContainer.getStart() + row.getPos();
%>

<liferay-portlet:renderURL portletConfiguration="<%= true %>" varImpl="configurationRenderURL" />

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="deleteURL">
	<portlet:param name="<%= Constants.CMD %>" value="remove-selection" />
	<portlet:param name="redirect" value="<%= configurationRenderURL.toString() %>" />
	<portlet:param name="assetEntryOrder" value="<%= String.valueOf(assetEntryOrder) %>" />
</liferay-portlet:actionURL>

<clay:link
	aria-label='<%= LanguageUtil.get(request, "delete") %>'
	cssClass="lfr-portal-tooltip"
	href="<%= deleteURL %>"
	icon="times-circle"
	title='<%= LanguageUtil.get(request, "delete") %>'
/>