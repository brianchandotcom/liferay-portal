AUI.add(
	'liferay-ddm-form',
	function(A) {
		var AArray = A.Array;

		var AJSON = A.JSON;

		var Lang = A.Lang;

		var INSTANCE_ID_PREFIX = '_INSTANCE_';

		var SELECTOR_REPEAT_BUTTONS = '.lfr-ddm-repeatable-add-button, .lfr-ddm-repeatable-delete-button';

		var TPL_REPEATABLE_ADD = '<a class="icon-plus-sign lfr-ddm-repeatable-add-button" href="javascript:;"></a>';

		var TPL_REPEATABLE_DELETE = '<a class="hide icon-minus-sign lfr-ddm-repeatable-delete-button" href="javascript:;"></a>';

		var TPL_REPEATABLE_HELPER = '<div class="lfr-ddm-repeatable-helper"></div>';

		var TPL_REPEATABLE_PLACEHOLDER = '<div class="lfr-ddm-repeatable-placeholder"></div>';

		var FieldTypes = Liferay.namespace('DDM.FieldTypes');

		var getFieldClass = function(type) {
			return FieldTypes[type] || FieldTypes.field;
		};

		var isNode = function(node) {
			return node && (node._node || node.nodeType);
		};

		var DDMPortletSupport = function() {};

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

			mode: {
			},

			values: {
				value: {}
			}
		};

		FieldsSupport.prototype = {
			extractInstanceId: function(fieldNode) {
				var instance = this;

				var fieldInstanceId = fieldNode.getData('fieldNamespace');

				return fieldInstanceId.replace(INSTANCE_ID_PREFIX, '');
			},

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
							displayLocale: instance.get('displayLocale'),
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

				A.io.request(
					themeDisplay.getPathMain() + '/dynamic_data_mapping/render_structure_field',
					{
						data: {
							controlPanelCategory: 'portlet',
							definition: AJSON.stringify(instance.get('definition')),
							doAsGroupId: instance.get('doAsGroupId'),
							fieldName: instance.get('name'),
							mode: instance.get('mode'),
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
							instance.renderRepeatableUI();
							instance.syncRepeatablelUI();
						}

						instance.syncLabelUI();
						instance.syncValueUI();

						instance.fire(
							'render',
							{
								field: instance
							}
						);
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

						instance.ddmRepeatableButton = currentTarget;

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

					getRepeatedSiblings: function() {
						var instance = this;

						return AArray.filter(
							instance.getSiblings(),
							function(item) {
								return item.get('name') === instance.get('name');
							}
						);
					},

					getSiblings: function() {
						var instance = this;

						var parent = instance.get('parent');
						var name = instance.get('name');

						return parent.get('fields');
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						return Lang.String.unescapeHTML(inputNode.val());
					},

					remove: function() {
						var instance = this;

						var siblings = instance.getSiblings();

						var index = AArray.indexOf(siblings, instance);

						siblings.splice(index, 1);

						instance.fire(
							'remove',
							{
								field: instance
							}
						);

						instance.destroy();

						instance.get('container').remove(true);
					},

					renderRepeatableUI: function() {
						var instance = this;

						var container = instance.get('container');

						container.append(TPL_REPEATABLE_ADD);
						container.append(TPL_REPEATABLE_DELETE);

						container.delegate('click', instance._handleToolbarClick, SELECTOR_REPEAT_BUTTONS, instance);

						container.plug(A.Plugin.ParseContent);
					},

					repeat: function() {
						var instance = this;

						instance._getTemplate(
							function(fieldTemplate) {
								var fieldNode = A.Node.create(fieldTemplate);

								instance.get('container').insert(fieldNode, 'after');

								var parent = instance.get('parent');

								var siblings = instance.getSiblings();

								var field = instance._getField(fieldNode);

								var index = AArray.indexOf(siblings, instance);

								siblings.splice(++index, 0, field);

								field.set('parent', parent);

								field.renderUI();

								instance.fire(
									'repeat',
									{
										field: field,
										originalField: instance
									}
								);
							}
						);
					},

					setLabel: function(label) {
						var instance = this;

						var labelNode = instance.getLabelNode();

						if (Lang.isValue(label)) {
							labelNode.html(A.Escape.html(label));
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
							labelNode.html('&nbsp;' + A.Escape.html(label));

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

						container.delegate('click', instance._handleButtonsClick, '.btn', instance);

						instance.uploader = new A.Uploader(
							{
								after: {
									fileselect: function(event) {
										instance.setPercentUploaded(0);

										if (instance.notice) {
											instance.notice.hide();
										}

										instance.uploader.uploadAll();
									}
								},
								appendNewFiles: false,
								dragAndDropArea: '#' + instance.getInputName() + 'Title',
								fileFieldName: 'file',
								fileFilters: instance.get('acceptedFileFormats'),
								on: {
									uploadcomplete: function(event) {
										try {
											var data = A.JSON.parse(event.data);

											if (data.status) {
												instance.showNotice(data.message);

												instance.setPercentUploaded(0);
											}
											else {
												data.tempFile = true;

												instance.setValue(data);

												instance.setPercentUploaded(100);
											}
										}
										catch (e) {
											instance.showNotice(Liferay.Language.get('an-unexpected-error-occurred'));

											instance.setPercentUploaded(0);
										}
									},
									uploaderror: function(event) {
										instance.showNotice(Liferay.Language.get('an-unexpected-error-occurred'));

										instance.setPercentUploaded(0);
									},
									uploadprogress: function(event) {
										instance.setPercentUploaded(event.percentLoaded);
									}
								},
								uploadURL: instance.getUploadURL(),
								withCredentials: false
							}
						).render('#' + instance.getInputName() + 'UploadContainer');
					},

					syncUI: function() {
						var instance = this;

						var parsedValue = instance.getParsedValue(instance.getValue());

						var titleNode = A.one('#' + instance.getInputName() + 'Title');

						titleNode.val(parsedValue.title || Liferay.Language.get('drag-file-here'));

						var clearButtonNode = A.one('#' + instance.getInputName() + 'ClearButton');

						clearButtonNode.toggle(!!parsedValue.uuid);
					},

					_handleButtonsClick: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						if (currentTarget.test('.select-button')) {
							instance._handleSelectButtonClick(event);
						}
						else if (currentTarget.test('.upload-button')) {
							instance._handleUploadButtonClick(event);
						}
						else if (currentTarget.test('.clear-button')) {
							instance._handleClearButtonClick(event);
						}
					},

					_handleClearButtonClick: function(event) {
						var instance = this;

						instance.setValue('');

						instance.uploader.set('fileList', []);

						instance.setPercentUploaded(0);
					},

					_handleSelectButtonClick: function(event) {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						instance.setPercentUploaded(0);

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
					},

					_handleUploadButtonClick: function(event) {
						var instance = this;

						instance.uploader.openFileSelectDialog();
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
						portletURL.setPortletId(Liferay.PortletKeys.DOCUMENT_SELECTOR);
						portletURL.setWindowState('pop_up');

						return portletURL.toString();
					},

					getParsedValue: function(value) {
						var instance = this;

						if (Lang.isString(value)) {
							if (value !== '') {
								value = AJSON.parse(value);
							}
							else {
								value = {};
							}
						}

						return value;
					},

					getUploadURL: function() {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						var portletURL = Liferay.PortletURL.createURL(themeDisplay.getURLControlPanel());

						portletURL.setDoAsGroupId(instance.get('doAsGroupId'));

						portletURL.setLifecycle(Liferay.PortletURL.ACTION_PHASE);

						portletURL.setParameter('cmd', 'add_temp');
						portletURL.setParameter('p_auth', Liferay.authToken);
						portletURL.setParameter('struts_action', '/journal/upload_file_entry');

						portletURL.setPortletId(Liferay.PortletKeys.JOURNAL);

						return portletURL.toString();
					},

					setPercentUploaded: function(value) {
						var instance = this;

						var progressContainerNode = A.one('#' + instance.getInputName() + 'Progress');

						progressContainerNode.toggle(value > 0);

						var progressBarNode = progressContainerNode.one('.progress-bar');

						progressBarNode.attr('aria-valuenow', value);
						progressBarNode.setStyle('width', value + '%');
					},

					setValue: function(value) {
						var instance = this;

						var parsedValue = instance.getParsedValue(value);

						if (!parsedValue.title && !parsedValue.uuid) {
							value = '';
						}
						else {
							value = AJSON.stringify(parsedValue);
						}

						DocumentLibraryField.superclass.setValue.call(instance, value);

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
					}
				}
			}
		);

		FieldTypes['ddm-documentlibrary'] = DocumentLibraryField;

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

							titleNode.val(parsedValue.name || '');
						}
						else {
							altNode.val('');

							titleNode.val(Liferay.Language.get('drag-file-here'));
						}

						var clearButtonNode = A.one('#' + instance.getInputName() + 'ClearButton');

						clearButtonNode.toggle(notEmpty);

						var previewButtonNode = A.one('#' + instance.getInputName() + 'PreviewButton');

						previewButtonNode.toggle(notEmpty);
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
								'/documents',
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

						var previewLinkNode = A.one('#' + instance.getInputName() + 'PreviewContainer a');
						var previewImageNode = A.one('#' + instance.getInputName() + 'PreviewContainer img');

						previewLinkNode.attr('href', imagePreviewURL);
						previewImageNode.attr('src', imagePreviewURL);

						instance.viewer.set('currentIndex', 0);
						instance.viewer.set('links', previewLinkNode);

						instance.viewer.show();
					},

					getDocumentLibraryURL: function() {
						var instance = this;

						var portletURL = ImageField.superclass.getDocumentLibraryURL.apply(instance, arguments);

						return portletURL + '&Type=image';
					},

					getValue: function() {
						var instance = this;

						var value;

						var parsedValue = instance.getParsedValue(ImageField.superclass.getValue.apply(instance, arguments));

						if (instance.isNotEmpty(parsedValue)) {
							var altNode = A.one('#' + instance.getInputName() + 'Alt');

							parsedValue.alt = altNode.val();

							value = AJSON.stringify(parsedValue);
						}
						else {
							value = '';
						}

						return value;
					},

					isNotEmpty: function(value) {
						var instance = this;

						var parsedValue = instance.getParsedValue(value);

						return (parsedValue.hasOwnProperty('data') && parsedValue.data !== '') || parsedValue.hasOwnProperty('uuid');
					},

					setValue: function(value) {
						var instance = this;

						var parsedValue = instance.getParsedValue(value);

						if (instance.isNotEmpty(parsedValue)) {
							if (!parsedValue.name && parsedValue.title) {
								parsedValue.name = parsedValue.title;
							}

							value = AJSON.stringify(parsedValue);
						}
						else {
							value = '';
						}

						DocumentLibraryField.superclass.setValue.call(instance, value);

						instance.syncUI();
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
							AJSON.stringify(
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
									item.html(A.Escape.html(optionLabel));

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
							value = AJSON.parse(value);
						}

						instance.getInputNode().all('option').each(
							function(item, index) {
								item.set('selected', AArray.indexOf(value, item.val()) > -1);
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
					repeatableInstances: {},

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

							Liferay.on('submitForm', instance._onLiferaySubmitForm, instance);

							Liferay.after('form:registered', instance._afterFormRegistered, instance);

							instance.after(
								['liferay-ddm-field:repeat', 'liferay-ddm-field:remove'],
								instance._afterUpdateRepeatableFields,
								instance
							);

							instance.after('liferay-ddm-field:render', instance._afterRenderField, instance);
						}
					},

					_afterFormRegistered: function(event) {
						var instance = this;

						if (event.formName === instance.formNode.attr('name')) {
							instance.liferayForm = event.form;
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

						AArray.some(
							parentField.get('fields'),
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

						var liferayForm = instance.liferayForm;

						if (liferayForm) {
							var validatorRules = liferayForm.formValidator.get('rules');

							if (event.type === 'liferay-ddm-field:repeat') {
								var originalField = event.originalField;

								var originalFieldInputName = originalField.getInputName();

								validatorRules[field.getInputName()] = validatorRules[originalFieldInputName];
							}
							else if (event.type === 'liferay-ddm-field:remove') {
								delete validatorRules[field.getInputName()];

								liferayForm.formValidator.resetField(field.getInputNode());

								instance.unregisterRepeatable(field);
							}

							liferayForm.formValidator.set('rules', validatorRules);
						}

						AArray.invoke(field.getRepeatedSiblings(), 'syncRepeatablelUI');
					},

					_onLiferaySubmitForm: function(event) {
						var instance = this;

						if (event.form.attr('name') === instance.formNode.attr('name')) {
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

						ddmFormValuesInput.val(AJSON.stringify(instance.toJSON()));
					}
				}
			}
		);

		Liferay.DDM.Form = Form;
	},
	'',
	{
		requires: ['aui-base', 'aui-datatype', 'aui-image-viewer', 'aui-io-request', 'aui-parse-content', 'aui-set', 'aui-sortable-list', 'json', 'liferay-map-base', 'liferay-notice', 'liferay-portlet-url', 'liferay-translation-manager', 'uploader']
	}
);