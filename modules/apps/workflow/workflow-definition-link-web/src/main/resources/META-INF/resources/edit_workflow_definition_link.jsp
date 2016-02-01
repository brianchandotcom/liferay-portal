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
String redirect = ParamUtil.getString(request, "redirect");

String className = ParamUtil.getString(request, "className");
String resource = ParamUtil.getString(request, "resource");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(resource);
%>

<portlet:actionURL name="updateWorkflowDefinitionLink" var="updateWorkflowDefinitionLinkURL" />

<div class="container-fluid-1280 workflow-definition-link-container" id="<portlet:namespace />formContainer">
	<aui:form action="<%= updateWorkflowDefinitionLinkURL %>" method="post">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden" value="<%= workflowDefinitionLinkDisplayContext.getGroupId() %>" />
		<aui:input name="resource" type="hidden" value="<%= resource %>" />

		<div class="col-xs-4">
			<aui:select label="<%= resource %>" name='<%= "workflowDefinitionName@" + className %>' title="workflow-definition">

				<%
				String defaultWorkflowDefinitionLabel = workflowDefinitionLinkDisplayContext.getDefaultWorkflowDefinitionLabel(className);
				%>

				<aui:option><%= defaultWorkflowDefinitionLabel %></aui:option>

				<%
				for (WorkflowDefinition workflowDefinition : workflowDefinitionLinkDisplayContext.getWorkflowDefinitions()) {
				%>

					<aui:option
						label="<%= workflowDefinitionLinkDisplayContext.getWorkflowDefinitionLabel(workflowDefinition) %>"
						selected="<%= workflowDefinitionLinkDisplayContext.isWorkflowDefinitionSelected(workflowDefinition, className) %>"
						value="<%= workflowDefinitionLinkDisplayContext.getWorkflowDefinitionValue(workflowDefinition) %>"
					/>

				<%
				}
				%>

			</aui:select>
		</div>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>