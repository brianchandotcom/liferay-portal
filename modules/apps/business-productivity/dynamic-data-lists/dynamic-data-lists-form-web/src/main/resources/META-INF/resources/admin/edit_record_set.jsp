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
String name = BeanParamUtil.getString(recordSet, request, "name");
String description = BeanParamUtil.getString(recordSet, request, "description");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((recordSet == null) ? LanguageUtil.get(request, "new-form") : LanguageUtil.get(request, "edit-form"));
%>

<div class="loading-animation" id="<portlet:namespace />loader"></div>

<portlet:actionURL name="addRecordSet" var="addRecordSetURL">
	<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
</portlet:actionURL>

<portlet:actionURL name="updateRecordSet" var="updateRecordSetURL">
	<portlet:param name="mvcPath" value="/admin/edit_record_set.jsp" />
</portlet:actionURL>

<div class="hide portlet-forms" id="<portlet:namespace />formContainer">
	<aui:form action="<%= (recordSet == null) ? addRecordSetURL : updateRecordSetURL %>" cssClass="ddl-form-builder-form" method="post" name="editForm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="recordSetId" type="hidden" value="<%= recordSetId %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />
		<aui:input name="serializedSettingsDDMFormValues" type="hidden" value="" />

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

		<liferay-ui:error exception="<%= RecordSetNameException.class %>" message="please-enter-a-valid-form-name" />
		<liferay-ui:error exception="<%= StorageException.class %>" message="please-enter-a-valid-form-settings" />
		<liferay-ui:error exception="<%= StructureDefinitionException.class %>" message="please-enter-a-valid-form-definition" />
		<liferay-ui:error exception="<%= StructureLayoutException.class %>" message="please-enter-a-valid-form-layout" />
		<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-form-name" />

		<aui:fieldset cssClass="ddl-form-basic-info">
			<div class="container-fluid-1280">
				<h1>
					<liferay-ui:input-editor contents="<%= HtmlUtil.escape(LocalizationUtil.getLocalization(name, themeDisplay.getLanguageId())) %>" cssClass="ddl-form-name" editorName="alloyeditor" name="nameEditor" onChangeMethod="OnChangeEditor" onInitMethod="OnInitEditor" placeholder="untitled-form" showSource="<%= false %>" />
				</h1>

				<aui:input name="name" type="hidden" />

				<h2>
					<liferay-ui:input-editor contents="<%= HtmlUtil.escape(LocalizationUtil.getLocalization(description, themeDisplay.getLanguageId())) %>" cssClass="ddl-form-description" editorName="alloyeditor" name="descriptionEditor" placeholder="add-a-short-description-for-this-form" showSource="<%= false %>" />
				</h2>

				<aui:input name="description" type="hidden" />
			</div>
		</aui:fieldset>

		<aui:fieldset cssClass="container-fluid-1280 ddl-form-builder-app">
			<aui:input name="definition" type="hidden" />
			<aui:input name="layout" type="hidden" />

			<div id="<portlet:namespace />formBuilder"></div>
		</aui:fieldset>

		<div class="container-fluid-1280">
			<aui:button-row cssClass="ddl-form-builder-buttons">
				<aui:button cssClass="btn-lg ddl-button" disabled="<%= true %>" id="submit" primary="<%= true %>" type="submit" value="save" />

				<aui:button cssClass="btn-lg" href="<%= redirect %>" name="cancelButton" type="cancel" />
			</aui:button-row>
		</div>

		<aui:script>
			function <portlet:namespace />OnChangeEditor(html) {
				Liferay.Util.toggleDisabled('#<portlet:namespace />submit', !html.trim());
			}

			function <portlet:namespace />OnInitEditor() {
				var html = window['<portlet:namespace />nameEditor'].getHTML();

				Liferay.Util.toggleDisabled('#<portlet:namespace />submit', !html.trim());
			}
		</aui:script>

		<div class="container-fluid-1280 ddl-publish-modal hide" id="<portlet:namespace />publishModal">
			<div class="alert alert-info">
				<a href="<%= ddlFormAdminDisplayContext.getPreviewFormURL() %>" target="_blank">
					<liferay-ui:message key="click-here-to-preview-the-form-in-a-new-window" />
				</a>
			</div>

			<div class="form-group">
				<label class="control-label ddl-publish-checkbox" for="<portlet:namespace />publishCheckbox">
					<span class="pull-left">
						<liferay-ui:message key="publish-form" />

						<small><liferay-ui:message key="make-this-form-public" /></small>
					</span>

					<aui:input label="" name="publishCheckbox" type="toggle-switch" value="<%= ddlFormAdminDisplayContext.isFormPublished() %>" />
				</label>
			</div>

			<div class="form-group">
				<label><liferay-ui:message key="copy-this-url-to-share-the-form" /></label>

				<div class="input-group">
					<input class="form-control" type="text" readOnly value="<%= ddlFormAdminDisplayContext.getPublishedFormURL() %>" />

					<span class="input-group-btn">
						<button class="btn btn-default" type="button"><liferay-ui:message key="copy-url" /></button>
					</span>
				</div>
			</div>
		</div>

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="publishRecordSet" var="publishRecordSetURL" />

		<aui:script>
			var initHandler = Liferay.after(
				'form:registered',
				function(event) {
					if (event.formName === '<portlet:namespace />editForm') {
						initHandler.detach();

						var fieldTypes = <%= ddlFormAdminDisplayContext.getDDMFormFieldTypesJSONArray() %>;

						var fieldModules = _.map(
							fieldTypes,
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
											dataProviders: <%= ddlFormAdminDisplayContext.getSerializedDDMDataProviders() %>,
											definition: <%= ddlFormAdminDisplayContext.getSerializedDDMForm() %>,
											description: '<%= HtmlUtil.escapeJS(description) %>',
											editForm: event.form,
											layout: <%= ddlFormAdminDisplayContext.getSerializedDDMFormLayout() %>,
											name: '<%= HtmlUtil.escapeJS(name) %>',
											namespace: '<portlet:namespace />',
											publishRecordSetURL: '<%= publishRecordSetURL.toString() %>',
											recordSetId: <%= recordSetId %>
										}
									)
								);
							},
							['liferay-ddl-portlet'].concat(fieldModules)
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
</div>