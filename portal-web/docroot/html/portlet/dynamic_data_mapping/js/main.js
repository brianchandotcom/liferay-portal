AUI().add(
	'liferay-portlet-dynamic-data-mapping',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;
		var FormBuilderField = A.FormBuilderField;

		var instanceOf = A.instanceOf;
		var isObject = Lang.isObject;

		var DEFAULTS_FORM_VALIDATOR = AUI.defaults.FormValidator;

		var LOCALIZABLE_FIELD_ATTRS = ['label', 'predefinedValue', 'tip'];

		var STR_BLANK = '';

		var MAP_ATTR_DISPLAY_CHILD_LABEL_AS_VALUE = {
			name: 'displayChildLabelAsValue'
		};

		var MAP_ATTR_FIELD_CSS_CLASS = {
			name: 'fieldCssClass'
		};

		var MAP_ATTR_LABEL = {
			name: 'label'
		};

		var MAP_ATTR_MULTIPLE = {
			name: 'multiple'
		};

		var MAP_ATTR_PREDEFINED_VALUE = {
			name: 'predefinedValue'
		};

		var MAP_ATTR_REQUIRED = {
			name: 'required'
		};

		var MAP_ATTR_SHOW_LABEL = {
			name: 'showLabel'
		};

		var MAP_ATTR_STYLE = {
			name: 'style'
		};

		var MAP_ATTR_TIP = {
			name: 'tip'
		};

		var MAP_ATTR_WIDTH = {
			name: 'width'
		};

		var MAP_ELEMENT_DATA = {
			attributeList: STR_BLANK,
			nodeName: STR_BLANK
		};

		var STR_CDATA_CLOSE = ']]>';

		var STR_CDATA_OPEN = '<![CDATA[';

		var STR_SPACE = ' ';

		var TPL_ELEMENT = '<{nodeName}{attributeList}></{nodeName}>';

		DEFAULTS_FORM_VALIDATOR.STRINGS.structureFieldName = Liferay.Language.get('please-enter-only-alphanumeric-characters');

		DEFAULTS_FORM_VALIDATOR.RULES.structureFieldName = function(value) {
			return (/^[\w\-]+$/).test(value);
		};

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

					translationManager: {
						validator: isObject,
						value: {}
					},

					validator: {
						setter: function(val) {
							var instance = this;

							var config = A.merge(
								{
									rules: {
										name: {
											required: true,
											structureFieldName: true
										}
									},
									fieldStrings: {
										name: {
											required: Liferay.Language.get('this-field-is-required')
										}
									}
								},
								val
							);

							return config;
						},
						value: {}
					},

					strings: {
						value: {
							addNode: Liferay.Language.get('add-field'),
							button: Liferay.Language.get('button'),
							buttonType: Liferay.Language.get('button-type'),
							cancel: Liferay.Language.get('cancel'),
							deleteFieldsMessage: Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-entries'),
							duplicateMessage: Liferay.Language.get('duplicate'),
							editMessage: Liferay.Language.get('edit'),
							label: Liferay.Language.get('field-label'),
							large: Liferay.Language.get('large'),
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
					}
				},

				EXTENDS: A.FormBuilder,

				NAME: 'liferayformbuilder',

				prototype: {
					initializer: function() {
						var instance = this;

						var translationManager = instance.translationManager = new Liferay.TranslationManager(instance.get('translationManager'));

						instance.after(
							'render',
							function(event) {
								translationManager.render();
							}
						);

						instance.addTarget(Liferay.Util.getOpener().Liferay);
					},

					bindUI: function() {
						var instance = this;

						LiferayFormBuilder.superclass.bindUI.apply(instance, arguments);

						instance.translationManager.after('editingLocaleChange', instance._afterEditingLocaleChange, instance);
					},

					createField: function() {
						var instance = this;

						var field = LiferayFormBuilder.superclass.createField.apply(instance, arguments);

						field.set('strings', instance.get('strings'));

						return field;
					},

					getXSD: function() {
						var instance = this;

						var buffer = [];

						var translationManager = instance.translationManager;

						var editingLocale = translationManager.get('editingLocale');

						instance._updateFieldsLocalizationMap(editingLocale);

						var root = instance._createDynamicNode(
							'root',
							{
								'available-locales': translationManager.get('availableLocales').join(),
								'default-locale': translationManager.get('defaultLocale')
							}
						);

						buffer.push(root.openTag);

						instance.get('fields').each(
							function(item, index, collection) {
								instance._appendStructureTypeElementAndMetaData(item, buffer);
							}
						);

						buffer.push(root.closeTag);

						return buffer.join(STR_BLANK);
					},

					getFieldLocalizedValue: function(field, attribute, locale) {
						var instance = this;

						var localizationMap = field.get('localizationMap');

						var value = A.Object.getValue(localizationMap, [locale, attribute]) || field.get(attribute);

						return instance.normalizeValue(value);
					},

					normalizeValue: function(value) {
						var instance = this;

						if (Lang.isUndefined(value)) {
							value = STR_BLANK;
						}

						return value;
					},

					_afterEditingLocaleChange: function(event) {
						var instance = this;

						var newVal = event.newVal;
						var prevVal = event.prevVal;

						instance._updateFieldsLocalizationMap(prevVal);

						instance._syncFieldsLocaleUI(newVal);
					},

					_appendStructureChildren: function(field, buffer) {
						var instance = this;

						field.get('fields').each(
							function(item, index, collection) {
								instance._appendStructureTypeElementAndMetaData(item, buffer);
							}
						);
					},

					_appendStructureFieldOptionsBuffer: function(field, buffer) {
						var instance = this;

						var options = field.get('options');

						if (options) {
							AArray.each(
								options,
								function(item, index, collection) {
									var optionValue = item.value;

									var typeElementOption = instance._createDynamicNode(
										'dynamic-element',
										{
											name: instance._formatOptionsKey(item.label),
											type: 'option',
											value: optionValue
										}
									);

									buffer.push(typeElementOption.openTag);

									instance._appendStructureOptionMetaData(item, buffer);

									buffer.push(typeElementOption.closeTag);
								}
							);
						}
					},

					_appendStructureOptionMetaData: function(option, buffer) {
						var instance = this;

						var label = instance._createDynamicNode('entry', MAP_ATTR_LABEL);
						var localizationMap = option.localizationMap;

						A.each(
							localizationMap,
							function(item, index, collection) {
								if (isObject(item)) {
									var metadata = instance._createDynamicNode(
										'meta-data',
										{
											locale: index
										}
									);

									var labelVal = instance.normalizeValue(item.label);

									buffer.push(
										metadata.openTag,
										label.openTag,
										STR_CDATA_OPEN + labelVal + STR_CDATA_CLOSE,
										label.closeTag,
										metadata.closeTag
									);
								}
							}
						);
					},

					_appendStructureTypeElementAndMetaData: function(field, buffer) {
						var instance = this;

						var typeElement = instance._createDynamicNode(
							'dynamic-element',
							{
								dataType: field.get('dataType'),
								fieldNamespace: field.get('fieldNamespace'),
								name: encodeURI(field.get('name')),
								type: field.get('type')
							}
						);

						var displayChildLabelAsValue = instance._createDynamicNode('entry', MAP_ATTR_DISPLAY_CHILD_LABEL_AS_VALUE);

						var entryRequired = instance._createDynamicNode('entry', MAP_ATTR_REQUIRED);

						var fieldCssClass = instance._createDynamicNode('entry', MAP_ATTR_FIELD_CSS_CLASS);

						var label = instance._createDynamicNode('entry', MAP_ATTR_LABEL);

						var multiple = instance._createDynamicNode('entry', MAP_ATTR_MULTIPLE);

						var predefinedValue = instance._createDynamicNode('entry', MAP_ATTR_PREDEFINED_VALUE);

						var showLabel = instance._createDynamicNode('entry', MAP_ATTR_SHOW_LABEL);

						var style = instance._createDynamicNode('entry', MAP_ATTR_STYLE);

						var tip = instance._createDynamicNode('entry', MAP_ATTR_TIP);

						var width = instance._createDynamicNode('entry', MAP_ATTR_WIDTH);

						buffer.push(typeElement.openTag);

						instance._appendStructureFieldOptionsBuffer(field, buffer);

						instance._appendStructureChildren(field, buffer);

						var availableLocales = instance.translationManager.get('availableLocales');

						AArray.each(
							availableLocales,
							function(item, index, collection) {
								var metadata = instance._createDynamicNode(
									'meta-data',
									{
										locale: item
									}
								);

								buffer.push(metadata.openTag);

								var requiredVal = instance.getFieldLocalizedValue(field, 'required', item);

								buffer.push(
										entryRequired.openTag,
										STR_CDATA_OPEN + requiredVal + STR_CDATA_CLOSE,
										entryRequired.closeTag
								);

								var fieldLabelVal = instance.getFieldLocalizedValue(field, 'label', item);

								buffer.push(
										label.openTag,
										STR_CDATA_OPEN + fieldLabelVal + STR_CDATA_CLOSE,
										label.closeTag
								);

								if (instanceOf(field, A.FormBuilderMultipleChoiceField)) {
									var multipleVal = instance.getFieldLocalizedValue(field, 'multiple', item);

									buffer.push(
											multiple.openTag,
											STR_CDATA_OPEN + multipleVal + STR_CDATA_CLOSE,
											multiple.closeTag
									);

									buffer.push(
											displayChildLabelAsValue.openTag,
											STR_CDATA_OPEN + true + STR_CDATA_CLOSE,
											displayChildLabelAsValue.closeTag
									);
								}

								var predefinedValueVal = instance.getFieldLocalizedValue(field, 'predefinedValue', item);

								buffer.push(
										predefinedValue.openTag,
										STR_CDATA_OPEN + predefinedValueVal + STR_CDATA_CLOSE,
										predefinedValue.closeTag
								);

								var showLabelVal = instance.getFieldLocalizedValue(field, 'showLabel', item);

								buffer.push(
										showLabel.openTag,
										STR_CDATA_OPEN + showLabelVal + STR_CDATA_CLOSE,
										showLabel.closeTag
								);

								var styleVal = instance.getFieldLocalizedValue(field, 'style', item);

								buffer.push(
									style.openTag,
									STR_CDATA_OPEN + styleVal + STR_CDATA_CLOSE,
									style.closeTag
								);

								var tipVal = instance.getFieldLocalizedValue(field, 'tip', item);

								buffer.push(
										tip.openTag,
										STR_CDATA_OPEN + tipVal + STR_CDATA_CLOSE,
										tip.closeTag
								);

								if (instanceOf(field, A.FormBuilderTextField)) {
									var widthVal = instance.getFieldLocalizedValue(field, 'width', item);
									var widthCssClassVal = A.getClassName('w' + widthVal);

									buffer.push(
											fieldCssClass.openTag,
											STR_CDATA_OPEN + widthCssClassVal + STR_CDATA_CLOSE,
											fieldCssClass.closeTag
									);

									buffer.push(
											width.openTag,
											STR_CDATA_OPEN + widthVal + STR_CDATA_CLOSE,
											width.closeTag
									);
								}

								buffer.push(metadata.closeTag);
							}
						);

						buffer.push(typeElement.closeTag);
					},

					_createDynamicNode: function(nodeName, attributeMap) {
						var instance = this;

						var attrs = [];
						var typeElement = [];

						if (!nodeName) {
							nodeName = 'dynamic-element';
						}

						MAP_ELEMENT_DATA.attributeList = STR_BLANK;
						MAP_ELEMENT_DATA.nodeName = nodeName;

						if (attributeMap) {
							A.each(
								attributeMap,
								function(item, index, collection) {
									if (item !== undefined) {
										attrs.push([index, '="', item, '" '].join(STR_BLANK));
									}
								}
							);

							MAP_ELEMENT_DATA.attributeList = STR_SPACE + attrs.join(STR_BLANK);
						}

						typeElement = Lang.sub(TPL_ELEMENT, MAP_ELEMENT_DATA);
						typeElement = typeElement.replace(/\s?(>)(<)/, '$1$1$2$2').split(/></);

						return {
							closeTag: typeElement[1],
							openTag: typeElement[0]
						};
					},

					_formatOptionsKey: function(str) {
						var instance = this;

						str = A.Text.AccentFold.fold(str);

						A.each(
							str,
							function(item, index, collection) {
								if (!A.Text.Unicode.test(item, 'L') && !A.Text.Unicode.test(item, 'N')) {
									str = str.replace(item, STR_SPACE);
								}
							}
						);

						return str.replace(/ /g, '_');
					},

					_syncFieldOptionsLocaleUI: function(field, locale) {
						var instance = this;

						var options = field.get('options');

						AArray.each(
							options,
							function(item, index, collection) {
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
										LOCALIZABLE_FIELD_ATTRS,
										function(item, index, collection) {
											field.set(item, localeMap[item]);
										}
									);

									instance._syncUniqueField(field);
								}

								if (instance.editingField === field) {
									instance.propertyList.set('recordset', field.getProperties());
								}

								instance._syncFieldsLocaleUI(locale, field.get('fields'));
							}
						);
					},

					_updateFieldOptionsLocalizationMap: function(field, locale) {
						var instance = this;

						var options = field.get('options');

						AArray.each(
							options,
							function(item, index, collection) {
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
							function(field, index, fields) {
								var localizationMap = field.get('localizationMap');

								if (!isObject(localizationMap)) {
									localizationMap = {};
								}

								var localeMap = localizationMap[locale] = {};

								if (instanceOf(field, A.FormBuilderMultipleChoiceField)) {
									instance._updateFieldOptionsLocalizationMap(field, locale);
								}

								AArray.each(
									LOCALIZABLE_FIELD_ATTRS,
									function(item, index, collection) {
										localeMap[item] = field.get(item);
									}
								);

								field.set('localizationMap', localizationMap);

								instance._updateFieldsLocalizationMap(locale, field.get('fields'));
							}
						);
					}
				}
			}
		);

		LiferayFormBuilder.DEFAULT_ICON_CLASS = 'aui-form-builder-field-icon aui-form-builder-field-icon-text';

		LiferayFormBuilder.AVAILABLE_FIELDS = {
			DEFAULT: [
				{
					fieldLabel: Liferay.Language.get('button'),
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-button',
					label: Liferay.Language.get('button'),
					type: 'button'
				},
				{
					fieldLabel: Liferay.Language.get('checkbox'),
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-checkbox',
					label: Liferay.Language.get('checkbox'),
					type: 'checkbox'
				},
				{
					fieldLabel: Liferay.Language.get('fieldset'),
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-fieldset',
					label: Liferay.Language.get('fieldset'),
					type: 'fieldset'
				},
				{
					fieldLabel: Liferay.Language.get('file-upload'),
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-fileupload',
					label: Liferay.Language.get('file-upload'),
					type: 'fileupload'
				},
				{
					fieldLabel: Liferay.Language.get('text-box'),
					iconClass: LiferayFormBuilder.DEFAULT_ICON_CLASS,
					label: Liferay.Language.get('text-box'),
					type: 'text'
				},
				{
					fieldLabel: Liferay.Language.get('text-area'),
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-textarea',
					label: Liferay.Language.get('text-area'),
					type: 'textarea'
				},
				{
					fieldLabel: Liferay.Language.get('radio-buttons'),
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-radio',
					label: Liferay.Language.get('radio-buttons'),
					type: 'radio'
				},
				{
					fieldLabel: Liferay.Language.get('select-option'),
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-select',
					label: Liferay.Language.get('select-option'),
					type: 'select'
				}
			],

			DDM_STRUCTURE: [
				{
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-checkbox',
					label: Liferay.Language.get('boolean'),
					type: 'checkbox'
				},
				{
					iconClass: LiferayFormBuilder.DEFAULT_ICON_CLASS,
					label: Liferay.Language.get('date'),
					type: 'ddm-date'
				},
				{
					iconClass: LiferayFormBuilder.DEFAULT_ICON_CLASS,
					label: Liferay.Language.get('decimal'),
					type: 'ddm-decimal'
				},
				{
					iconClass: LiferayFormBuilder.DEFAULT_ICON_CLASS,
					label: Liferay.Language.get('integer'),
					type: 'ddm-integer'
				},
				{
					iconClass: LiferayFormBuilder.DEFAULT_ICON_CLASS,
					label: Liferay.Language.get('number'),
					type: 'ddm-number'
				},
				{
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-radio',
					label: Liferay.Language.get('radio'),
					type: 'radio'
				},
				{
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-select',
					label: Liferay.Language.get('select'),
					type: 'select'
				},
				{
					iconClass: LiferayFormBuilder.DEFAULT_ICON_CLASS,
					label: Liferay.Language.get('text'),
					type: 'text'
				},
				{
					iconClass: LiferayFormBuilder.DEFAULT_ICON_CLASS,
					label: Liferay.Language.get('text-box'),
					type: 'textarea'
				}
			],

			DDM_TEMPLATE: [
				{
					iconClass: LiferayFormBuilder.DEFAULT_ICON_CLASS,
					label: Liferay.Language.get('paragraph'),
					type: 'ddm-paragraph'
				},
				{
					iconClass: 'aui-form-builder-field-icon ddm-field-icon-separator',
					label: Liferay.Language.get('separator'),
					type: 'ddm-separator'
				},
				{
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-fieldset',
					label: Liferay.Language.get('fieldset'),
					type: 'fieldset'
				}
			]
		};

		Liferay.FormBuilder = LiferayFormBuilder;
	},
	'',
	{
		requires: ['aui-form-builder', 'aui-form-validator', 'aui-text', 'liferay-translation-manager']
	}
);