AUI.add(
	'liferay-ddm-form-field-editor',
	function(A) {
		var Renderer = Liferay.DDM.Renderer;

		var Util = Renderer.Util;

		var EditorField = A.Component.create(
			{
				ATTRS: {
					placeholder: {
						value: ''
					},

					type: {
						value: 'editor'
					}
				},

				EXTENDS: Liferay.DDM.Field.Text,

				NAME: 'liferay-ddm-form-field-editor',

				prototype: {
					destructor: function() {
						var instance = this;

						if (instance._alloyEditor) {
							instance._alloyEditor.destroy();
						}
					},

					getEditorNode: function() {
						var instance = this;

						var container = instance.get('container');

						return container.one('.alloy-editor');
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							EditorField.superclass.getTemplateContext.apply(instance, arguments),
							{
								placeholder: instance.get('placeholder')
							}
						);
					},

					getValue: function() {
						var instance = this;

						var value = EditorField.superclass.getValue.apply(instance, arguments);

						if (instance._alloyEditor) {
							value = instance._alloyEditor.getHTML();
						}

						return value;
					},

					hasFocus: function() {
						var instance = this;

						return EditorField.superclass.hasFocus.apply(instance, arguments) || instance._hasAlloyEditorFocus();
					},

					render: function() {
						var instance = this;

						EditorField.superclass.render.apply(instance, arguments);

						var editorNode = instance.getEditorNode();

						if (editorNode.inDoc() && !instance.get('readOnly')) {
							var name = instance.getQualifiedName();

							var value = instance.get('value');

							editorNode.html(value);

							window[name] = {};

							instance._alloyEditor = new A.LiferayAlloyEditor(
								{
									contents: value,
									editorConfig: {
										extraPlugins: 'ae_placeholder,ae_selectionregion,ae_uicore',
										removePlugins: 'contextmenu,elementspath,image,link,liststyle,resize,tabletools,toolbar',
										srcNode: editorNode,
										toolbars: {
											add: {
												buttons: ['hline', 'table']
											},
											styles: {
												selections: AlloyEditor.Selections,
												tabIndex: 1
											}
										}
									},
									namespace: name,
									onChangeMethod: A.bind(A.debounce(instance._onChangeEditor, 100), instance),
									plugins: [],
									textMode: false
								}
							).render();

							instance._alloyEditor.getNativeEditor().on('actionPerformed', A.bind(instance._onActionPerformed, instance));
						}

						return instance;
					},

					setValue: function(value) {
						var instance = this;

						EditorField.superclass.setValue.apply(instance, arguments);

						if (instance._alloyEditor && value !== instance.getValue()) {
							instance._alloyEditor.setHTML(value);
						}
					},

					_findAncestor: function(node, ancestorClass) {
						var child = node;

						while (child && !child.classList.contains(ancestorClass)) {
							child = child.parentElement;
						}

						return child;
					},

					_hasAlloyEditorFocus: function() {
						var instance = this;

						return !!instance._findAncestor(document.activeElement, 'ae-ui');
					},

					_onActionPerformed: function() {
						var instance = this;

						instance._onChangeEditor(instance.getValue());
					},

					_onChangeEditor: function(value) {
						var instance = this;

						var inputNode = instance.getInputNode();

						if (inputNode && !Util.compare(value, inputNode.val())) {
							inputNode.val(value);

							instance.set('value', value);
						}
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Editor = EditorField;
	},
	'',
	{
		requires: ['liferay-alloy-editor', 'liferay-ddm-form-field-text', 'liferay-ddm-form-renderer-field']
	}
);