AUI.add(
	'liferay-ddm-form-renderer-field-validation',
	function(A) {
		var Lang = A.Lang;

		var Renderer = Liferay.DDM.Renderer;

		var Util = Renderer.Util;

		var FieldValidationSupport = function() {
		};

		FieldValidationSupport.ATTRS = {
			evaluator: {
				valueFn: '_valueEvaluator'
			},

			strings: {
				value: {
					defaultValidationMessage: Liferay.Language.get('unkown-error'),
					requestErrorMessage: Liferay.Language.get('there-was-an-error-when-trying-to-validate-your-form')
				}
			},

			validationExpression: {
				value: ''
			}
		};

		FieldValidationSupport.prototype = {
			initializer: function() {
				var instance = this;

				var evaluator = instance.get('evaluator');

				instance._eventHandlers.push(
					evaluator.after('evaluationEnded', A.bind('_afterEvaluationEnded', instance)),
					evaluator.after('evaluationStarted', A.bind('_afterEvaluationStarted', instance)),
					instance.after('blur', instance._afterBlur)
				);
			},

			hasErrors: function() {
				var instance = this;

				return instance.get('validationMessages').length > 0;
			},

			hasValidation: function() {
				var instance = this;

				var validationExpression = instance.get('validationExpression');

				return !!validationExpression && validationExpression !== 'true';
			},

			processValidation: function(result) {
				var instance = this;

				var instanceId = instance.get('instanceId');

				var validation = Util.getFieldByKey(result, instanceId, 'instanceId');

				if (validation) {
					var messages = validation.messages;

					if (!messages && !validation.valid) {
						var strings = instance.get('strings');

						messages = [strings.defaultValidationMessage];
					}

					if (messages && messages.length) {
						instance.set('validationMessages', messages);
					}
					else {
						instance.clearValidationMessages();
					}
				}
			},

			validate: function(callback) {
				var instance = this;

				if (instance.hasValidation()) {
					var evaluator = instance.get('evaluator');

					evaluator.evaluate(
						function(result) {
							if (callback) {
								var hasErrors = instance.hasErrors();

								if (!result || !Lang.isObject(result)) {
									hasErrors = true;
								}

								callback.call(instance, hasErrors, result);
							}
						}
					);
				}
				else if (callback) {
					callback.call(instance, false);
				}
			},

			_afterBlur: function() {
				var instance = this;

				instance.validate();
			},

			_afterEvaluationEnded: function(event) {
				var instance = this;

				var result = event.result;

				instance.hideFeedback();

				if (result && Lang.isObject(result)) {
					instance.processValidation(result);

					instance.showValidationStatus();
				}
				else {
					var root = instance.getRoot();

					var strings = instance.get('strings');

					root.showAlert(strings.requestErrorMessage);
				}
			},

			_afterEvaluationStarted: function() {
				var instance = this;

				instance.showLoadingFeedback();
			},

			_valueEvaluator: function() {
				var instance = this;

				return new Renderer.ExpressionsEvaluator(
					{
						form: instance.getRoot()
					}
				);
			}
		};

		Liferay.namespace('DDM.Renderer').FieldValidationSupport = FieldValidationSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-expressions-evaluator']
	}
);