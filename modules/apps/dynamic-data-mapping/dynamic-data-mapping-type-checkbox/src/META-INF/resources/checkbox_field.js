AUI.add(
	'liferay-ddm-form-field-checkbox',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var CheckboxField = A.Component.create(
			{
				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-checkbox',

				prototype: {
					getTemplateContext: function() {
						var instance = this;

						return A.merge(
								CheckboxField.superclass.getTemplateContext.apply(instance, arguments),
							{
								status: instance.get('value') ? 'checked' : ''
							}
						);
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						return inputNode.attr('checked');
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Checkbox = CheckboxField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);