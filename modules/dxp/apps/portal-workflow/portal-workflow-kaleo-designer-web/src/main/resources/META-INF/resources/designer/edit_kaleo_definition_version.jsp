<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/designer/init.jsp" %>

<%
String successMessageKey = KaleoDesignerPortletKeys.KALEO_DESIGNER + "requestProcessed";
%>

<c:if test="<%= MultiSessionMessages.contains(renderRequest, successMessageKey) %>">
	<liferay-ui:success key="<%= successMessageKey %>" message="<%= (String)MultiSessionMessages.get(renderRequest, successMessageKey) %>" translateMessage="<%= false %>" />
</c:if>

<c:choose>
	<c:when test="<%= WorkflowEngineManagerUtil.isDeployed() %>">

		<%
		String mvcPath = ParamUtil.getString(request, "mvcPath", "/designer/edit_kaleo_definition_version.jsp");

		String redirect = ParamUtil.getString(request, "redirect");
		String closeRedirect = ParamUtil.getString(request, "closeRedirect");

		KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)request.getAttribute(KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION);

		KaleoDefinition kaleoDefinition = kaleoDesignerDisplayContext.getKaleoDefinition(kaleoDefinitionVersion);

		String name = BeanParamUtil.getString(kaleoDefinitionVersion, request, "name");
		String draftVersion = BeanParamUtil.getString(kaleoDefinitionVersion, request, "version");
		String content = BeanParamUtil.getString(kaleoDefinitionVersion, request, "content");

		String latestDraftVersion = StringPool.BLANK;
		int version = 0;

		KaleoDefinitionVersion latestKaleoDefinitionVersion = null;

		if (kaleoDefinitionVersion != null) {
			if (Validator.isNull(name)) {
				name = kaleoDefinitionVersion.getName();
			}

			draftVersion = kaleoDefinitionVersion.getVersion();

			latestKaleoDefinitionVersion = KaleoDefinitionVersionLocalServiceUtil.getLatestKaleoDefinitionVersion(themeDisplay.getCompanyId(), name);

			latestDraftVersion = latestKaleoDefinitionVersion.getVersion();

			if (kaleoDefinition != null) {
				version = kaleoDefinition.getVersion();
			}
		}

		portletDisplay.setShowBackIcon(true);

		PortletURL backPortletURL = PortalUtil.getControlPanelPortletURL(renderRequest, KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW, PortletRequest.RENDER_PHASE);

		backPortletURL.setParameter("mvcPath", "/view.jsp");

		portletDisplay.setURLBack(backPortletURL.toString());

		renderResponse.setTitle((kaleoDefinitionVersion == null) ? LanguageUtil.get(request, "new-workflow") : kaleoDefinitionVersion.getTitle(locale));

		String state = (String)request.getParameter(WorkflowWebKeys.WORKFLOW_JSP_STATE);

		boolean isPreviewBeforeRestoreState = WorkflowWebKeys.WORKFLOW_PREVIEW_BEFORE_RESTORE_STATE.equals(state);

		String duplicateTitle = kaleoDesignerDisplayContext.getDuplicateTitle(kaleoDefinition);

		String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;
		%>

		<c:if test="<%= kaleoDefinitionVersion != null %>">
			<aui:model-context bean="<%= kaleoDefinitionVersion %>" model="<%= KaleoDefinitionVersion.class %>" />

			<liferay-frontend:info-bar>
				<div class="container-fluid-1280">
					<c:if test="<%= !isPreviewBeforeRestoreState %>">
						<div class="info-bar-item">
							<c:choose>
								<c:when test="<%= (kaleoDefinition != null) && kaleoDefinition.isActive() %>">
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
					</c:if>

					<%
					String userName = kaleoDesignerDisplayContext.getUserName(kaleoDefinitionVersion);
					%>

					<span>
						<c:choose>
							<c:when test="<%= userName == null %>">
								<%= dateFormatTime.format(kaleoDefinitionVersion.getModifiedDate()) %>
							</c:when>
							<c:when test="<%= isPreviewBeforeRestoreState %>">
								<liferay-ui:message arguments="<%= new String[] {dateFormatTime.format(kaleoDefinitionVersion.getModifiedDate()), userName} %>" key="revision-from-x-by-x" translateArguments="<%= false %>" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message arguments="<%= new String[] {dateFormatTime.format(kaleoDefinitionVersion.getModifiedDate()), userName} %>" key="x-by-x" translateArguments="<%= false %>" />
							</c:otherwise>
						</c:choose>
					</span>
				</div>

				<c:if test="<%= !isPreviewBeforeRestoreState %>">
					<liferay-frontend:info-bar-buttons>
						<liferay-frontend:info-bar-sidenav-toggler-button
							icon="info-circle"
							label="info"
							typeMobile="relative"
						/>
					</liferay-frontend:info-bar-buttons>
				</c:if>
			</liferay-frontend:info-bar>
		</c:if>

		<div class="closed sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
			<c:if test="<%= (kaleoDefinitionVersion != null) && !isPreviewBeforeRestoreState %>">
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
											<span class="text-truncate"><%= HtmlUtil.escape(kaleoDefinitionVersion.getTitle(locale)) %></span>
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
								<div class="tab-content">
									<c:if test="<%= kaleoDefinitionVersion != null %>">
										<liferay-ui:section>
											<div style="margin-top:1.5rem;">

												<%
												String creatorUserName = kaleoDesignerDisplayContext.getCreatorUserName(kaleoDefinitionVersion);
												String userName = kaleoDesignerDisplayContext.getUserName(kaleoDefinitionVersion);
												%>

												<dl class="sidebar-dl sidebar-section">
													<dt class="sidebar-dt">
														<liferay-ui:message key="created" />
													</dt>
													<dd class="sidebar-dd">
														<c:choose>
															<c:when test="<%= creatorUserName == null %>">
																<%= dateFormatTime.format(kaleoDesignerDisplayContext.getCreatedDate(kaleoDefinitionVersion)) %>
															</c:when>
															<c:otherwise>
																<liferay-ui:message arguments="<%= new String[] {dateFormatTime.format(kaleoDesignerDisplayContext.getCreatedDate(kaleoDefinitionVersion)), creatorUserName} %>" key="x-by-x" translateArguments="<%= false %>" />
															</c:otherwise>
														</c:choose>
													</dd>
													<dt class="sidebar-dt">
														<liferay-ui:message key="last-modified" />
													</dt>
													<dd class="sidebar-dd">
														<c:choose>
															<c:when test="<%= userName == null %>">
																<%= dateFormatTime.format(kaleoDefinitionVersion.getModifiedDate()) %>
															</c:when>
															<c:otherwise>
																<liferay-ui:message arguments="<%= new String[] {dateFormatTime.format(kaleoDefinitionVersion.getModifiedDate()), userName} %>" key="x-by-x" translateArguments="<%= false %>" />
															</c:otherwise>
														</c:choose>
													</dd>
													<dt class="sidebar-dt">
														<liferay-ui:message key="total-modifications" />
													</dt>
													<dd class="sidebar-dd">
														<liferay-ui:message arguments='<%= new String[] {kaleoDesignerDisplayContext.getKaleoDefinitionVersionCount(kaleoDefinitionVersion) + ""} %>' key="x-revisions" translateArguments="<%= false %>" />
													</dd>
													<dt class="sidebar-dt"></dt>
													<dd class="sidebar-dd"></dd>
												</dl>
											</div>
										</liferay-ui:section>
									</c:if>

									<c:if test="<%= !isPreviewBeforeRestoreState %>">
										<liferay-ui:section>
											<liferay-util:include page="/designer/view_kaleo_definition_version_history.jsp" servletContext="<%= application %>">
												<liferay-util:param name="redirect" value="<%= redirect %>" />
											</liferay-util:include>
										</liferay-ui:section>
									</c:if>
								</div>
							</liferay-ui:tabs>
						</div>
					</div>
				</div>
			</c:if>

			<div class="<%= isPreviewBeforeRestoreState ? "" : "container-fluid-1280" %>">
				<div class="sidenav-content">
					<aui:form method="post" name="fm" onSubmit="event.preventDefault();">
						<aui:model-context bean="<%= kaleoDefinitionVersion %>" model="<%= KaleoDefinitionVersion.class %>" />
						<aui:input name="mvcPath" type="hidden" value="<%= mvcPath %>" />
						<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
						<aui:input name="closeRedirect" type="hidden" value="<%= closeRedirect %>" />
						<aui:input name="name" type="hidden" value="<%= name %>" />
						<aui:input name="content" type="hidden" value="<%= content %>" />
						<aui:input name="version" type="hidden" value="<%= String.valueOf(version) %>" />
						<aui:input name="draftVersion" type="hidden" value="<%= draftVersion %>" />
						<aui:input name="latestDraftVersion" type="hidden" value="<%= latestDraftVersion %>" />

						<%@ include file="/designer/edit_kaleo_definition_version_exceptions.jspf" %>

						<aui:fieldset-group markupView="lexicon">
							<aui:fieldset>
								<liferay-ui:input-localized
									disabled="<%= kaleoDesignerDisplayContext.isDefinitionInputDisabled(isPreviewBeforeRestoreState, kaleoDefinitionVersion, permissionChecker) %>"
									name="title"
									placeholder="untitled-workflow"
									xml='<%= BeanPropertiesUtil.getString(kaleoDefinitionVersion, "title") %>'
								/>
							</aui:fieldset>

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
															<liferay-ui:message key="properties" />
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
												<div class="main-tab tabbable-content">
													<ul class="nav nav-tabs">
														<li class="active nav-item">
															<a href="javascript:;">
																<liferay-ui:message key="diagram" />
															</a>
														</li>
														<li class="nav-item">
															<a href="javascript:;">
																<liferay-ui:message key="source" />
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
															<liferay-util:buffer
																var="importFileMark"
															>
																<label class="btn btn-link p-0" for="<portlet:namespace />upload">
																	<%= StringUtil.toLowerCase(LanguageUtil.get(request, "import-a-file")) %>
																</label>
															</liferay-util:buffer>

															<c:if test="<%= !kaleoDesignerDisplayContext.isDefinitionInputDisabled(isPreviewBeforeRestoreState, kaleoDefinitionVersion, permissionChecker) %>">
																<liferay-ui:message arguments="<%= importFileMark %>" key="write-your-definition-or-x" translateArguments="<%= false %>" />
															</c:if>

															<input id="<portlet:namespace />upload" style="display:none" type="file" />

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
											var tabContentNode = event.newVal.get('boundingBox');

											var kaleoDesigner = <portlet:namespace />kaleoDesigner;

											if (tabContentNode === kaleoDesigner.viewNode && kaleoDesigner.editor) {
												setTimeout(function() {
													kaleoDesigner.set(
														'definition',
														kaleoDesigner.editor.get('value')
													);
												}, 0);
											}
										},
										['aui-base']
									);

									Liferay.provide(
										window,
										'<portlet:namespace />publishKaleoDefinitionVersion',
										function() {
											<portlet:namespace />updateContent();

											<portlet:namespace />updateTitle();

											<portlet:namespace />updateAction(
												'<portlet:actionURL name="publishKaleoDefinitionVersion" />'
											);

											submitForm(document.<portlet:namespace />fm);
										},
										['aui-base']
									);

									Liferay.provide(
										window,
										'<portlet:namespace />saveKaleoDefinitionVersion',
										function() {
											<portlet:namespace />updateContent();

											<portlet:namespace />updateTitle();

											<portlet:namespace />updateAction(
												'<portlet:actionURL name="saveKaleoDefinitionVersion" />'
											);

											submitForm(document.<portlet:namespace />fm);
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
												content.val(<portlet:namespace />kaleoDesigner.getContent());
											}
										},
										['aui-base']
									);

									Liferay.provide(
										window,
										'<portlet:namespace />updateTitle',
										function() {
											var titleComponent = Liferay.component('<portlet:namespace />title');

											var titlePlaceholderInput = titleComponent.get('inputPlaceholder');

											if (!titlePlaceholderInput.val()) {
												titlePlaceholderInput.val(
													'<liferay-ui:message key="untitled-workflow" />'
												);
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

									<%
									String saveCallback = ParamUtil.getString(request, "saveCallback");
									%>

									<c:if test="<%= (kaleoDefinitionVersion != null) && Validator.isNotNull(saveCallback) %>">
										Liferay.Util.getOpener()['<%= HtmlUtil.escapeJS(saveCallback) %>'](
											'<%= HtmlUtil.escapeJS(name) %>',
											<%= version %>,
											<%= draftVersion %>
										);
									</c:if>
								</aui:script>

								<aui:script use="liferay-kaleo-designer-dialogs,liferay-kaleo-designer-utils,liferay-portlet-kaleo-designer">
									var MAP_ROLE_TYPES = {
										organization: 3,
										regular: 1,
										site: 2
									};

									<portlet:namespace />kaleoDesigner = new Liferay.KaleoDesigner({

										<%
										String availableFields = ParamUtil.getString(request, "availableFields");
										%>

										<c:if test="<%= Validator.isNotNull(availableFields) %>">
											availableFields: A.Object.getValue(
												window,
												'<%= HtmlUtil.escapeJS(availableFields) %>'.split('.')
											),
										</c:if>

										<%
										String availablePropertyModels = ParamUtil.getString(request, "availablePropertyModels", "Liferay.KaleoDesigner.AVAILABLE_PROPERTY_MODELS.KALEO_FORMS_EDIT");
										%>

										<c:if test="<%= Validator.isNotNull(availablePropertyModels) %>">
											availablePropertyModels: A.Object.getValue(
												window,
												'<%= HtmlUtil.escapeJS(availablePropertyModels) %>'.split('.')
											),
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
												save: Liferay.Util.getOpener()[
													'<%= HtmlUtil.escapeJS(propertiesSaveCallback) %>'
												]
											},
										</c:if>

										portletNamespace: '<portlet:namespace />',

										<%
										String portletResourceNamespace = ParamUtil.getString(request, "portletResourceNamespace");
										%>

										portletResourceNamespace:
											'<%= HtmlUtil.escapeJS(portletResourceNamespace) %>',
										srcNode: '#<portlet:namespace />propertyBuilderContent'
									}).render();

									<c:if test="<%= kaleoDesignerDisplayContext.isDefinitionInputDisabled(isPreviewBeforeRestoreState, kaleoDefinitionVersion, permissionChecker) %>">
										<portlet:namespace />kaleoDesigner.after('render', function() {
											var diagramBuilderControlElements = document.querySelectorAll(
												'#<portlet:namespace />propertyBuilder .diagram-builder-controls'
											);

											diagramBuilderControlElements.forEach(function(element) {
												element.parentElement.removeChild(element);
											});

											<portlet:namespace />kaleoDesigner.detachAll();

											<portlet:namespace />kaleoDesigner.set('readOnly', true);
										});
									</c:if>

									var uploadFile = document.querySelector('#<portlet:namespace />upload');

									var previousContent = '';

									uploadFile.addEventListener('change', function(evt) {
										var files = evt.target.files;

										if (files) {
											var reader = new FileReader();

											reader.onloadend = function(evt) {
												if (evt.target.readyState == FileReader.DONE) {
													previousContent = <portlet:namespace />kaleoDesigner.getEditorContent();

													<portlet:namespace />kaleoDesigner.setEditorContent(
														evt.target.result
													);

													uploadFile.value = '';

													Liferay.KaleoDesignerDialogs.showDefinitionImportSuccessMessage(
														'<portlet:namespace />'
													);
												}
											};

											reader.readAsText(files[0]);
										}
									});

									<portlet:namespace />kaleoDesigner.contentTabView.after({
										selectionChange: <portlet:namespace />afterTabViewChange
									});

									var fields = <portlet:namespace />kaleoDesigner.get('fields');

									if (fields.size() == 0) {
										<portlet:namespace />kaleoDesigner.set('fields', [
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
													script: [
														'com.liferay.portal.kernel.workflow.WorkflowStatusManagerUtil.updateStatus(com.liferay.portal.kernel.workflow.WorkflowConstants.getLabelStatus("approved"), workflowContext);'
													],
													scriptLanguage: ['groovy']
												},
												name: 'EndNode',
												type: 'end',
												xy: [100, 500]
											}
										]);

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

										var autocomplete = Liferay.KaleoDesignerAutoCompleteUtil.create(
											'<portlet:namespace />',
											inputNode,
											'<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="roles" />',
											null,
											resultTextLocator,
											selectFn
										);

										autocomplete.set(
											'requestTemplate',
											'&<portlet:namespace />type=' +
												type +
												'&<portlet:namespace />keywords={query}'
										);

										autocomplete.sendRequest('');
									};

									var createUserAutocomplete = function(inputNode, resultTextLocator, selectFn) {
										var autocomplete = Liferay.KaleoDesignerAutoCompleteUtil.create(
											'<portlet:namespace />',
											inputNode,
											'<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="users" />',
											null,
											resultTextLocator,
											selectFn
										);

										autocomplete.sendRequest('');
									};

									A.getDoc().delegate(
										'focus',
										function(event) {
											var inputNode = event.currentTarget;

											var inputName = inputNode.attr('name');

											if (inputName == 'roleName' || inputName == 'roleNameAC') {
												createRoleAutocomplete(inputNode, null, function(event) {
													var data = event.result.raw;
													var roleId = inputNode.next('[name=roleId]');

													if (roleId) {
														roleId.val(data.roleId);
													}
												});
											}
											else if (inputName == 'fullName') {
												createUserAutocomplete(inputNode, inputName, function(event) {
													var data = event.result.raw;

													A.each(data, function(item, index, collection) {
														var input = inputNode
															.siblings('[name=' + index + ']')
															.first();

														if (input) {
															input.val(data[index]);
														}
													});
												});
											}
										},
										'.assignments-cell-editor-input'
									);

									<c:choose>
										<c:when test="<%= kaleoDefinitionVersion == null %>">
											var titleComponent = Liferay.component('<portlet:namespace />title');

											var titlePlaceholderInput = titleComponent.get('inputPlaceholder');

											if (titlePlaceholderInput) {
												titlePlaceholderInput.after('change', function(event) {
													<portlet:namespace />kaleoDesigner.set(
														'definitionName',
														titleComponent.getValue()
													);
												});
											}
										</c:when>
									</c:choose>

									var dialog = Liferay.Util.getWindow();

									if (dialog && !dialog._dialogAction) {
										dialog._dialogAction = function(event) {
											if (!event.newVal) {

												<%
												boolean refreshOpenerOnClose = ParamUtil.getBoolean(request, "refreshOpenerOnClose");
												%>

												<c:if test="<%= Validator.isNotNull(portletResourceNamespace) && refreshOpenerOnClose %>">

													<%
													String openerWindowName = ParamUtil.getString(request, "openerWindowName");
													%>

													var openerWindow = Liferay.Util.getTop();

													<c:if test="<%= Validator.isNotNull(openerWindowName) %>">
														var openerDialog = Liferay.Util.getWindow(
															'<%= HtmlUtil.escapeJS(openerWindowName) %>'
														);

														openerWindow = openerDialog.iframe.node
															.get('contentWindow')
															.getDOM();
													</c:if>

													openerWindow.Liferay.Portlet.refresh(
														'#p_p_id<%= HtmlUtil.escapeJS(portletResourceNamespace) %>'
													);
												</c:if>
											}
										};

										dialog.on('visibleChange', dialog._dialogAction);
									}
								</aui:script>
							</aui:fieldset>
						</aui:fieldset-group>

						<c:if test="<%= !isPreviewBeforeRestoreState %>">
							<aui:button-row>
								<c:if test="<%= kaleoDesignerDisplayContext.isPublishKaleoDefinitionVersionButtonVisible(permissionChecker, kaleoDefinitionVersion) %>">
									<aui:button onClick='<%= renderResponse.getNamespace() + "publishKaleoDefinitionVersion();" %>' primary="<%= true %>" value="<%= kaleoDesignerDisplayContext.getPublishKaleoDefinitionVersionButtonLabel(kaleoDefinitionVersion) %>" />
								</c:if>

								<c:if test="<%= kaleoDesignerDisplayContext.isSaveKaleoDefinitionVersionButtonVisible(kaleoDefinitionVersion) %>">
									<aui:button onClick='<%= renderResponse.getNamespace() + "saveKaleoDefinitionVersion();" %>' value="save" />
								</c:if>

								<c:if test="<%= Validator.isNotNull(closeRedirect) %>">
									<aui:button onClick='<%= renderResponse.getNamespace() + "closeKaleoDialog();" %>' value="cancel" />
								</c:if>

								<span class="lfr-portlet-workflowdesigner-message" id="<portlet:namespace />toolbarMessage"></span>
							</aui:button-row>
						</c:if>
					</aui:form>
				</div>
			</div>
		</div>

		<c:if test="<%= kaleoDefinition != null %>">
			<liferay-portlet:actionURL name="duplicateWorkflowDefinition" portletName="<%= KaleoDesignerPortletKeys.KALEO_DESIGNER %>" var="duplicateWorkflowDefinition">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</liferay-portlet:actionURL>

			<div class="hide" id="<%= randomNamespace %>titleInputLocalized">
				<aui:form name='<%= randomNamespace + "form" %>'>
					<aui:input name="randomNamespace" type="hidden" value="<%= randomNamespace %>" />
					<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
					<aui:input name="name" type="hidden" value="<%= PortalUUIDUtil.generate() %>" />
					<aui:input name="content" type="hidden" value="<%= kaleoDefinition.getContent() %>" />
					<aui:input name="duplicatedDefinitionName" type="hidden" value="<%= kaleoDefinition.getName() %>" />
					<aui:input name="duplicatedDefinitionTitle" type="hidden" value="<%= HtmlUtil.escape(kaleoDefinition.getTitle(LanguageUtil.getLanguageId(request))) %>" />
					<aui:input name="defaultDuplicationTitle" type="hidden" value="<%= duplicateTitle %>" />

					<aui:fieldset>
						<aui:col>
							<aui:field-wrapper label="title">
								<liferay-ui:input-localized
									name='<%= randomNamespace + "title" %>'
									placeholder="<%= duplicateTitle %>"
									xml=""
								/>
							</aui:field-wrapper>
						</aui:col>

						<aui:col>
							<liferay-ui:message key="copy-does-not-include-revisions" />
						</aui:col>
					</aui:fieldset>
				</aui:form>
			</div>

			<aui:script use="liferay-kaleo-designer-dialogs">
				var duplicateWorkflowTitle = '<liferay-ui:message key="duplicate-workflow" />';

				Liferay.on('<portlet:namespace />duplicateDefinition', function(event) {
					Liferay.KaleoDesignerDialogs.confirmBeforeDuplicateDialog(
						this,
						'<%= duplicateWorkflowDefinition %>',
						duplicateWorkflowTitle,
						'<%= randomNamespace %>',
						'<portlet:namespace />'
					);
				});
			</aui:script>
		</c:if>
	</c:when>
	<c:otherwise>
		<div class="portlet-msg-info">
			<liferay-ui:message key="no-workflow-engine-is-deployed" />
		</div>
	</c:otherwise>
</c:choose>