AUI.add(
	'liferay-ddm-form-field-checkbox',
	function(A) {
		var CheckboxField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'boolean'
					},

					showAsSwitcher: {
						value: false
					},

					type: {
						value: 'checkbox'
					},

					value: {
						setter: '_setValue'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-checkbox',

				prototype: {
					getTemplateContext: function() {
						var instance = this;

						var value = instance.getContextValue();

						return A.merge(
							CheckboxField.superclass.getTemplateContext.apply(instance, arguments),
							{
								showAsSwitcher: instance.get('showAsSwitcher'),
								status: value ? 'checked' : ''
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

						inputNode.attr('checked', !!value);
					},

					_renderErrorMessage: function() {
						var instance = this;

						var container = instance.get('container');

						CheckboxField.superclass._renderErrorMessage.apply(instance, arguments);

						container.all('.help-block').appendTo(container);
					},

					_setValue: function(value) {
						var instance = this;

						if (instance.get('localizable')) {
							for (var locale in value) {
								value[locale] = A.DataType.Boolean.parse(value[locale]);
							}
						}
						else {
							value = A.DataType.Boolean.parse(value);
						}

						return value;
					},

					_showFeedback: function() {
						var instance = this;

						var container = instance.get('container');

						CheckboxField.superclass._showFeedback.apply(instance, arguments);

						container.all('.form-control-feedback').appendTo(container);
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