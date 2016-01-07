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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");

WorkflowDefinitionSearch workflowDefinitionSearch = new WorkflowDefinitionSearch(renderRequest, portletURL);
%>

<liferay-ui:error exception="<%= RequiredWorkflowDefinitionException.class %>" message="you-cannot-deactivate-or-delete-this-definition" />

<liferay-util:include page="/add_button.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/search_bar.jsp" servletContext="<%= application %>" />

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-util:include page="/sort_buttons.jsp" servletContext="<%= application %>" />
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280 workflow-definition-container">
	<liferay-ui:search-container
		emptyResultsMessage="no-workflow-definitions-are-defined"
		id="searchContainer"
		searchContainer="<%= workflowDefinitionSearch %>"
	>

		<%
		request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);
		%>

		<liferay-ui:search-container-results
			results="<%= workflowDefinitionDisplayContext.getSearchContainerResults(searchContainer) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.workflow.WorkflowDefinition"
			modelVar="workflowDefinition"
		>

			<liferay-ui:search-container-column-text
				name="name"
				value="<%= workflowDefinitionDisplayContext.getName(workflowDefinition) %>"
			/>

			<liferay-ui:search-container-column-text
				name="title"
				value="<%= workflowDefinitionDisplayContext.getTitle(workflowDefinition) %>"
			/>

			<liferay-ui:search-container-column-text
				name="version"
				value="<%= workflowDefinitionDisplayContext.getVersion(workflowDefinition) %>"
			/>

			<liferay-ui:search-container-column-text
				name="active"
				value="<%= workflowDefinitionDisplayContext.getActive(workflowDefinition) %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				cssClass="entry-action"
				path="/workflow_definition_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
	</liferay-ui:search-container>
</div>