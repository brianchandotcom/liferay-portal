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
String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

String redirect = ParamUtil.getString(request, "redirect");

WorkflowDefinition workflowDefinition = (WorkflowDefinition)request.getAttribute(WebKeys.WORKFLOW_DEFINITION);

String name = BeanParamUtil.getString(workflowDefinition, request, "name");
int version = BeanParamUtil.getInteger(workflowDefinition, request, "version");
String content = BeanParamUtil.getString(workflowDefinition, request, "content");
boolean active = BeanParamUtil.getBoolean(workflowDefinition, request, "active");

String duplicateTitle = workflowDefinitionDisplayContext.getDuplicateTitle(workflowDefinition);

portletDisplay.setShowBackIcon(true);

PortletURL portletURL = PortalUtil.getControlPanelPortletURL(renderRequest, WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW, PortletRequest.RENDER_PHASE);

portletURL.setParameter("mvcPath", "/view.jsp");

portletDisplay.setURLBack(portletURL.toString());

renderResponse.setTitle((workflowDefinition == null) ? LanguageUtil.get(request, "new-workflow") : workflowDefinition.getTitle(LanguageUtil.getLanguageId(request)));
%>

<liferay-portlet:actionURL name="deployWorkflowDefinition" var="deployWorkflowDefinitionURL">
	<portlet:param name="mvcPath" value="/definition/edit_workflow_definition.jsp" />
</liferay-portlet:actionURL>

<liferay-portlet:actionURL name="duplicateWorkflowDefinition" var="duplicateWorkflowDefinition">
	<portlet:param name="mvcPath" value="/definition/edit_workflow_definition.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</liferay-portlet:actionURL>

<liferay-portlet:actionURL name="saveWorkflowDefinition" var="saveWorkflowDefinitionURL">
	<portlet:param name="mvcPath" value="/definition/edit_workflow_definition.jsp" />
</liferay-portlet:actionURL>

<c:if test="<%= workflowDefinition != null %>">
	<liferay-frontend:info-bar>
		<div class="container-fluid-1280">
			<div class="info-bar-item">
				<c:choose>
					<c:when test="<%= active %>">
						<span class="label label-info label-lg">
							<liferay-ui:message key="published" />
						</span>
					</c:when>
					<c:otherwise>
						<span class="label label-lg label-secondary">
							<liferay-ui:message key="not-published" />
						</span>
					</c:otherwise>
				</c:choose>
			</div>

			<%
			String userName = workflowDefinitionDisplayContext.getUserName(workflowDefinition);
			%>

			<span>
				<c:choose>
					<c:when test="<%= userName == null %>">
						<%= dateFormatTime.format(workflowDefinition.getModifiedDate()) %>
					</c:when>
					<c:otherwise>
						<liferay-ui:message arguments="<%= new String[] {dateFormatTime.format(workflowDefinition.getModifiedDate()), HtmlUtil.escape(userName)} %>" key="x-by-x" translateArguments="<%= false %>" />
					</c:otherwise>
				</c:choose>
			</span>
		</div>

		<liferay-frontend:info-bar-buttons>
			<liferay-frontend:info-bar-sidenav-toggler-button
				icon="info-circle"
				label="info"
				typeMobile="relative"
			/>
		</liferay-frontend:info-bar-buttons>
	</liferay-frontend:info-bar>
</c:if>

<div class="closed sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<c:if test="<%= workflowDefinition != null %>">
		<div class="sidenav-menu-slider sidenav-visible-xs">
			<div class="sidebar sidebar-light">
				<div class="tbar-visible-xs">
					<nav class="component-tbar tbar">
						<div class="container-fluid">
							<ul class="tbar-nav">
								<li class="tbar-item">
									<aui:icon cssClass="component-action sidenav-close" image="times" markupView="lexicon" url="javascript:;" />
								</li>
							</ul>
						</div>
					</nav>
				</div>

				<div class="sidebar-header">
					<div class="autofit-row sidebar-section">
						<div class="autofit-col autofit-col-expand">
							<h4 class="component-title">
								<span class="text-truncate-inline">
									<span class="text-truncate"><%= HtmlUtil.escape(workflowDefinition.getTitle(LanguageUtil.getLanguageId(request))) %></span>
								</span>
							</h4>
						</div>
					</div>
				</div>

				<div class="sidebar-body">
					<liferay-ui:tabs
						cssClass="navigation-bar component-navigation-bar navbar-no-collapse"
						names="details,revision-history"
						refresh="<%= false %>"
					>
						<liferay-ui:section>
							<div style="margin-top:1.5rem;">

								<%
								String creatorUserName = workflowDefinitionDisplayContext.getCreatorUserName(workflowDefinition);
								String userName = workflowDefinitionDisplayContext.getUserName(workflowDefinition);
								%>

								<dl class="sidebar-dl sidebar-section">
									<dt class="sidebar-dt">
										<liferay-ui:message key="created" />
									</dt>
									<dd class="sidebar-dd">
										<c:choose>
											<c:when test="<%= creatorUserName == null %>">
												<%= dateFormatTime.format(workflowDefinitionDisplayContext.getCreatedDate(workflowDefinition)) %>
											</c:when>
											<c:otherwise>
												<liferay-ui:message arguments="<%= new String[] {dateFormatTime.format(workflowDefinitionDisplayContext.getCreatedDate(workflowDefinition)), creatorUserName} %>" key="x-by-x" translateArguments="<%= false %>" />
											</c:otherwise>
										</c:choose>
									</dd>
									<dt class="sidebar-dt">
										<liferay-ui:message key="last-modified" />
									</dt>
									<dd class="sidebar-dd">
										<c:choose>
											<c:when test="<%= userName == null %>">
												<%= dateFormatTime.format(workflowDefinition.getModifiedDate()) %>
											</c:when>
											<c:otherwise>
												<liferay-ui:message arguments="<%= new String[] {dateFormatTime.format(workflowDefinition.getModifiedDate()), userName} %>" key="x-by-x" translateArguments="<%= false %>" />
											</c:otherwise>
										</c:choose>
									</dd>
									<dt class="sidebar-dt">
										<liferay-ui:message key="total-modifications" />
									</dt>
									<dd class="sidebar-dd">
										<liferay-ui:message arguments='<%= new String[] {workflowDefinitionDisplayContext.getWorkflowDefinitionCount(workflowDefinition) + ""} %>' key="x-revisions" translateArguments="<%= false %>" />
									</dd>
									<dt class="sidebar-dt"></dt>
									<dd class="sidebar-dd"></dd>
								</dl>
							</div>
						</liferay-ui:section>

						<liferay-ui:section>
							<liferay-util:include page="/definition/view_workflow_definition_history.jsp" servletContext="<%= application %>">
								<liferay-util:param name="redirect" value="<%= redirect %>" />
							</liferay-util:include>
						</liferay-ui:section>
					</liferay-ui:tabs>
				</div>
			</div>
		</div>
	</c:if>

	<div class="container-fluid-1280">
		<div class="sidenav-content">
			<aui:form method="post" name="fm">
				<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
				<aui:input name="name" type="hidden" value="<%= name %>" />
				<aui:input name="version" type="hidden" value="<%= version %>" />
				<aui:input name="content" type="hidden" value="<%= content %>" />
				<aui:input name="successMessage" type="hidden" value='<%= active ? LanguageUtil.get(request, "workflow-updated-successfully") : LanguageUtil.get(request, "workflow-published-successfully") %>' />

				<div class="card-horizontal main-content-card">
					<div class="card-row-padded">
						<liferay-ui:error exception="<%= RequiredWorkflowDefinitionException.class %>">
							<liferay-ui:message arguments="<%= workflowDefinitionDisplayContext.getMessageArguments((RequiredWorkflowDefinitionException)errorException) %>" key="<%= workflowDefinitionDisplayContext.getMessageKey((RequiredWorkflowDefinitionException)errorException) %>" translateArguments="<%= false %>" />
						</liferay-ui:error>

						<liferay-ui:error exception="<%= WorkflowDefinitionFileException.class %>">

							<%
							WorkflowDefinitionFileException wdfe = (WorkflowDefinitionFileException)errorException;
							%>

							<liferay-ui:message key="<%= HtmlUtil.escape(wdfe.getMessage()) %>" />
						</liferay-ui:error>

						<liferay-ui:error exception="<%= WorkflowDefinitionTitleException.class %>" message="please-add-a-workflow-title-before-publishing" />

						<liferay-ui:error exception="<%= WorkflowException.class %>" message="an-error-occurred-in-the-workflow-engine" />

						<aui:fieldset cssClass="workflow-definition-content">
							<aui:col>
								<aui:field-wrapper label="title">
									<liferay-ui:input-localized
										name="title"
										placeholder="untitled-workflow"
										xml='<%= BeanPropertiesUtil.getString(workflowDefinition, "title") %>'
									/>
								</aui:field-wrapper>
							</aui:col>

							<aui:col cssClass="workflow-definition-upload">
								<liferay-util:buffer
									var="importFileMark"
								>
									<aui:a href="#" id="uploadLink">
										<%= StringUtil.toLowerCase(LanguageUtil.get(request, "import-a-file")) %>
									</aui:a>
								</liferay-util:buffer>

								<liferay-ui:message arguments="<%= importFileMark %>" key="write-your-definition-or-x" translateArguments="<%= false %>" />

								<input accept="application/xml" class="workflow-definition-upload-source" id="<portlet:namespace />upload" type="file" />
							</aui:col>

							<aui:col cssClass="workflow-definition-content-source-wrapper" id="contentSourceWrapper">
								<div class="workflow-definition-content-source" id="<portlet:namespace />contentEditor"></div>
							</aui:col>
						</aui:fieldset>
					</div>
				</div>

				<aui:button-row>
					<c:if test="<%= workflowDefinitionDisplayContext.canPublishWorkflowDefinition() %>">

						<%
						String taglibUpdateOnClick = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "publishDefinition');";
						%>

						<aui:button onClick="<%= taglibUpdateOnClick %>" primary="<%= true %>" value='<%= ((workflowDefinition == null) || !active) ? "publish" : "update" %>' />
					</c:if>

					<c:if test="<%= (workflowDefinition == null) || !active %>">

						<%
						String taglibSaveOnClick = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "saveDefinition');";
						%>

						<aui:button onClick="<%= taglibSaveOnClick %>" value="save" />
					</c:if>
				</aui:button-row>
			</aui:form>
		</div>
	</div>
</div>

<div class="hide" id="<%= randomNamespace %>titleInputLocalized">
	<c:if test="<%= workflowDefinition != null %>">
		<aui:form name='<%= randomNamespace + "form" %>'>
			<aui:input name="randomNamespace" type="hidden" value="<%= randomNamespace %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="name" type="hidden" value="<%= PortalUUIDUtil.generate() %>" />
			<aui:input name="content" type="hidden" value="<%= workflowDefinition.getContent() %>" />
			<aui:input name="defaultDuplicationTitle" type="hidden" value="<%= duplicateTitle %>" />
			<aui:input name="duplicatedDefinitionTitle" type="hidden" value="<%= workflowDefinition.getTitle(LanguageUtil.getLanguageId(request)) %>" />

			<aui:fieldset>
				<aui:col>
					<aui:field-wrapper label="title">
						<liferay-ui:input-localized
							name='<%= randomNamespace + "title" %>'
							xml="<%= duplicateTitle %>"
						/>
					</aui:field-wrapper>
				</aui:col>

				<aui:col>
					<liferay-ui:message key="copy-does-not-include-revisions" />
				</aui:col>
			</aui:fieldset>
		</aui:form>
	</c:if>
</div>

<aui:script use="aui-ace-editor,liferay-workflow-web">
	var STR_VALUE = 'value';

	var contentEditor = new A.AceEditor({
		boundingBox: '#<portlet:namespace />contentEditor',
		height: 600,
		mode: 'xml',
		tabSize: 4,
		width: '100%'
	}).render();

	contentEditor.set(
		STR_VALUE,
		Liferay.Util.formatXML('<%= HtmlUtil.escapeJS(content) %>')
	);

	var uploadFile = document.getElementById('<portlet:namespace />upload');

	var previousContent = '';

	if (uploadFile) {
		uploadFile.addEventListener('change', function(evt) {
			var files = evt.target.files;

			if (files) {
				var reader = new FileReader();

				reader.onloadend = function(evt) {
					if (evt.target.readyState == FileReader.DONE) {
						previousContent = contentEditor.get(STR_VALUE);

						contentEditor.set(STR_VALUE, evt.target.result);

						uploadFile.value = '';

						Liferay.WorkflowWeb.showDefinitionImportSuccessMessage(
							'<portlet:namespace />'
						);
					}
				};

				reader.readAsText(files[0]);
			}
		});
	}

	var uploadLink = document.getElementById('<portlet:namespace />uploadLink');

	if (uploadLink) {
		uploadLink.addEventListener('click', function(event) {
			event.preventDefault();

			uploadFile.click();
		});
	}

	var untitledWorkflowTitle = '<liferay-ui:message key="untitled-workflow" />';

	var defaultLanguageId = '<%= themeDisplay.getLanguageId() %>';

	var form = document.<portlet:namespace />fm;

	Liferay.on('<portlet:namespace />publishDefinition', function(event) {
		var titleElement = Liferay.Util.getFormElement(
			form,
			'title_' + defaultLanguageId
		);

		if (!titleElement) {
			Liferay.Util.setFormValues(form, {
				titleElement: ''
			});
		}

		Liferay.Util.postForm(form, {
			data: {
				content: contentEditor.get(STR_VALUE),
				titleValue: untitledWorkflowTitle
			},
			url: '<%= deployWorkflowDefinitionURL %>'
		});
	});

	Liferay.on('<portlet:namespace />saveDefinition', function(event) {
		var titleElement = Liferay.Util.getFormElement(
			form,
			'title_' + defaultLanguageId
		);

		if (!titleElement) {
			Liferay.Util.setFormValues(form, {
				titleElement: ''
			});
		}

		Liferay.Util.postForm(form, {
			data: {
				content: contentEditor.get(STR_VALUE),
				titleValue: untitledWorkflowTitle
			},
			url: '<%= saveWorkflowDefinitionURL %>'
		});
	});

	Liferay.on('<portlet:namespace />undoDefinition', function(event) {
		if (contentEditor) {
			contentEditor.set(STR_VALUE, previousContent);

			Liferay.WorkflowWeb.showActionUndoneSuccessMessage();
		}
	});

	var duplicateWorkflowTitle = '<liferay-ui:message key="duplicate-workflow" />';

	Liferay.on('<portlet:namespace />duplicateDefinition', function(event) {
		Liferay.WorkflowWeb.confirmBeforeDuplicateDialog(
			this,
			'<%= duplicateWorkflowDefinition %>',
			duplicateWorkflowTitle,
			'<%= randomNamespace %>',
			'<portlet:namespace />'
		);
	});

	var title = document.getElementById('<portlet:namespace />title');

	if (title) {
		title.addEventListener('keypress', function(event) {
			var keycode = event.keyCode ? event.keyCode : event.which;

			if (keycode == '13') {
				event.preventDefault();
			}
		});
	}
</aui:script>