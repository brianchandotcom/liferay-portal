<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SearchContainer<?> searchContainer = (SearchContainer<?>)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = String.valueOf(searchContainer.getIteratorURL());

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CustomAttributesDisplay customAttributesDisplay = (CustomAttributesDisplay)row.getParameter("customAttributesDisplay");
%>

<c:if test="<%= permissionChecker.isCompanyAdmin() %>">
	<portlet:renderURL var="editURL">
		<portlet:param name="mvcPath" value="/view_attributes.jsp" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="modelResource" value="<%= customAttributesDisplay.getClassName() %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		icon="edit"
		markupView="lexicon"
		message="edit"
		url="<%= editURL %>"
	/>
</c:if>