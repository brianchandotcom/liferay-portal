Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/field-options-toolbar.es',
	function(MetalSoyBundle, FieldOptionsToolbar) {
		if (!window.DDLFieldSettingsToolbar) {
			window.DDLFieldSettingsToolbar = {};
		}

		FieldOptionsToolbar.default.forEach(function(item) {
			window.DDLFieldSettingsToolbar[item.key] = item.component;
		});

		AUI.add('liferay-ddl-form-builder-field-options-toolbar-template');
	}
);