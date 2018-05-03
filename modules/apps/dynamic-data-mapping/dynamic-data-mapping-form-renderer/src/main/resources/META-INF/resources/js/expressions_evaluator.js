AUI.add(
	'liferay-ddm-form-renderer-expressions-evaluator',
	function(A) {
		var CACHE = {};

		var ExpressionsEvaluator = A.Component.create(
			{
				ATTRS: {
					enabled: {
						getter: '_getEnabled',
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

						instance._queue = new A.Queue();

						instance.publish(
							{
								'evaluate': {
									defaultFn: instance._evaluate
								},
								start: {
									defaultFn: instance._start
								}
							}
						);

						instance.after('evaluationEnded', instance._afterEvaluationEnded);
					},

					evaluate: function(trigger, callback) {
						var instance = this;

						var enabled = instance.get('enabled');

						var form = instance.get('form');

						if (enabled && form) {
							if (instance.isEvaluating()) {
								instance.stop();
							}

							instance._evaluating = true;

							instance.fire(
								'start',
								{
									trigger: trigger
								}
							);

							instance._queue.add(trigger);

							instance.fire(
								'evaluate',
								{
									callback: function(result) {
										instance._evaluating = false;

										var triggers = {};

										while (instance._queue.size() > 0) {
											var next = instance._queue.next();

											if (!triggers[next.get('name')]) {
												instance.fire(
													'evaluationEnded',
													{
														result: result,
														trigger: next
													}
												);
											}

											triggers[next.get('name')] = true;
										}

										if (callback) {
											callback.apply(instance, arguments);
										}
									},
									trigger: trigger
								}
							);
						}
					},

					isEvaluating: function() {
						var instance = this;

						return instance._evaluating;
					},

					stop: function() {
						var instance = this;

						if (instance._request) {
							instance._request.destroy();

							delete instance._request;
						}
					},

					_afterEvaluationEnded: function() {
						var instance = this;

						instance.stop();
					},

					_evaluate: function(event) {
						var instance = this;

						var callback = event.callback;

						var form = instance.get('form');

						var payload = form.getEvaluationPayload();

						var type = payload.type;

						if (payload.newField && CACHE[type]) {
							callback.call(instance, JSON.parse(CACHE[type]));
						}
						else {
							instance._request = A.io.request(
								instance.get('evaluatorURL'),
								{
									data: A.merge(
										payload,
										{
											trigger: event.trigger ? event.trigger.get('fieldName') || '' : ''
										}
									),
									method: 'POST',
									on: {
										failure: function(event) {
											if (event.details[1].statusText !== 'abort') {
												callback.call(instance, null);
											}
											else {
												callback.call(instance, {});
											}
										},
										success: function(event, id, xhr) {
											var result = xhr.responseText;

											if (payload.newField) {
												CACHE[type] = result;
											}

											callback.call(instance, JSON.parse(result));
										}
									}
								}
							);
						}
					},

					_getEnabled: function(enabled) {
						var instance = this;

						return enabled && !!instance.get('evaluatorURL');
					},

					_start: function(event) {
						var instance = this;

						if (instance.isEvaluating()) {
							instance.fire(
								'evaluationStarted',
								{
									trigger: event.trigger
								}
							);
						}
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