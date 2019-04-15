AUI.add(
	'liferay-ddm-form',
	function(A) {
		var AArray = A.Array;

		var AObject = A.Object;

		var DateMath = A.DataType.DateMath;

		var Lang = A.Lang;

		var INSTANCE_ID_PREFIX = '_INSTANCE_';

		var SELECTOR_REPEAT_BUTTONS = '.lfr-ddm-repeatable-add-button, .lfr-ddm-repeatable-delete-button';

		var TPL_ICON_CARET = '<span class="collapse-icon-closed"><span class="icon-caret-right"></span></span>';

		var TPL_LAYOUTS_NAVBAR = '<nav class="navbar navbar-default">' +
				'<div class="collapse navbar-collapse">' +
					'<ul class="nav navbar-nav">' +
						'<li class="public {publicLayoutClass}"><a href="javascript:;">' + Liferay.Language.get('public-pages') + '</a></li>' +
						'<li class="private {privateLayoutClass}"><a href="javascript:;">' + Liferay.Language.get('private-pages') + '</a></li>' +
					'</ul>' +
				'</div>' +
			'</nav>';

		var TPL_LOADER = '<span class="linear loading-icon"></span>';

		var TPL_PAGE = '<li class="lfr-ddm-link" data-groupId="{groupId}" data-layoutId="{layoutId}" data-nodeType="{nodeType}" data-privateLayout="{privateLayout}">' +
				'<input class="lfr-ddm-page-radio" {checked} name="lfr-ddm-page" type="radio" />' +
				'<a class="collapsed collapse-icon lfr-ddm-page-label" href="javascript:;">{pageTitle}{icon}</a>' +
			'</li>';

		var TPL_PAGES_BREADCRUMB = '<ul class="breadcrumb lfr-ddm-breadcrumb"></ul>';

		var TPL_PAGES_BREADCRUMB_ELEMENT = '<li class="lfr-ddm-breadcrumb-element" data-groupId={groupId} data-layoutId={layoutId} data-privateLayout={privateLayout}>' +
				'<a title="{label}">{label}</a>' +
			'</li>';

		var TPL_PAGES_CONTAINER = '<ul class="lfr-ddm-pages-container nav vertical-scrolling"></ul>';

		var TPL_REPEATABLE_ADD = '<a class="icon-plus-sign lfr-ddm-repeatable-add-button" href="javascript:;"></a>';

		var TPL_REPEATABLE_DELETE = '<a class="hide icon-minus-sign lfr-ddm-repeatable-delete-button" href="javascript:;"></a>';

		var TPL_REPEATABLE_HELPER = '<div class="lfr-ddm-repeatable-helper"></div>';

		var TPL_REQUIRED_MARK = '<span class="icon-asterisk text-warning"><span class="hide-accessible">' + Liferay.Language.get('required') + '</span></span>';

		var FieldTypes = Liferay.namespace('DDM.FieldTypes');

		var getFieldClass = function(type) {
			return FieldTypes[type] || FieldTypes.field;
		};

		var isNode = function(node) {
			return node && (node._node || node.nodeType);
		};

		var DDMPortletSupport = function() {
		};

		DDMPortletSupport.ATTRS = {
			doAsGroupId: {
			},

			fieldsNamespace: {
			},

			p_l_id: {
			},

			portletNamespace: {
			}
		};

		var FieldsSupport = function() {
		};

		FieldsSupport.ATTRS = {
			container: {
				setter: A.one
			},

			definition: {
			},

			displayLocale: {
				valueFn: '_valueDisplayLocale'
			},

			fields: {
				valueFn: '_valueFields'
			},

			mode: {
			},

			values: {
				value: {}
			}
		};

		FieldsSupport.prototype = {
			eachParent: function(fn) {
				var instance = this;

				var parent = instance.get('parent');

				while (parent !== undefined) {
					fn.call(instance, parent);

					parent = parent.get('parent');
				}
			},

			extractInstanceId: function(fieldNode) {
				var instance = this;

				var fieldInstanceId = fieldNode.getData('fieldNamespace');

				return fieldInstanceId.replace(INSTANCE_ID_PREFIX, '');
			},

			getDefaultLocale: function() {
				var instance = this;

				var defaultLocale = themeDisplay.getDefaultLanguageId();

				var definition = instance.get('definition');

				if (definition) {
					defaultLocale = definition.defaultLanguageId;
				}

				return defaultLocale;
			},

			getFieldInfo: function(tree, key, value) {
				var queue = new A.Queue(tree);

				var addToQueue = function(item) {
					if (queue._q.indexOf(item) === -1) {
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
							children.forEach(addToQueue);
						}
					}
				}

				return fieldInfo;
			},

			getFieldNodes: function() {
				var instance = this;

				return instance.get('container').all('> .field-wrapper');
			},

			getForm: function() {
				var instance = this;

				var root;

				instance.eachParent(
					function(parent) {
						root = parent;
					}
				);

				return root || instance;
			},

			getReadOnly: function() {
				var instance = this;

				if (instance.get('readOnly')) {
					return true;
				}

				var form = instance.getForm();

				if (!instance.get('localizable') && form.getDefaultLocale() != instance.get('displayLocale')) {
					return true;
				}

				return false;
			},

			_getField: function(fieldNode) {
				var instance = this;

				var displayLocale = instance.get('displayLocale');

				var fieldInstanceId = instance.extractInstanceId(fieldNode);

				var fieldName = fieldNode.getData('fieldName');

				var definition = instance.get('definition');

				var fieldDefinition = instance.getFieldInfo(definition, 'name', fieldName);

				var FieldClass = getFieldClass(fieldDefinition.type);

				var field = new FieldClass(
					A.merge(
						instance.getAttrs(AObject.keys(DDMPortletSupport.ATTRS)),
						{
							container: fieldNode,
							dataType: fieldDefinition.dataType,
							definition: definition,
							displayLocale: displayLocale,
							instanceId: fieldInstanceId,
							name: fieldName,
							parent: instance,
							values: instance.get('values')
						}
					)
				);

				var form = instance.getForm();

				field.addTarget(form);

				return field;
			},

			_getTemplate: function(callback) {
				var instance = this;

				var config = {
					data: {},
					on: {
						success: function(event, id, xhr) {
							if (callback) {
								callback.call(instance, xhr.responseText);
							}
						}
					}
				};

				var key = Liferay.Util.getPortletNamespace(Liferay.PortletKeys.DYNAMIC_DATA_MAPPING) + 'definition';

				config.data[key] = JSON.stringify(instance.get('definition'));

				A.io.request(instance._getTemplateResourceURL(), config);
			},

			_getTemplateResourceURL: function() {
				var instance = this;

				var portletURL = Liferay.PortletURL.createRenderURL(themeDisplay.getURLControlPanel());

				var container = instance.get('container');

				portletURL.setDoAsGroupId(instance.get('doAsGroupId'));
				portletURL.setLifecycle(Liferay.PortletURL.RESOURCE_PHASE);
				portletURL.setParameter('fieldName', instance.get('name'));
				portletURL.setParameter('mode', instance.get('mode'));
				portletURL.setParameter('namespace', instance.get('fieldsNamespace'));
				portletURL.setParameter('p_p_auth', container.getData('ddmAuthToken'));
				portletURL.setParameter('p_p_isolated', true);
				portletURL.setParameter('portletNamespace', instance.get('portletNamespace'));
				portletURL.setParameter('readOnly', instance.get('readOnly'));
				portletURL.setPlid(instance.get('p_l_id'));
				portletURL.setPortletId(Liferay.PortletKeys.DYNAMIC_DATA_MAPPING);
				portletURL.setResourceId('renderStructureField');
				portletURL.setWindowState('pop_up');

				return portletURL.toString();
			},

			_valueDisplayLocale: function() {
				var instance = this;

				var displayLocale = instance.get('displayLocale');

				if (!displayLocale) {
					displayLocale = instance.getDefaultLocale();
				}

				var defaultEditLocale = instance.get('defaultEditLocale');

				if (defaultEditLocale) {
					displayLocale = defaultEditLocale;
				}

				return displayLocale;
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

					readOnly: {
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

						instance._bindedOnLocaleChanged = A.bind(
							'_onLocaleChanged',
							instance
						);

						Liferay.on(
							'inputLocalized:localeChanged',
							instance._bindedOnLocaleChanged
						);
					},

					renderUI: function() {
						var instance = this;

						if (instance.get('repeatable')) {
							instance.renderRepeatableUI();
							instance.syncRepeatablelUI();
						}

						instance.syncValueUI();

						AArray.invoke(instance.get('fields'), 'renderUI');

						instance.fire(
							'liferay-ddm-field:render',
							{
								field: instance
							}
						);
					},

					destructor: function() {
						var instance = this;

						if (instance._bindedOnLocaleChanged) {
							Liferay.detach(
								'inputLocalized:localeChanged',
								instance._bindedOnLocaleChanged
							);
						}

						instance.get('container').remove();
					},

					addLocaleToLocalizationMap: function(locale) {
						var instance = this;

						var localizationMap = instance.get('localizationMap');

						if (Lang.isUndefined(localizationMap[locale])) {
							var predefinedValue = instance.getPredefinedValueByLocale(locale);

							if (predefinedValue) {
								localizationMap[locale] = predefinedValue;
							}
							else {
								var defaultLocale = instance.getDefaultLocale();

								if (defaultLocale && localizationMap[defaultLocale]) {
									localizationMap[locale] = localizationMap[defaultLocale];
								}
								else {
									localizationMap[locale] = '';
								}
							}
						}
					},

					createField: function(fieldTemplate) {
						var instance = this;

						var fieldNode = A.Node.create(fieldTemplate);

						instance.get('container').placeAfter(fieldNode);

						instance.parseContent(fieldTemplate);

						var parent = instance.get('parent');

						var siblings = instance.getSiblings();

						var field = parent._getField(fieldNode);

						var index = siblings.indexOf(instance);

						siblings.splice(++index, 0, field);

						field.set('parent', parent);

						return field;
					},

					getFieldByNameInFieldDefinition: function(name) {
						var instance = this;

						var definition = instance.get('definition');

						var fields = [];

						if (definition && definition.fields) {
							fields = definition.fields;
						}

						return AArray.find(
							fields,
							function(item) {
								return item.name === name;
							}
						);
					},

					getFieldDefinition: function() {
						var instance = this;

						var definition = instance.get('definition');

						var name = instance.get('name');

						return instance.getFieldInfo(definition, 'name', name);
					},

					getFirstFieldByName: function(name) {
						var instance = this;

						return AArray.find(
							instance.get('fields'),
							function(item) {
								return item.get('name') === name;
							}
						);
					},

					getInputName: function() {
						var instance = this;

						var fieldsNamespace = instance.get('fieldsNamespace');
						var portletNamespace = instance.get('portletNamespace');

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

					getPredefinedValueByLocale: function(locale) {
						var instance = this;

						var name = instance.get('name');

						var field = instance.getFieldByNameInFieldDefinition(name);

						var predefinedValue;

						if (field) {
							var type = field.type;

							if (field.predefinedValue && field.predefinedValue[locale]) {
								predefinedValue = field.predefinedValue[locale];
							}

							if ((type === 'select') && (predefinedValue === '[""]')) {
								predefinedValue = '';
							}
						}

						return predefinedValue;
					},

					getRepeatedSiblings: function() {
						var instance = this;

						return instance.getSiblings().filter(
							function(item) {
								return item.get('name') === instance.get('name');
							}
						);
					},

					getSiblings: function() {
						var instance = this;

						return instance.get('parent').get('fields');
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						return Lang.String.unescapeHTML(inputNode.val());
					},

					parseContent: function(content) {
						var instance = this;

						var container = instance.get('container');

						container.plug(A.Plugin.ParseContent);

						var parser = container.ParseContent;

						parser.parseContent(content);
					},

					remove: function() {
						var instance = this;

						var siblings = instance.getSiblings();

						var index = siblings.indexOf(instance);

						siblings.splice(index, 1);

						instance._removeFieldValidation(instance);

						instance.destroy();

						instance.get('container').remove(true);
					},

					renderRepeatableUI: function() {
						var instance = this;

						var container = instance.get('container');

						container.append(TPL_REPEATABLE_ADD);
						container.append(TPL_REPEATABLE_DELETE);

						container.delegate('click', instance._handleToolbarClick, SELECTOR_REPEAT_BUTTONS, instance);
					},

					repeat: function() {
						var instance = this;

						instance._getTemplate(
							function(fieldTemplate) {
								var field = instance.createField(fieldTemplate);

								var displayLocale = instance.get('displayLocale');

								field.addLocaleToLocalizationMap(displayLocale);
								field.set('displayLocale', displayLocale);

								if (instance.originalField) {
									field.originalField = instance.originalField;
								}
								else {
									field.originalField = instance;
								}

								var form = field.getForm();

								form.newRepeatableInstances.push(field);

								field.renderUI();

								instance._addFieldValidation(field, instance);
							}
						);
					},

					setLabel: function(label) {
						var instance = this;

						var labelNode = instance.getLabelNode();

						if (labelNode) {
							var tipNode = labelNode.one('.taglib-icon-help');

							if (!A.UA.ie && Lang.isValue(label) && Lang.isNode(labelNode)) {
								labelNode.html(A.Escape.html(label));
							}

							var fieldDefinition = instance.getFieldDefinition();

							if (!A.UA.ie && fieldDefinition.required) {
								labelNode.append(TPL_REQUIRED_MARK);
							}

							instance._addTip(labelNode, tipNode);
						}
					},

					setValue: function(value) {
						var instance = this;

						var inputNode = instance.getInputNode();

						if (Lang.isValue(value)) {
							inputNode.val(value);
						}
					},

					syncReadOnlyUI: function() {
						var instance = this;

						var readOnly = instance.getReadOnly();

						var inputNode = instance.getInputNode();

						if (inputNode) {
							inputNode.attr('disabled', readOnly);
						}

						var container = instance.get('container');

						if (container) {
							var selectorInput = container.one('.selector-input');

							if (selectorInput) {
								selectorInput.attr('disabled', readOnly);
							}

							var checkboxInput = container.one('input[type="checkbox"]');

							if (checkboxInput) {
								checkboxInput.attr('disabled', readOnly);
							}

							var disableCheckboxInput = container.one('input[type="checkbox"][name$="disable"]');

							if (inputNode && disableCheckboxInput && disableCheckboxInput.get('checked')) {
								inputNode.attr('disabled', true);
							}
						}
					},

					syncRepeatablelUI: function() {
						var instance = this;

						var container = instance.get('container');

						var siblings = instance.getRepeatedSiblings();

						container.one('.lfr-ddm-repeatable-delete-button').toggle(siblings.length > 1);
					},

					syncValueUI: function() {
						var instance = this;

						var dataType = instance.get('dataType');

						if (dataType) {
							var localizationMap = instance.get('localizationMap');

							var value;

							if (instance.get('localizable')) {
								if (!A.Object.isEmpty(localizationMap)) {
									value = localizationMap[instance.get('displayLocale')];
								}
							}
							else {
								value = instance.getValue();
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

							if (instance.get('localizable')) {
								var form = instance.getForm();

								form.addAvailableLanguageIds(AObject.keys(fieldJSON.value));
							}

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
					},

					_addFieldValidation: function(newField, originalField) {
						var instance = this;

						instance.fire(
							'liferay-ddm-field:repeat',
							{
								field: newField,
								originalField: originalField
							}
						);

						newField.get('fields').forEach(
							function(item, index) {
								var name = item.get('name');

								var originalChildField = originalField.getFirstFieldByName(name);

								if (originalChildField) {
									instance._addFieldValidation(item, originalChildField);
								}
							}
						);
					},

					_addTip: function(labelNode, tipNode) {
						if (tipNode) {
							var instance = this;

							var defaultLocale = instance.getDefaultLocale();
							var fieldDefinition = instance.getFieldDefinition();

							var tipsMap = fieldDefinition.tip;

							if (Lang.isObject(tipsMap)) {
								var tip = tipsMap[instance.get('displayLocale')] || tipsMap[defaultLocale];

								tipNode.attr('title', tip);
							}

							labelNode.append(tipNode);
						}
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

						instance.ddmRepeatableButton = currentTarget;

						if (currentTarget.hasClass('lfr-ddm-repeatable-add-button')) {
							instance.repeat();
						}
						else if (currentTarget.hasClass('lfr-ddm-repeatable-delete-button')) {
							instance.remove();

							instance.syncRepeatablelUI();
						}

						event.stopPropagation();
					},

					_onLocaleChanged: function(event) {
						var instance = this;

						var currentLocale = instance.get('displayLocale');
						var displayLocale = event.item.getAttribute('data-value');

						instance.updateLocalizationMap(currentLocale);
						instance.addLocaleToLocalizationMap(displayLocale);

						instance.set('displayLocale', displayLocale);

						instance.syncValueUI();
						instance.syncReadOnlyUI();
					},

					_removeFieldValidation: function(field) {
						var instance = this;

						field.get('fields').forEach(
							function(item, index) {
								instance._removeFieldValidation(item);
							}
						);

						instance.fire(
							'liferay-ddm-field:remove',
							{
								field: field
							}
						);
					},

					_valueLocalizationMap: function() {
						var instance = this;

						var instanceId = instance.get('instanceId');

						var values = instance.get('values');

						var fieldValue = instance.getFieldInfo(values, 'instanceId', instanceId);

						var localizationMap = {};

						if (fieldValue && fieldValue.value) {
							localizationMap = fieldValue.value;
						}

						return localizationMap;
					}
				}
			}
		);

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

						var tipNode = labelNode.one('.taglib-icon-help');

						var inputNode = instance.getInputNode();

						if (Lang.isValue(label) && Lang.isNode(labelNode)) {
							labelNode.html('&nbsp;' + A.Escape.html(label));

							var fieldDefinition = instance.getFieldDefinition();

							if (fieldDefinition.required) {
								labelNode.append(TPL_REQUIRED_MARK);
							}

							labelNode.prepend(inputNode);
						}

						instance._addTip(labelNode, tipNode);
					},

					setValue: function(value) {
						var instance = this;

						instance.getInputNode().attr('checked', value === 'true');
					}
				}
			}
		);

		FieldTypes.checkbox = CheckboxField;

		var ColorField = A.Component.create(
			{
				EXTENDS: Field,

				prototype: {
					initializer: function() {
						var instance = this;

						var container = instance.get('container');

						var selectorInput = container.one('.selector-input');
						var valueField = container.one('.color-value');

						var colorPicker = new A.ColorPickerPopover(
							{
								position: 'bottom',
								trigger: selectorInput,
								zIndex: 65535
							}
						).render();

						colorPicker.on(
							'select',
							function(event) {
								selectorInput.setStyle('backgroundColor', event.color);

								valueField.val(event.color);
							}
						);

						colorPicker.set(
							'color',
							valueField.val(),
							{
								trigger: selectorInput
							}
						);

						instance.set('colorPicker', colorPicker);
					},

					getValue: function() {
						var instance = this;

						var container = instance.get('container');
						var valueField = container.one('.color-value');

						return valueField.val();
					},

					setValue: function(value) {
						var instance = this;

						var container = instance.get('container');

						var colorPicker = instance.get('colorPicker');
						var selectorInput = container.one('.selector-input');
						var valueField = container.one('.color-value');

						if (!colorPicker) {
							return;
						}

						valueField.val(value);
						selectorInput.setStyle('backgroundColor', value);

						colorPicker.set('color', value);
					}
				}
			}
		);

		FieldTypes['ddm-color'] = ColorField;

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

						var value = '';

						if (datePicker) {
							var selectedDate = datePicker.getDate();

							var formattedDate = A.DataType.Date.format(selectedDate);

							var inputNode = instance.getInputNode();

							value = inputNode.val() ? formattedDate : '';
						}

						return value;
					},

					repeat: function() {
						var instance = this;

						instance._getTemplate(
							function(fieldTemplate) {
								var field = instance.createField(fieldTemplate);

								var inputNode = field.getInputNode();

								Liferay.after(
									inputNode.attr('id') + 'DatePicker:registered',
									function() {
										field.renderUI();
									}
								);

								instance._addFieldValidation(field, instance);
							}
						);
					},

					setValue: function(value) {
						var instance = this;

						var datePicker = instance.getDatePicker();

						if (!datePicker) {
							return;
						}

						datePicker.set('activeInput', instance.getInputNode());

						datePicker.deselectDates();

						if (value) {
							var date = A.DataType.Date.parse(value);

							date = DateMath.add(date, DateMath.MINUTES, date.getTimezoneOffset());

							datePicker.selectDates(date);
						}
						else {
							datePicker.selectDates('');
						}
					}
				}
			}
		);

		FieldTypes['ddm-date'] = DateField;

		var DocumentLibraryField = A.Component.create(
			{
				ATTRS: {
					acceptedFileFormats: {
						value: ['*']
					}
				},

				EXTENDS: Field,

				prototype: {
					initializer: function() {
						var instance = this;

						var container = instance.get('container');

						container.delegate('click', instance._handleButtonsClick, '> .form-group .btn', instance);
					},

					syncUI: function() {
						var instance = this;

						var parsedValue = instance.getParsedValue(instance.getValue());

						var titleNode = A.one('#' + instance.getInputName() + 'Title');

						titleNode.val(parsedValue.title || '');

						var clearButtonNode = A.one('#' + instance.getInputName() + 'ClearButton');

						clearButtonNode.toggle(!!parsedValue.uuid);
					},

					getDocumentLibrarySelectorURL: function() {
						var instance = this;

						var form = instance.getForm();

						var documentLibrarySelectorURL = form.get('documentLibrarySelectorURL');

						var retVal = instance.getDocumentLibraryURL('com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion');

						if (documentLibrarySelectorURL) {
							retVal = documentLibrarySelectorURL;
						}

						return retVal;
					},

					getDocumentLibraryURL: function(criteria) {
						var instance = this;

						var container = instance.get('container');

						var portletNamespace = instance.get('portletNamespace');

						var portletURL = Liferay.PortletURL.createURL(themeDisplay.getLayoutRelativeControlPanelURL());

						portletURL.setParameter('criteria', criteria);
						portletURL.setParameter('itemSelectedEventName', portletNamespace + 'selectDocumentLibrary');
						portletURL.setParameter('p_p_auth', container.getData('itemSelectorAuthToken'));

						var criterionJSON = {
							desiredItemSelectorReturnTypes: 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType'
						};

						portletURL.setParameter('0_json', JSON.stringify(criterionJSON));
						portletURL.setParameter('1_json', JSON.stringify(criterionJSON));

						var uploadCriterionJSON = {
							desiredItemSelectorReturnTypes: 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
							URL: instance.getUploadURL()
						};

						portletURL.setParameter('2_json', JSON.stringify(uploadCriterionJSON));

						portletURL.setPortletId(Liferay.PortletKeys.ITEM_SELECTOR);
						portletURL.setPortletMode('view');
						portletURL.setWindowState('pop_up');

						return portletURL.toString();
					},

					getParsedValue: function(value) {
						var instance = this;

						if (Lang.isString(value)) {
							if (value !== '') {
								value = JSON.parse(value);
							}
							else {
								value = {};
							}
						}

						return value;
					},

					getUploadURL: function() {
						var instance = this;

						var portletURL = Liferay.PortletURL.createURL(themeDisplay.getLayoutRelativeControlPanelURL());

						portletURL.setLifecycle(Liferay.PortletURL.ACTION_PHASE);
						portletURL.setParameter('cmd', 'add_temp');
						portletURL.setParameter('javax.portlet.action', '/document_library/upload_file_entry');
						portletURL.setParameter('p_auth', Liferay.authToken);
						portletURL.setPortletId(Liferay.PortletKeys.DOCUMENT_LIBRARY);

						return portletURL.toString();
					},

					setValue: function(value) {
						var instance = this;

						var parsedValue = instance.getParsedValue(value);

						if (!parsedValue.title && !parsedValue.uuid) {
							value = '';
						}
						else {
							value = JSON.stringify(parsedValue);
						}

						DocumentLibraryField.superclass.setValue.call(instance, value);

						instance.syncUI();
					},

					syncReadOnlyUI: function() {
						var instance = this;

						var readOnly = instance.getReadOnly();

						var container = instance.get('container');

						var selectButtonNode = container.one('#' + instance.getInputName() + 'SelectButton');

						selectButtonNode.attr('disabled', readOnly);

						var clearButtonNode = container.one('#' + instance.getInputName() + 'ClearButton');

						clearButtonNode.attr('disabled', readOnly);

						var altNode = container.one('#' + instance.getInputName() + 'Alt');

						if (altNode) {
							altNode.set('readOnly', readOnly);
						}
					},

					_handleButtonsClick: function(event) {
						var instance = this;

						if (!instance.get('readOnly')) {
							var currentTarget = event.currentTarget;

							if (currentTarget.test('.select-button')) {
								instance._handleSelectButtonClick(event);
							}
							else if (currentTarget.test('.clear-button')) {
								instance._handleClearButtonClick(event);
							}
						}
					},

					_handleClearButtonClick: function(event) {
						var instance = this;

						instance.setValue('');
					},

					_handleSelectButtonClick: function(event) {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						var itemSelectorDialog = new A.LiferayItemSelectorDialog(
							{
								eventName: portletNamespace + 'selectDocumentLibrary',
								on: {
									selectedItemChange: function(event) {
										var selectedItem = event.newVal;

										if (selectedItem) {
											var itemValue = JSON.parse(selectedItem.value);

											instance.setValue(
												{
													classPK: itemValue.fileEntryId,
													groupId: itemValue.groupId,
													title: itemValue.title,
													type: itemValue.type,
													uuid: itemValue.uuid
												}
											);
										}
									}
								},
								url: instance.getDocumentLibrarySelectorURL()
							}
						);

						itemSelectorDialog.open();
					}
				}
			}
		);

		FieldTypes['ddm-documentlibrary'] = DocumentLibraryField;

		var JournalArticleField = A.Component.create(
			{
				EXTENDS: Field,

				prototype: {
					initializer: function() {
						var instance = this;

						var container = instance.get('container');

						container.delegate('click', instance._handleButtonsClick, '> .form-group .btn', instance);
					},

					syncUI: function() {
						var instance = this;

						var parsedValue = instance.getParsedValue(instance.getValue());

						var titleNode = A.one('#' + instance.getInputName() + 'Title');

						titleNode.val(parsedValue.title || '');

						var clearButtonNode = A.one('#' + instance.getInputName() + 'ClearButton');

						clearButtonNode.toggle(!!parsedValue.classPK);
					},

					getParsedValue: function(value) {
						if (Lang.isString(value)) {
							if (value !== '') {
								value = JSON.parse(value);
							}
							else {
								value = {};
							}
						}

						return value;
					},

					getWebContentSelectorURL: function() {
						var instance = this;

						var container = instance.get('container');

						var url = Liferay.PortletURL.createRenderURL(themeDisplay.getURLControlPanel());

						var groupIdNode = A.one('#' + this.get('portletNamespace') + 'groupId');

						var groupId = (groupIdNode && groupIdNode.getAttribute('value')) || themeDisplay.getScopeGroupId();

						url.setParameter('eventName', 'selectContent');
						url.setParameter('groupId', groupId);
						url.setParameter('p_p_auth', container.getData('assetBrowserAuthToken'));
						url.setParameter('selectedGroupId', groupId);
						url.setParameter('showNonindexable', true);
						url.setParameter('showScheduled', true);
						url.setParameter('typeSelection', 'com.liferay.journal.model.JournalArticle');
						url.setPortletId('com_liferay_asset_browser_web_portlet_AssetBrowserPortlet');
						url.setWindowState('pop_up');

						return url;
					},

					setValue: function(value) {
						var instance = this;

						var parsedValue = instance.getParsedValue(value);

						if (!parsedValue.className && !parsedValue.classPK) {
							value = '';
						}
						else {
							value = JSON.stringify(parsedValue);
						}

						JournalArticleField.superclass.setValue.call(instance, value);

						instance.syncUI();
					},

					showNotice: function(message) {
						var instance = this;

						if (!instance.notice) {
							instance.notice = new Liferay.Notice(
								{
									toggleText: false,
									type: 'warning'
								}
							).hide();
						}

						instance.notice.html(message);
						instance.notice.show();
					},

					syncReadOnlyUI: function() {
						var instance = this;

						var readOnly = instance.getReadOnly();

						var container = instance.get('container');

						var selectButtonNode = container.one('#' + instance.getInputName() + 'SelectButton');

						selectButtonNode.attr('disabled', readOnly);

						var clearButtonNode = container.one('#' + instance.getInputName() + 'ClearButton');

						clearButtonNode.attr('disabled', readOnly);
					},

					_handleButtonsClick: function(event) {
						var instance = this;

						if (!instance.get('readOnly')) {
							var currentTarget = event.currentTarget;

							if (currentTarget.test('.select-button')) {
								instance._handleSelectButtonClick(event);
							}
							else if (currentTarget.test('.clear-button')) {
								instance._handleClearButtonClick(event);
							}
						}
					},

					_handleClearButtonClick: function() {
						var instance = this;

						instance.setValue('');

						instance._hideMessage();
					},

					_handleSelectButtonClick: function(event) {
						var instance = this;

						Liferay.Util.selectEntity(
							{
								dialog: {
									constrain: true,
									destroyOnHide: true,
									modal: true
								},
								eventName: 'selectContent',
								id: 'selectContent',
								title: Liferay.Language.get('journal-article'),
								uri: instance.getWebContentSelectorURL()
							},
							function(event) {
								if (event.details.length > 0) {
									var selectedWebContent = event.details[0];

									instance.setValue(
										{
											className: selectedWebContent.assetclassname,
											classPK: selectedWebContent.assetclasspk,
											title: selectedWebContent.assettitle || ''
										}
									);

									instance._hideMessage();
								}
							}
						);
					},

					_hideMessage: function() {
						var instance = this;

						var container = instance.get('container');

						var message = container.one('#' + instance.getInputName() + 'Message');

						if (message) {
							message.addClass('hide');
						}

						var formGroup = container.one('#' + instance.getInputName() + 'FormGroup');

						formGroup.removeClass('has-warning');
					}
				}
			}
		);

		FieldTypes['ddm-journal-article'] = JournalArticleField;

		var LinkToPageField = A.Component.create(
			{
				ATTRS: {
					delta: {
						value: 10
					},

					selectedLayout: {
						valueFn: function() {
							var instance = this;

							var layoutValue = instance.getParsedValue(instance.getValue());

							var retVal = null;

							if (layoutValue.layoutId) {
								retVal = layoutValue;
							}

							return retVal;
						}
					},

					selectedLayoutPath: {
						valueFn: function() {
							var instance = this;

							var layoutValue = instance.getParsedValue(instance.getValue());

							var privateLayout = !!(layoutValue && layoutValue.privateLayout);

							var groupIdNode = A.one('#' + this.get('portletNamespace') + 'groupId');

							var groupId = (groupIdNode && groupIdNode.getAttribute('value')) || themeDisplay.getScopeGroupId();

							var layoutsRoot = {
								groupId: groupId,
								label: Liferay.Language.get('all'),
								layoutId: 0,
								privateLayout: privateLayout
							};

							return [layoutsRoot];
						}
					}
				},

				EXTENDS: Field,

				prototype: {
					initializer: function() {
						var instance = this;

						var container = instance.get('container');

						instance._currentParentLayoutId = 0;
						instance._loadingAnimationNode = A.Node.create(TPL_LOADER);

						instance._cache = {};

						instance._clearedModal = false;

						instance.after('selectedLayoutChange', instance._afterSelectedLayoutChange);
						instance.after('selectedLayoutPathChange', instance._afterSelectedLayoutPathChange);

						container.delegate('click', instance._handleControlButtonsClick, '> .form-group .btn', instance);
					},

					getParsedValue: function(value) {
						var instance = this;

						if (Lang.isString(value)) {
							if (value) {
								value = JSON.parse(value);
							}
							else {
								value = {};
							}
						}

						return value;
					},

					setValue: function(value) {
						var instance = this;

						var container = instance.get('container');

						var inputName = instance.getInputName();

						var layoutNameNode = container.one('#' + inputName + 'LayoutName');

						var parsedValue = instance.getParsedValue(value);

						if (parsedValue && parsedValue.layoutId) {
							if (parsedValue.label) {
								layoutNameNode.val(parsedValue.label);
							}

							value = JSON.stringify(parsedValue);
						}
						else {
							layoutNameNode.val('');

							value = '';
						}

						var clearButtonNode = container.one('#' + inputName + 'ClearButton');

						clearButtonNode.toggle(!!value);

						LinkToPageField.superclass.setValue.call(instance, value);
					},

					syncReadOnlyUI: function() {
						var instance = this;

						var readOnly = instance.getReadOnly();

						var container = instance.get('container');

						var selectButtonNode = container.one('#' + instance.getInputName() + 'SelectButton');

						selectButtonNode.attr('disabled', readOnly);

						var clearButtonNode = container.one('#' + instance.getInputName() + 'ClearButton');

						clearButtonNode.attr('disabled', readOnly);
					},

					_addBreadcrumbElement: function(label, layoutId, groupId, privateLayout) {
						var instance = this;

						var breadcrumbNode = instance._modal.bodyNode.one('.lfr-ddm-breadcrumb');

						var breadcrumbElementNode = A.Node.create(
							Lang.sub(
								TPL_PAGES_BREADCRUMB_ELEMENT,
								{
									groupId: groupId,
									label: label,
									layoutId: layoutId,
									privateLayout: privateLayout
								}
							)
						);

						breadcrumbNode.append(breadcrumbElementNode);
					},

					_addListElement: function(layout, container, selected, prepend) {
						var instance = this;

						var entryNode = A.Node.create(
							Lang.sub(
								TPL_PAGE,
								{
									checked: selected ? 'checked="checked"' : '',
									groupId: layout.groupId,
									icon: layout.hasChildren ? TPL_ICON_CARET : '',
									layoutId: layout.layoutId,
									nodeType: layout.hasChildren ? 'root' : 'leaf',
									pageTitle: layout.name,
									privateLayout: layout.privateLayout
								}
							)
						);

						if (prepend) {
							container.prepend(entryNode);
						}
						else {
							container.append(entryNode);
						}

						if (selected) {
							entryNode.scrollIntoView();
						}
					},

					_afterSelectedLayoutChange: function(event) {
						var instance = this;

						var modal = instance._modal;

						if (modal) {
							var notSelected = !event.newVal;

							var selectButton = modal.get('toolbars.footer')[0];

							var boundingBox = selectButton.boundingBox;

							boundingBox.attr('disabled', notSelected);
							boundingBox.toggleClass('disabled', notSelected);
						}
					},

					_afterSelectedLayoutPathChange: function(event) {
						var instance = this;

						instance._renderBreadcrumb(event.newVal);
					},

					_canLoadMore: function(key, start, end) {
						var instance = this;

						var cache = instance._getCache(key);

						return !cache || start < cache.start || end > cache.end;
					},

					_cleanSelectedLayout: function() {
						var instance = this;

						var checkedElement = instance._modal.bodyNode.one('.lfr-ddm-page-radio:checked');

						if (checkedElement) {
							checkedElement.attr('checked', false);

							instance.set('selectedLayout', null);
						}
					},

					_getCache: function(key) {
						var instance = this;

						var cache;

						if (instance._cache && instance._cache[key]) {
							cache = instance._cache[key];
						}

						return cache;
					},

					_getModalConfig: function() {
						var instance = this;

						return {
							dialog:	{
								cssClass: 'lfr-ddm-link-to-page-modal',
								height: 600,
								modal: true,
								on: {
									destroy: function() {
										instance.set('selectedLayout', null);
									}
								},
								resizable: false,
								toolbars: {
									footer: [
										{
											cssClass: 'btn-primary',
											disabled: !instance.get('selectedLayout'),
											label: Liferay.Language.get('select'),
											on: {
												click: A.bind(instance._handleChooseButtonClick, instance)
											}
										},
										{
											cssClass: 'btn-link',
											label: Liferay.Language.get('cancel'),
											on: {
												click: A.bind(instance._handleCancelButtonClick, instance)
											}
										}
									],
									header: [
										{
											cssClass: 'close',
											discardDefaultButtonCssClasses: true,
											labelHTML: Liferay.Util.getLexiconIconTpl('times'),
											on: {
												click: A.bind(instance._handleCancelButtonClick, instance)
											}
										}
									]
								},
								width: 400
							},
							title: Liferay.Language.get('select-layout')
						};
					},

					_handleBreadcrumbElementClick: function(event) {
						var instance = this;

						var currentTargetLayoutId = Number(event.currentTarget.getData('layoutId'));

						var selectedLayoutPath = instance.get('selectedLayoutPath');

						var lastLayoutIndex = selectedLayoutPath.length - 1;

						var lastLayout = selectedLayoutPath[lastLayoutIndex];

						var clickedLastElement = Number(lastLayout.layoutId) === currentTargetLayoutId;

						if (!clickedLastElement) {
							instance._cleanSelectedLayout();

							while (!clickedLastElement) {
								if (Number(lastLayout.layoutId) !== currentTargetLayoutId) {
									selectedLayoutPath.pop();

									lastLayoutIndex = selectedLayoutPath.length - 1;

									lastLayout = selectedLayoutPath[lastLayoutIndex];
								}
								else {
									clickedLastElement = true;

									var groupId = lastLayout.groupId;

									var privateLayout = lastLayout.privateLayout;

									instance._currentParentLayoutId = Number(currentTargetLayoutId);

									var bodyNode = instance._modal.bodyNode;

									var listNode = bodyNode.one('.lfr-ddm-pages-container');

									listNode.empty();

									instance._showLoader(listNode);

									listNode.addClass('top-ended');

									instance._requestInitialLayouts(currentTargetLayoutId, groupId, privateLayout, instance._renderLayouts);
								}
							}

							instance.set('selectedLayoutPath', selectedLayoutPath);
						}
					},

					_handleCancelButtonClick: function() {
						var instance = this;

						instance._modal.hide();
					},

					_handleChooseButtonClick: function() {
						var instance = this;

						var selectedLayout = instance.get('selectedLayout');

						instance.setValue(selectedLayout);

						instance._modal.hide();
					},

					_handleClearButtonClick: function() {
						var instance = this;

						instance._clearedModal = true;

						instance.setValue('');

						instance.set('selectedLayout', instance.get('selectedLayoutPath')[0]);
					},

					_handleControlButtonsClick: function(event) {
						var instance = this;

						if (!instance.get('readOnly')) {
							var currentTarget = event.currentTarget;

							if (currentTarget.test('.select-button')) {
								instance._handleSelectButtonClick(event);
							}
							else {
								instance._handleClearButtonClick(event);
							}
						}
					},

					_handleListEntryClick: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var label = event.currentTarget.text();

						var layoutId = event.currentTarget.getData('layoutId');

						var groupId = Number(event.currentTarget.getData('groupId'));

						var privateLayout = A.DataType.Boolean.parse(event.currentTarget.getData('privateLayout'));

						if (event.target.hasClass('lfr-ddm-page-label')) {
							if (currentTarget.getData('nodeType') === 'root') {
								instance._cleanSelectedLayout();

								instance._currentParentLayoutId = layoutId;

								instance._showLoader(currentTarget);

								var selectedLayoutPath = instance.get('selectedLayoutPath');

								selectedLayoutPath.push(
									{
										groupId: groupId,
										label: label,
										layoutId: layoutId,
										privateLayout: privateLayout
									}
								);

								instance.set('selectedLayoutPath', selectedLayoutPath);

								var listNode = instance._modal.bodyNode.one('.lfr-ddm-pages-container');

								listNode.addClass('top-ended');

								instance._requestInitialLayouts(layoutId, groupId, privateLayout, instance._renderLayouts);
							}
							else if (currentTarget.getData('nodeType') === 'leaf') {
								var inputRadioNode = currentTarget.getElementsByTagName('input').first();

								inputRadioNode.attr('checked', 'true');

								instance.set(
									'selectedLayout',
									{
										groupId: groupId,
										label: label,
										layoutId: layoutId,
										path: instance.get('selectedLayoutPath'),
										privateLayout: privateLayout
									}
								);
							}
						}
						else if (event.target.hasClass('lfr-ddm-page-radio')) {
							instance.set(
								'selectedLayout',
								{
									groupId: groupId,
									label: label,
									layoutId: layoutId,
									path: instance.get('selectedLayoutPath'),
									privateLayout: privateLayout
								}
							);
						}
					},

					_handleModalScroll: function(event) {
						var instance = this;

						var listNode = event.currentTarget;

						var innerHeight = listNode.innerHeight();

						var scrollHeight = listNode.get('scrollHeight');
						var scrollTop = listNode.get('scrollTop');

						var delta = instance.get('delta');

						var groupIdNode = A.one('#' + this.get('portletNamespace') + 'groupId');

						var groupId = (groupIdNode && groupIdNode.getAttribute('value')) || themeDisplay.getScopeGroupId();

						var parentLayoutId = instance._currentParentLayoutId;

						var privateLayout = !!instance._navbar.one('.private').hasClass('active');

						var key = [parentLayoutId, groupId, privateLayout].join('-');

						if (!instance._isListNodeEmpty(key)) {
							var cache = instance._getCache(key);

							var end = cache.end;
							var start = cache.start;

							if (scrollTop === 0) {
								start -= delta;

								if (start < 0) {
									start = 0;
									end = cache.start;
								}

								if (end > start) {
									listNode.prepend(instance._loadingAnimationNode);

									instance._requestLayouts(parentLayoutId, groupId, privateLayout, start, end, A.rbind('_renderLayoutsFragment', instance, key, 'up'));
								}
							}
							else if (scrollHeight - (scrollTop + innerHeight) <= 1) {
								start = end;
								end = start + delta;

								if (start <= cache.total && start != cache.oldStart) {
									cache.oldStart = start;

									listNode.append(instance._loadingAnimationNode);

									instance._requestLayouts(parentLayoutId, groupId, privateLayout, start, end, A.rbind('_renderLayoutsFragment', instance, key));
								}
							}
						}
					},

					_handleNavbarClick: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						event.container.one('.active').removeClass('active');

						currentTarget.addClass('active');

						instance._currentParentLayoutId = 0;

						instance._cleanSelectedLayout();

						var privateLayout = currentTarget.test('.private');

						instance._resetBreadcrumb(privateLayout);
						instance._renderLayoutsList(privateLayout);
					},

					_handleSelectButtonClick: function() {
						var instance = this;

						instance._openLinkToPageModal();
					},

					_hideLoader: function() {
						var instance = this;

						instance._loadingAnimationNode.remove();
					},

					_initBreadcrumb: function() {
						var instance = this;

						var breadcrumbNode = A.Node.create(TPL_PAGES_BREADCRUMB);

						instance._modal.bodyNode.append(breadcrumbNode);

						breadcrumbNode.delegate('click', instance._handleBreadcrumbElementClick, '.lfr-ddm-breadcrumb-element', instance);
					},

					_initLayoutsList: function() {
						var instance = this;

						var bodyNode = instance._modal.bodyNode;

						if (!bodyNode.one('.lfr-ddm-pages-container')) {
							var navNode = A.Node.create(TPL_PAGES_CONTAINER);

							bodyNode.append(navNode);

							navNode.delegate('click', instance._handleListEntryClick, '.lfr-ddm-link', instance);
						}
					},

					_isListNodeEmpty: function(key) {
						var instance = this;

						var cache = instance._getCache(key);

						return !(cache && cache.layouts);
					},

					_openLinkToPageModal: function() {
						var instance = this;

						var value = instance.getParsedValue(instance.getValue());

						var privateLayout = !!value.privateLayout;

						var modal = instance._modal;

						if (!modal) {
							var config = instance._getModalConfig();

							modal = Liferay.Util.Window.getWindow(config);

							modal.render();

							instance._modal = modal;

							instance._initBreadcrumb();
							instance._initLayoutsList();

							instance._renderNavbar(privateLayout);
							instance._renderBreadcrumb(instance.get('selectedLayoutPath'));
							instance._renderLayoutsList(privateLayout);

							var listNode = modal.bodyNode.one('.lfr-ddm-pages-container');

							listNode.on('scroll', instance._handleModalScroll, instance);
						}
						else if (instance._clearedModal) {
							instance._navbar.one('.active').removeClass('active');

							var activeClass = privateLayout ? '.private' : '.public';

							instance._navbar.one(activeClass).addClass('active');
							instance._resetBreadcrumb(privateLayout);
							instance._renderLayoutsList(privateLayout);
							instance._clearedModal = false;
						}

						modal.show();

						instance._syncModalHeight();
					},

					_renderBreadcrumb: function(layoutsPath) {
						var instance = this;

						var bodyNode = instance._modal.bodyNode;

						var breadcrumbContainer = bodyNode.one('.lfr-ddm-breadcrumb');

						breadcrumbContainer.empty();

						var layoutsPathLenght = layoutsPath.length;

						for (var index = 0; index < layoutsPathLenght; index++) {
							var layoutPath = layoutsPath[index];

							instance._addBreadcrumbElement(layoutPath.label, layoutPath.layoutId, layoutPath.groupId, layoutPath.privateLayout);
						}
					},

					_renderLayouts: function(layouts) {
						var instance = this;

						var bodyNode = instance._modal.bodyNode;

						var listNode = bodyNode.one('.lfr-ddm-pages-container');

						var selectedLayout = instance.get('selectedLayout');

						listNode.empty();

						layouts.forEach(
							function(layout) {
								var selected = selectedLayout && layout.layoutId === selectedLayout.layoutId;

								instance._addListElement(layout, listNode, selected);
							}
						);

						instance._syncModalHeight();
					},

					_renderLayoutsFragment: function(layouts, key, direction) {
						var instance = this;

						var bodyNode = instance._modal.bodyNode;

						var index;

						var listNode = bodyNode.one('.lfr-ddm-pages-container');

						instance._hideLoader();

						var total = layouts.length;

						if (direction === 'up') {
							var cache = instance._getCache(key);

							listNode.toggleClass('top-ended', cache.start === 0);

							for (index = total - 1; index >= 0; index--) {
								instance._addListElement(layouts[index], listNode, false, true);
							}

							if (cache.start > 0 && listNode.get('scrollTop') === 0) {
								listNode.set('scrollTop', 60);
							}
						}
						else {
							for (index = 0; index < total; index++) {
								instance._addListElement(layouts[index], listNode, false);
							}
						}

						instance._syncModalHeight();
					},

					_renderLayoutsList: function(privateLayout) {
						var instance = this;

						var bodyNode = instance._modal.bodyNode;

						var listNode = bodyNode.one('.lfr-ddm-pages-container');

						instance._showLoader(listNode);

						instance._syncModalHeight();

						var selectedLayout = instance.get('selectedLayout');

						var groupIdNode = A.one('#' + this.get('portletNamespace') + 'groupId');

						var groupId = (groupIdNode && groupIdNode.getAttribute('value')) || themeDisplay.getScopeGroupId();

						if (selectedLayout && selectedLayout.layoutId) {
							instance._requestSiblingLayouts(
								groupId,
								privateLayout,
								function(layouts) {
									var key = [instance._currentParentLayoutId, groupId, privateLayout].join('-');

									var cache = instance._getCache(key);

									listNode.toggleClass('top-ended', cache.start === 0);

									instance._renderLayouts(layouts);

									if (cache.start > 0 && listNode.get('scrollTop') === 0) {
										listNode.set('scrollTop', 50);
									}

									instance._hideLoader();
								}
							);
						}
						else {
							listNode.addClass('top-ended');

							instance._requestInitialLayouts(0, groupId, privateLayout, instance._renderLayouts);
						}
					},

					_renderNavbar: function(privateLayout) {
						var instance = this;

						var navbar = instance._navbar;

						if (!navbar) {
							navbar = A.Node.create(
								Lang.sub(
									TPL_LAYOUTS_NAVBAR,
									{
										privateLayoutClass: privateLayout ? 'active' : '',
										publicLayoutClass: privateLayout ? '' : 'active'
									}
								)
							);

							navbar.delegate('click', instance._handleNavbarClick, 'li', instance);

							instance._navbar = navbar;

							navbar.insertBefore(navbar, instance._modal.bodyNode);
						}
					},

					_requestInitialLayouts: function(parentLayoutId, groupId, privateLayout, callback) {
						var instance = this;

						var end = instance.get('delta');

						var start = 0;

						instance._requestLayouts(parentLayoutId, groupId, privateLayout, start, end, callback);
					},

					_requestLayouts: function(parentLayoutId, groupId, privateLayout, start, end, callback) {
						var instance = this;

						var key = [parentLayoutId, groupId, privateLayout].join('-');

						var cache = instance._getCache(key);

						if (!cache || start <= cache.total) {
							if (instance._canLoadMore(key, start, end)) {
								A.io.request(
									themeDisplay.getPathMain() + '/portal/get_layouts',
									{
										after: {
											success: function() {
												var	response = JSON.parse(this.get('responseData'));

												var layouts = response && response.layouts;

												if (layouts) {
													instance._updateCache(key, layouts, start, end, response.total);

													callback.call(instance, layouts);
												}
											}
										},
										data: {
											cmd: 'get',
											end: end,
											expandParentLayouts: false,
											groupId: groupId,
											p_auth: Liferay.authToken,
											paginate: true,
											parentLayoutId: parentLayoutId,
											privateLayout: privateLayout,
											start: start
										}
									}
								);
							}
							else if (cache) {
								callback.call(instance, cache.layouts);
							}
						}
					},

					_requestSiblingLayouts: function(groupId, privateLayout, callback) {
						var instance = this;

						var cache;

						var path = instance.get('selectedLayoutPath');

						var lastIndex = path.length - 1;

						if (lastIndex >= 0) {
							var parentLayout = path[lastIndex];

							var key = [parentLayout.layoutId, parentLayout.groupId, parentLayout.privateLayout].join('-');

							cache = instance._getCache(key);
						}

						if (cache) {
							callback.call(instance, cache.layouts);
						}
						else {
							var selectedLayout = instance.get('selectedLayout');

							A.io.request(
								themeDisplay.getPathMain() + '/portal/get_layouts',
								{
									after: {
										failure: function() {
											var bodyNode = instance._modal.bodyNode;

											var listNode = bodyNode.one('.lfr-ddm-pages-container');

											listNode.addClass('top-ended');

											instance._requestInitialLayouts(0, groupId, privateLayout, instance._renderLayouts);
										},
										success: function() {
											var	response = JSON.parse(this.get('responseData'));

											var layouts = response && response.layouts;

											if (layouts) {
												var parentLayoutId = response.ancestorLayoutIds[0];

												var key = [parentLayoutId, groupId, privateLayout].join('-');

												var start = response.start;

												var end = start + layouts.length;

												instance._currentParentLayoutId = parentLayoutId;

												instance._setSelectedLayoutPath(groupId, privateLayout, response);

												instance._updateCache(key, layouts, start, end, response.total);

												callback.call(instance, layouts);
											}
										}
									},
									data: {
										cmd: 'getSiblingLayoutsJSON',
										expandParentLayouts: false,
										groupId: groupId,
										layoutId: selectedLayout.layoutId,
										max: instance.get('delta'),
										p_auth: Liferay.authToken,
										paginate: true,
										privateLayout: privateLayout
									}
								}
							);
						}
					},

					_resetBreadcrumb: function(privateLayout) {
						var instance = this;

						var selectedLayoutRoot = instance.get('selectedLayoutPath')[0];

						selectedLayoutRoot.privateLayout = privateLayout;

						instance.set('selectedLayoutPath', [selectedLayoutRoot]);
					},

					_setSelectedLayoutPath: function(groupId, privateLayout, response) {
						var instance = this;

						var ancestorLayoutIds = response.ancestorLayoutIds;

						if (ancestorLayoutIds) {
							var selectedLayoutPath = [instance.get('selectedLayoutPath')[0]];

							var ancestorLayoutNames = response.ancestorLayoutNames;

							for (var index = ancestorLayoutIds.length - 1; index >= 0; index--) {
								selectedLayoutPath.push(
									{
										groupId: groupId,
										label: ancestorLayoutNames[index],
										layoutId: ancestorLayoutIds[index],
										privateLayout: privateLayout
									}
								);
							}

							instance.set('selectedLayoutPath', selectedLayoutPath);
						}
					},

					_showLoader: function(node) {
						var instance = this;

						instance._loadingAnimationNode.appendTo(node);
					},

					_syncModalHeight: function() {
						var instance = this;

						var modal = instance._modal;

						var bodyNode = modal.bodyNode;

						modal.fillHeight(bodyNode);

						bodyNode.set('offsetHeight', Lang.toInt(bodyNode.get('offsetHeight')) - Lang.toInt(instance._navbar.get('offsetHeight')));
					},

					_updateCache: function(key, layouts, start, end, total) {
						var instance = this;

						var cache = instance._cache[key];

						if (!cache) {
							var path = instance.get('selectedLayoutPath');

							cache = {
								end: end,
								layouts: layouts,
								oldStart: 0,
								path: path.slice(),
								start: start,
								total: total
							};

							instance._cache[key] = cache;
						}
						else {
							var cachedLayouts = cache.layouts || [];

							if (cache.start > start) {
								cachedLayouts = layouts.concat(cachedLayouts);

								cache.start = start;
							}

							if (cache.end < end) {
								cachedLayouts = cachedLayouts.concat(layouts);

								cache.end = end;
							}

							cache.layouts = cachedLayouts;
						}
					}
				}
			}
		);

		FieldTypes['ddm-link-to-page'] = LinkToPageField;

		FieldTypes.field = Field;

		var FieldsetField = A.Component.create(
			{
				EXTENDS: Field,

				prototype: {
					getFieldNodes: function() {
						var instance = this;

						return instance.get('container').all('> fieldset > div > .field-wrapper');
					}
				}
			}
		);

		FieldTypes.fieldset = FieldsetField;

		var ImageField = A.Component.create(
			{
				ATTRS: {
					acceptedFileFormats: {
						value: ['image/gif', 'image/jpeg', 'image/jpg', 'image/png']
					}
				},

				EXTENDS: DocumentLibraryField,

				prototype: {
					syncUI: function() {
						var instance = this;

						var parsedValue = instance.getParsedValue(instance.getValue());

						var notEmpty = instance.isNotEmpty(parsedValue);

						var altNode = A.one('#' + instance.getInputName() + 'Alt');

						altNode.attr('disabled', !notEmpty);

						var titleNode = A.one('#' + instance.getInputName() + 'Title');

						if (notEmpty) {
							altNode.val(parsedValue.alt || '');
							titleNode.val(parsedValue.title || '');
						}
						else {
							altNode.val('');
							titleNode.val('');
						}

						var clearButtonNode = A.one('#' + instance.getInputName() + 'ClearButton');

						clearButtonNode.toggle(notEmpty);

						var previewButtonNode = A.one('#' + instance.getInputName() + 'PreviewButton');

						previewButtonNode.toggle(notEmpty);
					},

					getDocumentLibrarySelectorURL: function() {
						var instance = this;

						var form = instance.getForm();

						var imageSelectorURL = form.get('imageSelectorURL');

						var retVal = instance.getDocumentLibraryURL('com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion,com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion');

						if (imageSelectorURL) {
							retVal = imageSelectorURL;
						}

						return retVal;
					},

					getDocumentLibraryURL: function(criteria) {
						var instance = this;

						var container = instance.get('container');

						var parsedValue = instance.getParsedValue(ImageField.superclass.getValue.apply(instance, arguments));

						var portletNamespace = instance.get('portletNamespace');

						var portletURL = Liferay.PortletURL.createURL(themeDisplay.getLayoutRelativeControlPanelURL());

						portletURL.setParameter('criteria', criteria);
						portletURL.setParameter('itemSelectedEventName', portletNamespace + 'selectDocumentLibrary');
						portletURL.setParameter('p_p_auth', container.getData('itemSelectorAuthToken'));

						var journalCriterionJSON = {
							desiredItemSelectorReturnTypes: 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
							resourcePrimKey: parsedValue.resourcePrimKey
						};

						portletURL.setParameter('0_json', JSON.stringify(journalCriterionJSON));

						var imageCriterionJSON = {
							desiredItemSelectorReturnTypes: 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType'
						};

						portletURL.setParameter('1_json', JSON.stringify(imageCriterionJSON));

						portletURL.setPortletId(Liferay.PortletKeys.ITEM_SELECTOR);
						portletURL.setPortletMode('view');
						portletURL.setWindowState('pop_up');

						return portletURL.toString();
					},

					getValue: function() {
						var instance = this;

						var value;

						var parsedValue = instance.getParsedValue(ImageField.superclass.getValue.apply(instance, arguments));

						if (instance.isNotEmpty(parsedValue)) {
							var altNode = A.one('#' + instance.getInputName() + 'Alt');

							parsedValue.alt = altNode.val();

							value = JSON.stringify(parsedValue);
						}
						else {
							value = '';
						}

						return value;
					},

					isNotEmpty: function(value) {
						var instance = this;

						var parsedValue = instance.getParsedValue(value);

						return parsedValue.hasOwnProperty('data') && parsedValue.data !== '' || parsedValue.hasOwnProperty('uuid');
					},

					setValue: function(value) {
						var instance = this;

						var parsedValue = instance.getParsedValue(value);

						if (instance.isNotEmpty(parsedValue)) {
							if (!parsedValue.name && parsedValue.title) {
								parsedValue.name = parsedValue.title;
							}

							var altNode = A.one('#' + instance.getInputName() + 'Alt');

							altNode.val(parsedValue.alt);

							value = JSON.stringify(parsedValue);
						}
						else {
							value = '';
						}

						DocumentLibraryField.superclass.setValue.call(instance, value);

						instance.syncUI();
					},

					_getImagePreviewURL: function() {
						var instance = this;

						var imagePreviewURL;

						var value = instance.getParsedValue(instance.getValue());

						if (value.data) {
							imagePreviewURL = themeDisplay.getPathContext() + value.data;
						}
						else if (value.uuid) {
							imagePreviewURL = [
								themeDisplay.getPathContext(),
								'documents',
								value.groupId,
								value.uuid
							].join('/');
						}

						return imagePreviewURL;
					},

					_handleButtonsClick: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						if (currentTarget.test('.preview-button')) {
							instance._handlePreviewButtonClick(event);
						}

						ImageField.superclass._handleButtonsClick.apply(instance, arguments);
					},

					_handlePreviewButtonClick: function(event) {
						var instance = this;

						if (!instance.viewer) {
							instance.viewer = new A.ImageViewer(
								{
									caption: 'alt',
									links: '#' + instance.getInputName() + 'PreviewContainer a',
									preloadAllImages: false,
									zIndex: Liferay.zIndex.OVERLAY
								}
							).render();
						}

						var imagePreviewURL = instance._getImagePreviewURL();

						var previewImageNode = A.one('#' + instance.getInputName() + 'PreviewContainer img');
						var previewLinkNode = A.one('#' + instance.getInputName() + 'PreviewContainer a');

						previewLinkNode.attr('href', imagePreviewURL);
						previewImageNode.attr('src', imagePreviewURL);

						instance.viewer.set('currentIndex', 0);
						instance.viewer.set('links', previewLinkNode);

						instance.viewer.show();
					}
				}
			}
		);

		FieldTypes['ddm-image'] = ImageField;

		var GeolocationField = A.Component.create(
			{
				EXTENDS: Field,

				prototype: {
					initializer: function() {
						var instance = this;

						Liferay.MapBase.get(
							instance.getInputName(),
							function(map) {
								map.on('positionChange', instance.onPositionChange, instance);
							}
						);
					},

					onPositionChange: function(event) {
						var instance = this;

						var inputName = instance.getInputName();

						var location = event.newVal.location;

						instance.setValue(
							JSON.stringify(
								{
									latitude: location.lat,
									longitude: location.lng
								}
							)
						);

						var locationNode = A.one('#' + inputName + 'Location');

						locationNode.html(event.newVal.address);
					}
				}
			}
		);

		FieldTypes['ddm-geolocation'] = GeolocationField;

		var TextHTMLField = A.Component.create(
			{
				EXTENDS: Field,

				prototype: {
					initializer: function() {
						var instance = this;

						instance.readOnlyLabel = A.Node.create('<label class="control-label hide"></label>');
						instance.readOnlyText = A.Node.create('<div class="hide"></div>');

						instance.after(
							{
								'render': instance._afterRenderTextHTMLField
							}
						);
					},

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

						var editorComponentName = instance.getInputName() + 'Editor';

						Liferay.componentReady(editorComponentName).then(
							function(editor) {
								if (isNode(editor)) {
									TextHTMLField.superclass.setValue.apply(instance, arguments);
								}
								else {
									var localizationMap = instance.get('localizationMap');

									if (value === localizationMap[instance.get('displayLocale')]) {
										editor.setHTML(value);
									}
								}
							}
						);
					},

					syncReadOnlyUI: function() {
						var instance = this;

						instance.readOnlyLabel.html(instance.getLabelNode().getHTML());
						instance.readOnlyText.html('<p>' + instance.getValue() + '</p>');

						var readOnly = instance.getReadOnly();

						instance.readOnlyLabel.toggle(readOnly);
						instance.readOnlyText.toggle(readOnly);

						instance.get('container').toggle(!readOnly);
					},

					_afterRenderTextHTMLField: function() {
						var instance = this;

						var container = instance.get('container');

						container.placeAfter(instance.readOnlyText);
						container.placeAfter(instance.readOnlyLabel);
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

					getRadioNodes: function() {
						var instance = this;

						var container = instance.get('container');

						return container.all('[name=' + instance.getInputName() + ']');
					},

					getValue: function() {
						var instance = this;

						var value = '';

						if (instance.getInputNode()) {
							value = RadioField.superclass.getValue.apply(instance, arguments);
						}

						return value;
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
									item.html(A.Escape.html(optionLabel));

									item.prepend(inputNode);
								}
							}
						);

						RadioField.superclass.setLabel.apply(instance, arguments);
					},

					setValue: function(value) {
						var instance = this;

						var radioNodes = instance.getRadioNodes();

						radioNodes.set('checked', false);

						radioNodes.filter('[value=' + value + ']').set('checked', true);
					},

					syncReadOnlyUI: function() {
						var instance = this;

						var readOnly = instance.getReadOnly();

						var radioNodes = instance.getRadioNodes();

						radioNodes.attr('disabled', readOnly);
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

					getValue: function() {
						var instance = this;

						var selectedItems = instance.getInputNode().all('option:selected');

						var value;

						if (selectedItems._nodes && selectedItems._nodes.length > 0) {
							value = selectedItems.val();
						}
						else {
							value = [];
						}

						return value;
					},

					setLabel: function() {
						var instance = this;

						var options = instance._getOptions();

						instance.getInputNode().all('option').each(
							function(item, index) {
								var optionDefinition = options[index];

								var optionLabel = optionDefinition.label[instance.get('displayLocale')];

								if (Lang.isValue(optionLabel)) {
									item.html(A.Escape.html(optionLabel));
								}
							}
						);

						Field.prototype.setLabel.apply(instance, arguments);
					},

					setValue: function(value) {
						var instance = this;

						if (Lang.isString(value)) {
							value = JSON.parse(value);
						}

						instance.getInputNode().all('option').each(
							function(item, index) {
								item.set('selected', value.indexOf(item.val()) > -1);
							}
						);
					},

					_getOptions: function() {
						var instance = this;

						var fieldDefinition = instance.getFieldDefinition();

						var fieldOptions = fieldDefinition.options;

						fieldOptions.unshift(instance._getPlaceholderOption());

						return fieldOptions;
					},

					_getPlaceholderOption: function() {
						var instance = this;
						var label = {};

						label[instance.get('displayLocale')] = '';

						return {
							label: label,
							value: ''
						};
					}
				}
			}
		);

		FieldTypes.select = SelectField;

		var SeparatorField = A.Component.create(
			{
				EXTENDS: Field,

				prototype: {
					getValue: function() {
						return '';
					}
				}
			}
		);

		FieldTypes['ddm-separator'] = SeparatorField;

		var Form = A.Component.create(
			{
				ATTRS: {
					availableLanguageIds: {
						value: []
					},

					ddmFormValuesInput: {
						setter: A.one
					},

					defaultEditLocale: {
					},

					documentLibrarySelectorURL: {
					},

					formNode: {
						valueFn: '_valueFormNode'
					},

					imageSelectorURL: {
					},

					liferayForm: {
						valueFn: '_valueLiferayForm'
					},

					repeatable: {
						validator: Lang.isBoolean,
						value: false
					},

					requestedLocale: {
						validator: Lang.isString
					},

					synchronousFormSubmission: {
						validator: Lang.isBoolean,
						value: true
					}
				},

				AUGMENTS: [DDMPortletSupport, FieldsSupport],

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.eventHandlers = [];
						instance.newRepeatableInstances = [];
						instance.repeatableInstances = {};

						instance.bindUI();
						instance.renderUI();
					},

					renderUI: function() {
						var instance = this;

						AArray.invoke(instance.get('fields'), 'renderUI');
					},

					bindUI: function() {
						var instance = this;

						var formNode = instance.get('formNode');

						if (formNode) {
							instance.eventHandlers.push(
								instance.after('liferay-ddm-field:render', instance._afterRenderField, instance),
								instance.after(
									['liferay-ddm-field:repeat', 'liferay-ddm-field:remove'],
									instance._afterUpdateRepeatableFields,
									instance
								),
								Liferay.after('form:registered', instance._afterFormRegistered, instance),
								Liferay.after('inputLocalized:defaultLocaleChanged', A.bind('_onDefaultLocaleChanged', instance))
							);

							if (instance.get('synchronousFormSubmission')) {
								instance.eventHandlers.push(
									formNode.on('submit', instance._onSubmitForm, instance),
									Liferay.on('submitForm', instance._onLiferaySubmitForm, instance)
								);
							}
						}
					},

					_onDefaultLocaleChanged: function(event) {
						var instance = this;

						var definition = instance.get('definition');

						definition.defaultLanguageId = event.item.getAttribute('data-value');

						instance.set('definition', definition);
					},

					destructor: function() {
						var instance = this;

						AArray.invoke(instance.eventHandlers, 'detach');
						AArray.invoke(instance.get('fields'), 'destroy');

						instance.get('container').remove();

						instance.eventHandlers = null;

						A.each(
							instance.repeatableInstances,
							function(item) {
								item.destroy();
							}
						);

						instance.repeatableInstances = null;
					},

					addAvailableLanguageIds: function(availableLanguageIds) {
						var instance = this;

						var currentAvailableLanguageIds = instance.get('availableLanguageIds');

						availableLanguageIds.forEach(
							function(item) {
								if (currentAvailableLanguageIds.indexOf(item) == -1) {
									currentAvailableLanguageIds.push(item);
								}
							}
						);
					},

					finalizeRepeatableFieldLocalizations: function() {
						var instance = this;

						var defaultLocale = instance.getDefaultLocale();

						Object.keys(instance.newRepeatableInstances).forEach(
							function (x) {
								var field = instance.newRepeatableInstances[x];

								if (!field.get('localizable')) {
									return;
								}

								var currentLocale = field.get('displayLocale');
								var originalField = field.originalField;

								var newFieldLocalizations = field.get('localizationMap');
								var totalLocalizations = originalField.get('localizationMap');

								for (var localization in totalLocalizations) {
									if (localization === currentLocale) {
										return;
									}

									if (!newFieldLocalizations[localization]) {
										var localizationValue = '';

										if (newFieldLocalizations[defaultLocale]) {
											localizationValue = newFieldLocalizations[defaultLocale];
										}
										else if (defaultLocale === field.get('displayLocale') && field.getValue()) {
											localizationValue = field.getValue();
										}

										newFieldLocalizations[localization] = localizationValue;
									}
								}

								field.set('localizationMap', newFieldLocalizations);
							}
						);
					},

					moveField: function(parentField, oldIndex, newIndex) {
						var instance = this;

						var fields = parentField.get('fields');

						fields.splice(newIndex, 0, fields.splice(oldIndex, 1)[0]);
					},

					registerRepeatable: function(field) {
						var instance = this;

						var fieldName = field.get('name');

						var fieldContainer = field.get('container');

						var parentField = field.get('parent');

						var parentNode = fieldContainer.get('parentNode');

						var treeName = fieldName + '_' + parentField.get('instanceId');

						var repeatableInstance = instance.repeatableInstances[treeName];

						if (!repeatableInstance) {
							var ddPlugins = [];

							if (Liferay.Util.getTop() === A.config.win) {
								ddPlugins.push(
									{
										fn: A.Plugin.DDWinScroll
									}
								);
							}
							else {
								ddPlugins.push(
									{
										cfg: {
											constrain: '.lfr-ddm-container'
										},
										fn: A.Plugin.DDConstrained
									},
									{
										cfg: {
											horizontal: false,
											node: '.lfr-ddm-container'
										},
										fn: A.Plugin.DDNodeScroll
									}
								);
							}

							repeatableInstance = new Liferay.DDM.RepeatableSortableList(
								{
									dd: {
										plugins: ddPlugins
									},
									dropOn: '#' + parentNode.attr('id'),
									helper: A.Node.create(TPL_REPEATABLE_HELPER),
									nodes: '#' + parentNode.attr('id') + ' [data-fieldName=' + fieldName + ']',
									placeholder: A.Node.create('<div class="form-builder-placeholder"></div>'),
									sortCondition: function(event) {
										var dropNode = event.drop.get('node');

										var dropNodeAncestor = dropNode.ancestor();

										var dragNode = event.drag.get('node');

										var dragNodeAncestor = dragNode.ancestor();

										var retVal = dropNode.getData('fieldName') === fieldName;

										if (dropNodeAncestor.get('id') !== dragNodeAncestor.get('id')) {
											retVal = false;
										}

										return retVal;
									}
								}
							);

							repeatableInstance.after('drag:align', A.bind(instance._afterRepeatableDragAlign, instance));

							repeatableInstance.after('drag:end', A.rbind(instance._afterRepeatableDragEnd, instance, parentField));

							instance.repeatableInstances[treeName] = repeatableInstance;
						}
						else {
							repeatableInstance.add(fieldContainer);
						}

						if (fieldContainer.hasAttribute('draggable')) {
							fieldContainer.removeAttribute('draggable');
						}

						var drag = A.DD.DDM.getDrag(fieldContainer);

						drag.addInvalid('.alloy-editor');
						drag.addInvalid('.cke');
						drag.addInvalid('.lfr-source-editor');
					},

					toJSON: function() {
						var instance = this;

						var definition = instance.get('definition');

						var fieldValues = AArray.invoke(instance.get('fields'), 'toJSON');

						return {
							availableLanguageIds: instance.get('availableLanguageIds'),
							defaultLanguageId: definition.defaultLanguageId || themeDisplay.getDefaultLanguageId(),
							fieldValues: fieldValues
						};
					},

					unregisterRepeatable: function(field) {
						var instance = this;

						field.get('container').dd.destroy();
					},

					updateDDMFormInputValue: function() {
						var instance = this;

						var ddmFormValuesInput = instance.get('ddmFormValuesInput');

						ddmFormValuesInput.val(JSON.stringify(instance.toJSON()));
					},

					_afterFormRegistered: function(event) {
						var instance = this;

						var formNode = instance.get('formNode');

						if (event.formName === formNode.attr('name')) {
							instance.set('liferayForm', event.form);
						}
					},

					_afterRenderField: function(event) {
						var instance = this;

						var field = event.field;

						if (field.get('repeatable')) {
							instance.registerRepeatable(field);
						}
					},

					_afterRepeatableDragAlign: function() {
						var DDM = A.DD.DDM;

						DDM.syncActiveShims();
						DDM._dropMove();
					},

					_afterRepeatableDragEnd: function(event, parentField) {
						var instance = this;

						var node = event.target.get('node');

						var oldIndex = -1;

						parentField.get('fields').some(
							function(item, index) {
								oldIndex = index;

								return item.get('instanceId') === instance.extractInstanceId(node);
							}
						);

						var newIndex = node.ancestor().all('> .field-wrapper').indexOf(node);

						instance.moveField(parentField, oldIndex, newIndex);
					},

					_afterUpdateRepeatableFields: function(event) {
						var instance = this;

						var field = event.field;

						var liferayForm = instance.get('liferayForm');

						if (liferayForm) {
							var validatorRules = liferayForm.formValidator.get('rules');

							if (event.type === 'liferay-ddm-field:repeat') {
								var originalField = event.originalField;

								var originalFieldInputName = originalField.getInputName();

								var originalFieldRules = validatorRules[originalFieldInputName];

								if (originalFieldRules) {
									validatorRules[field.getInputName()] = originalFieldRules;
								}
							}
							else if (event.type === 'liferay-ddm-field:remove') {
								delete validatorRules[field.getInputName()];

								var inputNode = field.getInputNode();

								if (inputNode) {
									liferayForm.formValidator.resetField(inputNode);
								}

								if (field.get('repeatable')) {
									instance.unregisterRepeatable(field);
								}
							}

							liferayForm.formValidator.set('rules', validatorRules);
						}
					},

					_onLiferaySubmitForm: function(event) {
						var instance = this;

						var formNode = instance.get('formNode');

						if (event.form.attr('name') === formNode.attr('name')) {
							instance.updateDDMFormInputValue();
						}
					},

					_onSubmitForm: function(event) {
						var instance = this;

						instance.finalizeRepeatableFieldLocalizations();

						instance.updateDDMFormInputValue();
					},

					_valueFormNode: function() {
						var instance = this;

						var container = instance.get('container');

						return container.ancestor('form', true);
					},

					_valueLiferayForm: function() {
						var instance = this;

						var formNode = instance.get('formNode');

						var formName = null;

						if (formNode) {
							formName = formNode.attr('name');
						}

						return Liferay.Form.get(formName);
					}
				}
			}
		);

		Liferay.DDM.RepeatableSortableList = A.Component.create(
			{
				EXTENDS: A.SortableList,

				prototype: {
					_createDrag: function(node) {
						var instance = this;

						var helper = instance.get('helper');

						if (!A.DD.DDM.getDrag(node)) {
							var dragOptions = {
								bubbleTargets: instance,
								node: node,
								target: true
							};

							var proxyOptions = instance.get('proxy');

							if (helper) {
								proxyOptions.borderStyle = null;
							}

							new A.DD.Drag(
								A.mix(dragOptions, instance.get('dd'))
							).plug(A.Plugin.DDProxy, proxyOptions);
						}
					}
				}
			}
		);

		Liferay.DDM.Form = Form;
	},
	'',
	{
		requires: ['aui-base', 'aui-color-picker-popover', 'aui-datatable', 'aui-datatype', 'aui-image-viewer', 'aui-io-request', 'aui-parse-content', 'aui-set', 'aui-sortable-list', 'json', 'liferay-form', 'liferay-item-selector-dialog', 'liferay-layouts-tree', 'liferay-layouts-tree-radio', 'liferay-layouts-tree-selectable', 'liferay-map-base', 'liferay-notice', 'liferay-portlet-url', 'liferay-translation-manager']
	}
);