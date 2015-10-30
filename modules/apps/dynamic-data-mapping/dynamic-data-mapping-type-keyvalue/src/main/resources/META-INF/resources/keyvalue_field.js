AUI.add(
	'liferay-ddm-form-field-keyvalue',
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
						value: 'keyvalue'
					}
				},

				EXTENDS: Liferay.DDM.Field.Text,

				NAME: 'liferay-ddm-form-field-keyvalue',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.after('editingChange', instance._afterEditingChange),
							instance.after('keyChange', instance._afterKeyChange),
							instance.bindContainerEvent('click', instance._onClickCancel, '.keyvalue-cancel'),
							instance.bindContainerEvent('click', instance._onClickDone, '.keyvalue-done'),
							instance.bindContainerEvent('click', instance._onClickEditor, '.keyvalue-output'),
							instance.bindContainerEvent('keypress', instance._onKeyPressEditorInput, '.keyvalue-input'),
							instance.bindContainerEvent('valuechange', instance._onValueChangeEditorInput, '.keyvalue-input'),
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
								strings: instance.get('strings')
							}
						);
					},

					normalizeKey: function(key) {
						var instance = this;

						key = key.trim();

						for (var i = 0; i < key.length; i++) {
							var item = key[i];

							if (!A.Text.Unicode.test(item, 'L') &&
								!A.Text.Unicode.test(item, 'N') &&
								!A.Text.Unicode.test(item, 'Pd')) {

								key = key.replace(item, ' ');
							}
						}

						key = Liferay.Util.camelize(key, ' ').trim();

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

						var editorInput = container.one('.keyvalue-input');

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
								'.keyvalue-input'
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

						var editorNode = container.one('.keyvalue-editor');

						editorNode.insert(container.one('.validation-message'), 'after');
					},

					_uiSetEditing: function(editing) {
						var instance = this;

						var container = instance.get('container');

						var editorNode = container.one('.keyvalue-editor');

						editorNode.toggleClass('active', editing);

						if (editing) {
							var editorInput = container.one('.keyvalue-input');

							editorInput.val('');
							editorInput.focus();
						}
					},

					_uiSetKey: function(key) {
						var instance = this;

						var container = instance.get('container');

						var editorInput = container.one('.keyvalue-input');

						editorInput.attr('placeholder', key);
						editorInput.attr('size', instance._getMaxInputSize(key) + 1);

						container.one('.keyvalue-output').html(key);
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