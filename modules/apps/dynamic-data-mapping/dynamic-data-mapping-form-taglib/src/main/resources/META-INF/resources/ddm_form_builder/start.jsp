<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/ddm_form_builder/init.jsp" %>

<aui:script use="liferay-ddm-form-builder, liferay-ddm-form-builder-fieldset, liferay-ddm-form-builder-rule-builder">
	function initTagLib() {
		Liferay.namespace('DDM').Settings = {
			evaluatorURL: '<%= HtmlUtil.escapeJS(evaluatorURL) %>',
			fieldSetDefinitionURL:
				'<%= HtmlUtil.escapeJS(fieldSetDefinitionURL) %>',
			functionsMetadata: JSON.parse(
				'<%= HtmlUtil.escapeJS(functionsMetadata) %>'
			),
			getDataProviderInstancesURL:
				'<%= HtmlUtil.escapeJS(dataProviderInstancesURL) %>',
			getDataProviderParametersSettingsURL:
				'<%= HtmlUtil.escapeJS(dataProviderInstanceParameterSettingsURL) %>',
			getFieldTypeSettingFormContextURL:
				'<%= HtmlUtil.escapeJS(fieldSettingsDDMFormContextURL) %>',
			getFunctionsURL: '<%= HtmlUtil.escapeJS(functionsURL) %>',
			getRolesURL: '<%= HtmlUtil.escapeJS(rolesURL) %>',
			portletNamespace: '<%= HtmlUtil.escapeJS(refererPortletNamespace) %>',
		};

		Liferay.DDM.FieldSets.register(<%= fieldSetsJSONArray %>);

		Liferay.component(
			'<%= HtmlUtil.escapeJS(refererPortletNamespace) %>formBuilder',
			() => {
				return new Liferay.DDM.FormBuilder({
					context: JSON.parse(
						'<%= HtmlUtil.escapeJS(formBuilderContext) %>'
					),
					defaultLanguageId:
						'<%= HtmlUtil.escapeJS(defaultLanguageId) %>',
					editingLanguageId:
						'<%= HtmlUtil.escapeJS(editingLanguageId) %>',
					showPagination: <%= showPagination %>,
				});
			}
		);

		Liferay.component(
			'<%= HtmlUtil.escapeJS(refererPortletNamespace) %>ruleBuilder',
			() => {
				return new Liferay.DDM.FormBuilderRuleBuilder({
					formBuilder: Liferay.component(
						'<%= HtmlUtil.escapeJS(refererPortletNamespace) %>formBuilder'
					),
					rules: JSON.parse(
						'<%= HtmlUtil.escapeJS(serializedDDMFormRules) %>'
					),
					visible: false,
				});
			}
		);

		Liferay.fire('DDMFormBuilderReady');
		Liferay.DDM.FormBuilder.ready = true;
	}

	Liferay.Loader.require.apply(Liferay.Loader, [
		'<%= npmResolvedPackageName %>/alloy/templates/autocomplete.es',
		'<%= npmResolvedPackageName %>/alloy/templates/calculate.es',
		'<%= npmResolvedPackageName %>/alloy/templates/calculator.es',
		'<%= npmResolvedPackageName %>/alloy/templates/data-provider-parameter.es',
		'<%= npmResolvedPackageName %>/alloy/templates/field-options-toolbar.es',
		'<%= npmResolvedPackageName %>/alloy/templates/field-types-sidebar.es',
		'<%= npmResolvedPackageName %>/alloy/templates/rule-builder.es',
		'<%= npmResolvedPackageName %>/alloy/templates/rule.es',
		'<%= npmResolvedPackageName %>/alloy/templates/sidebar.es',
		initTagLib,
	]);
</aui:script>