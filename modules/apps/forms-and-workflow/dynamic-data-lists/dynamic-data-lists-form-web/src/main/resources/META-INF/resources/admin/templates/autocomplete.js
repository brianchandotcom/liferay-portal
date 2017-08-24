Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/autocomplete.es',
	function(MetalSoyBundle, AutoComplete) {
		if (!window.DDLAutoComplete) {
			window.DDLAutoComplete = {};
		}

		AutoComplete.default.forEach(function(item) {
			window.DDLAutoComplete[item.key] = item.component;
		});

		AUI.add('liferay-ddl-form-builder-autocomplete-template');
	}
);