'use strict';

describe(
	'Liferay.DDM.Field.FieldSet',
	function() {
		before(
			function(done) {
				AUI().use(
					'liferay-ddm-form-field-fieldset',
					'liferay-ddm-form-soy',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							[
								{
									'javaScriptClass': 'Liferay.DDM.Field.FieldSet',
									'name': 'fieldset',
									'templateNamespace': 'ddm.fieldset'
								},
								{
									'javaScriptClass': 'window.TestField',
									'name': 'test',
									'templateNamespace': 'ddm.test'
								}
							]
						);

						window.TestField = A.Component.create(
							{
								ATTRS: {
									type: 'test'
								},
								EXTENDS: Liferay.DDM.Renderer.Field,
								prototype: {
									getTemplateRenderer: function() {
										return function(context) {
											return '<input class="field-test" name="' + context.name + '" />';
										};
									}
								}
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
							'should return an array',
							function() {
								var fieldSetField = new Liferay.DDM.Field.FieldSet();

								assert.isArray(fieldSetField.get('fields'));
							}
						);
						xit(
							'should has two nested fields created',
							function(done) {
								var fieldSetField = new Liferay.DDM.Field.FieldSet(
									{
										context: {
											name: 'fieldSetField',
											type: 'fieldset'
										},
										nestedFields: [
											{
												autocomplete: false,
												dataType: 'string',
												dir: 'ltr',
												displayStyle: 'singleline',
												errorMessage: '',
												label: 'Name',
												name: 'testField$$1',
												options: [],
												placeholder: '',
												readOnly: false,
												repeatable: false,
												required: false,
												showLabel: true,
												tip: '',
												type: 'test',
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
												name: 'testField$$2',
												options: [],
												placeholder: '',
												readOnly: false,
												repeatable: false,
												required: false,
												showLabel: true,
												tip: '',
												type: 'test',
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