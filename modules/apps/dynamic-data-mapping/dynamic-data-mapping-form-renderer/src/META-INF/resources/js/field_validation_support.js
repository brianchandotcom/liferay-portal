AUI.add(
	'liferay-ddm-form-renderer-field-validation',
	function(A) {
		var Lang = A.Lang;

		var TPL_ERROR_MESSAGE = '<div class="validation-message">{message}</div>';

		var FieldValidationSupport = function() {
		};

		FieldValidationSupport.ATTRS = {
			errorMessages: {
				value: []
			},

			validationExpression: {
				value: 'true'
			}
		};

		FieldValidationSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.after('errorMessagesChange', instance._afterErrorMessagesChange),
					instance.after(instance.renderErrorMessages, instance, 'render')
				);
			},

			addErrorMessage: function(message) {
				var instance = this;

				instance.getInputNode().insert(
					Lang.sub(
						TPL_ERROR_MESSAGE,
						{
							message: message
						}
					),
					'after'
				);
			},

			clearErrorMessages: function() {
				var instance = this;

				var container = instance.get('container');

				container.all('.validation-message').remove();

				container.removeClass('has-error');
			},

			renderErrorMessages: function() {
				var instance = this;

				instance.clearErrorMessages();

				var messages = instance.get('errorMessages');

				instance.get('container').toggleClass('has-error', messages.length > 0);

				messages.forEach(A.bind('addErrorMessage', instance));
			},

			_afterErrorMessagesChange: function(event) {
				var instance = this;

				instance.renderErrorMessages();
			}
		};

		Liferay.namespace('DDM.Renderer').FieldValidationSupport = FieldValidationSupport;
	},
	'',
	{
		requires: ['aui-base']
	}
);