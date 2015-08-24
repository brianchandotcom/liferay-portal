AUI.add(
	'liferay-ddm-form-renderer-feedback',
	function(A) {
		var TPL_BUTTON_SPINNER = '<span>&nbsp;<span class="icon-spinner"></span></span>';

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

			clearValidationMessages: function() {
				var instance = this;

				instance.eachField(
					function(field) {
						field.clearValidationMessages();
					}
				);
			},

			clearValidationStatus: function() {
				var instance = this;

				instance.eachField(
					function(field) {
						field.clearValidationStatus();
					}
				);
			},

			hideFeedback: function() {
				var instance = this;

				instance.eachField(
					function(field) {
						field.hideFeedback();
					}
				);

				instance.get('alert').hide();
				instance.spinner.hide();
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

				var submitButton = instance._getSubmitButton();

				if (submitButton) {
					instance.spinner.appendTo(submitButton);
					instance.spinner.show();
				}
				else {
					instance.showAlert(Liferay.Language.get('please-wait'), 'alert-info');
				}
			},

			showValidationStatus: function() {
				var instance = this;

				instance.eachField(
					function(field) {
						field.showValidationStatus();
					}
				);
			},

			_createSpinner: function() {
				var instance = this;

				instance.spinner = A.Node.create(TPL_BUTTON_SPINNER);
			},

			_getSubmitButton: function() {
				var instance = this;

				var container = instance.get('container');

				var formNode = instance.getFormNode();

				return (formNode || container).one('[type="submit"]');
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