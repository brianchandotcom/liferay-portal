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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowDefinition workflowDefinition = (WorkflowDefinition)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<portlet:renderURL var="viewURL">
		<portlet:param name="mvcPath" value="/definition/view_workflow_definition.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
		<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view"
		url="<%= viewURL %>"
	/>

	<portlet:renderURL var="editURL">
		<portlet:param name="mvcPath" value="/definition/edit_workflow_definition.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
		<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<c:if test="<%= !workflowDefinition.isActive() %>">
		<liferay-portlet:actionURL name="restoreWorkflowDefinition" var="restoreWorkflowDefinitionURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
			<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon
			message="publish"
			url="<%= restoreWorkflowDefinitionURL %>"
		/>
	</c:if>

	<c:choose>
		<c:when test="<%= workflowDefinition.isActive() %>">
			<liferay-portlet:actionURL name="deactivateWorkflowDefinition" var="deactivateWorkflowDefinitionURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
				<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				message="unpublish"
				url="<%= deactivateWorkflowDefinitionURL %>"
			/>
		</c:when>
		<c:otherwise>
			<liferay-portlet:actionURL name="deleteWorkflowDefinition" var="deleteWorkflowDefinitionURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
				<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				message="delete"
				onClick='<%= renderResponse.getNamespace() + "confirmDeleteDefinition('" + deleteWorkflowDefinitionURL + "'); return false;" %>'
				url="<%= deleteWorkflowDefinitionURL %>"
			/>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>