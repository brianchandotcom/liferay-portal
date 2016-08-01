'use strict';

var A = AUI();

var assert = chai.assert;

var createField = function(config) {
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

var waitValueChange = function(callback) {
	setTimeout(callback, A.ValueChange.POLL_INTERVAL);
};

describe(
	'DDM Field Options',
	function() {
		afterEach(
			function(done) {
				this.optionsField.destroy();

				done();
			}
		);

		before(
			function(done) {
				A.use(
					'liferay-ddm-form-field-options',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							[
								{
									javaScriptClass: 'Liferay.DDM.Field.KeyValue',
									name: 'key_value',
									templateNamespace: 'ddm.key_value'
								},
								{
									javaScriptClass: 'Liferay.DDM.Field.Options',
									name: 'options',
									templateNamespace: 'ddm.options'
								}
							]
						);

						done();
					}
				);
			}
		);

		it(
			'should create another empty option once a value is typed in the last empty option',
			function(done) {
				var optionsField = this.optionsField = createField();

				var initialValue = optionsField.getValue();

				assert.equal(0, initialValue.length);

				var lastOption = optionsField.getLastOption();

				lastOption.focus();
				lastOption.setValue('First Option');

				waitValueChange(
					function() {
						assert.equal(1, optionsField.getValue().length);

						done();
					}
				);
			}
		);

		it(
			'should keep values after re-rendering without changes',
			function(done) {
				var value = [
					{
						label: 'First Option',
						value: 'FirstOption'
					},
					{
						label: 'Second Option',
						value: 'Second,Option'
					},
					{
						label: 'Third Option',
						value: 'ThirdOption'
					}
				];

				var optionsField = this.optionsField = createField(
					{
						value: value
					}
				);

				assert.equal(3, optionsField.getValue().length);

				optionsField.render();

				assert.equal(3, optionsField.getValue().length);

				var count = 0;

				optionsField.eachOption(
					function(option, index) {
						if (option !== optionsField.getLastOption()) {
							assert.equal(value[index].label, option.getValue());
						}

						count++;
					}
				);

				assert.equal(4, count);

				done();
			}
		);

		it(
			'should keep values after re-rendering with changes',
			function(done) {
				var value = [
					{
						label: 'First Option',
						value: 'FirstOption'
					},
					{
						label: 'Second Option',
						value: 'Second,Option'
					},
					{
						label: 'Third Option',
						value: 'ThirdOption'
					}
				];

				var optionsField = this.optionsField = createField(
					{
						value: value
					}
				);

				var newValue = A.clone(value);

				newValue.push(
					{
						label: 'Fourth Option',
						value: 'FourthOption'
					}
				);

				optionsField.setValue(newValue);

				var count = 0;

				optionsField.eachOption(
					function(option, index) {
						if (index < newValue.length) {
							assert.equal(newValue[index].label, option.getValue());
							assert.equal(newValue[index].value, option.get('key'));
						}

						count++;
					}
				);

				assert.equal(5, count, 'there should be one empty field at the end');

				done();
			}
		);
	}
);