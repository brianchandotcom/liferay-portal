;(function() {
	AUI().applyConfig(
		{
			groups: {
				ddl: {
					base: '/o/ddl-form-web/admin/js/',
					modules: {
						'liferay-ddl-form-builder': {
							path: 'form_builder.js',
							requires: [
								'aui-form-builder',
								'aui-form-builder-pages',
								'liferay-ddl-form-builder-field-support',
								'liferay-ddl-form-builder-layout-deserializer',
								'liferay-ddl-form-builder-layout-visitor',
								'liferay-ddm-form-field-types',
								'liferay-ddm-form-renderer'
							]
						},
						'liferay-ddl-form-builder-definition-serializer': {
							path: 'form_definition_serializer.js',
							requires: [
								'json',
								'liferay-ddl-form-builder-layout-visitor',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddl-form-builder-field-support': {
							path: 'form_builder_field_support.js',
							requires: [
								'aui-form-field',
								'liferay-ddm-form-field-types'
							]
						},
						'liferay-ddl-form-builder-layout-deserializer': {
							path: 'form_layout_deserializer.js',
							requires: [
								'aui-layout',
								'aui-form-builder-field-list',
								'liferay-ddl-form-builder-field-support',
								'liferay-ddm-form-field-types'
							]
						},
						'liferay-ddl-form-builder-layout-serializer': {
							path: 'form_layout_serializer.js',
							requires: [
								'json',
								'liferay-ddl-form-builder-layout-visitor',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddl-form-builder-layout-visitor': {
							path: 'form_layout_visitor.js',
							requires: [
								'aui-form-builder-field-list',
								'aui-layout'
							]
						},
						'liferay-ddl-form-builder-util': {
							path: 'form_builder_util.js',
							requires: [
								'liferay-ddl-form-builder-field-support',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddl-portlet': {
							condition: {
								name: 'liferay-ddl-portlet',
								trigger: 'liferay-form',
								when: 'before'
							},
							path: 'form_portlet.js',
							requires: [
								'liferay-ddl-form-builder-definition-serializer',
								'liferay-ddl-form-builder-layout-serializer',
								'liferay-portlet-base'
							]
						}
					},
					root: '/o/ddl-form-web/admin/js/'
				},
				ddm: {
					base: '/o/ddm-form-renderer/js/',
					modules: {
						'liferay-ddm-form-renderer-field': {
							path: 'field.js',
							requires: [
								'aui-boolean-data-editor',
								'aui-form-builder-field-base',
								'aui-form-field',
								'aui-options-data-editor',
								'aui-radio-group-data-editor',
								'aui-tabs-data-editor',
								'aui-text-data-editor',
								'liferay-checkbox-field',
								'liferay-ddm-form-field-types',
								'liferay-text-field'
							]
						},
						'liferay-ddm-form-renderer-field-types': {
							path: 'field_types.js',
							requires: [
								'array-extras',
								'aui-form-builder-field-type',
								'liferay-ddm-form-field',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer': {
							path: 'form.js',
							requires: [
								'array-extras',
								'liferay-ddm-form-field',
								'liferay-ddm-form-field-types',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddm-form-renderer-util': {
							path: 'util.js',
							requires: [
								'array-extras',
								'liferay-ddm-form-field'
							]
						}
					},
					root: '/o/ddm-form-renderer/js/'
				},
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
				radio: {
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
				select: {
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
				text: {
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
				}
			}
		}
	);
})();