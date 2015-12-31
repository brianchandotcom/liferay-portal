AUI.add(
	'liferay-ddm-form-renderer-field-visibility',
	function(A) {
		var Lang = A.Lang;

		var Renderer = Liferay.DDM.Renderer;

		var Util = Renderer.Util;

		var FieldVisibilitySupport = function() {
		};

		FieldVisibilitySupport.ATTRS = {
			visibilityExpression: {
				value: ''
			},

			visible: {
				valueFn: '_valueVisible'
			}
		};

		FieldVisibilitySupport.prototype = {
			initializer: function() {
				var instance = this;

				var evaluator = instance.get('evaluator');

				instance._eventHandlers.push(
					evaluator.after('evaluationEnded', A.bind('_afterVisibilityEvaluationEnded', instance)),
					evaluator.after('evaluationStarted', A.bind('_afterVisibilityEvaluationStarted', instance)),
					instance.after('valueChanged', instance._afterValueChanged),
					instance.after('visibleChange', instance._afterVisibleChange)
				);
			},

			processVisibility: function(result) {
				var instance = this;

				var visibility;

				if (result && Lang.isObject(result)) {
					var name = instance.get('name');

					visibility = Util.getFieldByKey(result, name, 'name');
				}

				return visibility;
			},

			processVisibilityEvaluation: function(result) {
				var instance = this;

				instance.getRoot().eachField(
					function(field) {
						var visibility = field.processVisibility(result);

						if (visibility) {
							field.set('visible', visibility.visible);
						}
					}
				);
			},

			_afterValueChanged: function() {
				var instance = this;

				var evaluator = instance.get('evaluator');

				evaluator.evaluate();
			},

			_afterVisibilityEvaluationEnded: function(event) {
				var instance = this;

				instance.hideFeedback();

				instance.processVisibilityEvaluation(event.result);
			},

			_afterVisibilityEvaluationStarted: function() {
				var instance = this;

				instance.showLoadingFeedback();
			},

			_afterVisibleChange: function() {
				var instance = this;

				var value = instance.getValue();

				instance.render();

				instance.setValue(value);
			},

			_valueVisible: function() {
				var instance = this;

				var form = instance.getRoot();

				var visible = true;

				if (form) {
					var evaluation = form.get('evaluation');

					if (evaluation) {
						var visibility = instance.processVisibility(evaluation);

						if (visibility) {
							visible = visibility.visible;
						}
					}
					else {
						visible = instance.get('visibilityExpression') !== 'false';
					}
				}

				return visible;
			}
		};

		Liferay.namespace('DDM.Renderer').FieldVisibilitySupport = FieldVisibilitySupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-expressions-evaluator', 'liferay-ddm-form-renderer-util']
	}
);