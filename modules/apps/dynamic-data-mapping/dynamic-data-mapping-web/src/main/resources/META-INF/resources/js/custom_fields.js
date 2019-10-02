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
	'liferay-portlet-dynamic-data-mapping-custom-fields',
	A => {
		var AArray = A.Array;

		var AEscape = A.Escape;

		var FormBuilderTextField = A.FormBuilderTextField;
		var FormBuilderTypes = A.FormBuilderField.types;

		var LiferayFormBuilderUtil = Liferay.FormBuilder.Util;

		var Lang = A.Lang;

		var LString = Lang.String;

		var booleanParse = A.DataType.Boolean.parse;
		var camelize = Lang.String.camelize;

		var editorLocalizedStrings = {
			cancel: Liferay.Language.get('cancel'),
			edit: Liferay.Language.get('edit'),
			save: Liferay.Language.get('save')
		};

		var instanceOf = A.instanceOf;
		var isNull = Lang.isNull;
		var isObject = Lang.isObject;
		var isUndefined = Lang.isUndefined;
		var isValue = Lang.isValue;

		var structureFieldIndexEnable = function() {
			for (var i = 0; i < Liferay.Portlet.list.length; i++) {
				var indexableNode = A.one(
					'#_' + Liferay.Portlet.list[i] + '_indexable'
				);

				if (indexableNode) {
					var indexable = indexableNode.getAttribute('value');

					if (indexable === 'false') {
						return false;
					}
				}
			}

			return true;
		};

		var CSS_FIELD = A.getClassName('field');

		var CSS_FIELD_CHOICE = A.getClassName('field', 'choice');

		var CSS_FIELD_RADIO = A.getClassName('field', 'radio');

		var CSS_FORM_BUILDER_FIELD_NODE = A.getClassName(
			'form-builder-field',
			'node'
		);

		var CSS_RADIO = A.getClassName('radio');

		var DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

		var LOCALIZABLE_FIELD_ATTRS =
			Liferay.FormBuilder.LOCALIZABLE_FIELD_ATTRS;

		var RESTRICTED_NAME = 'submit';

		var STR_BLANK = '';

		var TPL_COLOR =
			'<input class="field form-control" type="text" value="' +
			A.Escape.html(Liferay.Language.get('color')) +
			'" readonly="readonly">';

		var TPL_GEOLOCATION =
			'<div class="field-labels-inline">' +
			'<img src="' +
			themeDisplay.getPathThemeImages() +
			'/common/geolocation.png" title="' +
			A.Escape.html(Liferay.Language.get('geolocate')) +
			'" />' +
			'<div>';

		var TPL_INPUT_BUTTON =
			'<div class="form-group">' +
			'<input class="field form-control" type="text" value="" readonly="readonly">' +
			'<div class="button-holder">' +
			'<button class="btn btn-secondary select-button" type="button">' +
			'<span class="lfr-btn-label">' +
			A.Escape.html(Liferay.Language.get('select')) +
			'</span>' +
			'</button>' +
			'</div>' +
			'</div>';

		var TPL_PARAGRAPH = '<p></p>';

		var TPL_RADIO =
			'<div class="' +
			CSS_RADIO +
			'">' +
			'<label class="radio-inline" for="{id}">' +
			'<input id="{id}" class="' +
			[
				CSS_FIELD,
				CSS_FIELD_CHOICE,
				CSS_FIELD_RADIO,
				CSS_FORM_BUILDER_FIELD_NODE
			].join(' ') +
			'" name="{name}" type="radio" value="{value}" {checked} {disabled} />' +
			'{label}' +
			'</label>' +
			'</div>';

		var TPL_SEPARATOR = '<div class="separator"></div>';

		var TPL_TEXT_HTML =
			'<textarea class="form-builder-field-node lfr-ddm-text-html"></textarea>';

		var TPL_WCM_IMAGE =
			'<div class="form-group">' +
			'<input class="field form-control" type="text" value="" readonly="readonly">' +
			'<div class="button-holder">' +
			'<button class="btn btn-secondary select-button" type="button">' +
			'<span class="lfr-btn-label">' +
			A.Escape.html(Liferay.Language.get('select')) +
			'</span>' +
			'</button>' +
			'</div>' +
			'<label class="control-label">' +
			A.Escape.html(Liferay.Language.get('image-description')) +
			'</label>' +
			Liferay.Util.getLexiconIconTpl('asterisk') +
			'<input class="field form-control" type="text" value="" disabled>' +
			'</div>';

		var UNIQUE_FIELD_NAMES_MAP = Liferay.FormBuilder.UNIQUE_FIELD_NAMES_MAP;

		var UNLOCALIZABLE_FIELD_ATTRS =
			Liferay.FormBuilder.UNLOCALIZABLE_FIELD_ATTRS;

		DEFAULTS_FORM_VALIDATOR.STRINGS.structureDuplicateFieldName = Liferay.Language.get(
			'please-enter-a-unique-field-name'
		);

		DEFAULTS_FORM_VALIDATOR.RULES.structureDuplicateFieldName = function(
			value,
			editorNode
		) {
			var instance = this;

			var editingField = UNIQUE_FIELD_NAMES_MAP.getValue(value);

			var duplicate = editingField && !editingField.get('selected');

			if (duplicate) {
				editorNode.selectText(0, value.length);

				instance.resetField(editorNode);
			}

			return !duplicate;
		};

		DEFAULTS_FORM_VALIDATOR.STRINGS.structureFieldName = Liferay.Language.get(
			'please-enter-only-alphanumeric-characters-or-underscore'
		);

		DEFAULTS_FORM_VALIDATOR.RULES.structureFieldName = function(value) {
			return LiferayFormBuilderUtil.validateFieldName(value);
		};

		DEFAULTS_FORM_VALIDATOR.STRINGS.structureRestrictedFieldName = Lang.sub(
			Liferay.Language.get('x-is-a-reserved-word'),
			[RESTRICTED_NAME]
		);

		DEFAULTS_FORM_VALIDATOR.RULES.structureRestrictedFieldName = function(
			value
		) {
			return RESTRICTED_NAME !== value;
		};

		var applyStyles = function(node, styleContent) {
			var styles = styleContent.replace(/\n/g, STR_BLANK).split(';');

			node.setStyle(STR_BLANK);

			styles.forEach(item => {
				var rule = item.split(':');

				if (rule.length == 2) {
					var key = camelize(rule[0]);
					var value = rule[1].trim();

					node.setStyle(key, value);
				}
			});
		};

		var ColorCellEditor = A.Component.create({
			EXTENDS: A.BaseCellEditor,

			NAME: 'color-cell-editor',

			prototype: {
				_defSaveFn() {
					var instance = this;

					var colorPicker = instance.get('colorPicker');

					var input = instance.get('boundingBox').one('input');

					if (/#[A-F\d]{6}/.test(input.val())) {
						ColorCellEditor.superclass._defSaveFn.apply(
							instance,
							arguments
						);
					} else {
						colorPicker.show();
					}
				},

				_uiSetValue(val) {
					var instance = this;

					var input = instance.get('boundingBox').one('input');

					input.setStyle('color', val);
					input.val(val);

					instance.elements.val(val);
				},

				ELEMENT_TEMPLATE: '<input type="text" />',

				getElementsValue() {
					var instance = this;

					var retVal;

					var input = instance.get('boundingBox').one('input');

					if (input) {
						var val = input.val();

						if (/#[A-F\d]{6}/.test(val)) {
							retVal = val;
						}
					}

					return retVal;
				},

				renderUI() {
					var instance = this;

					ColorCellEditor.superclass.renderUI.apply(
						instance,
						arguments
					);

					var input = instance.get('boundingBox').one('input');

					var colorPicker = new A.ColorPickerPopover({
						trigger: input,
						zIndex: 65535
					}).render();

					colorPicker.on('select', event => {
						input.setStyle('color', event.color);
						input.val(event.color);

						instance.fire('save', {
							newVal: instance.getValue(),
							prevVal: event.color
						});
					});

					instance.set('colorPicker', colorPicker);
				}
			}
		});

		var DLFileEntryCellEditor = A.Component.create({
			EXTENDS: A.BaseCellEditor,

			NAME: 'document-library-file-entry-cell-editor',

			prototype: {
				_defInitToolbarFn() {
					var instance = this;

					DLFileEntryCellEditor.superclass._defInitToolbarFn.apply(
						instance,
						arguments
					);

					instance.toolbar.add(
						{
							label: Liferay.Language.get('select'),
							on: {
								click: A.bind('_onClickChoose', instance)
							}
						},
						1
					);

					instance.toolbar.add(
						{
							label: Liferay.Language.get('clear'),
							on: {
								click: A.bind('_onClickClear', instance)
							}
						},
						2
					);
				},

				_getDocumentLibrarySelectorURL() {
					var instance = this;

					var portletNamespace = instance.get('portletNamespace');

					var criterionJSON = {
						desiredItemSelectorReturnTypes:
							'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType'
					};

					var uploadCriterionJSON = {
						URL: instance._getUploadURL(),
						desiredItemSelectorReturnTypes:
							'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType'
					};

					var documentLibrarySelectorParameters = {
						'0_json': JSON.stringify(criterionJSON),
						'1_json': JSON.stringify(criterionJSON),
						'2_json': JSON.stringify(uploadCriterionJSON),
						criteria:
							'com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion',
						itemSelectedEventName:
							portletNamespace + 'selectDocumentLibrary',
						p_p_id: Liferay.PortletKeys.ITEM_SELECTOR,
						p_p_mode: 'view',
						p_p_state: 'pop_up'
					};

					var documentLibrarySelectorURL = Liferay.Util.PortletURL.createPortletURL(
						themeDisplay.getLayoutRelativeControlPanelURL(),
						documentLibrarySelectorParameters
					);

					return documentLibrarySelectorURL.toString();
				},

				_getUploadURL() {
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

				_isDocumentLibraryDialogOpen() {
					var instance = this;

					var portletNamespace = instance.get('portletNamespace');

					return !!Liferay.Util.getWindow(
						portletNamespace + 'selectDocumentLibrary'
					);
				},

				_onClickChoose() {
					var instance = this;

					var portletNamespace = instance.get('portletNamespace');

					var itemSelectorDialog = new A.LiferayItemSelectorDialog({
						eventName: portletNamespace + 'selectDocumentLibrary',
						on: {
							selectedItemChange(event) {
								var selectedItem = event.newVal;

								if (selectedItem) {
									var itemValue = JSON.parse(
										selectedItem.value
									);

									instance._selectFileEntry(
										itemValue.groupId,
										itemValue.title,
										itemValue.uuid
									);
								}
							}
						},
						url: instance._getDocumentLibrarySelectorURL()
					});

					itemSelectorDialog.open();
				},

				_onClickClear() {
					var instance = this;

					instance.set('value', STR_BLANK);
				},

				_onDocMouseDownExt(event) {
					var instance = this;

					var boundingBox = instance.get('boundingBox');

					var documentLibraryDialogOpen = instance._isDocumentLibraryDialogOpen();

					if (
						!documentLibraryDialogOpen &&
						!boundingBox.contains(event.target)
					) {
						instance.set('visible', false);
					}
				},

				_selectFileEntry(groupId, title, uuid) {
					var instance = this;

					instance.set(
						'value',
						JSON.stringify({
							groupId,
							title,
							uuid
						})
					);
				},

				_syncElementsFocus() {
					var instance = this;

					var boundingBox = instance.toolbar.get('boundingBox');

					var button = boundingBox.one('button');

					if (button) {
						button.focus();
					} else {
						DLFileEntryCellEditor.superclass._syncElementsFocus.apply(
							instance,
							arguments
						);
					}
				},

				_syncFileLabel(title, url) {
					var instance = this;

					var contentBox = instance.get('contentBox');

					var linkNode = contentBox.one('a');

					if (!linkNode) {
						linkNode = A.Node.create('<a></a>');

						contentBox.prepend(linkNode);
					}

					linkNode.setAttribute('href', url);
					linkNode.setContent(LString.escapeHTML(title));
				},

				_uiSetValue(val) {
					var instance = this;

					if (val) {
						LiferayFormBuilderUtil.getFileEntry(val, fileEntry => {
							var url = LiferayFormBuilderUtil.getFileEntryURL(
								fileEntry
							);

							instance._syncFileLabel(fileEntry.title, url);
						});
					} else {
						instance._syncFileLabel(STR_BLANK, STR_BLANK);

						val = STR_BLANK;
					}

					instance.elements.val(val);
				},

				ELEMENT_TEMPLATE: '<input type="hidden" />',

				getElementsValue() {
					var instance = this;

					return instance.get('value');
				}
			}
		});

		var JournalArticleCellEditor = A.Component.create({
			EXTENDS: A.BaseCellEditor,

			NAME: 'journal-article-cell-editor',

			prototype: {
				_defInitToolbarFn() {
					var instance = this;

					JournalArticleCellEditor.superclass._defInitToolbarFn.apply(
						instance,
						arguments
					);

					instance.toolbar.add(
						{
							label: Liferay.Language.get('select'),
							on: {
								click: A.bind('_onClickChoose', instance)
							}
						},
						1
					);

					instance.toolbar.add(
						{
							label: Liferay.Language.get('clear'),
							on: {
								click: A.bind('_onClickClear', instance)
							}
						},
						2
					);
				},

				_getWebContentSelectorURL() {
					var webContentSelectorParameters = {
						eventName: 'selectContent',
						groupId: themeDisplay.getScopeGroupId(),
						p_auth: Liferay.authToken,
						p_p_id:
							'com_liferay_asset_browser_web_portlet_AssetBrowserPortlet',
						p_p_state: 'pop_up',
						selectedGroupId: themeDisplay.getScopeGroupId(),
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

				_handleCancelEvent() {
					var instance = this;

					instance.get('boundingBox').hide();
				},

				_handleSaveEvent() {
					var instance = this;

					JournalArticleCellEditor.superclass._handleSaveEvent.apply(
						instance,
						arguments
					);

					instance.get('boundingBox').hide();
				},

				_onClickChoose() {
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
							uri: instance._getWebContentSelectorURL()
						},
						event => {
							if (event.details.length > 0) {
								var selectedWebContent = event.details[0];

								instance.setValue({
									className:
										selectedWebContent.assetclassname,
									classPK: selectedWebContent.assetclasspk,
									title: selectedWebContent.assettitle
								});
							}
						}
					);
				},

				_onClickClear() {
					var instance = this;

					instance.set('value', STR_BLANK);
				},

				_onDocMouseDownExt(event) {
					var instance = this;

					var boundingBox = instance.get('boundingBox');

					if (!boundingBox.contains(event.target)) {
						instance._handleCancelEvent(event);
					}
				},

				_syncJournalArticleLabel(title) {
					var instance = this;

					var contentBox = instance.get('contentBox');

					var linkNode = contentBox.one('span');

					if (!linkNode) {
						linkNode = A.Node.create('<span></span>');

						contentBox.prepend(linkNode);
					}

					linkNode.setContent(LString.escapeHTML(title));
				},

				_uiSetValue(val) {
					var instance = this;

					if (val) {
						val = JSON.parse(val);
						var title =
							Liferay.Language.get('journal-article') +
							': ' +
							val.classPK;

						instance._syncJournalArticleLabel(title);
					} else {
						instance._syncJournalArticleLabel(STR_BLANK);
					}
				},

				ELEMENT_TEMPLATE: '<input type="hidden" />',

				getElementsValue() {
					var instance = this;

					return instance.get('value');
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

				setValue(value) {
					var instance = this;

					var parsedValue = instance.getParsedValue(value);

					if (!parsedValue.className && !parsedValue.classPK) {
						value = '';
					} else {
						value = JSON.stringify(parsedValue);
					}

					instance.set('value', value);
				}
			}
		});

		Liferay.FormBuilder.CUSTOM_CELL_EDITORS = {};

		var customCellEditors = [
			ColorCellEditor,
			DLFileEntryCellEditor,
			JournalArticleCellEditor
		];

		customCellEditors.forEach(item => {
			Liferay.FormBuilder.CUSTOM_CELL_EDITORS[item.NAME] = item;
		});

		var LiferayFieldSupport = function() {};

		LiferayFieldSupport.ATTRS = {
			autoGeneratedName: {
				setter: booleanParse,
				value: true
			},

			indexType: {
				valueFn() {
					return structureFieldIndexEnable() ? 'keyword' : '';
				}
			},

			localizable: {
				setter: booleanParse,
				value: true
			},

			name: {
				setter: LiferayFormBuilderUtil.normalizeKey,
				validator(val) {
					return !UNIQUE_FIELD_NAMES_MAP.has(val);
				},
				valueFn() {
					var instance = this;

					var label = LiferayFormBuilderUtil.normalizeKey(
						instance.get('label')
					);

					label = label.replace(/[^a-z0-9]/gi, '');

					var name = label + instance._randomString(4);

					while (UNIQUE_FIELD_NAMES_MAP.has(name)) {
						name = A.FormBuilderField.buildFieldName(name);
					}

					return name;
				}
			},

			repeatable: {
				setter: booleanParse,
				value: false
			}
		};

		LiferayFieldSupport.prototype.initializer = function() {
			var instance = this;

			instance.after('nameChange', instance._afterNameChange);
		};

		LiferayFieldSupport.prototype._afterNameChange = function(event) {
			var instance = this;

			UNIQUE_FIELD_NAMES_MAP.remove(event.prevVal);
			UNIQUE_FIELD_NAMES_MAP.put(event.newVal, instance);
		};

		LiferayFieldSupport.prototype._handleDeleteEvent = function(event) {
			var instance = this;

			var strings = instance.getStrings();

			var deleteModal = Liferay.Util.Window.getWindow({
				dialog: {
					bodyContent: strings.deleteFieldsMessage,
					destroyOnHide: true,
					height: 200,
					resizable: false,
					toolbars: {
						footer: [
							{
								cssClass: 'btn-primary',
								label: Liferay.Language.get('ok'),
								on: {
									click() {
										instance.destroy();

										deleteModal.hide();
									}
								}
							},
							{
								label: Liferay.Language.get('cancel'),
								on: {
									click() {
										deleteModal.hide();
									}
								}
							}
						]
					},
					width: 700
				},
				title: instance.get('label')
			})
				.render()
				.show();

			event.stopPropagation();
		};

		LiferayFieldSupport.prototype._randomString = function(length) {
			var randomString = Liferay.Util.randomInt().toString(36);

			return randomString.substring(0, length);
		};

		var LocalizableFieldSupport = function() {};

		LocalizableFieldSupport.ATTRS = {
			localizationMap: {
				setter: A.clone,
				value: {}
			},

			readOnlyAttributes: {
				getter: '_getReadOnlyAttributes'
			}
		};

		LocalizableFieldSupport.prototype.initializer = function() {
			var instance = this;

			var builder = instance.get('builder');

			instance.after('render', instance._afterLocalizableFieldRender);

			LOCALIZABLE_FIELD_ATTRS.forEach(localizableField => {
				instance.after(
					localizableField + 'Change',
					instance._afterLocalizableFieldChange
				);
			});

			builder.translationManager.after(
				'editingLocaleChange',
				instance._afterEditingLocaleChange,
				instance
			);
		};

		LocalizableFieldSupport.prototype._afterEditingLocaleChange = function(
			event
		) {
			var instance = this;

			instance._syncLocaleUI(event.newVal);
		};

		LocalizableFieldSupport.prototype._afterLocalizableFieldChange = function(
			event
		) {
			var instance = this;

			var builder = instance.get('builder');

			var translationManager = builder.translationManager;

			var editingLocale = translationManager.get('editingLocale');

			instance._updateLocalizationMapAttribute(
				editingLocale,
				event.attrName
			);
		};

		LocalizableFieldSupport.prototype._afterLocalizableFieldRender = function() {
			var instance = this;

			var builder = instance.get('builder');

			var translationManager = builder.translationManager;

			var editingLocale = translationManager.get('editingLocale');

			instance._updateLocalizationMap(editingLocale);
		};

		LocalizableFieldSupport.prototype._getReadOnlyAttributes = function(
			val
		) {
			var instance = this;

			var builder = instance.get('builder');

			var translationManager = builder.translationManager;

			var defaultLocale = translationManager.get('defaultLocale');
			var editingLocale = translationManager.get('editingLocale');

			if (defaultLocale !== editingLocale) {
				val = UNLOCALIZABLE_FIELD_ATTRS.concat(val);
			}

			return AArray.dedupe(val);
		};

		LocalizableFieldSupport.prototype._syncLocaleUI = function(locale) {
			var instance = this;

			var builder = instance.get('builder');

			var localizationMap = instance.get('localizationMap');

			var translationManager = builder.translationManager;

			var defaultLocale = themeDisplay.getDefaultLanguageId();

			if (translationManager) {
				defaultLocale = translationManager.get('defaultLocale');
			}

			var localeMap =
				localizationMap[locale] || localizationMap[defaultLocale];

			if (isObject(localeMap)) {
				LOCALIZABLE_FIELD_ATTRS.forEach(item => {
					if (item !== 'options') {
						var localizedItem = localeMap[item];

						if (
							!isUndefined(localizedItem) &&
							!isNull(localizedItem)
						) {
							instance.set(item, localizedItem);
						}
					}
				});

				builder._syncUniqueField(instance);
			}

			if (instanceOf(instance, A.FormBuilderMultipleChoiceField)) {
				instance._syncOptionsLocaleUI(locale);
			}

			if (builder.editingField === instance) {
				builder.propertyList.set('data', instance.getProperties());
			}
		};

		LocalizableFieldSupport.prototype._syncOptionsLocaleUI = function(
			locale
		) {
			var instance = this;

			var options = instance.get('options');

			options.forEach(item => {
				var localizationMap = item.localizationMap;

				if (isObject(localizationMap)) {
					var localeMap = localizationMap[locale];

					if (isObject(localeMap)) {
						item.label = localeMap.label;
					}
				}
			});

			instance.set('options', options);
		};

		LocalizableFieldSupport.prototype._updateLocalizationMap = function(
			locale
		) {
			var instance = this;

			LOCALIZABLE_FIELD_ATTRS.forEach(item => {
				instance._updateLocalizationMapAttribute(locale, item);
			});
		};

		LocalizableFieldSupport.prototype._updateLocalizationMapAttribute = function(
			locale,
			attributeName
		) {
			var instance = this;

			if (attributeName === 'options') {
				instance._updateLocalizationMapOptions(locale);
			} else {
				var localizationMap = instance.get('localizationMap');

				var localeMap = localizationMap[locale] || {};

				localeMap[attributeName] = instance.get(attributeName);

				localizationMap[locale] = localeMap;

				instance.set('localizationMap', localizationMap);
			}
		};

		LocalizableFieldSupport.prototype._updateLocalizationMapOptions = function(
			locale
		) {
			var instance = this;

			var options = instance.get('options');

			if (options) {
				options.forEach(item => {
					var localizationMap = item.localizationMap;

					if (!isObject(localizationMap)) {
						localizationMap = {};
					}

					localizationMap[locale] = {
						label: item.label
					};

					item.localizationMap = localizationMap;
				});
			}
		};

		var SerializableFieldSupport = function() {};

		SerializableFieldSupport.prototype._addDefinitionFieldLocalizedAttributes = function(
			fieldJSON
		) {
			var instance = this;

			LOCALIZABLE_FIELD_ATTRS.forEach(attr => {
				if (attr === 'options') {
					if (
						instanceOf(instance, A.FormBuilderMultipleChoiceField)
					) {
						instance._addDefinitionFieldOptions(fieldJSON);
					}
				} else {
					fieldJSON[attr] = instance._getLocalizedValue(attr);
				}
			});
		};

		SerializableFieldSupport.prototype._addDefinitionFieldUnlocalizedAttributes = function(
			fieldJSON
		) {
			var instance = this;

			UNLOCALIZABLE_FIELD_ATTRS.forEach(attr => {
				fieldJSON[attr] = instance.get(attr);
			});
		};

		SerializableFieldSupport.prototype._addDefinitionFieldOptions = function(
			fieldJSON
		) {
			var instance = this;

			var options = instance.get('options');

			var fieldOptions = [];

			if (options) {
				options.forEach(option => {
					var fieldOption = {};

					var localizationMap = option.localizationMap;

					fieldOption.value = option.value;
					fieldOption.label = {};

					A.each(localizationMap, (item, index) => {
						fieldOption.label[
							index
						] = LiferayFormBuilderUtil.normalizeValue(item.label);
					});

					fieldOptions.push(fieldOption);
				});

				fieldJSON.options = fieldOptions;
			}
		};

		SerializableFieldSupport.prototype._addDefinitionFieldNestedFields = function(
			fieldJSON
		) {
			var instance = this;

			var nestedFields = [];

			instance.get('fields').each(childField => {
				nestedFields.push(childField.serialize());
			});

			if (nestedFields.length > 0) {
				fieldJSON.nestedFields = nestedFields;
			}
		};

		SerializableFieldSupport.prototype._getLocalizedValue = function(
			attribute
		) {
			var instance = this;

			var builder = instance.get('builder');

			var localizationMap = instance.get('localizationMap');

			var localizedValue = {};

			var translationManager = builder.translationManager;

			var defaultLocale = translationManager.get('defaultLocale');

			translationManager.get('availableLocales').forEach(locale => {
				var value = A.Object.getValue(localizationMap, [
					locale,
					attribute
				]);

				if (!isValue(value)) {
					value = A.Object.getValue(localizationMap, [
						defaultLocale,
						attribute
					]);

					if (!isValue(value)) {
						for (var localizationMapLocale in localizationMap) {
							value = A.Object.getValue(localizationMap, [
								localizationMapLocale,
								attribute
							]);

							if (isValue(value)) {
								break;
							}
						}
					}

					if (!isValue(value)) {
						value = STR_BLANK;
					}
				}

				localizedValue[locale] = LiferayFormBuilderUtil.normalizeValue(
					value
				);
			});

			return localizedValue;
		};

		SerializableFieldSupport.prototype.serialize = function() {
			var instance = this;

			var fieldJSON = {};

			instance._addDefinitionFieldLocalizedAttributes(fieldJSON);
			instance._addDefinitionFieldUnlocalizedAttributes(fieldJSON);
			instance._addDefinitionFieldNestedFields(fieldJSON);

			return fieldJSON;
		};

		A.Base.mix(A.FormBuilderField, [
			LiferayFieldSupport,
			LocalizableFieldSupport,
			SerializableFieldSupport
		]);

		var FormBuilderProto = A.FormBuilderField.prototype;

		var originalGetPropertyModel = FormBuilderProto.getPropertyModel;

		FormBuilderProto.getPropertyModel = function() {
			var instance = this;

			var model = originalGetPropertyModel.call(instance);

			var type = instance.get('type');

			var booleanOptions = {
				false: Liferay.Language.get('no'),
				true: Liferay.Language.get('yes')
			};

			var indexTypeOptions = {
				'': Liferay.Language.get('no'),
				keyword: Liferay.Language.get('yes')
			};

			if (type == 'ddm-image' || type == 'text') {
				indexTypeOptions = {
					'': Liferay.Language.get('not-indexable'),
					keyword: Liferay.Language.get('indexable-keyword'),
					text: Liferay.Language.get('indexable-text')
				};
			}

			if (type == 'ddm-text-html' || type == 'textarea') {
				indexTypeOptions = {
					'': Liferay.Language.get('not-indexable'),
					text: Liferay.Language.get('indexable-text')
				};
			}

			model.forEach(item => {
				if (item.attributeName == 'name') {
					item.editor = new A.TextCellEditor({
						validator: {
							rules: {
								value: {
									required: true,
									structureDuplicateFieldName: true,
									structureFieldName: true,
									structureRestrictedFieldName: true
								}
							}
						}
					});
				}

				if (item.editor) {
					item.editor.set('strings', editorLocalizedStrings);
				}
			});

			return model.concat([
				{
					attributeName: 'indexType',
					editor: new A.RadioCellEditor({
						options: indexTypeOptions,
						strings: editorLocalizedStrings
					}),
					formatter(val) {
						return indexTypeOptions[val.data.value];
					},
					name: Liferay.Language.get('indexable')
				},
				{
					attributeName: 'localizable',
					editor: new A.RadioCellEditor({
						options: booleanOptions,
						strings: editorLocalizedStrings
					}),
					formatter(val) {
						return booleanOptions[val.data.value];
					},
					name: Liferay.Language.get('localizable')
				},
				{
					attributeName: 'repeatable',
					editor: new A.RadioCellEditor({
						options: booleanOptions,
						strings: editorLocalizedStrings
					}),
					formatter(val) {
						return booleanOptions[val.data.value];
					},
					name: Liferay.Language.get('repeatable')
				}
			]);
		};

		var DDMColorField = A.Component.create({
			ATTRS: {
				dataType: {
					value: 'color'
				},

				fieldNamespace: {
					value: 'ddm'
				},

				showLabel: {
					value: false
				}
			},

			EXTENDS: A.FormBuilderField,

			NAME: 'ddm-color',

			prototype: {
				getHTML() {
					return TPL_COLOR;
				},

				getPropertyModel() {
					var instance = this;

					var model = DDMColorField.superclass.getPropertyModel.apply(
						instance,
						arguments
					);

					model.forEach((item, index, collection) => {
						var attributeName = item.attributeName;

						if (attributeName === 'predefinedValue') {
							collection[index] = {
								attributeName,
								editor: new ColorCellEditor({
									strings: editorLocalizedStrings
								}),
								name: Liferay.Language.get('predefined-value')
							};
						}
					});

					return model;
				}
			}
		});

		var DDMDateField = A.Component.create({
			ATTRS: {
				dataType: {
					value: 'date'
				},

				fieldNamespace: {
					value: 'ddm'
				}
			},

			EXTENDS: A.FormBuilderTextField,

			NAME: 'ddm-date',

			prototype: {
				getPropertyModel() {
					var instance = this;

					var model = DDMDateField.superclass.getPropertyModel.apply(
						instance,
						arguments
					);

					model.forEach((item, index, collection) => {
						var attributeName = item.attributeName;

						if (attributeName === 'predefinedValue') {
							collection[index] = {
								attributeName,
								editor: new A.DateCellEditor({
									dateFormat: '%m/%d/%Y',
									inputFormatter(val) {
										var instance = this;

										var value = val;

										if (Array.isArray(val)) {
											value = instance.formatDate(val[0]);
										}

										return value;
									},

									outputFormatter(val) {
										var instance = this;

										var retVal = val;

										if (Array.isArray(val)) {
											var formattedValue = A.DataType.Date.parse(
												instance.get('dateFormat'),
												val[0]
											);

											retVal = [formattedValue];
										}

										return retVal;
									}
								}),
								name: Liferay.Language.get('predefined-value'),
								strings: editorLocalizedStrings
							};
						}
					});

					return model;
				},

				renderUI() {
					var instance = this;

					DDMDateField.superclass.renderUI.apply(instance, arguments);

					var trigger = instance.get('templateNode').one('input');

					if (trigger) {
						instance.datePicker = new A.DatePickerDeprecated({
							calendar: {
								locale: Liferay.ThemeDisplay.getLanguageId()
							},
							on: {
								selectionChange(event) {
									var date = event.newSelection;

									instance.setValue(A.Date.format(date));
								}
							},
							popover: {
								on: {
									keydown(event) {
										var instance = this;

										var domEvent = event.domEvent;

										if (
											domEvent.keyCode == 9 &&
											domEvent.target.hasClass(
												'yui3-calendar-grid'
											)
										) {
											instance.hide();

											Liferay.Util.focusFormField(
												trigger
											);
										}
									}
								}
							},
							trigger
						}).render();
					}

					instance.datePicker.calendar.set('strings', {
						next: Liferay.Language.get('next'),
						none: Liferay.Language.get('none'),
						previous: Liferay.Language.get('previous'),
						today: Liferay.Language.get('today')
					});
				}
			}
		});

		var DDMDecimalField = A.Component.create({
			ATTRS: {
				dataType: {
					value: 'double'
				},

				fieldNamespace: {
					value: 'ddm'
				}
			},

			EXTENDS: A.FormBuilderTextField,

			NAME: 'ddm-decimal'
		});

		var DDMDocumentLibraryField = A.Component.create({
			ATTRS: {
				dataType: {
					value: 'document-library'
				},

				fieldNamespace: {
					value: 'ddm'
				}
			},

			EXTENDS: A.FormBuilderField,

			NAME: 'ddm-documentlibrary',

			prototype: {
				_defaultFormatter() {
					return 'documents-and-media';
				},

				_uiSetValue() {
					return Liferay.Language.get('select');
				},

				getHTML() {
					return TPL_INPUT_BUTTON;
				},

				getPropertyModel() {
					var instance = this;

					var model = DDMDocumentLibraryField.superclass.getPropertyModel.apply(
						instance,
						arguments
					);

					model.forEach(item => {
						var attributeName = item.attributeName;

						if (attributeName === 'predefinedValue') {
							item.editor = new DLFileEntryCellEditor({
								strings: editorLocalizedStrings
							});

							item.formatter = function(obj) {
								var data = obj.data;

								var label = STR_BLANK;

								var value = data.value;

								if (value !== STR_BLANK) {
									label =
										'(' +
										Liferay.Language.get('file') +
										')';
								}

								return label;
							};
						} else if (attributeName === 'type') {
							item.formatter = instance._defaultFormatter;
						}
					});

					return model;
				}
			}
		});

		var DDMGeolocationField = A.Component.create({
			ATTRS: {
				dataType: {
					value: 'geolocation'
				},

				fieldNamespace: {
					value: 'ddm'
				},

				localizable: {
					setter: booleanParse,
					value: false
				}
			},

			EXTENDS: A.FormBuilderField,

			NAME: 'ddm-geolocation',

			prototype: {
				getHTML() {
					return TPL_GEOLOCATION;
				},

				getPropertyModel() {
					var instance = this;

					return DDMGeolocationField.superclass.getPropertyModel
						.apply(instance, arguments)
						.filter(item => {
							return item.attributeName !== 'predefinedValue';
						});
				}
			}
		});

		var DDMImageField = A.Component.create({
			ATTRS: {
				dataType: {
					value: 'image'
				},

				fieldNamespace: {
					value: 'ddm'
				},

				indexType: {
					valueFn() {
						return structureFieldIndexEnable() ? 'text' : '';
					}
				}
			},

			EXTENDS: A.FormBuilderField,

			NAME: 'ddm-image',

			prototype: {
				getHTML() {
					return TPL_WCM_IMAGE;
				}
			}
		});

		var DDMIntegerField = A.Component.create({
			ATTRS: {
				dataType: {
					value: 'integer'
				},

				fieldNamespace: {
					value: 'ddm'
				}
			},

			EXTENDS: A.FormBuilderTextField,

			NAME: 'ddm-integer'
		});

		var DDMNumberField = A.Component.create({
			ATTRS: {
				dataType: {
					value: 'number'
				},

				fieldNamespace: {
					value: 'ddm'
				}
			},

			EXTENDS: A.FormBuilderTextField,

			NAME: 'ddm-number'
		});

		var DDMParagraphField = A.Component.create({
			ATTRS: {
				dataType: {
					value: undefined
				},

				fieldNamespace: {
					value: 'ddm'
				},

				showLabel: {
					readOnly: true,
					value: true
				},

				style: {
					value: STR_BLANK
				}
			},

			EXTENDS: A.FormBuilderField,

			NAME: 'ddm-paragraph',

			UI_ATTRS: ['label', 'style'],

			prototype: {
				_uiSetLabel(val) {
					var instance = this;

					instance.get('templateNode').setContent(val);
				},

				_uiSetStyle(val) {
					var instance = this;

					var templateNode = instance.get('templateNode');

					applyStyles(templateNode, val);
				},

				getHTML() {
					return TPL_PARAGRAPH;
				},

				getPropertyModel() {
					return [
						{
							attributeName: 'type',
							editor: false,
							name: Liferay.Language.get('type')
						},
						{
							attributeName: 'label',
							editor: new A.TextAreaCellEditor({
								strings: editorLocalizedStrings
							}),
							name: Liferay.Language.get('text')
						},
						{
							attributeName: 'style',
							editor: new A.TextAreaCellEditor({
								strings: editorLocalizedStrings
							}),
							name: Liferay.Language.get('style')
						}
					];
				}
			}
		});

		var DDMRadioField = A.Component.create({
			ATTRS: {
				dataType: {
					value: 'radio'
				},

				predefinedValue: {
					setter(val) {
						return val;
					}
				}
			},

			EXTENDS: A.FormBuilderRadioField,

			NAME: 'ddm-radio',

			OVERRIDE_TYPE: 'radio',

			prototype: {
				_uiSetOptions(val) {
					var instance = this;

					var buffer = [];
					var counter = 0;

					var predefinedValue = instance.get('predefinedValue');
					var templateNode = instance.get('templateNode');

					A.each(val, item => {
						var checked = predefinedValue === item.value;

						buffer.push(
							Lang.sub(TPL_RADIO, {
								checked: checked ? 'checked="checked"' : '',
								disabled: instance.get('disabled')
									? 'disabled="disabled"'
									: '',
								id: AEscape.html(
									instance.get('id') + counter++
								),
								label: AEscape.html(item.label),
								name: AEscape.html(instance.get('name')),
								value: AEscape.html(item.value)
							})
						);
					});

					instance.optionNodes = A.NodeList.create(buffer.join(''));

					templateNode.setContent(instance.optionNodes);
				},

				_uiSetPredefinedValue(val) {
					var instance = this;

					var optionNodes = instance.optionNodes;

					if (!optionNodes) {
						return;
					}

					optionNodes.set('checked', false);

					optionNodes
						.all('input[value="' + AEscape.html(val) + '"]')
						.set('checked', true);
				}
			}
		});

		var DDMSeparatorField = A.Component.create({
			ATTRS: {
				dataType: {
					value: undefined
				},

				fieldNamespace: {
					value: 'ddm'
				},

				showLabel: {
					value: false
				},

				style: {
					value: STR_BLANK
				}
			},

			EXTENDS: A.FormBuilderField,

			NAME: 'ddm-separator',

			UI_ATTRS: ['style'],

			prototype: {
				_uiSetStyle(val) {
					var instance = this;

					var templateNode = instance.get('templateNode');

					applyStyles(templateNode, val);
				},

				getHTML() {
					return TPL_SEPARATOR;
				},

				getPropertyModel() {
					var instance = this;

					var model = DDMSeparatorField.superclass.getPropertyModel.apply(
						instance,
						arguments
					);

					model.push({
						attributeName: 'style',
						editor: new A.TextAreaCellEditor({
							strings: editorLocalizedStrings
						}),
						name: Liferay.Language.get('style')
					});

					return model;
				}
			}
		});

		var DDMHTMLTextField = A.Component.create({
			ATTRS: {
				dataType: {
					value: 'html'
				},

				fieldNamespace: {
					value: 'ddm'
				},

				indexType: {
					valueFn() {
						return structureFieldIndexEnable() ? 'text' : '';
					}
				}
			},

			EXTENDS: FormBuilderTextField,

			NAME: 'ddm-text-html',

			prototype: {
				getHTML() {
					return TPL_TEXT_HTML;
				}
			}
		});

		var DDMJournalArticleField = A.Component.create({
			ATTRS: {
				dataType: {
					value: 'journal-article'
				},

				fieldNamespace: {
					value: 'ddm'
				}
			},

			EXTENDS: A.FormBuilderField,

			NAME: 'ddm-journal-article',

			prototype: {
				getHTML() {
					return TPL_INPUT_BUTTON;
				},

				getPropertyModel() {
					var instance = this;

					var model = DDMJournalArticleField.superclass.getPropertyModel.apply(
						instance,
						arguments
					);

					model.push({
						attributeName: 'style',
						editor: new A.TextAreaCellEditor({
							strings: editorLocalizedStrings
						}),
						name: Liferay.Language.get('style')
					});

					model.forEach(item => {
						var attributeName = item.attributeName;

						if (attributeName === 'predefinedValue') {
							item.editor = new JournalArticleCellEditor({
								strings: editorLocalizedStrings
							});

							item.formatter = function(obj) {
								var data = obj.data;

								var label = STR_BLANK;

								var value = data.value;

								if (value !== STR_BLANK) {
									label =
										'(' +
										Liferay.Language.get(
											'journal-article'
										) +
										')';
								}

								return label;
							};
						}
					});

					return model;
				}
			}
		});

		var DDMLinkToPageField = A.Component.create({
			ATTRS: {
				dataType: {
					value: 'link-to-page'
				},

				fieldNamespace: {
					value: 'ddm'
				}
			},

			EXTENDS: A.FormBuilderField,

			NAME: 'ddm-link-to-page',

			prototype: {
				getHTML() {
					return TPL_INPUT_BUTTON;
				}
			}
		});

		var DDMTextAreaField = A.Component.create({
			ATTRS: {
				indexType: {
					valueFn() {
						return structureFieldIndexEnable() ? 'text' : '';
					}
				}
			},

			EXTENDS: A.FormBuilderTextAreaField,

			NAME: 'textarea'
		});

		var plugins = [
			DDMColorField,
			DDMDateField,
			DDMDecimalField,
			DDMDocumentLibraryField,
			DDMGeolocationField,
			DDMImageField,
			DDMIntegerField,
			DDMJournalArticleField,
			DDMLinkToPageField,
			DDMNumberField,
			DDMParagraphField,
			DDMRadioField,
			DDMSeparatorField,
			DDMHTMLTextField,
			DDMTextAreaField
		];

		plugins.forEach(item => {
			FormBuilderTypes[item.OVERRIDE_TYPE || item.NAME] = item;
		});
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-color-picker-popover',
			'aui-url',
			'liferay-item-selector-dialog',
			'liferay-portlet-dynamic-data-mapping'
		]
	}
);
