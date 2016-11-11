'use strict';

describe(
	'Liferay.DDM.Field.Select',
	function() {
		var selectField;

		afterEach(
			function(done) {
				selectField.destroy();

				done();
			}
		);

		before(
			function(done) {
				AUI().use(
					'liferay-ddm-form-field-select-template',
					'liferay-ddm-form-field-select',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							{
								'javaScriptClass': 'Liferay.DDM.Field.Select',
								'name': 'select',
								'templateNamespace': 'ddm.select'
							}
						);

						done();
					}
				);
			}
		);

		beforeEach(
			function(done) {
				selectField = new Liferay.DDM.Field.Select(
					{
						context: {
							name: 'selectField',
							required: true
						}
					}
				).render(document.body);

				done();
			}
		);

		describe(
			'.getValue()',
			function() {
				it(
					'should return string value for multiple selectField',
					function(done) {
						selectField.set('multiple', true);

						selectField.set('options', [{label: 'a', value: 'a'}, {label: 'b', value: 'b'}, {label: 'c', value: 'c'}]);

						selectField.setValue(['a', 'b', 'c']);

						assert.equal(
							selectField.getValue(),
							'a,b,c'
						);

						done();
					}
				);
			}
		);
	}
);