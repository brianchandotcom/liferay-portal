AUI.add(
	'liferay-ddl-form-builder-render-rule-condition',
	function(A) {
		var FormBuilderRenderRuleCondition = function(config) {};

		FormBuilderRenderRuleCondition.ATTRS = {
			functionsMetadata: {
				value: []
			},

			logicOperator: {
				setter: function(val) {
					return val.toUpperCase();
				},
				validator: '_isValidLogicOperator',
				value: Liferay.Language.get('or')
			}
		};

		FormBuilderRenderRuleCondition.prototype = {
			initializer: function() {
				var instance = this;

				var boundingBox = instance.get('boundingBox');

				boundingBox.delegate('click', A.bind(instance._handleLogicOperatorChange, instance), '.logic-operator');
				boundingBox.delegate('click', A.bind(instance._handleDeleteConditionClick, instance), '.condition-card-delete');
				boundingBox.delegate('click', A.bind(instance._handleAddConditionClick, instance), '.form-builder-rule-add-condition');

				instance.after(instance._toggleShowRemoveButton, instance, '_addCondition');

				instance.on('logicOperatorChange', A.bind(instance._onLogicOperatorChange, instance));
				instance.on('*:valueChange', A.bind(instance._handleConditionFieldsChange, instance));
			},

			_addCondition: function(index, condition) {
				var instance = this;

				var contentBox = instance.get('contentBox');

				instance._renderFirstOperand(index, condition, contentBox.one('.condition-if-' + index));
				instance._renderOperator(index, condition, contentBox.one('.condition-operator-' + index));
				instance._renderSecondOperandType(index, condition, contentBox.one('.condition-the-' + index));
				instance._renderSecondOperandInput(index, condition, contentBox.one('.condition-type-value-' + index));
				instance._renderSecondOperandSelectField(index, condition, contentBox.one('.condition-type-value-' + index));
				instance._renderSecondOperandSelectOptions(index, condition, contentBox.one('.condition-type-value-options-' + index));

				instance._conditionsIndexes.push(Number(index));
			},

			_canDeleteCondition: function() {
				var instance = this;

				return instance._conditionsIndexes.length > 1;
			},

			_deleteCondition: function(index) {
				var instance = this;

				instance._destroyConditionFields(index);

				instance.get('boundingBox').one('.form-builder-rule-condition-container-' + index).remove(true);

				var conditionIndex = instance._conditionsIndexes.indexOf(Number(index));

				if (conditionIndex > -1) {
					instance._conditionsIndexes.splice(conditionIndex, 1);
				}
			},

			_destroyConditionFields: function(index) {
				var instance = this;

				instance._conditions[index + '-condition-first-operand'].destroy();
				instance._conditions[index + '-condition-operator'].destroy();
				instance._conditions[index + '-condition-second-operand-type'].destroy();
				instance._conditions[index + '-condition-second-operand-select'].destroy();
				instance._conditions[index + '-condition-second-operand-input'].destroy();

				delete instance._conditions[index + '-condition-first-operand'];
				delete instance._conditions[index + '-condition-operator'];
				delete instance._conditions[index + '-condition-second-operand-type'];
				delete instance._conditions[index + '-condition-second-operand-select'];
				delete instance._conditions[index + '-condition-second-operand-input'];
			},

			_getConditions: function() {
				var instance = this;

				var conditions = [];

				for (var i = 0; i < instance._conditionsIndexes.length; i++) {
					var index = instance._conditionsIndexes[i];

					var condition = {
						'operands': [
							{
								label: instance._getFieldLabel(instance._getFirstOperandValue(index)),
								type: 'field',
								value: instance._getFirstOperandValue(index)
							}
						],
						operator: instance._getOperatorValue(index)
					};

					if (instance._isBinaryCondition(index)) {
						if (instance._getSecondOperandTypeValue(index) === 'constant') {
							if (instance._getSecondOperandValue(index, 'input')) {
								condition.operands.push(
									{
										type: 'constant',
										value: instance._getSecondOperandValue(index, 'input')
									}
								);
							}
							else {
								condition.operands.push(
									{
										label: instance._getOptionsLabel(instance._getSecondOperand(index, 'options'), instance._getSecondOperandValue(index, 'options')),
										type: 'constant',
										value: instance._getSecondOperandValue(index, 'options')
									}
								);
							}
						}
						else {
							condition.operands.push(
								{
									type: 'field',
									value: instance._getSecondOperandValue(index, 'fields')
								}
							);
						}
					}

					conditions.push(condition);
				}

				return conditions;
			},

			_getFieldLabel: function(fieldValue) {
				var instance = this;

				var field = instance.get('fields').find(
					function(currentField) {
						return currentField.value === fieldValue;
					}
				);

				return field && field.label;
			},

			_getFieldOptions: function(fieldName) {
				var instance = this;

				var field = instance.get('fields').find(
					function(currentField) {
						return currentField.value === fieldName;
					}
				);

				return (field && field.options) || [];
			},

			_getFieldType: function(fieldValue) {
				var instance = this;

				var field = instance.get('fields').find(
					function(currentField) {
						return currentField.value === fieldValue;
					}
				);

				return field && field.type;
			},

			_getFirstOperand: function(index) {
				var instance = this;

				return instance._conditions[index + '-condition-first-operand'];
			},

			_getFirstOperandValue: function(index) {
				var instance = this;

				return instance._getFirstOperand(index).getValue();
			},

			_getOperator: function(index) {
				var instance = this;

				return instance._conditions[index + '-condition-operator'];
			},

			_getOperatorValue: function(index) {
				var instance = this;

				return instance._getOperator(index).getValue();
			},

			_getSecondOperand: function(index, type) {
				var instance = this;

				switch (type) {
				case 'fields':
					return instance._conditions[index + '-condition-second-operand-select'];
				case 'options':
					return instance._conditions[index + '-condition-second-operand-options-select'];
				default:
					return instance._conditions[index + '-condition-second-operand-input'];
				}
			},

			_getSecondOperandType: function(index) {
				var instance = this;

				return instance._conditions[index + '-condition-second-operand-type'];
			},

			_getSecondOperandTypeValue: function(index) {
				var instance = this;

				return instance._getSecondOperandType(index).getValue();
			},

			_getSecondOperandValue: function(index, type) {
				var instance = this;

				return instance._getSecondOperand(index, type).getValue();
			},

			_handleAddConditionClick: function() {
				var instance = this;

				var conditionListNode = instance.get('boundingBox').one('.liferay-ddl-form-builder-rule-condition-list');

				var index = instance._conditionsIndexes[instance._conditionsIndexes.length - 1] + 1;

				var conditionTemplateRenderer = Liferay.DDM.SoyTemplateUtil.getTemplateRenderer('ddl.rule.condition');

				conditionListNode.append(
					conditionTemplateRenderer(
						{
							deleteIcon: Liferay.Util.getLexiconIconTpl('trash', 'icon-monospaced'),
							index: index,
							logicOperator: instance.get('logicOperator')
						}
					)
				);

				instance._addCondition(index);
			},

			_handleConditionFieldsChange: function(event) {
				var instance = this;

				var field = event.target;

				var fieldName = field.get('fieldName');

				if (fieldName && fieldName.match('-condition')) {
					var index = fieldName.split('-')[0];

					if (fieldName.match('-first-operand')) {
						instance._updateOperatorList(field.get('dataType'), index);
						instance._updateSecondOperandFieldVisibility(index);
					}
					else if (fieldName.match('-operator')) {
						instance._updateTypeFieldVisibility(index);
						instance._updateSecondOperandFieldVisibility(index);
					}
					else if (fieldName.match('-second-operand-type')) {
						instance._updateSecondOperandFieldVisibility(index);
					}
				}
			},

			_handleDeleteConditionClick: function(event) {
				var instance = this;

				var index = event.currentTarget.getData('card-id');

				if (instance._canDeleteCondition()) {
					instance._deleteCondition(index);
				}

				instance._toggleShowRemoveButton();
			},

			_handleLogicOperatorChange: function(event) {
				var instance = this;

				event.preventDefault();

				instance.set('logicOperator', event.currentTarget.get('text'));

				A.one('.dropdown-toggle-operator .dropdown-toggle-selected-value').setHTML(event.currentTarget.get('text'));
			},

			_hideSecondOperandField: function(index) {
				var instance = this;

				var secondOperandFields = instance._getSecondOperand(index, 'fields');
				var secondOperandOptions = instance._getSecondOperand(index, 'options');
				var secondOperandsInput = instance._getSecondOperand(index, 'input');

				instance._setVisibleToOperandField(secondOperandFields);
				instance._setVisibleToOperandField(secondOperandOptions);
				instance._setVisibleToOperandField(secondOperandsInput);
			},

			_isBinaryCondition: function(index) {
				var instance = this;

				var value = instance._getOperatorValue(index);

				return value === 'equals-to' || value === 'not-equals-to' || value === 'contains' || value === 'not-contains';
			},

			_isFieldList: function(field) {
				var instance = this;

				var value = field.getValue();

				return instance._getFieldOptions(value).length > 0 && instance._getFieldType(value) !== 'text';
			},

			_isUnaryCondition: function(index) {
				var instance = this;

				var value = instance._getOperatorValue(index);

				return value === 'is-email-address' || value === 'is-url';
			},

			_isValidLogicOperator: function(operator) {
				var instance = this;

				var strings = instance.get('strings');

				if (A.Lang.isString(operator)) {
					var upperCaseOperator = operator.toUpperCase();

					return upperCaseOperator === strings.and || upperCaseOperator === strings.or;
				}

				return false;
			},

			_onLogicOperatorChange: function(event) {
				var instance = this;

				var strings = instance.get('strings');

				var logicOperatorString = strings.and;

				if (event.newVal === 'or') {
					logicOperatorString = strings.or;
				}

				A.all('.operator .panel-body').each(
					function(operatorNode) {
						operatorNode.set('text', logicOperatorString);
					}
				);
			},

			_renderConditions: function(conditions) {
				var instance = this;

				var conditionsLength = conditions.length;

				for (var i = 0; i < conditionsLength; i++) {
					instance._addCondition(i, conditions[i]);
				}

				if (instance._conditionsIndexes.length === 0) {
					instance._addCondition(0);
				}
			},

			_renderFirstOperand: function(index, condition, container) {
				var instance = this;

				var value;

				if (condition) {
					value = condition.operands[0].value;
				}

				var field = new Liferay.DDM.Field.Select(
					{
						bubbleTargets: [instance],
						fieldName: index + '-condition-first-operand',
						label: instance.get('strings').if,
						options: instance.get('fields'),
						showLabel: false,
						value: value,
						visible: true
					}
				);

				field.render(container);

				instance._conditions[index + '-condition-first-operand'] = field;
			},

			_renderOperator: function(index, condition, container) {
				var instance = this;

				var field = new Liferay.DDM.Field.Select(
					{
						bubbleTargets: [instance],
						fieldName: index + '-condition-operator',
						showLabel: false,
						visible: true
					}
				);

				field.render(container);

				instance._conditions[index + '-condition-operator'] = field;

				if (condition) {
					instance._updateOperatorList(instance._getFieldDataType(condition.operands[0].value), index);

					field.setValue(condition.operator);
				}
			},

			_renderSecondOperandInput: function(index, condition, container) {
				var instance = this;

				var value;

				var firstOperand = instance._getFirstOperand(index);

				var secondOperandValue = instance._getSecondOperandTypeValue(index);

				var visible = secondOperandValue === 'constant' && !instance._isFieldList(firstOperand);

				if (condition && instance._isBinaryCondition(index) && visible) {
					value = condition.operands[1].value;
				}

				var field = new Liferay.DDM.Field.Text(
					{
						bubbleTargets: [instance],
						fieldName: index + '-condition-second-operand-input',
						options: [],
						showLabel: false,
						strings: {},
						value: value,
						visible: visible
					}
				);

				field.render(container);

				instance._conditions[index + '-condition-second-operand-input'] = field;
			},

			_renderSecondOperandSelectField: function(index, condition, container) {
				var instance = this;

				var value;

				var visible = instance._getSecondOperandTypeValue(index) === 'field';

				if (condition && instance._isBinaryCondition(index) && visible) {
					value = condition.operands[1].value;
				}

				var field = new Liferay.DDM.Field.Select(
					{
						bubbleTargets: [instance],
						fieldName: index + '-condition-second-operand-select',
						label: 'Put this label after',
						options: instance.get('fields'),
						showLabel: false,
						value: value,
						visible: visible
					}
				);

				field.render(container);

				instance._conditions[index + '-condition-second-operand-select'] = field;
			},

			_renderSecondOperandSelectOptions: function(index, condition, container) {
				var instance = this;

				var value;

				var options = [];

				var visible = instance._getSecondOperandTypeValue(index) === 'constant' &&
					instance._isFieldList(instance._getFirstOperand(index));

				if (condition && instance._isBinaryCondition(index) && visible) {
					options = instance._getFieldOptions(instance._getFirstOperandValue(index));
					value = condition.operands[1].value;
				}

				var field = new Liferay.DDM.Field.Select(
					{
						bubbleTargets: [instance],
						fieldName: index + '-condition-second-operand-options-select',
						label: 'Put this label after',
						options: options,
						showLabel: false,
						value: value,
						visible: visible
					}
				);

				field.render(container);

				instance._conditions[index + '-condition-second-operand-options-select'] = field;
			},

			_renderSecondOperandType: function(index, condition, container) {
				var instance = this;

				var value;

				if (condition && instance._isBinaryCondition(index)) {
					value = condition.operands[1].type;
				}

				var field = new Liferay.DDM.Field.Select(
					{
						bubbleTargets: [instance],
						fieldName: index + '-condition-second-operand-type',
						label: instance.get('strings').the,
						options: [
							{
								label: instance.get('strings').value,
								value: 'constant'
							},
							{
								label: instance.get('strings').otherField,
								value: 'field'
							}
						],
						showLabel: false,
						value: value,
						visible: instance._isBinaryCondition(index)
					}
				);

				field.render(container);

				instance._conditions[index + '-condition-second-operand-type'] = field;
			},

			_setVisibleToOperandField: function(field) {
				if (field) {
					field.set('visible', false);
				}
			},

			_updateOperatorList: function(dataType, conditionIndex) {
				var instance = this;

				var operator = instance._getOperator(conditionIndex);

				var operatorTypes = instance.get('functionsMetadata');

				var options = [];

				if (dataType === 'string') {
					for (var i = 0; i < operatorTypes.text.length; i++) {
						options.push(
							A.merge(
								{
									value: operatorTypes.text[i].name
								},
								operatorTypes.text[i]
							)
						);
					}
				}
				else if (dataType === 'number') {
					for (var j = 0; j < operatorTypes.number.length; j++) {
						options.push(
							A.merge(
								{
									value: operatorTypes.number[j].name
								},
								operatorTypes.number[j]
							)
						);
					}
				}

				operator.set('options', options);
			},

			_updateSecondOperandFieldVisibility: function(index) {
				var instance = this;

				instance._hideSecondOperandField(index);

				var secondOperandType = instance._getSecondOperandType(index);

				var secondOperandTypeValue = secondOperandType ? secondOperandType.getValue() : '';

				if (secondOperandTypeValue && secondOperandType.get('visible')) {
					var secondOperandFields = instance._getSecondOperand(index, 'fields');

					var secondOperandOptions = instance._getSecondOperand(index, 'options');

					if (secondOperandTypeValue === 'field') {
						secondOperandFields.set('visible', true);
						secondOperandOptions.cleanSelect();
					}
					else {
						var options = instance._getFieldOptions(instance._getFirstOperandValue(index));

						if (options.length > 0 && instance._getFieldType(instance._getFirstOperandValue(index)) !== 'text') {
							secondOperandOptions.set('options', options);
							secondOperandOptions.set('visible', true);

							secondOperandFields.cleanSelect();
						}
						else {
							instance._getSecondOperand(index, 'input').set('visible', true);

							secondOperandFields.cleanSelect();
							secondOperandOptions.cleanSelect();
						}
					}
				}
			},

			_updateTypeFieldVisibility: function(index) {
				var instance = this;

				var secondOperandType = instance._getSecondOperandType(index);

				if (secondOperandType) {
					if (instance._getFirstOperandValue(index) && instance._getOperatorValue(index) && !instance._isUnaryCondition(index)) {
						secondOperandType.set('visible', true);
					}
					else {
						instance._getSecondOperand(index, 'fields').set('value', '');
						secondOperandType.set('visible', false);
					}
				}
			}
		};

		Liferay.namespace('DDL').FormBuilderRenderRuleCondition = FormBuilderRenderRuleCondition;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);