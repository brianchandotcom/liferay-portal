AUI.add(
	'liferay-ddm-form',
	function(A) {
		var AArray = A.Array;

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

		var TPL_PAGES_CONTAINER = '<ul class="lfr-ddm-pages-container nav"></ul>';

		var TPL_REPEATABLE_ADD = '<a class="icon-plus-sign lfr-ddm-repeatable-add-button" href="javascript:;"></a>';

		var TPL_REPEATABLE_DELETE = '<a class="hide icon-minus-sign lfr-ddm-repeatable-delete-button" href="javascript:;"></a>';

		var TPL_REPEATABLE_HELPER = '<div class="lfr-ddm-repeatable-helper"></div>';

		var TPL_REPEATABLE_PLACEHOLDER = '<div class="lfr-ddm-repeatable-placeholder"></div>';

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
			},

			fields: {
				valueFn: '_valueFields'
			},

			mode: {
			},

			translationManager: {
				getter: '_getTranslationManager'
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

			getRoot: function() {
				var instance = this;

				var root;

				instance.eachParent(
					function(parent) {
						root = parent;
					}
				);

				return root;
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
						instance.getAttrs(A.Object.keys(DDMPortletSupport.ATTRS)),
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

				field.addTarget(instance);

				var translationManager = instance.get('translationManager');

				if (translationManager) {
					translationManager.addTarget(field);
				}

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

				var portletURL = Liferay.PortletURL.createURL(themeDisplay.getURLControlPanel());

				portletURL.setDoAsGroupId(instance.get('doAsGroupId'));
				portletURL.setLifecycle(Liferay.PortletURL.RESOURCE_PHASE);
				portletURL.setParameter('fieldName', instance.get('name'));
				portletURL.setParameter('mode', instance.get('mode'));
				portletURL.setParameter('namespace', instance.get('fieldsNamespace'));
				portletURL.setParameter('p_p_isolated', true);
				portletURL.setParameter('portletNamespace', instance.get('portletNamespace'));
				portletURL.setParameter('readOnly', instance.get('readOnly'));
				portletURL.setPlid(instance.get('p_l_id'));
				portletURL.setPortletId(Liferay.PortletKeys.DYNAMIC_DATA_MAPPING);
				portletURL.setResourceId('renderStructureField');
				portletURL.setWindowState('pop_up');

				return portletURL.toString();
			},

			_getTranslationManager: function(translationManager) {
				var instance = this;

				if (!A.instanceOf(instance, Liferay.DDM.Form)) {
					var form = instance.getRoot();

					translationManager = form.get('translationManager');
				}

				return translationManager;
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

						instance.after(
							{
								'translationmanager:deleteAvailableLocale': instance._afterDeleteAvailableLocale,
								'translationmanager:editingLocaleChange': instance._afterEditingLocaleChange
							}
						);
					},

					renderUI: function() {
						var instance = this;

						if (instance.get('repeatable')) {
							instance.renderRepeatableUI();
							instance.syncRepeatablelUI();
						}

						instance.syncLabelUI();
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

						instance.get('container').remove();
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

					getDefaulLocale: function() {
						var instance = this;

						var defaultLocale = themeDisplay.getDefaultLanguageId();

						var definition = instance.get('definition');

						if (definition) {
							defaultLocale = definition.defaultLanguageId;
						}

						return defaultLocale;
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

							if (Lang.isValue(label) && Lang.isNode(labelNode)) {
								labelNode.html(A.Escape.html(label));
							}

							var fieldDefinition = instance.getFieldDefinition();

							if (fieldDefinition.required) {
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

							inputNode.set('defaultValue', value);
						}
					},

					syncLabelUI: function() {
						var instance = this;

						var defaultLocale = instance.getDefaulLocale();

						var fieldDefinition = instance.getFieldDefinition();

						var labelsMap = fieldDefinition.label;

						var label = labelsMap[instance.get('displayLocale')] || labelsMap[defaultLocale];

						instance.setLabel(label);
					},

					syncReadOnlyUI: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						if (inputNode) {
							inputNode.attr('disabled', instance.get('readOnly'));
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

							instance.updateTranslationsDefaultValue();

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
					},

					updateTranslationsDefaultValue: function() {
						var instance = this;

						var localizationMap = instance.get('localizationMap');
						var parent = instance.get('parent');

						var translationManager = parent.get('translationManager');

						if (translationManager) {
							translationManager.get('availableLocales').forEach(
								function(item, index) {
									var value = localizationMap[item];

									if (Lang.isUndefined(value)) {
										localizationMap[item] = instance.getValue();
									}
								}
							);
						}
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

							var defaultLocale = instance.getDefaulLocale();
							var fieldDefinition = instance.getFieldDefinition();

							var tipsMap = fieldDefinition.tip;

							if (Lang.isObject(tipsMap)) {
								var tip = tipsMap[instance.get('displayLocale')] || tipsMap[defaultLocale];

								tipNode.attr('title', A.Escape.html(tip));
							}

							labelNode.append(tipNode);
						}
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

						var availableLocales = translationManager.get('availableLocales');

						var defaultLocale = translationManager.get('defaultLocale');

						var localizable = instance.get('localizable');

						var locales = [defaultLocale].concat(availableLocales);

						if (localizable && locales.indexOf(event.prevVal) > -1) {
							instance.updateLocalizationMap(event.prevVal);
						}

						instance.set('displayLocale', event.newVal);
						instance.set('readOnly', defaultLocale !== event.newVal && !localizable);

						instance.syncLabelUI();
						instance.syncValueUI();
						instance.syncReadOnlyUI();
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
						}

						event.stopPropagation();
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

						if (!A.Object.isEmpty(fieldValue)) {
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

						var selectedDate = datePicker.getDate();

						var formattedDate = A.DataType.Date.format(selectedDate);

						var inputNode = instance.getInputNode();

						return inputNode.val() ? formattedDate : '';
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

						datePicker.set('activeInput', instance.getInputNode());

						datePicker.deselectDates();

						if (value) {
							var date = A.DataType.Date.parse(value);

							date = DateMath.add(date, DateMath.MINUTES, date.getTimezoneOffset());

							datePicker.selectDates(date);
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

						return instance.getDocumentLibraryURL('com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion');
					},

					getDocumentLibraryURL: function(criteria) {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						var portletURL = Liferay.PortletURL.createURL(themeDisplay.getURLControlPanel());

						portletURL.setDoAsGroupId(instance.get('doAsGroupId'));
						portletURL.setParameter('criteria', criteria);
						portletURL.setParameter('itemSelectedEventName', portletNamespace + 'selectDocumentLibrary');

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

						var portletURL = Liferay.PortletURL.createURL(themeDisplay.getURLControlPanel());

						portletURL.setDoAsGroupId(instance.get('doAsGroupId'));
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

						var container = instance.get('container');

						var selectButtonNode = container.one('#' + instance.getInputName() + 'SelectButton');

						selectButtonNode.attr('disabled', instance.get('readOnly'));
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

						var clearButtonNode = A.one('#' + instance.getInputName() + 'ClearButton');

						var parsedValue = instance.getParsedValue(instance.getValue());

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
						var url = Liferay.PortletURL.createURL(themeDisplay.getURLControlPanel());

						url.setParameter('eventName', 'selectContent');
						url.setParameter('groupId', themeDisplay.getScopeGroupId());
						url.setParameter('selectedGroupIds', themeDisplay.getScopeGroupId());
						url.setParameter('showNonindexable', true);
						url.setParameter('showScheduled', true);
						url.setParameter('typeSelection', 'com.liferay.journal.model.JournalArticle');
						url.setPortletId('com_liferay_asset_browser_web_portlet_AssetBrowserPortlet');
						url.setWindowState('pop_up');

						return url;
					},

					setTitle: function(title) {
						var instance = this;

						var titleNode = A.one('#' + instance.getInputName() + 'Title');

						titleNode.val(title);
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

						var container = instance.get('container');

						var selectButtonNode = container.one('#' + instance.getInputName() + 'SelectButton');

						selectButtonNode.attr('disabled', instance.get('readOnly'));
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

						instance.setTitle('');
						instance.setValue('');
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

									instance.setTitle(selectedWebContent.assettitle || '');

									instance.setValue(
										{
											className: selectedWebContent.assetclassname,
											classPK: selectedWebContent.assetclasspk
										}
									);
								}
							}
						);
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

							if (layoutValue.layoutId) {
								return layoutValue;
							}

							return null;
						}
					},

					selectedLayoutPath: {
						valueFn: function() {
							var instance = this;

							var value = instance.getValue();

							var privateLayout = !!(value && value.privateLayout);

							var layoutsRoot = {
								groupId: themeDisplay.getScopeGroupId(),
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

						instance.after('selectedLayoutChange', instance._afterSelectedLayoutChange);
						instance.after('selectedLayoutPathChange', instance._afterSelectedLayoutPathChange);

						container.delegate('click', instance._handleControlButtonsClick, '.btn', instance);
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

						var container = instance.get('container');

						var selectButtonNode = container.one('#' + instance.getInputName() + 'SelectButton');

						selectButtonNode.attr('disabled', instance.get('readOnly'));
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
											cssClass: 'btn-lg btn-primary',
											disabled: !instance.get('selectedLayout'),
											label: Liferay.Language.get('select'),
											on: {
												click: A.bind(instance._handleChooseButtonClick, instance)
											}
										},
										{
											cssClass: 'btn-lg btn-link',
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

						instance.setValue('');

						instance.set('selectedLayout', instance.get('selectedLayoutPath')[0]);
					},

					_handleControlButtonsClick: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						if (currentTarget.test('.select-button')) {
							instance._handleSelectButtonClick(event);
						}
						else {
							instance._handleClearButtonClick(event);
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
						}
						else if (event.target.hasClass('lfr-ddm-page-radio')) {
							var path = instance.get('selectedLayoutPath');

							instance.set(
								'selectedLayout',
								{
									groupId: groupId,
									label: label,
									layoutId: layoutId,
									path: path,
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

						var groupId = themeDisplay.getScopeGroupId();

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
							else if (scrollTop + innerHeight === scrollHeight) {
								start = end + 1;
								end = start + delta;

								if (start <= cache.total) {
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

						instance._cleanSelectedLayout();

						instance._renderLayoutsList(currentTarget.test('.private'));
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
						else {
							var path = instance.get('selectedLayoutPath');

							instance.set(
								'selectedLayout',
								{
									groupId: value.groupId,
									label: value.label,
									layoutId: value.layoutId,
									path: path.slice(),
									privateLayout: privateLayout
								}
							);

							instance._renderLayoutsList(privateLayout);
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

						if (selectedLayout && selectedLayout.layoutId) {
							var groupId = themeDisplay.getScopeGroupId();

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

							instance._requestInitialLayouts(0, themeDisplay.getScopeGroupId(), privateLayout, instance._renderLayouts);
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

						return instance.getDocumentLibraryURL('com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion,com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion');
					},

					getDocumentLibraryURL: function(criteria) {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						var portletURL = Liferay.PortletURL.createURL(themeDisplay.getURLControlPanel());

						portletURL.setDoAsGroupId(instance.get('doAsGroupId'));
						portletURL.setParameter('criteria', criteria);
						portletURL.setParameter('itemSelectedEventName', portletNamespace + 'selectDocumentLibrary');

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

						var editor = instance.getEditor();

						if (isNode(editor)) {
							TextHTMLField.superclass.setValue.apply(instance, arguments);
						}
						else {
							editor.setHTML(value);
						}
					},

					syncReadOnlyUI: function() {
						var instance = this;

						instance.readOnlyLabel.html(instance.getLabelNode().getHTML());
						instance.readOnlyText.html('<p>' + instance.getValue() + '</p>');

						var readOnly = instance.get('readOnly');

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

						return JSON.stringify([value]);
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

						if (Lang.isString(value)) {
							value = JSON.parse(value);
						}

						if (value.length) {
							value = value[0];
						}

						radioNodes.filter('[value=' + value + ']').set('checked', true);
					},

					syncReadOnlyUI: function() {
						var instance = this;

						var radioNodes = instance.getRadioNodes();

						radioNodes.attr('disabled', instance.get('readOnly'));
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

						return instance.getInputNode().all('option:selected').val();
					},

					setLabel: function() {
						var instance = this;

						var fieldDefinition = instance.getFieldDefinition();

						instance.getInputNode().all('option').each(
							function(item, index) {
								var optionDefinition = fieldDefinition.options[index];

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

					formNode: {
						valueFn: '_valueFormNode'
					},

					liferayForm: {
						valueFn: '_valueLiferayForm'
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

						instance.eventHandlers = [];
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
								formNode.on('submit', instance._onSubmitForm, instance),
								Liferay.after('form:registered', instance._afterFormRegistered, instance),
								Liferay.on('submitForm', instance._onLiferaySubmitForm, instance)
							);
						}
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

					moveField: function(parentField, oldIndex, newIndex) {
						var instance = this;

						var fields = parentField.get('fields');

						fields.splice(newIndex, 0, fields.splice(oldIndex, 1)[0]);
					},

					registerRepeatable: function(field) {
						var instance = this;

						var fieldName = field.get('name');

						var repeatableInstance = instance.repeatableInstances[fieldName];

						if (!repeatableInstance) {
							repeatableInstance = new A.SortableList(
								{
									dropOn: field.get('container').get('parentNode'),
									helper: A.Node.create(TPL_REPEATABLE_HELPER),
									nodes: '[data-fieldName=' + fieldName + ']',
									placeholder: A.Node.create(TPL_REPEATABLE_PLACEHOLDER),
									sortCondition: function(event) {
										var dropNode = event.drop.get('node');

										return dropNode.getData('fieldName') === fieldName;
									}
								}
							);

							repeatableInstance.after('drag:end', A.rbind(instance._afterRepeatableDragEnd, instance, field.get('parent')));

							instance.repeatableInstances[fieldName] = repeatableInstance;
						}
						else {
							repeatableInstance.add(field.get('container'));
						}

						A.DD.DDM.getDrag(field.get('container')).addInvalid('.alloy-editor');
					},

					toJSON: function() {
						var instance = this;

						var translationManager = instance.get('translationManager');

						return {
							availableLanguageIds: translationManager.get('availableLocales'),
							defaultLanguageId: translationManager.get('defaultLocale'),
							fieldValues: AArray.invoke(instance.get('fields'), 'toJSON')
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

								liferayForm.formValidator.resetField(field.getInputNode());

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

						instance.updateDDMFormInputValue();
					},

					_valueDisplayLocale: function() {
						var instance = this;

						var translationManager = instance.get('translationManager');

						return translationManager.get('editingLocale');
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
					},

					_valueTranslationManager: function() {
						var instance = this;

						var translationManager = Liferay.component(instance.get('portletNamespace') + 'translationManager');

						if (!translationManager) {
							translationManager = new Liferay.TranslationManager(
								{
									defaultLocale: themeDisplay.getLanguageId()
								}
							);
						}

						translationManager.addTarget(instance);

						return translationManager;
					}
				}
			}
		);

		Liferay.DDM.Form = Form;
	},
	'',
	{
		requires: ['aui-base', 'aui-datatable', 'aui-datatype', 'aui-image-viewer', 'aui-io-request', 'aui-parse-content', 'aui-set', 'aui-sortable-list', 'json', 'liferay-form', 'liferay-item-selector-dialog', 'liferay-layouts-tree', 'liferay-layouts-tree-radio', 'liferay-layouts-tree-selectable', 'liferay-map-base', 'liferay-notice', 'liferay-portlet-url', 'liferay-translation-manager']
	}
);