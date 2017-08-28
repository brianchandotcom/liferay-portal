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

<%
String redirect = ParamUtil.getString(request, "redirect");

WorkflowDefinition workflowDefinition = (WorkflowDefinition)request.getAttribute(WebKeys.WORKFLOW_DEFINITION);

String content = workflowDefinition.getContent();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(workflowDefinition.getName());
%>

<liferay-portlet:renderURL var="editWorkflowDefinitionURL">
	<portlet:param name="mvcPath" value="/definition/edit_workflow_definition.jsp" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="name" value="<%= workflowDefinition.getName() %>" />
	<portlet:param name="version" value="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
</liferay-portlet:renderURL>

<div class="container-fluid-1280">
	<aui:model-context bean="<%= workflowDefinition %>" model="<%= WorkflowDefinition.class %>" />

	<div class="panel text-center">
		<aui:workflow-status markupView="lexicon" model="<%= WorkflowDefinition.class %>" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= WorkflowConstants.STATUS_APPROVED %>" version="<%= String.valueOf(workflowDefinition.getVersion()) %>" />
	</div>

	<aui:input name="content" type="hidden" value="<%= content %>" />

	<div class="card-horizontal">
		<div class="card-row-padded">
			<aui:fieldset>
				<aui:col>
					<aui:field-wrapper label="title">
						<liferay-ui:input-localized disabled="<%= true %>" name="title" xml='<%= BeanPropertiesUtil.getString(workflowDefinition, "title") %>' />
					</aui:field-wrapper>
				</aui:col>

				<aui:col id="contentSourceWrapper">
					<div class="content-source" id="<portlet:namespace />contentEditor"></div>
				</aui:col>
			</aui:fieldset>
		</div>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" href="<%= editWorkflowDefinitionURL %>" primary="<%= true %>" value='<%= LanguageUtil.get(request, "edit") %>' />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</div>

<aui:script use="aui-ace-editor,liferay-xml-formatter">
	var STR_VALUE = 'value';

	var contentEditor = new A.AceEditor(
		{
			boundingBox: '#<portlet:namespace />contentEditor',
			height: 600,
			mode: 'xml',
			readOnly: 'true',
			tabSize: 4,
			width: 600
		}
	).render();

	var editorContentElement = A.one('#<portlet:namespace />content');

	if (editorContentElement) {
		contentEditor.set(STR_VALUE, editorContentElement.val());
	}

	contentEditor.set('width', A.one('#<portlet:namespace />contentSourceWrapper').get('clientWidth'));
</aui:script>