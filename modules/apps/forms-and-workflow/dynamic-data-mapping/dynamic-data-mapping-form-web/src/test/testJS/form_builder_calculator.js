'use strict';

window.YUI_config = {
	"filter":"raw"
};

var A = AUI();

describe(
	'Liferay.DDM.FormBuilderCalculator',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-ddm-form-builder-calculator-template',
					'liferay-ddm-form-builder-calculator',
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
						var calculator = Liferay.DDM.FormBuilderCalculator();

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