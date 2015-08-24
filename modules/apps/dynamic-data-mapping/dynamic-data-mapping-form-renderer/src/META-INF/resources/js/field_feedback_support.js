AUI.add(
	'liferay-ddm-form-renderer-field-feedback',
	function(A) {
		var Lang = A.Lang;

		var TPL_FEEDBACK = '<span class="form-control-feedback" aria-hidden="true"><span class="icon-{icon}"></span></span>';

		var TPL_VALIDATION_MESSAGE = '<div class="validation-message">{message}</div>';

		var FieldFeedbackSupport = function() {
		};

		FieldFeedbackSupport.ATTRS = {
			validationMessages: {
				value: []
			}
		};

		FieldFeedbackSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.after('validationMessagesChange', instance._afterValidationMessagesChange),
					instance.after(instance._renderValidationMessages, instance, 'render')
				);

				instance._renderValidationMessages();
			},

			addValidationMessage: function(message) {
				var instance = this;

				var validationMessages = instance.get('validationMessages');

				validationMessages.push(message);

				instance.set('validationMessages', validationMessages);
			},

			clearValidationMessages: function() {
				var instance = this;

				instance.set('validationMessages', []);
			},

			clearValidationStatus: function() {
				var instance = this;

				var container = instance.get('container');

				container.removeClass('has-error');
				container.removeClass('has-success');

				instance.hideFeedback();
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

			_afterValidationMessagesChange: function() {
				var instance = this;

				instance._renderValidationMessages();
			},

			_appendValidationMessage: function(message) {
				var instance = this;

				instance.getInputNode().insert(
					Lang.sub(
						TPL_VALIDATION_MESSAGE,
						{
							message: message
						}
					),
					'after'
				);
			},

			_renderValidationMessages: function() {
				var instance = this;

				var container = instance.get('container');

				container.all('.validation-message').remove();

				var messages = instance.get('validationMessages');

				messages.forEach(A.bind('_appendValidationMessage', instance));
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