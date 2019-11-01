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

String mvcPath = ParamUtil.getString(request, "mvcPath", "/view.jsp");

String closeRedirect = ParamUtil.getString(request, "closeRedirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowTask workflowTask = workflowTaskDisplayContext.getWorkflowTask();

PortletURL redirectURL = renderResponse.createRenderURL();

redirectURL.setParameter("mvcPath", "/view.jsp");
%>

<liferay-ui:icon-menu
	cssClass="lfr-asset-actions"
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showExpanded="<%= row == null %>"
>
	<c:if test="<%= !workflowTask.isCompleted() %>">
		<c:choose>
			<c:when test="<%= workflowTaskDisplayContext.isAssignedToUser(workflowTask) %>">

				<%
				List<String> transitionNames = workflowTaskDisplayContext.getTransitionNames(workflowTask);

				for (String transitionName : transitionNames) {
					String message = workflowTaskDisplayContext.getTransitionMessage(transitionName);
				%>

					<liferay-portlet:actionURL copyCurrentRenderParameters="<%= false %>" name="completeWorkflowTask" portletName="<%= PortletKeys.MY_WORKFLOW_TASK %>" var="editURL">
						<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
						<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
						<portlet:param name="closeRedirect" value="<%= closeRedirect %>" />
						<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
						<portlet:param name="assigneeUserId" value="<%= String.valueOf(workflowTask.getAssigneeUserId()) %>" />

						<c:if test="<%= transitionName != null %>">
							<portlet:param name="transitionName" value="<%= transitionName %>" />
						</c:if>
					</liferay-portlet:actionURL>

					<liferay-ui:icon
						cssClass='<%= "workflow-task-" + randomId + " task-change-status-link" %>'
						data="<%= workflowTaskDisplayContext.getWorkflowTaskActionLinkData() %>"
						id='<%= randomId + HtmlUtil.escapeAttribute(transitionName) + "taskChangeStatusLink" %>'
						message="<%= HtmlUtil.escape(message) %>"
						method="get"
						url="<%= editURL %>"
					/>

				<%
				}
				%>

			</c:when>
			<c:otherwise>
				<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="assignToMeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/workflow_task_assign.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
					<portlet:param name="assigneeUserId" value="<%= String.valueOf(user.getUserId()) %>" />
				</liferay-portlet:renderURL>

				<liferay-ui:icon
					message="assign-to-me"
					onClick='<%= "javascript:" + renderResponse.getNamespace() + "taskAssignToMe('" + assignToMeURL + "');" %>'
					url="javascript:;"
				/>
			</c:otherwise>
		</c:choose>

		<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="assignURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/workflow_task_assign.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="assign-to-..."
			onClick='<%= "javascript:" + renderResponse.getNamespace() + "taskAssign('" + assignURL + "');" %>'
			url="javascript:;"
		/>

		<liferay-portlet:actionURL copyCurrentRenderParameters="<%= false %>" name="updateWorkflowTask" portletName="<%= PortletKeys.MY_WORKFLOW_TASK %>" var="updateDueDateURL">
			<portlet:param name="mvcPath" value="<%= mvcPath %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
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

<aui:form name='<%= randomId + "form" %>'>
	<div class="hide" id="<%= randomId %>updateDueDate">
		<aui:input bean="<%= workflowTask %>" ignoreRequestValue="<%= true %>" model="<%= WorkflowTask.class %>" name="dueDate" required="<%= true %>" />
	</div>

	<div class="hide" id="<%= randomId %>updateComments">
		<aui:input cols="55" cssClass="task-content-comment" name="comment" placeholder="comment" rows="1" type="textarea" />
	</div>
</aui:form>

<aui:script use="liferay-workflow-tasks">
	var maxLength = Liferay.AUI.getDateFormat().replace(/%[mdY]/gm, '').length + 8;

	A.all('#<portlet:namespace />dueDate').set('maxLength', maxLength);

	var onDueDateClickFn = A.rbind(
		'onDueDateClick',
		Liferay.WorkflowTasks,
		'<%= randomId %>',
		'<portlet:namespace />'
	);

	Liferay.delegateClick(
		'<portlet:namespace /><%= randomId %>taskDueDateLink',
		onDueDateClickFn
	);

	var onTaskClickFn = A.rbind(
		'onTaskClick',
		Liferay.WorkflowTasks,
		'<%= randomId %>'
	);

	<c:if test="<%= !workflowTask.isCompleted() && workflowTaskDisplayContext.isAssignedToUser(workflowTask) %>">

		<%
		List<String> transitionNames = workflowTaskDisplayContext.getTransitionNames(workflowTask);

		for (String transitionName : transitionNames) {
			String message = workflowTaskDisplayContext.getTransitionMessage(transitionName);
		%>

			Liferay.delegateClick(
				'<portlet:namespace /><%= randomId + HtmlUtil.escapeJS(transitionName) %>taskChangeStatusLink',
				onTaskClickFn
			);

		<%
		}
		%>

	</c:if>
</aui:script>

<aui:script>
	function <portlet:namespace />taskAssign(uri) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				height: 470,
				resizable: false,
				width: 896
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer task-dialog'
			},
			id: '<portlet:namespace />assignToDialog',
			title: '<liferay-ui:message key="assign-to-..." />',
			uri: uri
		});
	}

	function <portlet:namespace />taskAssignToMe(uri) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				height: 380,
				resizable: false,
				width: 896
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer task-dialog'
			},
			id: '<portlet:namespace />assignToDialog',
			title: '<liferay-ui:message key="assign-to-me" />',
			uri: uri
		});
	}

	function <portlet:namespace />refreshPortlet(uri) {
		location.href = uri;
	}
</aui:script>