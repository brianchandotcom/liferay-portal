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

boolean last = productEntryOrder == (searchContainer.getTotal() - 1);
%>

<c:choose>
	<c:when test="<%= (productEntryOrder == 0) && last %>">
	</c:when>
	<c:when test="<%= (productEntryOrder > 0) && !last %>">

		<%
		String taglibDownURL = "javascript:" + liferayPortletResponse.getNamespace() + "moveSelectionDown('" + productEntryOrder + "')";
		%>

		<liferay-ui:icon
			icon="angle-down"
			markupView="lexicon"
			message="down"
			url="<%= taglibDownURL %>"
		/>

		<%
		String taglibUpURL = "javascript:" + liferayPortletResponse.getNamespace() + "moveSelectionUp('" + productEntryOrder + "')";
		%>

		<liferay-ui:icon
			icon="angle-up"
			markupView="lexicon"
			message="up"
			url="<%= taglibUpURL %>"
		/>
	</c:when>
	<c:when test="<%= productEntryOrder == 0 %>">

		<%
		String taglibDownURL = "javascript:" + liferayPortletResponse.getNamespace() + "moveSelectionDown('" + productEntryOrder + "')";
		%>

		<liferay-ui:icon
			icon="angle-down"
			markupView="lexicon"
			message="down"
			url="<%= taglibDownURL %>"
		/>
	</c:when>
	<c:when test="<%= last %>">

		<%
		String taglibUpURL = "javascript:" + liferayPortletResponse.getNamespace() + "moveSelectionUp('" + productEntryOrder + "')";
		%>

		<liferay-ui:icon
			icon="angle-up"
			markupView="lexicon"
			message="up"
			url="<%= taglibUpURL %>"
		/>
	</c:when>
</c:choose>