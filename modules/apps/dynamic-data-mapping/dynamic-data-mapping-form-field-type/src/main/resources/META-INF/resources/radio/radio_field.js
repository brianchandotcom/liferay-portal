AUI.add(
	'liferay-ddm-form-field-radio',
	function(A) {
		var RadioField = A.Component.create(
			{
				ATTRS: {
					inline: {
						value: true
					},

					options: {
						state: true,
						validator: Array.isArray,
						value: []
					},

					type: {
						value: 'radio'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-radio',

				prototype: {
					getInputNode: function() {
						var instance = this;

						var container = instance.get('container');

						var inputNode = container.one('input[type="radio"]:checked');

						if (inputNode === null) {
							inputNode = container.one('input[type="radio"]');
						}

						return inputNode;
					},

					getPredefinedValue: function() {
						var instance = this;

						var predefinedValue = instance.get('predefinedValue');

						if (A.Lang.isArray(predefinedValue)) {
							return predefinedValue[0];
						}

						return predefinedValue;
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							RadioField.superclass.getTemplateContext.apply(instance, arguments),
							{
								inline: instance.get('inline'),
								options: instance.get('options'),
								predefinedValue: instance.getPredefinedValue()
							}
						);
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						var value = '';

						if (inputNode.attr('checked')) {
							value = inputNode.val();
						}

						return value;
					},

					setValue: function(value) {
						var instance = this;

						var container = instance.get('container');

						var radiosNodeList = container.all('input[type="radio"]');

						radiosNodeList.removeAttribute('checked');

						var radioToCheck = radiosNodeList.filter(
							function(node) {
								return node.val() === value;
							}
						).item(0);

						if (radioToCheck) {
							radioToCheck.attr('checked', true);
						}
					},

					showErrorMessage: function() {
						var instance = this;

						RadioField.superclass.showErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var formGroup = container.one('.form-group');

						formGroup.insert(container.one('.form-feedback-indicator'), 'after');
					},

					showPendingErrorMessage: function() {
						var instance = this;

						if (!instance.hasFocus()) {
							instance.showErrorMessage();
						}
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Radio = RadioField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);