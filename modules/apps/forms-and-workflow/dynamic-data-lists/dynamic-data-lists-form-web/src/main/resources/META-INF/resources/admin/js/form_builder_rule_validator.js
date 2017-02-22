AUI.add(
	'liferay-ddl-form-builder-rule-validator',
	function(A) {
		var FormBuilderRuleValidator = A.Component.create(
			{
				ATTRS: {
				},

				AUGMENTS: [],

				NAME: 'liferay-ddl-form-builder-rule-validator',

				prototype: {
					isValidRule: function(rule) {
						var instance = this;

						return instance._isValidActions(rule.actions);
					},

					_isPropertyAction: function(type) {
						return type === 'show' | type === 'enable' | type === 'require' | type === 'jump-to-page';
					},

					_isValidAction: function(action) {
						var instance = this;

						if (instance._isPropertyAction(action.action) && action.target) {
							return true;
						}

						return false;
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