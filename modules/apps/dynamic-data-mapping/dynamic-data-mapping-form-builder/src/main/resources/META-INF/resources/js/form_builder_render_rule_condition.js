AUI.add(
	'liferay-ddm-form-builder-render-rule-condition',
	function(A) {
		var currentUser = {
			dataType: 'user',
			label: Liferay.Language.get('user'),
			value: 'user'
		};

		var CSS_CAN_REMOVE_ITEM = A.getClassName('can', 'remove', 'item');

		var FormBuilderRenderRuleCondition = function(config) {};

		FormBuilderRenderRuleCondition.ATTRS = {
			if: {
				value: Liferay.Language.get('if')
			},

			logicOperator: {
				value: 'or'
			}
		};

		FormBuilderRenderRuleCondition.prototype = {
			initializer: function() {
				var instance = this;

				var boundingBox = instance.get('boundingBox');

				boundingBox.delegate('click', A.bind(instance._handleLogicOperatorChange, instance), '.logic-operator');
				boundingBox.delegate('click', A.bind(instance._handleDeleteConditionClick, instance), '.condition-card-delete');
				boundingBox.delegate('click', A.bind(instance._handleAddConditionClick, instance), '.form-builder-rule-add-condition');

				instance.after(instance._toggleDeleteConditionButton, instance, '_addCondition');

				instance.on('logicOperatorChange', A.bind(instance._onLogicOperatorChange, instance));

				instance.after('*:valueChange', A.bind(instance._handleConditionFieldsChange, instance));

				instance._validator = new Liferay.DDM.FormBuilderRuleValidator();
			},

			_addCondition: function(index, condition) {
				var instance = this;

				var contentBox = instance.get('contentBox');

				instance._renderFirstOperand(index, condition, contentBox.one('.condition-if-' + index));
				instance._renderOperator(index, condition, contentBox.one('.condition-operator-' + index));
				instance._renderSecondOperandType(index, condition, contentBox.one('.condition-type-' + index));
				instance._renderSecondOperandInput(index, condition, contentBox.one('.condition-type-value-' + index));
				instance._renderSecondOperandSelectField(index, condition, contentBox.one('.condition-type-value-' + index));
				instance._renderSecondOperandSelectOptions(index, condition, contentBox.one('.condition-type-value-options-' + index));

				instance._updateTypeFieldVisibility(index);

				instance._conditionsIndexes.push(Number(index));
			},

			_canDeleteCondition: function() {
				var instance = this;

				return instance._conditionsIndexes.length > 1;
			},

			_clearOperatorField: function(index) {
				var instance = this;

				var operator = instance._getOperator(index);

				operator.cleanSelect();
				operator.render();
			},

			_createDateField: function(index, condition, config, container, firstOperand, secondOperandTypeValue) {
				var instance = this;

				var visible = false;

				if (instance._isDate(secondOperandTypeValue) && instance._isBinaryCondition(index) && !instance._isFieldList(firstOperand)) {
					visible = true;
				}

				var value = '';

				if (condition && instance._isBinaryCondition(index) && visible) {
					value = condition.operands[1].value;
				}

				config.fieldName = index + '-condition-second-operand-input-date';
				config.label = '';
				config.readOnly = false;
				config.value = value;
				config.visible = visible;

				var fieldDate = instance.createDateField(
					config
				);

				instance._renderField(index, config, container, fieldDate);
			},

			_createDecimalField: function(index, condition, config, container, firstOperand, secondOperandTypeValue) {
				var instance = this;

				var visible = false;

				if (instance._isNumeric(secondOperandTypeValue) && instance._isBinaryCondition(index) && (secondOperandTypeValue === 'double') && !instance._isFieldList(firstOperand)) {
					visible = true;
				}

				var value = '';

				if (condition && instance._isBinaryCondition(index) && secondOperandTypeValue == 'double' && visible) {
					value = condition.operands[1].value;
				}

				config.fieldName = index + '-condition-second-operand-input-decimal';
				config.readOnly = false;
				config.value = value;
				config.visible = visible;

				var fieldDouble = instance.createDecimalField(
					config
				);

				instance._renderField(index, config, container, fieldDouble);
			},

			_createIntegerField: function(index, condition, config, container, firstOperand, secondOperandTypeValue) {
				var instance = this;

				var visible = false;

				if (instance._isNumeric(secondOperandTypeValue) && instance._isBinaryCondition(index) && secondOperandTypeValue == 'integer' && !instance._isFieldList(firstOperand)) {
					visible = true;
				}

				var value = '';

				if (condition && instance._isBinaryCondition(index) && secondOperandTypeValue == 'integer' && visible) {
					value = condition.operands[1].value;
				}

				config.fieldName = index + '-condition-second-operand-input-integer';
				config.readOnly = false;
				config.value = value;
				config.visible = visible;

				var fieldInteger = instance.createIntegerField(
					config
				);

				instance._renderField(index, config, container, fieldInteger);
			},

			_createTextField: function(index, condition, config, container, firstOperand, type) {
				var instance = this;

				var visible = false;

				if (instance._isText(type) && instance._isBinaryCondition(index) && !instance._isFieldList(firstOperand)) {
					visible = true;
				}

				var value = '';

				if (condition && condition.operands[1] && instance._isBinaryCondition(index) && condition.operands[1].type == 'string' && visible) {
					value = condition.operands[1].value;
				}

				config.fieldName = index + '-condition-second-operand-input-text';
				config.value = value;
				config.visible = visible;

				var fieldText = instance.createTextField(
					config
				);

				instance._renderField(index, config, container, fieldText);
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
				instance._conditions[index + '-condition-second-operand-input-date'].destroy();
				instance._conditions[index + '-condition-second-operand-input-decimal'].destroy();
				instance._conditions[index + '-condition-second-operand-input-integer'].destroy();
				instance._conditions[index + '-condition-second-operand-input-text'].destroy();

				delete instance._conditions[index + '-condition-first-operand'];
				delete instance._conditions[index + '-condition-operator'];
				delete instance._conditions[index + '-condition-second-operand-type'];
				delete instance._conditions[index + '-condition-second-operand-select'];
				delete instance._conditions[index + '-condition-second-operand-input-date'];
				delete instance._conditions[index + '-condition-second-operand-input-decimal'];
				delete instance._conditions[index + '-condition-second-operand-input-integer'];
				delete instance._conditions[index + '-condition-second-operand-input-text'];
			},

			_disableOperatorField: function(index, value) {
				var instance = this;

				if (value.length == 0) {
					var operator = instance._getOperator(index);
					var operatorContainer = operator.get('container');
					var operatorTrigger = operatorContainer.one('.select-field-trigger');

					operatorTrigger.attr('disabled', true);
				}
			},

			_getConditions: function() {
				var instance = this;

				var conditions = [];

				for (var i = 0; i < instance._conditionsIndexes.length; i++) {
					var index = instance._conditionsIndexes[i];

					var type = instance._getFirstOperandValue(index) === 'user' ? 'user' : 'field';

					var condition = {
						'operands': [
							{
								label: instance._getFieldProperty(instance._getFirstOperandValue(index), 'label'),
								repeatable: instance._getFieldProperty(instance._getFirstOperandValue(index), 'repeatable'),
								type: type,
								value: instance._getFirstOperandValue(index)
							}
						],
						operator: instance._getOperatorValue(index)
					};

					if (instance._isBinaryCondition(index)) {

						var secondOperandTypeValue = instance._getSecondOperandTypeValue(index);

						if (secondOperandTypeValue === 'field') {
							condition.operands.push(
								{
									type: 'field',
									value: instance._getSecondOperandValue(index, 'fields')
								}
							);
						}
						else if (instance._isConstant(secondOperandTypeValue)) {
							var dataType = instance._getFieldProperty(instance._getFirstOperandValue(index), 'dataType');
							var secondOperandValueFromInputTypes = instance._getSecondOperandValueFromInputTypes(index);

							if (secondOperandValueFromInputTypes) {
								condition.operands.push(
									{
										type: dataType,
										value: secondOperandValueFromInputTypes
									}
								);
							}
							else {
								condition.operands.push(
									{
										label: instance._getOptionsLabel(instance._getSecondOperand(index, 'options'), instance._getSecondOperandValue(index, 'options')),
										type: dataType,
										value: instance._getSecondOperandValue(index, 'options')
									}
								);
							}
						}
						else if (instance._getOperatorValue(index) === 'belongs-to') {
							condition.operands.push(
								{
									label: secondOperandTypeValue,
									type: 'list',
									value: secondOperandTypeValue
								}
							);
						}
					}

					conditions.push(condition);
				}

				return conditions;
			},

			_getFieldProperty: function(fieldValue, property) {
				var instance = this;

				var field = instance.get('fields').find(
					function(currentField) {
						return currentField.value === fieldValue;
					}
				);

				if (property === 'options') {
					return (field && field[property]) || [];
				}

				return field && field[property];
			},

			_getFirstOperand: function(index) {
				var instance = this;

				return instance._conditions[index + '-condition-first-operand'];
			},

			_getFirstOperandValue: function(index) {
				var instance = this;

				var firstOperand = instance._getFirstOperand(index);

				var value = firstOperand.getValue();

				instance._disableOperatorField(index, value);

				return value[0] || '';
			},

			_getOperator: function(index) {
				var instance = this;

				return instance._conditions[index + '-condition-operator'];
			},

			_getOperatorValue: function(index) {
				var instance = this;

				var operator = instance._getOperator(index);

				var value = operator.getValue();

				return value[0] || '';
			},

			_getProperty: function(field, property) {
				var instance = this;

				var options = field.get('options').concat(field.get('fixedOptions'));
				var value = field.getValue();

				for (var i = 0; i < options.length; i++) {
					var option = options[i];

					if (value.indexOf(option.value) > -1) {
						return option[property];
					}
				}

				return '';
			},

			_getSecondOperand: function(index, type) {
				var instance = this;

				switch (type) {
				case 'fields':
					return instance._conditions[index + '-condition-second-operand-select'];
				case 'options':
					return instance._conditions[index + '-condition-second-operand-options-select'];
				case 'input-integer':
					return instance._conditions[index + '-condition-second-operand-input-integer'];
				case 'input-decimal':
					return instance._conditions[index + '-condition-second-operand-input-decimal'];
				case 'input-date':
					return instance._conditions[index + '-condition-second-operand-input-date'];
				default:
					return instance._conditions[index + '-condition-second-operand-input-text'];
				}
			},

			_getSecondOperandType: function(index) {
				var instance = this;

				return instance._conditions[index + '-condition-second-operand-type'];
			},

			_getSecondOperandTypeValue: function(index) {
				var instance = this;

				var secondOperandType = instance._getSecondOperandType(index);

				var value = secondOperandType.getValue();

				return value[0] || '';
			},

			_getSecondOperandValue: function(index, type) {
				var instance = this;

				var secondOperand = instance._getSecondOperand(index, type);

				var value = secondOperand.getValue();

				if (A.Lang.isArray(value)) {
					value = value[0];
				}

				return value || '';
			},

			_getSecondOperandValueFromInputTypes: function(index) {
				var instance = this;

				var inputTypes = ['input-date', 'input-decimal', 'input-integer', 'input-text'];
				var secondOperandValue = '';

				for (var i = 0; i < inputTypes.length; i++) {
					secondOperandValue = instance._getSecondOperandValue(index, inputTypes[i]);

					if (secondOperandValue) {
						break;
					}
				}

				return secondOperandValue;
			},

			_handleAddConditionClick: function() {
				var instance = this;

				var conditionListNode = instance.get('boundingBox').one('.liferay-ddm-form-builder-rule-condition-list');

				var index = instance._conditionsIndexes[instance._conditionsIndexes.length - 1] + 1;

				var conditionTemplateRenderer = Liferay.DDM.SoyTemplateUtil.getTemplateRenderer('DDMRule.condition');

				var container = document.createDocumentFragment();

				new conditionTemplateRenderer(
					{
						deleteIcon: Liferay.Util.getLexiconIconTpl('trash', 'icon-monospaced'),
						if: instance.get('if'),
						index: index,
						logicalOperator: instance.get('logicOperator')
					},
					container
				);

				conditionListNode.append(container.firstChild.outerHTML);

				instance._addCondition(index);

				instance._updateLogicOperatorEnableState();
			},

			_handleConditionFieldsChange: function(event) {
				var instance = this;

				var field = event.target;
				var fieldName = field.get('fieldName');

				if (fieldName) {
					var index = fieldName.split('-')[0];

					if (fieldName.match('-condition-first-operand')) {
						var dataType = instance._getProperty(field, 'dataType');
						var operatorSelected = instance._getOperator(index);
						var repeatable = instance._getProperty(field, 'repeatable');

						operatorSelected.cleanSelect();

						instance._hideSecondOperandField(index);

						instance._hideSecondOperandTypeField(index);

						instance._clearOperatorField(index);

						instance._updateOperatorList(index, dataType, repeatable);
					}
					else if (fieldName.match('-condition-operator')) {
						var operator = event.newVal[0];

						instance._updateSecondOperandType(operator, index);
						instance._updateTypeFieldVisibility(index);
					}
					else if (fieldName.match('-condition-second-operand-type')) {
						instance._updateSecondOperandFieldVisibility(index);

						instance._conditions[index + '-condition-second-operand-input-integer'].setValue('');
						instance._conditions[index + '-condition-second-operand-input-text'].setValue('');
						instance._conditions[index + '-condition-second-operand-input-decimal'].setValue('');
						instance._conditions[index + '-condition-second-operand-input-date'].setValue('');
					}
				}
			},

			_handleDeleteConditionClick: function(event) {
				var instance = this;

				var index = event.currentTarget.getData('card-id');

				if (instance._canDeleteCondition()) {
					instance._deleteCondition(index);
				}

				instance._toggleDeleteConditionButton();

				instance._updateLogicOperatorEnableState();
			},

			_handleLogicOperatorChange: function(event) {
				var instance = this;

				event.preventDefault();

				instance.set('logicOperator', event.currentTarget.getData('logical-operator-value'));

				A.one('.dropdown-toggle-operator .dropdown-toggle-selected-value').setHTML(event.currentTarget.get('text'));
			},

			_hideSecondOperandField: function(index) {
				var instance = this;

				var secondOperandFields = instance._getSecondOperand(index, 'fields');
				var secondOperandOptions = instance._getSecondOperand(index, 'options');
				var secondOperandsInputDate = instance._getSecondOperand(index, 'input-date');
				var secondOperandsInputDecimal = instance._getSecondOperand(index, 'input-decimal');
				var secondOperandsInputInteger = instance._getSecondOperand(index, 'input-integer');
				var secondOperandsInputText = instance._getSecondOperand(index, 'input-text');

				instance._setVisibleToOperandField(secondOperandFields, false);
				instance._setVisibleToOperandField(secondOperandOptions, false);
				instance._setVisibleToOperandField(secondOperandsInputDate, false);
				instance._setVisibleToOperandField(secondOperandsInputDecimal, false);
				instance._setVisibleToOperandField(secondOperandsInputInteger, false);
				instance._setVisibleToOperandField(secondOperandsInputText, false);
			},

			_hideSecondOperandTypeField: function(index) {
				var instance = this;

				var secondOperandType = instance._getSecondOperandType(index);

				instance._setVisibleToOperandField(secondOperandType, false);
				secondOperandType.set('value', '');
			},

			_isBinaryCondition: function(index) {
				var instance = this;

				var value = instance._getOperatorValue(index);

				return value === 'equals-to' || value === 'not-equals-to' || value === 'contains' || value === 'not-contains' || value === 'belongs-to' || value === 'greater-than' || value === 'greater-than-equals' || value === 'less-than' || value === 'less-than-equals';
			},

			_isConstant: function(operandTypeValue) {
				var instance = this;

				return operandTypeValue === 'double' || operandTypeValue === 'integer' || operandTypeValue === 'string';
			},

			_isDate: function(operandTypeValue) {
				var instance = this;

				return operandTypeValue === 'date';
			},

			_isEmpty: function(operator) {
				return operator === '';
			},

			_isFieldList: function(field) {
				var instance = this;

				var value = field.getValue()[0] || '';

				return instance._getFieldProperty(value, 'options').length > 0 && instance._getFieldProperty(value, 'type') !== 'text';
			},

			_isNumeric: function(operandTypeValue) {
				return operandTypeValue === 'double' || operandTypeValue === 'integer';
			},

			_isText: function(operandTypeValue) {
				var instance = this;

				return operandTypeValue === 'text';
			},

			_isUnaryCondition: function(index) {
				var instance = this;

				var value = instance._getOperatorValue(index);

				return value === 'is-email-address' || value === 'is-url' || value === 'is-empty' || value === 'not-is-empty';
			},

			_onLogicOperatorChange: function(event) {
				var instance = this;

				var strings = instance.get('strings');

				var logicOperatorString = strings['and'];

				if (event.newVal === 'or') {
					logicOperatorString = strings['or'];
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

			_renderField: function(index, config, container, field) {
				var instance = this;

				field.render(container);

				instance._conditions[config.fieldName] = field;
			},

			_renderFirstOperand: function(index, condition, container) {
				var instance = this;

				var fixedFields = [];

				var value = [];

				if (condition && condition.operands[0].value) {
					value = [condition.operands[0].value];
				}

				var fields = instance.get('fields').slice();

				fixedFields.push(currentUser);

				var field = instance.createSelectField(
					{
						fieldName: index + '-condition-first-operand',
						fixedOptions: fixedFields,
						label: instance.get('strings').if,
						options: fields,
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

				var value = [];

				if (condition && !instance._isEmpty(condition.operator)) {
					value = [condition.operator];
				}

				var field = instance.createSelectField(
					{
						fieldName: index + '-condition-operator',
						options: [],
						showLabel: false,
						value: value,
						visible: true
					}
				);
				instance._conditions[index + '-condition-operator'] = field;

				field.render(container);

				if (condition) {
					var operand = condition.operands[0];

					instance._updateOperatorList(index, instance._getFieldProperty(operand.value, 'dataType'), operand.repeatable);
				}
			},

			_renderSecondOperandInput: function(index, condition, container) {
				var instance = this;

				var firstOperand = instance._getFirstOperand(index);

				var secondOperandTypeValue = instance._getSecondOperandTypeValue(index);

				var type = instance._getFieldProperty(instance._getFirstOperandValue(index), 'type');

				var config = {
					options: [],
					placeholder: '',
					showLabel: false,
					strings: {}
				};

				instance._createDateField(index, condition, config, container, firstOperand, type);
				instance._createTextField(index, condition, config, container, firstOperand, type);
				instance._createIntegerField(index, condition, config, container, firstOperand, secondOperandTypeValue);
				instance._createDecimalField(index, condition, config, container, firstOperand, secondOperandTypeValue);
			},

			_renderSecondOperandSelectField: function(index, condition, container) {
				var instance = this;

				var value = [];

				var visible = instance._getSecondOperandTypeValue(index) === 'field';

				if (condition && (condition.operands.length > 1) && instance._isBinaryCondition(index) && visible) {
					if (!instance._isEmpty(condition.operands[1].value)) {
						value = [condition.operands[1].value];
					}
				}

				var field = instance.createSelectField(
					{
						fieldName: index + '-condition-second-operand-select',
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

				var options = [];
				var value = [];

				var visible = instance._isConstant(instance._getSecondOperandTypeValue(index)) &&
					instance._isFieldList(instance._getFirstOperand(index));

				if (condition && instance._isBinaryCondition(index) && visible) {
					options = instance._getFieldProperty(instance._getFirstOperandValue(index), 'options');
					if (!instance._isEmpty(condition.operands[1].value)) {
						value = [condition.operands[1].value];
					}
				}

				var field = instance.createSelectField(
					{
						fieldName: index + '-condition-second-operand-options-select',
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

				var value = [];

				if (condition && instance._isBinaryCondition(index) && condition.operands[1]) {
					value = [condition.operands[1].type];
				}

				var field = instance.createSelectField(
					{
						fieldName: index + '-condition-second-operand-type',
						label: instance.get('strings').the,
						options: [
							{
								label: instance.get('strings').value,
								value: instance._getFieldProperty(instance._getFirstOperandValue(index), 'dataType')
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

				if (condition && instance._isBinaryCondition(index)) {
					instance._updateSecondOperandType(condition.operator, index);

					if (condition.operands[0].type === 'user') {
						field.set('value', [condition.operands[1].value]);
					}
				}
			},

			_setVisibleToOperandField: function(field, visibility) {
				if (field) {
					field.set('visible', visibility);
					field.get('container').addClass('hide');
				}
			},

			_toggleDeleteConditionButton: function() {
				var instance = this;

				var contentBox = instance.get('contentBox');

				var conditionList = contentBox.one('.liferay-ddm-form-builder-rule-condition-list');

				var conditionItems = conditionList.all('.timeline-item');

				conditionList.toggleClass(CSS_CAN_REMOVE_ITEM, conditionItems.size() > 2);
			},

			_updateLogicOperatorEnableState: function() {
				var instance = this;

				var logicOperatorNode = instance.get('boundingBox').one('.liferay-ddm-form-builder-rule-condition-list').one('.dropdown button');

				if (instance._conditionsIndexes.length > 1) {
					logicOperatorNode.removeAttribute('disabled');
				}
				else {
					logicOperatorNode.setAttribute('disabled', '');
				}
			},

			_updateOperatorList: function(conditionIndex, dataType, repeatable) {
				var instance = this;

				var operator = instance._getOperator(conditionIndex);

				var operatorTypes = Liferay.DDM.Settings.functionsMetadata;

				var options = [];

				if (dataType === 'string') {
					for (var i = 0; i < operatorTypes.text.length; i++) {
						if (repeatable) {
							if (operatorTypes.text[i].name === 'is-empty' || operatorTypes.text[i].name === 'not-is-empty') {
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
						else {
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
				}
				else if (dataType === 'double' || dataType === 'integer') {
					for (var j = 0; j < operatorTypes.number.length; j++) {
						if (repeatable) {
							if (operatorTypes.number[j].name === 'is-empty' || operatorTypes.number[j].name === 'not-is-empty') {
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
						else {
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
				}
				else if (dataType === 'user') {
					for (var k = 0; k < operatorTypes.user.length; k++) {
						options.push(
							A.merge(
								{
									value: operatorTypes.user[k].name
								},
								operatorTypes.user[k]
							)
						);
					}
				}

				operator.set('options', options);
			},

			_updateSecondOperandFieldVisibility: function(index) {
				var instance = this;

				var secondOperandType = instance._getSecondOperandType(index);

				var contentBox = instance.get('contentBox');

				var typeValueContainer = contentBox.one('.condition-type-value-' + index);
				var typeValueOptionsContainer = contentBox.one('.condition-type-value-options-' + index);

				if (secondOperandType.get('visible')) {
					var secondOperandTypeValue = instance._getSecondOperandTypeValue(index);

					var secondOperandFields = instance._getSecondOperand(index, 'fields');

					var secondOperandOptions = instance._getSecondOperand(index, 'options');

					typeValueContainer.hide();
					typeValueOptionsContainer.hide();
					instance._hideSecondOperandField(index);

					if (secondOperandTypeValue === 'field') {
						typeValueContainer.show();

						secondOperandFields.set('visible', true);

						secondOperandOptions.cleanSelect();
					}
					else if (instance._isConstant(secondOperandTypeValue)) {
						var options = instance._getFieldProperty(instance._getFirstOperandValue(index), 'options');
						var secondOperand = '';

						if ((options.length > 0) && instance._getFieldProperty(instance._getFirstOperandValue(index), 'type') !== 'text') {
							typeValueOptionsContainer.show();
							secondOperandOptions.set('options', options);
							secondOperandOptions.set('visible', true);

							secondOperandFields.cleanSelect();
						}
						else if (instance._getFieldProperty(instance._getFirstOperandValue(index), 'type') == 'date') {
							typeValueContainer.show();
							secondOperand = instance._getSecondOperand(index, 'input-date');

							secondOperand.set('visible', true);
							secondOperandFields.cleanSelect();
							secondOperandOptions.cleanSelect();
						}
						else {
							typeValueContainer.show();

							var type = instance._getFieldProperty(instance._getFirstOperandValue(index), 'dataType');

							if (type == 'integer') {
								secondOperand = instance._getSecondOperand(index, 'input-integer');
							}
							else if (type == 'double') {
								secondOperand = instance._getSecondOperand(index, 'input-decimal');
							}
							else {
								secondOperand = instance._getSecondOperand(index, 'input-text');
							}

							if (secondOperand) {
								if (instance._isNumeric(type)) {
									var container = secondOperand.get('container');
									var numeric = container.one('.liferay-ddm-form-field-numeric');

									container.removeClass('hide');
									numeric.removeClass('hide');
									numeric.setStyle('');
								}
								else {
									secondOperand.set('visible', true);
								}

								secondOperandFields.cleanSelect();
								secondOperandOptions.cleanSelect();
							}
						}
					}
				}
			},

			_updateSecondOperandType: function(operator, index) {
				var instance = this;

				var secondOperandType = instance._getSecondOperandType(index);

				var options = [];

				if (secondOperandType) {
					if (operator === 'belongs-to') {
						options = instance.get('roles');
					}
					else {
						options = [
							{
								label: instance.get('strings').value,
								value: instance._getFieldProperty(instance._getFirstOperandValue(index), 'dataType')
							},
							{
								label: instance.get('strings').otherField,
								value: 'field'
							}
						];
					}
					secondOperandType.set('options', options);
				}
			},

			_updateTypeFieldVisibility: function(index) {
				var instance = this;

				var secondOperandType = instance._getSecondOperandType(index);

				if (secondOperandType) {
					var contentBox = instance.get('contentBox');

					var typeContainer = contentBox.one('.condition-type-' + index);

					if (instance._getFirstOperandValue(index) && instance._getOperatorValue(index) && !instance._isUnaryCondition(index)) {
						secondOperandType.set('visible', true);

						typeContainer.show();
						instance._updateSecondOperandFieldVisibility(index);

					}
					else {
						instance._hideSecondOperandField(index);
						instance._hideSecondOperandTypeField(index);
					}
				}
			}
		};

		Liferay.namespace('DDM').FormBuilderRenderRuleCondition = FormBuilderRenderRuleCondition;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);