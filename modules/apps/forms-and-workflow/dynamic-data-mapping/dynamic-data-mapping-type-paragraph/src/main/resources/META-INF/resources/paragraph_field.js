AUI.add(
	'liferay-ddm-form-field-paragraph',
	function(A) {
		var Lang = A.Lang;

		var ParagraphField = A.Component.create(
			{
				ATTRS: {
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

					setValue: function(value) {
						var instance = this;

						instance.set('text', value || '');
					},

					getValue: function() {
						var instance = this;

						return '';
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