AUI.add(
	'liferay-ddm-form-renderer-field-evaluation',
	function(A) {
		var FieldEvaluationSupport = function() {
		};

		FieldEvaluationSupport.ATTRS = {
			evaluable: {
				value: false
			},

			evaluationTriggerEvents: {
				value: ['valueChange']
			},

			evaluator: {
				getter: '_getEvaluator'
			}
		};

		FieldEvaluationSupport.prototype = {
			initializer: function() {
				var instance = this;

				var evaluationTriggerEvents = instance.get('evaluationTriggerEvents');

				instance._eventHandlers.push(
					instance.after(evaluationTriggerEvents, instance.evaluate)
				);
			},

			evaluate: function() {
				var instance = this;

				var evaluator = instance.get('evaluator');

				if (evaluator && instance.get('rendered') && instance.get('evaluable')) {
					evaluator.evaluate(instance);
				}
			},

			processEvaluationContext: function(context) {
				var instance = this;

				return context;
			},

			_getEvaluator: function() {
				var instance = this;

				var evaluator;

				var root = instance.getRoot();

				if (root) {
					evaluator = root.get('evaluator');
				}

				return evaluator;
			}
		};

		Liferay.namespace('DDM.Renderer').FieldEvaluationSupport = FieldEvaluationSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-expressions-evaluator', 'liferay-ddm-form-renderer-util']
	}
);