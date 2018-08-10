AUI.add(
	'liferay-ddm-form-renderer-feedback',
	function(A) {
		var TPL_BUTTON_SPINNER = '<span aria-hidden="true"><span class="icon-spinner icon-spin"></span></span>';

		var FormFeedbackSupport = function() {
		};

		FormFeedbackSupport.ATTRS = {
			alert: {
				valueFn: '_valueAlert'
			}
		};

		FormFeedbackSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._createSpinner();
			},

			destructor: function() {
				var instance = this;

				instance._spinner.remove(true);

				instance._spinner = null;
			},

			clearValidationStatus: function() {
				var instance = this;

				instance.eachNestedField(
					function(field) {
						field.clearValidationStatus();
					}
				);
			},

			disableSubmitButton: function() {
				var instance = this;

				instance._setSubmitButtonDisabledState(true);
			},

			enableSubmitButton: function() {
				var instance = this;

				instance._setSubmitButtonDisabledState(false);
			},

			hideErrorMessages: function() {
				var instance = this;

				instance.eachNestedField(
					function(field) {
						field.hideErrorMessage();
					}
				);
			},

			hideFeedback: function() {
				var instance = this;

				instance.eachNestedField(
					function(field) {
						field.hideFeedback();
					}
				);

				instance.get('alert').hide();
				instance._spinner.hide();
			},

			showAlert: function(message, cssClass) {
				var instance = this;

				var container = instance.get('container');

				if (container.inDoc()) {
					var alert = instance.get('alert');

					alert.setAttrs(
						{
							bodyContent: message,
							cssClass: cssClass || 'alert-danger'
						}
					);

					alert.render();
					alert.show();

					container.insert(alert.get('boundingBox'), 'before');
				}
			},

			showLoadingFeedback: function() {
				var instance = this;

				var submitButton = instance.getSubmitButton();

				if (submitButton) {
					instance._spinner.appendTo(submitButton);
					instance._spinner.show();
				}
				else {
					instance.showAlert(Liferay.Language.get('please-wait'), 'alert-info');
				}
			},

			showValidationStatus: function() {
				var instance = this;

				instance.eachNestedField(
					function(field) {
						field.showValidationStatus();
					}
				);
			},

			_createSpinner: function() {
				var instance = this;

				instance._spinner = A.Node.create(TPL_BUTTON_SPINNER);
			},

			_setSubmitButtonDisabledState: function(state) {
				var instance = this;

				var submitButton = instance.getSubmitButton();

				if (submitButton) {
					submitButton.attr('disabled', state);
				}
			},

			_valueAlert: function() {
				var instance = this;

				return new A.Alert(
					{
						animated: false,
						closeable: true,
						destroyOnHide: false,
						duration: 0.5
					}
				);
			}
		};

		Liferay.namespace('DDM.Renderer').FormFeedbackSupport = FormFeedbackSupport;
	},
	'',
	{
		requires: ['aui-alert']
	}
);