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
String randomId = StringUtil.randomId();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowInstance workflowInstance = null;

if (row != null) {
	workflowInstance = (WorkflowInstance)row.getParameter("workflowInstance");
}
else {
	workflowInstance = (WorkflowInstance)request.getAttribute(WebKeys.WORKFLOW_INSTANCE);
}
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>" showExpanded="<%= (row == null) %>" showWhenSingleIcon="<%= (row == null) %>">
	<c:if test="<%= !workflowInstance.isComplete() %>">
		<portlet:renderURL var="redirectURL">
			<portlet:param name="mvcPath" value="/view.jsp" />
		</portlet:renderURL>

		<portlet:actionURL name="deleteInstance" var="deleteURL">
			<portlet:param name="redirect" value="<%= redirectURL %>" />
			<portlet:param name="workflowInstanceId" value="<%= StringUtil.valueOf(workflowInstance.getWorkflowInstanceId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			iconCssClass="icon-undo"
			message="withdraw-submission"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>