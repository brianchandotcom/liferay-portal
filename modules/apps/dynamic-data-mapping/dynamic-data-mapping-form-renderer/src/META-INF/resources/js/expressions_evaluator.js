AUI.add(
	'liferay-ddm-form-renderer-expressions-evaluator',
	function(A) {
		var ExpressionsEvaluator = A.Component.create(
			{
				ATTRS: {
					evaluationURL: {
						value: '/o/ddm-form-evaluator/'
					},

					form: {
					}
				},

				NAME: 'liferay-ddm-form-renderer-expressions-evaluator',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.after('evaluationEnded', instance._afterEvaluationEnded);
						instance.after('evaluationStarted', instance._afterEvaluationStarted);
					},

					evaluate: function(callback) {
						var instance = this;

						var form = instance.get('form');

						if (form && !instance.evaluating()) {
							instance.fire('evaluationStarted');

							instance._evaluate(
								function(result) {
									instance.fire(
										'evaluationEnded',
										{
											result: result
										}
									);

									if (callback) {
										callback.apply(instance, arguments);
									}
								}
							);
						}
					},

					evaluating: function() {
						var instance = this;

						return instance._evaluating === true;
					},

					_afterEvaluationEnded: function() {
						var instance = this;

						instance._evaluating = null;
					},

					_afterEvaluationStarted: function() {
						var instance = this;

						instance._evaluating = true;
					},

					_evaluate: function(callback) {
						var instance = this;

						var form = instance.get('form');

						A.io.request(
							instance.get('evaluationURL'),
							{
								data: {
									serializedDDMForm: JSON.stringify(form.get('definition')),
									serializedDDMFormValues: JSON.stringify(form.toJSON())
								},
								dataType: 'JSON',
								method: 'POST',
								on: {
									failure: function() {
										callback.call(instance, null);
									},
									success: function() {
										var result = this.get('responseData');

										callback.call(instance, result);
									}
								}
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDM.Renderer').ExpressionsEvaluator = ExpressionsEvaluator;
	},
	'',
	{
		requires: ['aui-component', 'aui-request']
	}
);