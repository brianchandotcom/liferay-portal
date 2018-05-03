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

<%@ include file="/ddm_form_builder/init.jsp" %>

<aui:script use="liferay-ddm-form-builder, liferay-ddm-form-builder-fieldset, liferay-ddm-form-builder-rule-builder">

	Liferay.namespace('DDM').Settings = {
		evaluatorURL: '<%= evaluatorURL %>',
		fieldSetDefinitionURL: '<%= fieldSetDefinitionURL %>',
		functionsMetadata: <%= functionsMetadata %>,
		getDataProviderInstancesURL: '<%= dataProviderInstancesURL %>',
		getDataProviderParametersSettingsURL: '<%= dataProviderInstanceParameterSettingsURL %>',
		getFieldTypeSettingFormContextURL: '<%= fieldSettingsDDMFormContextURL %>',
		getFunctionsURL: '<%= functionsURL %>',
		getRolesURL: '<%= rolesURL %>',
		portletNamespace: '<%= refererPortletNamespace %>'
	};

	Liferay.DDM.FieldSets.register(<%= fieldSets %>);

	Liferay.component(
		'<%= refererPortletNamespace %>formBuilder',
		function() {
			return new Liferay.DDM.FormBuilder(
				{
					context: <%= formBuilderContext %>,
					defaultLanguageId: '<%= defaultLanguageId %>',
					editingLanguageId: '<%= editingLanguageId %>',
					showPagination: <%= showPagination %>
				}
			);
		}
	);

	Liferay.component(
		'<%= refererPortletNamespace %>ruleBuilder',
		function() {
			return new Liferay.DDM.FormBuilderRuleBuilder(
				{
					formBuilder: Liferay.component('<%= refererPortletNamespace %>formBuilder'),
					rules: <%= serializedDDMFormRules %>,
					visible: false
				}
			);
		}
	);
</aui:script>