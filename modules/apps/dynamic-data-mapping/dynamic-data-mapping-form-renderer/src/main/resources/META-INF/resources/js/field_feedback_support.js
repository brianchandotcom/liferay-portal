AUI.add(
	'liferay-ddm-form-renderer-field-feedback',
	function(A) {
		var Lang = A.Lang;

		var TPL_ERROR_MESSAGE = '<div class="form-feedback-item help-block">{errorMessage}</div>';

		var FieldFeedbackSupport = function() {
		};

		FieldFeedbackSupport.ATTRS = {
			errorMessage: {
				value: ''
			}
		};

		FieldFeedbackSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._errorMessageNode = instance._createErrorMessageNode();

				instance._eventHandlers.push(
					instance.after('errorMessageChange', instance._afterErrorMessageChange),
					instance.after('visibleChange', instance._afterVisibleChange)
				);
			},

			clearValidationStatus: function() {
				var instance = this;

				var container = instance.get('container');

				container.removeClass('has-error');

				instance.hideFeedback();
			},

			hideErrorMessage: function() {
				var instance = this;

				instance._errorMessageNode.hide();

				instance.clearValidationStatus();
			},

			hideFeedback: function() {
				var instance = this;

				var container = instance.get('container');

				container.removeClass('has-feedback');

				container.all('.form-control-feedback').remove();
			},

			showErrorFeedback: function() {
				var instance = this;

				instance._showFeedback('remove');
			},

			showErrorMessage: function() {
				var instance = this;

				var errorMessage = instance.get('errorMessage');

				var inputNode = instance.getInputNode();

				if (errorMessage && inputNode) {
					var targetNode = inputNode.ancestor('.form-group');

					targetNode.append(instance._errorMessageNode);

					instance.set('valid', false);
					instance._errorMessageNode.show();
					instance.showValidationStatus();

					var root = instance.getRoot();

					if (root) {
						Liferay.fire('ddmFieldValidationError', {
							fieldName: instance.get('fieldName'),
							formId: root.getFormId()
						});
					}
				}
			},

			showSuccessFeedback: function() {
				var instance = this;

				instance._showFeedback('ok');
			},

			showValidationStatus: function() {
				var instance = this;

				var container = instance.get('container');

				container.toggleClass('has-error', instance.hasErrors());
			},

			_afterErrorMessageChange: function(event) {
				var instance = this;

				if (event.newVal) {
					instance._errorMessageNode.html(event.newVal);
				}
			},

			_afterVisibleChange: function(event) {
				var instance = this;

				var container = instance.get('container');

				container.toggleClass('hide', !event.newVal);
			},

			_createErrorMessageNode: function() {
				var instance = this;

				var errorMessage = instance.get('errorMessage');

				return A.Node.create(
					Lang.sub(
						TPL_ERROR_MESSAGE,
						{
							errorMessage: errorMessage
						}
					)
				);
			}
		};

		Liferay.namespace('DDM.Renderer').FieldFeedbackSupport = FieldFeedbackSupport;
	},
	'',
	{
		requires: ['aui-node']
	}
);