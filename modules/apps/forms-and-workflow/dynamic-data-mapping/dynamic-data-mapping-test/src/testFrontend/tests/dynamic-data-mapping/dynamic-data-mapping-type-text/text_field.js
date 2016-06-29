'use strict';

var assert = chai.assert;

describe(
	'DDM Field Text',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-ddm-form-field-text',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							{
								'icon': 'icon-list',
								'javaScriptModule': 'liferay-ddm-form-field-text',
								'name': 'text',
								'settings': {},
								'system': 0,
								'templateNamespace': 'ddm.text'
							}
						);

						done();
					}
				);
			}
		);

		it(
			'should show loading feedback',
			function(done) {
				var textField = new Liferay.DDM.Field.Text(
					{
						errorMessage: 'error',
						name: 'textField',
						required: true,
						value: 'marcellus'
					}
				).render(document.body);

				var container = textField.get('container');

				assert.notOk(
					container.one('.icon-spinner'),
					'Loading icon should not be visible'
				);

				textField.showLoadingFeedback();

				assert.ok(
					container.one('.icon-spinner'),
					'Loading icon should be visible'
				);

				textField.destroy();

				done();
			}
		);
	}
);