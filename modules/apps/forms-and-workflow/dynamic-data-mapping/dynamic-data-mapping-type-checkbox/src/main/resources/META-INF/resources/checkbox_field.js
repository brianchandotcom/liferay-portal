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
					getBooleanValue: function(value) {
						var instance = this;

						return value === 'true' || value === true;
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							CheckboxField.superclass.getTemplateContext.apply(instance, arguments),
							{
								predefinedValue: instance.getBooleanValue(instance.get('predefinedValue')),
								showAsSwitcher: instance.get('showAsSwitcher'),
								value: instance.getBooleanValue(instance.get('value'))
							}
						);
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						return inputNode.attr('checked');
					},

					hasFocus: function(node) {
						var instance = this;

						var hasFocus = false;

						if (node && node.hasClass('toggle-switch')) {
							hasFocus = (node.getAttribute('type') === instance.get('type'));
						}
						else {
							hasFocus = CheckboxField.superclass.hasFocus.apply(instance, arguments);
						}

						return hasFocus;
					},

					setValue: function(value) {
						var instance = this;

						instance.set('predefinedValue', value);
						instance.set('value', value);

						instance.render();
					},

					showErrorMessage: function() {
						var instance = this;

						CheckboxField.superclass.showErrorMessage.apply(instance, arguments);
					},

					_onValueChange: function(event) {
						var instance = this;

						var value = instance.getValue();

						instance.setValue(value);

						instance._fireStartedFillingEvent();
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