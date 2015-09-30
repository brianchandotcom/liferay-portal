AUI.add(
	'liferay-ddm-form-field-radio',
	function(A) {
		var Lang = A.Lang;

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

						var radiosNodeList = container.all(instance.getInputSelector());

						var inputNode = radiosNodeList.item(0);

						var checkedNodeList = radiosNodeList.filter(':checked');

						if (checkedNodeList.size()) {
							inputNode = checkedNodeList.item(0);
						}

						return inputNode;
					},

					getOptions: function() {
						var instance = this;

						var value = instance.get('value');

						if (Lang.isObject(value)) {
							value = value[instance.get('locale')];
						}

						return A.map(
							instance.get('options'),
							function(item) {
								return {
									label: item.label[instance.get('locale')],
									status: value === item.value ? 'checked' : '',
									value: item.value
								};
							}
						);
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							RadioField.superclass.getTemplateContext.apply(instance, arguments),
							{
								inline: instance.get('inline'),
								options: instance.getOptions()
							}
						);
					},

					_renderErrorMessage: function() {
						var instance = this;

						var container = instance.get('container');

						RadioField.superclass._renderErrorMessage.apply(instance, arguments);

						container.all('.validation-message').appendTo(container);
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