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
String randomId = workflowTaskDisplayContext.getWorkflowTaskRandomId();

String closeRedirect = ParamUtil.getString(request, "closeRedirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowTask workflowTask = workflowTaskDisplayContext.getWorkflowTask();

PortletURL redirectURL = renderResponse.createRenderURL();

redirectURL.setParameter("mvcPath", "/view.jsp");
%>

<liferay-ui:icon-menu cssClass="lfr-asset-actions" direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showExpanded="<%= (row == null) %>">
	<c:if test="<%= !workflowTask.isCompleted() && workflowTaskDisplayContext.isAssignedToUser(workflowTask) %>">

		<%
		List<String> transitionNames = workflowTaskDisplayContext.getTransitionNames(workflowTask);

		for (String transitionName : transitionNames) {
			String message = workflowTaskDisplayContext.getTransitionMessage(transitionName);
		%>

			<liferay-portlet:actionURL name="completeWorkflowTask" portletName="<%= PortletKeys.MY_WORKFLOW_TASK %>" var="editURL">
				<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
				<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
				<portlet:param name="closeRedirect" value="<%= closeRedirect %>" />
				<portlet:param name="workflowTaskId" value="<%= StringUtil.valueOf(workflowTask.getWorkflowTaskId()) %>" />
				<portlet:param name="assigneeUserId" value="<%= StringUtil.valueOf(workflowTask.getAssigneeUserId()) %>" />

				<c:if test="<%= transitionName != null %>">
					<portlet:param name="transitionName" value="<%= transitionName %>" />
				</c:if>
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				cssClass='<%= "workflow-task-" + randomId + " task-change-status-link" %>'
				data="<%= workflowTaskDisplayContext.getWorkflowTaskActionLinkData() %>"
				id='<%= randomId + HtmlUtil.escapeAttribute(transitionName) + "taskChangeStatusLink" %>'
				message="<%= message %>"
				method="get"
				url="<%= editURL %>"
			/>

		<%
		}
		%>

	</c:if>

	<c:if test="<%= !workflowTask.isCompleted() && !workflowTaskDisplayContext.isAssignedToUser(workflowTask) %>">
		<liferay-portlet:actionURL name="assignWorkflowTask" portletName="<%= PortletKeys.MY_WORKFLOW_TASK %>" var="assignToMeURL">
			<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="closeRedirect" value="<%= closeRedirect %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
			<portlet:param name="assigneeUserId" value="<%= String.valueOf(user.getUserId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon
			cssClass='<%= "workflow-task-" + randomId + " task-assign-to-me-link" %>'
			data="<%= workflowTaskDisplayContext.getWorkflowTaskActionLinkData() %>"
			id='<%= randomId + "taskAssignToMeLink" %>'
			message="assign-to-me"
			method="get"
			url="<%= assignToMeURL %>"
		/>
	</c:if>

	<c:if test="<%= workflowTaskDisplayContext.hasOtherAssignees(workflowTask) %>">
		<liferay-portlet:actionURL name="assignWorkflowTask" portletName="<%= PortletKeys.MY_WORKFLOW_TASK %>" var="assignURL">
			<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="closeRedirect" value="<%= closeRedirect %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon
			cssClass='<%= "workflow-task-" + randomId + " task-assign-link" %>'
			data="<%= workflowTaskDisplayContext.getWorkflowTaskActionLinkData() %>"
			id='<%= randomId + "taskAssignLink" %>'
			message="assign-to-..."
			method="get"
			url="<%= assignURL %>"
		/>
	</c:if>

	<c:if test="<%= !workflowTask.isCompleted() %>">
		<liferay-portlet:actionURL name="updateWorkflowTask" portletName="<%= PortletKeys.MY_WORKFLOW_TASK %>" var="updateDueDateURL">
			<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="workflowTaskId" value="<%= StringUtil.valueOf(workflowTask.getWorkflowTaskId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon
			cssClass='<%= "workflow-task-" + randomId + " task-due-date-link" %>'
			data="<%= workflowTaskDisplayContext.getWorkflowTaskActionLinkData() %>"
			id='<%= randomId + "taskDueDateLink" %>'
			message="update-due-date"
			method="get"
			url="<%= updateDueDateURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>

<div class="hide" id="<%= randomId %>updateAsignee">
	<c:if test="<%= workflowTaskDisplayContext.hasOtherAssignees(workflowTask) %>">
		<aui:select label="assign-to" name="assigneeUserId">

			<%
			for (long pooledActorId : workflowTaskDisplayContext.getActorsIds(workflowTask)) {
			%>

				<aui:option label="<%= workflowTaskDisplayContext.getActorName(pooledActorId) %>" selected="<%= workflowTask.getAssigneeUserId() == pooledActorId %>" value="<%= String.valueOf(pooledActorId) %>" />

			<%
			}
			%>

		</aui:select>
	</c:if>
</div>

<div class="hide" id="<%= randomId %>updateAsigneeToMe">
	<aui:input name="asigneeUserId" type="hidden" value="<%= user.getUserId() %>" />
</div>

<div class="hide" id="<%= randomId %>updateDueDate">
	<aui:input bean="<%= workflowTask %>" model="<%= WorkflowTask.class %>" name="dueDate" required="<%= true %>" />
</div>

<div class="hide" id="<%= randomId %>updateComments">
	<aui:input cols="55" name="comment" placeholder="comment" rows="1" type="textarea" />
</div>

<aui:script use="liferay-workflow-tasks">
	var onTaskClickFn = A.rbind('onTaskClick', Liferay.WorkflowTasks, '<%= randomId %>');

	<c:if test="<%= !workflowTask.isCompleted() && workflowTaskDisplayContext.isAssignedToUser(workflowTask) %>">

		<%
		List<String> transitionNames = workflowTaskDisplayContext.getTransitionNames(workflowTask);

		for (String transitionName : transitionNames) {
			String message = workflowTaskDisplayContext.getTransitionMessage(transitionName);
		%>

			Liferay.delegateClick('<portlet:namespace /><%= randomId + HtmlUtil.escapeJS(transitionName) %>taskChangeStatusLink', onTaskClickFn);

		<%
		}
		%>

	</c:if>

	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskAssignToMeLink', onTaskClickFn);
	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskAssignLink', onTaskClickFn);
	Liferay.delegateClick('<portlet:namespace /><%= randomId %>taskDueDateLink', onTaskClickFn);
</aui:script>