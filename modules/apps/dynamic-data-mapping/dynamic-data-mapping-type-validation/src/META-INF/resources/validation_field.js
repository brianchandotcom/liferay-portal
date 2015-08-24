AUI.add(
	'liferay-ddm-form-field-validation',
	function(A) {
		var Lang = A.Lang;

		var TPL_OPTION = '<option value="{value}">{label}</option>';

		var ValidationField = A.Component.create(
			{
				ATTRS: {
					parameterValue: {
						getter: '_getParameterValue',
						setter: '_setParameterValue'
					},

					selectedType: {
						getter: '_getSelectedType',
						setter: '_setSelectedType'
					},

					selectedValidation: {
						getter: '_getSelectedValidation',
						setter: '_setSelectedValidation'
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

						var container = instance.get('container');

						instance._eventHandlers.push(
							container.delegate('change', A.bind(instance._onChangeSelects, instance), 'select')
						);
					},

					extractParameterValue: function(regex, expression) {
						var instance = this;

						regex.lastIndex = 0;

						var matches = regex.exec(expression);

						return matches && matches[2] || '';
					},

					getValue: function() {
						var instance = this;

						var selectedValidation = instance.get('selectedValidation');

						var root = instance.getRoot();

						var nameField = root.getField('name');

						return Lang.sub(
							selectedValidation.template,
							{
								name: nameField && nameField.getValue() || '',
								parameter: instance.get('parameterValue')
							}
						);
					},

					render: function() {
						var instance = this;

						ValidationField.superclass.render.apply(instance, arguments);

						instance._populateTypesNode();
						instance._populateValidationsNode();
						instance._populateParameterNode();
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

					_afterValueChange: function(event) {
						var instance = this;

						ValidationField.superclass._afterValueChange.apply(instance, arguments);

						instance.updateValues(event.newVal);
					},

					_getParameterValue: function() {
						var instance = this;

						var container = instance.get('container');

						var parameterNode = container.one('.parameter-input');

						return parameterNode.val();
					},

					_getSelectedType: function() {
						var instance = this;

						var container = instance.get('container');

						var typesNode = container.one('.types-select');

						return typesNode.val();
					},

					_getSelectedValidation: function() {
						var instance = this;

						var container = instance.get('container');

						var validations = instance.get('validations');

						var validationsNode = container.one('.validations-select');

						return A.Array.find(
							validations[instance.get('selectedType')],
							function(validation) {
								return validation.name === validationsNode.val();
							}
						);
					},

					_onChangeSelects: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						if (currentTarget.hasClass('types-select')) {
							instance._populateValidationsNode();
						}

						instance._populateParameterNode();
					},

					_populateParameterNode: function() {
						var instance = this;

						var container = instance.get('container');

						var parameterNode = container.one('.parameter-input');

						var selectedValidation = instance.get('selectedValidation');

						if (selectedValidation.parameterMessage) {
							parameterNode.attr('placeholder', selectedValidation.parameterMessage);
						}

						parameterNode.toggle(!!selectedValidation.parameterMessage);
					},

					_populateTypesNode: function() {
						var instance = this;

						var container = instance.get('container');

						var strings = instance.get('strings');

						var typesNode = container.one('.types-select');

						typesNode.empty();

						A.each(
							instance.get('validations'),
							function(validation, validationType) {
								typesNode.append(
									Lang.sub(
										TPL_OPTION,
										{
											label: strings[validationType],
											value: validationType
										}
									)
								);
							}
						);
					},

					_populateValidationsNode: function() {
						var instance = this;

						var container = instance.get('container');

						var validationsNode = container.one('.validations-select');

						var validations = instance.get('validations');

						validationsNode.empty();

						A.each(
							validations[instance.get('selectedType')],
							function(validation) {
								validationsNode.append(
									Lang.sub(
										TPL_OPTION,
										{
											label: validation.label,
											value: validation.name
										}
									)
								);
							}
						);
					},

					_setParameterValue: function(value) {
						var instance = this;

						var container = instance.get('container');

						var parameterNode = container.one('.parameter-input');

						parameterNode.val(value);
					},

					_setSelectedType: function(value) {
						var instance = this;

						var container = instance.get('container');

						var typesNode = container.one('.types-select');

						typesNode.val(value);

						instance._populateValidationsNode();
						instance._populateParameterNode();
					},

					_setSelectedValidation: function(value) {
						var instance = this;

						var container = instance.get('container');

						var validationsNode = container.one('.validations-select');

						validationsNode.val(value);

						instance._populateParameterNode();
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