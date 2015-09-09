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
				valueFn: '_valueVisible'
			}
		};

		FieldVisibilitySupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
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

			processVisibilityEvaluation: function(result) {
				var instance = this;

				if (result && Lang.isObject(result)) {
					instance.getRoot().eachField(
						function(field) {
							field.processVisibility(result);
						}
					);
				}
			},

			_afterValueChanged: function() {
				var instance = this;

				var evaluator = instance.get('evaluator');

				instance.showLoadingFeedback();

				evaluator.evaluate(
					function(result) {
						instance.hideFeedback();

						instance.processVisibilityEvaluation(result);
					}
				);
			},

			_afterVisibleChange: function() {
				var instance = this;

				instance.render();
			},

			_valueVisible: function() {
				var instance = this;

				var visible = true;

				if (instance.get('visibilityExpression') === 'false') {
					visible = false;
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