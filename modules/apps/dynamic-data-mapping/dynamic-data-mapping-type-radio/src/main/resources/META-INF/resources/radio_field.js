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
					getContextValue: function() {
						var instance = this;

						var value = RadioField.superclass.getContextValue.apply(instance, arguments);

						if (!Lang.isArray(value)) {
							try {
								value = JSON.parse(value);
							}
							catch (e) {
								value = [value];
							}
						}

						return value[0];
					},

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

						var value = instance.getContextValue();

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

					getValue: function() {
						var instance = this;

						var container = instance.get('container');

						var radiosNodeList = container.all(instance.getInputSelector());

						var checkedNodeList = radiosNodeList.filter(':checked');

						var value = '';

						if (checkedNodeList.size()) {
							value = checkedNodeList.item(0).val();
						}

						return value;
					},

					setValue: function(value) {
						var instance = this;

						var container = instance.get('container');

						var radiosNodeList = container.all(instance.getInputSelector());

						radiosNodeList.attr('checked', false);

						var radiosToCheck = radiosNodeList.filter(
							function(node) {
								return node.val() === value;
							}
						);

						radiosToCheck.attr('checked', true);
					},

					_renderErrorMessage: function() {
						var instance = this;

						var container = instance.get('container');

						RadioField.superclass._renderErrorMessage.apply(instance, arguments);

						container.all('.help-block').appendTo(container.one('.form-group'));
					},

					_showFeedback: function() {
						var instance = this;

						RadioField.superclass._showFeedback.apply(instance, arguments);

						var container = instance.get('container');

						var feedBack = container.one('.form-control-feedback');

						feedBack.appendTo(container);
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