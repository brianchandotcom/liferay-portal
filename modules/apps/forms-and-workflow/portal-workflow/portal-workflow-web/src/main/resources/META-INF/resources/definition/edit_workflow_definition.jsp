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

String name = BeanParamUtil.getString(workflowDefinition, request, "name");
int version = BeanParamUtil.getInteger(workflowDefinition, request, "version");
String content = BeanParamUtil.getString(workflowDefinition, request, "content");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((workflowDefinition == null) ? LanguageUtil.get(request, (workflowDefinition == null) ? "new-definition" : workflowDefinition.getName()) : workflowDefinition.getName());
%>

<liferay-portlet:actionURL name='<%= (workflowDefinition == null) ? "addWorkflowDefinition" : "updateWorkflowDefinition" %>' var="editWorkflowDefinitionURL">
	<portlet:param name="mvcPath" value="/definition/edit_workflow_definition.jsp" />
</liferay-portlet:actionURL>

<c:if test="<%= workflowDefinition != null %>">
	<liferay-frontend:management-bar>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
			/>
		</liferay-frontend:management-bar-buttons>
	</liferay-frontend:management-bar>
</c:if>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<c:if test="<%= workflowDefinition != null %>">
		<div class="sidenav-menu-slider">
			<div class="sidebar sidebar-default sidenav-menu">
				<div class="sidebar-header">
					<aui:icon cssClass="icon-monospaced sidenav-close text-default visible-xs-inline-block" image="times" markupView="lexicon" url="javascript:;" />
				</div>

				<liferay-ui:tabs cssClass="navbar-no-collapse" names="details,versions" refresh="<%= false %>" type="dropdown">
					<liferay-ui:section>
						<div class="sidebar-body">
							<h3 class="version">
								<liferay-ui:message key="version" /> <%= workflowDefinition.getVersion() %>
							</h3>

							<div>
								<aui:model-context bean="<%= workflowDefinition %>" model="<%= WorkflowDefinition.class %>" />

								<aui:workflow-status model="<%= WorkflowDefinition.class %>" status="<%= WorkflowConstants.STATUS_APPROVED %>" />
							</div>
						</div>
					</liferay-ui:section>

					<liferay-ui:section>
						<div class="sidebar-body workflow-definition-sidebar">
							<liferay-util:include page="/definition/view_workflow_definition_history.jsp" servletContext="<%= application %>">
								<liferay-util:param name="redirect" value="<%= redirect %>" />
							</liferay-util:include>
						</div>
					</liferay-ui:section>
				</liferay-ui:tabs>
			</div>
		</div>
	</c:if>

	<div class="sidenav-content">
		<aui:form action="<%= editWorkflowDefinitionURL %>" method="post" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="name" type="hidden" value="<%= name %>" />
			<aui:input name="version" type="hidden" value="<%= version %>" />
			<aui:input name="content" type="hidden" value="<%= content %>" />

			<div class="card-horizontal">
				<div class="card-row-padded">
					<liferay-ui:error exception="<%= RequiredWorkflowDefinitionException.class %>" message="you-cannot-deactivate-or-delete-this-definition" />
					<liferay-ui:error exception="<%= WorkflowDefinitionFileException.class %>" message="please-enter-a-valid-definition" />

					<aui:fieldset>
						<aui:col>
							<aui:field-wrapper label="title">
								<liferay-ui:input-localized name="title" xml='<%= BeanPropertiesUtil.getString(workflowDefinition, "title") %>' />
							</aui:field-wrapper>
						</aui:col>

						<aui:col id="contentSourceWrapper">
							<div class="content-source" id="<portlet:namespace />contentEditor"></div>
						</aui:col>

						<aui:col>
							<aui:input inlineLabel="left" label="file" name="definition" type="file" />
						</aui:col>
					</aui:fieldset>
				</div>
			</div>

			<aui:button-row>

				<%
				String taglibOnClick = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveDefinition');";
				%>

				<aui:button cssClass="btn-lg" onClick="<%= taglibOnClick %>" primary="<%= true %>" value='<%= LanguageUtil.get(request, "save") %>' />

				<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
			</aui:button-row>
		</aui:form>
	</div>
</div>

<aui:script use="aui-ace-editor,liferay-xml-formatter">
	var STR_VALUE = 'value';

	var contentEditor = new A.AceEditor(
		{
			boundingBox: '#<portlet:namespace />contentEditor',
			height: 600,
			mode: 'xml',
			tabSize: 4,
			width: 600
		}
	).render();

	var editorContentElement = A.one('#<portlet:namespace />content');

	if (editorContentElement) {
		contentEditor.set(STR_VALUE, editorContentElement.val());
	}

	contentEditor.set('width', A.one('#<portlet:namespace />contentSourceWrapper').get('clientWidth'));

	var sidenavSlider = $('#<portlet:namespace />infoPanelId');

	sidenavSlider.on(
		'closed.lexicon.sidenav',
		function(event) {
			contentEditor.set('width', A.one('#<portlet:namespace />contentSourceWrapper').get('clientWidth'));
		}
	);

	sidenavSlider.on(
		'open.lexicon.sidenav',
		function(event) {
			contentEditor.set('width', A.one('#<portlet:namespace />contentSourceWrapper').get('clientWidth'));
		}
	);

	var definitionFile = $('#<portlet:namespace />definition');

	definitionFile.on(
		'change',
		function(evt) {
			var files = evt.target.files;

			if (files) {
				var reader = new FileReader();

				reader.onloadend = function(evt) {

					if (evt.target.readyState == FileReader.DONE) {
						contentEditor.set(STR_VALUE, evt.target.result);
					}
				};

				reader.readAsText(files[0]);
			}
		}
	);

	Liferay.on(
		'<portlet:namespace />saveDefinition',
		function(event) {
			var form = AUI.$('#<portlet:namespace />fm');

			form.fm('content').val(contentEditor.get(STR_VALUE));

			submitForm(form);
		}
	);
</aui:script>