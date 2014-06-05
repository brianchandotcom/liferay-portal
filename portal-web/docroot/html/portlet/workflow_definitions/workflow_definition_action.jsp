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

<%@ include file="/html/portlet/workflow_definitions/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowDefinition workflowDefinition = (WorkflowDefinition)row.getObject();
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<portlet:renderURL var="editURL">
		<portlet:param name="struts_action" value="/workflow_definitions/edit_workflow_definition" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
		<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		iconCssClass="icon-plus"
		message='<%= LanguageUtil.format(pageContext, "add-new-x", "file") %>'
		url="<%= editURL %>"
	/>

	<c:if test='<%= DeployManagerUtil.isDeployed("kaleo-designer-portlet") %>'>

		<%
		String taglibOnClick = "javascript:Liferay.Util.getOpener()." + renderResponse.getNamespace() + "openKaleoDesigner('" + HtmlUtil.escapeJS(workflowDefinition.getName()) + "', '" + workflowDefinition.getVersion() + "', '', Liferay.Util.getWindowName());";
		%>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= taglibOnClick %>"
		/>
	</c:if>

	<c:if test="<%= !workflowDefinition.isActive() %>">
		<portlet:actionURL var="restoreWorkflowDefinitionURL">
			<portlet:param name="struts_action" value="/workflow_definitions/edit_workflow_definition" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
			<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			iconCssClass="icon-ok-sign"
			message="activate"
			url="<%= restoreWorkflowDefinitionURL %>"
		/>
	</c:if>

	<portlet:actionURL var="deleteURL">
		<portlet:param name="struts_action" value="/workflow_definitions/edit_workflow_definition" />
		<portlet:param name="<%= Constants.CMD %>" value="<%= workflowDefinition.isActive() ? Constants.DEACTIVATE : Constants.DELETE %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
		<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
	</portlet:actionURL>

	<c:choose>
		<c:when test="<%= workflowDefinition.isActive() %>">
			<liferay-ui:icon-deactivate url="<%= deleteURL %>" />
		</c:when>
		<c:otherwise>
			<liferay-ui:icon-delete
				url="<%= deleteURL %>"
			/>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>