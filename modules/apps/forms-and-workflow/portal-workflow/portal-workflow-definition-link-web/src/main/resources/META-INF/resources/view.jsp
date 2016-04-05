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
WorkflowDefinitionLinkSearch workflowDefinitionLinkSearchContainer = workflowDefinitionLinkDisplayContext.getSearchContainer();
%>

<liferay-util:include page="/search_bar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/management_bar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280 workflow-definition-link-container" id="<portlet:namespace />Container">
	<liferay-ui:search-container
		id="searchContainer"
		searchContainer="<%= workflowDefinitionLinkSearchContainer %>"
	>

		<liferay-ui:search-container-row
			className="com.liferay.portal.workflow.definition.link.web.search.WorkflowDefinitionLinkSearchEntry"
			modelVar="workflowDefinitionLinkSearchEntry"
		>

			<liferay-ui:search-container-row-parameter
				name="workflowDefinitionLinkSearchEntry"
				value="<%= workflowDefinitionLinkSearchEntry %>"
			/>

			<liferay-ui:search-container-column-text
				name="resource"
				value="<%= workflowDefinitionLinkSearchEntry.getResource() %>"
			/>

			<liferay-ui:search-container-column-text
				name="workflow"
				value="<%= workflowDefinitionLinkSearchEntry.getWorkflowDefinitionLabel() %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				cssClass="entry-action"
				path="/workflow_definition_link_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
	</liferay-ui:search-container>
</div>