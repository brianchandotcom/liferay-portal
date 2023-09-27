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

boolean last = assetEntryOrder == (searchContainer.getTotal() - 1);
%>

<c:choose>
	<c:when test="<%= (assetEntryOrder == 0) && last %>">
	</c:when>
	<c:when test="<%= (assetEntryOrder > 0) && !last %>">

		<%
		String taglibUpURL = "javascript:" + liferayPortletResponse.getNamespace() + "moveSelectionUp('" + assetEntryOrder + "')";
		%>

		<clay:link
			aria-label='<%= LanguageUtil.get(request, "up") %>'
			cssClass="lfr-portal-tooltip"
			href="<%= taglibUpURL %>"
			icon="angle-up"
			title='<%= LanguageUtil.get(request, "up") %>'
		/>

		<%
		String taglibDownURL = "javascript:" + liferayPortletResponse.getNamespace() + "moveSelectionDown('" + assetEntryOrder + "')";
		%>

		<clay:link
			aria-label='<%= LanguageUtil.get(request, "down") %>'
			cssClass="lfr-portal-tooltip"
			href="<%= taglibDownURL %>"
			icon="angle-down"
			title='<%= LanguageUtil.get(request, "down") %>'
		/>
	</c:when>
	<c:when test="<%= assetEntryOrder == 0 %>">

		<%
		String taglibDownURL = "javascript:" + liferayPortletResponse.getNamespace() + "moveSelectionDown('" + assetEntryOrder + "')";
		%>

		<clay:link
			aria-label='<%= LanguageUtil.get(request, "down") %>'
			cssClass="lfr-portal-tooltip"
			href="<%= taglibDownURL %>"
			icon="angle-down"
			title='<%= LanguageUtil.get(request, "down") %>'
		/>
	</c:when>
	<c:when test="<%= last %>">

		<%
		String taglibUpURL = "javascript:" + liferayPortletResponse.getNamespace() + "moveSelectionUp('" + assetEntryOrder + "')";
		%>

		<clay:link
			aria-label='<%= LanguageUtil.get(request, "up") %>'
			cssClass="lfr-portal-tooltip"
			href="<%= taglibUpURL %>"
			icon="angle-up"
			title='<%= LanguageUtil.get(request, "up") %>'
		/>
	</c:when>
</c:choose>