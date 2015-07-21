AUI.add(
	'liferay-ddm-form-renderer-validation',
	function(A) {
		var Renderer = Liferay.DDM.Renderer;

		var Util = Renderer.Util;

		var FormValidationSupport = function() {
		};

		FormValidationSupport.ATTRS = {
			defaultErrorMessage: {
				value: 'Unkown error.'
			},

			validationURL: {
				value: '/o/ddm-eval'
			}
		};

		FormValidationSupport.prototype = {
			clearErrorMessages: function() {
				var instance = this;

				instance.eachField(
					function(field) {
						field.clearErrorMessages();
					}
				);
			},

			syncValidationUI: function() {
				var instance = this;

				instance.eachField(
					function(field) {
						var errorMessages = field.get('errorMessages');

						var hasError = errorMessages.length > 0;

						if (hasError) {
							field.focus();
						}

						return hasError;
					}
				);
			},

			validate: function(callback) {
				var instance = this;

				A.io.request(
					instance.get('validationURL'),
					{
						data: {
							serializedDDMForm: JSON.stringify(instance.get('definition')),
							serializedDDMFormValues: JSON.stringify(instance.toJSON())
						},
						dataType: 'JSON',
						method: 'POST',
						on: {
							failure: function() {
								callback(false);
							},
							success: function() {
								var data = this.get('responseData');

								var valid = instance._parseResponse(data);

								instance.syncValidationUI();

								callback(valid, data);
							}
						}
					}
				);
			},

			_parseResponse: function(responseData) {
				var instance = this;

				var valid = true;

				instance.eachField(
					function(field) {
						var instanceId = field.get('instanceId');

						var data = Util.getFieldByKey(responseData, instanceId, 'instanceId');

						if (data.valid === false) {
							valid = false;
						}

						var messages = data.messages;

						if (!messages && !data.valid) {
							messages = [instance.get('defaultErrorMessage')];
						}

						if (messages && messages.length) {
							field.set('errorMessages', messages);
						}
					}
				);

				return valid;
			}
		};

		Liferay.namespace('DDM.Renderer').FormValidationSupport = FormValidationSupport;
	},
	'',
	{
		requires: ['aui-request', 'json']
	}
);