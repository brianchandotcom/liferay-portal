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

<%@ include file="/html/portlet/workflow_instances/init.jsp" %>

<%
String randomId = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowTask workflowTask = (WorkflowTask)row.getObject();
%>

<liferay-ui:icon-menu>
	<c:if test="<%= portletName.equals(PortletKeys.WORKFLOW_DEFINITIONS) && !workflowTask.isCompleted() && !_isAssignedToUser(workflowTask, user) %>">
		<portlet:actionURL var="assignToMeURL">
			<portlet:param name="struts_action" value="/workflow_instances/edit_workflow_instance_task" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ASSIGN %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="workflowTaskId" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />
			<portlet:param name="assigneeUserId" value="<%= String.valueOf(user.getUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			cssClass='<%= "workflow-task-" + randomId + " task-assign-to-me-link" %>'
			image="assign"
			message="assign-to-me"
			method="get"
			url="<%= assignToMeURL %>"
		/>
	</c:if>

	<c:if test="<%= !workflowTask.isCompleted() && _isAssignedToUser(workflowTask, user) %>">

		<%
		List<String> transitionNames = WorkflowTaskManagerUtil.getNextTransitionNames(company.getCompanyId(), user.getUserId(), workflowTask.getWorkflowTaskId());

		for (String transitionName : transitionNames) {
			String message = "proceed";

			if (Validator.isNotNull(transitionName)) {
				message = transitionName;
			}
		%>

			<portlet:actionURL var="editURL">
				<portlet:param name="struts_action" value="/workflow_instances/edit_workflow_instance_task" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SAVE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="workflowTaskId" value="<%= StringUtil.valueOf(workflowTask.getWorkflowTaskId()) %>" />
				<portlet:param name="assigneeUserId" value="<%= StringUtil.valueOf(workflowTask.getAssigneeUserId()) %>" />

				<c:if test="<%= transitionName != null %>">
					<portlet:param name="transitionName" value="<%= transitionName %>" />
				</c:if>
			</portlet:actionURL>

			<liferay-ui:icon
				cssClass='<%= "workflow-task-" + randomId + " task-change-status-link" %>'
				image="../aui/shuffle"
				message="<%= message %>"
				method="get"
				url="<%= editURL %>"
			/>

		<%
		}
		%>

	</c:if>
</liferay-ui:icon-menu>

<div class="hide" id="<%= randomId %>updateComments">
	<aui:input cols="55" name="comment" rows="10" type="textarea" />
</div>

<aui:script use="aui-io-plugin-deprecated,liferay-util-window">
	var showPopup = function(url, title) {
		var form = A.Node.create('<form />');

		form.setAttribute('action', url);
		form.setAttribute('method', 'POST');

		var comments = A.one('#<%= randomId %>updateComments');

		if (comments) {
			form.append(comments);

			comments.show();
		}

		var dialog = Liferay.Util.Window.getWindow(
			{
				dialog: {
					bodyContent: form,
					toolbars: {
						footer: [
							{
								label: '<%= UnicodeLanguageUtil.get(pageContext, "ok") %>',
								on: {
									click: function() {
										submitForm(form);
									}
								}
							},
							{
								label: '<%= UnicodeLanguageUtil.get(pageContext, "cancel") %>',
								on: {
									click: function() {
										dialog.hide();
									}
								}
							}
						]
					}
				},
				title: title
			}
		);
	};

	A.all('.workflow-task-<%= randomId %> a').on(
		'click',
		function(event) {
			event.preventDefault();

			var icon = event.currentTarget;
			var title = icon.text();

			showPopup(icon.attr('href'), title);
		}
	);
</aui:script>