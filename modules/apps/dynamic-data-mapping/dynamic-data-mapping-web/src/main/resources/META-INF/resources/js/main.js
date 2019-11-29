/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-portlet-dynamic-data-mapping',
	A => {
		var AArray = A.Array;
		var Lang = A.Lang;

		var BODY = A.getBody();

		var instanceOf = A.instanceOf;
		var isArray = Array.isArray;

		var isFormBuilderField = function(value) {
			return value instanceof A.FormBuilderField;
		};

		var isObject = Lang.isObject;
		var isString = Lang.isString;
		var isUndefined = Lang.isUndefined;

		var DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

		var ICON_ASTERISK_TPL =
			'<span>' + Liferay.Util.getLexiconIconTpl('asterisk') + '</span>';

		var ICON_QUESTION_TPL =
			'<span>' +
			Liferay.Util.getLexiconIconTpl('question-circle-full') +
			'</span>';

		var MAP_HIDDEN_FIELD_ATTRS = {
			DEFAULT: ['readOnly', 'width'],

			checkbox: ['readOnly'],

			separator: [
				'indexType',
				'localizable',
				'predefinedValue',
				'readOnly',
				'required'
			]
		};

		var REGEX_HYPHEN = /[-–—]/i;

		var SETTINGS_TAB_INDEX = 1;

		var STR_BLANK = '';

		var STR_SPACE = ' ';

		var STR_UNDERSCORE = '_';

		DEFAULTS_FORM_VALIDATOR.STRINGS.structureFieldName = Liferay.Language.get(
			'please-enter-only-alphanumeric-characters'
		);

		DEFAULTS_FORM_VALIDATOR.RULES.structureFieldName = function(value) {
			return /^[\w-]+$/.test(value);
		};

		// Updates icons to produce lexicon SVG markup instead of default glyphicon

		A.PropertyBuilderAvailableField.prototype.FIELD_ITEM_TEMPLATE = A.PropertyBuilderAvailableField.prototype.FIELD_ITEM_TEMPLATE.replace(
			/<\s*span[^>]*>(.*?)<\s*\/\s*span>/,
			Liferay.Util.getLexiconIconTpl('{iconClass}')
		);

		A.ToolbarRenderer.prototype.TEMPLATES.icon = Liferay.Util.getLexiconIconTpl(
			'{cssClass}'
		);

		var LiferayAvailableField = A.Component.create({
			ATTRS: {
				localizationMap: {
					validator: isObject,
					value: {}
				},
				name: {
					validator: isString
				}
			},

			EXTENDS: A.FormBuilderAvailableField,

			NAME: 'availableField'
		});

		var ReadOnlyFormBuilderSupport = function() {};

		ReadOnlyFormBuilderSupport.ATTRS = {
			readOnly: {
				value: false
			}
		};

		A.mix(ReadOnlyFormBuilderSupport.prototype, {
			_afterFieldRender(event) {
				var field = event.target;

				if (instanceOf(field, A.FormBuilderField)) {
					var readOnlyAttributes = AArray.map(
						field.getPropertyModel(),
						item => {
							return item.attributeName;
						}
					);

					field.set('readOnlyAttributes', readOnlyAttributes);
				}
			},

			_afterRenderReadOnlyFormBuilder() {
				var instance = this;

				instance.tabView.enableTab(1);
				instance.openEditProperties(instance.get('fields').item(0));
				instance.tabView
					.getTabs()
					.item(0)
					.hide();
			},

			_onMouseOverFieldReadOnlyFormBuilder(event) {
				var field = A.Widget.getByNode(event.currentTarget);

				field.controlsToolbar.hide();

				field
					.get('boundingBox')
					.removeClass('form-builder-field-hover');
			},

			initializer() {
				var instance = this;

				if (instance.get('readOnly')) {
					instance.set('allowRemoveRequiredFields', false);
					instance.set('enableEditing', false);
					instance.translationManager.hide();

					instance.after(
						'render',
						instance._afterRenderReadOnlyFormBuilder
					);

					instance.after('*:render', instance._afterFieldRender);

					instance.dropContainer.delegate(
						'mouseover',
						instance._onMouseOverFieldReadOnlyFormBuilder,
						'.form-builder-field'
					);
				}
			}
		});

		A.LiferayAvailableField = LiferayAvailableField;

		var LiferayFormBuilder = A.Component.create({
			ATTRS: {
				availableFields: {
					validator: isObject,
					valueFn() {
						return LiferayFormBuilder.AVAILABLE_FIELDS.DEFAULT;
					}
				},

				fieldNameEditionDisabled: {
					value: false
				},

				portletNamespace: {
					value: STR_BLANK
				},

				portletResourceNamespace: {
					value: STR_BLANK
				},

				propertyList: {
					value: {
						strings: {
							asc: Liferay.Language.get('ascending'),
							desc: Liferay.Language.get('descending'),
							propertyName: Liferay.Language.get('property-name'),
							reverseSortBy: Lang.sub(
								Liferay.Language.get('reverse-sort-by-x'),
								['{column}']
							),
							sortBy: Lang.sub(
								Liferay.Language.get('sort-by-x'),
								['{column}']
							),
							value: Liferay.Language.get('value')
						}
					}
				},

				strings: {
					value: {
						addNode: Liferay.Language.get('add-field'),
						button: Liferay.Language.get('button'),
						buttonType: Liferay.Language.get('button-type'),
						cancel: Liferay.Language.get('cancel'),
						deleteFieldsMessage: Liferay.Language.get(
							'are-you-sure-you-want-to-delete-the-selected-entries'
						),
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
						predefinedValue: Liferay.Language.get(
							'predefined-value'
						),
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
					setter(val) {
						var config = A.merge(
							{
								fieldStrings: {
									name: {
										required: Liferay.Language.get(
											'this-field-is-required'
										)
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

			AUGMENTS: [ReadOnlyFormBuilderSupport],

			EXTENDS: A.FormBuilder,

			LOCALIZABLE_FIELD_ATTRS: [
				'label',
				'options',
				'predefinedValue',
				'style',
				'tip'
			],

			NAME: 'liferayformbuilder',

			UNIQUE_FIELD_NAMES_MAP: new A.Map(),

			UNLOCALIZABLE_FIELD_ATTRS: [
				'dataType',
				'fieldNamespace',
				'indexType',
				'localizable',
				'multiple',
				'name',
				'readOnly',
				'repeatable',
				'required',
				'showLabel',
				'type'
			],

			prototype: {
				_afterEditingLocaleChange(event) {
					var instance = this;

					instance._toggleInputDirection(event.newVal);
				},

				_afterFieldsChange(event) {
					var instance = this;

					var tabs = instance.tabView.getTabs();

					var activeTabIndex = tabs.indexOf(
						instance.tabView.getActiveTab()
					);

					if (activeTabIndex === SETTINGS_TAB_INDEX) {
						instance.editField(event.newVal.item(0));
					}
				},

				_beforeGetEditor(record, column) {
					if (column.key === 'name') {
						return;
					}

					var instance = this;

					var columnEditor = column.editor;

					var recordEditor = record.get('editor');

					var editor = recordEditor || columnEditor;

					if (instanceOf(editor, A.BaseOptionsCellEditor)) {
						if (editor.get('rendered')) {
							instance._toggleOptionsEditorInputs(editor);
						}
						else {
							editor.after('render', () => {
								instance._toggleOptionsEditorInputs(editor);
							});
						}
					}

					editor.after('render', () => {
						editor.set('visible', true);

						var boundingBox = editor.get('boundingBox');

						if (boundingBox) {
							boundingBox.show();
						}
					});
				},

				_deserializeField(fieldJSON, availableLanguageIds) {
					var instance = this;

					var fields = fieldJSON.fields;

					if (isArray(fields)) {
						fields.forEach(item => {
							instance._deserializeField(
								item,
								availableLanguageIds
							);
						});
					}

					instance._deserializeFieldLocalizationMap(
						fieldJSON,
						availableLanguageIds
					);
					instance._deserializeFieldLocalizableAttributes(fieldJSON);
				},

				_deserializeFieldLocalizableAttributes(fieldJSON) {
					var instance = this;

					var defaultLocale = instance.translationManager.get(
						'defaultLocale'
					);
					var editingLocale = instance.translationManager.get(
						'editingLocale'
					);

					LiferayFormBuilder.LOCALIZABLE_FIELD_ATTRS.forEach(item => {
						var localizedValue = fieldJSON[item];

						if (item !== 'options' && localizedValue) {
							fieldJSON[item] =
								localizedValue[editingLocale] ||
								localizedValue[defaultLocale];
						}
					});
				},

				_deserializeFieldLocalizationMap(
					fieldJSON,
					availableLanguageIds
				) {
					var instance = this;

					availableLanguageIds.forEach(languageId => {
						fieldJSON.localizationMap =
							fieldJSON.localizationMap || {};
						fieldJSON.localizationMap[languageId] = {};

						LiferayFormBuilder.LOCALIZABLE_FIELD_ATTRS.forEach(
							attribute => {
								var attributeMap = fieldJSON[attribute];

								if (attributeMap && attributeMap[languageId]) {
									fieldJSON.localizationMap[languageId][
										attribute
									] = attributeMap[languageId];
								}
							}
						);
					});

					if (fieldJSON.options) {
						instance._deserializeFieldOptionsLocalizationMap(
							fieldJSON,
							availableLanguageIds
						);
					}
				},

				_deserializeFieldOptionsLocalizationMap(
					fieldJSON,
					availableLanguageIds
				) {
					var instance = this;

					var labels;

					var defaultLocale = instance.translationManager.get(
						'defaultLocale'
					);
					var editingLocale = instance.translationManager.get(
						'editingLocale'
					);

					fieldJSON.options.forEach(item => {
						labels = item.label;

						item.label =
							labels[editingLocale] || labels[defaultLocale];

						item.localizationMap = {};

						availableLanguageIds.forEach(languageId => {
							item.localizationMap[languageId] = {
								label: labels[languageId]
							};
						});
					});
				},

				_getGeneratedFieldName(label) {
					var normalizedLabel = LiferayFormBuilder.Util.normalizeKey(
						label
					);

					var generatedName = normalizedLabel;

					if (
						LiferayFormBuilder.Util.validateFieldName(generatedName)
					) {
						var counter = 1;

						while (
							LiferayFormBuilder.UNIQUE_FIELD_NAMES_MAP.has(
								generatedName
							)
						) {
							generatedName = normalizedLabel + counter++;
						}
					}

					return generatedName;
				},

				_getSerializedFields() {
					var instance = this;

					var fields = [];

					instance.get('fields').each(field => {
						fields.push(field.serialize());
					});

					return fields;
				},

				_onDataTableRender(event) {
					var instance = this;

					A.on(
						instance._beforeGetEditor,
						event.target,
						'getEditor',
						instance
					);
				},

				_onDefaultLocaleChange(event) {
					var instance = this;

					var fields = instance.get('fields');

					var newVal = event.newVal;

					var translationManager = instance.translationManager;

					var availableLanguageIds = translationManager.get(
						'availableLocales'
					);

					if (availableLanguageIds.indexOf(newVal) < 0) {
						var config = {
							fields,
							newVal,
							prevVal: event.prevVal
						};

						translationManager.addAvailableLocale(newVal);

						instance._updateLocalizationMaps(config);
					}
				},

				_onMouseOutField(event) {
					var instance = this;

					var field = A.Widget.getByNode(event.currentTarget);

					instance._setInvalidDDHandles(field, 'remove');

					LiferayFormBuilder.superclass._onMouseOutField.apply(
						instance,
						arguments
					);
				},

				_onMouseOverField(event) {
					var instance = this;

					var field = A.Widget.getByNode(event.currentTarget);

					instance._setInvalidDDHandles(field, 'add');

					LiferayFormBuilder.superclass._onMouseOverField.apply(
						instance,
						arguments
					);
				},

				_onPropertyModelChange(event) {
					var instance = this;

					var fieldNameEditionDisabled = instance.get(
						'fieldNameEditionDisabled'
					);

					var changed = event.changed;

					var attributeName = event.target.get('attributeName');

					var editingField = instance.editingField;

					var readOnlyAttributes = editingField.get(
						'readOnlyAttributes'
					);

					if (
						Object.prototype.hasOwnProperty.call(
							changed,
							'value'
						) &&
						readOnlyAttributes.indexOf('name') === -1
					) {
						if (attributeName === 'name') {
							editingField.set(
								'autoGeneratedName',
								event.autoGeneratedName === true
							);
						}
						else if (
							attributeName === 'label' &&
							editingField.get('autoGeneratedName') &&
							!fieldNameEditionDisabled
						) {
							var translationManager =
								instance.translationManager;

							if (
								translationManager.get('editingLocale') ===
								translationManager.get('defaultLocale')
							) {
								var generatedName = instance._getGeneratedFieldName(
									changed.value.newVal
								);

								if (
									LiferayFormBuilder.Util.validateFieldName(
										generatedName
									)
								) {
									var nameModel = instance.propertyList
										.get('data')
										.filter(item => {
											return (
												item.get('attributeName') ===
												'name'
											);
										});

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
						else if (attributeName === 'required') {
							var state = changed.value.newVal === 'true';
							var requiredNode = editingField
								._getFieldNode()
								.one('.lexicon-icon-asterisk');

							if (requiredNode) {
								requiredNode.toggle(state);
							}
						}
					}
				},

				_renderSettings() {
					var instance = this;

					instance._renderPropertyList();

					// Dynamically removes unnecessary icons from editor toolbar buttons

					var defaultGetEditorFn = instance.propertyList.getEditor;

					instance.propertyList.getEditor = function() {
						var editor = defaultGetEditorFn.apply(this, arguments);

						if (editor) {
							var defaultSetToolbarFn = A.bind(
								editor._setToolbar,
								editor
							);

							editor._setToolbar = function(val) {
								var toolbar = defaultSetToolbarFn(val);

								if (toolbar && toolbar.children) {
									toolbar.children = toolbar.children.map(
										children => {
											children = children.map(item => {
												delete item.icon;

												return item;
											});

											return children;
										}
									);
								}

								return toolbar;
							};
						}

						return editor;
					};
				},

				_setAvailableFields(val) {
					var fields = val.map(item => {
						return instanceOf(item, A.PropertyBuilderAvailableField)
							? item
							: new A.LiferayAvailableField(item);
					});

					fields.sort((a, b) => {
						return A.ArraySort.compare(
							a.get('label'),
							b.get('label')
						);
					});

					return fields;
				},

				_setFields() {
					var instance = this;

					LiferayFormBuilder.UNIQUE_FIELD_NAMES_MAP.clear();

					return LiferayFormBuilder.superclass._setFields.apply(
						instance,
						arguments
					);
				},

				_setFieldsSortableListConfig() {
					var instance = this;

					var config = LiferayFormBuilder.superclass._setFieldsSortableListConfig.apply(
						instance,
						arguments
					);

					config.dd.plugins = [
						{
							cfg: {
								constrain: '#main-content'
							},
							fn: A.Plugin.DDConstrained
						},
						{
							cfg: {
								horizontal: false,
								node: '#main-content'
							},
							fn: A.Plugin.DDNodeScroll
						}
					];

					return config;
				},

				_setInvalidDDHandles(field, type) {
					var instance = this;

					var methodName = type + 'Invalid';

					instance.eachParentField(field, parent => {
						var parentBB = parent.get('boundingBox');

						parentBB.dd[methodName]('#' + parentBB.attr('id'));
					});
				},

				_toggleInputDirection(locale) {
					var rtl = Liferay.Language.direction[locale] === 'rtl';

					BODY.toggleClass('form-builder-ltr-inputs', !rtl);
					BODY.toggleClass('form-builder-rtl-inputs', rtl);
				},

				_toggleOptionsEditorInputs(editor) {
					var instance = this;

					var boundingBox = editor.get('boundingBox');

					if (boundingBox.hasClass('radiocelleditor')) {
						var defaultLocale = instance.translationManager.get(
							'defaultLocale'
						);
						var editingLocale = instance.translationManager.get(
							'editingLocale'
						);

						var inputs = boundingBox.all(
							'.celleditor-edit-input-value'
						);

						Liferay.Util.toggleDisabled(
							inputs,
							defaultLocale !== editingLocale
						);
					}
				},

				_updateLocalizationMaps(config) {
					var instance = this;

					var fields = config.fields;
					var newVal = config.newVal;
					var prevVal = config.prevVal;

					fields._items.forEach(field => {
						var childFields = field.get('fields');
						var localizationMap = field.get('localizationMap');

						var config = {
							fields: childFields,
							newVal,
							prevVal
						};

						localizationMap[newVal] = localizationMap[prevVal];

						instance._updateLocalizationMaps(config);
					});
				},

				bindUI() {
					var instance = this;

					LiferayFormBuilder.superclass.bindUI.apply(
						instance,
						arguments
					);

					instance.translationManager.after(
						'defaultLocaleChange',
						instance._onDefaultLocaleChange,
						instance
					);
					instance.translationManager.after(
						'editingLocaleChange',
						instance._afterEditingLocaleChange,
						instance
					);

					instance.on(
						'datatable:render',
						instance._onDataTableRender
					);
					instance.on(
						'drag:drag',
						A.DD.DDM.syncActiveShims,
						A.DD.DDM,
						true
					);
					instance.on(
						'model:change',
						instance._onPropertyModelChange
					);
				},

				createField() {
					var instance = this;

					var field = LiferayFormBuilder.superclass.createField.apply(
						instance,
						arguments
					);

					if (field.name === 'ddm-image' && !field.get('required')) {
						var requiredNode = field
							._getFieldNode()
							.one('.lexicon-icon-asterisk');

						if (requiredNode) {
							requiredNode.toggle(false);
						}
					}

					// Dynamically updates field toolbar items to produce lexicon svg markup instead of default glyphicon

					field.set(
						'requiredFlagNode',
						A.Node.create(ICON_ASTERISK_TPL)
					);

					field.set('tipFlagNode', A.Node.create(ICON_QUESTION_TPL));

					var defaultGetToolbarItemsFn = A.bind(
						field._getToolbarItems,
						field
					);

					field._getToolbarItems = function() {
						var toolbarItems = defaultGetToolbarItemsFn();

						return (
							toolbarItems &&
							toolbarItems.map(toolbarItem => {
								return toolbarItem.map(item => {
									if (item.icon) {
										item.icon = item.icon
											.replace('glyphicon glyphicon-', '')
											.replace('wrench', 'cog');
									}

									return item;
								});
							})
						);
					};

					field.set('strings', instance.get('strings'));

					var fieldHiddenAttributeMap = {
						checkbox: instance.MAP_HIDDEN_FIELD_ATTRS.checkbox,
						'ddm-separator':
							instance.MAP_HIDDEN_FIELD_ATTRS.separator,
						default: instance.MAP_HIDDEN_FIELD_ATTRS.DEFAULT
					};

					var hiddenAtributes =
						fieldHiddenAttributeMap[field.get('type')];

					if (!hiddenAtributes) {
						hiddenAtributes = fieldHiddenAttributeMap.default;
					}

					field.set('hiddenAttributes', hiddenAtributes);

					return field;
				},

				deserializeDefinitionFields(content) {
					var instance = this;

					var availableLanguageIds = content.availableLanguageIds;

					var fields = content.fields;

					fields.forEach(fieldJSON => {
						instance._deserializeField(
							fieldJSON,
							availableLanguageIds
						);
					});

					return fields;
				},

				eachParentField(field, fn) {
					var instance = this;

					var parent = field.get('parent');

					while (isFormBuilderField(parent)) {
						fn.call(instance, parent);

						parent = parent.get('parent');
					}
				},

				getContent() {
					var instance = this;

					var definition = {};

					var translationManager = instance.translationManager;

					definition.availableLanguageIds = translationManager.get(
						'availableLocales'
					);
					definition.defaultLanguageId = translationManager.get(
						'defaultLocale'
					);

					definition.fields = instance._getSerializedFields();

					return JSON.stringify(definition, null, 4);
				},

				getContentValue() {
					var instance = this;

					return window[
						instance.get('portletResourceNamespace') +
							'getContentValue'
					]();
				},

				initializer() {
					var instance = this;

					instance.MAP_HIDDEN_FIELD_ATTRS = A.clone(
						MAP_HIDDEN_FIELD_ATTRS
					);

					var translationManager = (instance.translationManager = new Liferay.TranslationManager(
						instance.get('translationManager')
					));

					instance.after('render', () => {
						translationManager.render();
					});

					instance.after('fieldsChange', instance._afterFieldsChange);

					if (themeDisplay.isStatePopUp()) {
						instance.addTarget(Liferay.Util.getOpener().Liferay);
					}

					instance._toggleInputDirection(
						translationManager.get('defaultLocale')
					);
				},

				plotField(field) {
					var instance = this;

					LiferayFormBuilder.UNIQUE_FIELD_NAMES_MAP.put(
						field.get('name'),
						field
					);

					return LiferayFormBuilder.superclass.plotField.apply(
						instance,
						arguments
					);
				}
			}
		});

		LiferayFormBuilder.Util = {
			getFileEntry(fileJSON, callback) {
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

			getFileEntryURL(fileEntry) {
				var buffer = [
					themeDisplay.getPathContext(),
					'documents',
					fileEntry.groupId,
					fileEntry.folderId,
					encodeURIComponent(fileEntry.title)
				];

				return buffer.join('/');
			},

			normalizeKey(key) {
				key = key.trim();

				for (var i = 0; i < key.length; i++) {
					var item = key[i];

					if (
						!A.Text.Unicode.test(item, 'L') &&
						!A.Text.Unicode.test(item, 'N') &&
						!A.Text.Unicode.test(item, 'Pd') &&
						item != STR_UNDERSCORE
					) {
						key = key.replace(item, STR_SPACE);
					}
				}

				key = Lang.String.camelize(key, STR_SPACE);

				return key.replace(/\s+/gi, '');
			},

			normalizeValue(value) {
				if (isUndefined(value)) {
					value = STR_BLANK;
				}

				return value;
			},

			parseJSON(value) {
				var data = {};

				try {
					data = JSON.parse(value);
				}
				catch (e) {}

				return data;
			},

			validateFieldName(fieldName) {
				var valid = true;

				if (REGEX_HYPHEN.test(fieldName)) {
					valid = false;

					return valid;
				}

				for (var i = 0; i < fieldName.length; i++) {
					var item = fieldName[i];

					if (
						!A.Text.Unicode.test(item, 'L') &&
						!A.Text.Unicode.test(item, 'N') &&
						!A.Text.Unicode.test(item, 'Pd') &&
						item != STR_UNDERSCORE
					) {
						valid = false;

						break;
					}
				}

				return valid;
			}
		};

		LiferayFormBuilder.DEFAULT_ICON_CLASS = 'text';

		var AVAILABLE_FIELDS = {
			DDM_STRUCTURE: [
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.checkbox,
					iconClass: 'check-square',
					label: Liferay.Language.get('boolean'),
					type: 'checkbox'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'adjust',
					label: Liferay.Language.get('color'),
					type: 'ddm-color'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'calendar',
					label: Liferay.Language.get('date'),
					type: 'ddm-date'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'decimal',
					label: Liferay.Language.get('decimal'),
					type: 'ddm-decimal'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'document-text',
					label: Liferay.Language.get('documents-and-media'),
					type: 'ddm-documentlibrary'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'text',
					label: Liferay.Language.get('journal-article'),
					type: 'ddm-journal-article'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'code',
					label: Liferay.Language.get('html'),
					type: 'ddm-text-html'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'globe',
					label: Liferay.Language.get('geolocation'),
					type: 'ddm-geolocation'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'integer',
					label: Liferay.Language.get('integer'),
					type: 'ddm-integer'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'link',
					label: Liferay.Language.get('link-to-page'),
					type: 'ddm-link-to-page'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'number',
					label: Liferay.Language.get('number'),
					type: 'ddm-number'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'radio-button',
					label: Liferay.Language.get('radio'),
					type: 'radio'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'select',
					label: Liferay.Language.get('select'),
					type: 'select'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'text',
					label: Liferay.Language.get('text'),
					type: 'text'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'textbox',
					label: Liferay.Language.get('text-box'),
					type: 'textarea'
				}
			],

			DDM_TEMPLATE: [
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'paragraph',
					label: Liferay.Language.get('paragraph'),
					type: 'ddm-paragraph'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'separator',
					label: Liferay.Language.get('separator'),
					type: 'ddm-separator'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'blogs',
					label: Liferay.Language.get('fieldset'),
					type: 'fieldset'
				}
			],

			DEFAULT: [
				{
					fieldLabel: Liferay.Language.get('button'),
					iconClass: 'square-hole',
					label: Liferay.Language.get('button'),
					type: 'button'
				},
				{
					fieldLabel: Liferay.Language.get('checkbox'),
					iconClass: 'check-square',
					label: Liferay.Language.get('checkbox'),
					type: 'checkbox'
				},
				{
					fieldLabel: Liferay.Language.get('fieldset'),
					iconClass: 'cards',
					label: Liferay.Language.get('fieldset'),
					type: 'fieldset'
				},
				{
					fieldLabel: Liferay.Language.get('text-box'),
					iconClass: 'text',
					label: Liferay.Language.get('text-box'),
					type: 'text'
				},
				{
					fieldLabel: Liferay.Language.get('text-area'),
					iconClass: 'textbox',
					label: Liferay.Language.get('text-area'),
					type: 'textarea'
				},
				{
					fieldLabel: Liferay.Language.get('radio-buttons'),
					iconClass: 'radio',
					label: Liferay.Language.get('radio-buttons'),
					type: 'radio'
				},
				{
					fieldLabel: Liferay.Language.get('select-option'),
					iconClass: 'select',
					label: Liferay.Language.get('select-option'),
					type: 'select'
				}
			],

			WCM_STRUCTURE: [
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'picture',
					label: Liferay.Language.get('image'),
					type: 'ddm-image'
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.separator,
					iconClass: 'separator',
					label: Liferay.Language.get('separator'),
					type: 'ddm-separator'
				}
			]
		};

		AVAILABLE_FIELDS.WCM_STRUCTURE = AVAILABLE_FIELDS.WCM_STRUCTURE.concat(
			AVAILABLE_FIELDS.DDM_STRUCTURE
		);

		LiferayFormBuilder.AVAILABLE_FIELDS = AVAILABLE_FIELDS;

		Liferay.FormBuilder = LiferayFormBuilder;
	},
	'',
	{
		requires: [
			'arraysort',
			'aui-form-builder-deprecated',
			'aui-form-validator',
			'aui-map',
			'aui-text-unicode',
			'json',
			'liferay-menu',
			'liferay-translation-manager',
			'liferay-util-window',
			'text'
		]
	}
);
