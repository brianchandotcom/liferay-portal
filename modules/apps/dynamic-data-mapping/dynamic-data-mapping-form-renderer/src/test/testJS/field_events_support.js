'use strict';

var A = AUI();

var FieldTest;

var createField = function(config) {
	return new FieldTest(
		A.merge(
			{
				type: 'field',
				value: ''
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
	'DDM Field Event Support',
	function() {
		before(
			function(done) {
				A.use(
					'liferay-ddm-form-renderer-field',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							{
								'javaScriptClass': 'Liferay.DDM.Renderer.Field',
								'name': 'field',
								'templateNamespace': 'ddm.test'
							}
						);

						FieldTest = A.Component.create(
							{
								EXTENDS: Liferay.DDM.Renderer.Field,
								prototype: {
									getTemplateRenderer: function() {
										return function(context) {
											return '<input name="' + context.name + '" />';
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
			'Events',
			function() {
				it(
					'should fire valueChange event',
					function(done) {
						var field = createField(
							{
								after: {
									valueChange: function() {
										done();
									}
								}
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