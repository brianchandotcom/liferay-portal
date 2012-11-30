AUI.add(
	'liferay-ddm-repeatable-fields',
	function(A) {
		var Lang = A.Lang;

		var owns = A.Object.owns;

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
						setter: A.one,
						validator: A.Lang.isNode,
						value: null
					},

					fieldsMapInput: {
						setter: A.one,
						validator: A.Lang.isNode,
						value: null
					}
				},

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-repeatable-fields',

				prototype: {
					initializer: function() {
						var instance = this;

						var fieldsCountMap = {};
						var fieldsMap = {};

						instance.fieldsCountMap = fieldsCountMap;
						instance.fieldsMap = fieldsMap;

						instance.getFieldsList().each(
							function(item, index, collection) {
								var fieldName = item.attr('data-fieldName');

								if (!owns(fieldsCountMap, fieldName)) {
									fieldsCountMap[fieldName] = 0;
								}
								else {
									fieldsCountMap[fieldName]++;
								}

								instance.makeFieldRepeatable(item);
								instance.syncFieldsMap(fieldName);
							}
						);

						instance.bindUI();
					},

					bindUI: function() {
						var instance = this;

						var container = instance.get('container');

						container.delegate('click', instance._onClickRepeatableButton, SELECTOR_REPEAT_BUTTONS, instance);

						var hoverHandler = instance._onHoverRepeatableButton;

						container.delegate('hover', hoverHandler, hoverHandler, SELECTOR_REPEAT_BUTTONS, instance);
					},

					addField: function(fieldName, repeatableIndex) {
						var instance = this;

						var fieldsList = instance.getFieldsList(fieldName);

						var fieldWrapper = fieldsList.item(repeatableIndex);

						var fieldCount = (++instance.fieldsCountMap[fieldName]);

						instance.getField(
							fieldName,
							fieldCount,
							function(fieldHTML) {
								fieldWrapper.insert(fieldHTML, 'after');

								fieldsList.refresh();

								var nextField = fieldsList.item(repeatableIndex + 1);

								instance.makeFieldRepeatable(nextField);
								instance.syncFieldsMap(fieldName);
							}
						);
					},

					deleteField: function(fieldName, repeatableIndex) {
						var instance = this;

						var fieldsList = instance.getFieldsList(fieldName);

						fieldsList.item(repeatableIndex).remove();

						instance.syncFieldsMap(fieldName);
					},

					getField: function(fieldName, repeatableIndex, callback) {
						var instance = this;

						A.io.request(
							themeDisplay.getPathMain() + '/dynamic_data_mapping/render_structure_field',
							{
								data: {
									classNameId: instance.get('classNameId'),
									classPK: instance.get('classPK'),
									fieldName: fieldName,
									p_p_isolated: true,
									readOnly: instance.get('readOnly'),
									repeatableIndex: repeatableIndex
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

					getFieldsList: function(fieldName) {
						var instance = this;

						var container = instance.get('container');

						var query = '.aui-field-wrapper';

						if (fieldName) {
							query += '[data-fieldName="' + fieldName + '"]';
						}

						query += '[data-repeatable="true"]';

						return container.all(query);
					},

					makeFieldRepeatable: function(fieldWrapper) {
						var instance = this;

						var fieldName = fieldWrapper.attr('data-fieldName');

						var fieldsList = instance.getFieldsList(fieldName);

						var html = TPL_ADD_REPEATABLE;

						if (fieldsList.indexOf(fieldWrapper) > 0) {
							html += TPL_DELETE_REPEATABLE;
						}

						fieldWrapper.append(html);

						fieldWrapper.plug(A.Plugin.ParseContent);
					},

					syncFieldsMap: function(fieldName) {
						var instance = this;

						var fieldsMapInput = instance.get('fieldsMapInput');

						var fieldsList = instance.getFieldsList(fieldName);

						var fieldsMap = instance.fieldsMap;

						var fieldMap = [];

						fieldsList.each(
							function(item, index, collection) {
								var repeatableIndex = item.attr('data-repeatableIndex');

								if (repeatableIndex) {
									fieldMap.push(repeatableIndex);
								}
							}
						);

						fieldsMap[fieldName] = fieldMap;

						fieldsMapInput.val(A.JSON.stringify(fieldsMap));
					},

					_onClickRepeatableButton: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var fieldWrapper = currentTarget.ancestor('.aui-field-wrapper');

						var fieldName = fieldWrapper.attr('data-fieldName');

						var repeatableIndex = instance.getFieldsList(fieldName).indexOf(fieldWrapper);

						if (currentTarget.hasClass('lfr-ddm-repeatable-add-button')) {
							instance.addField(fieldName, repeatableIndex);
						}
						else if (currentTarget.hasClass('lfr-ddm-repeatable-delete-button')) {
							instance.deleteField(fieldName, repeatableIndex);
						}
					},

					_onHoverRepeatableButton: function(event) {
						var instance = this;

						var fieldWrapper = event.currentTarget.ancestor('.aui-field-wrapper');

						fieldWrapper.toggleClass('lfr-ddm-repeatable-active', (event.phase === 'over'));
					}

				}
			}
		);

		Liferay.namespace('DDM').RepeatableFields = RepeatableFields;
	},
	'',
	{
		requires: ['aui-base', 'aui-io-request', 'aui-parse-content']
	}
);