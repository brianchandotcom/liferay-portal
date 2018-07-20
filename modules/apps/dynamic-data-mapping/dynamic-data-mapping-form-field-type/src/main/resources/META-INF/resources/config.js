;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-captcha': {
					base: MODULE_PATH + '/',
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
					root: MODULE_PATH + '/'
				},
				'field-checkbox': {
					base: MODULE_PATH + '/',
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
					root: MODULE_PATH + '/'
				},
				'field-checkbox-multiple': {
					base: MODULE_PATH + '/',
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
					root: MODULE_PATH + '/'
				},
				'field-date': {
					base: MODULE_PATH + '/',
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
					root: MODULE_PATH + '/'
				},
				'field-document-library': {
					base: MODULE_PATH + '/',
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
					root: MODULE_PATH + '/'
				},
				'field-editor': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-editor': {
							path: 'editor_field.js',
							requires: [
								'liferay-alloy-editor',
								'liferay-ddm-form-field-text',
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/'
				},
				'field-fieldset': {
					base: MODULE_PATH + '/',
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
					root: MODULE_PATH + '/'
				},
				'field-grid': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
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
					root: MODULE_PATH + '/'
				},
				'field-key-value': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
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
					root: MODULE_PATH + '/'
				},
				'field-numeric': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-numeric': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'numeric_field.js',
							requires: [
								'aui-autosize-deprecated',
								'aui-tooltip',
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/'
				},
				'field-options': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
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
					root: MODULE_PATH + '/'
				},
				'field-paragraph': {
					base: MODULE_PATH + '/',
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
					root: MODULE_PATH + '/'
				},
				'field-password': {
					base: MODULE_PATH + '/',
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
					root: MODULE_PATH + '/'
				},
				'field-radio': {
					base: MODULE_PATH + '/',
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
					root: MODULE_PATH + '/'
				},
				'field-select': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
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
					root: MODULE_PATH + '/'
				},
				'field-text': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-text': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'text_field.js',
							requires: [
								'aui-autosize-deprecated',
								'aui-tooltip',
								'autocomplete',
								'autocomplete-highlighters',
								'autocomplete-highlighters-accentfold',
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/'
				},
				'field-validation': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
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
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();