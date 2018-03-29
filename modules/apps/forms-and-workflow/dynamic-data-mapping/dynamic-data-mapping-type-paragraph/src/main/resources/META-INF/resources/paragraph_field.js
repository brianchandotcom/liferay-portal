AUI.add(
	'liferay-ddm-form-field-paragraph',
	function(A) {
		var Lang = A.Lang;

		var ParagraphField = A.Component.create(
			{
				ATTRS: {
					context: {
						getter: '_getContext'
					},

					dataType: {
						value: ''
					},

					required: {
						readOnly: true,
						value: false
					},

					text: {
						value: ''
					},

					type: {
						value: 'paragraph'
					},

					validation: {
						readOnly: true,
						value: {
							errorMessage: '',
							expression: 'true'
						}
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-paragraph',

				prototype: {
					focus: function() {
						var instance = this;

						instance.get('container').focus();
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							ParagraphField.superclass.getTemplateContext.apply(instance, arguments),
							{
								text: instance.get('text')
							}
						);
					},

					getValue: function() {
						var instance = this;

						return '';
					},

					setValue: function(value) {
						var instance = this;

						instance.set('text', value || '');
					},

					_getContext: function(context) {
						var instance = this;

						if (!context) {
							return {};
						}
						else if (context.text && Lang.isObject(context.text) && !Lang.isFunction(context.text)) {
							return A.merge(
								context,
								{
									text: window.DDMParagraph.render.Soy.toIncDom(context.text.content)
								}
							);
						}
						return context;
					},

					_renderErrorMessage: Lang.emptyFn
				}
			}
		);

		Liferay.namespace('DDM.Field').Paragraph = ParagraphField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);