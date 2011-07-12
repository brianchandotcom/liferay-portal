AUI().add(
	'liferay-portlet-dynamic-data-mapping',
	function(A) {
		var Lang = A.Lang;

		var DataType = A.DataType;

		var FormBuilderField = A.FormBuilderField;

		var instanceOf = A.instanceOf;

		var DEFAULTS_FORM_VALIDATOR = AUI.defaults.FormValidator;

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
			attributeList: '',
			nodeName: ''
		};

		var STR_CDATA_CLOSE = ']]>';

		var STR_CDATA_OPEN = '<![CDATA[';

		var STR_BLANK = '';

		var STR_SPACE = ' ';

		var TPL_ELEMENT = '<{nodeName}{attributeList}></{nodeName}>';

		DEFAULTS_FORM_VALIDATOR.STRINGS.structureFieldName = Liferay.Language.get('please-enter-only-alphanumeric-characters');

		DEFAULTS_FORM_VALIDATOR.RULES.structureFieldName = function(value) {
			return /^[\w-]+$/.test(value);
		};

		var LiferayFormBuilder = A.Component.create(
			{
				ATTRS: {
					availableFields: {
						valueFn: function() {
							return LiferayFormBuilder.AVAILABLE_FIELDS.DEFAULT;
						},
						validator: Lang.isObject
					},

					portletNamespace: {
						value: STR_BLANK
					},

					portletResourceNamespace: {
						value: STR_BLANK
					},

					validator: {
						setter: function(val) {
							var instance = this;

							var config = A.merge({
								boundingBox: instance.get('settingsFormNode'),

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
								},
								on: {
									errorField: function(event) {
										instance._tabs.selectTab(1);
									}
								},
								validateOnBlur: true
							}, val);

							return config;
						},
						value: {}
					},

					strings: {
						value: {
							button: Liferay.Language.get('button'),
							defaultMessage: Liferay.Language.get('drop-fields-here'),
							emptySelection: Liferay.Language.get('no-field-selected'),
							large: Liferay.Language.get('large'),
							medium: Liferay.Language.get('medium'),
							reset: Liferay.Language.get('Reset'),
							small: Liferay.Language.get('small'),
							submit: Liferay.Language.get('Submit'),
							type: Liferay.Language.get('type')
						}
					}
				},

				EXTENDS: A.FormBuilder,

				NAME: 'liferayformbuilder',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.validator = new A.FormValidator(instance.get('validator'));

						instance.addTarget(Liferay.Util.getOpener().Liferay);
					},

					getXSD: function() {
						var instance = this;

						var fields = instance.get('fields');
						var buffer = [];

						var root = instance._createDynamicNode('root');

						buffer.push(root.openTag);

						A.each(
							fields,
							function(item, index, collection) {
								instance._appendStructureTypeElementAndMetaData(item, buffer);
							}
						);

						buffer.push(root.closeTag);

						return buffer.join(STR_BLANK);
					},

					normalizeValue: function(value) {
						var instance = this;

						if (Lang.isUndefined(value)) {
							value = STR_BLANK;
						}

						return value;
					},

					_afterSelectedChange: function() {
						var instance = this;

						LiferayFormBuilder.superclass._afterSelectedChange.apply(instance, arguments);

						instance.validator._uiSetValidateOnBlur(true);
					},

					_appendStructureChildren: function(field, buffer, generateArticleContent) {
						var instance = this;

						var children = field.get('fields');

						A.each(
							children,
							function(item, index, collection) {
								instance._appendStructureTypeElementAndMetaData(item, buffer, generateArticleContent);
							}
						);
					},

					_appendStructureFieldOptionsBuffer: function(field, buffer, generateArticleContent) {
						var instance = this;

						var type = field.get('fieldType');
						var options = field.get('options');

						if (options) {
							A.each(
								options,
								function(item, index, collection) {
									var optionLabel = item.label;
									var optionValue = item.value;

									var typeElementOption = instance._createDynamicNode(
										'dynamic-element',
										{
											name: instance._formatOptionsKey(optionLabel),
											type: 'option',
											value: optionValue
										}
									);

									var metadata = instance._createDynamicNode('meta-data');

									var label = instance._createDynamicNode('entry', MAP_ATTR_LABEL);

									buffer.push(
										typeElementOption.openTag,
										metadata.openTag,
										label.openTag,
										STR_CDATA_OPEN + optionLabel + STR_CDATA_CLOSE,
										label.closeTag,
										metadata.closeTag,
										typeElementOption.closeTag
									);
								}
							);
						}
					},

					_appendStructureTypeElementAndMetaData: function(field, buffer, generateArticleContent) {
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

						var metadata = instance._createDynamicNode('meta-data');
						
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

						instance._appendStructureChildren(field, buffer, generateArticleContent);

						buffer.push(metadata.openTag);

						var requiredVal = instance.normalizeValue(field.get('required'));

						buffer.push(
							entryRequired.openTag,
							STR_CDATA_OPEN + requiredVal + STR_CDATA_CLOSE,
							entryRequired.closeTag
						);

						var fieldLabelVal = instance.normalizeValue(field.get('label'));

						buffer.push(
							label.openTag,
							STR_CDATA_OPEN + fieldLabelVal + STR_CDATA_CLOSE,
							label.closeTag
						);

						if (instanceOf(field, A.FormBuilderMultipleChoiceField)) {
							var multipleVal = instance.normalizeValue(field.get('multiple'));

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

						var predefinedValueVal = instance.normalizeValue(field.get('predefinedValue'));

						buffer.push(
							predefinedValue.openTag,
							STR_CDATA_OPEN + predefinedValueVal + STR_CDATA_CLOSE,
							predefinedValue.closeTag
						);

						var showLabelVal = instance.normalizeValue(field.get('showLabel'));

						buffer.push(
							showLabel.openTag,
							STR_CDATA_OPEN + showLabelVal + STR_CDATA_CLOSE,
							showLabel.closeTag
						);

						var styleVal = instance.normalizeValue(field.get('style'));

						buffer.push(
							style.openTag,
							STR_CDATA_OPEN + styleVal + STR_CDATA_CLOSE,
							style.closeTag
						);

						var tipVal = instance.normalizeValue(field.get('tip'));

						buffer.push(
							tip.openTag,
							STR_CDATA_OPEN + tipVal + STR_CDATA_CLOSE,
							tip.closeTag
						);

						if (instanceOf(field, A.FormBuilderTextField)) {
							var widthVal = instance.normalizeValue(field.get('width'));
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

						buffer.push(metadata.closeTag, typeElement.closeTag);
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

						return str.replace(/\W+/g, STR_SPACE).replace(/^\W+|\W+$/g, STR_BLANK).replace(/ /g, '_');
					},

					_onClickSettingsButton: function(event) {
						var instance = this;

						var target = event.currentTarget;

						LiferayFormBuilder.superclass._onClickSettingsButton.apply(instance, arguments);

						if (target.hasClass('aui-form-builder-button-save')) {
							instance.validator.validate();
						}
					}
				}
			}
		);

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
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-text',
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
					fieldLabel: Liferay.Language.get('boolean'),
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-checkbox',
					label: Liferay.Language.get('boolean'),
					type: 'checkbox'
				},
				{
					fieldLabel: Liferay.Language.get('date'),
					iconClass: 'aui-form-builder-field-icon aui-icon aui-icon-calendar',
					label: Liferay.Language.get('date'),
					type: 'ddm-date'
				},
				{
					fieldLabel: Liferay.Language.get('decimal'),
					label: Liferay.Language.get('decimal'),
					type: 'ddm-decimal'
				},
				{
					fieldLabel: Liferay.Language.get('integer'),
					label: Liferay.Language.get('integer'),
					type: 'ddm-integer'
				},
				{
					fieldLabel: Liferay.Language.get('number'),
					label: Liferay.Language.get('number'),
					type: 'ddm-number'
				},
				{
					fieldLabel: Liferay.Language.get('radio'),
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-radio',
					label: Liferay.Language.get('radio'),
					type: 'radio'
				},
				{
					fieldLabel: Liferay.Language.get('select'),
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-select',
					label: Liferay.Language.get('select'),
					type: 'select'
				},
				{
					fieldLabel: Liferay.Language.get('text'),
					label: Liferay.Language.get('text'),
					type: 'text'
				},
				{
					fieldLabel: Liferay.Language.get('text-box'),
					label: Liferay.Language.get('text-box'),
					type: 'textarea'
				}
			],

			DDM_TEMPLATE: [
				{
					fieldLabel: Liferay.Language.get('paragraph'),
					iconClass: 'aui-form-builder-field-icon aui-form-builder-field-icon-paragraph',
					label: Liferay.Language.get('paragraph'),
					type: 'ddm-paragraph'
				},
				{
					fieldLabel: Liferay.Language.get('separator'),
					iconClass: 'aui-form-builder-field-icon ddm-field-icon-separator',
					label: Liferay.Language.get('separator'),
					type: 'ddm-separator'
				},
				{
					fieldLabel: Liferay.Language.get('fieldset'),
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
		requires: ['aui-form-builder', 'aui-form-validator', 'text']
	}
);