AUI.add(
	'liferay-ddm-html-util',
	function(A) {
		var TPL_ADD_REPEATABLE = '<a class="lfr-ddm-repeatable-add-button" href="javascript:;"></a>';
		var TPL_DELETE_REPEATABLE = '<a class="lfr-ddm-repeatable-delete-button" href="javascript:;"></a>';

		var RepeatableUtil = A.Component.create(
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

				NAME: 'liferay-ddm-repeatable-util',

				prototype: {
					fieldsCountMap: null,

					fieldsMap: null,

					initializer: function() {
						var instance = this;

						instance.fieldsMap = {};
						instance.fieldsCountMap = {};

						instance.getFieldsList().each(
							function(item, index, collection) {
								var fieldName = item.attr('data-fieldName');

								if (!instance.fieldsCountMap.hasOwnProperty(fieldName)) {
									instance.fieldsCountMap[fieldName] = 0;
								}
								else {
									instance.fieldsCountMap[fieldName]++;
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

						container.delegate('click', A.bind(instance._onClickRepeatableButton, instance), '.lfr-ddm-repeatable-add-button, .lfr-ddm-repeatable-delete-button');

						var hoverHandler = A.bind(instance._onHoverRepeatableButton, instance);

						container.delegate('hover', hoverHandler, hoverHandler, '.lfr-ddm-repeatable-add-button, .lfr-ddm-repeatable-delete-button');
					},

					addField: function(fieldName, repeatableIndex) {
						var instance = this;

						var fieldsList = instance.getFieldsList(fieldName);

						var fieldWrapper = fieldsList.item(repeatableIndex);

						instance.getField(
							fieldName,
							++instance.fieldsCountMap[fieldName],
							function(fieldHTML) {
								fieldWrapper.insert(fieldHTML, 'after');

								fieldsList.refresh();

								instance.makeFieldRepeatable(fieldsList.item(repeatableIndex + 1));

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
							themeDisplay.getPathContext() + themeDisplay.getPathMain() + '/dynamic_data_mapping/render_structure_field',
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

						fieldWrapper.append(TPL_ADD_REPEATABLE);

						if (fieldsList.indexOf(fieldWrapper) > 0) {
							fieldWrapper.append(TPL_DELETE_REPEATABLE);
						}

						fieldWrapper.plug(A.Plugin.ParseContent);
					},

					syncFieldsMap: function(fieldName) {
						var instance = this;

						var fieldsMapInput = instance.get('fieldsMapInput');

						var fieldsList = instance.getFieldsList(fieldName);

						instance.fieldsMap[fieldName] = [];

						fieldsList.each(
							function(item, index, collection) {
								var repeatableIndex = item.attr('data-repeatableIndex');

								if (repeatableIndex) {
									instance.fieldsMap[fieldName].push(repeatableIndex);
								}
							}
						);

						fieldsMapInput.val(JSON.stringify(instance.fieldsMap));
					},

					_onClickRepeatableButton: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var fieldWrapper = currentTarget.ancestor('.aui-field-wrapper');
						var fieldName = fieldWrapper.attr('data-fieldName');

						var repeatableIndex = instance.getFieldsList(fieldName).indexOf(fieldWrapper);

						if (currentTarget.test('.lfr-ddm-repeatable-add-button')) {
							instance.addField(fieldName, repeatableIndex);
						}
						else if (currentTarget.test('.lfr-ddm-repeatable-delete-button')) {
							instance.deleteField(fieldName, repeatableIndex);
						}
					},

					_onHoverRepeatableButton: function(event) {
						var instance = this;

						var fieldWrapper = event.currentTarget.ancestor('.aui-field-wrapper');

						if (fieldWrapper) {
							fieldWrapper.toggleClass('lfr-ddm-repeatable-active', (event.phase === 'over'));
						}
					}

				}
			}
		);

		Liferay.DDMRepeatableUtil = RepeatableUtil;
	},
	'',
	{
		requires: ['liferay-portlet-url']
	}
);