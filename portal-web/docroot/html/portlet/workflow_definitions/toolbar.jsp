<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<div class="lfr-portlet-toolbar">
	<portlet:renderURL var="viewDefinitionsURL">
		<portlet:param name="struts_action" value="/workflow_definitions/view" />
		<portlet:param name="tabs1" value="workflow-definitions" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-all") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewDefinitionsURL %>"><liferay-ui:message key="view-all" /></a>
	</span>

	<portlet:renderURL var="addWorkflowDefinitionURL">
		<portlet:param name="struts_action" value="/workflow_definitions/edit_workflow_definition" />
		<portlet:param name="tabs1" value="workflow-definitions" />
		<portlet:param name="redirect" value="<%= viewDefinitionsURL %>" />
		<portlet:param name="backURL" value="<%= viewDefinitionsURL %>" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button add-button <%= toolbarItem.equals("add") ? "current" : StringPool.BLANK %>">
		<a href="<%= addWorkflowDefinitionURL %>"><liferay-ui:message key="add" /></a>
	</span>
</div>