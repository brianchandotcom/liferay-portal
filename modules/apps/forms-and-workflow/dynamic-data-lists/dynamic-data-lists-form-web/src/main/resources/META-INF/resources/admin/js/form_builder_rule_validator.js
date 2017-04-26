AUI.add(
	'liferay-ddl-form-builder-rule-validator',
	function(A) {
		var AObject = A.Object;

		var CONDITIONS_OPERATOR = {
			'contains': 'binary',
			'equals-to': 'binary',
			'is-empty': 'unary',
			'not-contains': 'binary',
			'not-equals-to': 'binary',
			'not-is-empty': 'unary'
		};

		var FormBuilderRuleValidator = A.Component.create(
			{
				ATTRS: {
				},

				AUGMENTS: [],

				NAME: 'liferay-ddl-form-builder-rule-validator',

				prototype: {
					isValidRule: function(rule) {
						var instance = this;

						var validConditions = instance._isValidConditions(rule.conditions);

						var validActions = instance._isValidActions(rule.actions);

						return validConditions && validActions;
					},

					_checkPropertyAction: function(action) {
						var instance = this;

						if (action.action === 'show' | action.action === 'enable' | action.action === 'require' | action.action === 'jump-to-page') {
							return action.target;
						}
						else if (action.action === 'auto-fill') {
							var inputs = action.inputs;

							var outputs = action.outputs;

							var requiredInputs = action.requiredInputs;

							if (!AObject.isEmpty(requiredInputs)) {
								return !AObject.isEmpty(outputs) && !AObject.isEmpty(inputs) && instance._checkRequiredInputIsFilled(inputs, requiredInputs);
							}

							return !AObject.isEmpty(outputs);
						}
						else if (action.action === 'calculate') {
							return action.expression && action.target;
						}
					},

					_checkRequiredInputIsFilled: function(inputs, requiredInputs) {
						for (var input in inputs) {
							if (requiredInputs[input]) {
								return true;
							}
						}

						return false;
					},

					_isValidAction: function(action) {
						var instance = this;

						if (action.action === '') {
							return false;
						}

						var actionDone = instance._checkPropertyAction(action);

						return actionDone;
					},

					_isValidActions: function(actions) {
						var instance = this;

						if (actions.length === 0) {
							return false;
						}

						for (var i = 0; i < actions.length; i++) {
							if (!instance._isValidAction(actions[i])) {
								return false;
							}
						}

						return true;
					},

					_isValidConditions: function(conditions) {
						var instance = this;

						if (conditions.length === 0) {
							return false;
						}

						for (var i = 0; i < conditions.length; i++) {
							if (!instance._isValidConditon(conditions[i])) {
								return false;
							}
						}

						return true;
					},

					_isValidConditon: function(condition) {
						var instance = this;

						if (condition.operands.length === 0) {
							return false;
						}

						if (!condition.operator) {
							return false;
						}

						var operatorType = CONDITIONS_OPERATOR[condition.operator];

						if (operatorType === 'unary' && condition.operands.length > 1) {
							return false;
						}

						if (operatorType === 'binary' && condition.operands.length == 2) {
							if (condition.operands[1].type && condition.operands[1].value) {
								return true;
							}

							return false;
						}

						return true;
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderRuleValidator = FormBuilderRuleValidator;
	},
	'',
	{
		requires: []
	}
);