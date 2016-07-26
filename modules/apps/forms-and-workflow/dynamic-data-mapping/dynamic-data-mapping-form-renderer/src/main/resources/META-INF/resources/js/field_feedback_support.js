AUI.add(
	'liferay-ddm-form-renderer-field-feedback',
	function(A) {
		var Lang = A.Lang;

		var TPL_ERROR_MESSAGE = '<div class="help-block">{errorMessage}</div>';

		var TPL_FEEDBACK = '<span aria-hidden="true" class="form-control-feedback"><span class="icon-{icon}"></span></span>';

		var FieldFeedbackSupport = function() {
		};

		FieldFeedbackSupport.ATTRS = {
			errorMessage: {
				repaint: false,
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
					inputNode.insert(instance._errorMessageNode, 'after');

					instance._errorMessageNode.show();

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

			_afterErrorMessageChange: function(event) {
				var instance = this;

				instance._errorMessageNode.html(event.newVal);
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