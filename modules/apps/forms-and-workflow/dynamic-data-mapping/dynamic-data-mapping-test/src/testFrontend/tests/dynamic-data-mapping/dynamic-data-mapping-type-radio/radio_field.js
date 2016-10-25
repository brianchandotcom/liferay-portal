'use strict';

describe(
	'DDM Field Radio',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-ddm-form-field-radio',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							{
								'javaScriptClass': 'Liferay.DDM.Renderer.Radio',
								'name': 'radio',
								'templateNamespace': 'ddm.radio'
							}
						);

						done();
					}
				);
			}
		);

		it(
			'should return a empty string if no option is selected',
			function(done) {
				var radioField = new Liferay.DDM.Field.Radio(
					{
						context: {
							name: 'radioField'
						},
						options: [
							{
								label: 'First Option',
								value: 'first_option'
							}
						]
					}
				).render(document.body);

				assert.equal('', radioField.getValue());

				done();
			}
		);

		it(
			'should have the getValue() method return the value of the selected option',
			function(done) {
				var options = [
					{
						label: 'First Option',
						value: 'first_option'
					},
					{
						label: 'Second Option',
						value: 'second_option'
					}
				];

				var radioField = new Liferay.DDM.Field.Radio(
					{
						context: {
							name: 'radioField'
						},
						options: options
					}
				).render(document.body);

				var container = radioField.get('container');

				var firstOption = container.one('.field');

				firstOption.set('checked', 'checked');

				assert.equal(options[0].value, radioField.getValue());

				done();
			}
		);
	}
);