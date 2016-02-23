/* global AlloyEditor */

AUI.add(
	'liferay-alloy-editor',
	function(A) {
		var Do = A.Do;
		var Lang = A.Lang;

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
					editorConfig: {
						validator: Lang.isObject,
						value: {}
					},

					onBlurMethod: {
						validator: Lang.isFunction
					},

					onChangeMethod: {
						validator: Lang.isFunction
					},

					onFocusMethod: {
						validator: Lang.isFunction
					},

					onInitMethod: {
						validator: Lang.isFunction
					},

					textMode: {
						validator: Lang.isBoolean,
						value: {}
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

						instance._alloyEditor = AlloyEditor.editable(editorConfig.srcNode, editorConfig);
						instance._srcNode = A.one('#' + editorConfig.srcNode);
					},

					bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							Do.after('_afterGet', instance._srcNode, 'get', instance)
						];

						var nativeEditor = instance.getNativeEditor();

						nativeEditor.on('paste', instance._onPaste, instance);
						nativeEditor.on('instanceReady', instance._onInstanceReady, instance);

						if (instance.get('onBlurMethod')) {
							nativeEditor.on('blur', instance._onBlur, instance);
						}

						if (instance.get('onChangeMethod')) {
							nativeEditor.on('change', instance._onChange, instance);
						}

						if (instance.get('onFocusMethod')) {
							nativeEditor.on('focus', instance._onFocus, instance);
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
					},

					focus: function() {
						var instance = this;

						instance.getNativeEditor().focus();
					},

					getCkData: function() {
						var instance = this;

						var data = instance.getNativeEditor().getData();

						if (CKEDITOR.env.gecko && CKEDITOR.tools.trim(data) === '<br />') {
							data = '';
						}

						return data;
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

						instance.getNativeEditor().setData(value);
					},

					_afterGet: function(attrName) {
						var instance = this;

						if (attrName === 'form') {
							var parentForm = instance._parentForm;

							if (!parentForm) {
								parentForm = instance._srcNode.ancestor('form');

								instance._parentForm = parentForm;
							}

							return new Do.AlterReturn(
								'Return ancestor parent form',
								parentForm
							);
						}
					},

					_onBlur: function(event) {
						var instance = this;

						var blurFn = instance.get('onBlurMethod');

						blurFn(event.editor);
					},

					_onChange: function() {
						var instance = this;

						var changeFn = instance.get('onChangeMethod');

						changeFn(instance.getText());
					},

					_onFocus: function(event) {
						var instance = this;

						var focusFn = instance.get('onFocusMethod');

						focusFn(event.editor);
					},

					_onInstanceReady: function() {
						var instance = this;

						var onInitFn = instance.get('onInitMethod');

						if (onInitFn) {
							onInitFn();
						}

						instance.instanceReady = true;
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
					}
				}
			}
		);

		A.LiferayAlloyEditor = LiferayAlloyEditor;
	},
	'',
	{
		requires: ['aui-component', 'liferay-portlet-base']
	}
);