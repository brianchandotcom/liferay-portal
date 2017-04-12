AUI.add(
	'liferay-ddl-form-builder-action-calculate',
	function(A) {
		var CSS_CALCULATE_CONTAINER_CALCULATOR = A.getClassName('calculate', 'container', 'calculator', 'component');

		var CSS_CALCULATE_CONTAINER_FIELDS = A.getClassName('calculate', 'container', 'fields');

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

						var index = instance.get('index');

						var boundingBox = instance.get('boundingBox');

						var calculateContainer = boundingBox.one('.additional-info-' + index);

						calculateContainer.setHTML(instance._getRuleContainerTemplate());

						instance._getCalculator().render(calculateContainer.one('.' + CSS_CALCULATE_CONTAINER_CALCULATOR));

						var expressionField = instance._createExpressionField();

						expressionField.render(calculateContainer.one('.' + CSS_CALCULATE_CONTAINER_FIELDS));

						expressionField.getInputNode().setAttribute('disabled');

						instance._createTargetField().render(calculateContainer.one('.' + CSS_CALCULATE_CONTAINER_FIELDS));
					},

					_createCalculator: function() {
						var instance = this;

						var calculator = new Liferay.DDL.FormBuilderCalculator(
							{
								options: instance.get('options')
							}
						);

						return calculator;
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
								placeholder: Liferay.Language.get('the-expression-will-be-displayed-here'),
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
								label: Liferay.Language.get('choose-a-field-to-show-the-result'),
								options: instance.get('options'),
								value: value,
								visible: true
							}
						);

						var container = instance._targetField.get('container');

						container.addClass('calculate-field-target');

						return instance._targetField;
					},

					_getCalculator: function() {
						var instance = this;

						if (!instance._calculator) {
							instance._calculator = instance._createCalculator();
						}

						return instance._calculator;
					},

					_getRuleContainerTemplate: function() {
						var instance = this;

						var calculateTemplateRenderer = Liferay.DDM.SoyTemplateUtil.getTemplateRenderer('ddl.calculate.settings');

						return calculateTemplateRenderer();
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderActionCalculate = FormBuilderActionCalculate;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-action', 'liferay-ddl-form-builder-calculator']
	}
);