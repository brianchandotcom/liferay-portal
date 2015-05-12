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
List<WorkflowDefinition> workflowDefinitions = (List<WorkflowDefinition>)request.getAttribute(WebKeys.WORKFLOW_DEFINITIONS);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
%>

<liferay-ui:error exception="<%= RequiredWorkflowDefinitionException.class %>" message="you-cannot-deactivate-or-delete-this-definition" />

<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>" />

<liferay-ui:search-container
	emptyResultsMessage="no-workflow-definitions-are-defined"
	iteratorURL="<%= portletURL %>"
	total="<%= workflowDefinitions.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= ListUtil.subList(workflowDefinitions, searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.workflow.WorkflowDefinition"
		modelVar="workflowDefinition"
	>

		<liferay-ui:search-container-column-text
			name="name"
			value="<%= HtmlUtil.escape(workflowDefinition.getName()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="title"
			value="<%= HtmlUtil.escape(workflowDefinition.getTitle(themeDisplay.getLanguageId())) %>"
		/>

		<liferay-ui:search-container-column-text
			name="version"
			value="<%= String.valueOf(workflowDefinition.getVersion()) %>"
		/>

		<liferay-ui:search-container-column-text
			name="active"
			value='<%= workflowDefinition.isActive() ? LanguageUtil.get(locale, "yes") : LanguageUtil.get(locale, "no") %>'
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			cssClass="entry-action"
			path="/workflow_definition_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>