;(function() {
	var LiferayAUI = Liferay.AUI;

	var PATH_FORM_RENDERER = Liferay.ThemeDisplay.getPathContext() + '/o/dynamic-data-mapping-form-renderer';

	AUI().applyConfig(
		{
			groups: {
				'form': {
					base: PATH_FORM_RENDERER + '/js/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-renderer': {
							path: 'form.js',
							requires: [
								'aui-component',
								'aui-tabview',
								'liferay-ddm-form-renderer-definition',
								'liferay-ddm-form-renderer-feedback',
								'liferay-ddm-form-renderer-nested-fields',
								'liferay-ddm-form-renderer-pagination',
								'liferay-ddm-form-renderer-tabs',
								'liferay-ddm-form-renderer-template',
								'liferay-ddm-form-renderer-types',
								'liferay-ddm-form-renderer-util',
								'liferay-ddm-form-renderer-validation',
								'liferay-ddm-form-soy'
							]
						},
						'liferay-ddm-form-renderer-definition': {
							path: 'form_definition_support.js',
							requires: [
								'liferay-ddm-form-renderer-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-expressions-evaluator': {
							path: 'expressions_evaluator.js',
							requires: [
								'aui-component',
								'aui-io-request'
							]
						},
						'liferay-ddm-form-renderer-feedback': {
							path: 'form_feedback_support.js',
							requires: [
								'aui-alert'
							]
						},
						'liferay-ddm-form-renderer-field': {
							path: 'field.js',
							requires: [
								'aui-datatype',
								'aui-node',
								'liferay-ddm-form-renderer',
								'liferay-ddm-form-renderer-field-events',
								'liferay-ddm-form-renderer-field-feedback',
								'liferay-ddm-form-renderer-field-repetition',
								'liferay-ddm-form-renderer-field-validation',
								'liferay-ddm-form-renderer-nested-fields',
								'liferay-ddm-form-renderer-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-field-events': {
							path: 'field_events_support.js',
							requires: []
						},
						'liferay-ddm-form-renderer-field-feedback': {
							path: 'field_feedback_support.js',
							requires: [
								'aui-node'
							]
						},
						'liferay-ddm-form-renderer-field-repetition': {
							path: 'field_repetition_support.js',
							requires: [
								'aui-datatype',
								'liferay-ddm-form-renderer-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-field-validation': {
							path: 'field_validation_support.js',
							requires: [
								'liferay-ddm-form-renderer-expressions-evaluator'
							]
						},
						'liferay-ddm-form-renderer-field-visibility': {
							path: 'field_visibility_support.js',
							requires: [
								'liferay-ddm-form-renderer-expressions-evaluator',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-nested-fields': {
							path: 'nested_fields_support.js',
							requires: [
								'array-invoke',
								'liferay-ddm-form-renderer-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-pagination': {
							path: 'form_pagination_support.js',
							requires: [
								'aui-pagination',
								'liferay-ddm-form-renderer-wizard'
							]
						},
						'liferay-ddm-form-renderer-tabs': {
							path: 'form_tabs_support.js',
							requires: [
								'aui-tabview'
							]
						},
						'liferay-ddm-form-renderer-template': {
							path: 'form_template_support.js',
							requires: [
								'aui-base'
							]
						},
						'liferay-ddm-form-renderer-types': {
							path: 'types.js',
							requires: [
								'array-extras',
								'aui-form-builder-field-type',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-util': {
							path: 'util.js',
							requires: [
								'liferay-ddm-form-renderer-types',
								'queue'
							]
						},
						'liferay-ddm-form-renderer-validation': {
							path: 'form_validation_support.js',
							requires: [
								'aui-alert',
								'liferay-ddm-form-renderer-expressions-evaluator'
							]
						},
						'liferay-ddm-form-renderer-wizard': {
							path: 'wizard.js',
							requires: [
								'aui-component',
								'aui-node',
								'widget'
							]
						}
					},
					root: PATH_FORM_RENDERER + '/js/'
				},
				'templates': {
					base: PATH_FORM_RENDERER + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-ddm-form-soy': {
							path: 'form.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: PATH_FORM_RENDERER + '/'
				}
			}
		}
	);
})();