'use strict';

var waitValueChange = function(callback) {
	setTimeout(callback, AUI().ValueChange.POLL_INTERVAL);
};

describe(
	'Liferay.DDM.Field.Options',
	function() {
		before(
			function(done) {
				AUI().use(
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

		describe(
			'.getValue',
			function() {

				it(
					'should create a default option',
					function(done) {
						var optionsField = new Liferay.DDM.Field.Options(
							{
								allowEmptyOptions: false,
								context: {
									name: 'optionsField'
								}
							}
						).render(document.body);

						assert.equal(1, optionsField.getValue().length);

						optionsField.eachOption(
							function(option, index) {
								if (option !== optionsField.getLastOption()) {
									assert.equal('Option', option.getValue());
								}
							}
						);

						done();
					}
				);

				it(
					'should create another empty option once a value is typed in the last empty option',
					function(done) {

						var optionsField = new Liferay.DDM.Field.Options(
							{
								context: {
									name: 'optionsField'
								}
							}
						).render(document.body);

						var initialValue = optionsField.getValue();

						assert.equal(1, initialValue.length);

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

						var optionsField = new Liferay.DDM.Field.Options(
							{
								allowEmptyOptions: false,
								context: {
									name: 'optionsField'
								},
								value: value
							}
						).render(document.body);

						optionsField.setValue(value);

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

						var optionsField = new Liferay.DDM.Field.Options(
							{
								allowEmptyOptions: false,
								context: {
									name: 'optionsField'
								},
								value: value
							}
						).render(document.body);

						var newValue = AUI().clone(value);

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
	}
);