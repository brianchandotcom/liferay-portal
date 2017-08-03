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

<%@ include file="/designer/init.jsp" %>

<c:choose>
	<c:when test="<%= WorkflowEngineManagerUtil.isDeployed() %>">

		<%
		String mvcPath = ParamUtil.getString(request, "mvcPath", "/designer/edit_kaleo_draft_definition.jsp");

		String backURL = ParamUtil.getString(request, "backURL");
		String closeRedirect = ParamUtil.getString(request, "closeRedirect");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(request);

		KaleoDraftDefinition kaleoDraftDefinition = KaleoDesignerUtil.getKaleoDraftDefinition(request);

		String content = (String)request.getAttribute(KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION_CONTENT);

		WorkflowDefinition workflowDefinition = null;

		KaleoDraftDefinition latestKaleoDraftDefinition = null;

		if (kaleoDraftDefinition != null) {
			try {
				latestKaleoDraftDefinition = KaleoDraftDefinitionLocalServiceUtil.getLatestKaleoDraftDefinition(kaleoDraftDefinition.getName(), kaleoDraftDefinition.getVersion(), serviceContext);

				if (kaleoDraftDefinition.getVersion() > 0) {
					workflowDefinition = WorkflowDefinitionManagerUtil.getWorkflowDefinition(themeDisplay.getCompanyId(), kaleoDraftDefinition.getName(), kaleoDraftDefinition.getVersion());
				}
			}
			catch (Exception e) {
			}

			if (Validator.isNull(content)) {
				content = kaleoDraftDefinition.getContent();
			}
		}

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(backURL);

		String title = null;

		if (kaleoDraftDefinition == null) {
			title = LanguageUtil.get(kaleoDesignerDisplayContext.getResourceBundle(locale), "new-workflow-definition-draft");
		}
		else {
			title = kaleoDraftDefinition.getName();
		}

		renderResponse.setTitle(title);
		%>

		<aui:form cssClass="container-fluid-1280" method="post" name="fm" onSubmit="event.preventDefault();">
			<aui:model-context bean="<%= kaleoDraftDefinition %>" model="<%= KaleoDraftDefinition.class %>" />

			<aui:input name="mvcPath" type="hidden" value="<%= mvcPath %>" />
			<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
			<aui:input name="closeRedirect" type="hidden" value="<%= closeRedirect %>" />
			<aui:input name="kaleoDraftDefinitionId" type="hidden" />
			<aui:input name="name" type="hidden" />
			<aui:input name="content" type="hidden" value="<%= content %>" />
			<aui:input name="version" type="hidden" />
			<aui:input name="draftVersion" type="hidden" />
			<aui:input name="latestDraftVersion" type="hidden" value="<%= (latestKaleoDraftDefinition == null) ? 1 : latestKaleoDraftDefinition.getDraftVersion() %>" />

			<liferay-ui:error exception="<%= DuplicateKaleoDraftDefinitionNameException.class %>" message="please-enter-a-unique-name" />
			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.class %>" message="please-enter-valid-content" />

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MultipleInitialStateNodes.class %>">

				<%
				KaleoDefinitionValidationException.MultipleInitialStateNodes misn = (KaleoDefinitionValidationException.MultipleInitialStateNodes)errorException;
				%>

				<liferay-ui:message arguments="<%= new Object[] {misn.getState1(), misn.getState2()} %>" key="the-workflow-has-too-many-start-nodes-state-nodes-x-and-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MustNotSetIncomingTransition.class %>">

				<%
				KaleoDefinitionValidationException.MustNotSetIncomingTransition mnsit = (KaleoDefinitionValidationException.MustNotSetIncomingTransition)errorException;
				%>

				<liferay-ui:message arguments="<%= mnsit.getNode() %>" key="the-x-node-cannot-have-an-incoming-transition" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MustPairedForkAndJoinNodes.class %>">

				<%
				KaleoDefinitionValidationException.MustPairedForkAndJoinNodes mpfajn = (KaleoDefinitionValidationException.MustPairedForkAndJoinNodes)errorException;
				%>

				<liferay-ui:message arguments="<%= new Object[] {mpfajn.getFork(), mpfajn.getNode()} %>" key="fork-x-and-join-x-nodes-must-be-paired" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MustSetAssignments.class %>">

				<%
				KaleoDefinitionValidationException.MustSetAssignments msa = (KaleoDefinitionValidationException.MustSetAssignments)errorException;
				%>

				<liferay-ui:message arguments="<%= msa.getTask() %>" key="specify-at-least-one-assignment-for-the-x-task-node" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MustSetIncomingTransition.class %>">

				<%
				KaleoDefinitionValidationException.MustSetIncomingTransition msit = (KaleoDefinitionValidationException.MustSetIncomingTransition)errorException;
				%>

				<liferay-ui:message arguments="<%= msit.getNode() %>" key="the-x-node-must-have-an-incoming-transition" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MustSetInitialStateNode.class %>" message="you-must-define-a-start-node" />

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MustSetJoinNode.class %>">

				<%
				KaleoDefinitionValidationException.MustSetJoinNode msjn = (KaleoDefinitionValidationException.MustSetJoinNode)errorException;
				%>

				<liferay-ui:message arguments="<%= msjn.getFork() %>" key="the-x-fork-node-must-have-a-matching-join-node" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MustSetMultipleOutgoingTransition.class %>">

				<%
				KaleoDefinitionValidationException.MustSetMultipleOutgoingTransition msmot = (KaleoDefinitionValidationException.MustSetMultipleOutgoingTransition)errorException;
				%>

				<liferay-ui:message arguments="<%= msmot.getNode() %>" key="the-x-node-must-have-at-least-two-outgoing-transitions" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MustSetOutgoingTransition.class %>">

				<%
				KaleoDefinitionValidationException.MustSetOutgoingTransition msot = (KaleoDefinitionValidationException.MustSetOutgoingTransition)errorException;
				%>

				<liferay-ui:message arguments="<%= msot.getNode() %>" key="the-x-node-must-have-an-outgoing-transition" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MustSetSourceNode.class %>">

				<%
				KaleoDefinitionValidationException.MustSetSourceNode mssn = (KaleoDefinitionValidationException.MustSetSourceNode)errorException;
				%>

				<liferay-ui:message arguments="<%= mssn.getNode() %>" key="the-x-transition-must-have-a-source-node" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MustSetTargetNode.class %>">

				<%
				KaleoDefinitionValidationException.MustSetTargetNode mstn = (KaleoDefinitionValidationException.MustSetTargetNode)errorException;
				%>

				<liferay-ui:message arguments="<%= mstn.getNode() %>" key="the-x-transition-must-end-at-a-node" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MustSetTaskFormDefinitionOrReference.class %>">

				<%
				KaleoDefinitionValidationException.MustSetTaskFormDefinitionOrReference mstfdor = (KaleoDefinitionValidationException.MustSetTaskFormDefinitionOrReference)errorException;
				%>

				<liferay-ui:message arguments="<%= new Object[] {mstfdor.getTask(), mstfdor.getTaskForm()} %>" key="the-task-form-x-for-task-x-must-specify-a-form-reference-or-form-definition" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.MustSetTerminalStateNode.class %>" message="you-must-define-an-end-node" />

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.UnbalancedForkAndJoinNode.class %>">

				<%
				KaleoDefinitionValidationException.UnbalancedForkAndJoinNode ufajn = (KaleoDefinitionValidationException.UnbalancedForkAndJoinNode)errorException;
				%>

				<liferay-ui:message arguments="<%= new Object[] {ufajn.getFork(), ufajn.getJoin()} %>" key="fix-the-errors-between-the-fork-node-x-and-join-node-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= KaleoDefinitionValidationException.UnbalancedForkAndJoinNodes.class %>" message="each-fork-node-requires-a-join-node-make-sure-all-forks-and-joins-are-properly-paired" />

			<liferay-ui:error exception="<%= KaleoDraftDefinitionNameException.class %>" message="please-enter-a-valid-name" />
			<liferay-ui:error exception="<%= WorkflowException.class %>" message="an-error-occurred-in-the-workflow-engine" />

			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<aui:input name="title" />
				</aui:fieldset>

				<c:if test="<%= kaleoDraftDefinition != null %>">
					<liferay-ui:panel-container extended="<%= false %>" id="kaleoDesignerDetailsPanelContainer" persistState="<%= true %>">
						<liferay-ui:panel collapsible="<%= true %>" cssClass="lfr-portlet-workflowdesigner-panel" extended="<%= false %>" id="kaleoDesignerSectionPanel" markupView="lexicon" persistState="<%= true %>" title='<%= LanguageUtil.get(request, "details") %>'>
							<div class="lfr-portlet-workflowdesigner-details-view">
								<c:if test="<%= workflowDefinition != null %>">
									<aui:field-wrapper cssClass="lfr-portlet-workflowdesigner-field-wrapper-first" helpMessage="the-definition-name-is-defined-in-the-workflow-definition-file" label="workflow-definition-name">
										<%= HtmlUtil.escape(workflowDefinition.getName()) %>
									</aui:field-wrapper>

									<aui:field-wrapper label="workflow-definition-version">
										<%= workflowDefinition.getVersion() %>
									</aui:field-wrapper>

									<aui:field-wrapper label="workflow-definition-status">
										<%= workflowDefinition.isActive() ? LanguageUtil.get(request, "active") : LanguageUtil.get(request, "not-active") %>
									</aui:field-wrapper>
								</c:if>

								<c:if test="<%= kaleoDraftDefinition != null %>">
									<aui:field-wrapper label="draft-version">
										<div class="lfr-portlet-workflowdesigner-draft-version" id="<portlet:namespace />draftVersionLabel">
											<%= kaleoDraftDefinition.getDraftVersion() %>
										</div>
									</aui:field-wrapper>

									<aui:field-wrapper label="draft-history">
										<div class="lfr-portlet-workflowdesigner-toolbar" id="<portlet:namespace />kaleoDesignerToolbarContainer"></div>
									</aui:field-wrapper>
								</c:if>
							</div>
						</liferay-ui:panel>
					</liferay-ui:panel-container>
				</c:if>

				<aui:fieldset>
					<div class="diagram-builder property-builder" id="<portlet:namespace />propertyBuilder">
						<div class="property-builder-content" id="<portlet:namespace />propertyBuilderContent">
							<div class="tabbable">
								<div class="tabbable-content">
									<ul class="nav nav-tabs nav-tabs-default">
										<li class="active">
											<a href="javascript:;">
												<liferay-ui:message key="nodes" />
											</a>
										</li>
										<li>
											<a href="javascript:;">
												<liferay-ui:message key="settings" />
											</a>
										</li>
									</ul>

									<div class="tab-content">
										<div class="tab-pane"></div>

										<div class="tab-pane"></div>
									</div>
								</div>
							</div>

							<div class="property-builder-content-container">
								<div class="tabbable">
									<div class="tabbable-content">
										<ul class="nav nav-tabs nav-tabs-default">
											<li class="active">
												<a href="javascript:;">
													<liferay-ui:message key="view" />
												</a>
											</li>
											<li>
												<a href="javascript:;">
													<liferay-ui:message key="source" /> (Kaleo <liferay-ui:message key="xml" />)
												</a>
											</li>
										</ul>

										<div class="tab-content">
											<div class="tab-pane">
												<div class="property-builder-canvas">
													<div class="property-builder-drop-container"></div>
												</div>
											</div>

											<div class="tab-pane">
												<div class="lfr-template-editor-wrapper" id="<portlet:namespace />editorWrapper"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<aui:script>
						Liferay.provide(
							window,
							'<portlet:namespace />afterTabViewChange',
							function(event) {
								var A = AUI();

								var tabContentNode = event.newVal.get('boundingBox');

								var kaleoDesigner = <portlet:namespace />kaleoDesigner;

								if ((tabContentNode === kaleoDesigner.viewNode) && kaleoDesigner.editor) {
									setTimeout(
										function() {
											kaleoDesigner.set('definition', kaleoDesigner.editor.get('value'));
										},
										0
									);
								}
							},
							['aui-base']
						);

						Liferay.provide(
							window,
							'<portlet:namespace />getKaleoDraftDefinition',
							function(name, version, draftVersion, callback) {
								var A = AUI();

								var contentURL = Liferay.PortletURL.createResourceURL();

								contentURL.setParameter('name', name);
								contentURL.setParameter('version', version);
								contentURL.setParameter('draftVersion', draftVersion);
								contentURL.setPortletId('<%= portletDisplay.getId() %>');
								contentURL.setResourceId('kaleoDraftDefinitions');

								A.io.request(
									contentURL.toString(),
									{
										dataType: 'JSON',
										on: {
											success: callback
										}
									}
								);
							},
							['aui-base', 'liferay-portlet-url']
						);

						Liferay.provide(
							window,
							'<portlet:namespace />getLatestKaleoDraftDefinition',
							function(event, button) {
								var A = AUI();

								var button = event.target;

								if (!button.get('disabled')) {
									<portlet:namespace />navigateKaleoDraftDefinition(Infinity);
								}
							},
							['aui-base']
						);

						Liferay.provide(
							window,
							'<portlet:namespace />initKaleoDraftDefinitionToolbar',
							function() {
								var A = AUI();

								<portlet:namespace />kaleoDesignerToolbar = new A.Toolbar(
									{
										activeState: false,
										boundingBox: '#<portlet:namespace />kaleoDesignerToolbarContainer',
										children: [
											{
												icon: 'icon-arrow-left',
												id: '<portlet:namespace />undoButton',
												label: '<liferay-ui:message key="undo" />',
												on: {
													click: <portlet:namespace />undoKaleoDraftDefinition
												}
											},
											{
												icon: 'icon-arrow-right',
												id: '<portlet:namespace />redoButton',
												label: '<liferay-ui:message key="redo" />',
												on: {
													click: <portlet:namespace />redoKaleoDraftDefinition
												}
											},
											{
												icon: 'icon-forward',
												id: '<portlet:namespace />latestButton',
												label: '<liferay-ui:message key="latest-version" />',
												on: {
													click: <portlet:namespace />getLatestKaleoDraftDefinition
												}
											}
										]
									}
								).render();

								<portlet:namespace />updateToolbarButtons();
							},
							['aui-toolbar']
						);

						Liferay.provide(
							window,
							'<portlet:namespace />navigateKaleoDraftDefinition',
							function(direction) {
								var A = AUI();

								var draftVersion = A.one('#<portlet:namespace />draftVersion');
								var draftVersionLabel = A.one('#<portlet:namespace />draftVersionLabel');
								var latestDraftVersion = A.one('#<portlet:namespace />latestDraftVersion');

								var name = A.one('#<portlet:namespace />name');
								var title = A.one('#<portlet:namespace />title_<%= LocaleUtil.toLanguageId(locale) %>');
								var versionNode = A.one('#<portlet:namespace />version');

								var draftVersionVal = Liferay.Util.toNumber(draftVersion.val()) + direction;

								var latestDraftVersionVal = Liferay.Util.toNumber(latestDraftVersion.val());
								var versionVal = Liferay.Util.toNumber(versionNode.val());

								var currentDraftVersion = Liferay.Util.clamp(draftVersionVal, 1, latestDraftVersionVal);

								A.one('#<portlet:namespace />toolbarMessage').setContent('<liferay-ui:message key="loading-workflow-definition" />&hellip;');

								<portlet:namespace />getKaleoDraftDefinition(
									name.val(),
									versionVal,
									currentDraftVersion,
									function() {
										var responseData = this.get('responseData');

										if (responseData) {
											var kaleoDesigner = <portlet:namespace />kaleoDesigner;

											var content = responseData.content;

											draftVersion.val(responseData.draftVersion);

											draftVersionLabel.setContent(responseData.draftVersion);

											title.val(responseData.title);

											<portlet:namespace />updateToolbarButtons();

											kaleoDesigner.set('definition', content);

											if (kaleoDesigner.editor) {
												kaleoDesigner.editor.set('value', kaleoDesigner.toFormattedXML());
											}

											A.one('#<portlet:namespace />toolbarMessage').setContent('');
										}
									}
								);
							},
							['aui-base']
						);

						<c:if test="<%= KaleoDesignerPermission.contains(permissionChecker, themeDisplay.getCompanyGroupId(), KaleoDesignerActionKeys.PUBLISH) %>">
							Liferay.provide(
								window,
								'<portlet:namespace />publishKaleoDraftDefinition',
								function() {
									var A = AUI();

									<portlet:namespace />updateContent();

									<portlet:namespace />updateAction('<portlet:actionURL name="publishKaleoDraftDefinition" />');

									submitForm(document.<portlet:namespace />fm);
								},
								['aui-base']
							);
						</c:if>

						Liferay.provide(
							window,
							'<portlet:namespace />redoKaleoDraftDefinition',
							function(event) {
								var A = AUI();

								var button = event.target;

								if (!button.get('disabled')) {
									<portlet:namespace />navigateKaleoDraftDefinition(1);
								}
							},
							['aui-base']
						);

						<c:if test="<%= ((kaleoDraftDefinition == null) && KaleoDesignerPermission.contains(permissionChecker, themeDisplay.getCompanyGroupId(), KaleoDesignerActionKeys.ADD_DRAFT)) || ((kaleoDraftDefinition != null) && KaleoDraftDefinitionPermission.contains(permissionChecker, kaleoDraftDefinition, ActionKeys.UPDATE)) %>">
							Liferay.provide(
								window,
								'<portlet:namespace />updateKaleoDraftDefinition',
								function() {
									var A = AUI();

									<portlet:namespace />updateContent();

									<portlet:namespace />updateAction('<portlet:actionURL name="updateKaleoDraftDefinition" />');

									submitForm(document.<portlet:namespace />fm);
								},
								['aui-base']
							);
						</c:if>

						Liferay.provide(
							window,
							'<portlet:namespace />undoKaleoDraftDefinition',
							function(event) {
								var A = AUI();

								var button = event.target;

								if (!button.get('disabled')) {
									<portlet:namespace />navigateKaleoDraftDefinition(-1);
								}
							},
							['aui-base']
						);

						Liferay.provide(
							window,
							'<portlet:namespace />updateAction',
							function(action) {
								var A = AUI();

								var form = A.one(document.<portlet:namespace />fm);

								form.attr('action', action);
							},
							['aui-base']
						);

						Liferay.provide(
							window,
							'<portlet:namespace />updateContent',
							function() {
								var A = AUI();

								var content = A.one('#<portlet:namespace />content');

								var activeTab = <portlet:namespace />kaleoDesigner.contentTabView.getActiveTab();

								if (activeTab === <portlet:namespace />kaleoDesigner.sourceNode) {
									content.val(<portlet:namespace />kaleoDesigner.editor.get('value'));
								}
								else {
									content.val(<portlet:namespace />kaleoDesigner.toFormattedXML());
								}
							},
							['aui-base']
						);

						Liferay.provide(
							window,
							'<portlet:namespace />closeKaleoDialog',
							function() {
								var dialog = Liferay.Util.getWindow();

								if (dialog) {
									dialog.destroy();
								}
							},
							['aui-base']
						);

						Liferay.provide(
							window,
							'<portlet:namespace />updateToolbarButtons',
							function() {
								var A = AUI();

								var draftVersion = Liferay.Util.toNumber(A.one('#<portlet:namespace />draftVersion').val());
								var latestDraftVersion = Liferay.Util.toNumber(A.one('#<portlet:namespace />latestDraftVersion').val());

								var latestButton = <portlet:namespace />kaleoDesignerToolbar.item(2);
								var redoButton = <portlet:namespace />kaleoDesignerToolbar.item(1);
								var undoButton = <portlet:namespace />kaleoDesignerToolbar.item(0);

								latestButton.set('disabled', (draftVersion === latestDraftVersion));

								redoButton.set('disabled', (draftVersion === latestDraftVersion));

								undoButton.set('disabled', (draftVersion === 1));
							},
							['aui-base']
						);

						<%
						String saveCallback = ParamUtil.getString(request, "saveCallback");
						%>

						<c:if test="<%= (kaleoDraftDefinition != null) && Validator.isNotNull(saveCallback) %>">
							Liferay.Util.getOpener()['<%= HtmlUtil.escapeJS(saveCallback) %>']('<%= HtmlUtil.escapeJS(kaleoDraftDefinition.getName()) %>', <%= kaleoDraftDefinition.getVersion() %>, <%= kaleoDraftDefinition.getDraftVersion() %>);
						</c:if>
					</aui:script>

					<aui:script use="liferay-kaleo-designer-utils,liferay-portlet-kaleo-designer">
						var emptyFn = A.Lang.emptyFn;

						var MAP_ROLE_TYPES = {
							regular: 1,
							site: 2,
							organization: 3
						};

						<portlet:namespace />kaleoDesigner = new Liferay.KaleoDesigner(
							{

								<%
								String availableFields = ParamUtil.getString(request, "availableFields");
								%>

								<c:if test="<%= Validator.isNotNull(availableFields) %>">
									availableFields: A.Object.getValue(window, '<%= HtmlUtil.escapeJS(availableFields) %>'.split('.')),
								</c:if>

								<%
								String availablePropertyModels = ParamUtil.getString(request, "availablePropertyModels", "Liferay.KaleoDesigner.AVAILABLE_PROPERTY_MODELS.KALEO_FORMS_EDIT");
								%>

								<c:if test="<%= Validator.isNotNull(availablePropertyModels) %>">
									availablePropertyModels: A.Object.getValue(window, '<%= HtmlUtil.escapeJS(availablePropertyModels) %>'.split('.')),
								</c:if>

								boundingBox: '#<portlet:namespace />propertyBuilder',
								data: {

									<%
									long kaleoProcessId = ParamUtil.getLong(request, "kaleoProcessId");
									%>

									kaleoProcessId: '<%= kaleoProcessId %>'
								},

								<c:if test="<%= Validator.isNotNull(content) %>">
									definition: '<%= HtmlUtil.escapeJS(content) %>',
								</c:if>

								<%
								String propertiesSaveCallback = ParamUtil.getString(request, "propertiesSaveCallback");
								%>

								<c:if test="<%= Validator.isNotNull(propertiesSaveCallback) %>">
									on: {
										save: Liferay.Util.getOpener()['<%= HtmlUtil.escapeJS(propertiesSaveCallback) %>']
									},
								</c:if>

								portletNamespace: '<portlet:namespace />',

								<%
								String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace");
								%>

								portletResourceNamespace: '<%= HtmlUtil.escapeJS(portletResourceNamespace) %>',
								srcNode: '#<portlet:namespace />propertyBuilderContent'
							}
						).render();

						<portlet:namespace />kaleoDesigner.contentTabView.after(
							{
								selectionChange: <portlet:namespace />afterTabViewChange
							}
						);

						var fields = <portlet:namespace />kaleoDesigner.get('fields');

						if (fields.size() == 0) {
							<portlet:namespace />kaleoDesigner.set(
								'fields',
								[
									{
										name: 'StartNode',
										type: 'start',
										xy: [100, 40]
									},

									{
										actions: {
											description: [Liferay.KaleoDesignerStrings.approve],
											executionType: ['onEntry'],
											name: [Liferay.KaleoDesignerStrings.approve],
											script: ['com.liferay.portal.kernel.workflow.WorkflowStatusManagerUtil.updateStatus(com.liferay.portal.kernel.workflow.WorkflowConstants.getLabelStatus("approved"), workflowContext);'],
											scriptLanguage: ['groovy']
										},
										name: 'EndNode',
										type: 'end',
										xy: [100, 500]
									}
								]
							);

							<portlet:namespace />kaleoDesigner.connect('StartNode', 'EndNode');
						}

						var createRoleAutocomplete = function(inputNode, resultTextLocator, selectFn) {
							var instance = this;

							var roleType = 0;
							var roleTypeNode = inputNode.previous('[name=roleType]');

							if (roleTypeNode) {
								roleType = roleTypeNode.val();
							}

							var type = MAP_ROLE_TYPES[roleType] || 0;

							var autocomplete = Liferay.KaleoDesignerAutoCompleteUtil.create('<portlet:namespace />', inputNode, '<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="roles" />', null, resultTextLocator, selectFn);

							autocomplete.set('requestTemplate', '&<portlet:namespace />type=' + type + '&<portlet:namespace />keywords={query}');

							autocomplete.sendRequest('');
						};

						var createUserAutocomplete = function(inputNode, resultTextLocator, selectFn) {
							var autocomplete = Liferay.KaleoDesignerAutoCompleteUtil.create('<portlet:namespace />', inputNode, '<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="users" />', null, resultTextLocator, selectFn);

							autocomplete.sendRequest('');
						};

						A.getDoc().delegate(
							'focus',
							function(event) {
								var inputNode = event.currentTarget;

								var inputName = inputNode.attr('name');

								if ((inputName == 'roleName') || (inputName == 'roleNameAC')) {
									createRoleAutocomplete(
										inputNode,
										null,
										function(event) {
											var data = event.result.raw;
											var roleId = inputNode.next('[name=roleId]');

											if (roleId) {
												roleId.val(data.roleId);
											}
										}
									);
								}
								else if (inputName == 'fullName') {
									createUserAutocomplete(
										inputNode,
										inputName,
										function(event) {
											var data = event.result.raw;

											A.each(
												data,
												function(item, index, collection) {
													var input = inputNode.siblings('[name=' + index + ']').first();

													if (input) {
														input.val(data[index]);
													}
												}
											);
										}
									);
								}
							},
							'.assignments-cell-editor-input'
						);

						<c:choose>
							<c:when test="<%= kaleoDraftDefinition == null %>">
								var titleComponent = Liferay.component('<portlet:namespace />title');

								var titlePlaceholderInput = titleComponent.get('inputPlaceholder');

								if (titlePlaceholderInput) {
									titlePlaceholderInput.after(
										'change',
										function(event) {
											<portlet:namespace />kaleoDesigner.set('definitionName', titleComponent.getValue());
										}
									);
								}
							</c:when>
							<c:otherwise>
								<portlet:namespace />initKaleoDraftDefinitionToolbar();
							</c:otherwise>
						</c:choose>

						var dialog = Liferay.Util.getWindow();

						if (dialog) {
							dialog.on(
								'visibleChange',
								function(event) {
									if (!event.newVal) {
										<c:choose>
											<c:when test="<%= (workflowDefinition != null) && !workflowDefinition.isActive() %>">
												if (confirm('<liferay-ui:message key="do-you-want-to-publish-this-draft" />')) {
													event.halt();

													<portlet:namespace />publishKaleoDraftDefinition();
												}
											</c:when>
											<c:otherwise>

												<%
												boolean refreshOpenerOnClose = ParamUtil.getBoolean(request, "refreshOpenerOnClose");
												%>

												<c:if test="<%= Validator.isNotNull(portletResourceNamespace) && refreshOpenerOnClose %>">

													<%
													String openerWindowName = ParamUtil.getString(request, "openerWindowName");
													%>

													var openerWindow = Liferay.Util.getTop();

													<c:if test="<%= Validator.isNotNull(openerWindowName) %>">
														var openerDialog = Liferay.Util.getWindow('<%= HtmlUtil.escapeJS(openerWindowName) %>');

														openerWindow = openerDialog.iframe.node.get('contentWindow').getDOM();
													</c:if>

													openerWindow.Liferay.Portlet.refresh('#p_p_id<%= HtmlUtil.escapeJS(portletResourceNamespace) %>');
												</c:if>
											</c:otherwise>
										</c:choose>
									}
								}
							);
						}
					</aui:script>
				</aui:fieldset>
			</aui:fieldset-group>

			<aui:button-row>
				<c:if test="<%= KaleoDesignerPermission.contains(permissionChecker, themeDisplay.getCompanyGroupId(), KaleoDesignerActionKeys.ADD_DRAFT) || ((kaleoDraftDefinition != null) && KaleoDraftDefinitionPermission.contains(permissionChecker, kaleoDraftDefinition, ActionKeys.UPDATE)) %>">
					<aui:button onClick='<%= renderResponse.getNamespace() + "updateKaleoDraftDefinition();" %>' value="save-as-draft" />
				</c:if>

				<c:if test="<%= KaleoDesignerPermission.contains(permissionChecker, themeDisplay.getCompanyGroupId(), KaleoDesignerActionKeys.PUBLISH) %>">
					<aui:button onClick='<%= renderResponse.getNamespace() + "publishKaleoDraftDefinition();" %>' primary="<%= true %>" value="publish" />
				</c:if>

				<c:if test="<%= Validator.isNotNull(backURL) %>">
					<aui:button href="<%= backURL %>" value="cancel" />
				</c:if>

				<c:if test="<%= Validator.isNull(backURL) %>">
					<aui:button onClick='<%= renderResponse.getNamespace() + "closeKaleoDialog();" %>' value="cancel" />
				</c:if>

				<span class="lfr-portlet-workflowdesigner-message" id="<portlet:namespace />toolbarMessage"></span>
			</aui:button-row>
		</aui:form>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="no-workflow-engine-is-deployed" />
		</div>
	</c:otherwise>
</c:choose>