AUI.add(
	'liferay-ddm-form-builder-action-calculate',
	function(A) {
		var CSS_CALCULATE_CONTAINER_CALCULATOR = A.getClassName('calculate', 'container', 'calculator', 'component');

		var CSS_CALCULATE_CONTAINER_FIELDS = A.getClassName('calculate', 'container', 'fields');

		var OPERATORS_MAP = ['+', '-', '*', '/', '.'];

		var FormBuilderActionCalculate = A.Component.create(
			{
				ATTRS: {
					action: {
						value: ''
					},

					builder: {
						value: {}
					},

					functions: {
						value: ''
					},

					index: {
						value: ''
					},

					options: {
						value: []
					},

					type: {
						value: 'calculate'
					}
				},

				AUGMENTS: [],

				EXTENDS: Liferay.DDM.FormBuilderAction,

				NAME: 'liferay-ddm-form-builder-action-calculate',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._regexExpression = new RegExp('\\[([^\\]]+)\\]|sum|(.)', 'g');

						instance.on('liferay-ddm-form-builder-calculator:clickedKey', A.bind(instance._handleClickedKey, instance));
					},

					getValue: function() {
						var instance = this;

						return {
							action: 'calculate',
							expression: instance._getCalculateKeyActions().join().replace(/\,/g, ''),
							target: instance._targetField.getValue()[0] || ''
						};
					},

					render: function() {
						var instance = this;

						var index = instance.get('index');

						var boundingBox = instance.get('boundingBox');

						var builder = instance.get('builder');

						var calculateContainer = boundingBox.one('.additional-info-' + index);

						calculateContainer.setHTML(instance._getRuleContainerTemplate());

						var payload = {
							languageId: builder.get('defaultLanguageId')
						};

						A.io.request(
							Liferay.DDM.Settings.getFunctionsURL,
							{
								data: payload,
								method: 'GET',
								on: {
									success: function(event, id, xhr) {
										var result = JSON.parse(xhr.responseText);

										instance.set('functions', result);

										instance._getCalculator().render(calculateContainer.one('.' + CSS_CALCULATE_CONTAINER_CALCULATOR));
									}
								}
							}
						);

						var expressionField = instance._createExpressionField();

						expressionField.render(calculateContainer.one('.' + CSS_CALCULATE_CONTAINER_FIELDS));

						expressionField.getInputNode().setAttribute('disabled');

						instance._createTargetField().render(calculateContainer.one('.' + CSS_CALCULATE_CONTAINER_FIELDS));
					},

					_createCalculator: function() {
						var instance = this;

						var calculator = new Liferay.DDM.FormBuilderCalculator(
							{
								functions: instance.get('functions'),
								options: instance._getNumericFields()
							}
						);

						calculator.addTarget(instance);

						return calculator;
					},

					_createExpressionField: function() {
						var instance = this;

						var value = '';

						var action = instance.get('action');

						if (action && action.expression) {
							instance._setCalculateKeyActions(action.expression.match(instance._regexExpression));

							value = action.expression.replace(/\[|\]/g, '');
						}

						instance._expressionField = instance.createTextField(
							{
								displayStyle: 'multiline',
								fieldName: instance.get('index') + '-action',
								placeholder: Liferay.Language.get('the-expression-will-be-displayed-here'),
								readOnly: true,
								value: value,
								visible: true
							}
						);

						return instance._expressionField;
					},

					_createTargetField: function() {
						var instance = this;

						var action = instance.get('action');

						var value = [];

						if (action && action.target) {
							value = [action.target];
						}

						instance._targetField = instance.createSelectField(
							{
								fieldName: instance.get('index') + '-action',
								label: Liferay.Language.get('choose-a-field-to-show-the-result'),
								options: instance._getNumericFields(),
								value: value,
								visible: true
							}
						);

						var container = instance._targetField.get('container');

						container.addClass('calculate-field-target');

						return instance._targetField;
					},

					_getCalculateKeyActions: function() {
						var instance = this;

						if (!instance._keyActions) {
							instance._keyActions = [];
						}

						return instance._keyActions;
					},

					_getCalculator: function() {
						var instance = this;

						if (!instance._calculator) {
							instance._calculator = instance._createCalculator();
						}

						return instance._calculator;
					},

					_getNumericFields: function() {
						var instance = this;

						var fields = instance.get('options');

						var numericFields = [];

						for (var i = 0; i < fields.length; i++) {
							if (fields[i].dataType === 'integer' || fields[i].dataType === 'double') {
								numericFields.push(fields[i]);
							}
						}

						return numericFields;
					},

					_getRuleContainerTemplate: function() {
						var instance = this;

						var calculateTemplateRenderer = Liferay.DDM.SoyTemplateUtil.getTemplateRenderer('DDMCalculate.render');

						var container = document.createDocumentFragment();

						new calculateTemplateRenderer({}, container);

						return container.firstChild.outerHTML;
					},

					_handleClickedKey: function(event) {
						var instance = this;

						if (event.key !== undefined) {
							if (event.key === 'backspace') {
								instance._getCalculateKeyActions().pop();
							}
							else {
								instance._removeRepeatedOperatorKey(event.key);

								instance._getCalculateKeyActions().push(event.key);
							}
						}

						instance._expressionField.set('value', instance._processExpressionString());
						instance._expressionField.render();
					},

					_processExpressionString: function(keyActions) {
						var instance = this;

						return instance._getCalculateKeyActions().join('').replace(/\[|\]/g, '');
					},

					_removeRepeatedOperatorKey: function(key) {
						var instance = this;

						if (OPERATORS_MAP.includes(key) && instance._getCalculateKeyActions().length >= 1) {
							var lastKey = instance._getCalculateKeyActions()[instance._getCalculateKeyActions().length - 1];

							if (OPERATORS_MAP.includes(lastKey)) {
								instance._getCalculateKeyActions().pop();
							}
						}
					},

					_setCalculateKeyActions: function(value) {
						var instance = this;

						instance._keyActions = value;
					}
				}
			}
		);

		Liferay.namespace('DDM').FormBuilderActionCalculate = FormBuilderActionCalculate;
	},
	'',
	{
		requires: ['liferay-ddm-form-builder-action', 'liferay-ddm-form-builder-calculator']
	}
);