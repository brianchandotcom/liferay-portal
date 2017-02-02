'use strict';

var A = AUI();

var createGridField = function(config) {
	return new Liferay.DDM.Field.Grid(
		A.merge(
			{
				context: {
					columns: [
						{
							label: '1',
							value: '1'
						},
						{
							label: '2',
							value: '2'
						}
					],
					name: 'gridField',
					rows: [
						{
							label: 'A',
							value: 'A'
						},
						{
							label: 'B',
							value: 'B'
						}
					]
				}
			},
			config || {}
		)
	).render(document.body);
};

describe(
	'Liferay.DDM.Field.Grid',
	function() {
		var gridField;

		afterEach(
			function(done) {
				gridField.destroy();

				done();
			}
		);

		before(
			function(done) {
				AUI().use(
					'liferay-ddm-form-field-grid-template',
					'liferay-ddm-form-field-grid',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							{
								'javaScriptClass': 'Liferay.DDM.Field.Grid',
								'name': 'grid',
								'templateNamespace': 'ddm.grid'
							}
						);

						done();
					}
				);
			}
		);

		describe(
			'.showErrorMessage()',
			function() {
				it(
					'should exists an error container if the grid field has an error message',
					function(done) {
						gridField = createGridField();

						gridField.set('errorMessage', 'error');

						gridField.showErrorMessage();

						var container = gridField.get('container');

						assert.isNotNull(container.one('.help-block'), 'The selectField has an error');

						done();
					}
				);
			}
		);

		describe(
			'.setValue()',
			function() {
				it(
					'should return empty value if set an empty object value',
					function(done) {
						gridField = createGridField();

						gridField.setValue({});

						assert.equal(gridField.getValue().A, undefined);

						assert.equal(gridField.getValue().B, undefined);

						done();
					}
				);

				it(
					'should return object values if set an object with properties',
					function(done) {
						gridField = createGridField();

						gridField.setValue({A: '1', B: '2'});

						assert.isObject(gridField.getValue());

						assert.equal(gridField.getValue().A, '1');

						assert.equal(gridField.getValue().B, '2');

						done();
					}
				);

				it(
					'should return object values if set an only option',
					function(done) {
						gridField = createGridField();

						gridField.setValue({A: '1'});

						assert.isObject(gridField.getValue());

						assert.equal(gridField.getValue().A, '1');

						assert.equal(gridField.getValue().B, undefined);

						done();
					}
				);

				it(
					'should return object values only if exists in the UI',
					function(done) {
						gridField = createGridField();

						gridField.setValue({A: '1', B: '2'});

						assert.isObject(gridField.getValue());

						assert.equal(gridField.getValue().A, '1');

						assert.equal(gridField.getValue().B, '2');

						assert.equal(gridField.getValue().C, undefined);

						assert.isObject(gridField.get('value'));

						assert.equal(gridField.get('value').A, '1');

						assert.equal(gridField.get('value').B, '2');

						assert.equal(gridField.get('value').C, undefined);

						done();
					}
				);
			}
		);

		describe(
			'.getValue()',
			function() {
				it(
					'should return object value if grid has a value in context',
					function(done) {
						gridField = createGridField(
							{
								value: {B: '2'}
							}
						);

						var value = gridField.getValue();

						assert.isObject(value);

						assert.equal(value.B, '2');

						done();
					}
				);

				it(
					'should return changed value object of the context with new object value',
					function(done) {
						gridField = createGridField(
							{
								value: {A: '1', B: '1'}
							}
						);

						gridField.setValue({A: '2'});

						var value = gridField.getValue();

						assert.isObject(value);

						assert.equal(value.A, '2');

						assert.equal(value.B, undefined);

						done();
					}
				);

				it(
					'should return the same context value if the grid began with value in context',
					function(done) {
						gridField = createGridField(
							{
								value: {A: '2', B: '1'}
							}
						);

						var value = gridField.get('value');

						assert.isObject(value);

						assert.equal(value.A, '2');

						assert.equal(value.B, '1');

						done();
					}
				);
			}
		);

		describe(
			'.checkItem()',
			function() {
				it(
					'should click in a radio input and get its value',
					function(done) {
						gridField = createGridField();

						var container = gridField.get('container');

						var input = container.one('tr[name="A"]').one('input[value="2"]');

						input.simulate('click');

						assert.isObject(gridField.getValue());

						assert.equal(gridField.getValue().A, '2');

						done();
					}
				);
			}
		);
	}
);