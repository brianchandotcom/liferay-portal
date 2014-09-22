AUI.add(
	'liferay-ddm-form',
	function(A) {
		var AArray = A.Array;

		var AJSON = A.JSON;

		var Lang = A.Lang;

		var INSTANCE_ID_PREFIX = '_INSTANCE_';

		var SELECTOR_REPEAT_BUTTONS = '.lfr-ddm-repeatable-add-button, .lfr-ddm-repeatable-delete-button';

		var TPL_ADD_REPEATABLE = '<a class="lfr-ddm-repeatable-add-button icon-plus-sign" href="javascript:;"></a>';

		var TPL_DELETE_REPEATABLE = '<a class="lfr-ddm-repeatable-delete-button icon-minus-sign" href="javascript:;"></a>';

		var FieldTypes = Liferay.namespace('DDM.FieldTypes');

		var getFieldClass = function(type) {
			return FieldTypes[type] || FieldTypes.field;
		};

		var DDMPortletSupport = function() {};

		DDMPortletSupport.ATTRS = {
			classNameId: {
			},

			classPK: {
			},

			doAsGroupId: {
			},

			fieldsNamespace: {
			},

			p_l_id: {
			},

			portletNamespace: {
			}
		};

		var FieldsSupport = function() {};

		FieldsSupport.ATTRS = {
			container: {
				setter: A.one
			},

			definition: {
			},

			displayLocale: {
			},

			fields: {
				valueFn: '_valueFields'
			},

			values: {
				value: {}
			}
		};

		FieldsSupport.prototype._getField = function(fieldNode) {
			var instance = this;

			var fieldInstanceId = fieldNode.getData('fieldNamespace');

			fieldInstanceId = fieldInstanceId.replace(INSTANCE_ID_PREFIX, '');

			var fieldName = fieldNode.getData('fieldName');

			var definition = instance.get('definition');

			var fieldDefinition = instance.getFieldInfo(definition, 'name', fieldName);

			var FieldClass = getFieldClass(fieldDefinition.type);

			var field = new FieldClass(
				A.merge(
					instance.getAttrs(A.Object.keys(DDMPortletSupport.ATTRS)),
					{
						container: fieldNode,
						definition: definition,
						displayLocale: instance.get('displayLocale'),
						instanceId: fieldInstanceId,
						name: fieldName,
						parent: instance,
						values: instance.get('values')
					}
				)
			);

			instance.addTarget(field);

			return field;
		};

		FieldsSupport.prototype._getTemplate = function(callback) {
			var instance = this;

			A.io.request(
				themeDisplay.getPathMain() + '/dynamic_data_mapping/render_structure_field',
				{
					data: {
						classNameId: instance.get('classNameId'),
						classPK: instance.get('classPK'),
						controlPanelCategory: 'portlet',
						doAsGroupId: instance.get('doAsGroupId'),
						fieldName: instance.get('name'),
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
		};

		FieldsSupport.prototype._valueFields = function() {
			var instance = this;

			var fields = [];

			instance.getFieldNodes().each(
				function(item) {
					fields.push(instance._getField(item));
				}
			);

			return fields;
		};

		FieldsSupport.prototype.getFieldInfo = function(tree, key, value) {
			var queue = new A.Queue();
			var set = new A.Set();

			queue.add(tree);
			set.add(tree);

			var addToQueue = function(item) {
				if (!set.has(item)) {
					set.add(item);
					queue.add(item);
				}
			};

			var fieldInfo = {};

			while (queue.size() > 0) {
				var next = queue.next();

				if (next[key] === value) {
					fieldInfo = next;
				}
				else {
					var children = next.fields || next.nestedFields || next.fieldValues || next.nestedFieldValues;

					if (children) {
						AArray.each(children, addToQueue);
					}
				}
			}

			return fieldInfo;
		};

		FieldsSupport.prototype.getFieldNodes = function() {
			var instance = this;

			return instance.get('container').all('> .field-wrapper');
		};

		var Field = A.Component.create(
			{
				ATTRS: {
					container: {
						setter: A.one
					},

					definition: {
						validator: Lang.isObject
					},

					instanceId: {
					},

					localizable: {
						getter: '_getLocalizable',
						readOnly: true
					},

					localizationMap: {
						valueFn: '_valueLocalizationMap'
					},

					name: {
						validator: Lang.isString
					},

					node: {
					},

					parent: {
					},

					repeatable: {
						getter: '_getRepeatable',
						readOnly: true
					}
				},

				AUGMENTS: [DDMPortletSupport, FieldsSupport],

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-field',

				prototype: {
					initializer: function() {
						var instance = this;

						if (instance.get('localizable')) {
							instance.after(
								{
									'translationmanager:deleteAvailableLocale': instance._afterDeleteAvailableLocale,
									'translationmanager:editingLocaleChange': instance._afterEditingLocaleChange
								}
							);
						}
					},

					renderUI: function() {
						var instance = this;

						if (instance.get('repeatable')) {
							var container = instance.get('container');

							container.append(TPL_ADD_REPEATABLE);

							if (AArray.indexOf(instance.getSiblings(), instance) > 0) {
								container.append(TPL_DELETE_REPEATABLE);
							}

							container.delegate('click', instance._handleToolbarClick, SELECTOR_REPEAT_BUTTONS, instance);

							container.plug(A.Plugin.ParseContent);
						}

						instance.syncValueUI();
					},

					_afterDeleteAvailableLocale: function(event) {
						var instance = this;

						var localizationMap = instance.get('localizationMap');

						delete localizationMap[event.locale];

						instance.set('localizationMap', localizationMap);
					},

					_afterEditingLocaleChange: function(event) {
						var instance = this;

						var translationManager = event.target;

						var defaultLocale = translationManager.get('defaultLocale');

						var availableLocales = translationManager.get('availableLocales');

						if (AArray.indexOf([defaultLocale].concat(availableLocales), event.prevVal) > -1) {
							instance.updateLocalizationMap(event.prevVal);
						}

						instance.set('displayLocale', event.newVal);

						instance.syncValueUI();
					},

					_getLocalizable: function() {
						var instance = this;

						var definition = instance.get('definition');

						var name = instance.get('name');

						return instance.getFieldInfo(definition, 'name', name).localizable === true;
					},

					_getRepeatable: function() {
						var instance = this;

						var definition = instance.get('definition');

						var name = instance.get('name');

						return instance.getFieldInfo(definition, 'name', name).repeatable === true;
					},

					_handleToolbarClick: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						if (currentTarget.hasClass('lfr-ddm-repeatable-add-button')) {
							instance.repeat();
						}
						else if (currentTarget.hasClass('lfr-ddm-repeatable-delete-button')) {
							instance.remove();
						}
					},

					_valueLocalizationMap: function() {
						var instance = this;

						var values = instance.get('values');

						var instanceId = instance.get('instanceId');

						var fieldValue = instance.getFieldInfo(values, 'instanceId', instanceId);

						var localizationMap = {};

						if (!A.Object.isEmpty(fieldValue)) {
							localizationMap = fieldValue.value;
						}

						return localizationMap;
					},

					getInputName: function() {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						var fieldsNamespace = instance.get('fieldsNamespace');

						var prefix = [portletNamespace];

						if (fieldsNamespace) {
							prefix.push(fieldsNamespace);
						}

						return prefix.concat(
							[
								instance.get('name'),
								INSTANCE_ID_PREFIX,
								instance.get('instanceId')
							]
						) .join('');
					},

					getInputNode: function() {
						var instance = this;

						return instance.get('container').one('[name=' + instance.getInputName() + ']');
					},

					getSiblings: function() {
						var instance = this;

						var parent = instance.get('parent');

						return AArray.filter(
							parent.get('fields'),
							function(item) {
								return item.get('name') === instance.get('name');
							}
						);
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						return Lang.String.unescapeHTML(inputNode.val());
					},

					remove: function() {
						var instance = this;

						var container = instance.get('container');

						container.remove(true);

						var parent = instance.get('parent');

						var siblings = parent.get('fields');

						var index = AArray.indexOf(siblings, instance);

						siblings.splice(index, 1);

						instance.destroy();
					},

					repeat: function() {
						var instance = this;

						instance._getTemplate(
							function(fieldTemplate) {
								var fieldNode = A.Node.create(fieldTemplate);

								instance.get('container').insert(fieldNode, 'after');

								var parent = instance.get('parent');

								var siblings = parent.get('fields');

								var field = instance._getField(fieldNode);

								var index = AArray.indexOf(siblings, instance);

								siblings.splice(++index, 0, field);

								field.set('parent', parent);

								field.renderUI();
							}
						);
					},

					setValue: function(value) {
						var instance = this;

						var inputNode = instance.getInputNode();

						if (Lang.isValue(value)) {
							inputNode.val(value);
						}
					},

					syncValueUI: function() {
						var instance = this;

						var localizationMap = instance.get('localizationMap');

						var value;

						if (Lang.isString(localizationMap)) {
							value = localizationMap;
						}
						else if (!A.Object.isEmpty(localizationMap)) {
							value = localizationMap[instance.get('displayLocale')];
						}

						if (Lang.isUndefined(value)) {
							value = instance.getValue();
						}

						instance.setValue(value);
					},

					toJSON: function() {
						var instance = this;

						instance.updateLocalizationMap(instance.get('displayLocale'));

						var fieldJSON = {
							instanceId: instance.get('instanceId'),
							name: instance.get('name'),
							value: instance.get('localizationMap')
						};

						var fields = instance.get('fields');

						if (fields.length) {
							fieldJSON.nestedFieldValues = AArray.invoke(fields, 'toJSON');
						}

						return fieldJSON;
					},

					updateLocalizationMap: function(locale) {
						var instance = this;

						var localizationMap = instance.get('localizationMap');

						var value = instance.getValue();

						if (instance.get('localizable')) {
							localizationMap[locale] = value;
						}
						else {
							localizationMap = value;
						}

						instance.set('localizationMap', localizationMap);
					}
				}
			}
		);

		FieldTypes.field = Field;

		var CheckboxField = A.Component.create(
			{
				EXTENDS: Field,

				prototype: {
					getValue: function() {
						var instance = this;

						return instance.getInputNode().test(':checked') + '';
					},

					setValue: function(value) {
						var instance = this;

						instance.getInputNode().attr('checked', value === 'true');
					}
				}
			}
		);

		FieldTypes.checkbox = CheckboxField;

		var DateField = A.Component.create(
			{
				EXTENDS: Field,

				prototype: {
					getDatePicker: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						return Liferay.component(inputNode.attr('id') + 'DatePicker');
					},

					getValue: function() {
						var instance = this;

						var datePicker = instance.getDatePicker();

						var timestamp = datePicker.getDate().getTime();

						var inputNode = instance.getInputNode();

						return inputNode.val() ? String(timestamp) : '';
					},

					setValue: function(value) {
						var instance = this;

						var datePicker = instance.getDatePicker();

						datePicker.set('activeInput', instance.getInputNode());

						datePicker.deselectDates();

						if (value) {
							datePicker.selectDates(new Date(Lang.toInt(value)));
						}
					}
				}
			}
		);

		FieldTypes['ddm-date'] = DateField;

		var DocumentLibraryField = A.Component.create(
			{
				EXTENDS: Field,

				prototype: {
					initializer: function() {
						var instance = this;

						var container = instance.get('container');

						container.delegate('click', instance._handleButtonsClick, '.btn', instance);
					},

					_handleButtonsClick: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var portletNamespace = instance.get('portletNamespace');

						if (currentTarget.test('.select-button')) {
							Liferay.Util.selectEntity(
								{
									dialog: {
										constrain: true,
										destroyOnHide: true,
										modal: true
									},
									eventName: portletNamespace + 'selectDocumentLibrary',
									id: portletNamespace + 'selectDocumentLibrary',
									title: Liferay.Language.get('select-document'),
									uri: instance.getDocumentLibraryURL()
								},
								function(event) {
									instance.setValue(
										{
											groupId: event.groupId,
											title: event.title,
											uuid: event.uuid
										}
									);
								}
							);
						}
						else if (currentTarget.test('.clear-button')) {
							instance.setValue('');
						}
					},

					getDocumentLibraryURL: function() {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						var portletURL = Liferay.PortletURL.createURL(themeDisplay.getURLControlPanel());

						portletURL.setDoAsGroupId(instance.get('doAsGroupId'));
						portletURL.setParameter('eventName', portletNamespace + 'selectDocumentLibrary');
						portletURL.setParameter('groupId', themeDisplay.getScopeGroupId());
						portletURL.setParameter('refererPortletName', '');
						portletURL.setParameter('struts_action', '/document_selector/view');
						portletURL.setParameter('tabs1Names', 'documents');
						portletURL.setPortletId('200');
						portletURL.setWindowState('pop_up');

						return portletURL.toString();
					},

					setValue: function(value) {
						var instance = this;

						if (Lang.isString(value) && value !== '') {
							value = AJSON.parse(value);
						}

						var titleNode = A.one('#' + instance.getInputName() + 'Title');

						titleNode.val((value && value.title) || '');

						DocumentLibraryField.superclass.setValue.call(instance, AJSON.stringify(value));
					}
				}
			}
		);

		FieldTypes['ddm-documentlibrary'] = DocumentLibraryField;

		var Form = A.Component.create(
			{
				ATTRS: {
					ddmFormValuesInput: {
						setter: A.one
					},

					displayLocale: {
						valueFn: '_valueDisplayLocale'
					},

					namespace: {
					},

					repeatable: {
						validator: Lang.isBoolean,
						value: false
					},

					translationManager: {
						valueFn: '_valueTranslationManager'
					}
				},

				AUGMENTS: [DDMPortletSupport, FieldsSupport],

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.bindUI();
						instance.renderUI();
					},

					renderUI: function() {
						var instance = this;

						AArray.invoke(instance.get('fields'), 'renderUI');
					},

					bindUI: function() {
						var instance = this;

						var container = instance.get('container');

						instance.formNode = container.ancestor('form', true);

						if (instance.formNode) {
							instance.formNode.on('submit', instance._onSubmitForm, instance);
						}
					},

					_onSubmitForm: function(event) {
						var instance = this;

						event.preventDefault();

						var ddmFormValuesInput = instance.get('ddmFormValuesInput');

						ddmFormValuesInput.set('value', AJSON.stringify(instance.toJSON()));

						submitForm(instance.formNode);
					},

					_valueDisplayLocale: function() {
						var instance = this;

						var translationManager = instance.get('translationManager');

						return translationManager.get('editingLocale');
					},

					_valueTranslationManager: function() {
						var instance = this;

						var translationManager = Liferay.component(instance.get('portletNamespace') + 'translationManager');

						if (!translationManager) {
							translationManager = new Liferay.TranslationManager();
						}

						translationManager.addTarget(instance);

						return translationManager;
					},

					toJSON: function() {
						var instance = this;

						var translationManager = instance.get('translationManager');

						return {
							availableLanguageIds: translationManager.get('availableLocales'),
							defaultLanguageId: translationManager.get('defaultLocale'),
							fieldValues: AArray.invoke(instance.get('fields'), 'toJSON')
						};
					}
				}
			}
		);

		Liferay.DDM.Form = Form;
	},
	'',
	{
		requires: ['aui-base', 'aui-datatype', 'aui-io-request', 'aui-parse-content', 'aui-set', 'json', 'liferay-portlet-url', 'liferay-translation-manager']
	}
);