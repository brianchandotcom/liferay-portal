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

<%@ include file="/definition_link/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

String randomNamespace = (String)row.getParameter("randomNamespace");

WorkflowDefinitionLinkSearchEntry workflowDefinitionLinkSearchEntry = (WorkflowDefinitionLinkSearchEntry)row.getObject();

String className = workflowDefinitionLinkSearchEntry.getClassName();
String resource = workflowDefinitionLinkSearchEntry.getResource();
%>

<portlet:actionURL name="updateWorkflowDefinitionLink" var="updateWorkflowDefinitionLinkURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<div hidden="true" id="<%= randomNamespace %>formContainer">
	<aui:form action="<%= updateWorkflowDefinitionLinkURL %>" cssClass="workflow-definition-form" method="post">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="namespace" type="hidden" value="<%= randomNamespace %>" />
		<aui:input name="groupId" type="hidden" value="<%= workflowDefinitionLinkDisplayContext.getGroupId() %>" />
		<aui:input name="resource" type="hidden" value="<%= resource %>" />
		<aui:input name="editMode" type="hidden" value="false" />

		<%
		String workflowAssignedValue = "";
		%>

		<aui:select cssClass="form-control-sm workflow-definition-form" label="<%= StringPool.BLANK %>" name='<%= "workflowDefinitionName@" + className %>' title="workflow-definition">

			<%
			WorkflowDefinition defaultWorkflowDefinition = workflowDefinitionLinkDisplayContext.fetchDefaultWorkflowDefinition(className);
			String defaultWorkflowDefinitionLabel = workflowDefinitionLinkDisplayContext.getDefaultWorkflowDefinitionLabel(className);
			%>

			<aui:option><%= HtmlUtil.escape(defaultWorkflowDefinitionLabel) %></aui:option>

			<%
			for (WorkflowDefinition workflowDefinition : workflowDefinitionLinkDisplayContext.getWorkflowDefinitions()) {
				if (!workflowDefinitionLinkDisplayContext.isControlPanelPortlet() && workflowDefinitionLinkDisplayContext.isWorkflowDefinitionEquals(workflowDefinition, defaultWorkflowDefinition)) {
					continue;
				}

				boolean selected = workflowDefinitionLinkDisplayContext.isWorkflowDefinitionSelected(workflowDefinition, className);

				String value = workflowDefinitionLinkDisplayContext.getWorkflowDefinitionValue(workflowDefinition);

				if (selected) {
					workflowAssignedValue = value;
				}
			%>

				<aui:option label="<%= HtmlUtil.escape(workflowDefinitionLinkDisplayContext.getWorkflowDefinitionLabel(workflowDefinition)) %>" selected="<%= selected %>" value="<%= value %>" />

			<%
			}
			%>

		</aui:select>

		<aui:input name="workflowAssignedValue" type="hidden" value="<%= workflowAssignedValue %>" />
	</aui:form>
</div>

<div id="<%= randomNamespace %>definitionLabel">
	<%= HtmlUtil.escape(workflowDefinitionLinkSearchEntry.getWorkflowDefinitionLabel()) %>
</div>