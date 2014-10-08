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

		var isNode = function(node) {
			return node && (node._node || node.nodeType);
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

		FieldsSupport.prototype = {
			getFieldInfo: function(tree, key, value) {
				var queue = new A.Queue(tree);

				var addToQueue = function(item) {
					if (AArray.indexOf(queue._q, item) === -1) {
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
			},

			getFieldNodes: function() {
				var instance = this;

				return instance.get('container').all('> .field-wrapper');
			},

			_getField: function(fieldNode) {
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
							dataType: fieldDefinition.dataType,
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
			},

			_getTemplate: function(callback) {
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
			},

			_valueFields: function() {
				var instance = this;

				var fields = [];

				instance.getFieldNodes().each(
					function(item) {
						fields.push(instance._getField(item));
					}
				);

				return fields;
			}
		};

		var Field = A.Component.create(
			{
				ATTRS: {
					container: {
						setter: A.one
					},

					dataType: {
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

						instance.syncLabelUI();
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

						instance.syncLabelUI();
						instance.syncValueUI();
					},

					_getLocalizable: function() {
						var instance = this;

						return instance.getFieldDefinition().localizable === true;
					},

					_getRepeatable: function() {
						var instance = this;

						return instance.getFieldDefinition().repeatable === true;
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

					getFieldDefinition: function() {
						var instance = this;

						var definition = instance.get('definition');

						var name = instance.get('name');

						return instance.getFieldInfo(definition, 'name', name);
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
						).join('');
					},

					getInputNode: function() {
						var instance = this;

						return instance.get('container').one('[name=' + instance.getInputName() + ']');
					},

					getLabelNode: function() {
						var instance = this;

						return instance.get('container').one('.control-label');
					},

					getSiblings: function() {
						var instance = this;

						var parent = instance.get('parent');
						var name = instance.get('name');

						return AArray.filter(
							parent.get('fields'),
							function(item) {
								return item.get('name') === name;
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

					setLabel: function(label) {
						var instance = this;

						var labelNode = instance.getLabelNode();

						if (Lang.isValue(label)) {
							labelNode.html(label);
						}
					},

					setValue: function(value) {
						var instance = this;

						var inputNode = instance.getInputNode();

						if (Lang.isValue(value)) {
							inputNode.val(value);
						}
					},

					syncLabelUI: function() {
						var instance = this;

						var fieldDefinition = instance.getFieldDefinition();

						var labelsMap = fieldDefinition.label;

						instance.setLabel(labelsMap[instance.get('displayLocale')]);
					},

					syncValueUI: function() {
						var instance = this;

						var dataType = instance.get('dataType');

						if (dataType) {
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
						}
					},

					toJSON: function() {
						var instance = this;

						var fieldJSON = {
							instanceId: instance.get('instanceId'),
							name: instance.get('name')
						};

						var dataType = instance.get('dataType');

						if (dataType) {
							instance.updateLocalizationMap(instance.get('displayLocale'));

							fieldJSON.value = instance.get('localizationMap');
						}

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
					getLabelNode: function() {
						var instance = this;

						return instance.get('container').one('label');
					},

					getValue: function() {
						var instance = this;

						return instance.getInputNode().test(':checked') + '';
					},

					setLabel: function(label) {
						var instance = this;

						var labelNode = instance.getLabelNode();

						var inputNode = instance.getInputNode();

						if (Lang.isValue(label)) {
							labelNode.html('&nbsp;' + label);

							labelNode.prepend(inputNode);
						}
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
											groupId: event.groupid,
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

		var GeolocationField = A.Component.create(
			{
				EXTENDS: Field,

				prototype: {
					initializer: function() {
						var instance = this;

						var container = instance.get('container');

						container.one('.geolocate-button').on('click', instance.getGeolocation, instance);
					},

					getGeolocation: function() {
						var instance = this;

						var inputName = instance.getInputName();

						var coordinatesNode = A.one('#' + inputName + 'Coordinates');
						var coordinatesContainerNode = A.one('#' + inputName + 'CoordinatesContainer');

						coordinatesContainerNode.show();

						coordinatesNode.html(Liferay.Language.get('loading'));

						Liferay.Util.getGeolocation(
							function(latitude, longitude) {
								instance.setValue(
									AJSON.stringify(
										{
											latitude: latitude,
											longitude: longitude
										}
									)
								);
							}
						);
					},

					setValue: function(value) {
						var instance = this;

						if (Lang.isString(value) && value !== '') {
							var inputNode = instance.getInputNode();

							inputNode.val(value);

							value = AJSON.parse(value);

							var coordinatesNode = A.one('#' + instance.getInputName() + 'Coordinates');

							coordinatesNode.html([value.latitude, value.longitude].join(', '));
						}
					}
				}
			}
		);

		FieldTypes['ddm-geolocation'] = GeolocationField;

		var TextHTMLField = A.Component.create(
			{
				EXTENDS: Field,

				prototype: {
					getEditor: function() {
						var instance = this;

						return window[instance.getInputName() + 'Editor'];
					},

					getValue: function() {
						var instance = this;

						var editor = instance.getEditor();

						return isNode(editor) ? A.one(editor).val() : editor.getHTML();
					},

					setValue: function(value) {
						var instance = this;

						var editor = instance.getEditor();

						if (isNode(editor)) {
							TextHTMLField.superclass.setValue.apply(instance, arguments);
						}
						else {
							editor.setHTML(value);
						}
					}
				}
			}
		);

		FieldTypes['ddm-text-html'] = TextHTMLField;

		var RadioField = A.Component.create(
			{
				EXTENDS: Field,

				prototype: {
					getInputNode: function() {
						var instance = this;

						var container = instance.get('container');

						return container.one('[name=' + instance.getInputName() + ']:checked');
					},

					getValue: function() {
						var instance = this;

						var value = '';

						if (instance.getInputNode()) {
							value = RadioField.superclass.getValue.apply(instance, arguments);
						}

						return AJSON.stringify([value]);
					},

					setLabel: function() {
						var instance = this;

						var container = instance.get('container');

						var fieldDefinition = instance.getFieldDefinition();

						container.all('label').each(
							function(item, index) {
								var optionDefinition = fieldDefinition.options[index];

								var inputNode = item.one('input');

								var optionLabel = optionDefinition.label[instance.get('displayLocale')];

								if (Lang.isValue(optionLabel)) {
									item.html(optionLabel);

									item.prepend(inputNode);
								}
							}
						);

						RadioField.superclass.setLabel.apply(instance, arguments);
					},

					setValue: function(value) {
						var instance = this;

						var container = instance.get('container');

						var radioNodes = container.all('[name=' + instance.getInputName() + ']');

						radioNodes.set('checked', false);

						if (Lang.isString(value)) {
							value = AJSON.parse(value);
						}

						if (value.length) {
							value = value[0];
						}

						radioNodes.filter('[value=' + value + ']').set('checked', true);
					}
				}
			}
		);

		FieldTypes.radio = RadioField;

		var SelectField = A.Component.create(
			{
				EXTENDS: RadioField,

				prototype: {
					getInputNode: function() {
						var instance = this;

						return Field.prototype.getInputNode.apply(instance, arguments);
					},

					setLabel: function() {
						var instance = this;

						var fieldDefinition = instance.getFieldDefinition();

						instance.getInputNode().all('option').each(
							function(item, index) {
								var optionDefinition = fieldDefinition.options[index];

								var optionLabel = optionDefinition.label[instance.get('displayLocale')];

								if (Lang.isValue(optionLabel)) {
									item.html(optionLabel);
								}
							}
						);

						Field.prototype.setLabel.apply(instance, arguments);
					},

					setValue: function(value) {
						var instance = this;

						if (Lang.isString(value)) {
							value = AJSON.parse(value);
						}

						if (value.length) {
							value = value[0];
						}

						Field.prototype.setValue.call(instance, value);
					}
				}
			}
		);

		FieldTypes.select = SelectField;

		var Form = A.Component.create(
			{
				ATTRS: {
					ddmFormValuesInput: {
						setter: A.one
					},

					displayLocale: {
						valueFn: '_valueDisplayLocale'
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