<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/process_date/init.jsp" %>

<c:if test="<%= date != null %>">
	<c:choose>
		<c:when test="<%= listView %>">
			<span class="process-date <%= labelKey %>"><%= dateFormatDateTime.format(date) %></span>
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="<%= labelKey %>" />: <%= dateFormatDateTime.format(date) %>
		</c:otherwise>
	</c:choose>
</c:if>