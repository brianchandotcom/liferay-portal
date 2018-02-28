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

<%@ include file="/admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

DDMFormInstance formInstance = ddmFormAdminDisplayContext.getDDMFormInstance();

long formInstanceId = BeanParamUtil.getLong(formInstance, request, "formInstanceId");
long groupId = BeanParamUtil.getLong(formInstance, request, "groupId", scopeGroupId);
long ddmStructureId = BeanParamUtil.getLong(formInstance, request, "structureId");

String defaultLanguageId = ddmFormAdminDisplayContext.getDefaultLanguageId();

Locale[] availableLocales = ddmFormAdminDisplayContext.getAvailableLocales();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((formInstance == null) ? LanguageUtil.get(request, "new-form") : LanguageUtil.get(request, "edit-form"));
%>

<div class="loading-animation" id="<portlet:namespace />loader"></div>

<portlet:actionURL name="saveFormInstance" var="saveFormInstanceURL">
	<portlet:param name="mvcPath" value="/admin/edit_form_instance.jsp" />
</portlet:actionURL>

<div class="hide portlet-forms" id="<portlet:namespace />formContainer">
	<aui:nav-bar cssClass="collapse-basic-search" id="toolbar" markupView="lexicon">
		<aui:nav cssClass="navbar-nav">
			<aui:nav-item id="showForm" label='<%= LanguageUtil.get(request, "builder") %>' selected="<%= true %>" />
			<aui:nav-item id="showRules" label='<%= LanguageUtil.get(request, "rules") %>' />
		</aui:nav>
	</aui:nav-bar>

	<button class="btn btn-primary lfr-ddm-add-field lfr-ddm-plus-button">
		<svg class="lexicon-icon">
			<use xlink:href="<%= ddmFormAdminDisplayContext.getLexiconIconsPath() %>plus" />
		</svg>
	</button>

	<div class="autosave-bar management-bar management-bar-default">
		<div class="container-fluid-1280">
			<div class="toolbar">
				<div class="toolbar-group-field">
				</div>

				<div class="toolbar-group-content">
					<span class="autosave-feedback management-bar-text" id="<portlet:namespace />autosaveMessage"></span>
				</div>

				<div class="toolbar-group-field">
					<button class="btn btn-link publish-icon <%= (formInstance == null) ? "hide" : "" %>" data-original-title="<liferay-ui:message key="copy-url" />" id="<portlet:namespace />publishIcon" type="button" title="<liferay-ui:message key="copy-url" />">
						<svg class="lexicon-icon">
							<use xlink:href="<%= ddmFormAdminDisplayContext.getLexiconIconsPath() %>link" />
						</svg>
					</button>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid-1280">
		<aui:translation-manager
			availableLocales="<%= availableLocales %>"
			changeableDefaultLanguage="<%= false %>"
			defaultLanguageId="<%= defaultLanguageId %>"
			id="translationManager"
		/>
	</div>

	<aui:form action="<%= saveFormInstanceURL %>" cssClass="ddm-form-builder-form" enctype="multipart/form-data" method="post" name="editForm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="formInstanceId" type="hidden" value="<%= formInstanceId %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
		<aui:input name="serializedSettingsContext" type="hidden" value="" />

		<%@ include file="/admin/exceptions.jspf" %>

		<div class="ddm-form-basic-info">
			<div class="container-fluid-1280">
				<h1>
					<liferay-ui:input-editor
						autoCreate="<%= false %>"
						contents="<%= HtmlUtil.escape(ddmFormAdminDisplayContext.getFormName()) %>"
						cssClass="ddm-form-name"
						editorName="alloyeditor"
						name="nameEditor"
						placeholder="untitled-form"
						showSource="<%= false %>"
					/>
				</h1>

				<aui:input name="name" type="hidden" />

				<h5>
					<liferay-ui:input-editor
						autoCreate="<%= false %>"
						contents="<%= HtmlUtil.escape(ddmFormAdminDisplayContext.getFormDescription()) %>"
						cssClass="ddm-form-description h5"
						editorName="alloyeditor"
						name="descriptionEditor"
						placeholder="add-a-short-description-for-this-form"
						showSource="<%= false %>"
					/>
				</h5>

				<aui:input name="description" type="hidden" />
			</div>
		</div>

		<div class="container-fluid-1280 ddm-form-builder-app">
			<aui:input name="serializedFormBuilderContext" type="hidden" />

			<div id="<portlet:namespace />formBuilder"></div>
			<div id="<portlet:namespace />ruleBuilder"></div>
		</div>

		<div class="container-fluid-1280">
			<aui:button-row cssClass="ddm-form-builder-buttons">
				<aui:button cssClass="btn-primary ddm-button" id="publish" value='<%= ddmFormAdminDisplayContext.isFormPublished() ? "unpublish-form": "publish-form" %>' />

				<aui:button cssClass="ddm-button" id="save" value="save-form" />

				<aui:button cssClass="btn-link" id="preview" value="preview-form" />
			</aui:button-row>
		</div>

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="publishFormInstance" var="publishFormInstanceURL" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="saveFormInstance" var="autoSaveFormInstanceURL">
			<portlet:param name="autoSave" value="<%= Boolean.TRUE.toString() %>" />
		</liferay-portlet:resourceURL>

		<liferay-form:ddm-form-builder
			ddmStructureId="<%= ddmFormAdminDisplayContext.getDDMStructureId() %>"
			defaultLanguageId="<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>"
			editingLanguageId="<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>"
			fieldSetsClassNameId="<%= PortalUtil.getClassNameId(DDMFormInstance.class) %>"
			refererPortletNamespace="<%= liferayPortletResponse.getNamespace() %>"
		/>

		<aui:script>
			Liferay.namespace('DDM').FormSettings = {
				autosaveInterval: '<%= ddmFormAdminDisplayContext.getAutosaveInterval() %>',
				autosaveURL: '<%= autoSaveFormInstanceURL.toString() %>',
				portletNamespace: '<portlet:namespace />',
				publishFormInstanceURL: '<%= publishFormInstanceURL.toString() %>',
				restrictedFormURL: '<%= ddmFormAdminDisplayContext.getRestrictedFormURL() %>',
				showPagination: true,
				sharedFormURL: '<%= ddmFormAdminDisplayContext.getSharedFormURL() %>'
			};

			var initHandler = Liferay.after(
				'form:registered',
				function(event) {
					if (event.formName === '<portlet:namespace />editForm') {
						initHandler.detach();

						var fieldTypes = <%= ddmFormAdminDisplayContext.getDDMFormFieldTypesJSONArray() %>;

						var systemFieldModules = fieldTypes.filter(
							function(item) {
								return item.system;
							}
						).map(
							function(item) {
								return item.javaScriptModule;
							}
						);

						Liferay.provide(
							window,
							'<portlet:namespace />init',
							function() {
								Liferay.DDM.SoyTemplateUtil.loadModules(
									function() {
										Liferay.DDM.Renderer.FieldTypes.register(fieldTypes);

										<portlet:namespace />registerFormPortlet(event.form);
									}
								);
							},
							['liferay-ddm-form-portlet', 'liferay-ddm-soy-template-util'].concat(systemFieldModules)
						);

						<portlet:namespace />init();
					}
				}
			);

			function <portlet:namespace />registerFormPortlet(form) {
				Liferay.component(
					'formPortlet',
					new Liferay.DDM.FormPortlet(
						{
							localizedDescription: <%= ddmFormAdminDisplayContext.getFormLocalizedDescription() %>,
							localizedName: <%= ddmFormAdminDisplayContext.getFormLocalizedName() %>,
							defaultLanguageId: '<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>',
							editingLanguageId: '<%= ddmFormAdminDisplayContext.getDefaultLanguageId() %>',
							editForm: form,
							formBuilder: Liferay.component('<portlet:namespace />formBuilder'),
							namespace: '<portlet:namespace />',
							published: !!<%= ddmFormAdminDisplayContext.isFormPublished() %>,
							publishFormInstanceURL: '<%= publishFormInstanceURL.toString() %>',
							formInstanceId: <%= formInstanceId %>,
							ruleBuilder: Liferay.component('<portlet:namespace />ruleBuilder'),
							translationManager: Liferay.component('<portlet:namespace />translationManager')
						}
					)
				);
			}
		</aui:script>
	</aui:form>

	<div class="container-fluid-1280 ddm-form-instance-settings hide" id="<portlet:namespace />settings">
		<%= request.getAttribute(DDMWebKeys.DYNAMIC_DATA_MAPPING_FORM_HTML) %>
	</div>

	<aui:script use="aui-base">
		Liferay.namespace('FormPortlet').destroySettings = function() {
			var settingsNode = A.one('#<portlet:namespace />settingsModal');

			if (settingsNode) {
				Liferay.Util.getWindow('<portlet:namespace />settingsModal').destroy();
			}
		};

		var clearPortletHandlers = function(event) {
			if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
				Liferay.namespace('FormPortlet').destroySettings();

				Liferay.detach('destroyPortlet', clearPortletHandlers);
			}
		};

		Liferay.on('destroyPortlet', clearPortletHandlers);

		Liferay.namespace('DDM').openSettings = function() {
			Liferay.Util.openWindow(
				{
					dialog: {
						cssClass: 'ddm-form-settings-modal',
						height: 620,
						resizable: false,
						'toolbars.footer': [
							{
								cssClass: 'btn-primary',
								label: '<liferay-ui:message key="done" />',
								on: {
									click: function() {
										var ddmForm = Liferay.component('settingsDDMForm');

										ddmForm.validate(
											function(hasErrors) {
												if (!hasErrors) {
													Liferay.Util.getWindow('<portlet:namespace />settingsModal').hide();
												}
											}
										);
									}
								}
							},
							{
								cssClass: 'btn-link',
								label: '<liferay-ui:message key="cancel" />',
								on: {
									click: function() {
										Liferay.Util.getWindow('<portlet:namespace />settingsModal').hide();
									}
								}
							}
						],
						width: 720
					},
					id: '<portlet:namespace />settingsModal',
					title: '<liferay-ui:message key="form-settings" />'
				},
				function(dialogWindow) {
					var bodyNode = dialogWindow.bodyNode;

					var settingsNode = A.one('#<portlet:namespace />settings');

					settingsNode.show();

					bodyNode.append(settingsNode);
				}
			);
		};
	</aui:script>

	<%@ include file="/admin/copy_form_publish_url.jspf" %>
</div>