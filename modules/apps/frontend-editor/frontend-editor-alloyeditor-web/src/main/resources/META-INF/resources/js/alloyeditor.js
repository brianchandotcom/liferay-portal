/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* global AlloyEditor */

AUI.add(
	'liferay-alloy-editor',
	(A) => {
		const Do = A.Do;
		const Lang = A.Lang;

		const KEY_ENTER = 13;

		const UA = A.UA;

		const LiferayAlloyEditor = A.Component.create({
			ATTRS: {
				contents: {
					validator: Lang.isString,
					value: '',
				},

				editorConfig: {
					validator: Lang.isObject,
					value: {},
				},

				editorPaths: {
					validator: Lang.isArray,
					value: [],
				},

				onBlurMethod: {
					getter: '_getEditorMethod',
					validator: '_validateEditorMethod',
				},

				onChangeMethod: {
					getter: '_getEditorMethod',
					validator: '_validateEditorMethod',
				},

				onFocusMethod: {
					getter: '_getEditorMethod',
					validator: '_validateEditorMethod',
				},

				onInitMethod: {
					getter: '_getEditorMethod',
					validator: '_validateEditorMethod',
				},

				portletId: {
					validator: Lang.isString,
					value: '',
				},

				textMode: {
					validator: Lang.isBoolean,
					value: {},
				},

				useCustomDataProcessor: {
					validator: Lang.isBoolean,
					value: false,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Widget,

			NAME: 'liferayalloyeditor',

			NS: 'liferayalloyeditor',

			prototype: {
				_afterGet(attrName) {
					const instance = this;

					let alterReturn;

					if (attrName === 'form') {
						let parentForm = instance._parentForm;

						if (!parentForm) {
							parentForm = instance._srcNode.ancestor('form');

							instance._parentForm = parentForm;
						}

						alterReturn = new Do.AlterReturn(
							'Return ancestor parent form',
							parentForm
						);
					}
					else if (attrName === 'name') {
						alterReturn = new Do.AlterReturn(
							'Return editor namespace',
							instance.get('namespace')
						);
					}
					else if (attrName === 'type') {
						alterReturn = new Do.AlterReturn(
							'Return editor node name',
							instance._srcNode.get('nodeName')
						);
					}

					return alterReturn;
				},

				_afterVal(value) {
					const instance = this;

					if (value) {
						instance.setHTML(value);
					}

					return new Do.AlterReturn(
						'Return editor content',
						instance.getHTML()
					);
				},

				_changeLocale(localeChange) {
					const instance = this;

					const nativeEditor = instance.getNativeEditor();

					const editable = nativeEditor.editable();

					editable.changeAttr('dir', localeChange.dir);
					editable.changeAttr('lang', localeChange.lang);
				},

				_getEditorMethod(method) {
					return Lang.isFunction(method)
						? method
						: window[method] || method;
				},

				_initializeData() {
					const instance = this;

					const contents = instance.get('contents');

					if (contents) {
						instance.getNativeEditor().setData(contents);
					}

					const onInitFn = instance.get('onInitMethod');

					if (Lang.isFunction(onInitFn)) {
						onInitFn();
					}

					if (instance.pendingFocus) {
						instance.pendingFocus = false;

						instance.focus();
					}
				},

				_onBlur(event) {
					const instance = this;

					const blurFn = instance.get('onBlurMethod');

					if (Lang.isFunction(blurFn)) {
						blurFn(event.editor);
					}
				},

				_onChange() {
					const instance = this;

					const changeFn = instance.get('onChangeMethod');

					if (Lang.isFunction(changeFn)) {
						changeFn(instance.getText());
					}
				},

				_onCustomDataProcessorLoaded() {
					const instance = this;

					instance.customDataProcessorLoaded = true;

					if (instance.instanceReady) {
						instance._initializeData();
					}
				},

				_onDataReady() {
					const instance = this;

					if (instance._pendingData) {
						const pendingData = instance._pendingData;

						instance._pendingData = null;

						instance.getNativeEditor().setData(pendingData);
					}
					else {
						instance._dataReady = true;
					}
				},

				_onError(event) {
					Liferay.Util.openToast({
						message: event.data,
						type: 'danger',
					});
				},

				_onFocus(event) {
					const instance = this;

					const focusFn = instance.get('onFocusMethod');

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
					const instance = this;

					const editorNamespace = instance.get('namespace');

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
							portletId: instance.get('portletId'),
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
								const nativeEditor = instance.getNativeEditor();

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

					const editorConfig = instance.getNativeEditor().config;

					const removeResizePlugin =
						editorConfig.removePlugins &&
						editorConfig.removePlugins.indexOf('ae_dragresize') >
							-1;

					if (CKEDITOR.env.gecko && removeResizePlugin) {
						const doc = instance.getNativeEditor().document.$;

						doc.designMode = 'on';

						doc.execCommand('enableObjectResizing', false, false);
						doc.execCommand(
							'enableInlineTableEditing',
							false,
							false
						);

						doc.designMode = 'off';
					}

					// LPS-118801

					instance.get('editorPaths').forEach((editorPath) => {
						document
							.querySelectorAll(
								`link[href*="${editorPath}"],script[src*="${editorPath}"]`
							)
							.forEach((tag) => {
								tag.setAttribute(
									'data-senna-track',
									'temporary'
								);
							});
					});
				},

				_onKey(event) {
					if (event.data.keyCode === KEY_ENTER) {
						event.cancel();
					}
				},

				_onLocaleChangedHandler(event) {
					const instance = this;

					const contentsLanguage = event.item.getAttribute(
						'data-value'
					);
					const contentsLanguageDir =
						Liferay.Language.direction[contentsLanguage];

					const localeChange = {
						dir: contentsLanguageDir,
						lang: contentsLanguage,
					};

					if (instance.instanceReady) {
						instance._changeLocale(localeChange);
					}
					else {
						instance._pendingLocaleChange = localeChange;
					}
				},

				_onSetData() {
					const instance = this;

					instance._dataReady = false;
				},

				_validateEditorMethod(method) {
					return Lang.isString(method) || Lang.isFunction(method);
				},

				bindUI() {
					const instance = this;

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
						),
					];

					// LPS-84186

					window[
						instance.get('namespace')
					]._localeChangeHandle = Liferay.on(
						'inputLocalized:localeChanged',
						instance._onLocaleChangedHandler,
						instance
					);

					const nativeEditor = instance.getNativeEditor();

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

					const editorConfig = instance.get('editorConfig');

					if (
						editorConfig.disallowedContent &&
						editorConfig.disallowedContent.indexOf('br') !== -1
					) {
						nativeEditor.on('key', instance._onKey, instance);
					}
				},

				destructor() {
					const instance = this;

					const editor = instance._alloyEditor;

					if (editor) {
						editor.destroy();
					}

					new A.EventHandle(instance._eventHandles).detach();

					// LPS-84186

					const localeChangeHandle =
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
					const instance = this;

					if (instance.instanceReady) {
						instance.getNativeEditor().focus();
					}
					else {
						instance.pendingFocus = true;
					}
				},

				getCkData() {
					const instance = this;

					let data = instance.getNativeEditor().getData();

					if (
						CKEDITOR.env.gecko &&
						CKEDITOR.tools.trim(data) === '<br />'
					) {
						data = '';
					}

					return data;
				},

				getEditor() {
					const instance = this;

					return instance._alloyEditor;
				},

				getHTML() {
					const instance = this;

					return instance.get('textMode')
						? instance.getText()
						: instance.getCkData();
				},

				getNativeEditor() {
					const instance = this;

					return instance._alloyEditor.get('nativeEditor');
				},

				getText() {
					const instance = this;

					const editorName = instance.getNativeEditor().name;

					const editor = CKEDITOR.instances[editorName];

					let text = '';

					if (editor) {
						text = editor.editable().getText();
					}

					return text;
				},

				initializer() {
					const instance = this;

					const editorConfig = instance.get('editorConfig');

					let srcNode = editorConfig.srcNode;

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
					const instance = this;

					if (instance.instanceReady) {
						if (instance._dataReady) {
							instance.getNativeEditor().setData(value);
						}
						else {
							instance._pendingData = value;
						}
					}
					else {
						instance.set('contents', value);
					}
				},
			},
		});

		A.LiferayAlloyEditor = LiferayAlloyEditor;
	},
	'',
	{
		requires: ['aui-component', 'liferay-portlet-base', 'timers'],
	}
);
