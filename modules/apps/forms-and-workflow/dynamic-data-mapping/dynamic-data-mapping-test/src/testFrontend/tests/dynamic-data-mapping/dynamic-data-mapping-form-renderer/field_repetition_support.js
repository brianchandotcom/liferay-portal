'use strict';

var A = AUI();

var FieldTypes;

var createField = function(config) {
	return new FieldTest(
		A.merge(
			{
				type: 'field',
				value: 'test',
				context: {}
			},
			config || {}
		)
	).render(document.body);
};

var compareField = function(field1, field2) {
	var contextField1 = field1.get('context');

	var contextField2 = field2.get('context');

	var field1Class = field1.getFieldClass();

	assert.isTrue(A.instanceOf(field2, field1Class));

	delete contextField1.fieldName;
	delete contextField2.fieldName;

	return assert.deepEqual(contextField1, contextField2);
};

describe(
	'DDM Field Event Support',
	function() {
		before(
			function(done) {
				A.use(
					'liferay-ddm-form-renderer-field',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							{
								'javaScriptClass': 'window.FieldTest',
								'name': 'field',
								'templateNamespace': 'ddm.test'
							}
						);

						FieldTypes = Liferay.DDM.Renderer.FieldTypes;

						window.FieldTest = A.Component.create(
							{
								ATTRS: {},
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
					'.copy()',
					function() {
						it(
							'should create a copy of the Field',
							function(done) {
								var field = createField();

								compareField(field, field.copy());

								field.destroy();

								done();
							}
						);
					}
				);

				describe(
					'.repeat()',
					function() {
						it(
							'should add a copy of the Field above itself when the repeat function is called',
							function(done) {
								var field = createField();

								assert.equal(document.body.getElementsByClassName('field-test').length, 1);

								field.repeat();

								assert.equal(document.body.getElementsByClassName('field-test').length, 2);

								done();
							}
						);
					}
				);
			}
		);
	}
);