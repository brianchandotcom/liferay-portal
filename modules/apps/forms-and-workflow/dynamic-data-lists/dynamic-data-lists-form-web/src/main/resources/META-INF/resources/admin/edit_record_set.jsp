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

DDLRecordSet recordSet = ddlFormAdminDisplayContext.getRecordSet();

long recordSetId = BeanParamUtil.getLong(recordSet, request, "recordSetId");
long groupId = BeanParamUtil.getLong(recordSet, request, "groupId", scopeGroupId);
long ddmStructureId = BeanParamUtil.getLong(recordSet, request, "DDMStructureId");
boolean showPublishModal = ParamUtil.getBoolean(request, "showPublishModal");

String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

Locale[] availableLocales = ddlFormAdminDisplayContext.getAvailableLocales();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((recordSet == null) ? LanguageUtil.get(request, "new-form") : LanguageUtil.get(request, "edit-form"));
%>

<div class="loading-animation" id="<portlet:namespace />loader"></div>

<portlet:actionURL name="saveRecordSet" var="saveRecordSetURL">
	<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
</portlet:actionURL>

<div class="hide portlet-forms" id="<portlet:namespace />formContainer">
	<aui:nav-bar cssClass="collapse-basic-search" id="toolbar" markupView="lexicon">
		<aui:nav cssClass="navbar-nav">
			<aui:nav-item id="showForm" label='<%= LanguageUtil.get(request, "builder") %>' selected="<%= true %>" />
			<aui:nav-item id="showRules" label='<%= LanguageUtil.get(request, "rules") %>' />
		</aui:nav>
	</aui:nav-bar>

	<div class="autosave-bar management-bar management-bar-default">
		<div class="container-fluid-1280">
			<div class="toolbar">
				<div class="toolbar-group-field">
				</div>

				<div class="toolbar-group-content">
					<span class="autosave-feedback management-bar-text" id="<portlet:namespace />autosaveMessage"></span>
				</div>

				<div class="toolbar-group-field">
					<button class="btn btn-link publish-icon <%= (recordSet == null) ? "hide" : "" %>" data-original-title="<liferay-ui:message key="copy-url" />" id="<portlet:namespace />publishIcon" type="button" title="<liferay-ui:message key="copy-url" />">
						<svg class="lexicon-icon">
							<use xlink:href="<%= ddlFormAdminDisplayContext.getLexiconIconsPath() %>link" />
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

	<aui:form action="<%= saveRecordSetURL %>" cssClass="ddl-form-builder-form" enctype="multipart/form-data" method="post" name="editForm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="recordSetId" type="hidden" value="<%= recordSetId %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
		<aui:input name="serializedSettingsContext" type="hidden" value="" />

		<liferay-ui:error exception="<%= DDMFormLayoutValidationException.class %>" message="please-enter-a-valid-form-layout" />

		<liferay-ui:error exception="<%= DDMFormLayoutValidationException.MustNotDuplicateFieldName.class %>">

			<%
			DDMFormLayoutValidationException.MustNotDuplicateFieldName mndfn = (DDMFormLayoutValidationException.MustNotDuplicateFieldName)errorException;
			%>

			<liferay-ui:message arguments="<%= StringUtil.merge(mndfn.getDuplicatedFieldNames(), StringPool.COMMA_AND_SPACE) %>" key="the-definition-field-name-x-was-defined-more-than-once" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= DDMFormValidationException.class %>" message="please-enter-a-valid-form-definition" />

		<liferay-ui:error exception="<%= DDMFormValidationException.MustNotDuplicateFieldName.class %>">

			<%
			DDMFormValidationException.MustNotDuplicateFieldName mndfn = (DDMFormValidationException.MustNotDuplicateFieldName)errorException;
			%>

			<liferay-ui:message arguments="<%= mndfn.getFieldName() %>" key="the-definition-field-name-x-was-defined-more-than-once" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= DDMFormValidationException.MustSetFieldsForForm.class %>" message="please-add-at-least-one-field" />

		<liferay-ui:error exception="<%= DDMFormValidationException.MustSetOptionsForField.class %>">

			<%
			DDMFormValidationException.MustSetOptionsForField msoff = (DDMFormValidationException.MustSetOptionsForField)errorException;
			%>

			<liferay-ui:message arguments="<%= msoff.getFieldName() %>" key="at-least-one-option-should-be-set-for-field-x" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= DDMFormValidationException.MustSetValidCharactersForFieldName.class %>">

			<%
			DDMFormValidationException.MustSetValidCharactersForFieldName msvcffn = (DDMFormValidationException.MustSetValidCharactersForFieldName)errorException;
			%>

			<liferay-ui:message arguments="<%= msvcffn.getFieldName() %>" key="invalid-characters-were-defined-for-field-name-x" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= DDMFormValidationException.MustSetValidVisibilityExpression.class %>">

			<%
			DDMFormValidationException.MustSetValidVisibilityExpression msvve = (DDMFormValidationException.MustSetValidVisibilityExpression)errorException;
			%>

			<liferay-ui:message arguments="<%= new Object[] {msvve.getVisibilityExpression(), msvve.getFieldName()} %>" key="the-visibility-expression-x-set-for-field-x-is-invalid" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= RecordSetNameException.class %>" message="please-enter-a-valid-form-name" />
		<liferay-ui:error exception="<%= RecordSetSettingsRedirectURLException.class %>" message="the-specified-redirect-url-is-not-allowed" />
		<liferay-ui:error exception="<%= StorageException.class %>" message="please-enter-a-valid-form-settings" />
		<liferay-ui:error exception="<%= StructureDefinitionException.class %>" message="please-enter-a-valid-form-definition" />
		<liferay-ui:error exception="<%= StructureLayoutException.class %>" message="please-enter-a-valid-form-layout" />
		<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-form-name" />

		<aui:fieldset cssClass="ddl-form-basic-info">
			<div class="container-fluid-1280">
				<h1>
					<liferay-ui:input-editor
						autoCreate="<%= false %>"
						contents="<%= HtmlUtil.escape(ddlFormAdminDisplayContext.getFormName()) %>"
						cssClass="ddl-form-name"
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
						contents="<%= HtmlUtil.escape(ddlFormAdminDisplayContext.getFormDescription()) %>"
						cssClass="ddl-form-description h5"
						editorName="alloyeditor"
						name="descriptionEditor"
						placeholder="add-a-short-description-for-this-form"
						showSource="<%= false %>"
					/>
				</h5>

				<aui:input name="description" type="hidden" />
			</div>
		</aui:fieldset>

		<aui:fieldset cssClass="container-fluid-1280 ddl-form-builder-app">
			<aui:input name="serializedFormBuilderContext" type="hidden" />

			<div id="<portlet:namespace />formBuilder"></div>
			<div id="<portlet:namespace />ruleBuilder"></div>
		</aui:fieldset>

		<div class="container-fluid-1280">
			<aui:button-row cssClass="ddl-form-builder-buttons">
				<aui:button cssClass="btn-lg btn-primary ddl-button" id="publish" value='<%= ddlFormAdminDisplayContext.isFormPublished() ? "unpublish-form": "publish-form" %>' />

				<aui:button cssClass="btn-lg ddl-button" id="save" value="save-form" />

				<aui:button cssClass="btn-lg btn-link" id="preview" value="preview-form" />
			</aui:button-row>
		</div>

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="publishRecordSet" var="publishRecordSetURL" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="saveRecordSet" var="autoSaveRecordSetURL">
			<portlet:param name="autoSave" value="<%= Boolean.TRUE.toString() %>" />
		</liferay-portlet:resourceURL>

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getDataProviderInstances" var="getDataProviderInstancesURL" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getDataProviderParametersSettings" var="getDataProviderParametersSettings" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getFieldSettingsDDMFormContext" var="getFieldSettingsDDMFormContext" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getFunctions" var="getFunctions" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getRoles" var="getRoles" />

		<aui:script>
			Liferay.namespace('DDL').Settings = {
				autosaveInterval: '<%= ddlFormAdminDisplayContext.getAutosaveInterval() %>',
				autosaveURL: '<%= autoSaveRecordSetURL.toString() %>',
				evaluatorURL: '<%= ddlFormAdminDisplayContext.getDDMFormContextProviderServletURL() %>',
				functionsMetadata: <%= ddlFormAdminDisplayContext.getSerializedDDMExpressionFunctionsMetadata() %>,
				getDataProviderInstancesURL: '<%= getDataProviderInstancesURL.toString() %>',
				getDataProviderParametersSettingsURL: '<%= getDataProviderParametersSettings.toString() %>',
				getFieldTypeSettingFormContextURL: '<%= getFieldSettingsDDMFormContext.toString() %>',
				getFunctionsURL: '<%= getFunctions.toString() %>',
				getRolesURL: '<%= getRoles.toString() %>',
				portletNamespace: '<portlet:namespace />',
				publishRecordSetURL: '<%= publishRecordSetURL.toString() %>',
				restrictedFormURL: '<%= ddlFormAdminDisplayContext.getRestrictedFormURL() %>',
				sharedFormURL: '<%= ddlFormAdminDisplayContext.getSharedFormURL() %>'
			};

			var initHandler = Liferay.after(
				'form:registered',
				function(event) {
					if (event.formName === '<portlet:namespace />editForm') {
						initHandler.detach();

						var fieldTypes = <%= ddlFormAdminDisplayContext.getDDMFormFieldTypesJSONArray() %>;

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
								Liferay.DDM.Renderer.FieldTypes.register(fieldTypes);

								Liferay.component(
									'formPortlet',
									new Liferay.DDL.Portlet(
										{
											context: <%= ddlFormAdminDisplayContext.getFormBuilderContext() %>,
											localizedDescription: <%= ddlFormAdminDisplayContext.getFormLocalizedDescription() %>,
											localizedName: <%= ddlFormAdminDisplayContext.getFormLocalizedName() %>,
											defaultLanguageId: '<%= ddlFormAdminDisplayContext.getDefaultLanguageId() %>',
											editingLanguageId: '<%= ddlFormAdminDisplayContext.getDefaultLanguageId() %>',
											editForm: event.form,
											namespace: '<portlet:namespace />',
											published: !!<%= ddlFormAdminDisplayContext.isFormPublished() %>,
											publishRecordSetURL: '<%= publishRecordSetURL.toString() %>',
											recordSetId: <%= recordSetId %>,
											rules: <%= ddlFormAdminDisplayContext.getSerializedDDMFormRules() %>,
											translationManager: Liferay.component('<portlet:namespace />translationManager')
										}
									)
								);
							},
							['liferay-ddl-portlet'].concat(systemFieldModules)
						);

						<portlet:namespace />init();
					}
				}
			);

			var clearPortletHandlers = function(event) {
				if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
					Liferay.namespace('DDL').destroySettings();

					Liferay.detach('destroyPortlet', clearPortletHandlers);
				}
			};

			Liferay.on('destroyPortlet', clearPortletHandlers);
		</aui:script>
	</aui:form>

	<div class="container-fluid-1280 ddl-record-set-settings hide" id="<portlet:namespace />settings">
		<%= request.getAttribute(DDMWebKeys.DYNAMIC_DATA_MAPPING_FORM_HTML) %>
	</div>

	<aui:script use="aui-base">
		Liferay.namespace('DDL').destroySettings = function() {
			var settingsNode = A.one('#<portlet:namespace />settingsModal');

			if (settingsNode) {
				Liferay.Util.getWindow('<portlet:namespace />settingsModal').destroy();
			}
		};

		Liferay.namespace('DDL').openSettings = function() {
			Liferay.Util.openWindow(
				{
					dialog: {
						cssClass: 'ddl-form-settings-modal',
						height: 620,
						resizable: false,
						'toolbars.footer': [
							{
								cssClass: 'btn-lg btn-primary',
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
								cssClass: 'btn-lg btn-link',
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