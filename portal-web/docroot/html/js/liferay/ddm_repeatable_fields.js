AUI.add(
	'liferay-ddm-repeatable-fields',
	function(A) {
		var Lang = A.Lang;

		var SELECTOR_REPEAT_BUTTONS = '.lfr-ddm-repeatable-add-button, .lfr-ddm-repeatable-delete-button';

		var TPL_ADD_REPEATABLE = '<a class="lfr-ddm-repeatable-add-button" href="javascript:;"></a>';

		var TPL_DELETE_REPEATABLE = '<a class="lfr-ddm-repeatable-delete-button" href="javascript:;"></a>';

		var RepeatableFields = A.Component.create(
			{
				ATTRS: {
					classNameId: {
					},

					classPK: {
					},

					container: {
						setter: A.one
					},

					fieldsDisplayInput: {
						setter: A.one
					},

					namespace: {
					},

					portletNamespace: {
					},

					repeatable: {
						validator: Lang.isBoolean,
						value: false
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-repeatable-fields',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.bindUI();
						instance.syncUI();
					},

					bindUI: function() {
						var instance = this;

						var container = instance.get('container');

						container.delegate('click', instance._onClickRepeatableButton, SELECTOR_REPEAT_BUTTONS, instance);

						var hoverHandler = instance._onHoverRepeatableButton;

						container.delegate('hover', hoverHandler, hoverHandler, SELECTOR_REPEAT_BUTTONS, instance);
					},

					syncUI: function() {
						var instance = this;

						instance.syncFieldsTreeUI();
					},

					createFieldTree: function(fieldNode) {
						var instance = this;

						var fieldName = fieldNode.getData('fieldName');
						var fieldNamespace = fieldNode.getData('fieldNamespace');

						var tree = [fieldName + fieldNamespace];

						instance.getFieldsList(null, fieldNode).each(
							function(item, index, collection) {
								tree = tree.concat(instance.createFieldTree(item));
							}
						);

						return tree;
					},

					getField: function(fieldName, callback) {
						var instance = this;

						A.io.request(
							themeDisplay.getPathMain() + '/dynamic_data_mapping/render_structure_field',
							{
								data: {
									classNameId: instance.get('classNameId'),
									classPK: instance.get('classPK'),
									fieldName: fieldName,
									namespace: instance.get('namespace'),
									p_p_isolated: true,
									portletNamespace: instance.get('portletNamespace'),
									readOnly: instance.get('readOnly')
								},
								on: {
									success: function(event, id, xhr) {
										if (callback) {
											callback.call(instance, xhr.responseText);
										}
									}
								}
							}
						);
					},

					getFieldsList: function(fieldName, parentNode) {
						var instance = this;

						var container;

						if (parentNode) {
							container = parentNode;
						}
						else {
							container = instance.get('container');
						}

						var selector = ['>'];

						if (container.hasClass('field-wrapper')) {
							selector.push(' .field-wrapper-content >');
						}

						selector.push(' .field-wrapper');

						if (fieldName) {
							selector.push('[data-fieldName="' + fieldName + '"]');
						}

						return container.all(selector.join(''));
					},

					getFieldParentNode: function(fieldNode) {
						var instance = this;

						var parentNode = fieldNode.ancestor('.field-wrapper');

						if (!parentNode) {
							parentNode = instance.get('container');
						}

						return parentNode;
					},

					insertField: function(fieldNode) {
						var instance = this;

						var fieldName = fieldNode.getData('fieldName');

						instance.getField(
							fieldName,
							function(newFieldHTML) {
								fieldNode.insert(newFieldHTML, 'after');

								instance.syncFieldsTreeUI();
							}
						);
					},

					removeField: function(fieldNode) {
						var instance = this;

						fieldNode.remove();

						instance.syncFieldsTreeUI();
					},

					renderRepeatableUI: function(fieldNode) {
						var instance = this;

						var fieldRepeatable = A.DataType.Boolean.parse(fieldNode.getData('repeatable'));

						if (instance.get('repeatable') && fieldRepeatable) {
							if (!fieldNode.getData('rendered-toolbar')) {
								var fieldName = fieldNode.getData('fieldName');

								var parentNode = instance.getFieldParentNode(fieldNode);

								var fieldsList = instance.getFieldsList(fieldName, parentNode);

								var html = TPL_ADD_REPEATABLE;

								if (fieldsList.indexOf(fieldNode) > 0) {
									html += TPL_DELETE_REPEATABLE;
								}

								fieldNode.append(html);

								fieldNode.plug(A.Plugin.ParseContent);

								fieldNode.setData('rendered-toolbar', true);
							}
						}

						instance.getFieldsList(null, fieldNode).each(
							function(item, index, collection) {
								instance.renderRepeatableUI(item);
							}
						);
					},

					syncFieldsTreeUI: function() {
						var instance = this;

						var fieldsDisplay = [];

						var fieldsDisplayInput = instance.get('fieldsDisplayInput');

						instance.getFieldsList().each(
							function(item, index, collection) {
								instance.renderRepeatableUI(item);

								fieldsDisplay = fieldsDisplay.concat(instance.createFieldTree(item));
							}
						);

						fieldsDisplayInput.val(fieldsDisplay.join());
					},

					_onClickRepeatableButton: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var fieldNode = currentTarget.ancestor('.field-wrapper');

						if (currentTarget.hasClass('lfr-ddm-repeatable-add-button')) {
							instance.insertField(fieldNode);
						}
						else if (currentTarget.hasClass('lfr-ddm-repeatable-delete-button')) {
							instance.removeField(fieldNode);
						}
					},

					_onHoverRepeatableButton: function(event) {
						var instance = this;

						var fieldNode = event.currentTarget.ancestor('.field-wrapper');

						fieldNode.toggleClass('lfr-ddm-repeatable-active', (event.phase === 'over'));
					}

				}
			}
		);

		Liferay.namespace('DDM').RepeatableFields = RepeatableFields;
	},
	'',
	{
		requires: ['aui-base', 'aui-datatype', 'aui-io-request', 'aui-parse-content']
	}
);