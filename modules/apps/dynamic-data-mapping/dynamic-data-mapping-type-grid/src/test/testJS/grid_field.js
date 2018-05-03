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

var compareObject = function(objA, objB) {
	var aProps = Object.getOwnPropertyNames(objA);
	var bProps = Object.getOwnPropertyNames(objB);

	if (aProps.length != bProps.length) {
		return false;
	}

	for (var i = 0; i < aProps.length; i++) {
		var propName = aProps[i];

		if (objA[propName] !== objB[propName]) {
			return false;
		}
	}

	return true;
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

						assert.isNotNull(container.one('.form-feedback-indicator'), 'The selectField has an error');

						done();
					}
				);
			}
		);

		describe(
			'.setValue()',
			function() {
				it(
					'should return empty object if set an empty object value',
					function(done) {
						gridField = createGridField();

						gridField.setValue({});

						assert.equal((Object.getOwnPropertyNames(gridField.getValue())).length, 0);

						done();
					}
				);

				it(
					'should return object values if set an object with properties',
					function(done) {
						gridField = createGridField();

						var newValue = {A: '1', B: '2'};

						gridField.setValue(newValue);

						assert.isObject(gridField.getValue());

						assert.isTrue(compareObject(newValue, gridField.getValue()));

						done();
					}
				);

				it(
					'should return only one object value',
					function(done) {
						gridField = createGridField();

						var newValue = {A: '1'};

						gridField.setValue(newValue);

						assert.isObject(gridField.getValue());

						assert.isTrue(compareObject(newValue, gridField.getValue()));

						assert.isFalse(
							compareObject(
								{B: '1'},
								gridField.getValue()
							)
						);

						done();
					}
				);

				it(
					'should return object values only if exists in the UI',
					function(done) {
						gridField = createGridField();

						var newValue = {A: '1', B: '2'};

						gridField.setValue(newValue);

						assert.isObject(gridField.getValue());

						assert.isTrue(compareObject(newValue, gridField.getValue()));

						assert.isFalse(
							compareObject(
								{A: '1', C: '2'},
								gridField.getValue()
							)
						);

						assert.isObject(gridField.get('value'));

						assert.isTrue(compareObject(newValue, gridField.get('value')));

						assert.isFalse(
							compareObject(
								{A: '1', C: '2'},
								gridField.get('value')
							)
						);

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
						var newValue = {B: '2'};

						gridField = createGridField(
							{
								value: newValue
							}
						);

						assert.isObject(gridField.getValue());

						assert.isTrue(compareObject(newValue, gridField.getValue()));

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

						var newValue = {A: '2'};

						gridField.setValue(newValue);

						assert.isObject(gridField.getValue());

						assert.isTrue(compareObject(newValue, gridField.getValue()));

						assert.isFalse(
							compareObject(
								{A: '1', B: '1'},
								gridField.getValue()
							)
						);

						done();
					}
				);

				it(
					'should return the same context value if the grid began with value in context',
					function(done) {
						var contextValue = {A: '2', B: '1'};

						gridField = createGridField(
							{
								context: {
									value: contextValue
								}
							}
						);

						var context = gridField.get('context');

						var value = context.value;

						assert.isObject(value);

						assert.isTrue(compareObject(value, contextValue));

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

						assert.isTrue(
							compareObject(
								gridField.getValue(),
								{A: '2'}
							)
						);

						done();
					}
				);

				it(
					'should change value after click in another radio input column value',
					function(done) {
						gridField = createGridField();

						gridField.setValue({A: '2'});

						var container = gridField.get('container');

						var input = container.one('tr[name="A"]').one('input[value="1"]');

						input.simulate('click');

						assert.isObject(gridField.getValue());

						assert.isFalse(
							compareObject(
								{A: '2'},
								gridField.getValue()
							)
						);

						assert.isTrue(
							compareObject(
								{A: '1'},
								gridField.getValue()
							)
						);

						done();
					}
				);
			}
		);
	}
);