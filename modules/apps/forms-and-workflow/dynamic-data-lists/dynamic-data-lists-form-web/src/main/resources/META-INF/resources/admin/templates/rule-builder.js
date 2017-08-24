Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/rule-builder.es',
	function(MetalSoyBundle, RuleBuilder) {
		if (!window.DDLRuleBuilder) {
			window.DDLRuleBuilder = {};
		}

		RuleBuilder.default.forEach(function(item) {
			window.DDLRuleBuilder[item.key] = item.component;
		});

		AUI.add('liferay-ddl-form-builder-rule-builder-template');
	}
);