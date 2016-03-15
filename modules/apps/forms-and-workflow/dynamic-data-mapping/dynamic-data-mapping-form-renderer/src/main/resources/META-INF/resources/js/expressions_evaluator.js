AUI.add(
	'liferay-ddm-form-renderer-expressions-evaluator',
	function(A) {
		var ExpressionsEvaluator = A.Component.create(
			{
				ATTRS: {
					enabled: {
						value: true
					},

					evaluatorURL: {
						valueFn: '_valueEvaluatorURL'
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

						var enabled = instance.get('enabled');

						var form = instance.get('form');

						if (instance._request) {
							instance._request.stop();
						}

						if (enabled && form && !instance.evaluating()) {
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

						instance._request = A.io.request(
							instance.get('evaluatorURL'),
							{
								data: {
									languageId: form.get('locale'),
									serializedDDMForm: JSON.stringify(form.get('definition')),
									serializedDDMFormValues: JSON.stringify(form.toJSON())
								},
								dataType: 'JSON',
								method: 'POST',
								on: {
									failure: function(event) {
										if (event.details[1].statusText !== 'abort') {
											callback.call(instance, null);
										}
										callback.call(instance, {});
									},
									success: function() {
										var result = this.get('responseData');

										callback.call(instance, result);
									}
								}
							}
						);
					},

					_valueEvaluatorURL: function() {
						var instance = this;

						var evaluatorURL;

						var form = instance.get('form');

						if (form) {
							evaluatorURL = form.get('evaluatorURL');
						}

						return evaluatorURL;
					}
				}
			}
		);

		Liferay.namespace('DDM.Renderer').ExpressionsEvaluator = ExpressionsEvaluator;
	},
	'',
	{
		requires: ['aui-component', 'aui-io-request']
	}
);