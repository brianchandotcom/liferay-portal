/* global AlloyEditor */

AUI.add(
	'liferay-alloy-editor',
	function(A) {
		var Do = A.Do;
		var Lang = A.Lang;
		var UA = A.UA;

		var contentFilter = new CKEDITOR.filter(
			{
				$1: {
					attributes: ['alt', 'aria-*', 'height', 'href', 'src', 'width'],
					classes: false,
					elements: CKEDITOR.dtd,
					styles: false
				}
			}
		);

		var KEY_ENTER = 13;

		var LiferayAlloyEditor = A.Component.create(
			{
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
					initializer: function() {
						var instance = this;

						var editorConfig = instance.get('editorConfig');

						var srcNode = editorConfig.srcNode;

						if (Lang.isString(srcNode)) {
							srcNode = A.one('#' + srcNode);
						}

						instance._alloyEditor = AlloyEditor.editable(srcNode.attr('id'), editorConfig);
						instance._srcNode = srcNode;
					},

					bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							Do.after('_afterGet', instance._srcNode, 'get', instance),
							Do.after('_afterVal', instance._srcNode, 'val', instance)
						];

						var nativeEditor = instance.getNativeEditor();

						nativeEditor.on('error', instance._onError, instance);
						nativeEditor.on('instanceReady', instance._onInstanceReady, instance);
						nativeEditor.on('paste', instance._onPaste, instance);

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
							nativeEditor.on('customDataProcessorLoaded', instance._onCustomDataProcessorLoaded, instance);
						}

						var editorConfig = instance.get('editorConfig');

						if (editorConfig.disallowedContent && editorConfig.disallowedContent.indexOf('br') !== -1) {
							nativeEditor.on('key', instance._onKey, instance);
						}
					},

					destructor: function() {
						var instance = this;

						var editor = instance._alloyEditor;

						if (editor) {
							editor.destroy();
						}

						(new A.EventHandle(instance._eventHandles)).detach();

						instance.instanceReady = false;

						window[instance.get('namespace')].instanceReady = false;
					},

					focus: function() {
						var instance = this;

						if (instance.instanceReady) {
							instance.getNativeEditor().focus();
						}
						else {
							instance.pendingFocus = true;
						}
					},

					getCkData: function() {
						var instance = this;

						var data = instance.getNativeEditor().getData();

						if (CKEDITOR.env.gecko && CKEDITOR.tools.trim(data) === '<br />') {
							data = '';
						}

						return data;
					},

					getEditor: function() {
						var instance = this;

						return instance._alloyEditor;
					},

					getHTML: function() {
						var instance = this;

						return instance.get('textMode') ? instance.getText() : instance.getCkData();
					},

					getNativeEditor: function() {
						var instance = this;

						return instance._alloyEditor.get('nativeEditor');
					},

					getText: function() {
						var instance = this;

						var editorName = instance.getNativeEditor().name;

						var editor = CKEDITOR.instances[editorName];

						var text = '';

						if (editor) {
							text = editor.editable().getText();
						}

						return text;
					},

					setHTML: function(value) {
						var instance = this;

						if (instance.instanceReady) {
							instance.getNativeEditor().setData(value);
						}
						else {
							instance.set('contents', value);
						}
					},

					_afterGet: function(attrName) {
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

					_afterVal: function(value) {
						var instance = this;

						if (value) {
							instance.setHTML(value);
						}

						return new Do.AlterReturn(
							'Return editor content',
							instance.getHTML()
						);
					},

					_getEditorMethod: function(method) {
						return Lang.isFunction(method) ? method : (window[method] || method);
					},

					_initializeData: function() {
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

					_onBlur: function(event) {
						var instance = this;

						var blurFn = instance.get('onBlurMethod');

						if (Lang.isFunction(blurFn)) {
							blurFn(event.editor);
						}
					},

					_onChange: function() {
						var instance = this;

						var changeFn = instance.get('onChangeMethod');

						if (Lang.isFunction(changeFn)) {
							changeFn(instance.getText());
						}
					},

					_onCustomDataProcessorLoaded: function() {
						var instance = this;

						instance.customDataProcessorLoaded = true;

						if (instance.instanceReady) {
							instance._initializeData();
						}
					},

					_onError: function(event) {
						new Liferay.Notification(
							{
								closeable: true,
								delay: {
									hide: 5000,
									show: 0
								},
								duration: 500,
								message: event.data,
								title: Liferay.Language.get('error'),
								type: 'danger'
							}
						).render();
					},

					_onFocus: function(event) {
						var instance = this;

						var focusFn = instance.get('onFocusMethod');

						if (Lang.isFunction(focusFn)) {
							focusFn(event.editor);
						}
					},

					_onFocusFix: function(activeElement, nativeEditor) {
						var instance = this;

						setTimeout(
							function() {
								if (activeElement) {
									nativeEditor.focusManager.blur(true);
									activeElement.focus();
								}
							},
							100
						);
					},

					_onInstanceReady: function() {
						var instance = this;

						instance.instanceReady = true;

						var editorNamespace = instance.get('namespace');

						window[editorNamespace].instanceReady = true;

						Liferay.component(editorNamespace, window[editorNamespace]);

						if (instance.customDataProcessorLoaded || !instance.get('useCustomDataProcessor')) {
							instance._initializeData();
						}

						// LPS-73775

						instance.getNativeEditor().editable().$.addEventListener('compositionend', A.bind('_onChange', instance));

						// LPS-71967

						if (UA.edge && parseInt(UA.edge, 10) >= 14) {
							A.soon(
								function() {
									var nativeEditor = instance.getNativeEditor();

									nativeEditor.once('focus', A.bind('_onFocusFix', instance, document.activeElement, nativeEditor));
									nativeEditor.focus();
								}
							);
						}

						// LPS-72963

						var editorConfig = instance.getNativeEditor().config;

						var removeResizePlugin = editorConfig.removePlugins && editorConfig.removePlugins.indexOf('ae_dragresize') > -1;

						if (CKEDITOR.env.gecko && removeResizePlugin) {
							var doc = instance.getNativeEditor().document.$;

							doc.designMode = 'on';

							doc.execCommand('enableObjectResizing', false, false);
							doc.execCommand('enableInlineTableEditing', false, false);

							doc.designMode = 'off';
						}
					},

					_onKey: function(event) {
						if (event.data.keyCode === KEY_ENTER) {
							event.cancel();
						}
					},

					_onPaste: function(event) {
						var fragment = CKEDITOR.htmlParser.fragment.fromHtml(event.data.dataValue);

						var writer = new CKEDITOR.htmlParser.basicWriter();

						contentFilter.applyTo(fragment);

						fragment.writeHtml(writer);

						event.data.dataValue = writer.getHtml();
					},

					_validateEditorMethod: function(method) {
						return Lang.isString(method) || Lang.isFunction(method);
					}
				}
			}
		);

		A.LiferayAlloyEditor = LiferayAlloyEditor;
	},
	'',
	{
		requires: ['aui-component', 'liferay-notification', 'liferay-portlet-base', 'timers']
	}
);