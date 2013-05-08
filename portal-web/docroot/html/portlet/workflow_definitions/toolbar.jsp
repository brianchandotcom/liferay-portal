<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all");
%>

<aui:nav-bar>
	<aui:nav>
		<portlet:renderURL var="viewDefinitionsURL">
			<portlet:param name="struts_action" value="/workflow_definitions/view" />
			<portlet:param name="tabs1" value="workflow-definitions" />
		</portlet:renderURL>

		<aui:nav-item href="<%= viewDefinitionsURL %>" label="view-all" selected='<%= toolbarItem.equals("view-all") %>' />

		<portlet:renderURL var="addWorkflowDefinitionURL">
			<portlet:param name="struts_action" value="/workflow_definitions/edit_workflow_definition" />
			<portlet:param name="tabs1" value="workflow-definitions" />
			<portlet:param name="redirect" value="<%= viewDefinitionsURL %>" />
			<portlet:param name="backURL" value="<%= viewDefinitionsURL %>" />
		</portlet:renderURL>

		<c:if test='<%= DeployManagerUtil.isDeployed("kaleo-designer-portlet") %>'>
			<aui:nav-item href='<%= "javascript:Liferay.Util.getOpener()." + renderResponse.getNamespace() + "openKaleoDesigner(\'\', \'0\', \'\', Liferay.Util.getWindowName());" %>' iconClass="icon-plus" label='<%= LanguageUtil.format(pageContext, "add-new-x", "definition") %>' />
		</c:if>
		<aui:nav-item href="<%= addWorkflowDefinitionURL %>" iconClass="icon-upload" label="file-upload" selected='<%= toolbarItem.equals("add") %>' />
	</aui:nav>
</aui:nav-bar>

<c:if test='<%= DeployManagerUtil.isDeployed("kaleo-designer-portlet") %>'>
	<aui:script>
		Liferay.provide(
			window,
			'<portlet:namespace />openKaleoDesigner',
			function(workflowDefinitionName, workflowDefinitionVersion, saveCallback, openerWindowName) {
				Liferay.Util.openKaleoDesignerPortlet(
					{
						availablePropertyModels: 'Liferay.KaleoDesigner.AVAILABLE_PROPERTY_MODELS.KALEO_FORMS_EDIT',
						name: workflowDefinitionName,
						openerWindowName: openerWindowName,
						portletResourceNamespace: '<%= renderResponse.getNamespace() %>',
						saveCallback: saveCallback,
						version: workflowDefinitionVersion,
						versionLabel: '<liferay-ui:message key="version" />'
					}
				);
			},
			['aui-base']
		);
	</aui:script>
</c:if>