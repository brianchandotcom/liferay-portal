AUI.add(
	'liferay-ddm-form-field-key-value',
	function(A) {
		var KeyValueField = A.Component.create(
			{
				ATTRS: {
					generationLocked: {
						valueFn: '_valueGenerationLocked'
					},

					key: {
						valueFn: '_valueKey'
					},

					keyInputEnabled: {
						value: true
					},

					maxKeyInputSize: {
						value: 50
					},

					minKeyInputSize: {
						value: 5
					},

					strings: {
						value: {
							cancel: Liferay.Language.get('cancel'),
							done: Liferay.Language.get('done'),
							keyLabel: Liferay.Language.get('field-name')
						}
					},

					type: {
						value: 'key_value'
					}
				},

				EXTENDS: Liferay.DDM.Field.Text,

				NAME: 'liferay-ddm-form-field-key-value',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.after('keyChange', instance._afterKeyChange),
							instance.after('keyInputEnabledChange', instance._afterKeyInputEnabledChange),
							instance.bindContainerEvent('keyup', instance._onKeyUpKeyInput, '.key-value-input'),
							instance.bindContainerEvent('valuechange', instance._onValueChangeKeyInput, '.key-value-input'),
							instance.bindInputEvent('valuechange', instance._onValueChangeInput)
						);
					},

					getTemplateContext: function() {
						var instance = this;

						var key = instance.get('key');

						return A.merge(
							KeyValueField.superclass.getTemplateContext.apply(instance, arguments),
							{
								key: key,
								keyInputEnabled: instance.get('keyInputEnabled'),
								keyInputSize: instance._getKeyInputSize(key),
								strings: instance.get('strings')
							}
						);
					},

					isValidCharacter: function(character) {
						var instance = this;

						return A.Text.Unicode.test(character, 'L') || A.Text.Unicode.test(character, 'N');
					},

					normalizeKey: function(key) {
						var instance = this;

						var normalizedKey = '';

						var nextUpperCase = false;

						key = key.trim();

						for (var i = 0; i < key.length; i++) {
							var item = key[i];

							if (item === ' ') {
								nextUpperCase = true;

								continue;
							}
							else if (!instance.isValidCharacter(item)) {
								continue;
							}

							if (nextUpperCase) {
								item = item.toUpperCase();

								nextUpperCase = false;
							}

							normalizedKey += item;
						}

						return normalizedKey;
					},

					render: function() {
						var instance = this;

						var key = instance.get('key');

						if (!key) {
							instance.set('key', instance._valueKey());
						}

						KeyValueField.superclass.render.apply(instance, arguments);

						return instance;
					},

					showErrorMesasage: function() {
						var instance = this;

						KeyValueField.superclass.showErrorMesasage.apply(instance, arguments);

						var container = instance.get('container');

						var editorNode = container.one('.key-value-editor');

						editorNode.insert(container.one('.help-block'), 'after');
					},

					_afterKeyChange: function(event) {
						var instance = this;

						instance.set('generationLocked', event.newVal !== instance.normalizeKey(instance.getValue()));

						instance._uiSetKey(event.newVal);
					},

					_afterKeyInputEnabledChange: function() {
						var instance = this;

						instance._uiSetKey(instance.get('key'));
					},

					_getKeyInputSize: function(str) {
						var instance = this;

						var size = str.length;

						var maxKeyInputSize = instance.get('maxKeyInputSize');

						var minKeyInputSize = instance.get('minKeyInputSize');

						if (size > maxKeyInputSize) {
							size = maxKeyInputSize;
						}
						else if (size <= minKeyInputSize) {
							size = minKeyInputSize;
						}

						return size + 1;
					},

					_onKeyUpKeyInput: function(event) {
						var instance = this;

						var inputNode = event.target;

						var value = inputNode.val();

						var validValue = value.split('').filter(instance.isValidCharacter);

						var newValue = validValue.join('');

						if (newValue !== value) {
							instance._updateInputValue(inputNode, newValue);
						}
					},

					_onValueChangeInput: function(event) {
						var instance = this;

						if (!instance.get('generationLocked')) {
							var value = instance.getValue();

							instance.set('key', instance.normalizeKey(value));
						}
					},

					_onValueChangeKeyInput: function(event) {
						var instance = this;

						var value = event.newVal;

						instance.set('key', instance.normalizeKey(value));
					},

					_uiSetKey: function(key) {
						var instance = this;

						var keyInput = instance.get('container').one('.key-value-input');

						if (document.activeElement !== keyInput.getDOM()) {
							keyInput.val(key);
						}

						keyInput.attr('size', instance._getKeyInputSize(key));

						if (instance.get('keyInputEnabled')) {
							keyInput.removeAttribute('readonly');
						}
						else {
							keyInput.attr('readonly', 'true');
						}
					},

					_updateInputValue: function(inputNode, newValue) {
						var instance = this;

						var currentValue = inputNode.val();

						var selectionEnd = inputNode.get('selectionEnd');
						var selectionStart = inputNode.get('selectionStart');

						inputNode.val(newValue);

						inputNode.set('selectionStart', selectionStart);
						inputNode.set('selectionEnd', selectionEnd - (currentValue.length - newValue.length));
					},

					_valueGenerationLocked: function() {
						var instance = this;

						return instance.get('key') !== instance.normalizeKey(instance.get('value'));
					},

					_valueKey: function() {
						var instance = this;

						return instance.normalizeKey(instance.get('value'));
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').KeyValue = KeyValueField;
	},
	'',
	{
		requires: ['aui-text-unicode', 'event-valuechange', 'liferay-ddm-form-field-text', 'liferay-ddm-form-renderer-field']
	}
);