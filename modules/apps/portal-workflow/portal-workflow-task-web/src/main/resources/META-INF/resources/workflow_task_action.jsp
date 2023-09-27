<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String randomId = workflowTaskDisplayContext.getWorkflowTaskRandomId();

String closeRedirect = ParamUtil.getString(request, "closeRedirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowTask workflowTask = workflowTaskDisplayContext.getWorkflowTask();

PortletURL redirectURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCPath(
	"/view.jsp"
).buildPortletURL();
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
				for (WorkflowTransition workflowTransition : workflowTaskDisplayContext.getWorkflowTaskWorkflowTransitions(workflowTask)) {
				%>

					<liferay-portlet:actionURL copyCurrentRenderParameters="<%= false %>" name="/portal_workflow_task/complete_task" portletName="<%= PortletKeys.MY_WORKFLOW_TASK %>" var="editURL">
						<portlet:param name="mvcPath" value="/edit_workflow_task.jsp" />
						<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
						<portlet:param name="closeRedirect" value="<%= closeRedirect %>" />
						<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
						<portlet:param name="assigneeUserId" value="<%= String.valueOf(workflowTask.getAssigneeUserId()) %>" />

						<c:if test="<%= Validator.isNotNull(workflowTransition.getName()) %>">
							<portlet:param name="transitionName" value="<%= workflowTransition.getName() %>" />
						</c:if>
					</liferay-portlet:actionURL>

					<liferay-ui:icon
						cssClass='<%= "workflow-task-" + randomId + " task-change-status-link" %>'
						data="<%= workflowTaskDisplayContext.getWorkflowTaskActionLinkData() %>"
						id='<%= randomId + HtmlUtil.escapeAttribute(workflowTransition.getName()) + "taskChangeStatusLink" %>'
						message="<%= workflowTransition.getLabel(workflowTaskDisplayContext.getTaskContentLocale()) %>"
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
					<portlet:param name="redirect" value='<%= Validator.isNull(request.getParameter("workflowTaskId")) ? redirectURL.toString() : currentURL %>' />
					<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
					<portlet:param name="assigneeUserId" value="<%= String.valueOf(user.getUserId()) %>" />
					<portlet:param name="assignMode" value="assignToMe" />
				</liferay-portlet:renderURL>

				<liferay-ui:icon
					message="assign-to-me"
					onClick='<%= "javascript:" + liferayPortletResponse.getNamespace() + "taskAssignToMe('" + assignToMeURL + "');" %>'
					url="javascript:void(0);"
				/>
			</c:otherwise>
		</c:choose>

		<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="assignURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/workflow_task_assign.jsp" />
			<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
			<portlet:param name="workflowTaskURL" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="assign-to-..."
			onClick='<%= "javascript:" + liferayPortletResponse.getNamespace() + "taskAssign('" + assignURL + "');" %>'
			url="javascript:void(0);"
		/>

		<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="updateDueDateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/workflow_task_due_date.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="update-due-date"
			onClick='<%= "javascript:" + liferayPortletResponse.getNamespace() + "taskUpdate('" + updateDueDateURL + "');" %>'
			url="javascript:void(0);"
		/>
	</c:if>
</liferay-ui:icon-menu>

<aui:form name='<%= randomId + "form" %>'>
	<div class="hide" id="<%= randomId %>updateComments">
		<aui:input cols="55" cssClass="task-content-comment" name="comment" placeholder="comment" rows="1" type="textarea" />
	</div>
</aui:form>

<aui:script use="liferay-workflow-tasks">
	var onTaskClickFn = A.rbind(
		'onTaskClick',
		Liferay.WorkflowTasks,
		'<%= randomId %>'
	);

	<c:if test="<%= !workflowTask.isCompleted() && workflowTaskDisplayContext.isAssignedToUser(workflowTask) %>">

		<%
		for (String transitionName : workflowTaskDisplayContext.getTransitionNames(workflowTask)) {
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
				height: 430,
				resizable: false,
				width: 896,
			},
			dialogIframe: {
				bodyCssClass: 'task-dialog',
			},
			id: '<portlet:namespace />assignToDialog',
			title: '<liferay-ui:message key="assign-to-..." />',
			uri: uri,
		});
	}

	function <portlet:namespace />taskAssignToMe(uri) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				height: 340,
				resizable: false,
				width: 896,
			},
			dialogIframe: {
				bodyCssClass: 'task-dialog',
			},
			id: '<portlet:namespace />assignToDialog',
			title: '<liferay-ui:message key="assign-to-me" />',
			uri: uri,
		});
	}

	function <portlet:namespace />taskUpdate(uri) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				height: 430,
				resizable: false,
				width: 896,
			},
			dialogIframe: {
				bodyCssClass: 'task-dialog',
			},
			id: '<portlet:namespace />updateDialog',
			title: '<liferay-ui:message key="update-due-date" />',
			uri: uri,
		});
	}

	function <portlet:namespace />refreshPortlet(uri) {
		location.href = uri;
	}
</aui:script>