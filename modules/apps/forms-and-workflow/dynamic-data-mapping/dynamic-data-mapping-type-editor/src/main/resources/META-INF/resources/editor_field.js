AUI.add(
	'liferay-ddm-form-field-editor',
	function(A) {
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
								placeholder: instance.getLocalizedValue(instance.get('placeholder'))
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

					render: function() {
						var instance = this;

						EditorField.superclass.render.apply(instance, arguments);

						var editorNode = instance.getEditorNode();

						if (editorNode.inDoc() && !instance.get('readOnly')) {
							var name = instance.getQualifiedName();

							var value = instance.getContextValue();

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
						}

						return instance;
					},

					setValue: function(value) {
						var instance = this;

						EditorField.superclass.setValue.apply(instance, arguments);

						if (instance._alloyEditor) {
							instance._alloyEditor.setHTML(value);
						}
					},

					_onChangeEditor: function() {
						var instance = this;

						var value = instance._alloyEditor.getHTML();

						instance.getInputNode().val(value);

						instance.fire(
							'valueChanged',
							{
								field: instance,
								value: value
							}
						);
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