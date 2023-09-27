<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/definition/init.jsp" %>

<%
WorkflowDefinition currentWorkflowDefinition = (WorkflowDefinition)request.getAttribute(WebKeys.WORKFLOW_DEFINITION);
%>

<liferay-ui:search-container
	cssClass="lfr-sidebar-list-group-workflow sidebar-list-group"
	id="workflowDefinitions"
>
	<liferay-ui:search-container-results
		results="<%= workflowDefinitionDisplayContext.getWorkflowDefinitionsOrderByDesc(currentWorkflowDefinition.getName()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.workflow.WorkflowDefinition"
		modelVar="workflowDefinition"
	>
		<liferay-ui:search-container-column-jsp
			cssClass="autofit-col-expand"
			path="/definition/workflow_definition_version_info.jsp"
		/>

		<liferay-ui:search-container-column-jsp
			path="/definition/workflow_definition_version_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="descriptive"
		markupView="lexicon"
	/>
</liferay-ui:search-container>