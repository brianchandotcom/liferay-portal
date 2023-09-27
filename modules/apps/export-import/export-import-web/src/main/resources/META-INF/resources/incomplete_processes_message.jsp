<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
int incompleteBackgroundTasksCount = ParamUtil.getInteger(request, "incompleteBackgroundTasksCount");
%>

<div class="alert alert-info">
	<c:choose>
		<c:when test="<%= incompleteBackgroundTasksCount == 1 %>">
			<liferay-ui:message key="there-is-currently-1-process-in-progress" />
		</c:when>
		<c:when test="<%= incompleteBackgroundTasksCount > 1 %>">
			<liferay-ui:message arguments="<%= incompleteBackgroundTasksCount - 1 %>" key="there-is-currently-1-process-in-progress-and-x-pending" translateArguments="<%= false %>" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="there-are-no-processes-in-progress-anymore" />
		</c:otherwise>
	</c:choose>
</div>