AUI.add(
	'liferay-ddm-form-field-validation',
	function(A) {
		var Lang = A.Lang;

		var Renderer = Liferay.DDM.Renderer;

		var Util = Renderer.Util;

		var ValidationField = A.Component.create(
			{
				ATTRS: {
					errorMessageValue: {
						value: ''
					},

					parameterValue: {
						value: ''
					},

					selectedType: {
						value: 'text'
					},

					selectedValidation: {
						getter: '_getSelectedValidation',
						value: 'notEmpty'
					},

					strings: {
						value: {
							disableValidation: Liferay.Language.get('disable-validation'),
							email: Liferay.Language.get('email'),
							enableValidation: Liferay.Language.get('enable-validation'),
							errorMessageGoesHere: Liferay.Language.get('error-message-goes-here'),
							number: Liferay.Language.get('number'),
							text: Liferay.Language.get('text'),
							url: Liferay.Language.get('url')
						}
					},

					type: {
						value: 'validation'
					},

					typesOptions: {
						value: []
					},

					validations: {
						value: Util.getValidations()
					},

					value: {
						setter: '_setValue',
						valueFn: '_valueValidation'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-validation',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.bindContainerEvent('change', A.bind('_syncValidationUI', instance), '.enable-validation'),
							instance.bindContainerEvent('change', A.bind('_syncValidationUI', instance), 'select')
						);
					},

					extractParameterValue: function(regex, expression) {
						var instance = this;

						regex.lastIndex = 0;

						var matches = regex.exec(expression);

						return matches && matches[2] || '';
					},

					getTemplateContext: function() {
						var instance = this;

						var strings = instance.get('strings');

						var parameterMessage = '';

						var selectedValidation = instance.get('selectedValidation');

						if (selectedValidation) {
							parameterMessage = selectedValidation.parameterMessage;
						}

						var value = instance.get('value');

						return A.merge(
							ValidationField.superclass.getTemplateContext.apply(instance, arguments),
							{
								disableValidationMessage: strings.disableValidation,
								enableValidationMessage: strings.enableValidation,
								enableValidationValue: !!(value && value.expression),
								errorMessagePlaceholder: strings.errorMessageGoesHere,
								errorMessageValue: instance.get('errorMessageValue'),
								parameterMessagePlaceholder: parameterMessage,
								parameterValue: instance.get('parameterValue'),
								typesOptions: instance._getTypesOptions(),
								validationsOptions: instance._getValidatiionsOptions()
							}
						);
					},

					getValue: function() {
						var instance = this;

						var expression = '';

						var selectedValidation = instance.get('selectedValidation');

						var validationEnabled = instance._getEnableValidationValue();

						if (selectedValidation && validationEnabled) {
							var root = instance.getRoot();

							var nameField = root.getField('name');

							expression = Lang.sub(
								selectedValidation.template,
								{
									name: nameField && nameField.getValue() || '',
									parameter: instance._getParameterValue()
								}
							);
						}

						return {
							errorMessage: instance._getMessageValue(),
							expression: expression
						};
					},

					_getEnableValidationValue: function() {
						var instance = this;

						var container = instance.get('container');

						var enableValidationNode = container.one('.enable-validation');

						return !!enableValidationNode.attr('checked');
					},

					_getMessageValue: function() {
						var instance = this;

						var container = instance.get('container');

						var messageNode = container.one('.message-input');

						return messageNode.val();
					},

					_getParameterValue: function() {
						var instance = this;

						var container = instance.get('container');

						var parameterNode = container.one('.parameter-input');

						return parameterNode.val();
					},

					_getSelectedValidation: function(val) {
						var instance = this;

						var selectedType = instance.get('selectedType');

						var validations = instance.get('validations');

						var selectedValidation = A.Array.find(
							validations[selectedType],
							function(validation) {
								return validation.name === val;
							}
						);

						if (!selectedValidation) {
							selectedValidation = validations[selectedType][0];
						}

						return selectedValidation;
					},

					_getTypesOptions: function() {
						var instance = this;

						var selectedType = instance.get('selectedType');

						var strings = instance.get('strings');

						var options = [];

						A.each(
							instance.get('validations'),
							function(validation, validationType) {
								var status = selectedType === validationType ? 'selected' : '';

								options.push(
									{
										label: strings[validationType],
										status: status,
										value: validationType
									}
								);
							}
						);

						return options;
					},

					_getValidatiionsOptions: function() {
						var instance = this;

						var selectedValidation = instance.get('selectedValidation');

						var validations = instance.get('validations');

						return validations[instance.get('selectedType')].map(
							function(validation) {
								var status = '';

								if (selectedValidation && selectedValidation.name === validation.name) {
									status = 'selected';
								}

								return {
									label: validation.label,
									status: status,
									value: validation.name
								};
							}
						);
					},

					_setValue: function(validation) {
						var instance = this;

						if (validation) {
							var errorMessage = validation.errorMessage;

							var expression = validation.expression;

							A.each(
								instance.get('validations'),
								function(validation, type) {
									validation.forEach(
										function(item) {
											var regex = item.regex;

											if (regex.test(expression)) {
												instance.set('errorMessageValue', errorMessage);
												instance.set('selectedType', type);
												instance.set('selectedValidation', item.name);

												instance.set(
													'parameterValue',
													instance.extractParameterValue(regex, expression)
												);
											}
										}
									);
								}
							);
						}

						return validation;
					},

					_syncValidationUI: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var newVal = currentTarget.val();

						var selectedValidation = newVal;

						if (currentTarget.hasClass('types-select')) {
							instance.set('selectedType', newVal);

							var validations = instance.get('validations');

							selectedValidation = validations[newVal][0].name;
						}

						instance.set('selectedValidation', selectedValidation);

						instance.set('value', instance.getValue());
					},

					_valueValidation: function() {
						var instance = this;

						return {
							errorMessage: Liferay.Language.get('is-empty'),
							expression: 'NOT(equals({name}, ""))'
						};
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Validation = ValidationField;
	},
	'',
	{
		requires: ['aui-dropdown', 'liferay-ddm-form-renderer-field']
	}
);