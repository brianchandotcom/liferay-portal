Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/data-provider-parameter.es',
	function(MetalSoyBundle, DataProviderParameter) {
		if (!window.DDLDataProviderParameter) {
			window.DDLDataProviderParameter = {};
		}

		DataProviderParameter.default.forEach(function(item) {
			window.DDLDataProviderParameter[item.key] = item.component;
		});

		AUI.add('liferay-ddl-form-builder-data-provider-parameter-template');
	}
);