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
	'liferay-ddm-form',
	A => {
		var AArray = A.Array;

		var AObject = A.Object;

		var DateMath = A.DataType.DateMath;

		var Lang = A.Lang;

		var INSTANCE_ID_PREFIX = '_INSTANCE_';

		var SELECTOR_REPEAT_BUTTONS =
			'.lfr-ddm-repeatable-add-button, .lfr-ddm-repeatable-delete-button';

		var TPL_ICON_CARET =
			'<span class="collapse-icon-closed"><span class="icon-caret-right"></span></span>';

		var TPL_LAYOUTS_NAVBAR =
			'<nav class="navbar navbar-default">' +
			'<div class="collapse navbar-collapse">' +
			'<ul class="nav navbar-nav">' +
			'<li class="public {publicLayoutClass}"><a href="javascript:;">' +
			Liferay.Language.get('public-pages') +
			'</a></li>' +
			'<li class="private {privateLayoutClass}"><a href="javascript:;">' +
			Liferay.Language.get('private-pages') +
			'</a></li>' +
			'</ul>' +
			'</div>' +
			'</nav>';

		var TPL_LOADER = '<span class="linear loading-icon"></span>';

		var TPL_PAGE =
			'<li class="lfr-ddm-link" data-groupId="{groupId}" data-layoutId="{layoutId}" data-nodeType="{nodeType}" data-privateLayout="{privateLayout}">' +
			'<input class="lfr-ddm-page-radio" {checked} name="lfr-ddm-page" type="radio" />' +
			'<a class="collapsed collapse-icon lfr-ddm-page-label" href="javascript:;">{pageTitle}{icon}</a>' +
			'</li>';

		var TPL_PAGES_BREADCRUMB =
			'<ul class="breadcrumb lfr-ddm-breadcrumb"></ul>';

		var TPL_PAGES_BREADCRUMB_ELEMENT =
			'<li class="lfr-ddm-breadcrumb-element" data-groupId={groupId} data-layoutId={layoutId} data-privateLayout={privateLayout}>' +
			'<a title="{label}">{label}</a>' +
			'</li>';

		var TPL_PAGES_CONTAINER =
			'<ul class="lfr-ddm-pages-container nav vertical-scrolling"></ul>';

		var TPL_REPEATABLE_ADD =
			'<a class="lfr-ddm-repeatable-add-button" href="javascript:;">' +
			Liferay.Util.getLexiconIconTpl('plus') +
			'</a>';

		var TPL_REPEATABLE_ICON =
			'<div class="lfr-ddm-repeatable-drag-icon">' +
			Liferay.Util.getLexiconIconTpl('drag') +
			'</div>';

		var TPL_REPEATABLE_DELETE =
			'<a class="hide lfr-ddm-repeatable-delete-button" href="javascript:;">' +
			Liferay.Util.getLexiconIconTpl('hr') +
			'</a>';

		var TPL_REPEATABLE_HELPER =
			'<div class="lfr-ddm-repeatable-helper"></div>';

		var TPL_REQUIRED_MARK =
			'<span class="text-warning">' +
			Liferay.Util.getLexiconIconTpl('asterisk') +
			'<span class="hide-accessible">' +
			Liferay.Language.get('required') +
			'</span></span>';

		var FieldTypes = Liferay.namespace('DDM.FieldTypes');

		var getFieldClass = function(type) {
			return FieldTypes[type] || FieldTypes.field;
		};

		var isNode = function(node) {
			return node && (node._node || node.nodeType);
		};

		var DDMPortletSupport = function() {};

		DDMPortletSupport.ATTRS = {
			doAsGroupId: {},

			fieldsNamespace: {},

			p_l_id: {},

			portletNamespace: {}
		};

		var FieldsSupport = function() {};

		FieldsSupport.ATTRS = {
			container: {
				setter: A.one
			},

			definition: {},

			displayLocale: {
				valueFn: '_valueDisplayLocale'
			},

			fields: {
				valueFn: '_valueFields'
			},

			mode: {},

			values: {
				value: {}
			}
		};

		FieldsSupport.prototype = {
			_getField(fieldNode) {
				var instance = this;

				var displayLocale = instance.get('displayLocale');

				var fieldInstanceId = instance.extractInstanceId(fieldNode);

				var fieldName = fieldNode.getData('fieldName');

				var definition = instance.get('definition');

				var fieldDefinition = instance.getFieldInfo(
					definition,
					'name',
					fieldName
				);

				var FieldClass = getFieldClass(fieldDefinition.type);

				var field = new FieldClass(
					A.merge(
						instance.getAttrs(
							AObject.keys(DDMPortletSupport.ATTRS)
						),
						{
							container: fieldNode,
							dataType: fieldDefinition.dataType,
							definition,
							displayLocale,
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

			_getTemplate(callback) {
				var instance = this;

				var key =
					Liferay.Util.getPortletNamespace(
						Liferay.PortletKeys.DYNAMIC_DATA_MAPPING
					) + 'definition';

				const data = new URLSearchParams();
				data.append(key, JSON.stringify(instance.get('definition')));

				Liferay.Util.fetch(instance._getTemplateResourceURL(), {
					body: data,
					method: 'POST'
				})
					.then(response => {
						return response.text();
					})
					.then(response => {
						if (callback) {
							callback.call(instance, response);
						}
					});
			},

			_getTemplateResourceURL() {
				var instance = this;

				var container = instance.get('container');

				var templateResourceParameters = {
					doAsGroupId: instance.get('doAsGroupId'),
					fieldName: instance.get('name'),
					mode: instance.get('mode'),
					namespace: instance.get('fieldsNamespace'),
					p_l_id: instance.get('p_l_id'),
					p_p_auth: container.getData('ddmAuthToken'),
					p_p_id: Liferay.PortletKeys.DYNAMIC_DATA_MAPPING,
					p_p_isolated: true,
					p_p_resource_id: 'renderStructureField',
					p_p_state: 'pop_up',
					portletNamespace: instance.get('portletNamespace'),
					readOnly: instance.get('readOnly')
				};

				var templateResourceURL = Liferay.Util.PortletURL.createResourceURL(
					themeDisplay.getURLControlPanel(),
					templateResourceParameters
				);

				return templateResourceURL.toString();
			},

			_valueDisplayLocale() {
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

			_valueFields() {
				var instance = this;

				var fields = [];

				instance.getFieldNodes().each(item => {
					fields.push(instance._getField(item));
				});

				return fields;
			},

			eachParent(fn) {
				var instance = this;

				var parent = instance.get('parent');

				while (parent !== undefined) {
					fn.call(instance, parent);

					parent = parent.get('parent');
				}
			},

			extractInstanceId(fieldNode) {
				var fieldInstanceId = fieldNode.getData('fieldNamespace');

				return fieldInstanceId.replace(INSTANCE_ID_PREFIX, '');
			},

			getDefaultLocale() {
				var instance = this;

				var defaultLocale = themeDisplay.getDefaultLanguageId();

				var definition = instance.get('definition');

				if (definition) {
					defaultLocale = definition.defaultLanguageId;
				}

				return defaultLocale;
			},

			getFieldInfo(tree, key, value) {
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
					} else {
						var children =
							next.fields ||
							next.nestedFields ||
							next.fieldValues ||
							next.nestedFieldValues;

						if (children) {
							children.forEach(addToQueue);
						}
					}
				}

				return fieldInfo;
			},

			getFieldNodes() {
				var instance = this;

				return instance.get('container').all('> .field-wrapper');
			},

			getForm() {
				var instance = this;

				var root;

				instance.eachParent(parent => {
					root = parent;
				});

				return root || instance;
			},

			getReadOnly() {
				var instance = this;

				var retVal = false;

				if (instance.get('readOnly')) {
					retVal = true;
				} else {
					var form = instance.getForm();

					if (
						!instance.get('localizable') &&
						form.getDefaultLocale() != instance.get('displayLocale')
					) {
						retVal = true;
					}
				}

				return retVal;
			}
		};

		var Field = A.Component.create({
			ATTRS: {
				container: {
					setter: A.one
				},

				dataType: {},

				definition: {
					validator: Lang.isObject
				},

				formNode: {
					valueFn: '_valueFormNode'
				},

				instanceId: {},

				liferayForm: {
					valueFn: '_valueLiferayForm'
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

				node: {},

				parent: {},

				readOnly: {},

				repeatable: {
					getter: '_getRepeatable',
					readOnly: true
				}
			},

			AUGMENTS: [DDMPortletSupport, FieldsSupport],

			EXTENDS: A.Base,

			NAME: 'liferay-ddm-field',

			prototype: {
				_addFieldValidation(newField, originalField) {
					var instance = this;

					instance.fire('liferay-ddm-field:repeat', {
						field: newField,
						originalField
					});

					newField.get('fields').forEach(item => {
						var name = item.get('name');

						var originalChildField = originalField.getFirstFieldByName(
							name
						);

						if (originalChildField) {
							instance._addFieldValidation(
								item,
								originalChildField
							);
						}
					});
				},

				_addTip(labelNode, tipNode) {
					if (tipNode) {
						var instance = this;

						var defaultLocale = instance.getDefaultLocale();
						var fieldDefinition = instance.getFieldDefinition();

						var tipsMap = fieldDefinition.tip;

						if (Lang.isObject(tipsMap)) {
							var tip =
								tipsMap[instance.get('displayLocale')] ||
								tipsMap[defaultLocale];

							tipNode.attr('title', tip);
						}

						labelNode.append(tipNode);
					}
				},

				_afterFormRegistered(event) {
					var instance = this;

					var formNode = instance.get('formNode');

					if (event.formName === formNode.attr('name')) {
						instance.set('liferayForm', event.form);
					}
				},

				_getLocalizable() {
					var instance = this;

					return instance.getFieldDefinition().localizable === true;
				},

				_getRepeatable() {
					var instance = this;

					return instance.getFieldDefinition().repeatable === true;
				},

				_handleToolbarClick(event) {
					var instance = this;

					var currentTarget = event.currentTarget;

					instance.ddmRepeatableButton = currentTarget;

					if (
						currentTarget.hasClass('lfr-ddm-repeatable-add-button')
					) {
						instance.repeat();
					} else if (
						currentTarget.hasClass(
							'lfr-ddm-repeatable-delete-button'
						)
					) {
						instance.remove();

						instance.syncRepeatablelUI();
					}

					event.stopPropagation();
				},

				_onLocaleChanged(event) {
					var instance = this;

					var currentLocale = instance.get('displayLocale');
					var displayLocale = event.item.getAttribute('data-value');

					instance.updateLocalizationMap(currentLocale);

					instance.set('displayLocale', displayLocale);

					instance.syncLabel(displayLocale);
					instance.syncValueUI();
					instance.syncReadOnlyUI();
				},

				_removeFieldValidation(field) {
					var instance = this;

					field.get('fields').forEach(item => {
						instance._removeFieldValidation(item);
					});

					instance.fire('liferay-ddm-field:remove', {
						field
					});
				},

				_valueFormNode() {
					var instance = this;

					var container = instance.get('container');

					return container.ancestor('form', true);
				},

				_valueLiferayForm() {
					var instance = this;

					var formNode = instance.get('formNode');

					var formName = null;

					if (formNode) {
						formName = formNode.attr('name');
					}

					return Liferay.Form.get(formName);
				},

				_valueLocalizationMap() {
					var instance = this;

					var instanceId = instance.get('instanceId');

					var values = instance.get('values');

					var fieldValue = instance.getFieldInfo(
						values,
						'instanceId',
						instanceId
					);

					var localizationMap = {};

					if (fieldValue && fieldValue.value) {
						localizationMap = fieldValue.value;
					}

					return localizationMap;
				},

				bindUI() {
					var instance = this;

					instance.eventHandlers.push(
						Liferay.on(
							'inputLocalized:localeChanged',
							instance._onLocaleChanged,
							instance
						)
					);

					var formNode = instance.get('formNode');

					if (formNode) {
						instance.eventHandlers.push(
							Liferay.after(
								'form:registered',
								instance._afterFormRegistered,
								instance
							)
						);
					}
				},

				createField(fieldTemplate) {
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

				destructor() {
					var instance = this;

					AArray.invoke(instance.eventHandlers, 'detach');

					AArray.invoke(instance.get('fields'), 'destroy');

					instance.eventHandlers = null;

					instance.get('container').remove();
				},

				getDefaultLocalization(locale) {
					var instance = this;

					var localizationMap = instance.get('localizationMap');

					if (Lang.isUndefined(localizationMap[locale])) {
						var predefinedValue = instance.getPredefinedValueByLocale(
							locale
						);

						if (predefinedValue) {
							return predefinedValue;
						}

						var defaultLocale = instance.getDefaultLocale();

						if (defaultLocale && localizationMap[defaultLocale]) {
							return localizationMap[defaultLocale];
						}

						return '';
					}

					return localizationMap[locale];
				},

				getFieldByNameInFieldDefinition(name) {
					var instance = this;

					var definition = instance.get('definition');

					var fields = [];

					if (definition && definition.fields) {
						fields = definition.fields;
					}

					return AArray.find(fields, item => {
						return item.name === name;
					});
				},

				getFieldDefinition() {
					var instance = this;

					var definition = instance.get('definition');

					var name = instance.get('name');

					return instance.getFieldInfo(definition, 'name', name);
				},

				getFirstFieldByName(name) {
					var instance = this;

					return AArray.find(instance.get('fields'), item => {
						return item.get('name') === name;
					});
				},

				getInputName() {
					var instance = this;

					var fieldsNamespace = instance.get('fieldsNamespace');
					var portletNamespace = instance.get('portletNamespace');

					var prefix = [portletNamespace];

					if (fieldsNamespace) {
						prefix.push(fieldsNamespace);
					}

					return prefix
						.concat([
							instance.get('name'),
							INSTANCE_ID_PREFIX,
							instance.get('instanceId')
						])
						.join('');
				},

				getInputNode() {
					var instance = this;

					return instance
						.get('container')
						.one('[name=' + instance.getInputName() + ']');
				},

				getLabelNode() {
					var instance = this;

					return instance.get('container').one('.control-label');
				},

				getPredefinedValueByLocale(locale) {
					var instance = this;

					var name = instance.get('name');

					var field = instance.getFieldByNameInFieldDefinition(name);

					var predefinedValue;

					if (field) {
						var type = field.type;

						if (
							field.predefinedValue &&
							field.predefinedValue[locale]
						) {
							predefinedValue = field.predefinedValue[locale];
						}

						if (type === 'select' && predefinedValue === '[""]') {
							predefinedValue = '';
						}
					}

					return predefinedValue;
				},

				getRepeatedSiblings() {
					var instance = this;

					return instance.getSiblings().filter(item => {
						return item.get('name') === instance.get('name');
					});
				},

				getRuleInputName() {
					var instance = this;

					var inputName = instance.getInputName();

					return inputName;
				},

				getSiblings() {
					var instance = this;

					return instance.get('parent').get('fields');
				},

				getValue() {
					var instance = this;

					var inputNode = instance.getInputNode();

					return Lang.String.unescapeHTML(inputNode.val());
				},

				initializer() {
					var instance = this;

					instance.eventHandlers = [];

					instance.bindUI();
				},

				parseContent(content) {
					var instance = this;

					var container = instance.get('container');

					container.plug(A.Plugin.ParseContent);

					var parser = container.ParseContent;

					parser.parseContent(content);
				},

				remove() {
					var instance = this;

					var siblings = instance.getSiblings();

					var index = siblings.indexOf(instance);

					siblings.splice(index, 1);

					instance._removeFieldValidation(instance);

					instance.destroy();

					instance.get('container').remove(true);
				},

				renderRepeatableUI() {
					var instance = this;

					var container = instance.get('container');

					var containerLabel = container._node.children[0];

					containerLabel.insertAdjacentHTML(
						'afterbegin',
						TPL_REPEATABLE_ICON
					);

					container.append(TPL_REPEATABLE_ADD);
					container.append(TPL_REPEATABLE_DELETE);

					container.delegate(
						'click',
						instance._handleToolbarClick,
						SELECTOR_REPEAT_BUTTONS,
						instance
					);
				},

				renderUI() {
					var instance = this;

					if (instance.get('repeatable')) {
						instance.renderRepeatableUI();
						instance.syncRepeatablelUI();
					}

					instance.syncLabel(instance.get('displayLocale'));

					instance.syncValueUI();

					AArray.invoke(instance.get('fields'), 'renderUI');

					instance.fire('liferay-ddm-field:render', {
						field: instance
					});
				},

				repeat() {
					var instance = this;

					var field = instance.getFieldDefinition();

					if (field.type === 'select') {
						field.options.shift();
					}

					instance._getTemplate(fieldTemplate => {
						var field = instance.createField(fieldTemplate);

						var displayLocale = instance.get('displayLocale');

						field.set('displayLocale', displayLocale);

						if (instance.originalField) {
							field.originalField = instance.originalField;
						} else {
							field.originalField = instance;
						}

						var form = field.getForm();

						form.newRepeatableInstances.push(field);

						field.renderUI();

						instance._addFieldValidation(field, instance);
					});
				},

				setLabel(label) {
					var instance = this;

					var labelNode = instance.getLabelNode();

					if (labelNode) {
						var tipNode = labelNode.one('.taglib-icon-help');

						if (
							!A.UA.ie &&
							Lang.isValue(label) &&
							Lang.isNode(labelNode)
						) {
							labelNode.html(A.Escape.html(label));
						}

						var fieldDefinition = instance.getFieldDefinition();

						if (!A.UA.ie && fieldDefinition.required) {
							labelNode.append(TPL_REQUIRED_MARK);
						}

						instance._addTip(labelNode, tipNode);
					}
				},

				setValue(value) {
					var instance = this;

					var inputNode = instance.getInputNode();

					if (Lang.isValue(value)) {
						inputNode.val(value);
					}
				},

				syncLabel(locale) {
					var instance = this;

					var fieldDefinition = instance.getFieldDefinition();

					if (Lang.isUndefined(fieldDefinition.label[locale])) {
						instance.setLabel(
							fieldDefinition.label[instance.getDefaultLocale()]
						);
					} else {
						instance.setLabel(fieldDefinition.label[locale]);
					}
				},

				syncReadOnlyUI() {
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

						var checkboxInput = container.one(
							'input[type="checkbox"]'
						);

						if (checkboxInput) {
							checkboxInput.attr('disabled', readOnly);
						}

						var disableCheckboxInput = container.one(
							'input[type="checkbox"][name$="disable"]'
						);

						if (
							inputNode &&
							disableCheckboxInput &&
							disableCheckboxInput.get('checked')
						) {
							inputNode.attr('disabled', true);
						}
					}
				},

				syncRepeatablelUI() {
					var instance = this;

					var container = instance.get('container');

					var siblings = instance.getRepeatedSiblings();

					container
						.one('.lfr-ddm-repeatable-delete-button')
						.toggle(siblings.length > 1);
				},

				syncValueUI() {
					var instance = this;

					var dataType = instance.get('dataType');

					if (dataType) {
						var localizationMap = instance.get('localizationMap');

						var value;

						if (instance.get('localizable')) {
							if (!A.Object.isEmpty(localizationMap)) {
								value =
									localizationMap[
										instance.get('displayLocale')
									];
							}
						} else {
							value = instance.getValue();
						}

						if (Lang.isUndefined(value)) {
							value = instance.getDefaultLocalization(
								instance.get('displayLocale')
							);
						}

						instance.setValue(value);
					}
				},

				toJSON() {
					var instance = this;

					var fieldJSON = {
						instanceId: instance.get('instanceId'),
						name: instance.get('name')
					};

					var dataType = instance.get('dataType');

					if (dataType) {
						instance.updateLocalizationMap(
							instance.get('displayLocale')
						);

						fieldJSON.value = instance.get('localizationMap');

						if (instance.get('localizable')) {
							var form = instance.getForm();

							form.addAvailableLanguageIds(
								AObject.keys(fieldJSON.value)
							);
						}
					}

					var fields = instance.get('fields');

					if (fields.length) {
						fieldJSON.nestedFieldValues = AArray.invoke(
							fields,
							'toJSON'
						);
					}

					return fieldJSON;
				},

				updateLocalizationMap(locale) {
					var instance = this;

					var localizationMap = instance.get('localizationMap');

					var value = instance.getValue();

					if (instance.get('localizable')) {
						var defaultLocale = instance.getDefaultLocale();

						if (
							locale === defaultLocale ||
							value !== localizationMap[defaultLocale]
						) {
							localizationMap[locale] = value;
						}
					} else {
						localizationMap = value;
					}

					instance.set('localizationMap', localizationMap);
				}
			}
		});

		var CheckboxField = A.Component.create({
			EXTENDS: Field,

			prototype: {
				getLabelNode() {
					var instance = this;

					return instance.get('container').one('label');
				},

				getValue() {
					var instance = this;

					return instance.getInputNode().test(':checked') + '';
				},

				setLabel(label) {
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

				setValue(value) {
					var instance = this;

					instance.getInputNode().attr('checked', value === 'true');
				}
			}
		});

		FieldTypes.checkbox = CheckboxField;

		var ColorField = A.Component.create({
			EXTENDS: Field,

			prototype: {
				getValue() {
					var instance = this;

					var container = instance.get('container');
					var valueField = container.one('.color-value');

					return valueField.val();
				},

				initializer() {
					var instance = this;

					var container = instance.get('container');

					var selectorInput = container.one('.selector-input');
					var valueField = container.one('.color-value');

					var colorPicker = new A.ColorPickerPopover({
						position: 'bottom',
						trigger: selectorInput,
						zIndex: 65535
					}).render();

					colorPicker.on('select', event => {
						selectorInput.setStyle('backgroundColor', event.color);

						valueField.val(event.color);

						instance.validateField(valueField);
					});

					colorPicker.after('visibleChange', event => {
						if (!event.newVal) {
							instance.validateField(valueField);
						}
					});

					colorPicker.set('color', valueField.val(), {
						trigger: selectorInput
					});

					instance.set('colorPicker', colorPicker);
				},

				setValue(value) {
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
				},

				validateField(valueField) {
					var instance = this;

					var liferayForm = instance.get('liferayForm');

					if (liferayForm) {
						var formValidator = liferayForm.formValidator;

						if (formValidator) {
							formValidator.validateField(valueField);
						}
					}
				}
			}
		});

		FieldTypes['ddm-color'] = ColorField;

		var DateField = A.Component.create({
			EXTENDS: Field,

			prototype: {
				getDatePicker() {
					var instance = this;

					var inputNode = instance.getInputNode();

					return Liferay.component(
						inputNode.attr('id') + 'DatePicker'
					);
				},

				getValue() {
					var instance = this;

					var datePicker = instance.getDatePicker();

					var value = '';

					if (datePicker) {
						var selectedDate = datePicker.getDate();

						var formattedDate = A.DataType.Date.format(
							selectedDate
						);

						var inputNode = instance.getInputNode();

						value = inputNode.val() ? formattedDate : '';
					}

					return value;
				},

				repeat() {
					var instance = this;

					instance._getTemplate(fieldTemplate => {
						var field = instance.createField(fieldTemplate);

						var inputNode = field.getInputNode();

						Liferay.after(
							inputNode.attr('id') + 'DatePicker:registered',
							() => {
								field.renderUI();
							}
						);

						instance._addFieldValidation(field, instance);
					});
				},

				setValue(value) {
					var instance = this;

					var datePicker = instance.getDatePicker();

					if (!datePicker) {
						return;
					}

					datePicker.set('activeInput', instance.getInputNode());

					datePicker.deselectDates();

					if (value) {
						var date = A.DataType.Date.parse(value);

						if (!date) {
							datePicker.selectDates('');

							return;
						}

						date = DateMath.add(
							date,
							DateMath.MINUTES,
							date.getTimezoneOffset()
						);

						datePicker.selectDates(date);
					} else {
						datePicker.selectDates('');
					}
				}
			}
		});

		FieldTypes['ddm-date'] = DateField;

		var DocumentLibraryField = A.Component.create({
			ATTRS: {
				acceptedFileFormats: {
					value: ['*']
				}
			},

			EXTENDS: Field,

			prototype: {
				_handleButtonsClick(event) {
					var instance = this;

					if (!instance.get('readOnly')) {
						var currentTarget = event.currentTarget;

						if (currentTarget.test('.select-button')) {
							instance._handleSelectButtonClick(event);
						} else if (currentTarget.test('.clear-button')) {
							instance._handleClearButtonClick(event);
						}
					}
				},

				_handleClearButtonClick() {
					var instance = this;

					instance.setValue('');
				},

				_handleSelectButtonClick() {
					var instance = this;

					var portletNamespace = instance.get('portletNamespace');

					Liferay.Loader.require(
						'frontend-js-web/liferay/ItemSelectorDialog.es',
						ItemSelectorDialog => {
							var itemSelectorDialog = new ItemSelectorDialog.default(
								{
									eventName:
										portletNamespace +
										'selectDocumentLibrary',
									url: instance.getDocumentLibrarySelectorURL()
								}
							);

							itemSelectorDialog.on(
								'selectedItemChange',
								event => {
									var selectedItem = event.selectedItem;

									if (selectedItem) {
										var itemValue = JSON.parse(
											selectedItem.value
										);

										instance.setValue({
											classPK: itemValue.fileEntryId,
											groupId: itemValue.groupId,
											title: itemValue.title,
											type: itemValue.type,
											uuid: itemValue.uuid
										});
									}
								}
							);

							itemSelectorDialog.open();
						}
					);
				},

				_validateField(fieldNode) {
					var instance = this;

					var liferayForm = instance.get('liferayForm');

					if (liferayForm) {
						var formValidator = liferayForm.formValidator;

						if (formValidator) {
							formValidator.validateField(fieldNode);
						}
					}
				},

				getDocumentLibrarySelectorURL() {
					var instance = this;

					var form = instance.getForm();

					var documentLibrarySelectorURL = form.get(
						'documentLibrarySelectorURL'
					);

					var retVal = instance.getDocumentLibraryURL(
						'com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion'
					);

					if (documentLibrarySelectorURL) {
						retVal = documentLibrarySelectorURL;
					}

					return retVal;
				},

				getDocumentLibraryURL(criteria) {
					var instance = this;

					var container = instance.get('container');

					var portletNamespace = instance.get('portletNamespace');

					var criterionJSON = {
						desiredItemSelectorReturnTypes:
							'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType'
					};

					var uploadCriterionJSON = {
						URL: instance.getUploadURL(),
						desiredItemSelectorReturnTypes:
							'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType'
					};

					var documentLibraryParameters = {
						'0_json': JSON.stringify(criterionJSON),
						'1_json': JSON.stringify(criterionJSON),
						'2_json': JSON.stringify(uploadCriterionJSON),
						criteria,
						itemSelectedEventName:
							portletNamespace + 'selectDocumentLibrary',
						p_p_auth: container.getData('itemSelectorAuthToken'),
						p_p_id: Liferay.PortletKeys.ITEM_SELECTOR,
						p_p_mode: 'view',
						p_p_state: 'pop_up'
					};

					var documentLibraryURL = Liferay.Util.PortletURL.createPortletURL(
						themeDisplay.getLayoutRelativeControlPanelURL(),
						documentLibraryParameters
					);

					return documentLibraryURL.toString();
				},

				getParsedValue(value) {
					if (Lang.isString(value)) {
						if (value !== '') {
							value = JSON.parse(value);
						} else {
							value = {};
						}
					}

					return value;
				},

				getRuleInputName() {
					var instance = this;

					var inputName = instance.getInputName();

					return inputName + 'Title';
				},

				getUploadURL() {
					var uploadParameters = {
						cmd: 'add_temp',
						'javax.portlet.action':
							'/document_library/upload_file_entry',
						p_auth: Liferay.authToken,
						p_p_id: Liferay.PortletKeys.DOCUMENT_LIBRARY
					};

					var uploadURL = Liferay.Util.PortletURL.createActionURL(
						themeDisplay.getLayoutRelativeControlPanelURL(),
						uploadParameters
					);

					return uploadURL.toString();
				},

				initializer() {
					var instance = this;

					var container = instance.get('container');

					container.delegate(
						'click',
						instance._handleButtonsClick,
						'> .form-group .btn',
						instance
					);
				},

				setValue(value) {
					var instance = this;

					var parsedValue = instance.getParsedValue(value);

					if (!parsedValue.title && !parsedValue.uuid) {
						value = '';
					} else {
						value = JSON.stringify(parsedValue);
					}

					DocumentLibraryField.superclass.setValue.call(
						instance,
						value
					);

					instance.syncUI();
				},

				syncReadOnlyUI() {
					var instance = this;

					var readOnly = instance.getReadOnly();

					var container = instance.get('container');

					var selectButtonNode = container.one(
						'#' + instance.getInputName() + 'SelectButton'
					);

					selectButtonNode.attr('disabled', readOnly);

					var clearButtonNode = container.one(
						'#' + instance.getInputName() + 'ClearButton'
					);

					clearButtonNode.attr('disabled', readOnly);

					var altNode = container.one(
						'#' + instance.getInputName() + 'Alt'
					);

					if (altNode) {
						altNode.set('readOnly', readOnly);
					}
				},

				syncUI() {
					var instance = this;

					var parsedValue = instance.getParsedValue(
						instance.getValue()
					);

					var titleNode = A.one(
						'#' + instance.getInputName() + 'Title'
					);

					titleNode.val(parsedValue.title || '');

					instance._validateField(titleNode);

					var clearButtonNode = A.one(
						'#' + instance.getInputName() + 'ClearButton'
					);

					clearButtonNode.toggle(!!parsedValue.uuid);
				}
			}
		});

		FieldTypes['ddm-documentlibrary'] = DocumentLibraryField;

		var JournalArticleField = A.Component.create({
			EXTENDS: Field,

			prototype: {
				_handleButtonsClick(event) {
					var instance = this;

					if (!instance.get('readOnly')) {
						var currentTarget = event.currentTarget;

						if (currentTarget.test('.select-button')) {
							instance._handleSelectButtonClick(event);
						} else if (currentTarget.test('.clear-button')) {
							instance._handleClearButtonClick(event);
						}
					}
				},

				_handleClearButtonClick() {
					var instance = this;

					instance.setValue('');

					instance._hideMessage();
				},

				_handleSelectButtonClick() {
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
						event => {
							if (event.details.length > 0) {
								var selectedWebContent = event.details[0];

								instance.setValue({
									className:
										selectedWebContent.assetclassname,
									classPK: selectedWebContent.assetclasspk,
									title: selectedWebContent.assettitle || '',
									titleMap: selectedWebContent.assettitlemap
								});

								instance._hideMessage();
							}
						}
					);
				},

				_hideMessage() {
					var instance = this;

					var container = instance.get('container');

					var message = container.one(
						'#' + instance.getInputName() + 'Message'
					);

					if (message) {
						message.addClass('hide');
					}

					var formGroup = container.one(
						'#' + instance.getInputName() + 'FormGroup'
					);

					formGroup.removeClass('has-warning');
				},

				_validateField(fieldNode) {
					var instance = this;

					var liferayForm = instance.get('liferayForm');

					if (liferayForm) {
						var formValidator = liferayForm.formValidator;

						if (formValidator) {
							formValidator.validateField(fieldNode);
						}
					}
				},

				getParsedValue(value) {
					if (Lang.isString(value)) {
						if (value !== '') {
							value = JSON.parse(value);
						} else {
							value = {};
						}
					}

					return value;
				},

				getRuleInputName() {
					var instance = this;

					var inputName = instance.getInputName();

					return inputName + 'Title';
				},

				getWebContentSelectorURL() {
					var instance = this;

					var container = instance.get('container');

					var groupIdNode = A.one(
						'#' + this.get('portletNamespace') + 'groupId'
					);

					var groupId =
						(groupIdNode && groupIdNode.getAttribute('value')) ||
						themeDisplay.getScopeGroupId();

					var webContentSelectorParameters = {
						eventName: 'selectContent',
						groupId,
						p_p_auth: container.getData('assetBrowserAuthToken'),
						p_p_id:
							'com_liferay_asset_browser_web_portlet_AssetBrowserPortlet',
						p_p_state: 'pop_up',
						selectedGroupId: groupId,
						showNonindexable: true,
						showScheduled: true,
						typeSelection:
							'com.liferay.journal.model.JournalArticle'
					};

					var webContentSelectorURL = Liferay.Util.PortletURL.createRenderURL(
						themeDisplay.getURLControlPanel(),
						webContentSelectorParameters
					);

					return webContentSelectorURL.toString();
				},

				initializer() {
					var instance = this;

					var container = instance.get('container');

					container.delegate(
						'click',
						instance._handleButtonsClick,
						'> .form-group .btn',
						instance
					);
				},

				setValue(value) {
					var instance = this;

					var parsedValue = instance.getParsedValue(value);

					if (!parsedValue.className && !parsedValue.classPK) {
						value = '';
					} else {
						value = JSON.stringify(parsedValue);
					}

					JournalArticleField.superclass.setValue.call(
						instance,
						value
					);

					instance.syncUI();
				},

				showNotice(message) {
					var instance = this;

					if (!instance.notice) {
						instance.notice = new Liferay.Notice({
							toggleText: false,
							type: 'warning'
						}).hide();
					}

					instance.notice.html(message);
					instance.notice.show();
				},

				syncReadOnlyUI() {
					var instance = this;

					var readOnly = instance.getReadOnly();

					var container = instance.get('container');

					var selectButtonNode = container.one(
						'#' + instance.getInputName() + 'SelectButton'
					);

					selectButtonNode.attr('disabled', readOnly);

					var clearButtonNode = container.one(
						'#' + instance.getInputName() + 'ClearButton'
					);

					clearButtonNode.attr('disabled', readOnly);
				},

				syncUI() {
					var instance = this;

					var parsedValue = instance.getParsedValue(
						instance.getValue()
					);

					var titleNode = A.one(
						'#' + instance.getInputName() + 'Title'
					);

					var parsedTitleMap = instance.getParsedValue(
						parsedValue.titleMap
					);

					if (parsedTitleMap) {
						var journalTitle =
							parsedTitleMap[instance.get('displayLocale')];

						if (journalTitle) {
							parsedValue.title = journalTitle;
						}
					}

					titleNode.val(parsedValue.title || '');

					instance._validateField(titleNode);

					var clearButtonNode = A.one(
						'#' + instance.getInputName() + 'ClearButton'
					);

					clearButtonNode.toggle(!!parsedValue.classPK);
				}
			}
		});

		FieldTypes['ddm-journal-article'] = JournalArticleField;

		var LinkToPageField = A.Component.create({
			ATTRS: {
				delta: {
					value: 10
				},

				selectedLayout: {
					valueFn() {
						var instance = this;

						var layoutValue = instance.getParsedValue(
							instance.getValue()
						);

						var retVal = null;

						if (layoutValue.layoutId) {
							retVal = layoutValue;
						}

						return retVal;
					}
				},

				selectedLayoutPath: {
					valueFn() {
						var instance = this;

						var layoutValue = instance.getParsedValue(
							instance.getValue()
						);

						var privateLayout = !!(
							layoutValue && layoutValue.privateLayout
						);

						var groupIdNode = A.one(
							'#' + this.get('portletNamespace') + 'groupId'
						);

						var groupId =
							(groupIdNode &&
								groupIdNode.getAttribute('value')) ||
							themeDisplay.getScopeGroupId();

						var layoutsRoot = {
							groupId,
							label: Liferay.Language.get('all'),
							layoutId: 0,
							privateLayout
						};

						return [layoutsRoot];
					}
				}
			},

			EXTENDS: Field,

			prototype: {
				_addBreadcrumbElement(label, layoutId, groupId, privateLayout) {
					var instance = this;

					var breadcrumbNode = instance._modal.bodyNode.one(
						'.lfr-ddm-breadcrumb'
					);

					var breadcrumbElementNode = A.Node.create(
						Lang.sub(TPL_PAGES_BREADCRUMB_ELEMENT, {
							groupId,
							label,
							layoutId,
							privateLayout
						})
					);

					breadcrumbNode.append(breadcrumbElementNode);
				},

				_addListElement(layout, container, selected, prepend) {
					var entryNode = A.Node.create(
						Lang.sub(TPL_PAGE, {
							checked: selected ? 'checked="checked"' : '',
							groupId: layout.groupId,
							icon: layout.hasChildren ? TPL_ICON_CARET : '',
							layoutId: layout.layoutId,
							nodeType: layout.hasChildren ? 'root' : 'leaf',
							pageTitle: layout.name,
							privateLayout: layout.privateLayout
						})
					);

					if (prepend) {
						container.prepend(entryNode);
					} else {
						container.append(entryNode);
					}

					if (selected) {
						entryNode.scrollIntoView();
					}
				},

				_afterSelectedLayoutChange(event) {
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

				_afterSelectedLayoutPathChange(event) {
					var instance = this;

					instance._renderBreadcrumb(event.newVal);
				},

				_canLoadMore(key, start, end) {
					var instance = this;

					var cache = instance._getCache(key);

					return !cache || start < cache.start || end > cache.end;
				},

				_cleanSelectedLayout() {
					var instance = this;

					var checkedElement = instance._modal.bodyNode.one(
						'.lfr-ddm-page-radio:checked'
					);

					if (checkedElement) {
						checkedElement.attr('checked', false);

						instance.set('selectedLayout', null);
					}
				},

				_getCache(key) {
					var instance = this;

					var cache;

					if (instance._cache && instance._cache[key]) {
						cache = instance._cache[key];
					}

					return cache;
				},

				_getModalConfig() {
					var instance = this;

					return {
						dialog: {
							cssClass: 'lfr-ddm-link-to-page-modal',
							height: 600,
							modal: true,
							on: {
								destroy() {
									instance.set('selectedLayout', null);
								}
							},
							resizable: false,
							toolbars: {
								footer: [
									{
										cssClass: 'btn-primary',
										disabled: !instance.get(
											'selectedLayout'
										),
										label: Liferay.Language.get('select'),
										on: {
											click: A.bind(
												instance._handleChooseButtonClick,
												instance
											)
										}
									},
									{
										cssClass: 'btn-link',
										label: Liferay.Language.get('cancel'),
										on: {
											click: A.bind(
												instance._handleCancelButtonClick,
												instance
											)
										}
									}
								],
								header: [
									{
										cssClass: 'close',
										discardDefaultButtonCssClasses: true,
										labelHTML: Liferay.Util.getLexiconIconTpl(
											'times'
										),
										on: {
											click: A.bind(
												instance._handleCancelButtonClick,
												instance
											)
										}
									}
								]
							},
							width: 400
						},
						title: Liferay.Language.get('select-layout')
					};
				},

				_handleBreadcrumbElementClick(event) {
					var instance = this;

					var currentTargetLayoutId = Number(
						event.currentTarget.getData('layoutId')
					);

					var selectedLayoutPath = instance.get('selectedLayoutPath');

					var lastLayoutIndex = selectedLayoutPath.length - 1;

					var lastLayout = selectedLayoutPath[lastLayoutIndex];

					var clickedLastElement =
						Number(lastLayout.layoutId) === currentTargetLayoutId;

					if (!clickedLastElement) {
						instance._cleanSelectedLayout();

						while (!clickedLastElement) {
							if (
								Number(lastLayout.layoutId) !==
								currentTargetLayoutId
							) {
								selectedLayoutPath.pop();

								lastLayoutIndex = selectedLayoutPath.length - 1;

								lastLayout =
									selectedLayoutPath[lastLayoutIndex];
							} else {
								clickedLastElement = true;

								var groupId = lastLayout.groupId;

								var privateLayout = lastLayout.privateLayout;

								instance._currentParentLayoutId = Number(
									currentTargetLayoutId
								);

								var bodyNode = instance._modal.bodyNode;

								var listNode = bodyNode.one(
									'.lfr-ddm-pages-container'
								);

								listNode.empty();

								instance._showLoader(listNode);

								listNode.addClass('top-ended');

								instance._requestInitialLayouts(
									currentTargetLayoutId,
									groupId,
									privateLayout,
									instance._renderLayouts
								);
							}
						}

						instance.set('selectedLayoutPath', selectedLayoutPath);
					}
				},

				_handleCancelButtonClick() {
					var instance = this;

					instance._modal.hide();
				},

				_handleChooseButtonClick() {
					var instance = this;

					var selectedLayout = instance.get('selectedLayout');

					instance.setValue(selectedLayout);

					instance._modal.hide();
				},

				_handleClearButtonClick() {
					var instance = this;

					instance._clearedModal = true;

					instance.setValue('');

					instance.set(
						'selectedLayout',
						instance.get('selectedLayoutPath')[0]
					);
				},

				_handleControlButtonsClick(event) {
					var instance = this;

					if (!instance.get('readOnly')) {
						var currentTarget = event.currentTarget;

						if (currentTarget.test('.select-button')) {
							instance._handleSelectButtonClick(event);
						} else {
							instance._handleClearButtonClick(event);
						}
					}
				},

				_handleListEntryClick(event) {
					var instance = this;

					var currentTarget = event.currentTarget;

					var label = event.currentTarget.text();

					var layoutId = event.currentTarget.getData('layoutId');

					var groupId = Number(
						event.currentTarget.getData('groupId')
					);

					var privateLayout = A.DataType.Boolean.parse(
						event.currentTarget.getData('privateLayout')
					);

					if (event.target.hasClass('lfr-ddm-page-label')) {
						if (currentTarget.getData('nodeType') === 'root') {
							instance._cleanSelectedLayout();

							instance._currentParentLayoutId = layoutId;

							instance._showLoader(currentTarget);

							var selectedLayoutPath = instance.get(
								'selectedLayoutPath'
							);

							selectedLayoutPath.push({
								groupId,
								label,
								layoutId,
								privateLayout
							});

							instance.set(
								'selectedLayoutPath',
								selectedLayoutPath
							);

							var listNode = instance._modal.bodyNode.one(
								'.lfr-ddm-pages-container'
							);

							listNode.addClass('top-ended');

							instance._requestInitialLayouts(
								layoutId,
								groupId,
								privateLayout,
								instance._renderLayouts
							);
						} else if (
							currentTarget.getData('nodeType') === 'leaf'
						) {
							var inputRadioNode = currentTarget
								.getElementsByTagName('input')
								.first();

							inputRadioNode.attr('checked', 'true');

							instance.set('selectedLayout', {
								groupId,
								label,
								layoutId,
								path: instance.get('selectedLayoutPath'),
								privateLayout
							});
						}
					} else if (event.target.hasClass('lfr-ddm-page-radio')) {
						instance.set('selectedLayout', {
							groupId,
							label,
							layoutId,
							path: instance.get('selectedLayoutPath'),
							privateLayout
						});
					}
				},

				_handleModalScroll(event) {
					var instance = this;

					var listNode = event.currentTarget;

					var innerHeight = listNode.innerHeight();

					var scrollHeight = listNode.get('scrollHeight');
					var scrollTop = listNode.get('scrollTop');

					var delta = instance.get('delta');

					var groupIdNode = A.one(
						'#' + this.get('portletNamespace') + 'groupId'
					);

					var groupId =
						(groupIdNode && groupIdNode.getAttribute('value')) ||
						themeDisplay.getScopeGroupId();

					var parentLayoutId = instance._currentParentLayoutId;

					var privateLayout = !!instance._navbar
						.one('.private')
						.hasClass('active');

					var key = [parentLayoutId, groupId, privateLayout].join(
						'-'
					);

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
								listNode.prepend(
									instance._loadingAnimationNode
								);

								instance._requestLayouts(
									parentLayoutId,
									groupId,
									privateLayout,
									start,
									end,
									A.rbind(
										'_renderLayoutsFragment',
										instance,
										key,
										'up'
									)
								);
							}
						} else if (
							scrollHeight - (scrollTop + innerHeight) <=
							1
						) {
							start = end;
							end = start + delta;

							if (
								start <= cache.total &&
								start != cache.oldStart
							) {
								cache.oldStart = start;

								listNode.append(instance._loadingAnimationNode);

								instance._requestLayouts(
									parentLayoutId,
									groupId,
									privateLayout,
									start,
									end,
									A.rbind(
										'_renderLayoutsFragment',
										instance,
										key
									)
								);
							}
						}
					}
				},

				_handleNavbarClick(event) {
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

				_handleSelectButtonClick() {
					var instance = this;

					instance._openLinkToPageModal();
				},

				_hideLoader() {
					var instance = this;

					instance._loadingAnimationNode.remove();
				},

				_initBreadcrumb() {
					var instance = this;

					var breadcrumbNode = A.Node.create(TPL_PAGES_BREADCRUMB);

					instance._modal.bodyNode.append(breadcrumbNode);

					breadcrumbNode.delegate(
						'click',
						instance._handleBreadcrumbElementClick,
						'.lfr-ddm-breadcrumb-element',
						instance
					);
				},

				_initLayoutsList() {
					var instance = this;

					var bodyNode = instance._modal.bodyNode;

					if (!bodyNode.one('.lfr-ddm-pages-container')) {
						var navNode = A.Node.create(TPL_PAGES_CONTAINER);

						bodyNode.append(navNode);

						navNode.delegate(
							'click',
							instance._handleListEntryClick,
							'.lfr-ddm-link',
							instance
						);
					}
				},

				_isListNodeEmpty(key) {
					var instance = this;

					var cache = instance._getCache(key);

					return !(cache && cache.layouts);
				},

				_openLinkToPageModal() {
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
						instance._renderBreadcrumb(
							instance.get('selectedLayoutPath')
						);
						instance._renderLayoutsList(privateLayout);

						var listNode = modal.bodyNode.one(
							'.lfr-ddm-pages-container'
						);

						listNode.on(
							'scroll',
							instance._handleModalScroll,
							instance
						);
					} else if (instance._clearedModal) {
						instance._navbar.one('.active').removeClass('active');

						var activeClass = privateLayout
							? '.private'
							: '.public';

						instance._navbar.one(activeClass).addClass('active');
						instance._resetBreadcrumb(privateLayout);
						instance._renderLayoutsList(privateLayout);
						instance._clearedModal = false;
					}

					modal.show();

					instance._syncModalHeight();
				},

				_renderBreadcrumb(layoutsPath) {
					var instance = this;

					var bodyNode = instance._modal.bodyNode;

					var breadcrumbContainer = bodyNode.one(
						'.lfr-ddm-breadcrumb'
					);

					breadcrumbContainer.empty();

					var layoutsPathLenght = layoutsPath.length;

					for (var index = 0; index < layoutsPathLenght; index++) {
						var layoutPath = layoutsPath[index];

						instance._addBreadcrumbElement(
							layoutPath.label,
							layoutPath.layoutId,
							layoutPath.groupId,
							layoutPath.privateLayout
						);
					}
				},

				_renderLayouts(layouts) {
					var instance = this;

					var bodyNode = instance._modal.bodyNode;

					var listNode = bodyNode.one('.lfr-ddm-pages-container');

					var selectedLayout = instance.get('selectedLayout');

					listNode.empty();

					layouts.forEach(layout => {
						var selected =
							selectedLayout &&
							layout.layoutId === selectedLayout.layoutId;

						instance._addListElement(layout, listNode, selected);
					});

					instance._syncModalHeight();
				},

				_renderLayoutsFragment(layouts, key, direction) {
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
							instance._addListElement(
								layouts[index],
								listNode,
								false,
								true
							);
						}

						if (
							cache.start > 0 &&
							listNode.get('scrollTop') === 0
						) {
							listNode.set('scrollTop', 60);
						}
					} else {
						for (index = 0; index < total; index++) {
							instance._addListElement(
								layouts[index],
								listNode,
								false
							);
						}
					}

					instance._syncModalHeight();
				},

				_renderLayoutsList(privateLayout) {
					var instance = this;

					var bodyNode = instance._modal.bodyNode;

					var listNode = bodyNode.one('.lfr-ddm-pages-container');

					instance._showLoader(listNode);

					instance._syncModalHeight();

					var selectedLayout = instance.get('selectedLayout');

					var groupIdNode = A.one(
						'#' + this.get('portletNamespace') + 'groupId'
					);

					var groupId =
						(groupIdNode && groupIdNode.getAttribute('value')) ||
						themeDisplay.getScopeGroupId();

					if (selectedLayout && selectedLayout.layoutId) {
						instance._requestSiblingLayouts(
							groupId,
							privateLayout,
							layouts => {
								var key = [
									instance._currentParentLayoutId,
									groupId,
									privateLayout
								].join('-');

								var cache = instance._getCache(key);

								listNode.toggleClass(
									'top-ended',
									cache.start === 0
								);

								instance._renderLayouts(layouts);

								if (
									cache.start > 0 &&
									listNode.get('scrollTop') === 0
								) {
									listNode.set('scrollTop', 50);
								}

								instance._hideLoader();
							}
						);
					} else {
						listNode.addClass('top-ended');

						instance._requestInitialLayouts(
							0,
							groupId,
							privateLayout,
							instance._renderLayouts
						);
					}
				},

				_renderNavbar(privateLayout) {
					var instance = this;

					var navbar = instance._navbar;

					if (!navbar) {
						navbar = A.Node.create(
							Lang.sub(TPL_LAYOUTS_NAVBAR, {
								privateLayoutClass: privateLayout
									? 'active'
									: '',
								publicLayoutClass: privateLayout ? '' : 'active'
							})
						);

						navbar.delegate(
							'click',
							instance._handleNavbarClick,
							'li',
							instance
						);

						instance._navbar = navbar;

						navbar.insertBefore(navbar, instance._modal.bodyNode);
					}
				},

				_requestInitialLayouts(
					parentLayoutId,
					groupId,
					privateLayout,
					callback
				) {
					var instance = this;

					var end = instance.get('delta');

					var start = 0;

					instance._requestLayouts(
						parentLayoutId,
						groupId,
						privateLayout,
						start,
						end,
						callback
					);
				},

				_requestLayouts(
					parentLayoutId,
					groupId,
					privateLayout,
					start,
					end,
					callback
				) {
					var instance = this;

					var key = [parentLayoutId, groupId, privateLayout].join(
						'-'
					);

					var cache = instance._getCache(key);

					if (!cache || start <= cache.total) {
						if (instance._canLoadMore(key, start, end)) {
							const data = new URLSearchParams({
								cmd: 'get',
								end,
								expandParentLayouts: false,
								groupId,
								p_auth: Liferay.authToken,
								paginate: true,
								parentLayoutId,
								privateLayout,
								start
							});

							Liferay.Util.fetch(
								themeDisplay.getPathMain() +
									'/portal/get_layouts',
								{
									body: data,
									method: 'POST'
								}
							)
								.then(response => {
									return response.json();
								})
								.then(response => {
									var layouts = response && response.layouts;

									if (layouts) {
										instance._updateCache(
											key,
											layouts,
											start,
											end,
											response.total
										);

										callback.call(instance, layouts);
									}
								});
						} else if (cache) {
							callback.call(instance, cache.layouts);
						}
					}
				},

				_requestSiblingLayouts(groupId, privateLayout, callback) {
					var instance = this;

					var cache;

					var path = instance.get('selectedLayoutPath');

					var lastIndex = path.length - 1;

					if (lastIndex >= 0) {
						var parentLayout = path[lastIndex];

						var key = [
							parentLayout.layoutId,
							parentLayout.groupId,
							parentLayout.privateLayout
						].join('-');

						cache = instance._getCache(key);
					}

					if (cache) {
						callback.call(instance, cache.layouts);
					} else {
						var selectedLayout = instance.get('selectedLayout');

						const data = new URLSearchParams({
							cmd: 'getSiblingLayoutsJSON',
							expandParentLayouts: false,
							groupId,
							layoutId: selectedLayout.layoutId,
							max: instance.get('delta'),
							p_auth: Liferay.authToken,
							paginate: true,
							privateLayout
						});

						Liferay.Util.fetch(
							themeDisplay.getPathMain() + '/portal/get_layouts',
							{
								body: data,
								method: 'POST'
							}
						)
							.then(response => {
								return response.json();
							})
							.then(response => {
								var layouts = response && response.layouts;

								if (layouts) {
									var parentLayoutId =
										response.ancestorLayoutIds[0];

									var key = [
										parentLayoutId,
										groupId,
										privateLayout
									].join('-');

									var start = response.start;

									var end = start + layouts.length;

									instance._currentParentLayoutId = parentLayoutId;

									instance._setSelectedLayoutPath(
										groupId,
										privateLayout,
										response
									);

									instance._updateCache(
										key,
										layouts,
										start,
										end,
										response.total
									);

									callback.call(instance, layouts);
								}
							})
							.catch(() => {
								var bodyNode = instance._modal.bodyNode;

								var listNode = bodyNode.one(
									'.lfr-ddm-pages-container'
								);

								listNode.addClass('top-ended');

								instance._requestInitialLayouts(
									0,
									groupId,
									privateLayout,
									instance._renderLayouts
								);
							});
					}
				},

				_resetBreadcrumb(privateLayout) {
					var instance = this;

					var selectedLayoutRoot = instance.get(
						'selectedLayoutPath'
					)[0];

					selectedLayoutRoot.privateLayout = privateLayout;

					instance.set('selectedLayoutPath', [selectedLayoutRoot]);
				},

				_setSelectedLayoutPath(groupId, privateLayout, response) {
					var instance = this;

					var ancestorLayoutIds = response.ancestorLayoutIds;

					if (ancestorLayoutIds) {
						var selectedLayoutPath = [
							instance.get('selectedLayoutPath')[0]
						];

						var ancestorLayoutNames = response.ancestorLayoutNames;

						for (
							var index = ancestorLayoutIds.length - 1;
							index >= 0;
							index--
						) {
							selectedLayoutPath.push({
								groupId,
								label: ancestorLayoutNames[index],
								layoutId: ancestorLayoutIds[index],
								privateLayout
							});
						}

						instance.set('selectedLayoutPath', selectedLayoutPath);
					}
				},

				_showLoader(node) {
					var instance = this;

					instance._loadingAnimationNode.appendTo(node);
				},

				_syncModalHeight() {
					var instance = this;

					var modal = instance._modal;

					var bodyNode = modal.bodyNode;

					modal.fillHeight(bodyNode);

					bodyNode.set(
						'offsetHeight',
						Lang.toInt(bodyNode.get('offsetHeight')) -
							Lang.toInt(instance._navbar.get('offsetHeight'))
					);
				},

				_updateCache(key, layouts, start, end, total) {
					var instance = this;

					var cache = instance._cache[key];

					if (!cache) {
						var path = instance.get('selectedLayoutPath');

						cache = {
							end,
							layouts,
							oldStart: 0,
							path: path.slice(),
							start,
							total
						};

						instance._cache[key] = cache;
					} else {
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
				},

				_validateField(fieldNode) {
					var instance = this;

					var liferayForm = instance.get('liferayForm');

					if (liferayForm) {
						var formValidator = liferayForm.formValidator;

						if (formValidator) {
							formValidator.validateField(fieldNode);
						}
					}
				},

				getParsedValue(value) {
					if (Lang.isString(value)) {
						if (value) {
							value = JSON.parse(value);
						} else {
							value = {};
						}
					}

					return value;
				},

				getRuleInputName() {
					var instance = this;

					var inputName = instance.getInputName();

					return inputName + 'LayoutName';
				},

				initializer() {
					var instance = this;

					var container = instance.get('container');

					instance._currentParentLayoutId = 0;
					instance._loadingAnimationNode = A.Node.create(TPL_LOADER);

					instance._cache = {};

					instance._clearedModal = false;

					instance.after(
						'selectedLayoutChange',
						instance._afterSelectedLayoutChange
					);
					instance.after(
						'selectedLayoutPathChange',
						instance._afterSelectedLayoutPathChange
					);

					container.delegate(
						'click',
						instance._handleControlButtonsClick,
						'> .form-group .btn',
						instance
					);
				},

				setValue(value) {
					var instance = this;

					var container = instance.get('container');

					var inputName = instance.getInputName();

					var layoutNameNode = container.one(
						'#' + inputName + 'LayoutName'
					);

					var parsedValue = instance.getParsedValue(value);

					if (parsedValue && parsedValue.layoutId) {
						if (parsedValue.label) {
							layoutNameNode.val(parsedValue.label);
						}

						value = JSON.stringify(parsedValue);
					} else {
						layoutNameNode.val('');

						value = '';
					}

					instance._validateField(layoutNameNode);

					var clearButtonNode = container.one(
						'#' + inputName + 'ClearButton'
					);

					clearButtonNode.toggle(!!value);

					LinkToPageField.superclass.setValue.call(instance, value);
				},

				syncReadOnlyUI() {
					var instance = this;

					var readOnly = instance.getReadOnly();

					var container = instance.get('container');

					var selectButtonNode = container.one(
						'#' + instance.getInputName() + 'SelectButton'
					);

					selectButtonNode.attr('disabled', readOnly);

					var clearButtonNode = container.one(
						'#' + instance.getInputName() + 'ClearButton'
					);

					clearButtonNode.attr('disabled', readOnly);
				}
			}
		});

		FieldTypes['ddm-link-to-page'] = LinkToPageField;

		FieldTypes.field = Field;

		var FieldsetField = A.Component.create({
			EXTENDS: Field,

			prototype: {
				getFieldNodes() {
					var instance = this;

					return instance
						.get('container')
						.all('> fieldset > div > .field-wrapper');
				}
			}
		});

		FieldTypes.fieldset = FieldsetField;

		var ImageField = A.Component.create({
			ATTRS: {
				acceptedFileFormats: {
					value: ['image/gif', 'image/jpeg', 'image/jpg', 'image/png']
				}
			},

			EXTENDS: DocumentLibraryField,

			prototype: {
				_getImagePreviewURL() {
					var instance = this;

					var imagePreviewURL;

					var value = instance.getParsedValue(instance.getValue());

					if (value.data) {
						imagePreviewURL =
							themeDisplay.getPathContext() + value.data;
					} else if (value.uuid) {
						imagePreviewURL = [
							themeDisplay.getPathContext(),
							'documents',
							value.groupId,
							value.uuid
						].join('/');
					}

					return imagePreviewURL;
				},

				_handleButtonsClick(event) {
					var instance = this;

					var currentTarget = event.currentTarget;

					if (currentTarget.test('.preview-button')) {
						instance._handlePreviewButtonClick(event);
					}

					ImageField.superclass._handleButtonsClick.apply(
						instance,
						arguments
					);
				},

				_handlePreviewButtonClick() {
					var instance = this;

					if (!instance.viewer) {
						instance.viewer = new A.ImageViewer({
							caption: 'alt',
							links:
								'#' +
								instance.getInputName() +
								'PreviewContainer a',
							preloadAllImages: false,
							zIndex: Liferay.zIndex.OVERLAY
						});

						instance.viewer.TPL_CLOSE = instance.viewer.TPL_CLOSE.replace(
							/<\s*span[^>]*>(.*?)<\s*\/\s*span>/,
							Liferay.Util.getLexiconIconTpl(
								'times',
								'icon-monospaced'
							)
						);

						var TPL_PLAYER_PAUSE =
							'<span>' +
							Liferay.Util.getLexiconIconTpl(
								'pause',
								'glyphicon'
							) +
							'</span>';

						var TPL_PLAYER_PLAY =
							'<span>' +
							Liferay.Util.getLexiconIconTpl(
								'play',
								'glyphicon'
							) +
							'</span>';

						instance.viewer.TPL_PLAYER = TPL_PLAYER_PLAY;

						instance.viewer._syncPlaying = function() {
							if (this.get('playing')) {
								this._player.setHTML(TPL_PLAYER_PAUSE);
							} else {
								this._player.setHTML(TPL_PLAYER_PLAY);
							}
						};

						instance.viewer.render();
					}

					var imagePreviewURL = instance._getImagePreviewURL();

					var previewImageNode = A.one(
						'#' + instance.getInputName() + 'PreviewContainer img'
					);
					var previewLinkNode = A.one(
						'#' + instance.getInputName() + 'PreviewContainer a'
					);

					previewLinkNode.attr('href', imagePreviewURL);
					previewImageNode.attr('src', imagePreviewURL);

					instance.viewer.set('currentIndex', 0);
					instance.viewer.set('links', previewLinkNode);

					instance.viewer.show();
				},

				_validateField(fieldNode) {
					var instance = this;

					var liferayForm = instance.get('liferayForm');

					if (liferayForm) {
						var formValidator = liferayForm.formValidator;

						if (formValidator) {
							formValidator.validateField(fieldNode);
						}
					}
				},

				getDocumentLibrarySelectorURL() {
					var instance = this;

					var form = instance.getForm();

					var imageSelectorURL = form.get('imageSelectorURL');

					var retVal = instance.getDocumentLibraryURL(
						'com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion,com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion'
					);

					if (imageSelectorURL) {
						retVal = imageSelectorURL;
					}

					return retVal;
				},

				getDocumentLibraryURL(criteria) {
					var instance = this;

					var container = instance.get('container');

					var parsedValue = instance.getParsedValue(
						ImageField.superclass.getValue.apply(
							instance,
							arguments
						)
					);

					var portletNamespace = instance.get('portletNamespace');

					var journalCriterionJSON = {
						desiredItemSelectorReturnTypes:
							'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
						resourcePrimKey: parsedValue.resourcePrimKey
					};

					var imageCriterionJSON = {
						desiredItemSelectorReturnTypes:
							'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType'
					};

					var documentLibraryParameters = {
						'0_json': JSON.stringify(journalCriterionJSON),
						'1_json': JSON.stringify(imageCriterionJSON),
						criteria,
						itemSelectedEventName:
							portletNamespace + 'selectDocumentLibrary',
						p_p_auth: container.getData('itemSelectorAuthToken'),
						p_p_id: Liferay.PortletKeys.ITEM_SELECTOR,
						p_p_mode: 'view',
						p_p_state: 'pop_up'
					};

					var documentLibraryURL = Liferay.Util.PortletURL.createPortletURL(
						themeDisplay.getLayoutRelativeControlPanelURL(),
						documentLibraryParameters
					);

					return documentLibraryURL.toString();
				},

				getValue() {
					var instance = this;

					var value;

					var parsedValue = instance.getParsedValue(
						ImageField.superclass.getValue.apply(
							instance,
							arguments
						)
					);

					if (instance.isNotEmpty(parsedValue)) {
						var altNode = A.one(
							'#' + instance.getInputName() + 'Alt'
						);

						parsedValue.alt = altNode.val();

						value = JSON.stringify(parsedValue);
					} else {
						value = '';
					}

					return value;
				},

				isNotEmpty(value) {
					var instance = this;

					var parsedValue = instance.getParsedValue(value);

					return (
						(Object.prototype.hasOwnProperty.call(
							parsedValue,
							'data'
						) &&
							parsedValue.data !== '') ||
						Object.prototype.hasOwnProperty.call(
							parsedValue,
							'uuid'
						)
					);
				},

				setValue(value) {
					var instance = this;

					var parsedValue = instance.getParsedValue(value);

					if (instance.isNotEmpty(parsedValue)) {
						if (!parsedValue.name && parsedValue.title) {
							parsedValue.name = parsedValue.title;
						}

						var altNode = A.one(
							'#' + instance.getInputName() + 'Alt'
						);

						altNode.val(parsedValue.alt);

						value = JSON.stringify(parsedValue);
					} else {
						value = '';
					}

					DocumentLibraryField.superclass.setValue.call(
						instance,
						value
					);

					instance.syncUI();
				},

				syncUI() {
					var instance = this;

					var parsedValue = instance.getParsedValue(
						instance.getValue()
					);

					var notEmpty = instance.isNotEmpty(parsedValue);

					var altNode = A.one('#' + instance.getInputName() + 'Alt');

					altNode.attr('disabled', !notEmpty);

					var titleNode = A.one(
						'#' + instance.getInputName() + 'Title'
					);

					if (notEmpty) {
						altNode.val(parsedValue.alt || '');
						titleNode.val(parsedValue.title || '');
					} else {
						altNode.val('');
						titleNode.val('');
					}

					instance._validateField(altNode);
					instance._validateField(titleNode);

					var clearButtonNode = A.one(
						'#' + instance.getInputName() + 'ClearButton'
					);

					clearButtonNode.toggle(notEmpty);

					var previewButtonNode = A.one(
						'#' + instance.getInputName() + 'PreviewButton'
					);

					previewButtonNode.toggle(notEmpty);
				}
			}
		});

		FieldTypes['ddm-image'] = ImageField;

		var GeolocationField = A.Component.create({
			EXTENDS: Field,

			prototype: {
				initializer() {
					var instance = this;

					Liferay.componentReady(instance.getInputName()).then(
						map => {
							map.on(
								'positionChange',
								instance.onPositionChange,
								instance
							);
						}
					);
				},

				onPositionChange(event) {
					var instance = this;

					var inputName = instance.getInputName();

					var location = event.newVal.location;

					instance.setValue(
						JSON.stringify({
							latitude: location.lat,
							longitude: location.lng
						})
					);

					var locationNode = A.one('#' + inputName + 'Location');

					locationNode.html(event.newVal.address);
				}
			}
		});

		FieldTypes['ddm-geolocation'] = GeolocationField;

		var TextHTMLField = A.Component.create({
			EXTENDS: Field,

			prototype: {
				_afterRenderTextHTMLField() {
					var instance = this;

					var container = instance.get('container');

					container.placeAfter(instance.readOnlyText);
					container.placeAfter(instance.readOnlyLabel);
				},

				getEditor() {
					var instance = this;

					return window[instance.getInputName() + 'Editor'];
				},

				getValue() {
					var instance = this;

					var editor = instance.getEditor();

					return isNode(editor)
						? A.one(editor).val()
						: editor.getHTML();
				},

				initializer() {
					var instance = this;

					instance.readOnlyLabel = A.Node.create(
						'<label class="control-label hide"></label>'
					);
					instance.readOnlyText = A.Node.create(
						'<div class="hide"></div>'
					);

					instance.after({
						render: instance._afterRenderTextHTMLField
					});
				},

				setValue(value) {
					var instance = this;

					var editorComponentName =
						instance.getInputName() + 'Editor';

					Liferay.componentReady(editorComponentName).then(function(
						editor
					) {
						if (isNode(editor)) {
							TextHTMLField.superclass.setValue.apply(
								instance,
								arguments
							);
						} else {
							var localizationMap = instance.get(
								'localizationMap'
							);

							if (
								value ===
								localizationMap[instance.get('displayLocale')]
							) {
								editor.setHTML(value);
							}
						}
					});
				},

				syncReadOnlyUI() {
					var instance = this;

					instance.readOnlyLabel.html(
						instance.getLabelNode().getHTML()
					);
					instance.readOnlyText.html(
						'<p>' + instance.getValue() + '</p>'
					);

					var readOnly = instance.getReadOnly();

					instance.readOnlyLabel.toggle(readOnly);
					instance.readOnlyText.toggle(readOnly);

					instance.get('container').toggle(!readOnly);
				}
			}
		});

		FieldTypes['ddm-text-html'] = TextHTMLField;

		var RadioField = A.Component.create({
			EXTENDS: Field,

			prototype: {
				getInputNode() {
					var instance = this;

					var container = instance.get('container');

					return container.one(
						'[name=' + instance.getInputName() + ']:checked'
					);
				},

				getRadioNodes() {
					var instance = this;

					var container = instance.get('container');

					return container.all(
						'[name=' + instance.getInputName() + ']'
					);
				},

				getValue() {
					var instance = this;

					var value = '';

					if (instance.getInputNode()) {
						value = RadioField.superclass.getValue.apply(
							instance,
							arguments
						);
					}

					return value;
				},

				setLabel() {
					var instance = this;

					var container = instance.get('container');

					var fieldDefinition = instance.getFieldDefinition();

					container.all('label').each((item, index) => {
						var optionDefinition = fieldDefinition.options[index];

						var inputNode = item.one('input');

						var optionLabel =
							optionDefinition.label[
								instance.get('displayLocale')
							];

						if (Lang.isValue(optionLabel)) {
							item.html(A.Escape.html(optionLabel));

							item.prepend(inputNode);
						}
					});

					RadioField.superclass.setLabel.apply(instance, arguments);
				},

				setValue(value) {
					var instance = this;

					var radioNodes = instance.getRadioNodes();

					radioNodes.set('checked', false);

					radioNodes
						.filter('[value=' + value + ']')
						.set('checked', true);
				},

				syncReadOnlyUI() {
					var instance = this;

					var readOnly = instance.getReadOnly();

					var radioNodes = instance.getRadioNodes();

					radioNodes.attr('disabled', readOnly);
				}
			}
		});

		FieldTypes.radio = RadioField;

		var SelectField = A.Component.create({
			EXTENDS: RadioField,

			prototype: {
				_getOptions() {
					var instance = this;

					var fieldDefinition = instance.getFieldDefinition();

					var fieldOptions = fieldDefinition.options;

					if (fieldOptions && fieldOptions[0]) {
						if (fieldOptions[0].value === '') {
							var displayLocale = instance.get('displayLocale');

							fieldOptions[0].label[displayLocale] = '';
						} else {
							fieldOptions.unshift(
								instance._getPlaceholderOption()
							);
						}
					}

					return fieldOptions;
				},

				_getPlaceholderOption() {
					var instance = this;
					var label = {};

					label[instance.get('displayLocale')] = '';

					return {
						label,
						value: ''
					};
				},

				getInputNode() {
					var instance = this;

					return Field.prototype.getInputNode.apply(
						instance,
						arguments
					);
				},

				getValue() {
					var instance = this;

					var selectedItems = instance
						.getInputNode()
						.all('option:selected');

					var value;

					if (
						selectedItems._nodes &&
						selectedItems._nodes.length > 0
					) {
						value = selectedItems.val();
					} else {
						value = [];
					}

					return value;
				},

				setLabel() {
					var instance = this;

					var options = instance._getOptions();

					instance
						.getInputNode()
						.all('option')
						.each((item, index) => {
							var optionDefinition = options[index];

							var optionLabel =
								optionDefinition.label[
									instance.get('displayLocale')
								];

							if (Lang.isValue(optionLabel)) {
								item.html(A.Escape.html(optionLabel));
							}
						});

					Field.prototype.setLabel.apply(instance, arguments);
				},

				setValue(value) {
					var instance = this;

					if (Lang.isString(value)) {
						if (value !== '') {
							value = JSON.parse(value);
						} else {
							value = [''];
						}
					}

					instance
						.getInputNode()
						.all('option')
						.each(item => {
							item.set(
								'selected',
								value.indexOf(item.val()) > -1
							);
						});
				}
			}
		});

		FieldTypes.select = SelectField;

		var SeparatorField = A.Component.create({
			EXTENDS: Field,

			prototype: {
				getValue() {
					return '';
				}
			}
		});

		FieldTypes['ddm-separator'] = SeparatorField;

		var Form = A.Component.create({
			ATTRS: {
				availableLanguageIds: {
					value: []
				},

				ddmFormValuesInput: {
					setter: A.one
				},

				defaultEditLocale: {},

				documentLibrarySelectorURL: {},

				formNode: {
					valueFn: '_valueFormNode'
				},

				imageSelectorURL: {},

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
				_afterFormRegistered(event) {
					var instance = this;

					var formNode = instance.get('formNode');

					if (event.formName === formNode.attr('name')) {
						instance.set('liferayForm', event.form);
					}
				},

				_afterRenderField(event) {
					var instance = this;

					var field = event.field;

					if (field.get('repeatable')) {
						instance.registerRepeatable(field);
					}
				},

				_afterRepeatableDragAlign() {
					var DDM = A.DD.DDM;

					DDM.syncActiveShims();
					DDM._dropMove();
				},

				_afterRepeatableDragEnd(event, parentField) {
					var instance = this;

					var node = event.target.get('node');

					var oldIndex = -1;

					parentField.get('fields').some((item, index) => {
						oldIndex = index;

						return (
							item.get('instanceId') ===
							instance.extractInstanceId(node)
						);
					});

					var newIndex = node
						.ancestor()
						.all('> .field-wrapper')
						.indexOf(node);

					instance.moveField(parentField, oldIndex, newIndex);
				},

				_afterUpdateRepeatableFields(event) {
					var instance = this;

					var field = event.field;

					var liferayForm = instance.get('liferayForm');

					if (liferayForm) {
						var validatorRules = liferayForm.formValidator.get(
							'rules'
						);

						if (event.type === 'liferay-ddm-field:repeat') {
							var originalField = event.originalField;

							var originalFieldRuleInputName = originalField.getRuleInputName();

							var originalFieldRules =
								validatorRules[originalFieldRuleInputName];

							if (originalFieldRules) {
								validatorRules[
									field.getRuleInputName()
								] = originalFieldRules;
							}
						} else if (event.type === 'liferay-ddm-field:remove') {
							delete validatorRules[field.getRuleInputName()];

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

				_onDefaultLocaleChanged(event) {
					var instance = this;

					var definition = instance.get('definition');

					definition.defaultLanguageId = event.item.getAttribute(
						'data-value'
					);

					instance.set('definition', definition);
				},

				_onLiferaySubmitForm(event) {
					var instance = this;

					var formNode = instance.get('formNode');

					if (event.form.attr('name') === formNode.attr('name')) {
						instance.updateDDMFormInputValue();
					}
				},

				_onSubmitForm() {
					var instance = this;

					instance.toJSON();

					instance.fillEmptyLocales();

					instance.finalizeRepeatableFieldLocalizations();

					instance.updateDDMFormInputValue();
				},

				_valueFormNode() {
					var instance = this;

					var container = instance.get('container');

					return container.ancestor('form', true);
				},

				_valueLiferayForm() {
					var instance = this;

					var formNode = instance.get('formNode');

					var formName = null;

					if (formNode) {
						formName = formNode.attr('name');
					}

					return Liferay.Form.get(formName);
				},

				addAvailableLanguageIds(availableLanguageIds) {
					var instance = this;

					var currentAvailableLanguageIds = instance.get(
						'availableLanguageIds'
					);

					availableLanguageIds.forEach(item => {
						if (currentAvailableLanguageIds.indexOf(item) == -1) {
							currentAvailableLanguageIds.push(item);
						}
					});
				},

				bindUI() {
					var instance = this;

					var formNode = instance.get('formNode');

					if (formNode) {
						instance.eventHandlers.push(
							instance.after(
								'liferay-ddm-field:render',
								instance._afterRenderField,
								instance
							),
							instance.after(
								[
									'liferay-ddm-field:repeat',
									'liferay-ddm-field:remove'
								],
								instance._afterUpdateRepeatableFields,
								instance
							),
							Liferay.after(
								'form:registered',
								instance._afterFormRegistered,
								instance
							),
							Liferay.after(
								'inputLocalized:defaultLocaleChanged',
								A.bind('_onDefaultLocaleChanged', instance)
							)
						);

						if (instance.get('synchronousFormSubmission')) {
							instance.eventHandlers.push(
								formNode.on(
									'submit',
									instance._onSubmitForm,
									instance
								),
								Liferay.on(
									'submitForm',
									instance._onLiferaySubmitForm,
									instance
								)
							);
						}
					}
				},

				destructor() {
					var instance = this;

					AArray.invoke(instance.eventHandlers, 'detach');
					AArray.invoke(instance.get('fields'), 'destroy');

					instance.get('container').remove();

					instance.eventHandlers = null;

					A.each(instance.repeatableInstances, item => {
						item.destroy();
					});

					instance.repeatableInstances = null;
				},

				fillEmptyLocales() {
					var instance = this;

					instance.get('fields').forEach(field => {
						if (!field.get('localizable')) {
							return;
						}

						var localizationMap = field.get('localizationMap');

						var defaultLocale = instance.getDefaultLocale();

						instance.get('availableLanguageIds').forEach(locale => {
							if (!localizationMap[locale]) {
								localizationMap[locale] =
									localizationMap[defaultLocale];
							}
						});

						field.set('localizationMap', localizationMap);
					});
				},

				finalizeRepeatableFieldLocalizations() {
					var instance = this;

					var defaultLocale = instance.getDefaultLocale();

					Object.keys(instance.newRepeatableInstances).forEach(x => {
						var field = instance.newRepeatableInstances[x];

						if (!field.get('localizable')) {
							return;
						}

						instance.populateBlankLocalizationMap(
							defaultLocale,
							field.originalField,
							field
						);
						instance.populateBlankLocalizationMap(
							defaultLocale,
							field,
							field.originalField
						);
					});
				},

				initializer() {
					var instance = this;

					instance.eventHandlers = [];
					instance.newRepeatableInstances = [];
					instance.repeatableInstances = {};

					instance.bindUI();
					instance.renderUI();
				},

				moveField(parentField, oldIndex, newIndex) {
					var fields = parentField.get('fields');

					fields.splice(newIndex, 0, fields.splice(oldIndex, 1)[0]);
				},

				populateBlankLocalizationMap(
					defaultLocale,
					originalField,
					repeatedField
				) {
					var instance = this;

					var newFieldLocalizations = repeatedField.get(
						'localizationMap'
					);

					var totalLocalizations;

					if (originalField) {
						totalLocalizations = originalField.get(
							'localizationMap'
						);
					} else {
						totalLocalizations = {};
					}

					var currentLocale = repeatedField.get('displayLocale');

					var localizations = Object.keys(totalLocalizations);

					localizations.push(currentLocale);

					localizations.forEach(localization => {
						if (!newFieldLocalizations[localization]) {
							var localizationValue = '';

							if (newFieldLocalizations[defaultLocale]) {
								localizationValue =
									newFieldLocalizations[defaultLocale];
							} else if (
								defaultLocale ===
									repeatedField.get('displayLocale') &&
								repeatedField.getValue()
							) {
								localizationValue = repeatedField.getValue();
							}

							newFieldLocalizations[
								localization
							] = localizationValue;
						}
					});

					repeatedField.set('localizationMap', newFieldLocalizations);

					var newNestedFields = repeatedField.get('fields');
					var originalNestedFields;

					if (originalField) {
						originalNestedFields = originalField.get('fields');
					} else {
						originalNestedFields = [];
					}

					for (var i = 0; i < newNestedFields.length; i++) {
						instance.populateBlankLocalizationMap(
							defaultLocale,
							originalNestedFields[i],
							newNestedFields[i]
						);
					}
				},

				registerRepeatable(field) {
					var instance = this;

					var fieldName = field.get('name');

					var fieldContainer = field.get('container');

					var parentField = field.get('parent');

					var parentNode = fieldContainer.get('parentNode');

					var treeName =
						fieldName + '_' + parentField.get('instanceId');

					var repeatableInstance =
						instance.repeatableInstances[treeName];

					if (!repeatableInstance) {
						var ddPlugins = [];

						if (Liferay.Util.getTop() === A.config.win) {
							ddPlugins.push({
								fn: A.Plugin.DDWinScroll
							});
						} else {
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
								nodes:
									'#' +
									parentNode.attr('id') +
									' [data-fieldName=' +
									fieldName +
									']',
								placeholder: A.Node.create(
									'<div class="form-builder-placeholder"></div>'
								),
								sortCondition(event) {
									var dropNode = event.drop.get('node');

									var dropNodeAncestor = dropNode.ancestor();

									var dragNode = event.drag.get('node');

									var dragNodeAncestor = dragNode.ancestor();

									var retVal =
										dropNode.getData('fieldName') ===
										fieldName;

									if (
										dropNodeAncestor.get('id') !==
										dragNodeAncestor.get('id')
									) {
										retVal = false;
									}

									return retVal;
								}
							}
						);

						repeatableInstance.after(
							'drag:align',
							A.bind(instance._afterRepeatableDragAlign, instance)
						);

						repeatableInstance.after(
							'drag:end',
							A.rbind(
								instance._afterRepeatableDragEnd,
								instance,
								parentField
							)
						);

						instance.repeatableInstances[
							treeName
						] = repeatableInstance;
					} else {
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

				renderUI() {
					var instance = this;

					AArray.invoke(instance.get('fields'), 'renderUI');
				},

				toJSON() {
					var instance = this;

					var definition = instance.get('definition');

					var fieldValues = AArray.invoke(
						instance.get('fields'),
						'toJSON'
					);

					return {
						availableLanguageIds: instance.get(
							'availableLanguageIds'
						),
						defaultLanguageId:
							definition.defaultLanguageId ||
							themeDisplay.getDefaultLanguageId(),
						fieldValues
					};
				},

				unregisterRepeatable(field) {
					field.get('container').dd.destroy();
				},

				updateDDMFormInputValue() {
					var instance = this;

					var ddmFormValuesInput = instance.get('ddmFormValuesInput');

					ddmFormValuesInput.val(JSON.stringify(instance.toJSON()));
				}
			}
		});

		Liferay.DDM.RepeatableSortableList = A.Component.create({
			EXTENDS: A.SortableList,

			prototype: {
				_createDrag(node) {
					var instance = this;

					var helper = instance.get('helper');

					if (!A.DD.DDM.getDrag(node)) {
						var dragOptions = {
							bubbleTargets: instance,
							node,
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
		});

		Liferay.DDM.Form = Form;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-color-picker-popover',
			'aui-datatable',
			'aui-datatype',
			'aui-image-viewer',
			'aui-parse-content',
			'aui-set',
			'aui-sortable-list',
			'json',
			'liferay-form',
			'liferay-layouts-tree',
			'liferay-layouts-tree-radio',
			'liferay-layouts-tree-selectable',
			'liferay-map-base',
			'liferay-notice',
			'liferay-translation-manager'
		]
	}
);
