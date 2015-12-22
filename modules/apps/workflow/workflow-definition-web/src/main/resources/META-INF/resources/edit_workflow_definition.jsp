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

WorkflowDefinition workflowDefinition = (WorkflowDefinition)request.getAttribute(WebKeys.WORKFLOW_DEFINITION);

String name = StringPool.BLANK;
String version = StringPool.BLANK;

if (workflowDefinition != null) {
	name = workflowDefinition.getName();
	version = String.valueOf(workflowDefinition.getVersion());
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((workflowDefinition == null) ? LanguageUtil.get(request, (workflowDefinition == null) ? "upload-definition" : workflowDefinition.getName()) : workflowDefinition.getName());
%>

<liferay-portlet:actionURL name='<%= (workflowDefinition == null) ? "addWorkflowDefinition" : "updateWorkflowDefinition" %>' var="editWorkflowDefinitionURL">
	<portlet:param name="mvcPath" value="/edit_workflow_definition.jsp" />
</liferay-portlet:actionURL>

<div class="container-fluid-1280 workflow-definition-container">
	<aui:form action="<%= editWorkflowDefinitionURL %>" enctype="multipart/form-data" method="post">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="name" type="hidden" value="<%= name %>" />
		<aui:input name="version" type="hidden" value="<%= version %>" />

		<liferay-ui:error exception="<%= WorkflowDefinitionFileException.class %>" message="please-enter-a-valid-file" />

		<aui:fieldset>
			<aui:field-wrapper label="title">
				<liferay-ui:input-localized name="title" xml='<%= BeanPropertiesUtil.getString(workflowDefinition, "title") %>' />
			</aui:field-wrapper>

			<aui:input name="file" type="file" />

			<aui:button-row>
				<aui:button cssClass="btn-lg" type="submit" />

				<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</aui:fieldset>
	</aui:form>
</div>