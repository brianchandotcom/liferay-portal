;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-checkbox': {
					base: '/o/ddm-type-checkbox/',
					modules: {
						'liferay-ddm-form-field-checkbox': {
							path: 'checkbox_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-checkbox-template': {
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
							path: 'options_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-options-template': {
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
							path: 'radio_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-radio-template': {
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
							path: 'select_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-select-template': {
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
						'liferay-ddm-form-field': {
							path: 'field.js',
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
								'liferay-ddm-form-field-checkbox-template',
								'liferay-ddm-form-field-radio-template',
								'liferay-ddm-form-field-select-template',
								'liferay-ddm-form-field-text-template',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer': {
							path: 'form.js',
							requires: [
								'array-extras',
								'aui-tabview',
								'liferay-ddm-form-field',
								'liferay-ddm-form-field-checkbox',
								'liferay-ddm-form-field-checkbox-template',
								'liferay-ddm-form-field-options',
								'liferay-ddm-form-field-options-template',
								'liferay-ddm-form-field-radio',
								'liferay-ddm-form-field-radio-template',
								'liferay-ddm-form-field-select',
								'liferay-ddm-form-field-select-template',
								'liferay-ddm-form-field-text-template',
								'liferay-ddm-form-renderer-field-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-util': {
							path: 'util.js',
							requires: [
								'array-extras'
							]
						}
					},
					root: '/o/ddm-form-renderer/js/'
				}
			}
		}
	);
})();