AUI.add(
	'liferay-ddm-form-renderer-validation',
	function(A) {
		var Lang = A.Lang;

		var FormValidationSupport = function() {
		};

		FormValidationSupport.ATTRS = {
		};

		FormValidationSupport.prototype = {
			hasErrors: function() {
				var instance = this;

				var hasErrors = false;

				instance.eachField(
					function(field) {
						if (field.hasErrors()) {
							hasErrors = true;

							field.showErrorMessage();
						}
						else {
							field.hideErrorMessage();
						}
					}
				);

				return hasErrors;
			},

			hasPageErrors: function(pageNode) {
				var instance = this;

				var hasPageErrors = false;

				instance.eachField(
					function(field) {
						var container = field.get('container');

						if (pageNode.contains(container) && field.hasErrors()) {
							hasPageErrors = true;

							field.showErrorMessage();
						}
						else {
							field.hideErrorMessage();
						}
					}
				);

				return hasPageErrors;
			},

			validate: function(callback) {
				var instance = this;

				if (instance.get('readOnly')) {
					callback.call(instance, false, null);
				}
				else {
					var evaluator = instance.get('evaluator');

					evaluator.evaluate(
						instance,
						function(result) {
							var hasErrors = true;

							if (result && Lang.isObject(result)) {
								hasErrors = instance.hasErrors();
							}

							if (callback) {
								callback.call(instance, hasErrors, result);
							}
						}
					);
				}
			},

			validatePage: function(pageNode, callback) {
				var instance = this;

				if (instance.get('readOnly')) {
					callback.call(instance, false, null);
				}
				else {
					var evaluator = instance.get('evaluator');

					evaluator.evaluate(
						instance,
						function(result) {
							var hasPageErrors = true;

							if (result && Lang.isObject(result)) {
								hasPageErrors = instance.hasPageErrors(pageNode, result);
							}

							if (callback) {
								callback.call(instance, hasPageErrors, result);
							}
						}
					);
				}
			}
		};

		Liferay.namespace('DDM.Renderer').FormValidationSupport = FormValidationSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-expressions-evaluator']
	}
);