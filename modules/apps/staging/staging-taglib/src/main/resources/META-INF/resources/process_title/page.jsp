<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/process_title/init.jsp" %>

<c:choose>
	<c:when test="<%= listView %>">
		<strong class="process-title table-list-title" id="<%= domId %>">
			<liferay-ui:message key="<%= HtmlUtil.escape(backgroundTaskName) %>" />
		</strong>
	</c:when>
	<c:otherwise>
		<strong class="process-title table-list-title" id="<%= domId %>">
			<liferay-ui:message key="<%= HtmlUtil.escape(backgroundTaskName) %>" />
		</strong>
	</c:otherwise>
</c:choose>