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