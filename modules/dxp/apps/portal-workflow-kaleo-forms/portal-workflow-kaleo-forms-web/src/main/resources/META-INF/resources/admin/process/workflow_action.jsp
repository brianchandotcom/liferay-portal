<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	<c:if test="<%= permissionChecker.isCompanyAdmin() %>">
		<liferay-portlet:renderURL portletName="<%= KaleoDesignerPortletKeys.KALEO_DESIGNER %>" var="editURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/designer/edit_kaleo_definition_version.jsp" />
			<portlet:param name="tabs1" value="published" />
			<portlet:param name="closeRedirect" value="<%= backURL %>" />
			<portlet:param name="historyKey" value="workflow" />
			<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
			<portlet:param name="draftVersion" value="<%= String.valueOf(workflowDefinition.getVersion()) + StringPool.PERIOD + '0' %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			onClick='<%= "javascript:" + liferayPortletResponse.getNamespace() + "editWorkflow('" + editURL + "');" %>'
			url="javascript:void(0);"
		/>
	</c:if>

	<liferay-ui:icon
		message="choose"
		onClick='<%= "Liferay.fire('" + liferayPortletResponse.getNamespace() + "chooseWorkflow', {name: '" + HtmlUtil.escapeJS(workflowDefinition.getName()) + "', title: '" + HtmlUtil.escapeJS(workflowDefinition.getTitle(themeDisplay.getLanguageId())) + "', version: '" + workflowDefinition.getVersion() + "'});" %>'
		url="javascript:void(0);"
	/>

	<c:if test="<%= permissionChecker.isCompanyAdmin() %>">
		<portlet:actionURL name="/kaleo_forms_admin/deactivate_workflow_definition" var="deactivateWorkflowDefinition">
			<portlet:param name="tabs1" value="published" />
			<portlet:param name="redirect" value="<%= backURL %>" />
			<portlet:param name="historyKey" value="workflow" />
			<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
			<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="unpublish"
			url="<%= deactivateWorkflowDefinition %>"
		/>
	</c:if>
</liferay-ui:icon-menu>