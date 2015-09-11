AUI.add(
	'liferay-ddm-form-field-validation',
	function(A) {
		var Lang = A.Lang;

		var ValidationField = A.Component.create(
			{
				ATTRS: {
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
							number: Liferay.Language.get('number'),
							text: Liferay.Language.get('text')
						}
					},

					type: {
						value: 'validation'
					},

					validations: {
						value: {
							number: [
								{
									label: Liferay.Language.get('is-greater-than-or-equal-to'),
									name: 'gteq',
									parameterMessage: Liferay.Language.get('this-number'),
									regex: /^(\w+)\>\=(\d+)$/,
									template: '{name}>={parameter}'
								},
								{
									label: Liferay.Language.get('is-greater-than'),
									name: 'gt',
									parameterMessage: Liferay.Language.get('this-number'),
									regex: /^(\w+)\>(\d+)$/,
									template: '{name}>{parameter}'
								},
								{
									label: Liferay.Language.get('is-equal-to'),
									name: 'eq',
									parameterMessage: Liferay.Language.get('this-number'),
									regex: /^(\w+)\=\=(\d+)$/,
									template: '{name}=={parameter}'
								},
								{
									label: Liferay.Language.get('is-less-than-or-equal-to'),
									name: 'lteq',
									parameterMessage: Liferay.Language.get('this-number'),
									regex: /^(\w+)\<\=(\d+)$/,
									template: '{name}<={parameter}'
								},
								{
									label: Liferay.Language.get('is-less-than'),
									name: 'lt',
									parameterMessage: Liferay.Language.get('this-number'),
									regex: /^(\w+)\<(\d+)$/,
									template: '{name}<{parameter}'
								}
							],
							text: [
								{
									label: Liferay.Language.get('is-empty'),
									name: 'empty',
									regex: /^(\w+)\.isEmpty\(\)$/,
									template: '{name}.isEmpty()'
								},
								{
									label: Liferay.Language.get('is-not-empty'),
									name: 'notEmpty',
									regex: /^\!(\w+)\.isEmpty\(\)$/,
									template: '!{name}.isEmpty()'
								},
								{
									label: Liferay.Language.get('contains'),
									name: 'contains',
									parameterMessage: Liferay.Language.get('this-text'),
									regex: /^(\w+)\.contains\("(\w+)"\)$/,
									template: '{name}.contains("{parameter}")'
								},
								{
									label: Liferay.Language.get('does-not-contain'),
									name: 'notContains',
									parameterMessage: Liferay.Language.get('this-text'),
									regex: /^\!(\w+)\.contains\("(\w+)"\)$/,
									template: '!{name}.contains("{parameter}")'
								}
							]
						}
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-validation',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.after('containerChange', instance._afterValidationContainerChange),
							instance.after('render', instance._afterValidationRender)
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

						return A.merge(
							ValidationField.superclass.getTemplateContext.apply(instance, arguments),
							{
								typesOptions: instance._getTypesOptions(),
								validationsOptions: instance._getValidatiionsOptions()
							}
						);
					},

					getValue: function() {
						var instance = this;

						var selectedValidation = instance.get('selectedValidation');

						var root = instance.getRoot();

						var nameField = root.getField('name');

						var expression = Lang.sub(
							selectedValidation.template,
							{
								name: nameField && nameField.getValue() || '',
								parameter: instance.get('parameterValue')
							}
						);

						return {
							errorMessage: instance._getMessageValue(),
							expression: expression
						};
					},

					setValue: function(expression) {
						var instance = this;

						instance.updateValues(expression);
					},

					updateValues: function(expression) {
						var instance = this;

						A.each(
							instance.get('validations'),
							function(validation, type) {
								validation.forEach(
									function(item) {
										var regex = item.regex;

										if (regex.test(expression)) {
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
					},

					_afterValidationContainerChange: function(event) {
						var instance = this;

						instance._bindContainerEvents();
					},

					_afterValidationRender: function() {
						var instance = this;

						instance._bindContainerEvents();
					},

					_bindContainerEvents: function() {
						var instance = this;

						var container = instance.get('container');

						instance._eventHandlers.push(
							container.delegate('change', A.bind('_onChangeSelects', instance), 'select')
						);
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

						var validations = instance.get('validations');

						return A.Array.find(
							validations[instance.get('selectedType')],
							function(validation) {
								return validation.name === val;
							}
						);
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

						var validations = instance.get('validations');

						return validations[instance.get('selectedType')].map(
							function(validation) {
								return {
									label: validation.label,
									value: validation.name
								};
							}
						);
					},

					_onChangeSelects: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var newVal = currentTarget.val();

						if (currentTarget.hasClass('types-select')) {
							instance.set('selectedType', newVal);

							var validations = instance.get('validations');

							instance.set('selectedValidation', validations[newVal][0].name);
						}
						else {
							instance.set('selectedValidation', newVal);
						}

						instance.render();

						instance._syncParameterNode();
					},

					_setParameterValue: function(value) {
						var instance = this;

						var container = instance.get('container');

						var parameterNode = container.one('.parameter-input');

						parameterNode.val(value);
					},

					_syncParameterNode: function() {
						var instance = this;

						var container = instance.get('container');

						var parameterNode = container.one('.parameter-input');

						var selectedValidation = instance.get('selectedValidation');

						if (selectedValidation.parameterMessage) {
							parameterNode.attr('placeholder', selectedValidation.parameterMessage);
						}

						parameterNode.toggle(!!selectedValidation.parameterMessage);
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