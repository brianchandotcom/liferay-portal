AUI.add(
	'liferay-ddm-form-renderer-validation',
	function(A) {
		var Lang = A.Lang;

		var Renderer = Liferay.DDM.Renderer;

		var Util = Renderer.Util;

		var FormValidationSupport = function() {
		};

		FormValidationSupport.ATTRS = {
			strings: {
				value: {
					defaultValidationMessage: Liferay.Language.get('unkonwn-error'),
					requestErrorMessage: Liferay.Language.get('there-was-an-error-when-trying-to-validate-your-form')
				}
			},

			validationURL: {
				value: '/o/ddm-eval'
			}
		};

		FormValidationSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance.after('*:blur', instance._afterFieldBlur);

				instance._bindFormEvents();
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

			hasValidation: function(response) {
				var instance = this;

				var hasValidation = false;

				instance.eachField(
					function(field) {
						hasValidation = field.hasValidation();

						return hasValidation;
					}
				);

				return hasValidation;
			},

			hideFeedback: function() {
				var instance = this;

				instance.eachField(
					function(field) {
						field.hideFeedback();
					}
				);
			},

			showErrorAlert: function() {
				var instance = this;

				var container = instance.get('container');

				if (container.inDoc()) {
					var strings = instance.get('strings');

					var alert = new A.Alert(
						{
							animated: true,
							bodyContent: strings.requestErrorMessage,
							closeable: true,
							cssClass: 'alert-danger',
							destroyOnHide: true,
							duration: 1
						}
					).render();

					container.insert(alert.get('boundingBox'), 'before');
				}
			},

			showLoadingFeedback: function() {
				var instance = this;

				instance.eachField(
					function(field) {
						if (field.hasValidation()) {
							field.showLoadingFeedback();
						}
					}
				);
			},

			showValidationStatus: function() {
				var instance = this;

				instance.eachField(
					function(field) {
						if (field.hasValidation()) {
							field.showValidationStatus();
						}
					}
				);
			},

			validate: function(callback) {
				var instance = this;

				if (instance.hasValidation()) {
					instance.showLoadingFeedback();

					instance._validate(
						function(response) {
							var hasErrors = true;

							instance.hideFeedback();

							if (response && Lang.isObject(response)) {
								instance._handleFormValidation(response);

								hasErrors = instance._hasErrors(response);

								instance.showValidationStatus();
							}
							else {
								instance.showErrorAlert();
							}

							if (callback) {
								callback.call(instance, hasErrors, response);
							}
						}
					);
				}
				else if (callback) {
					callback.call(instance, false);
				}
			},

			validateField: function(field, callback) {
				var instance = this;

				if (field.hasValidation()) {
					field.showLoadingFeedback();

					instance._validate(
						function(response) {
							var hasErrors = true;

							field.hideFeedback();

							if (response && Lang.isObject(response)) {
								instance._handleFieldValidation(field, response);

								hasErrors = field.hasErrors();

								field.showValidationStatus();
							}
							else {
								instance.showErrorAlert();
							}

							if (callback) {
								callback.call(instance, hasErrors, response);
							}
						}
					);
				}
				else if (callback) {
					callback.call(instance, false);
				}
			},

			_afterFieldBlur: function(event) {
				var instance = this;

				instance.validateField(event.field);
			},

			_bindFormEvents: function() {
				var instance = this;

				var domForm = instance._getDOMForm();

				if (domForm) {
					domForm.on('submit', A.bind('_onDOMSubmitForm', instance));

					Liferay.on('submitForm', instance._onLiferaySubmitForm, instance);
				}
			},

			_getDOMForm: function() {
				var instance = this;

				var container = instance.get('container');

				return container.ancestor('form', true);
			},

			_handleFieldValidation: function(field, response) {
				var instance = this;

				var instanceId = field.get('instanceId');

				var validation = Util.getFieldByKey(response, instanceId, 'instanceId');

				if (validation) {
					var messages = validation.messages;

					if (!messages && !validation.valid) {
						var strings = instance.get('strings');

						messages = [strings.defaultValidationMessage];
					}

					if (messages && messages.length) {
						field.set('validationMessages', messages);
					}
					else {
						field.clearValidationMessages();
					}
				}
			},

			_handleFormValidation: function(response) {
				var instance = this;

				var fieldToFocus;

				instance.eachField(
					function(field) {
						instance._handleFieldValidation(field, response);

						if (field.hasErrors() && !fieldToFocus) {
							fieldToFocus = field;
						}
					}
				);

				if (fieldToFocus) {
					fieldToFocus.focus();
				}
			},

			_hasErrors: function(response) {
				var instance = this;

				var hasErrors = false;

				instance.eachField(
					function(field) {
						var instanceId = field.get('instanceId');

						var validation = Util.getFieldByKey(response, instanceId, 'instanceId');

						if (validation && validation.valid === false) {
							hasErrors = true;
						}

						return hasErrors;
					}
				);

				return hasErrors;
			},

			_onDOMSubmitForm: function(event) {
				var instance = this;

				event.preventDefault();

				instance.validate(
					function(hasErrors) {
						if (!hasErrors) {
							var domForm = instance._getDOMForm();

							domForm.submit();
						}
					}
				);
			},

			_onLiferaySubmitForm: function(event) {
				var instance = this;

				if (event.form === instance._getDOMForm()) {
					event.preventDefault();
				}
			},

			_validate: function(callback) {
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
								callback.call(instance, null);
							},
							success: function() {
								var response = this.get('responseData');

								callback.call(instance, response);
							}
						}
					}
				);
			}
		};

		Liferay.namespace('DDM.Renderer').FormValidationSupport = FormValidationSupport;
	},
	'',
	{
		requires: ['aui-alert', 'aui-request']
	}
);