;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-checkbox': {
					base: '/o/ddm-type-checkbox/',
					modules: {
						'liferay-ddm-form-field-checkbox': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'checkbox_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-checkbox-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'checkbox.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: '/o/ddm-type-checkbox/'
				},
				'field-options': {
					base: '/o/ddm-type-options/',
					modules: {
						'liferay-ddm-form-field-options': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'options_field.js',
							requires: [
								'liferay-auto-fields',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-options-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'options.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: '/o/ddm-type-options/'
				},
				'field-radio': {
					base: '/o/ddm-type-radio/',
					modules: {
						'liferay-ddm-form-field-radio': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'radio_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-radio-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'radio.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: '/o/ddm-type-radio/'
				},
				'field-select': {
					base: '/o/ddm-type-select/',
					modules: {
						'liferay-ddm-form-field-select': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'select_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-select-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'select.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: '/o/ddm-type-select/'
				},
				'field-text': {
					base: '/o/ddm-type-text/',
					modules: {
						'liferay-ddm-form-field-text-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'text.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: '/o/ddm-type-text/'
				},
				'form': {
					base: '/o/ddm-form-renderer/js/',
					modules: {
						'liferay-ddm-form-renderer': {
							path: 'form.js',
							requires: [
								'aui-component',
								'aui-tabview',
								'liferay-ddm-form-renderer-field-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-definition': {
							path: 'form_definition_support.js',
							requires: [
								'liferay-ddm-form-renderer-field-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-field': {
							path: 'field.js',
							requires: [
								'aui-datatype',
								'aui-node',
								'liferay-ddm-form-renderer',
								'liferay-ddm-form-renderer-field-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-field-repetition': {
							path: 'field_repetition_support.js',
							requires: [
								'aui-datatype',
								'liferay-ddm-form-renderer-field-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-field-types': {
							path: 'field_types.js',
							requires: [
								'array-extras',
								'aui-form-builder-field-type',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-nested-fields': {
							path: 'nested_fields_support.js',
							requires: [
								'liferay-ddm-form-renderer-field-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-util': {
							path: 'util.js',
							requires: [
								'liferay-ddm-form-renderer-field-types',
								'queue'
							]
						}
					},
					root: '/o/ddm-form-renderer/js/'
				}
			}
		}
	);
})();