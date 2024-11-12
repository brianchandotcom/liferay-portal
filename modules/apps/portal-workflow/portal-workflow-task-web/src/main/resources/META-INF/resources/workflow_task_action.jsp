<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String randomId = workflowTaskDisplayContext.getWorkflowTaskRandomId();

WorkflowTask workflowTask = workflowTaskDisplayContext.getWorkflowTask();
%>

<div class="c-mr-4 lfr-asset-actions">
	<clay:dropdown-actions
		aria-label='<%= LanguageUtil.get(request, "open-actions-menu") %>'
		borderless="<%= true %>"
		dropdownItems="<%= workflowTaskDisplayContext.getActionDropdownItems(workflowTask) %>"
		monospaced="<%= true %>"
		propsTransformer="{WorkFlowTaskActionDropdownPropsTransformer} from portal-workflow-task-web"
		small="<%= true %>"
	/>
</div>

<aui:form name='<%= randomId + "hiddenForm" %>'>
	<div class="hide" id="<%= randomId %>updateComments">
		<aui:input cols="55" cssClass="task-content-comment" name="comment" placeholder="comment" rows="1" type="textarea" />
	</div>
</aui:form>

<c:if test="<%= !workflowTask.isCompleted() && workflowTaskDisplayContext.isAssignedToUser(workflowTask) %>">
	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"randomId", randomId
			).put(
				"workflowTasks", workflowTaskDisplayContext.getTransitionNames(workflowTask)
			).build()
		%>'
		module="{WorkflowTaskAction} from portal-workflow-task-web"
	/>
</c:if>

<aui:script>
	function <portlet:namespace />refreshPortlet(uri) {
		location.href = uri;
	}
</aui:script>