;(function() {
	var LiferayAUI = Liferay.AUI;

	var PATH_DDL_FORM_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/dynamic-data-lists-form-web';

	AUI().applyConfig(
		{
			groups: {
				ddl: {
					base: PATH_DDL_FORM_WEB + '/admin/js/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
					modules: {
						'liferay-ddl-form-builder': {
							path: 'form_builder.js',
							requires: [
								'aui-form-builder',
								'aui-form-builder-pages',
								'liferay-ddl-form-builder-field-support',
								'liferay-ddl-form-builder-field-types-modal',
								'liferay-ddl-form-builder-layout-deserializer',
								'liferay-ddl-form-builder-layout-visitor',
								'liferay-ddl-form-builder-pages-manager',
								'liferay-ddl-form-builder-util',
								'liferay-ddm-form-field-types',
								'liferay-ddm-form-renderer'
							]
						},
						'liferay-ddl-form-builder-definition-serializer': {
							path: 'form_definition_serializer.js',
							requires: [
								'json',
								'liferay-ddl-form-builder-layout-visitor'
							]
						},
						'liferay-ddl-form-builder-field-types-modal': {
							path: 'form_builder_field_types_modal.js',
							requires: [
								'aui-form-builder-field-types-modal'
							]
						},
						'liferay-ddl-form-builder-layout-deserializer': {
							path: 'form_layout_deserializer.js',
							requires: [
								'aui-form-builder-field-list',
								'aui-layout',
								'liferay-ddl-form-builder-field-support',
								'liferay-ddm-form-field-types'
							]
						},
						'liferay-ddl-form-builder-layout-serializer': {
							path: 'form_layout_serializer.js',
							requires: [
								'json',
								'liferay-ddl-form-builder-layout-visitor'
							]
						},
						'liferay-ddl-form-builder-layout-visitor': {
							path: 'form_layout_visitor.js',
							requires: [
								'aui-form-builder-field-list',
								'aui-layout'
							]
						},
						'liferay-ddl-form-builder-pages-manager': {
							path: 'form_builder_pages_manager.js',
							requires: [
								'aui-form-builder-page-manager',
								'liferay-ddm-form-renderer-wizard'
							]
						},
						'liferay-ddl-form-builder-settings-form': {
							path: 'form_builder_settings_form.js',
							requires: [
								'liferay-ddm-form-renderer',
								'liferay-form'
							]
						},
						'liferay-ddl-form-builder-settings-support': {
							path: 'form_builder_settings_support.js',
							requires: [
								'liferay-ddl-form-builder-settings-form'
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
							path: 'form_portlet.js',
							requires: [
								'liferay-ddl-form-builder',
								'liferay-ddl-form-builder-definition-serializer',
								'liferay-ddl-form-builder-layout-serializer',
								'liferay-portlet-base',
								'liferay-util-window'
							]
						}
					},
					root: PATH_DDL_FORM_WEB + '/admin/js/'
				}
			}
		}
	);
})();