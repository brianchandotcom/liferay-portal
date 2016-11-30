'use strict';

describe(
	'Liferay.DDM.Field.FieldSet',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-ddm-form-field-fieldset',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							{
								'javaScriptClass': 'Liferay.DDM.Field.FieldSet',
								'name': 'fieldset',
								'templateNamespace': 'ddm.fieldset'
							}
						);

						done();
					}
				);
			}
		);

		describe(
			'unit',
			function() {
				describe(
					'.getFields()',
					function() {
						it(
							'should has two nested fields created',
							function(done) {
								var fieldSetField = new Liferay.DDM.Field.FieldSet(
									{
										context: {
											name: 'fieldSetField',
											type: 'fieldset'
										},
										fields: [
											{
												autocomplete: false,
												dataType: 'string',
												dir: 'ltr',
												displayStyle: 'singleline',
												errorMessage: '',
												label: 'Name',
												name: '',
												options: [],
												placeholder: '',
												readOnly: false,
												repeatable: false,
												required: false,
												showLabel: true,
												tip: '',
												tooltip: '',
												valid: true,
												value: '',
												visible: true
											},
											{
												autocomplete: false,
												dataType: 'string',
												dir: 'ltr',
												displayStyle: 'singleline',
												errorMessage: '',
												label: 'Path',
												name: '',
												options: [],
												placeholder: '',
												readOnly: false,
												repeatable: false,
												required: false,
												showLabel: true,
												tip: '',
												tooltip: '',
												valid: true,
												value: '',
												visible: true
											}
										]
									}
								).render(document.body);

								assert.equal(2, fieldSetField.get('fields').length);

								done();
							}
						);
					}
				);
			}
		);
	}
);