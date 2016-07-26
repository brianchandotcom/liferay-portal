'use strict';

var A = AUI();

var assert = chai.assert;

var createField = function(config) {
	return new Liferay.DDM.Field.KeyValue(
		A.merge(
			{
				context: {
					name: 'keyValueField'
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
	'DDM Field Key Value',
	function() {
		afterEach(
			function(done) {
				this.keyValueField.destroy();

				done();
			}
		);

		before(
			function(done) {
				A.use(
					'liferay-ddm-form-field-key-value',
					function(A) {
						Liferay.DDM.Renderer.FieldTypes.register(
							{
								'name': 'key_value',
								'templateNamespace': 'ddm.key_value'
							}
						);

						done();
					}
				);
			}
		);

		it(
			'should normalize the "key" so that it does not contain invalid characters',
			function(done) {
				var keyValueField = this.keyValueField = createField();

				assert.equal('', keyValueField.normalizeKey(''));
				assert.equal('someValue', keyValueField.normalizeKey('someValue'));
				assert.equal('someValue', keyValueField.normalizeKey('  some value '));
				assert.equal('someValue', keyValueField.normalizeKey('some # value '));
				assert.equal('soméValue', keyValueField.normalizeKey('somé # value '));
				assert.equal('soméValue', keyValueField.normalizeKey('somé          value '));

				done();
			}
		);

		it(
			'should generate the "key" when value changes',
			function(done) {
				var keyValueField = this.keyValueField = createField();

				assert.equal('', keyValueField.get('key'));

				var value = 'Some value';

				keyValueField.focus();
				keyValueField.setValue(value);

				waitValueChange(
					function() {
						var normalizedValue = keyValueField.normalizeKey(value);

						assert.equal(normalizedValue, keyValueField.get('key'));

						done();
					}
				);
			}
		);

		it(
			'should not generate the "key" after it was manually edited',
			function(done) {
				var keyValueField = this.keyValueField = createField(
					{
						key: 'OldValue',
						value: 'Old Value'
					}
				);

				var keyValueFieldContainer = keyValueField.get('container');
				var keyValueFieldEditorInput = keyValueFieldContainer.one('.key-value-input');

				keyValueFieldEditorInput.focus();
				keyValueFieldEditorInput.val('newName');

				waitValueChange(
					function() {
						assert.equal('newName', keyValueField.get('key'));

						keyValueField.focus();
						keyValueField.setValue('Changed Value');

						waitValueChange(
							function() {
								assert.equal('newName', keyValueField.get('key'));
								assert.equal('newName', keyValueFieldEditorInput.val());

								done();
							}
						);
					}
				);
			}
		);

		it(
			'should respect key input size maximum and minimum values',
			function(done) {
				var keyValueField = this.keyValueField = createField();

				var keyValueFieldContainer = keyValueField.get('container');
				var keyValueFieldEditorInput = keyValueFieldContainer.one('.key-value-input');

				assert.equal(keyValueField.get('minKeyInputSize') + 1, keyValueFieldEditorInput.attr('size'));

				keyValueFieldEditorInput.focus();

				var maxKeyInputSize = keyValueField.get('maxKeyInputSize');

				var bigValue = 'test';

				while (bigValue.length < maxKeyInputSize) {
					bigValue += bigValue;
				}

				keyValueFieldEditorInput.val(bigValue);

				waitValueChange(
					function() {
						assert.equal(maxKeyInputSize + 1, keyValueFieldEditorInput.attr('size'));

						done();
					}
				);
			}
		);
	}
);