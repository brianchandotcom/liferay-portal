;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-captcha': {
					base: MODULE_PATH + '/captcha/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-captcha': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'captcha_field.js',
							requires: [
								'aui-parse-content',
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/captcha/'
				},
				'field-checkbox': {
					base: MODULE_PATH + '/checkbox/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-checkbox': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'checkbox_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/checkbox/'
				},
				'field-checkbox-multiple': {
					base: MODULE_PATH + '/checkbox-multiple/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-checkbox-multiple': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'checkbox_multiple_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/checkbox-multiple/'
				},
				'field-date': {
					base: MODULE_PATH + '/date/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-date': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'date_field.js',
							requires: [
								'aui-datepicker',
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/date/'
				},
				'field-document-library': {
					base: MODULE_PATH + '/document-library/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-document-library': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'document_library_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/document-library/'
				},
				'field-editor': {
					base: MODULE_PATH + '/editor/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-editor': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'editor_field.js',
							requires: [
								'liferay-alloy-editor',
								'liferay-ddm-form-field-text',
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/editor/'
				},
				'field-fieldset': {
					base: MODULE_PATH + '/fieldset/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-fieldset': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'fieldset_field.js',
							requires: [
								'liferay-ddm-form-field-fieldset-util',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-fieldset-util': {
							path: 'fieldset_field_util.js',
							requires: [
								'aui-component',
								'liferay-ddm-form-renderer-util'
							]
						}
					},
					root: MODULE_PATH + '/fieldset/'
				},
				'field-grid': {
					base: MODULE_PATH + '/grid/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-grid': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'grid_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/grid/'
				},
				'field-key-value': {
					base: MODULE_PATH + '/key-value/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-key-value': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'key_value_field.js',
							requires: [
								'aui-text-unicode',
								'liferay-ddm-form-field-text',
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/key-value/'
				},
				'field-numeric': {
					base: MODULE_PATH + '/numeric/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-numeric': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'numeric_field.js',
							requires: [
								'aui-tooltip',
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/numeric/'
				},
				'field-options': {
					base: MODULE_PATH + '/options/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-options': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'options_field.js',
							requires: [
								'aui-sortable-list',
								'liferay-ddm-form-field-key-value',
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/options/'
				},
				'field-paragraph': {
					base: MODULE_PATH + '/paragraph/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-paragraph': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'paragraph_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/paragraph/'
				},
				'field-password': {
					base: MODULE_PATH + '/password/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-password': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'password_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/password/'
				},
				'field-radio': {
					base: MODULE_PATH + '/radio/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-radio': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'radio_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/radio/'
				},
				'field-select': {
					base: MODULE_PATH + '/select/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-select': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'select_field.js',
							requires: [
								'aui-tooltip',
								'liferay-ddm-form-field-select-search-support',
								'liferay-ddm-form-renderer-field',
								'liferay-ddm-soy-template-util'
							]
						},
						'liferay-ddm-form-field-select-search-support': {
							path: 'select_search_support.js',
							requires: [
								'highlight',
								'liferay-ddm-soy-template-util'
							]
						}
					},
					root: MODULE_PATH + '/select/'
				},
				'field-text': {
					base: MODULE_PATH + '/text/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-text': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'text_field.js',
							requires: [
								'aui-tooltip',
								'autocomplete',
								'autocomplete-highlighters',
								'autocomplete-highlighters-accentfold',
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/text/'
				},
				'field-validation': {
					base: MODULE_PATH + '/validation/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-validation': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'validation_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/validation/'
				}
			}
		}
	);
})();