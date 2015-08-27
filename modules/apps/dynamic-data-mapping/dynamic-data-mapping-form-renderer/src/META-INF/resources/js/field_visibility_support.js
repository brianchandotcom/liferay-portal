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
				value: 'true'
			},

			visible: {
				value: true
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

				var instanceId = instance.get('instanceId');

				var visibility = Util.getFieldByKey(result, instanceId, 'instanceId');

				instance.set('visible', visibility && visibility.visible === true);
			},

			_afterValueChanged: function() {
				var instance = this;

				var evaluator = instance.get('evaluator');

				evaluator.evaluate();
			},

			_afterVisibilityEvaluationEnded: function(event) {
				var instance = this;

				var result = event.result;

				instance.hideFeedback();

				if (result && Lang.isObject(result)) {
					instance.getRoot().eachField(
						function(field) {
							field.processVisibility(result);
						}
					);
				}
			},

			_afterVisibilityEvaluationStarted: function() {
				var instance = this;

				instance.showLoadingFeedback();
			},

			_afterVisibleChange: function() {
				var instance = this;

				instance.render();
			}
		};

		Liferay.namespace('DDM.Renderer').FieldVisibilitySupport = FieldVisibilitySupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-expressions-evaluator', 'liferay-ddm-form-renderer-util']
	}
);