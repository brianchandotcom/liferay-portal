'use strict';

var A = AUI();

chai.config.truncateThreshold = 0;

var createOptionsField = function(config) {
	return new Liferay.DDM.Field.Options(
		A.merge(
			{
				context: {
					name: 'optionsField'
				}
			},
			config || {}
		)
	).render(document.body);
};

var getContainers = function(field) {
	return field.get('container').all('.options > .lfr-ddm-form-field-container');
};

var getOption = function(field, optionContainer) {
	var option;

	field.eachOption(
		function(currentOption) {
			if (currentOption.get('container') === optionContainer) {
				option = currentOption;
			}
		}
	);

	return option;
};

describe(
	'Liferay.DDM.Field.Options',
	function() {
		var optionsField;

		afterEach(
			function(done) {
				optionsField.destroy();

				done();
			}
		);

		before(
			function(done) {
				AUI().use(
					'liferay-ddm-form-field-options',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							[
								{
									'javaScriptClass': 'Liferay.DDM.Field.Options',
									'name': 'options',
									'templateNamespace': 'ddm.options'
								},
								{
									'javaScriptClass': 'Liferay.DDM.Field.Text',
									'name': 'text',
									'templateNamespace': 'ddm.text'
								},
								{
									'javaScriptClass': 'Liferay.DDM.Field.KeyValue',
									'name': 'key_value',
									'templateNamespace': 'ddm.key_value'
								}
							]
						);

						done();
					}
				);
			}
		);

		describe(
			'.getValue()',
			function() {
				xit(
					'should return an array of values based on options values and keys',
					function(done) {
						optionsField = createOptionsField(
							{
								allowEmptyOptions: true
							}
						);

						optionsField.getLastOption().set('value', 'One');
						optionsField.getLastOption().set('value', 'Two');
						optionsField.getLastOption().set('value', 'Three');
						optionsField.getLastOption().set('value', 'Four');
						optionsField.getLastOption().set('value', 'Five');

						var value = optionsField.getValue();

						assert.deepEqual(
							[
								{
									label: 'One',
									value: 'One'
								},
								{
									label: 'Two',
									value: 'Two'
								},
								{
									label: 'Three',
									value: 'Three'
								},
								{
									label: 'Four',
									value: 'Four'
								},
								{
									label: 'Five',
									value: 'Five'
								}
							],
							value
						);

						done();
					}
				);
			}
		);

		describe(
			'.moveOption(oldIndex, newIndex)',
			function() {
				xit(
					'should make the value refect the moved option',
					function(done) {
						optionsField = createOptionsField(
							{
								allowEmptyOptions: true
							}
						);

						optionsField.getLastOption().set('value', 'One');
						optionsField.getLastOption().set('value', 'Two');
						optionsField.getLastOption().set('value', 'Three');
						optionsField.getLastOption().set('value', 'Four');
						optionsField.getLastOption().set('value', 'Five');

						optionsField.moveOption(2, 4);

						var value = optionsField.getValue();

						assert.deepEqual(
							[
								{
									label: 'One',
									value: 'One'
								},
								{
									label: 'Two',
									value: 'Two'
								},
								{
									label: 'Four',
									value: 'Four'
								},
								{
									label: 'Five',
									value: 'Five'
								},
								{
									label: 'Three',
									value: 'Three'
								}
							],
							value
						);

						done();
					}
				);

				xit(
					'should render in correct order after moving an option',
					function(done) {
						optionsField = createOptionsField(
							{
								allowEmptyOptions: true,
								value: [
									{
										label: 'One',
										value: 'One'
									},
									{
										label: 'Two',
										value: 'Two'
									},
									{
										label: 'Three',
										value: 'Three'
									},
									{
										label: 'Four',
										value: 'Four'
									},
									{
										label: 'Five',
										value: 'Five'
									}
								]
							}
						);

						optionsField.moveOption(2, 4);

						var containers = getContainers(optionsField);

						containers.each(
							function(container, index) {
								var optionInDOM = getOption(optionsField, container);

								assert.ok(optionInDOM, 'Could not find option in DOM with index ' + index);

								var optionInField = optionsField.getOption(index);

								assert.equal(optionInDOM.get('key'), optionInField.get('key'));
								assert.equal(optionInDOM.get('value'), optionInField.get('value'));
							}
						);

						done();
					}
				);
			}
		);

		describe(
			'LPS-70570',
			function() {
				it(
					'should create the field',
					function(done) {
						optionsField = createOptionsField();

						assert.ok(optionsField);

						done();
					}
				);

				xit(
					'should not allow the same name for different options',
					function(done) {
						optionsField = createOptionsField(
							{
								allowEmptyOptions: true
							}
						);

						optionsField.getLastOption().set('value', 'One');
						optionsField.getLastOption().set('value', 'One');
						optionsField.getLastOption().set('value', 'One');

						var value = optionsField.getValue();

						assert.strictEqual('One', value[0].value);
						assert.strictEqual('One1', value[1].value);
						assert.strictEqual('One2', value[2].value);

						done();
					}
				);

				xit(
					'should allow deleting options',
					function(done) {
						optionsField = createOptionsField(
							{
								allowEmptyOptions: true
							}
						);

						optionsField.getLastOption().set('value', 'One');
						optionsField.getLastOption().set('value', 'Two');
						optionsField.getLastOption().set('value', 'Three');
						optionsField.getLastOption().set('value', 'Four');
						optionsField.getLastOption().set('value', 'Five');

						optionsField.getOption(2).remove();

						var value = optionsField.getValue();

						assert.deepEqual(
							[
								{
									label: 'One',
									value: 'One'
								},
								{
									label: 'Two',
									value: 'Two'
								},
								{
									label: 'Four',
									value: 'Four'
								},
								{
									label: 'Five',
									value: 'Five'
								}
							],
							value
						);

						done();
					}
				);

				xit(
					'should allow deleting options on the UI',
					function(done) {
						optionsField = createOptionsField(
							{
								allowEmptyOptions: true
							}
						);

						optionsField.getLastOption().set('value', 'One');
						optionsField.getLastOption().set('value', 'Two');
						optionsField.getLastOption().set('value', 'Three');
						optionsField.getLastOption().set('value', 'Four');
						optionsField.getLastOption().set('value', 'Five');

						optionsField.getOption(2).get('container').one('button.close').simulate('click');

						var containers = getContainers(optionsField);

						assert.strictEqual(5, containers.size());

						done();
					}
				);

				xit(
					'should allow generation of keys after changing the value',
					function(done) {
						optionsField = createOptionsField();

						optionsField.set(
							'value',
							[
								{
									label: 'Test',
									value: 'Test'
								}
							]
						);

						assert.isFalse(optionsField.getOption(0).get('generationLocked'));

						done();
					}
				);

				xit(
					'should lock the generation of keys after changing the value with items with labels different than the value',
					function(done) {
						optionsField = createOptionsField();

						optionsField.setValue(
							[
								{
									label: 'Test',
									value: 'manuallychanged'
								}
							]
						);

						assert.isTrue(optionsField.getOption(0).get('generationLocked'));

						done();
					}
				);
			}
		);
	}
);