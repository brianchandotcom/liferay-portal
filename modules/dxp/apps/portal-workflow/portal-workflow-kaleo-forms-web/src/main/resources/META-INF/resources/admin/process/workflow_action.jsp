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

<%@ include file="/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WorkflowDefinition workflowDefinition = (WorkflowDefinition)row.getObject();

String backURL = (String)row.getParameter("backURL");
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<liferay-portlet:renderURL portletName="<%= KaleoDesignerPortletKeys.KALEO_DESIGNER %>" var="editURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/designer/edit_kaleo_definition_version.jsp" />
		<portlet:param name="closeRedirect" value="<%= backURL %>" />
		<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
		<portlet:param name="draftVersion" value="<%= String.valueOf(workflowDefinition.getVersion()) + StringPool.PERIOD + '0' %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		onClick='<%= "javascript:" + renderResponse.getNamespace() + "editWorkflow('" + editURL + "');" %>'
		url="javascript:;"
	/>

	<liferay-ui:icon
		message="choose"
		onClick='<%= "Liferay.fire('" + renderResponse.getNamespace() + "chooseWorkflow', {name: '" + HtmlUtil.escapeJS(workflowDefinition.getName()) + "', title: '" + HtmlUtil.escapeJS(workflowDefinition.getTitle(themeDisplay.getLanguageId())) + "', version: '" + workflowDefinition.getVersion() + "'});" %>'
		url="javascript:;"
	/>

	<portlet:actionURL name="deactivateWorkflowDefinition" var="deactivateWorkflowDefinition">
		<portlet:param name="redirect" value="<%= backURL %>" />
		<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
		<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-deactivate
		url="<%= deactivateWorkflowDefinition %>"
	/>
</liferay-ui:icon-menu>