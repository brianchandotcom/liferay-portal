AUI.add(
	'liferay-ddm-form-field-checkbox',
	function(A) {
		var DataTypeBoolean = A.DataType.Boolean;

		var CheckboxField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'boolean'
					},

					showAsSwitcher: {
						state: true,
						value: false
					},

					type: {
						value: 'checkbox'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-checkbox',

				prototype: {
					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							CheckboxField.superclass.getTemplateContext.apply(instance, arguments),
							{
								showAsSwitcher: instance.get('showAsSwitcher')
							}
						);
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						return inputNode.attr('checked');
					},

					setValue: function(value) {
						var instance = this;

						var inputNode = instance.getInputNode();

						inputNode.attr('checked', DataTypeBoolean.parse(value));
					},

					showErrorMessage: function() {
						var instance = this;

						CheckboxField.superclass.showErrorMessage.apply(instance, arguments);
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