AUI.add(
	'liferay-ddm-form-field-key-value',
	function(A) {
		var KeyValueField = A.Component.create(
			{
				ATTRS: {
					editing: {
						value: false
					},

					key: {
						valueFn: '_valueKey'
					},

					strings: {
						value: {
							cancel: Liferay.Language.get('cancel'),
							done: Liferay.Language.get('done'),
							keyLabel: Liferay.Language.get('field-name')
						}
					},

					type: {
						value: 'key-value'
					}
				},

				EXTENDS: Liferay.DDM.Field.Text,

				NAME: 'liferay-ddm-form-field-key-value',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.after('editingChange', instance._afterEditingChange),
							instance.after('keyChange', instance._afterKeyChange),
							instance.bindContainerEvent('click', instance._onClickCancel, '.key-value-cancel'),
							instance.bindContainerEvent('click', instance._onClickDone, '.key-value-done'),
							instance.bindContainerEvent('click', instance._onClickEditor, '.key-value-output'),
							instance.bindContainerEvent('keypress', instance._onKeyPressEditorInput, '.key-value-input'),
							instance.bindContainerEvent('valuechange', instance._onValueChangeEditorInput, '.key-value-input'),
							instance.bindInputEvent('valuechange', instance._onValueChangeInput)
						);
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							KeyValueField.superclass.getTemplateContext.apply(instance, arguments),
							{
								editing: instance.get('editing'),
								key: instance.get('key'),
								strings: instance.get('strings'),
								tooltip: instance.getLocalizedValue(instance.get('tooltip'))
							}
						);
					},

					normalizeKey: function(key) {
						var instance = this;

						key = key.trim();

						for (var i = 0; i < key.length; i++) {
							var item = key[i];

							if (!A.Text.Unicode.test(item, 'L') && !A.Text.Unicode.test(item, 'N') && !A.Text.Unicode.test(item, 'Pd')) {
								key = key.replace(item, ' ');
							}
						}

						key = Liferay.Util.camelize(key, ' ');

						return key.replace(/\s+/ig, '');
					},

					render: function() {
						var instance = this;

						KeyValueField.superclass.render.apply(instance, arguments);

						instance._uiSetKey(instance.get('key'));

						return instance;
					},

					saveEditor: function() {
						var instance = this;

						var container = instance.get('container');

						var editorInput = container.one('.key-value-input');

						var value = editorInput.val();

						if (value) {
							instance.set('key', instance.normalizeKey(value));
						}

						instance.set('editing', false);
					},

					_afterEditingChange: function(event) {
						var instance = this;

						var container = instance.get('container');

						var editing = event.newVal;

						if (editing && !instance._eventOutsideHandler) {
							instance._eventOutsideHandler = container.on(
								'clickoutside',
								function(event) {
									instance.set('editing', false);

									instance._eventOutsideHandler.detach();

									instance._eventOutsideHandler = null;
								},
								'.key-value-input'
							);
						}

						instance._uiSetEditing(editing);
					},

					_afterKeyChange: function(event) {
						var instance = this;

						instance._uiSetKey(event.newVal);
					},

					_getMaxInputSize: function(str) {
						var size = str.length;

						if (size > 50) {
							size = 50;
						}
						else if (size <= 5) {
							size = 5;
						}

						return size;
					},

					_onClickCancel: function() {
						var instance = this;

						instance.set('editing', false);
					},

					_onClickDone: function() {
						var instance = this;

						instance.saveEditor();
					},

					_onClickEditor: function() {
						var instance = this;

						instance.set('editing', !instance.get('editing'));
					},

					_onKeyPressEditorInput: function(event) {
						var instance = this;

						if (event.isKey('ENTER')) {
							event.preventDefault();

							instance.saveEditor();
						}
					},

					_onValueChangeEditorInput: function(event) {
						var instance = this;

						var input = event.target;

						var value = event.newVal;

						if (value.length === 0) {
							value = input.attr('placeholder');
						}

						event.target.attr('size', instance._getMaxInputSize(value) + 1);
					},

					_onValueChangeInput: function(event) {
						var instance = this;

						if (instance.normalizeKey(event.prevVal) === instance.get('key')) {
							var value = instance.getValue();

							instance.set('key', instance.normalizeKey(value));
						}
					},

					_renderErrorMessage: function() {
						var instance = this;

						KeyValueField.superclass._renderErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var editorNode = container.one('.key-value-editor');

						editorNode.insert(container.one('.help-block'), 'after');
					},

					_uiSetEditing: function(editing) {
						var instance = this;

						var container = instance.get('container');

						var editorNode = container.one('.key-value-editor');

						editorNode.toggleClass('active', editing);

						if (editing) {
							var editorInput = container.one('.key-value-input');

							editorInput.val('');
							editorInput.focus();
						}
					},

					_uiSetKey: function(key) {
						var instance = this;

						var container = instance.get('container');

						var editorInput = container.one('.key-value-input');

						editorInput.attr('placeholder', key);
						editorInput.attr('size', instance._getMaxInputSize(key) + 1);

						container.one('.key-value-output').html(key);
					},

					_valueKey: function() {
						var instance = this;

						var value = instance.getLocalizedValue(instance.get('value'));

						return instance.normalizeKey(value);
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').KeyValue = KeyValueField;
	},
	'',
	{
		requires: ['aui-text-unicode', 'liferay-ddm-form-field-text', 'liferay-ddm-form-renderer-field']
	}
);