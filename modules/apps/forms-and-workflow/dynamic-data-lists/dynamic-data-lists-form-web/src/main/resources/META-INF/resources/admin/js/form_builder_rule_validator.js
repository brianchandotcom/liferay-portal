AUI.add(
	'liferay-ddl-form-builder-rule-validator',
	function(A) {
		var CONDITIONS_OPERATOR = {
			'equals-to': 'binary',
			'is-empty': 'unary',
			'not-is-empty': 'unary',
			'not-equals-to': 'binary',
			'contains': 'binary',
			'not-contains': 'binary'
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

						return  validConditions && validActions;
					},

					_isPropertyAction: function(type) {
						return type === 'show' | type === 'enable' | type === 'require' | type === 'jump-to-page' | type === 'auto-fill' | type === 'calculate';
					},

					_isValidAction: function(action) {
						var instance = this;

						if (action.action === '') {
							return false;
						}

						if (instance._isPropertyAction(action.action)) {
							return action.target;
						}

						return true;
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
						
						if (operatorType === 'binary' && condition.operands.length == 2){
							if (condition.operands[1].type && condition.operands[1].value) {
								return true;
							} 
							else {
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