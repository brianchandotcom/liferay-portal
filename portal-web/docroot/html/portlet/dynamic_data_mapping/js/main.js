AUI.add(
	'liferay-portlet-dynamic-data-mapping',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var BODY = A.getBody();

		var instanceOf = A.instanceOf;
		var isObject = Lang.isObject;
		var isUndefined = Lang.isUndefined;
		var isNull = Lang.isNull;

		var DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

		var LOCALIZABLE_FIELD_ATTRS = ['label', 'predefinedValue', 'style' ,'tip'];

		var UNLOCALIZABLE_FIELD_ATTRS = ['dataType', 'fieldNamespace', 'indexType', 'localizable', 'multiple', 'name', 'readOnly', 'repeatable', 'required', 'type'];

		var MAP_HIDDEN_FIELD_ATTRS = {
			checkbox: ['readOnly', 'required'],

			DEFAULT: ['readOnly'],

			separator: ['readOnly', 'required', 'predefinedValue', 'indexType']
		};

		var SETTINGS_TAB_INDEX = 1;

		var STR_BLANK = '';

		var STR_SPACE = ' ';

		DEFAULTS_FORM_VALIDATOR.STRINGS.structureFieldName = Liferay.Language.get('please-enter-only-alphanumeric-characters');

		DEFAULTS_FORM_VALIDATOR.RULES.structureFieldName = function(value) {
			return (/^[\w\-]+$/).test(value);
		};

		var LiferayAvailableField = A.Component.create(
			{
				ATTRS: {
					localizationMap: {
						validator: isObject,
						value: {}
					}
				},

				EXTENDS: A.FormBuilderAvailableField,

				NAME: 'availableField'
			}
		);

		A.LiferayAvailableField = LiferayAvailableField;

		var LiferayFormBuilder = A.Component.create(
			{
				ATTRS: {
					availableFields: {
						validator: isObject,
						valueFn: function() {
							return LiferayFormBuilder.AVAILABLE_FIELDS.DEFAULT;
						}
					},

					portletNamespace: {
						value: STR_BLANK
					},

					portletResourceNamespace: {
						value: STR_BLANK
					},

					strings: {
						value: {
							addNode: Liferay.Language.get('add-field'),
							button: Liferay.Language.get('button'),
							buttonType: Liferay.Language.get('button-type'),
							deleteFieldsMessage: Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-entries'),
							duplicateMessage: Liferay.Language.get('duplicate'),
							editMessage: Liferay.Language.get('edit'),
							label: Liferay.Language.get('field-label'),
							large: Liferay.Language.get('large'),
							localizable: Liferay.Language.get('localizable'),
							medium: Liferay.Language.get('medium'),
							multiple: Liferay.Language.get('multiple'),
							name: Liferay.Language.get('name'),
							no: Liferay.Language.get('no'),
							options: Liferay.Language.get('options'),
							predefinedValue: Liferay.Language.get('predefined-value'),
							propertyName: Liferay.Language.get('property-name'),
							required: Liferay.Language.get('required'),
							reset: Liferay.Language.get('reset'),
							save: Liferay.Language.get('save'),
							settings: Liferay.Language.get('settings'),
							showLabel: Liferay.Language.get('show-label'),
							small: Liferay.Language.get('small'),
							submit: Liferay.Language.get('submit'),
							tip: Liferay.Language.get('tip'),
							type: Liferay.Language.get('type'),
							value: Liferay.Language.get('value'),
							width: Liferay.Language.get('width'),
							yes: Liferay.Language.get('yes')
						}
					},

					translationManager: {
						validator: isObject,
						value: {}
					},

					validator: {
						setter: function(val) {
							var instance = this;

							var config = A.merge(
								{
									fieldStrings: {
										name: {
											required: Liferay.Language.get('this-field-is-required')
										}
									},
									rules: {
										name: {
											required: true,
											structureFieldName: true
										}
									}
								},
								val
							);

							return config;
						},
						value: {}
					}
				},

				EXTENDS: A.FormBuilder,

				NAME: 'liferayformbuilder',

				UNIQUE_FIELD_NAMES_MAP: new A.Map(),

				prototype: {
					initializer: function() {
						var instance = this;

						instance.LOCALIZABLE_FIELD_ATTRS = AArray(LOCALIZABLE_FIELD_ATTRS);
						instance.MAP_HIDDEN_FIELD_ATTRS = A.clone(MAP_HIDDEN_FIELD_ATTRS);

						var translationManager = instance.translationManager = new Liferay.TranslationManager(instance.get('translationManager'));

						instance.after(
							'render',
							function(event) {
								translationManager.render();
							}
						);

						instance.addTarget(Liferay.Util.getOpener().Liferay);

						instance._toggleInputDirection(translationManager.get('defaultLocale'));
					},

					bindUI: function() {
						var instance = this;

						LiferayFormBuilder.superclass.bindUI.apply(instance, arguments);

						instance.translationManager.after('editingLocaleChange', instance._afterEditingLocaleChange, instance);

						instance.on('model:change', instance._onPropertyModelChange);
						instance.on('save', instance._onSave);
					},

					createField: function() {
						var instance = this;

						var field = LiferayFormBuilder.superclass.createField.apply(instance, arguments);

						field.set('readOnlyAttributes', instance._getReadOnlyFieldAttributes(field));
						field.set('strings', instance.get('strings'));

						return field;
					},

					getContentValue: function() {
						var instance = this;

						return window[instance.get('portletNamespace') + 'getContentValue']();
					},

					getContent: function() {
						var instance = this;

						var structure = {};

						instance._addStructureAvailableLanguageIds(structure);
						instance._addStructureDefaultLanguageId(structure);
						instance._addStructureFields(structure);

						return A.JSON.stringify(structure, null, 4);
					},

					getParsedDefinition: function(content) {
						var instance = this;

						var availableLanguageIds = content.availableLanguageIds;

						var fields = content.fields;

						A.each(
							fields,
							function(field) {
								instance._addLocalizationMapField(field, availableLanguageIds);

								if (field.options) {
									instance._addLocalizationMapFieldForOptions(field, availableLanguageIds);
								}

								instance._addLocalizationMapFieldForNestedFields(field.fields, availableLanguageIds);
							}
						);

						instance._addLocalizableFields(fields);

						return fields;
					},

					normalizeKey: function(str) {
						A.each(
							str,
							function(item, index) {
								if (!A.Text.Unicode.test(item, 'L') && !A.Text.Unicode.test(item, 'N') && !A.Text.Unicode.test(item, 'Pd')) {
									str = str.replace(item, STR_SPACE);
								}
							}
						);

						return str.replace(/\s/g, '_');
					},

					normalizeValue: function(value) {
						var instance = this;

						if (Lang.isUndefined(value)) {
							value = STR_BLANK;
						}

						return value;
					},

					setEditingField: function() {
						var instance = this;

						var tabs = instance.tabView.getTabs();

						var settingsTabIndex = tabs.indexOf(instance.tabView.getActiveTab());

						var fields = instance.get('fields');

						if (settingsTabIndex === SETTINGS_TAB_INDEX) {
							instance.editingField = fields._items[0];
						}
					},

					_addLocalizableFields: function(fields) {
						var instance = this;

						A.each(
							fields,
							function(field) {
								A.each(
									instance.LOCALIZABLE_FIELD_ATTRS,
									function(localizableField) {
										if (field[localizableField]) {
											field[localizableField] = field[localizableField][instance.translationManager.get('editingLocale')];
										}
									}
								);

								if (field.fields) {
									instance._addLocalizableFields(field.fields);
								}
							}
						);
					},

					_addLocalizationMapField: function(field, availableLanguageIds) {
						var instance = this;

						A.each(
							availableLanguageIds,
							function(language) {

								field.localizationMap = field.localizationMap || {};
								field.localizationMap[language] = {};

								A.each(
									instance.LOCALIZABLE_FIELD_ATTRS,
									function(localizableField) {
										if (field[localizableField]) {
											field.localizationMap[language][localizableField] = field[localizableField][language];
										}

									}
								);
							}
						);
					},

					_addLocalizationMapFieldForNestedFields: function(fields, availableLanguageIds) {
						var instance = this;

						A.each(
							fields,
							function(field) {
								instance._addLocalizationMapField(field, availableLanguageIds);

								if (field.options) {
									instance._addLocalizationMapFieldForOptions(field, availableLanguageIds);
								}
							}
						);
					},

					_addLocalizationMapFieldForOptions: function(field, availableLanguageIds) {
						var instance = this;

						var labels;

						var translationManager = instance.translationManager;

						A.each(
							field.options,
							function(option) {
								labels = option.label;
								option.label = labels[translationManager.get('editingLocale')];
								option.localizationMap = {};
								A.each(
									availableLanguageIds,
									function(language) {
										option.localizationMap[language] = {};
										option.localizationMap[language]['label'] = labels[language];
									}
								);
							}
						);
					},

					_addStructureAvailableLanguageIds: function(structure) {
						var instance = this;

						var translationManager = instance.translationManager;

						structure.availableLanguageIds = translationManager.get('availableLocales');
					},

					_addStructureDefaultLanguageId: function(structure) {
						var instance = this;

						var translationManager = instance.translationManager;

						structure.defaultLanguageId = translationManager.get('defaultLocale');
					},

					_addStructureFieldLocalizedAttributes: function(field, structureField) {
						var instance = this;

						AArray.each(
							instance.LOCALIZABLE_FIELD_ATTRS,
							function(attr) {
								structureField[attr] = instance._getLocalizedValue(field, attr);
							}
						);
					},

					_addStructureFieldNestedFields: function(field, structureField) {
						var instance = this;

						var nestedFields = [];

						field.get('fields').each(
							function(childField) {
								var structureField = instance._toStructureField(childField);

								nestedFields.push(structureField);
							}
						);

						if (nestedFields.length > 0) {
							structureField.nestedFields = nestedFields;
						}
					},

					_addStructureFieldUnlocalizedAttributes: function(field, structureField) {
						var instance = this;

						AArray.each(
							UNLOCALIZABLE_FIELD_ATTRS,
							function(attr) {
								structureField[attr] = field.get(attr);
							}
						);
					},

					_addStructureFieldOptions: function(field, structureField) {
						var instance = this;

						var options = field.get('options');

						var fieldOptions = [];

						if (options) {
							AArray.each(
								options,
								function(option) {
									var fieldOption = {};

									var localizationMap = option.localizationMap;

									fieldOption.value = option.value;
									fieldOption.label = {};

									A.each(
										localizationMap,
										function(item, index, collection) {
											fieldOption.label[index] = instance.normalizeValue(item.label);
										}
									);

									fieldOptions.push(fieldOption);
								}
							);

							structureField.options = fieldOptions;
						}
					},

					_addStructureFields: function(structure) {
						var instance = this;

						structure.fields = [];

						instance.get('fields').each(
							function(field) {
								var structureField = instance._toStructureField(field);

								structure.fields.push(structureField);
							}
						);
					},

					_afterEditingLocaleChange: function(event) {
						var instance = this;

						instance._syncFieldsReadOnlyAttributes();

						instance._updateFieldsLocalizationMap(event.prevVal);

						instance._syncFieldsLocaleUI(event.newVal);

						instance._toggleInputDirection(event.newVal);
					},

					_getLocalizedValue: function(field, attribute, locale) {
						var instance = this;

						var localizationMap = field.get('localizationMap');

						var localizedValue = {};

						var translationManager = instance.translationManager;

						AArray.each(
							translationManager.get('availableLocales'),
							function(locale) {
								var value = A.Object.getValue(localizationMap, [locale, attribute]) || '';

								localizedValue[locale] = instance.normalizeValue(value);
							}
						);

						return localizedValue;
					},

					_getReadOnlyFieldAttributes: function(field) {
						var instance = this;

						var translationManager = instance.translationManager;

						var defaultLocale = translationManager.get('defaultLocale');
						var editingLocale = translationManager.get('editingLocale');

						var readOnlyAttributes = field.get('readOnlyAttributes');

						AArray.each(
							UNLOCALIZABLE_FIELD_ATTRS,
							function(item, index) {
								if (defaultLocale === editingLocale) {
									AArray.removeItem(readOnlyAttributes, item);
								}
								else {
									readOnlyAttributes.push(item);
								}
							}
						);

						return AArray.dedupe(readOnlyAttributes);
					},

					_onPropertyModelChange: function(event) {
						var instance = this;

						var changed = event.changed;

						var attributeName = event.target.get('attributeName');

						var editingField = instance.editingField;

						var readOnlyAttributes = editingField.get('readOnlyAttributes');

						if (changed.hasOwnProperty('value') && (AArray.indexOf(readOnlyAttributes, 'name') === -1)) {
							if (attributeName === 'name') {
								editingField.set('autoGeneratedName', event.autoGeneratedName === true);
							}
							else if ((attributeName === 'label') && editingField.get('autoGeneratedName')) {
								var translationManager = instance.translationManager;

								if (translationManager.get('editingLocale') === translationManager.get('defaultLocale')) {
									var normalizedLabel = LiferayFormBuilder.normalizeKey(changed.value.newVal);

									var generatedName = normalizedLabel;

									var counter = 1;

									while (LiferayFormBuilder.UNIQUE_FIELD_NAMES_MAP.has(generatedName)) {
										generatedName = normalizedLabel + counter++;
									}

									var modelList = instance.propertyList.get('data');

									var nameModel = modelList.filter(
										function(item, index) {
											return (item.get('attributeName') === 'name');
										}
									);

									if (nameModel.length) {
										nameModel[0].set(
											'value',
											generatedName,
											{
												autoGeneratedName: true
											}
										);
									}
								}
							}
						}
					},

					_onSave: function(arguments) {
						var instance = this;

						LiferayFormBuilder.superclass._onSave.apply(instance, arguments);

						var editingLocale = instance.translationManager.get('editingLocale');

						instance._updateFieldsLocalizationMap(editingLocale);
					},

					_renderSettings: function() {
						var instance = this;

						instance._renderPropertyList();
					},

					_setAvailableFields: function(val) {
						var instance = this;

						var fields = AArray.map(
							val,
							function(item, index) {
								return A.instanceOf(item, A.PropertyBuilderAvailableField) ? item : new A.LiferayAvailableField(item);
							}
						);

						fields.sort(
							function(a, b) {
								return A.ArraySort.compare(a.get('label'), b.get('label'));
							}
						);

						return fields;
					},

					_setFields: function() {
						var instance = this;

						LiferayFormBuilder.UNIQUE_FIELD_NAMES_MAP.clear();

						return LiferayFormBuilder.superclass._setFields.apply(instance, arguments);
					},

					_syncFieldOptionsLocaleUI: function(field, locale) {
						var instance = this;

						var options = field.get('options');

						AArray.each(
							options,
							function(item, index) {
								var localizationMap = item.localizationMap;

								if (isObject(localizationMap)) {
									var localeMap = localizationMap[locale];

									if (isObject(localeMap)) {
										item.label = localeMap.label;
									}
								}
							}
						);

						field.set('options', options);
					},

					_syncFieldsLocaleUI: function(locale, fields) {
						var instance = this;

						fields = fields || instance.get('fields');

						fields.each(
							function(field, index, fields) {
								if (instanceOf(field, A.FormBuilderMultipleChoiceField)) {
									instance._syncFieldOptionsLocaleUI(field, locale);
								}

								var localizationMap = field.get('localizationMap');
								var localeMap = localizationMap[locale];

								if (isObject(localizationMap) && isObject(localeMap)) {
									AArray.each(
										instance.LOCALIZABLE_FIELD_ATTRS,
										function(item, index) {
											var localizedItem = localeMap[item];

											if (!isUndefined(localizedItem) && !isNull(localizedItem)) {
												field.set(item, localizedItem);
											}
										}
									);

									instance._syncUniqueField(field);
								}

								if (instance.editingField === field) {
									instance.propertyList.set('data', field.getProperties());
								}

								instance._syncFieldsLocaleUI(locale, field.get('fields'));
							}
						);
					},

					_syncFieldsReadOnlyAttributes: function(fields) {
						var instance = this;

						fields = fields || instance.get('fields');

						fields.each(
							function(field) {
								field.set('readOnlyAttributes', instance._getReadOnlyFieldAttributes(field));

								instance._syncFieldsReadOnlyAttributes(field.get('fields'));
							}
						);
					},

					_toggleInputDirection: function(locale) {
						var rtl = (Liferay.Language.direction[locale] === 'rtl');

						BODY.toggleClass('form-builder-ltr-inputs', !rtl);
						BODY.toggleClass('form-builder-rtl-inputs', rtl);
					},

					_toStructureField: function(field) {
						var instance = this;

						var structureField = {};

						instance._addStructureFieldLocalizedAttributes(field, structureField);
						instance._addStructureFieldUnlocalizedAttributes(field, structureField);
						instance._addStructureFieldOptions(field, structureField);
						instance._addStructureFieldNestedFields(field, structureField);

						return structureField;
					},

					_updateFieldOptionsLocalizationMap: function(field, locale) {
						var instance = this;

						var options = field.get('options');

						AArray.each(
							options,
							function(item, index) {
								var localizationMap = item.localizationMap;

								if (!isObject(localizationMap)) {
									localizationMap = {};
								}

								localizationMap[locale] = {
									label: item.label
								};

								item.localizationMap = localizationMap;
							}
						);

						field.set('options', options);
					},

					_updateFieldsLocalizationMap: function(locale, fields) {
						var instance = this;

						fields = fields || instance.get('fields');

						fields.each(
							function(item, index) {
								var localizationMap = {};

								localizationMap[locale] = item.getAttrs(instance.LOCALIZABLE_FIELD_ATTRS);

								item.set(
									'localizationMap',
									A.mix(
										localizationMap,
										item.get('localizationMap')
									)
								);

								if (instanceOf(item, A.FormBuilderMultipleChoiceField)) {
									instance._updateFieldOptionsLocalizationMap(item, locale);
								}

								instance._updateFieldsLocalizationMap(locale, item.get('fields'));
							}
						);
					}
				}
			}
		);

		LiferayFormBuilder.Util = {
			getFileEntry: function(fileJSON, callback) {
				var instance = this;

				fileJSON = instance.parseJSON(fileJSON);

				Liferay.Service(
					'/dlapp/get-file-entry-by-uuid-and-group-id',
					{
						groupId: fileJSON.groupId,
						uuid: fileJSON.uuid
					},
					callback
				);
			},

			getFileEntryURL: function(fileEntry) {
				var instance = this;

				var buffer = [
					themeDisplay.getPathContext(),
					'documents',
					fileEntry.groupId,
					fileEntry.folderId,
					encodeURIComponent(fileEntry.title)
				];

				return buffer.join('/');
			},

			parseJSON: function(value) {
				var instance = this;

				var data = {};

				try {
					data = JSON.parse(value);
				}
				catch (e) {
				}

				return data;
			}
		};

		LiferayFormBuilder.DEFAULT_ICON_CLASS = 'icon-fb-custom-field';

		var AVAILABLE_FIELDS = {
			DDM_STRUCTURE: [
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.checkbox,
					iconClass: 'icon-fb-boolean',
					label: Liferay.Language.get('boolean'),
					type: 'checkbox'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-calendar',
					label: Liferay.Language.get('date'),
					type: 'ddm-date'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-fb-decimal',
					label: Liferay.Language.get('decimal'),
					type: 'ddm-decimal'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-file-text',
					label: Liferay.Language.get('documents-and-media'),
					type: 'ddm-documentlibrary'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-edit-sign',
					label: Liferay.Language.get('html'),
					type: 'ddm-text-html'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-globe',
					label: Liferay.Language.get('geolocation'),
					type: 'ddm-geolocation'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-fb-integer',
					label: Liferay.Language.get('integer'),
					type: 'ddm-integer'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-link',
					label: Liferay.Language.get('link-to-page'),
					type: 'ddm-link-to-page'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-fb-number',
					label: Liferay.Language.get('number'),
					type: 'ddm-number'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-fb-radio',
					label: Liferay.Language.get('radio'),
					type: 'radio'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-fb-select',
					label: Liferay.Language.get('select'),
					type: 'select'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-fb-text',
					label: Liferay.Language.get('text'),
					type: 'text'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-fb-text-box',
					label: Liferay.Language.get('text-box'),
					type: 'textarea'
				}
			],

			DDM_TEMPLATE: [
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-fb-paragraph',
					label: Liferay.Language.get('paragraph'),
					type: 'ddm-paragraph'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-fb-separator',
					label: Liferay.Language.get('separator'),
					type: 'ddm-separator'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-fb-fieldset',
					label: Liferay.Language.get('fieldset'),
					type: 'fieldset'
				}
			],

			DEFAULT: [
				{
					fieldLabel: Liferay.Language.get('button'),
					iconClass: 'form-builder-field-icon form-builder-field-icon-button',
					label: Liferay.Language.get('button'),
					type: 'button'
				},
				{
					fieldLabel: Liferay.Language.get('checkbox'),
					iconClass: 'icon-fb-boolean',
					label: Liferay.Language.get('checkbox'),
					type: 'checkbox'
				},
				{
					fieldLabel: Liferay.Language.get('fieldset'),
					iconClass: 'form-builder-field-icon form-builder-field-icon-fieldset',
					label: Liferay.Language.get('fieldset'),
					type: 'fieldset'
				},
				{
					fieldLabel: Liferay.Language.get('text-box'),
					iconClass: 'icon-fb-text',
					label: Liferay.Language.get('text-box'),
					type: 'text'
				},
				{
					fieldLabel: Liferay.Language.get('text-area'),
					iconClass: 'icon-fb-text-box',
					label: Liferay.Language.get('text-area'),
					type: 'textarea'
				},
				{
					fieldLabel: Liferay.Language.get('radio-buttons'),
					iconClass: 'icon-fb-radio',
					label: Liferay.Language.get('radio-buttons'),
					type: 'radio'
				},
				{
					fieldLabel: Liferay.Language.get('select-option'),
					iconClass: 'icon-fb-select',
					label: Liferay.Language.get('select-option'),
					type: 'select'
				}
			],

			WCM_STRUCTURE: [
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'icon-picture',
					label: Liferay.Language.get('image'),
					type: 'ddm-image'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.separator,
					iconClass: 'icon-fb-separator',
					label: Liferay.Language.get('separator'),
					type: 'ddm-separator'
				}
			]
		};

		AVAILABLE_FIELDS.WCM_STRUCTURE = AVAILABLE_FIELDS.WCM_STRUCTURE.concat(AVAILABLE_FIELDS.DDM_STRUCTURE);

		LiferayFormBuilder.AVAILABLE_FIELDS = AVAILABLE_FIELDS;

		Liferay.FormBuilder = LiferayFormBuilder;
	},
	'',
	{
		requires: ['arraysort', 'aui-form-builder', 'aui-form-validator', 'aui-map', 'aui-text-unicode', 'json', 'liferay-menu', 'liferay-translation-manager', 'liferay-util-window', 'text']
	}
);