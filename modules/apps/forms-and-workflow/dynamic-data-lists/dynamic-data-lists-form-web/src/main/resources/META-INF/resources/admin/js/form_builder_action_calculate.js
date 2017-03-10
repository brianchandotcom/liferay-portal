AUI.add(
	'liferay-ddl-form-builder-action-calculate',
	function(A) {
		var Lang = A.Lang;

		var FormBuilderActionCalculate = A.Component.create(
			{
				ATTRS: {
					action: {
						value: ''
					},

					index: {
						value: ''
					},

					options: {
						value: []
					}
				},

				AUGMENTS: [],

				EXTENDS: Liferay.DDL.FormBuilderAction,

				NAME: 'liferay-ddl-form-builder-action-calculate',

				prototype: {
					getValue: function() {
						var instance = this;

						return {
							action: 'calculate',
							expression: instance._expressionField.getValue(),
							target: instance._targetField.getValue()
						};
					},

					render: function() {
						var instance = this;

						var strings = instance.get('strings');

						var index = instance.get('index');

						var container = instance.get('boundingBox').one('.additional-info-' + index);

						container.addClass('col-md-6');

						instance._createTargetField().render(container);
						instance._createExpressionField().render(container);
					},

					_createExpressionField: function() {
						var instance = this;

						var value;

						var action = instance.get('action');

						if (action && action.expression) {
							value = action.expression;
						}

						instance._expressionField = new Liferay.DDM.Field.Text(
							{
								displayStyle: 'multiline',
								fieldName: instance.get('index') + '-action',
								label: Liferay.Language.get('expression'),
								value: value,
								visible: true
							}
						);

						return instance._expressionField;
					},

					_createTargetField: function() {
						var instance = this;

						var value;

						var action = instance.get('action');

						if (action && action.target) {
							value = action.target;
						}

						instance._targetField = new Liferay.DDM.Field.Select(
							{
								fieldName: instance.get('index') + '-action',
								label: Liferay.Language.get('field'),
								options: instance.get('options'),
								value: value,
								visible: true
							}
						);

						return instance._targetField;
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderActionCalculate = FormBuilderActionCalculate;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-action']
	}
);