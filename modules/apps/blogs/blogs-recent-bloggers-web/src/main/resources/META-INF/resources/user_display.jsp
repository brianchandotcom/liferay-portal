<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

BlogsStatsUser statsUser = (BlogsStatsUser)objArray[0];
%>

<liferay-user:user-display
	url="<%= (String)objArray[1] %>"
	userId="<%= statsUser.getStatsUserId() %>"
>
	<div class="blogger-post-count">
		<c:choose>
			<c:when test="<%= statsUser.getEntryCount() == 1 %>">
				<span><liferay-ui:message key="post" />:</span> <%= statsUser.getEntryCount() %>
			</c:when>
			<c:otherwise>
				<span><liferay-ui:message key="posts" />:</span> <%= statsUser.getEntryCount() %>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="blogger-stars">
		<span><liferay-ui:message key="number-of-ratings" />:</span> <%= statsUser.getRatingsTotalEntries() %>
	</div>

	<div class="blogger-date">
		<span><liferay-ui:message key="date" />:</span> <%= dateFormatDate.format(statsUser.getLastPostDate()) %>
	</div>
</liferay-user:user-display>