<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
int incompleteBackgroundTaskCount = ParamUtil.getInteger(request, "incompleteBackgroundTaskCount");
%>

<div class="alert alert-info">
	<c:choose>
		<c:when test="<%= incompleteBackgroundTaskCount == 1 %>">
			<liferay-ui:message key="there-is-currently-1-process-in-progress" />
		</c:when>
		<c:when test="<%= incompleteBackgroundTaskCount > 1 %>">
			<liferay-ui:message arguments="<%= incompleteBackgroundTaskCount - 1 %>" key="there-is-currently-1-process-in-progress-and-x-pending" translateArguments="<%= false %>" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="there-are-no-processes-in-progress-anymore" />
		</c:otherwise>
	</c:choose>
</div>