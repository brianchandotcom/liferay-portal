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

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							RadioField.superclass.getTemplateContext.apply(instance, arguments),
							{
								inline: instance.get('inline'),
								options: instance.get('options')
							}
						);
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						return inputNode.val();
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

						var container = instance.get('container');

						RadioField.superclass.showErrorMessage.apply(instance, arguments);

						container.all('.help-block').appendTo(container.one('.form-group'));
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