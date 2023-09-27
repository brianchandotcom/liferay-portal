/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-portlet-dynamic-data-mapping',
	(A) => {
		const AArray = A.Array;
		const Lang = A.Lang;

		const BODY = document.body;

		const instanceOf = A.instanceOf;
		const isArray = Array.isArray;

		const isFormBuilderField = function (value) {
			return value instanceof A.FormBuilderField;
		};

		const isObject = Lang.isObject;
		const isString = Lang.isString;
		const isUndefined = Lang.isUndefined;

		const DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

		const ICON_ASTERISK_TPL =
			'<label>' +
			'<span class="reference-mark">' +
			Liferay.Util.getLexiconIconTpl('asterisk') +
			'</span>' +
			'</label>';

		const ICON_QUESTION_TPL =
			'<span>' +
			Liferay.Util.getLexiconIconTpl('question-circle-full') +
			'</span>';

		const MAP_HIDDEN_FIELD_ATTRS = {
			DEFAULT: ['readOnly', 'width'],

			checkbox: ['readOnly'],

			separator: [
				'indexType',
				'localizable',
				'predefinedValue',
				'readOnly',
				'required',
			],
		};

		const REGEX_HYPHEN = /[-–—]/i;

		const SETTINGS_TAB_INDEX = 1;

		const STR_BLANK = '';

		const STR_SPACE = ' ';

		const STR_UNDERSCORE = '_';

		DEFAULTS_FORM_VALIDATOR.STRINGS.structureFieldName = Liferay.Language.get(
			'please-enter-only-alphanumeric-characters'
		);

		DEFAULTS_FORM_VALIDATOR.RULES.structureFieldName = function (value) {
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

		const LiferayAvailableField = A.Component.create({
			ATTRS: {
				localizationMap: {
					validator: isObject,
					value: {},
				},
				name: {
					validator: isString,
				},
			},

			EXTENDS: A.FormBuilderAvailableField,

			NAME: 'availableField',
		});

		const ReadOnlyFormBuilderSupport = function () {};

		ReadOnlyFormBuilderSupport.ATTRS = {
			readOnly: {
				value: false,
			},
		};

		A.mix(ReadOnlyFormBuilderSupport.prototype, {
			_afterFieldRender(event) {
				const field = event.target;

				if (instanceOf(field, A.FormBuilderField)) {
					const readOnlyAttributes = AArray.map(
						field.getPropertyModel(),
						(item) => {
							return item.attributeName;
						}
					);

					field.set('readOnlyAttributes', readOnlyAttributes);
				}
			},

			_afterRenderReadOnlyFormBuilder() {
				const instance = this;

				instance.tabView.enableTab(1);
				instance.openEditProperties(instance.get('fields').item(0));
				instance.tabView.getTabs().item(0).hide();
			},

			_onMouseOverFieldReadOnlyFormBuilder(event) {
				const field = A.Widget.getByNode(event.currentTarget);

				field.controlsToolbar.hide();

				field
					.get('boundingBox')
					.removeClass('form-builder-field-hover');
			},

			initializer() {
				const instance = this;

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
			},
		});

		A.LiferayAvailableField = LiferayAvailableField;

		const LiferayFormBuilder = A.Component.create({
			ATTRS: {
				availableFields: {
					validator: isObject,
					valueFn() {
						return LiferayFormBuilder.AVAILABLE_FIELDS.DEFAULT;
					},
				},

				fieldNameEditionDisabled: {
					value: false,
				},

				portletNamespace: {
					value: STR_BLANK,
				},

				portletResourceNamespace: {
					value: STR_BLANK,
				},

				propertyList: {
					value: {
						strings: {
							asc: Liferay.Language.get('ascending'),
							// eslint-disable-next-line @liferay/no-abbreviations
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
							value: Liferay.Language.get('value'),
						},
					},
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
						requiredDescription: Liferay.Language.get(
							'required-description'
						),
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
						yes: Liferay.Language.get('yes'),
					},
				},

				translationManager: {
					validator: isObject,
					value: {},
				},

				validator: {
					setter(val) {
						const config = {
							fieldStrings: {
								name: {
									required: Liferay.Language.get(
										'this-field-is-required'
									),
								},
							},
							rules: {
								name: {
									required: true,
									structureFieldName: true,
								},
							},
							...val,
						};

						return config;
					},
					value: {},
				},
			},

			AUGMENTS: [ReadOnlyFormBuilderSupport],

			EXTENDS: A.FormBuilder,

			LOCALIZABLE_FIELD_ATTRS: [
				'label',
				'options',
				'predefinedValue',
				'style',
				'tip',
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
				'requiredDescription',
				'showLabel',
				'type',
			],

			prototype: {
				_afterEditingLocaleChange(event) {
					const instance = this;

					instance._toggleInputDirection(event.newVal);
				},

				_afterFieldsChange(event) {
					const instance = this;

					const tabs = instance.tabView.getTabs();

					const activeTabIndex = tabs.indexOf(
						instance.tabView.getActiveTab()
					);

					if (activeTabIndex === SETTINGS_TAB_INDEX) {
						instance.editField(event.newVal.item(0));
					}

					this._handleAlertMessages(instance.get('fields'));
				},

				_beforeGetEditor(record, column) {
					if (column.key === 'name') {
						return;
					}

					const instance = this;

					const columnEditor = column.editor;

					const recordEditor = record.get('editor');

					const editor = recordEditor || columnEditor;

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

						const boundingBox = editor.get('boundingBox');

						if (boundingBox) {
							boundingBox.show();
						}
					});
				},

				_deserializeField(fieldJSON, availableLanguageIds) {
					const instance = this;

					const fields = fieldJSON.fields;

					if (isArray(fields)) {
						fields.forEach((item) => {
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
					const instance = this;

					const defaultLocale = instance.translationManager.get(
						'defaultLocale'
					);
					const editingLocale = instance.translationManager.get(
						'editingLocale'
					);

					LiferayFormBuilder.LOCALIZABLE_FIELD_ATTRS.forEach(
						(item) => {
							const localizedValue = fieldJSON[item];

							if (item !== 'options' && localizedValue) {
								fieldJSON[item] =
									localizedValue[editingLocale] ||
									localizedValue[defaultLocale];
							}
						}
					);
				},

				_deserializeFieldLocalizationMap(
					fieldJSON,
					availableLanguageIds
				) {
					const instance = this;

					availableLanguageIds.forEach((languageId) => {
						fieldJSON.localizationMap =
							fieldJSON.localizationMap || {};
						fieldJSON.localizationMap[languageId] = {};

						LiferayFormBuilder.LOCALIZABLE_FIELD_ATTRS.forEach(
							(attribute) => {
								const attributeMap = fieldJSON[attribute];

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
					const instance = this;

					let labels;

					const defaultLocale = instance.translationManager.get(
						'defaultLocale'
					);
					const editingLocale = instance.translationManager.get(
						'editingLocale'
					);

					fieldJSON.options.forEach((item) => {
						labels = item.label;

						item.label =
							labels[editingLocale] || labels[defaultLocale];

						item.localizationMap = {};

						availableLanguageIds.forEach((languageId) => {
							item.localizationMap[languageId] = {
								label: labels[languageId],
							};
						});
					});
				},

				_getGeneratedFieldName(label) {
					const normalizedLabel = LiferayFormBuilder.Util.normalizeKey(
						label
					);

					let generatedName = normalizedLabel;

					if (
						LiferayFormBuilder.Util.validateFieldName(generatedName)
					) {
						let counter = 1;

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
					const instance = this;

					const fields = [];

					instance.get('fields').each((field) => {
						fields.push(field.serialize());
					});

					return fields;
				},

				_handleAlertMessages(fields) {
					const hasDocumentLibrary = fields.some(
						(field) => field.name === 'ddm-documentlibrary'
					);
					const documentsAndMediaField = document.querySelector(
						'.ddm-documents-and-media-field'
					);
					if (documentsAndMediaField !== null) {
						const isHidden = documentsAndMediaField.classList.contains(
							'hide'
						);
						if (hasDocumentLibrary && isHidden) {
							documentsAndMediaField.classList.remove('hide');
						}
						else if (!hasDocumentLibrary) {
							documentsAndMediaField.classList.add('hide');
						}
					}
				},

				_onDataTableRender(event) {
					const instance = this;

					A.on(
						instance._beforeGetEditor,
						event.target,
						'getEditor',
						instance
					);
				},

				_onDefaultLocaleChange(event) {
					const instance = this;

					const fields = instance.get('fields');

					const newVal = event.newVal;

					const translationManager = instance.translationManager;

					const availableLanguageIds = translationManager.get(
						'availableLocales'
					);

					if (availableLanguageIds.indexOf(newVal) < 0) {
						const config = {
							fields,
							newVal,
							prevVal: event.prevVal,
						};

						translationManager.addAvailableLocale(newVal);

						instance._updateLocalizationMaps(config);
					}
				},

				_onMouseOutField(event) {
					const instance = this;

					const field = A.Widget.getByNode(event.currentTarget);

					instance._setInvalidDDHandles(field, 'remove');

					LiferayFormBuilder.superclass._onMouseOutField.apply(
						instance,
						arguments
					);
				},

				_onMouseOverField(event) {
					const instance = this;

					const field = A.Widget.getByNode(event.currentTarget);

					instance._setInvalidDDHandles(field, 'add');

					LiferayFormBuilder.superclass._onMouseOverField.apply(
						instance,
						arguments
					);
				},

				_onPropertyModelChange(event) {
					const instance = this;

					const fieldNameEditionDisabled = instance.get(
						'fieldNameEditionDisabled'
					);

					const changed = event.changed;

					const attributeName = event.target.get('attributeName');

					const editingField = instance.editingField;

					const readOnlyAttributes = editingField.get(
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
							const translationManager =
								instance.translationManager;

							if (
								translationManager.get('editingLocale') ===
								translationManager.get('defaultLocale')
							) {
								const generatedName = instance._getGeneratedFieldName(
									changed.value.newVal
								);

								if (
									LiferayFormBuilder.Util.validateFieldName(
										generatedName
									)
								) {
									const nameModel = instance.propertyList
										.get('data')
										.filter((item) => {
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
												autoGeneratedName: true,
											}
										);
									}
								}
							}
						}
						else if (editingField.get('type') === 'ddm-image') {
							if (attributeName === 'required') {
								if (editingField.get('requiredDescription')) {
									instance._toggleImageDescriptionAsterisk(
										editingField,
										changed.value.newVal === 'true'
									);
								}

								instance._toggleRequiredDescriptionPropertyModel(
									editingField,
									changed.value.newVal === 'true'
								);
							}
							else if (
								attributeName === 'requiredDescription' &&
								editingField.get('required')
							) {
								instance._toggleImageDescriptionAsterisk(
									editingField,
									changed.value.newVal === 'true'
								);
							}
						}
						else if (
							attributeName === 'multiple' &&
							changed.value.newVal === 'false'
						) {
							editingField.set('multiple', changed.value.newVal);
							editingField.set('predefinedValue', ['']);

							instance.editField(editingField);
						}
					}
				},

				_renderSettings() {
					const instance = this;

					instance._renderPropertyList();

					// Dynamically removes unnecessary icons from editor toolbar buttons

					const defaultGetEditorFn = instance.propertyList.getEditor;

					instance.propertyList.getEditor = function () {
						const editor = defaultGetEditorFn.apply(
							this,
							arguments
						);

						if (editor) {
							const defaultSetToolbarFn = A.bind(
								editor._setToolbar,
								editor
							);

							editor._setToolbar = function (val) {
								const toolbar = defaultSetToolbarFn(val);

								if (toolbar && toolbar.children) {
									toolbar.children = toolbar.children.map(
										(children) => {
											children = children.map((item) => {
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
					const fields = val.map((item) => {
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
					const instance = this;

					LiferayFormBuilder.UNIQUE_FIELD_NAMES_MAP.clear();

					return LiferayFormBuilder.superclass._setFields.apply(
						instance,
						arguments
					);
				},

				_setFieldsSortableListConfig() {
					const instance = this;

					const config = LiferayFormBuilder.superclass._setFieldsSortableListConfig.apply(
						instance,
						arguments
					);

					config.dd.plugins = [
						{
							cfg: {
								constrain: '#main-content',
							},
							fn: A.Plugin.DDConstrained,
						},
						{
							cfg: {
								horizontal: false,
								node: '#main-content',
							},
							fn: A.Plugin.DDNodeScroll,
						},
					];

					return config;
				},

				_setInvalidDDHandles(field, type) {
					const instance = this;

					const methodName = type + 'Invalid';

					instance.eachParentField(field, (parent) => {
						const parentBB = parent.get('boundingBox');

						parentBB.dd[methodName]('#' + parentBB.attr('id'));
					});
				},

				_toggleImageDescriptionAsterisk(field, state) {
					const requiredNode = field
						._getFieldNode()
						.one('.lexicon-icon-asterisk');

					if (requiredNode) {
						requiredNode.toggle(state);
					}
				},

				_toggleInputDirection(locale) {
					const rtl = Liferay.Language.direction[locale] === 'rtl';

					BODY.classList.toggle('form-builder-ltr-inputs', !rtl);
					BODY.classList.toggle('form-builder-rtl-inputs', rtl);
				},

				_toggleOptionsEditorInputs(editor) {
					const instance = this;

					const boundingBox = editor.get('boundingBox');

					if (boundingBox.hasClass('radiocelleditor')) {
						const defaultLocale = instance.translationManager.get(
							'defaultLocale'
						);
						const editingLocale = instance.translationManager.get(
							'editingLocale'
						);

						const inputs = boundingBox.all(
							'.celleditor-edit-input-value'
						);

						Liferay.Util.toggleDisabled(
							inputs,
							defaultLocale !== editingLocale
						);
					}
				},

				_toggleRequiredDescriptionPropertyModel(field, state) {
					const instance = this;

					const modelList = instance.propertyList.get('data');

					if (state) {
						modelList.add(
							{
								...field.getRequiredDescriptionPropertyModel(),
								value: field.get('requiredDescription'),
							},
							{
								index:
									modelList.indexOf(
										modelList.getById('required')
									) + 1,
							}
						);
					}
					else {
						modelList.remove(
							modelList.getById('requiredDescription')
						);
					}
				},

				_updateLocalizationMaps(config) {
					const instance = this;

					const fields = config.fields;
					const newVal = config.newVal;
					const prevVal = config.prevVal;

					fields._items.forEach((field) => {
						const childFields = field.get('fields');
						const localizationMap = field.get('localizationMap');

						const config = {
							fields: childFields,
							newVal,
							prevVal,
						};

						localizationMap[newVal] = localizationMap[prevVal];

						instance._updateLocalizationMaps(config);
					});
				},

				bindUI() {
					const instance = this;

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
					const instance = this;

					const field = LiferayFormBuilder.superclass.createField.apply(
						instance,
						arguments
					);

					if (field.name === 'ddm-image') {
						if (!field.get('required')) {
							instance._toggleImageDescriptionAsterisk(
								field,
								false
							);

							instance.MAP_HIDDEN_FIELD_ATTRS.DEFAULT.push(
								'requiredDescription'
							);
						}
						else if (field.get('requiredDescription') === false) {
							instance._toggleImageDescriptionAsterisk(
								field,
								false
							);
						}
					}

					// Dynamically updates field toolbar items to produce lexicon svg markup instead of default glyphicon

					field.set(
						'requiredFlagNode',
						A.Node.create(ICON_ASTERISK_TPL)
					);

					field.set('tipFlagNode', A.Node.create(ICON_QUESTION_TPL));

					const defaultGetToolbarItemsFn = A.bind(
						field._getToolbarItems,
						field
					);

					field._getToolbarItems = function () {
						const toolbarItems = defaultGetToolbarItemsFn();

						return (
							toolbarItems &&
							toolbarItems.map((toolbarItem) => {
								return toolbarItem.map((item) => {
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

					const fieldHiddenAttributeMap = {
						'checkbox': instance.MAP_HIDDEN_FIELD_ATTRS.checkbox,
						'ddm-separator':
							instance.MAP_HIDDEN_FIELD_ATTRS.separator,
						'default': instance.MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					};

					let hiddenAtributes =
						fieldHiddenAttributeMap[field.get('type')];

					if (!hiddenAtributes) {
						hiddenAtributes = fieldHiddenAttributeMap.default;
					}

					field.set('hiddenAttributes', hiddenAtributes);

					return field;
				},

				deserializeDefinitionFields(content) {
					const instance = this;

					const availableLanguageIds = content.availableLanguageIds;

					const fields = content.fields;

					fields.forEach((fieldJSON) => {
						instance._deserializeField(
							fieldJSON,
							availableLanguageIds
						);
					});

					return fields;
				},

				eachParentField(field, fn) {
					const instance = this;

					let parent = field.get('parent');

					while (isFormBuilderField(parent)) {
						fn.call(instance, parent);

						parent = parent.get('parent');
					}
				},

				getContent() {
					const instance = this;

					const definition = {};

					const translationManager = instance.translationManager;

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
					const instance = this;

					return window[
						instance.get('portletResourceNamespace') +
							'getContentValue'
					]();
				},

				initializer() {
					const instance = this;

					instance.MAP_HIDDEN_FIELD_ATTRS = A.clone(
						MAP_HIDDEN_FIELD_ATTRS
					);

					const translationManager = (instance.translationManager = new Liferay.TranslationManager(
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
					const instance = this;

					LiferayFormBuilder.UNIQUE_FIELD_NAMES_MAP.put(
						field.get('name'),
						field
					);

					return LiferayFormBuilder.superclass.plotField.apply(
						instance,
						arguments
					);
				},
			},
		});

		LiferayFormBuilder.Util = {
			getFileEntry(fileJSON, callback) {
				const instance = this;

				fileJSON = instance.parseJSON(fileJSON);

				Liferay.Service(
					'/dlapp/get-file-entry-by-uuid-and-group-id',
					{
						groupId: fileJSON.groupId,
						uuid: fileJSON.uuid,
					},
					callback
				);
			},

			getFileEntryURL(fileEntry) {
				const buffer = [
					themeDisplay.getPathContext(),
					'documents',
					fileEntry.groupId,
					fileEntry.folderId,
					encodeURIComponent(fileEntry.title),
				];

				return buffer.join('/');
			},

			normalizeKey(key) {
				key = key.trim();

				for (let i = 0; i < key.length; i++) {
					const item = key[i];

					if (
						!A.Text.Unicode.test(item, 'L') &&
						!A.Text.Unicode.test(item, 'N') &&
						!A.Text.Unicode.test(item, 'Pd') &&
						item !== STR_UNDERSCORE
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
				let data = {};

				try {
					data = JSON.parse(value);
				}
				catch (error) {}

				return data;
			},

			validateFieldName(fieldName) {
				let valid = true;

				if (REGEX_HYPHEN.test(fieldName)) {
					valid = false;

					return valid;
				}

				for (let i = 0; i < fieldName.length; i++) {
					const item = fieldName[i];

					if (
						!A.Text.Unicode.test(item, 'L') &&
						!A.Text.Unicode.test(item, 'N') &&
						!A.Text.Unicode.test(item, 'Pd') &&
						item !== STR_UNDERSCORE
					) {
						valid = false;

						break;
					}
				}

				return valid;
			},
		};

		LiferayFormBuilder.DEFAULT_ICON_CLASS = 'text';

		const AVAILABLE_FIELDS = {
			DDM_STRUCTURE: [
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.checkbox,
					iconClass: 'check-square',
					label: Liferay.Language.get('boolean'),
					type: 'checkbox',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'adjust',
					label: Liferay.Language.get('color'),
					type: 'ddm-color',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'calendar',
					label: Liferay.Language.get('date'),
					type: 'ddm-date',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'decimal',
					label: Liferay.Language.get('decimal'),
					type: 'ddm-decimal',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'document-text',
					label: Liferay.Language.get('documents-and-media'),
					type: 'ddm-documentlibrary',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'text',
					label: Liferay.Language.get('journal-article'),
					type: 'ddm-journal-article',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'code',
					label: Liferay.Language.get('html'),
					type: 'ddm-text-html',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'globe',
					label: Liferay.Language.get('geolocation'),
					type: 'ddm-geolocation',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'integer',
					label: Liferay.Language.get('integer'),
					type: 'ddm-integer',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'link',
					label: Liferay.Language.get('link-to-page'),
					type: 'ddm-link-to-page',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'number',
					label: Liferay.Language.get('number'),
					type: 'ddm-number',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'radio-button',
					label: Liferay.Language.get('radio'),
					type: 'radio',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'select',
					label: Liferay.Language.get('select'),
					type: 'select',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'text',
					label: Liferay.Language.get('text'),
					type: 'text',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'textbox',
					label: Liferay.Language.get('text-box'),
					type: 'textarea',
				},
			],

			DDM_TEMPLATE: [
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'paragraph',
					label: Liferay.Language.get('paragraph'),
					type: 'ddm-paragraph',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'separator',
					label: Liferay.Language.get('separator'),
					type: 'ddm-separator',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'blogs',
					label: Liferay.Language.get('fieldset'),
					type: 'fieldset',
				},
			],

			DEFAULT: [
				{
					fieldLabel: Liferay.Language.get('button'),
					iconClass: 'square-hole',
					label: Liferay.Language.get('button'),
					type: 'button',
				},
				{
					fieldLabel: Liferay.Language.get('checkbox'),
					iconClass: 'check-square',
					label: Liferay.Language.get('checkbox'),
					type: 'checkbox',
				},
				{
					fieldLabel: Liferay.Language.get('fieldset'),
					iconClass: 'cards',
					label: Liferay.Language.get('fieldset'),
					type: 'fieldset',
				},
				{
					fieldLabel: Liferay.Language.get('text-box'),
					iconClass: 'text',
					label: Liferay.Language.get('text-box'),
					type: 'text',
				},
				{
					fieldLabel: Liferay.Language.get('text-area-html'),
					iconClass: 'textbox',
					label: Liferay.Language.get('text-area-html'),
					type: 'textarea',
				},
				{
					fieldLabel: Liferay.Language.get('radio-buttons'),
					iconClass: 'radio',
					label: Liferay.Language.get('radio-buttons'),
					type: 'radio',
				},
				{
					fieldLabel: Liferay.Language.get('select-option'),
					iconClass: 'select',
					label: Liferay.Language.get('select-option'),
					type: 'select',
				},
			],

			WCM_STRUCTURE: [
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.DEFAULT,
					iconClass: 'picture',
					label: Liferay.Language.get('image'),
					type: 'ddm-image',
				},
				{
					hiddenAttributes: MAP_HIDDEN_FIELD_ATTRS.separator,
					iconClass: 'separator',
					label: Liferay.Language.get('separator'),
					type: 'ddm-separator',
				},
			],
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
			'text',
		],
	}
);
