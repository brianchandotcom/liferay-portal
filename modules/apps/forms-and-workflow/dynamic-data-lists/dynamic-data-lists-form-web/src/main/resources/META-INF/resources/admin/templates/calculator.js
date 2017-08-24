Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/calculator.es',
	function(MetalSoyBundle, Calculator) {
		if (!window.DDLCalculator) {
			window.DDLCalculator = {};
		}

		window.DDLCalculator.render = Calculator.default;

		AUI.add('liferay-ddl-form-builder-calculator-template');
	}
);