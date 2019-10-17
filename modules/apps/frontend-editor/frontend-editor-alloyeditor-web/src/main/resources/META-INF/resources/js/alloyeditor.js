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

/* global AlloyEditor */

AUI.add(
	'liferay-alloy-editor',
	A => {
		var Do = A.Do;
		var Lang = A.Lang;

		var KEY_ENTER = 13;

		var UA = A.UA;

		var LiferayAlloyEditor = A.Component.create({
			ATTRS: {
				contents: {
					validator: Lang.isString,
					value: ''
				},

				editorConfig: {
					validator: Lang.isObject,
					value: {}
				},

				onBlurMethod: {
					getter: '_getEditorMethod',
					validator: '_validateEditorMethod'
				},

				onChangeMethod: {
					getter: '_getEditorMethod',
					validator: '_validateEditorMethod'
				},

				onFocusMethod: {
					getter: '_getEditorMethod',
					validator: '_validateEditorMethod'
				},

				onInitMethod: {
					getter: '_getEditorMethod',
					validator: '_validateEditorMethod'
				},

				portletId: {
					validator: Lang.isString,
					value: ''
				},

				textMode: {
					validator: Lang.isBoolean,
					value: {}
				},

				useCustomDataProcessor: {
					validator: Lang.isBoolean,
					value: false
				}
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Widget,

			NAME: 'liferayalloyeditor',

			NS: 'liferayalloyeditor',

			prototype: {
				_afterGet(attrName) {
					var instance = this;

					var alterReturn;

					if (attrName === 'form') {
						var parentForm = instance._parentForm;

						if (!parentForm) {
							parentForm = instance._srcNode.ancestor('form');

							instance._parentForm = parentForm;
						}

						alterReturn = new Do.AlterReturn(
							'Return ancestor parent form',
							parentForm
						);
					} else if (attrName === 'name') {
						alterReturn = new Do.AlterReturn(
							'Return editor namespace',
							instance.get('namespace')
						);
					} else if (attrName === 'type') {
						alterReturn = new Do.AlterReturn(
							'Return editor node name',
							instance._srcNode.get('nodeName')
						);
					}

					return alterReturn;
				},

				_afterVal(value) {
					var instance = this;

					if (value) {
						instance.setHTML(value);
					}

					return new Do.AlterReturn(
						'Return editor content',
						instance.getHTML()
					);
				},

				_changeLocale(localeChange) {
					var instance = this;

					var nativeEditor = instance.getNativeEditor();

					var editable = nativeEditor.editable();

					editable.changeAttr('dir', localeChange.dir);
					editable.changeAttr('lang', localeChange.lang);
				},

				_getEditorMethod(method) {
					return Lang.isFunction(method)
						? method
						: window[method] || method;
				},

				_initializeData() {
					var instance = this;

					var contents = instance.get('contents');

					if (contents) {
						instance.getNativeEditor().setData(contents);
					}

					var onInitFn = instance.get('onInitMethod');

					if (Lang.isFunction(onInitFn)) {
						onInitFn();
					}

					if (instance.pendingFocus) {
						instance.pendingFocus = false;

						instance.focus();
					}
				},

				_onBlur(event) {
					var instance = this;

					var blurFn = instance.get('onBlurMethod');

					if (Lang.isFunction(blurFn)) {
						blurFn(event.editor);
					}
				},

				_onChange() {
					var instance = this;

					var changeFn = instance.get('onChangeMethod');

					if (Lang.isFunction(changeFn)) {
						changeFn(instance.getText());
					}
				},

				_onCustomDataProcessorLoaded() {
					var instance = this;

					instance.customDataProcessorLoaded = true;

					if (instance.instanceReady) {
						instance._initializeData();
					}
				},

				_onDataReady() {
					var instance = this;

					if (instance._pendingData) {
						var pendingData = instance._pendingData;

						instance._pendingData = null;

						instance.getNativeEditor().setData(pendingData);
					} else {
						instance._dataReady = true;
					}
				},

				_onError(event) {
					new Liferay.Notification({
						closeable: true,
						delay: {
							hide: 5000,
							show: 0
						},
						duration: 500,
						message: event.data,
						title: Liferay.Language.get('error'),
						type: 'danger'
					}).render();
				},

				_onFocus(event) {
					var instance = this;

					var focusFn = instance.get('onFocusMethod');

					if (Lang.isFunction(focusFn)) {
						focusFn(event.editor);
					}
				},

				_onFocusFix(activeElement, nativeEditor) {
					setTimeout(() => {
						nativeEditor.focusManager.blur(true);
						activeElement.focus();
					}, 100);
				},

				_onInstanceReady() {
					var instance = this;

					var editorNamespace = instance.get('namespace');

					if (instance._pendingLocaleChange) {
						instance._changeLocale(instance._pendingLocaleChange);

						instance._pendingLocaleChange = null;
					}

					if (
						instance.customDataProcessorLoaded ||
						!instance.get('useCustomDataProcessor')
					) {
						instance._initializeData();
					}

					instance.instanceReady = true;

					window[editorNamespace].instanceReady = true;

					Liferay.component(
						editorNamespace,
						window[editorNamespace],
						{
							portletId: instance.get('portletId')
						}
					);

					// LPS-73775

					instance
						.getNativeEditor()
						.editable()
						.$.addEventListener(
							'compositionend',
							A.bind('_onChange', instance)
						);

					// LPS-71967

					if (UA.edge && parseInt(UA.edge, 10) >= 14) {
						A.soon(() => {
							if (
								document.activeElement &&
								document.activeElement !== document.body
							) {
								var nativeEditor = instance.getNativeEditor();

								nativeEditor.once(
									'focus',
									A.bind(
										'_onFocusFix',
										instance,
										document.activeElement,
										nativeEditor
									)
								);

								nativeEditor.focus();
							}
						});
					}

					// LPS-72963

					var editorConfig = instance.getNativeEditor().config;

					var removeResizePlugin =
						editorConfig.removePlugins &&
						editorConfig.removePlugins.indexOf('ae_dragresize') >
							-1;

					if (CKEDITOR.env.gecko && removeResizePlugin) {
						var doc = instance.getNativeEditor().document.$;

						doc.designMode = 'on';

						doc.execCommand('enableObjectResizing', false, false);
						doc.execCommand(
							'enableInlineTableEditing',
							false,
							false
						);

						doc.designMode = 'off';
					}
				},

				_onKey(event) {
					if (event.data.keyCode === KEY_ENTER) {
						event.cancel();
					}
				},

				_onLocaleChangedHandler(event) {
					var instance = this;

					var contentsLanguage = event.item.getAttribute(
						'data-value'
					);
					var contentsLanguageDir =
						Liferay.Language.direction[contentsLanguage];

					var localeChange = {
						dir: contentsLanguageDir,
						lang: contentsLanguage
					};

					if (instance.instanceReady) {
						instance._changeLocale(localeChange);
					} else {
						instance._pendingLocaleChange = localeChange;
					}
				},

				_onSetData() {
					var instance = this;

					instance._dataReady = false;
				},

				_validateEditorMethod(method) {
					return Lang.isString(method) || Lang.isFunction(method);
				},

				bindUI() {
					var instance = this;

					instance._eventHandles = [
						Do.after(
							'_afterGet',
							instance._srcNode,
							'get',
							instance
						),
						Do.after(
							'_afterVal',
							instance._srcNode,
							'val',
							instance
						)
					];

					// LPS-84186

					window[
						instance.get('namespace')
					]._localeChangeHandle = Liferay.on(
						'inputLocalized:localeChanged',
						instance._onLocaleChangedHandler,
						instance
					);

					var nativeEditor = instance.getNativeEditor();

					nativeEditor.on(
						'dataReady',
						instance._onDataReady,
						instance
					);
					nativeEditor.on('error', instance._onError, instance);
					nativeEditor.on(
						'instanceReady',
						instance._onInstanceReady,
						instance
					);
					nativeEditor.on('setData', instance._onSetData, instance);

					if (instance.get('onBlurMethod')) {
						nativeEditor.on('blur', instance._onBlur, instance);
					}

					if (instance.get('onChangeMethod')) {
						nativeEditor.on('change', instance._onChange, instance);
					}

					if (instance.get('onFocusMethod')) {
						nativeEditor.on('focus', instance._onFocus, instance);
					}

					if (instance.get('useCustomDataProcessor')) {
						nativeEditor.on(
							'customDataProcessorLoaded',
							instance._onCustomDataProcessorLoaded,
							instance
						);
					}

					var editorConfig = instance.get('editorConfig');

					if (
						editorConfig.disallowedContent &&
						editorConfig.disallowedContent.indexOf('br') !== -1
					) {
						nativeEditor.on('key', instance._onKey, instance);
					}
				},

				destructor() {
					var instance = this;

					var editor = instance._alloyEditor;

					if (editor) {
						editor.destroy();
					}

					new A.EventHandle(instance._eventHandles).detach();

					// LPS-84186

					var localeChangeHandle =
						window[instance.get('namespace')]._localeChangeHandle;

					if (localeChangeHandle) {
						localeChangeHandle.detach();

						delete window[instance.get('namespace')]
							._localeChangeHandle;
					}

					instance.instanceReady = false;

					window[instance.get('namespace')].instanceReady = false;
				},

				focus() {
					var instance = this;

					if (instance.instanceReady) {
						instance.getNativeEditor().focus();
					} else {
						instance.pendingFocus = true;
					}
				},

				getCkData() {
					var instance = this;

					var data = instance.getNativeEditor().getData();

					if (
						CKEDITOR.env.gecko &&
						CKEDITOR.tools.trim(data) === '<br />'
					) {
						data = '';
					}

					return data;
				},

				getEditor() {
					var instance = this;

					return instance._alloyEditor;
				},

				getHTML() {
					var instance = this;

					return instance.get('textMode')
						? instance.getText()
						: instance.getCkData();
				},

				getNativeEditor() {
					var instance = this;

					return instance._alloyEditor.get('nativeEditor');
				},

				getText() {
					var instance = this;

					var editorName = instance.getNativeEditor().name;

					var editor = CKEDITOR.instances[editorName];

					var text = '';

					if (editor) {
						text = editor.editable().getText();
					}

					return text;
				},

				initializer() {
					var instance = this;

					var editorConfig = instance.get('editorConfig');

					var srcNode = editorConfig.srcNode;

					if (Lang.isString(srcNode)) {
						srcNode = A.one('#' + srcNode);
					}

					instance._alloyEditor = AlloyEditor.editable(
						srcNode.attr('id'),
						editorConfig
					);
					instance._srcNode = srcNode;
				},

				setHTML(value) {
					var instance = this;

					if (instance.instanceReady) {
						if (instance._dataReady) {
							instance.getNativeEditor().setData(value);
						} else {
							instance._pendingData = value;
						}
					} else {
						instance.set('contents', value);
					}
				}
			}
		});

		A.LiferayAlloyEditor = LiferayAlloyEditor;
	},
	'',
	{
		requires: [
			'aui-component',
			'liferay-notification',
			'liferay-portlet-base',
			'timers'
		]
	}
);
