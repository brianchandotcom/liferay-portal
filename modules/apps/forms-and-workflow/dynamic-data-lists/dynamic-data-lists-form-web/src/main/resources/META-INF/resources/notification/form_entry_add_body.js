Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/form_entry_add_body.es',
	function(MetalSoyBundle, FormEntryAddBody) {
		if (!window.ddl) {
			window.ddl = {};
		}

		window.ddl['form_entry_add_body'] = FormEntryAddBody.default;

		AUI.add(
			'liferay-ddl-form-entry-add-body-soy',
			function(A) {
				debugger;
			}
		);
	}
);