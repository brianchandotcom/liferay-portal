<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SearchContainer<?> searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

int productEntryOrder = searchContainer.getStart() + row.getPos();
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="deleteURL">
	<portlet:param name="<%= Constants.CMD %>" value="remove-selection" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="productEntryOrder" value="<%= String.valueOf(productEntryOrder) %>" />
</liferay-portlet:actionURL>

<liferay-ui:icon
	icon="times"
	markupView="lexicon"
	url="<%= deleteURL %>"
/>