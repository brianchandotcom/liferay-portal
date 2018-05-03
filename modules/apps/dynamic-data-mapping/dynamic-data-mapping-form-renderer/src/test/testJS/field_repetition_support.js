'use strict';

var A = AUI();

chai.config.truncateThreshold = 0;

var createField = function(config) {
	return new TestField(
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

describe(
	'DDM Repetition Event Support',
	function() {
		before(
			function(done) {
				A.use(
					'liferay-ddm-form-renderer-field',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							{
								'javaScriptClass': 'window.TestField',
								'name': 'field',
								'templateNamespace': 'ddm.test'
							}
						);

						window.TestField = A.Component.create(
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
			'.copy()',
			function() {
				it(
					'should create a copy of the Field',
					function(done) {
						var field = createField();
						var copiedField = field.copy();

						var fieldContext = field.get('context');
						var copiedFieldContext = copiedField.get('context');

						assert.isTrue(A.instanceOf(copiedField, field.getFieldClass()));

						delete fieldContext.fieldName;
						delete copiedFieldContext.fieldName;
						delete fieldContext.repeatedIndex;
						delete copiedFieldContext.repeatedIndex;
						delete fieldContext.instanceId;
						delete copiedFieldContext.instanceId;
						delete copiedFieldContext.value;

						assert.deepEqual(fieldContext, copiedFieldContext);

						field.destroy();
						copiedField.destroy();

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

						assert.equal(1, document.body.getElementsByClassName('field-test').length);

						field.repeat();

						assert.equal(2, document.body.getElementsByClassName('field-test').length);

						done();
					}
				);
			}
		);
	}
);