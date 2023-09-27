<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/definition/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WorkflowDefinition currentWorkflowDefinition = (WorkflowDefinition)request.getAttribute(WebKeys.WORKFLOW_DEFINITION);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowDefinition workflowDefinition = (WorkflowDefinition)row.getObject();
%>

<portlet:renderURL var="viewURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/definition/view_workflow_definition.jsp" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
	<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
	<portlet:param name="<%= WorkflowWebKeys.WORKFLOW_JSP_STATE %>" value="previewBeforeRevert" />
</portlet:renderURL>

<liferay-portlet:actionURL name="/portal_workflow/revert_workflow_definition" var="revertWorkflowDefinitionURL">
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="name" value="<%= currentWorkflowDefinition.getName() %>" />
	<portlet:param name="version" value="<%= String.valueOf(currentWorkflowDefinition.getVersion()) %>" />
	<portlet:param name="previousName" value="<%= workflowDefinition.getName() %>" />
	<portlet:param name="previousVersion" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
</liferay-portlet:actionURL>

<c:if test="<%= currentWorkflowDefinition.getVersion() != workflowDefinition.getVersion() %>">
	<liferay-ui:icon-menu
		direction="left-side"
		icon="<%= StringPool.BLANK %>"
		id='<%= "iconMenu_" + String.valueOf(workflowDefinition.getVersion()) %>'
		markupView="lexicon"
		message="<%= StringPool.BLANK %>"
		showWhenSingleIcon="<%= true %>"
	>
		<liferay-ui:icon
			id='<%= "previewBeforeRevert" + String.valueOf(workflowDefinition.getVersion()) %>'
			message="preview"
			url="javascript:void(0);"
		/>

		<liferay-ui:icon
			message="restore"
			url="<%= revertWorkflowDefinitionURL %>"
		/>
	</liferay-ui:icon-menu>
</c:if>

<aui:script use="liferay-workflow-web">
	var title =
		'<liferay-ui:message arguments="<%= new String[] {dateFormatTime.format(workflowDefinition.getModifiedDate()), HtmlUtil.escape(workflowDefinitionDisplayContext.getUserName(workflowDefinition))} %>" key="preview" translateArguments="<%= false %>" />';

	var previewBeforeRevertDialog = A.rbind(
		'previewBeforeRevertDialog',
		Liferay.WorkflowWeb,
		'<%= viewURL %>',
		'<%= revertWorkflowDefinitionURL %>',
		title
	);

	Liferay.delegateClick(
		'<portlet:namespace />previewBeforeRevert<%= String.valueOf(workflowDefinition.getVersion()) %>',
		previewBeforeRevertDialog
	);
</aui:script>