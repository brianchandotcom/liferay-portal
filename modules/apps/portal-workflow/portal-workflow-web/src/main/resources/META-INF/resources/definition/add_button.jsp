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

<%
List<AddMenuItem> addMenuItems = new ArrayList<>();

addMenuItems.add(new AddMenuItem(HtmlUtil.escape(LanguageUtil.get(request, "new-workflow")), addWorkflowDefinitionURL.toString()));
%>

<c:if test="<%= !addMenuItems.isEmpty() %>">
	<liferay-frontend:add-menu
		addMenuItems="<%= addMenuItems %>"
		inline="<%= true %>"
	/>
</c:if>