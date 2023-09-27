<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long assigneeUserId = ParamUtil.getLong(renderRequest, "assigneeUserId");
String workflowTaskURL = ParamUtil.getString(request, "workflowTaskURL");

WorkflowTask workflowTask = workflowTaskDisplayContext.getWorkflowTask();

boolean hasAssignableUsers = workflowTaskDisplayContext.hasAssignableUsers(workflowTask);
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/portal_workflow_task/assign_task" var="assignURL" />

<div class="task-action">
	<aui:form action="<%= assignURL %>" method="post" name="assignFm">
		<div class="modal-body task-action-content">
			<aui:input name="workflowTaskId" type="hidden" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />

			<c:choose>
				<c:when test="<%= assigneeUserId > 0 %>">
					<aui:input name="assigneeUserId" type="hidden" value="<%= String.valueOf(assigneeUserId) %>" />
				</c:when>
				<c:otherwise>
					<aui:select disabled="<%= !hasAssignableUsers %>" label="assign-to" name="assigneeUserId">

						<%
						for (User assignableUser : workflowTaskDisplayContext.getAssignableUsers(workflowTask)) {
						%>

							<aui:option label="<%= HtmlUtil.escape(assignableUser.getScreenName()) + StringPool.SPACE + StringPool.OPEN_PARENTHESIS + HtmlUtil.escape(assignableUser.getFullName()) + StringPool.CLOSE_PARENTHESIS %>" selected="<%= workflowTask.getAssigneeUserId() == assignableUser.getUserId() %>" value="<%= String.valueOf(assignableUser.getUserId()) %>" />

						<%
						}
						%>

					</aui:select>
				</c:otherwise>
			</c:choose>

			<aui:input cols="55" cssClass="task-action-comment" disabled="<%= !hasAssignableUsers && (assigneeUserId <= 0) %>" name="comment" placeholder="comment" rows="1" type="textarea" />
		</div>

		<div class="modal-footer">
			<div class="modal-item-last">
				<div class="btn-group">
					<div class="btn-group-item">
						<aui:button name="close" type="cancel" />
					</div>

					<div class="btn-group-item">
						<aui:button disabled="<%= !hasAssignableUsers && (assigneeUserId <= 0) %>" name="done" primary="<%= true %>" value="done" />
					</div>
				</div>
			</div>
		</div>
	</aui:form>
</div>

<aui:script use="aui-base">
	var done = A.one('#<portlet:namespace />done');

	if (done) {
		done.on('click', (event) => {
			var data = new FormData(
				document.querySelector('#<portlet:namespace />assignFm')
			);

			Liferay.Util.fetch('<%= assignURL.toString() %>', {
				body: data,
				method: 'POST',
			})
				.then((response) => response.json())
				.then((json) => {
					const assignMode =
						'<%= ParamUtil.getString(request, "assignMode") %>';

					if (assignMode === 'assignToMe') {
						Liferay.Util.getOpener().<portlet:namespace />refreshPortlet(
							'<%= PortalUtil.escapeRedirect(redirect) %>'
						);
					}
					else {
						Liferay.Util.getOpener().<portlet:namespace />refreshPortlet(
							json.hasPermission
								? '<%= PortalUtil.escapeRedirect(workflowTaskURL) %>'
								: '<%= PortalUtil.escapeRedirect(redirect) %>'
						);
					}

					Liferay.Util.getWindow(
						'<portlet:namespace />assignToDialog'
					).destroy();
				});
		});
	}
</aui:script>