<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/definition/init.jsp" %>

<portlet:renderURL var="viewDefinitionsURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
	<portlet:param name="tab" value="<%= WorkflowWebKeys.WORKFLOW_TAB_DEFINITION %>" />
	<portlet:param name="tabs1" value="workflow-definitions" />
</portlet:renderURL>

<portlet:renderURL var="addWorkflowDefinitionURL">
	<portlet:param name="mvcPath" value="/definition/edit_workflow_definition.jsp" />
	<portlet:param name="tabs1" value="workflow-definitions" />
	<portlet:param name="redirect" value="<%= viewDefinitionsURL %>" />
	<portlet:param name="backURL" value="<%= viewDefinitionsURL %>" />
</portlet:renderURL>

<liferay-ui:icon
	icon="plus"
	label="<%= false %>"
	linkCssClass="btn btn-monospaced btn-primary"
	markupView="lexicon"
	message="new-workflow"
	toolTip="<%= true %>"
	url="<%= addWorkflowDefinitionURL %>"
/>