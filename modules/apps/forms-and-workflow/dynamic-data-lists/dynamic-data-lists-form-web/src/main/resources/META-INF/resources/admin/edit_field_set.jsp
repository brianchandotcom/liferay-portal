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

DDMStructure ddmStructure = ddlFormAdminFieldLibraryDisplayContext.getDDMStructure();

long groupId = BeanParamUtil.getLong(ddmStructure, request, "groupId", scopeGroupId);
long ddmStructureId = 0;

if (ddmStructure != null) {
	ddmStructureId = ddmStructure.getStructureId();
}

String name = BeanParamUtil.getString(ddmStructure, request, "name");
String description = BeanParamUtil.getString(ddmStructure, request, "description");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((ddmStructure == null) ? LanguageUtil.get(request, "new-field-set") : LanguageUtil.get(request, "edit-field-set"));
%>

<div class="loading-animation" id="<portlet:namespace />loader"></div>

<portlet:actionURL name="saveFieldSet" var="saveFieldSetURL">
	<portlet:param name="mvcPath" value="/admin/edit_field_set.jsp" />
</portlet:actionURL>

<div class="hide portlet-forms" id="<portlet:namespace />formContainer">
	<aui:form action="<%= saveFieldSetURL %>" cssClass="ddl-form-builder-form" method="post" name="editForm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="ddmStructureId" type="hidden" value="<%= ddmStructureId %>" />

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

		<liferay-ui:error exception="<%= StorageException.class %>" message="please-enter-a-valid-form-settings" />
		<liferay-ui:error exception="<%= StructureDefinitionException.class %>" message="please-enter-a-valid-form-definition" />
		<liferay-ui:error exception="<%= StructureLayoutException.class %>" message="please-enter-a-valid-form-layout" />
		<liferay-ui:error exception="<%= StructureNameException.class %>" message="please-enter-a-valid-form-name" />

		<aui:fieldset cssClass="ddl-form-basic-info">
			<div class="container-fluid-1280">
				<h1>
					<liferay-ui:input-editor
						autoCreate="<%= false %>"
						contents="<%= HtmlUtil.escape(LocalizationUtil.getLocalization(name, themeDisplay.getLanguageId())) %>"
						cssClass="ddl-form-name"
						editorName="alloyeditor"
						name="nameEditor"
						placeholder="untitled-field-set"
						showSource="<%= false %>"
					/>
				</h1>

				<aui:input name="name" type="hidden" />

				<h5>
					<liferay-ui:input-editor
						autoCreate="<%= false %>"
						contents="<%= HtmlUtil.escape(LocalizationUtil.getLocalization(description, themeDisplay.getLanguageId())) %>"
						cssClass="ddl-form-description h5"
						editorName="alloyeditor"
						name="descriptionEditor"
						placeholder="add-a-short-description-for-this-field-set"
						showSource="<%= false %>"
					/>
				</h5>

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
				<aui:button cssClass="btn-lg" id="save" type="submit" value="save" />
				<aui:button cssClass="btn-lg" href="<%= redirect %>" name="cancelButton" type="cancel" />
			</aui:button-row>
		</div>

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getDataProviderInstances" var="getDataProviderInstancesURL" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getDataProviderParametersSettings" var="getDataProviderParametersSettings" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getFieldSettingsDDMFormContext" var="getFieldSettingsDDMFormContext" />

		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="getRoles" var="getRoles" />

		<aui:script>
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
								var definition = <%= ddlFormAdminDisplayContext.getSerializedDDMForm() %>;

								Liferay.DDM.Renderer.FieldTypes.register(fieldTypes);

								Liferay.component(
									'formPortlet',
									new Liferay.DDL.Portlet(
										{
											availableLanguageIds: definition.availableLanguageIds,
											defaultLanguageId: definition.defaultLanguageId,
											definition: definition,
											description: '<%= HtmlUtil.escapeJS(description) %>',
											editForm: event.form,
											emptyName: '<%= LanguageUtil.get(request, "untitled-field-set") %>',
											evaluatorURL: '<%= ddlFormAdminDisplayContext.getDDMFormContextProviderServletURL() %>',
											fieldTypesDefinitions: <%= ddlFormAdminDisplayContext.getDDMFormFieldTypesDefinitionsMap() %>,
											functionsMetadata: <%= ddlFormAdminDisplayContext.getSerializedDDMExpressionFunctionsMetadata() %>,
											getDataProviderParametersSettingsURL: '<%= getDataProviderParametersSettings.toString() %>',
											getDataProviderInstancesURL: '<%= getDataProviderInstancesURL.toString() %>',
											getDataProviderParametersSettingsURL: '<%= getDataProviderParametersSettings.toString() %>',
											getFieldTypeSettingFormContextURL: '<%= getFieldSettingsDDMFormContext.toString() %>',
											getRolesURL: '<%= getRoles.toString() %>',
											layout: <%= ddlFormAdminDisplayContext.getSerializedDDMFormLayout() %>,
											name: '<%= HtmlUtil.escapeJS(name) %>',
											namespace: '<portlet:namespace />',
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
					Liferay.detach('destroyPortlet', clearPortletHandlers);
				}
			};

			Liferay.on('destroyPortlet', clearPortletHandlers);
		</aui:script>
	</aui:form>
</div>