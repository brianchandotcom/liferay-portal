;(function() {
	var LiferayAUI = Liferay.AUI;

	AUI().applyConfig(
		{
			groups: {
				'form': {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-renderer': {
							path: 'form.js',
							requires: [
								'aui-component',
								'aui-tabview',
								'liferay-ddm-form-renderer-context',
								'liferay-ddm-form-renderer-evaluation',
								'liferay-ddm-form-renderer-feedback',
								'liferay-ddm-form-renderer-nested-fields',
								'liferay-ddm-form-renderer-pagination',
								'liferay-ddm-form-renderer-tabs',
								'liferay-ddm-form-renderer-template',
								'liferay-ddm-form-renderer-type',
								'liferay-ddm-form-renderer-types',
								'liferay-ddm-form-renderer-util',
								'liferay-ddm-form-renderer-validation',
								'liferay-ddm-form-soy'
							]
						},
						'liferay-ddm-form-renderer-context': {
							path: 'form_context_support.js',
							requires: [
								'liferay-ddm-form-renderer-field-class-factory',
								'liferay-ddm-form-renderer-layout-visitor',
								'liferay-ddm-form-renderer-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-evaluation': {
							path: 'form_evaluation_support.js',
							requires: []
						},
						'liferay-ddm-form-renderer-expressions-evaluator': {
							path: 'expressions_evaluator.js',
							requires: [
								'aui-component',
								'aui-io-request',
								'aui-map'
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
								'liferay-ddm-form-renderer-field-evaluation',
								'liferay-ddm-form-renderer-field-events',
								'liferay-ddm-form-renderer-field-feedback',
								'liferay-ddm-form-renderer-field-repetition',
								'liferay-ddm-form-renderer-field-validation',
								'liferay-ddm-form-renderer-nested-fields',
								'liferay-ddm-form-renderer-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-field-class-factory': {
							path: 'field_class_factory.js',
							requires: [
								'liferay-ddm-form-renderer-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-field-evaluation': {
							path: 'field_evaluation_support.js',
							requires: [
								'liferay-ddm-form-renderer-expressions-evaluator',
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
						'liferay-ddm-form-renderer-layout-visitor': {
							path: 'layout_visitor.js',
							requires: ['aui-base']
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
						'liferay-ddm-form-renderer-type': {
							path: 'type.js',
							requires: [
								'aui-form-builder-field-type'
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
								'liferay-ddm-form-renderer-types'
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
					root: MODULE_PATH + '/js/'
				},
				'templates': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-ddm-form-soy': {
							path: 'form.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();