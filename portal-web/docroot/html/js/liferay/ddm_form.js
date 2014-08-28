AUI.add(
	'liferay-ddm-form',
	function(A) {

		var BOOLEAN_TYPE = 'Boolean';

		var CHECKBOX_TYPE = 'checkbox';

		var INSTANCE_STR = '_INSTANCE_';

		var Lang = A.Lang;

		var RADIO_TYPE = 'Radio';

		var SELECTOR_REPEAT_BUTTONS = '.lfr-ddm-repeatable-add-button, .lfr-ddm-repeatable-delete-button';

		var TPL_ADD_REPEATABLE = '<a class="lfr-ddm-repeatable-add-button icon-plus-sign" href="javascript:;"></a>';

		var TPL_DELETE_REPEATABLE = '<a class="lfr-ddm-repeatable-delete-button icon-minus-sign" href="javascript:;"></a>';

		var Field = A.Component.create(
			{
				ATTRS: {
					nodeWrapper: {
						setter: A.one
					},

					portletNamespace: {
						value: ''
					}
				},

				childNode: {},

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-field',

				prototype: {
					initializer: function() {
					},

					getChildNode: function() {
						var instance = this;

						var id = instance._getId();

						var name = instance.getName();

						var nodeWrapper = instance.get('nodeWrapper');

						if (!instance.childNode) {
							if (name === RADIO_TYPE) {
								instance.childNode = nodeWrapper.all('[name="' + id + '"]');
							}
							else {
								instance.childNode = nodeWrapper.one('#' + id);
							}
						}

						return instance.childNode;
					},

					getName: function() {
						var instance = this;

						return instance.get('nodeWrapper').getData('fieldName');
					},

					getNamespace: function() {
						var instance = this;

						return instance.get('nodeWrapper').getData('fieldNamespace');
					},

					getValue: function(nodes) {
						var instance = this;

						var name = instance.getName();

						var value;

						if (name === RADIO_TYPE) {
							A.each(
								nodes,
								function(node) {
									if (node.get('checked') === true) {
										value = node.get('value');
									}
								}
							);

						}
						else if (name === BOOLEAN_TYPE) {
							value = nodes.get('checked').toString();
						}
						else {
							value = Lang.String.unescapeHTML(nodes.get('value'));
						}

						return value;
					},

					setValue: function(fields, value) {
						var instance = this;

						var name = instance.getName();

						if (name === RADIO_TYPE) {
							A.each(
								fields,
								function(field) {
									if (field.get('value') === value) {
										field.set('checked', true);
									}
									else {
										field.set('checked', false);
									}
								}
							);

						}
						else if (name === BOOLEAN_TYPE) {
							fields.set('checked', value === 'true' ? true : false);
						}
						else {
							fields.set('value', value);
						}
					},

					_getId: function() {
						var instance = this;

						var name = instance.getName();

						var namespace = instance.getNamespace();

						var portletNamespace = instance.get('portletNamespace');

						return portletNamespace + name + namespace;
					}
				}
			}
		);

		var Form = A.Component.create(
			{
				ATTRS: {
					classNameId: {
					},

					classPK: {
					},

					container: {
						setter: A.one
					},

					definition: {
						value: {}
					},

					doAsGroupId: {
					},

					ddmFormValuesInput: {
						setter: A.one
					},

					namespace: {
					},

					p_l_id: {
					},

					portletNamespace: {
					},

					repeatable: {
						validator: Lang.isBoolean,
						value: false
					},

					values: {
						value: {}
					}
				},

				EXTENDS: A.Base,

				fieldsLocalizationMap: {},

				form: {},

				NAME: 'liferay-ddm-form',

				translationManager: {},

				prototype: {
					initializer: function() {
						var instance = this;

						instance.translationManager = Liferay.component(instance.get('portletNamespace') + 'translationManager');
						instance.fieldsLocalizationMap = {};

						instance.bindUI();
						instance.syncUI();
					},

					bindUI: function() {
						var instance = this;

						var container = instance.get('container');

						container.delegate('click', instance._onClickRepeatableButton, SELECTOR_REPEAT_BUTTONS, instance);

						instance.form = container.ancestor('form');

						if (instance.form) {
							instance.form.on('submit', instance._onSubmitForm, instance);
						}

						var hoverHandler = instance._onHoverRepeatableButton;

						container.delegate('hover', hoverHandler, hoverHandler, SELECTOR_REPEAT_BUTTONS, instance);

						var translationManager = instance.translationManager;

						if (translationManager) {
							translationManager.on('editingLocaleChange', instance._onEditingLocaleChange, instance);
							translationManager.on('deleteAvailableLocale', instance._onDeleteAvailableLocale, instance);
						}
					},

					syncUI: function() {
						var instance = this;

						instance._syncRepeatableFields();
					},

					_getDefinitionFieldNames: function(fields) {
						var instance = this;

						var fieldNames = [];

						A.each(
							fields,
							function(field) {
								fieldNames.push(field['name']);
							}
						);
						return fieldNames;
					},

					_getField: function(fieldName, callback) {
						var instance = this;

						A.io.request(
							themeDisplay.getPathMain() + '/dynamic_data_mapping/render_structure_field',
							{
								data: {
									classNameId: instance.get('classNameId'),
									classPK: instance.get('classPK'),
									controlPanelCategory: 'portlet',
									doAsGroupId: instance.get('doAsGroupId'),
									fieldName: fieldName,
									namespace: instance.get('namespace'),
									p_l_id: instance.get('p_l_id'),
									p_p_id: '166',
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

					_getFieldParentNode: function(fieldNode) {
						var instance = this;

						var parentNode = fieldNode.ancestor('.field-wrapper');

						if (!parentNode) {
							parentNode = instance.get('container');
						}

						return parentNode;
					},

					_getFieldValues: function(nodes, isNested) {
						var instance = this;

						var definitionFields = instance.get('definition').fields;

						var definitionFieldNames = instance._getDefinitionFieldNames(definitionFields);

						var fieldValues = [];

						var portletNamespace = instance.get('portletNamespace');

						A.each(
							nodes,
							function(item) {
								var fieldName = item.getData('fieldName');

								var fieldNamespace = item.getData('fieldNamespace');

								var instanceId = fieldNamespace.split(INSTANCE_STR)[1];

								var isStructureField = definitionFieldNames.indexOf(fieldName) >= 0;

								if (isStructureField || isNested) {
									var field = {
										instanceId: instanceId,
										name: fieldName
									};

									var nestedFields = instance._getFieldsList(null, item, true);

									if (nestedFields._nodes.length) {
										field.nestedFieldValues = instance._getFieldValues(nestedFields, true);
									}

									field.value = instance.fieldsLocalizationMap[instanceId];

									fieldValues.push(field);
								}
							}
						);

						return fieldValues;
					},

					_getFieldsList: function(fieldName, parentNode, directChildren) {
						var instance = this;

						var container;

						var selector = [' .field-wrapper'];

						if (parentNode) {
							container = parentNode;
						}
						else {
							container = instance.get('container');
						}

						if (directChildren) {
							selector.unshift('>');
						}

						if (fieldName) {
							selector.push('[data-fieldName="' + fieldName + '"]');
						}

						return container.all(selector.join(''));
					},

					_getFieldValue: function(fieldNode) {
						var value;

						if (fieldNode.get('type') === CHECKBOX_TYPE) {
							value = fieldNode.get('checked').toString();
						}
						else {
							value = Lang.String.unescapeHTML(fieldNode.get('value'));
						}

						return value;
					},

					_insertField: function(fieldNode) {
						var instance = this;

						var fieldName = fieldNode.getData('fieldName');

						instance._getField(
							fieldName,
							function(newFieldHTML) {
								fieldNode.insert(newFieldHTML, 'after');

								instance._syncRepeatableFields();
							}
						);
					},

					_isFieldLocalizable: function(fieldName, definitionFields, isFieldLocalizable) {
						var instance = this;

						A.each(
							definitionFields,
							function(field) {
								if (field['name'] === fieldName) {
									isFieldLocalizable = field['localizable'];
								}
								else if (field['nestedFields']) {
									isFieldLocalizable = instance._isFieldLocalizable(fieldName, field['nestedFields'], isFieldLocalizable);
								}
							}
						);

						return isFieldLocalizable;
					},

					_onClickRepeatableButton: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var fieldNode = currentTarget.ancestor('.field-wrapper');

						if (currentTarget.hasClass('lfr-ddm-repeatable-add-button')) {
							instance._insertField(fieldNode);
						}
						else if (currentTarget.hasClass('lfr-ddm-repeatable-delete-button')) {
							instance._removeField(fieldNode);
						}
					},

					_onDeleteAvailableLocale: function(event) {
						var instance = this;

						var map = instance.fieldsLocalizationMap;

						for (var prop in map) {
							if (map.hasOwnProperty(prop)) {
								delete map[prop][event.locale];
							}
						}
					},

					_onEditingLocaleChange: function(event) {
						var instance = this;

						var defaultLocale = instance.translationManager.get('defaultLocale');

						var editingLocale = instance.translationManager.get('editingLocale');

						var locale = editingLocale || defaultLocale;

						instance._registerFieldsLocalizationMap(locale);

						instance._syncFieldsValues(event.newVal);
					},

					_onHoverRepeatableButton: function(event) {
						var instance = this;

						var fieldNode = event.currentTarget.ancestor('.field-wrapper');

						fieldNode.toggleClass('lfr-ddm-repeatable-active', (event.phase === 'over'));
					},

					_onSubmitForm: function(event) {
						var instance = this;

						var defaultLocale = instance.translationManager.get('defaultLocale');

						var editingLocale = instance.translationManager.get('editingLocale');

						var locale = editingLocale || defaultLocale;

						instance._registerFieldsLocalizationMap(locale);

						instance._syncFieldsTreeUI();

						submitForm(instance.form);
					},

					_registerFieldsLocalizationMap: function(locale) {
						var instance = this;

						var definitionFields = instance.get('definition').fields;

						var fields = instance._getFieldsList(null, null, false);

						var map = instance.fieldsLocalizationMap;

						var portletNamespace = instance.get('portletNamespace');

						A.each(
							fields,
							function(field) {
								var field = new Field(
									{
										nodeWrapper: field,
										portletNamespace: portletNamespace
									}
								);

								var fieldName = field.getName();

								var fieldNamespace = field.getNamespace();

								var instanceId = fieldNamespace.split(INSTANCE_STR)[1];

								var isFieldLocalizable;

								var childNode = field.getChildNode();

								var value;

								if (childNode) {
									value = field.getValue(childNode);

									isFieldLocalizable = instance._isFieldLocalizable(fieldName, definitionFields);

									if (isFieldLocalizable) {
										map[instanceId] = map[instanceId] || {};
										map[instanceId][locale] = value;
									}
									else {
										map[instanceId] = value;
									}
								}
							}
						);
					},

					_removeField: function(fieldNode) {
						var instance = this;

						fieldNode.remove();

						instance._syncRepeatableFields();
					},

					_renderRepeatableUI: function(fieldNode) {
						var instance = this;

						var fieldRepeatable = A.DataType.Boolean.parse(fieldNode.getData('repeatable'));

						if (instance.get('repeatable') && fieldRepeatable) {
							if (!fieldNode.getData('rendered-toolbar')) {
								var fieldName = fieldNode.getData('fieldName');

								var parentNode = instance._getFieldParentNode(fieldNode);

								var fieldsList = instance._getFieldsList(fieldName, parentNode);

								var html = TPL_ADD_REPEATABLE;

								if (fieldsList.indexOf(fieldNode) > 0) {
									html += TPL_DELETE_REPEATABLE;
								}

								fieldNode.append(html);

								fieldNode.plug(A.Plugin.ParseContent);

								fieldNode.setData('rendered-toolbar', true);
							}
						}

						instance._getFieldsList(null, fieldNode).each(
							function(item, index) {
								instance._renderRepeatableUI(item);
							}
						);
					},

					_syncFieldsTreeUI: function() {
						var instance = this;

						var defaultLocale = instance.translationManager.get('defaultLocale');

						var availableLocales = instance.translationManager.get('availableLocales');

						availableLocales.push(defaultLocale);

						var ddmFormValuesInput = instance.get('ddmFormValuesInput');

						var fieldsList = instance._getFieldsList(null, null, true);

						var ddmFormValuesJSON = {
							availableLanguageIds: availableLocales,
							defaultLanguageId: defaultLocale,
							fieldValues: instance._getFieldValues(fieldsList, false)
						};

						ddmFormValuesInput.set('value', JSON.stringify(ddmFormValuesJSON));
					},

					_syncFieldsValues: function(locale) {
						var instance = this;

						var defaultLocale = instance.translationManager.get('defaultLocale');

						var fields = instance._getFieldsList(null, null, false);

						var map = instance.fieldsLocalizationMap;

						var portletNamespace = instance.get('portletNamespace');

						A.each(
							fields,
							function(field) {
								var field = new Field(
									{
										nodeWrapper: field,
										portletNamespace: portletNamespace
									}
								);

								var fieldName = field.getName();

								var fieldNamespace = field.getNamespace();

								var instanceId = fieldNamespace.split(INSTANCE_STR)[1];

								var fieldMap = map[instanceId];

								var childNode = field.getChildNode();

								var value;

								if (childNode && fieldMap) {
									value = fieldMap[locale] || fieldMap[defaultLocale];
									field.setValue(childNode, value);
								}
							}
						);
					},

					_syncRepeatableFields: function() {
						var instance = this;

						instance._getFieldsList().each(
							function(item, index) {
								instance._renderRepeatableUI(item);
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDM').Form = Form;
	},
	'',
	{
		requires: ['aui-base', 'aui-datatype', 'aui-io-request', 'aui-parse-content']
	}
);