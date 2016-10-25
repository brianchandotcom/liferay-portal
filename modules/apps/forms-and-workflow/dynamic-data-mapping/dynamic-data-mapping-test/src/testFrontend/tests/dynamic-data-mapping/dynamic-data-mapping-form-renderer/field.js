'use strict';

var A = AUI();

var assert = chai.assert;

var FieldTest;

var createField = function(config) {
	return new FieldTest(
		A.merge(
			{
				value: '',
				type: 'field'
			},
			config || {}
		)
	).render(document.body);
};

var changeInputValue = function(container) {
	container.one('input').set('value', 'test');
	container.one('input').simulate('change');
};

describe(
	'DDM Field',
	function() {
		before(
			function(done) {
				A.use(
					'liferay-ddm-form-renderer-field',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							{
								'name': 'field',
								'javaScriptClass': 'Liferay.DDM.Renderer.Field'
							}
						);

						FieldTest = A.Component.create({
							EXTENDS: Liferay.DDM.Renderer.Field,
							prototype: {
								getTemplateRenderer: function() {
									var name = this.getQualifiedName();

									return function(context) {
										return '<input name="' + context.name + '" />';
									};
								}
							}
						});
						done();
					}
				);
			}
		);

		describe(
			'Events',
			function() {
				it(
					'should fire valueChange event',
					function(done) {
						var field = createField();

						field.after(
							'valueChange',
							function() {
								done();
							}
						);

						var container = field.get('container');

						changeInputValue(container);
					}
				);
			}
		);
	}
);