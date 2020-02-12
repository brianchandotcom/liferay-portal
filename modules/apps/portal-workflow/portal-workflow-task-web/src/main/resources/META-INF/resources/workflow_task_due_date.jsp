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
WorkflowTask workflowTask = workflowTaskDisplayContext.getWorkflowTask();

String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="updateWorkflowTask" var="updateURL" />

<div class="task-action">
	<aui:form action="<%= updateURL %>" method="post" name="updateFm">
		<div class="modal-body task-action-content">
			<aui:input name="workflowTaskId" type="hidden" value="<%= String.valueOf(workflowTask.getWorkflowTaskId()) %>" />

			<aui:input bean="<%= workflowTask %>" ignoreRequestValue="<%= true %>" model="<%= WorkflowTask.class %>" name="dueDate" required="<%= true %>" />

			<aui:input cols="55" cssClass="task-content-comment" name="comment" placeholder="comment" rows="1" type="textarea" />
		</div>

		<div class="modal-footer">
			<div class="btn-group">
				<div class="btn-group-item">
					<aui:button name="close" type="cancel" />
				</div>

				<div class="btn-group-item">
					<aui:button name="done" primary="<%= true %>" value="done" />
				</div>
			</div>
		</div>
	</aui:form>
</div>

<aui:script use="aui-base">
	var maxLength = Liferay.AUI.getDateFormat().replace(/%[mdY]/gm, '').length + 8;

	A.all('#<portlet:namespace />dueDate').set('maxLength', maxLength);

	var done = A.one('#<portlet:namespace />done');

	if (done) {
		done.on('click', function(event) {
			var data = new FormData(
				document.querySelector('#<portlet:namespace />updateFm')
			);

			Liferay.Util.fetch('<%= updateURL.toString() %>', {
				body: data,
				method: 'POST'
			}).then(function() {
				Liferay.Util.getOpener().<portlet:namespace />refreshPortlet(
					'<%= redirect.toString() %>'
				);
				Liferay.Util.getWindow(
					'<portlet:namespace />updateDialog'
				).destroy();
			});
		});
	}
</aui:script>