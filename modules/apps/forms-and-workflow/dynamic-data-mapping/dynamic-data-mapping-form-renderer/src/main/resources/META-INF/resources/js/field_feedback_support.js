AUI.add(
	'liferay-ddm-form-renderer-field-feedback',
	function(A) {
		var Lang = A.Lang;

		var TPL_ERROR_MESSAGE = '<div class="error-message help-block">{errorMessage}</div>';

		var TPL_FEEDBACK = '<span aria-hidden="true" class="form-control-feedback"><span class="icon-{icon}"></span></span>';

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

				instance._eventHandlers.push(
					instance.after('blur', instance._afterBlur),
					instance.after('errorMessageChange', instance._afterErrorMessageChange),
					instance.after('focus', instance._afterFocus),
					instance.after(instance._afterRenderFeedbackSupport, instance, 'render')
				);
			},

			clearErrorMessage: function() {
				var instance = this;

				instance.set('errorMessage', '');
				instance.clearValidationStatus();
			},

			clearValidationStatus: function() {
				var instance = this;

				var container = instance.get('container');

				container.removeClass('has-error');
				container.removeClass('has-success');
			},

			hasErrors: function() {
				var instance = this;

				return !!instance.get('errorMessage');
			},

			hideErrorMessage: function() {
				var instance = this;

				var container = instance.get('container');

				container.all('.error-message').hide();
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

				if (!errorMessage) {
					errorMessage = instance.get('errorMessage');
				}
				else {
					instance.set('errorMessage', errorMessage);
				}

				var inputNode = instance.getInputNode();

				if (document.activeElement != inputNode.getDOM()) {
					var container = instance.get('container');

					container.all('.error-message').show();

					instance.showValidationStatus();
				}
			},

			showLoadingFeedback: function() {
				var instance = this;

				instance._showFeedback('spinner icon-spin');
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

			_afterBlur: function() {
				var instance = this;

				if (instance.hasErrors()) {
					instance.showErrorMessage();
					instance.showValidationStatus();
				}
			},

			_afterErrorMessageChange: function() {
				var instance = this;

				instance._renderErrorMessage();
			},

			_afterFocus: function() {
				var instance = this;

				instance.clearValidationStatus();
				instance.hideErrorMessage();
			},

			_afterRenderFeedbackSupport: function() {
				var instance = this;

				instance._renderErrorMessage();
				instance.hideErrorMessage();
			},

			_renderErrorMessage: function() {
				var instance = this;

				var errorMessage = instance.get('errorMessage');

				var inputNode = instance.getInputNode();

				if (inputNode) {
					var container = instance.get('container');

					var errorMessageNode = container.one('.error-message');

					if (errorMessageNode) {
						errorMessageNode.html(errorMessage);
					}
					else {
						errorMessageNode = A.Node.create(
							Lang.sub(
								TPL_ERROR_MESSAGE,
								{
									errorMessage: errorMessage
								}
							)
						);

						inputNode.insert(errorMessageNode, 'after');
					}
				}
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