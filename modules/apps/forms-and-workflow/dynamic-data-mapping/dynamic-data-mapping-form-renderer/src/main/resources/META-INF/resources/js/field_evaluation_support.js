AUI.add(
	'liferay-ddm-form-renderer-field-evaluation',
	function(A) {
		var FieldEvaluationSupport = function() {
		};

		FieldEvaluationSupport.ATTRS = {
			enableEvaluations: {
				value: true
			},

			evaluator: {
				getter: '_getEvaluator'
			}
		};

		FieldEvaluationSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.after('valueChanged', instance._afterValueChanged)
				);
			},

			evaluate: function() {
				var instance = this;

				var evaluator = instance.get('evaluator');

				if (evaluator) {
					evaluator.evaluate(instance);
				}
			},

			processEvaluationContext: function(context) {
				var instance = this;

				return context;
			},

			_afterValueChanged: function() {
				var instance = this;

				instance.evaluate();
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