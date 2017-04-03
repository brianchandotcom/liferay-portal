'use strict';

window.YUI_config = {
	"filter":"raw"
};

var A = AUI();

describe(
	'Liferay.DDL.FormBuilderCalculator',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-ddl-form-builder-calculator-template',
					'liferay-ddl-form-builder-calculator',
					function() {
						done();
					}
				);
			}
		);

		describe(
			'.fire event',
			function() {
				it(
					'should fire the press key',
					function(done) {
						var calculator = Liferay.DDL.FormBuilderCalculator();

						calculator.render();

						calculator.on('clickedKey', function(event) {
							console.log(event.key);
							assert.isNotNull(event.key);
							done();
						});

						var buttons = calculator.get('boundingBox').all('.calculator-button');

						buttons.item(1).simulate('click');
					}
				);
			}
		);
	}
);