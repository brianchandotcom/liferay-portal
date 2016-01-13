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
DateSearchEntry dateSearchEntry = new DateSearchEntry();

String displayStyle = workflowTaskDisplayContext.getDisplayStyle();
%>

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280">
	<c:choose>
		<c:when test="<%= workflowTaskDisplayContext.isAssignedToMeTabSelected() %>">
			<liferay-ui:panel-container extended="<%= false %>" id="workflowTasksPanelContainer" persistState="<%= true %>">

				<%
				WorkflowTaskSearch workflowTaskSearch = workflowTaskDisplayContext.getTasksAssignedToMe();
				%>

				<%@ include file="/workflow_tasks.jspf" %>
			</liferay-ui:panel-container>
		</c:when>
		<c:otherwise>

			<%
			WorkflowTaskSearch workflowTaskSearch = workflowTaskDisplayContext.getTasksAssignedToMyRoles();
			%>

			<%@ include file="/workflow_tasks.jspf" %>
		</c:otherwise>
	</c:choose>
</div>