'use strict';

var assert = chai.assert;

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
			'should return a empty string if any option was selected',
			function(done) {
				var radioField = new Liferay.DDM.Field.Radio(
					{
						context: {
							name: 'radioField'
						},
						options: [
							{
								label: 'Liferay',
								value: 'portal'
							}
						]
					}
				).render(document.body);

				var value = radioField.getValue();

				assert.equal(value, '');

				done();
			}
		);

		it(
			'should the selected input value to be equal of its respective option value',
			function(done) {
				var radioField = new Liferay.DDM.Field.Radio(
					{
						context: {
							name: 'radioField'
						},
						options: [
							{
								label: 'Liferay',
								value: 'portal'
							},
							{
								label: 'Latin',
								value: 'America'
							}
						]
					}
				).render(document.body);

				var container = radioField.get('container');

				var firstField = container.one('.field');

				firstField.set('checked', 'checked');

				var firstFieldValue = radioField.getValue();

				var options = radioField.get('options');

				assert.equal(options[0].value, firstFieldValue);

				done();
			}
		);
	}
);