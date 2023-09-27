<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/process_start_date/init.jsp" %>

<c:choose>
	<c:when test="<%= listView %>">
		<span class="process-date"><%= dateFormatDateTime.format(backgroundTask.getCreateDate()) %></span>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="start-date" />: <%= dateFormatDateTime.format(backgroundTask.getCreateDate()) %>
	</c:otherwise>
</c:choose>