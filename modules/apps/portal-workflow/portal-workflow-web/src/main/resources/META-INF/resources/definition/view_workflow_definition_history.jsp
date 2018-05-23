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