Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/rule.es',
	function(MetalSoyBundle, Rule) {
		if (!window.DDLRule) {
			window.DDLRule = {};
		}

		Rule.default.forEach(function(item) {
			window.DDLRule[item.key] = item.component;
		});

		AUI.add('liferay-ddl-form-builder-rule-template');
	}
);