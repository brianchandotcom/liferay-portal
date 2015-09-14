AUI.add(
	'liferay-ddm-form-renderer-field-feedback',
	function(A) {
		var Lang = A.Lang;

		var TPL_ERROR_MESSAGE = '<div class="validation-message">{errorMessage}</div>';

		var TPL_FEEDBACK = '<span aria-hidden="true" class="form-control-feedback"><span class="icon-{icon}"></span></span>';

		var FieldFeedbackSupport = function() {
		};

		FieldFeedbackSupport.ATTRS = {
			errorMessage: {
				value: []
			}
		};

		FieldFeedbackSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.after('errorMessageChange', instance._afterErrorMessageChange),
					instance.after(instance._renderErrorMessage, instance, 'render')
				);
			},

			clearValidationStatus: function() {
				var instance = this;

				var container = instance.get('container');

				container.removeClass('has-error');
				container.removeClass('has-success');

				instance.hideFeedback();
			},

			hideErrorMessage: function() {
				var instance = this;

				instance.set('errorMessage', '');
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

			showErrorMessage: function(errorMessage) {
				var instance = this;

				instance.set('errorMessage', errorMessage);
			},

			showLoadingFeedback: function() {
				var instance = this;

				instance._showFeedback('spinner');
			},

			showSuccessFeedback: function() {
				var instance = this;

				instance._showFeedback('ok');
			},

			showValidationStatus: function() {
				var instance = this;

				if (instance.hasValidation()) {
					var container = instance.get('container');

					var hasErrors = instance.hasErrors();

					container.toggleClass('has-error', hasErrors);
					container.toggleClass('has-success', !hasErrors);

					if (hasErrors) {
						instance.showErrorFeedback();
					}
					else {
						instance.showSuccessFeedback();
					}
				}
			},

			_afterErrorMessageChange: function() {
				var instance = this;

				instance._renderErrorMessage();
			},

			_renderErrorMessage: function() {
				var instance = this;

				var container = instance.get('container');

				container.all('.validation-message').remove();

				instance.getInputNode().insert(
					Lang.sub(
						TPL_ERROR_MESSAGE,
						{
							errorMessage: instance.get('errorMessage')
						}
					),
					'after'
				);
			},

			_showFeedback: function(icon) {
				var instance = this;

				instance.hideFeedback();

				var container = instance.get('container');

				container.addClass('has-feedback');

				instance.getInputNode().insert(
					Lang.sub(
						TPL_FEEDBACK,
						{
							icon: icon
						}
					),
					'after'
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