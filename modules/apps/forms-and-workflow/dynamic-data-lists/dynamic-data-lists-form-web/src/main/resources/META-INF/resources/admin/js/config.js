;(function() {
	var LiferayAUI = Liferay.AUI;

	AUI().applyConfig(
		{
			groups: {
				ddl: {
					base: MODULE_PATH + '/admin/js/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
					modules: {
						'liferay-ddl-form-builder': {
							path: 'form_builder.js',
							requires: [
								'aui-form-builder',
								'aui-form-builder-pages',
								'liferay-ddl-form-builder-confirmation-dialog',
								'liferay-ddl-form-builder-field-list',
								'liferay-ddl-form-builder-field-options-toolbar',
								'liferay-ddl-form-builder-field-settings-sidebar',
								'liferay-ddl-form-builder-field-support',
								'liferay-ddl-form-builder-field-types-modal',
								'liferay-ddl-form-builder-layout-builder-support',
								'liferay-ddl-form-builder-layout-deserializer',
								'liferay-ddl-form-builder-layout-visitor',
								'liferay-ddl-form-builder-pages-manager',
								'liferay-ddl-form-builder-rule-builder',
								'liferay-ddl-form-builder-util',
								'liferay-ddm-form-field-types',
								'liferay-ddm-form-renderer'
							]
						},
						'liferay-ddl-form-builder-autocomplete-template': {
							path: '../templates/autocomplete.soy.js',
							requires: [
								'soyutils'
							]
						},
						'liferay-ddl-form-builder-confirmation-dialog': {
							path: 'form_builder_confirmation_dialog.js',
							requires: []
						},
						'liferay-ddl-form-builder-definition-serializer': {
							path: 'form_definition_serializer.js',
							requires: [
								'json',
								'liferay-ddl-form-builder-layout-visitor'
							]
						},
						'liferay-ddl-form-builder-field-list': {
							path: 'form_builder_field_list.js',
							requires: [
								'aui-form-builder-field-list'
							]
						},
						'liferay-ddl-form-builder-field-options-toolbar': {
							path: 'form_builder_field_options_toolbar.js',
							requires: ['liferay-ddl-form-builder-field-options-toolbar-template']
						},
						'liferay-ddl-form-builder-field-options-toolbar-template': {
							path: '../templates/field_options_toolbar.soy.js',
							requires: [
								'soyutils'
							]
						},
						'liferay-ddl-form-builder-field-settings-form': {
							path: 'form_builder_field_settings_form.js',
							requires: [
								'liferay-ddl-form-builder-autocomplete-template',
								'liferay-ddl-soy-template-util',
								'liferay-ddm-form-renderer',
								'liferay-form'
							]
						},
						'liferay-ddl-form-builder-field-settings-sidebar': {
							path: 'form_builder_field_settings_sidebar.js',
							requires: ['aui-tabview', 'liferay-ddl-form-builder-sidebar', 'liferay-ddm-form-renderer-types']
						},
						'liferay-ddl-form-builder-field-support': {
							path: 'form_builder_field_support.js',
							requires: [
								'liferay-ddl-form-builder-field-settings-form',
								'liferay-ddl-form-builder-settings-retriever'
							]
						},
						'liferay-ddl-form-builder-field-types-modal': {
							path: 'form_builder_field_types_modal.js',
							requires: [
								'aui-form-builder-field-types-modal',
								'liferay-ddl-form-builder-modal-support'
							]
						},
						'liferay-ddl-form-builder-layout-builder-support': {
							path: 'form_builder_layout_builder_support.js',
							requires: []
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
						'liferay-ddl-form-builder-modal-support': {
							path: 'form_builder_modal_support.js',
							requires: [
								'aui-modal'
							]
						},
						'liferay-ddl-form-builder-pages-manager': {
							path: 'form_builder_pages_manager.js',
							requires: [
								'aui-form-builder-page-manager',
								'liferay-ddm-form-renderer-wizard'
							]
						},
						'liferay-ddl-form-builder-rule-builder': {
							path: 'form_builder_rule_builder.js',
							requires: ['aui-popover', 'liferay-ddl-form-builder-rule-builder-template']
						},
						'liferay-ddl-form-builder-rule-builder-template': {
							path: '../templates/rule_builder.soy.js',
							requires: [
								'soyutils'
							]
						},
						'liferay-ddl-form-builder-settings-retriever': {
							path: 'form_builder_settings_retriever.js',
							requires: [
								'aui-request'
							]
						},
						'liferay-ddl-form-builder-sidebar': {
							path: 'form_builder_sidebar.js',
							requires: ['aui-tabview', 'liferay-ddl-form-builder-sidebar-template', 'liferay-ddl-form-field-options-toolbar']
						},
						'liferay-ddl-form-builder-sidebar-template': {
							path: '../templates/sidebar.soy.js',
							requires: [
								'soyutils'
							]
						},
						'liferay-ddl-form-builder-util': {
							path: 'form_builder_util.js',
							requires: [
								'liferay-ddl-form-builder-field-support',
								'liferay-ddm-form-renderer-util'
							]
						},
						'liferay-ddl-form-sidebar-soy': {
							path: 'sidebar.soy.js',
							requires: [
								'soyutils'
							]
						},
						'liferay-ddl-portlet': {
							path: 'form_portlet.js',
							requires: [
								'liferay-ddl-form-builder',
								'liferay-ddl-form-builder-definition-serializer',
								'liferay-ddl-form-builder-layout-serializer',
								'liferay-ddl-form-builder-rule-builder',
								'liferay-portlet-base',
								'liferay-util-window'
							]
						},
						'liferay-ddl-soy-template-util': {
							path: 'soy_template_util.js',
							requires: []
						}
					},
					root: MODULE_PATH + '/admin/js/'
				}
			}
		}
	);
})();