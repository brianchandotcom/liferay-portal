AUI.add(
	'liferay-ddl-form-builder-render-rule-condition',
	function(A) {
		var currentUser = {
			dataType: 'user',
			label: Liferay.Language.get('user'),
			value: 'user'
		};

		var CSS_CAN_REMOVE_ITEM = A.getClassName('can', 'remove', 'item');

		var Settings = Liferay.DDL.Settings;

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

				instance._validator = new Liferay.DDL.FormBuilderRuleValidator();
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

					var type = instance._getFirstOperandValue(index) === 'user' ? 'user' : 'field';

					var condition = {
						'operands': [
							{
								label: instance._getFieldLabel(instance._getFirstOperandValue(index)),
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
						else if (secondOperandTypeValue === 'constant') {
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

			_getDataType: function(value, options) {
				var instance = this;

				var option;

				var dataType;

				for (var i = 0; i < options.length; i++) {
					option = options[i];

					if (value.indexOf(option.value) > -1) {
						dataType = option.dataType;

						break;
					}
				}

				return dataType;
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

				var firstOperand = instance._getFirstOperand(index);

				var value = firstOperand.getValue();

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

			_handleAddConditionClick: function() {
				var instance = this;

				var conditionListNode = instance.get('boundingBox').one('.liferay-ddl-form-builder-rule-condition-list');

				var index = instance._conditionsIndexes[instance._conditionsIndexes.length - 1] + 1;

				var conditionTemplateRenderer = Liferay.DDM.SoyTemplateUtil.getTemplateRenderer('ddl.rule.condition');

				conditionListNode.append(
					conditionTemplateRenderer(
						{
							deleteIcon: Liferay.Util.getLexiconIconTpl('trash', 'icon-monospaced'),
							if: instance.get('if'),
							index: index,
							logicOperator: instance.get('logicOperator')
						}
					)
				);

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
						var type = instance._getDataType(field.getValue(), field.get('options'));

						instance._updateOperatorList(type, index);
					}
					else if (fieldName.match('-condition-operator')) {
						var operator = event.newVal[0];

						instance._updateTypeFieldVisibility(index);
						instance._updateSecondOperandType(operator, index);
					}
					else if (fieldName.match('-condition-second-operand-type')) {
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
				var secondOperandsInput = instance._getSecondOperand(index, 'input');

				instance._setVisibleToOperandField(secondOperandFields);
				instance._setVisibleToOperandField(secondOperandOptions);
				instance._setVisibleToOperandField(secondOperandsInput);
			},

			_isBinaryCondition: function(index) {
				var instance = this;

				var value = instance._getOperatorValue(index);

				return value === 'equals-to' || value === 'not-equals-to' || value === 'contains' || value === 'not-contains' || value === 'belongs-to' || value === 'greater-than' || value === 'greater-than-equals' || value === 'less-than' || value === 'less-than-equals';
			},

			_isFieldList: function(field) {
				var instance = this;

				var value = field.getValue()[0] || '';

				return instance._getFieldOptions(value).length > 0 && instance._getFieldType(value) !== 'text';
			},

			_isUnaryCondition: function(index) {
				var instance = this;

				var value = instance._getOperatorValue(index);

				return value === 'is-email-address' || value === 'is-url' || value === 'is-empty' || value === 'not-is-empty';
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

				var value = [];

				if (condition) {
					value = [condition.operands[0].value];
				}

				var fields = instance.get('fields').slice();

				fields.unshift(currentUser);

				var field = instance.createSelectField(
					{
						fieldName: index + '-condition-first-operand',
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

				if (condition) {
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
					instance._updateOperatorList(instance._getFieldDataType(condition.operands[0].value), index);
				}
			},

			_renderSecondOperandInput: function(index, condition, container) {
				var instance = this;

				var value = '';

				var firstOperand = instance._getFirstOperand(index);

				var secondOperandValue = instance._getSecondOperandTypeValue(index);

				var visible = secondOperandValue === 'constant' && !instance._isFieldList(firstOperand);

				if (condition && instance._isBinaryCondition(index) && visible) {
					value = condition.operands[1].value;
				}

				var field = instance.createTextField(
					{
						fieldName: index + '-condition-second-operand-input',
						options: [],
						placeholder: '',
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

				var value = [];

				var visible = instance._getSecondOperandTypeValue(index) === 'field';

				if (condition && instance._isBinaryCondition(index) && visible) {
					value = [condition.operands[1].value];
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

				var value = [];
				var options = [];

				var visible = instance._getSecondOperandTypeValue(index) === 'constant' &&
					instance._isFieldList(instance._getFirstOperand(index));

				if (condition && instance._isBinaryCondition(index) && visible) {
					options = instance._getFieldOptions(instance._getFirstOperandValue(index));
					value = [condition.operands[1].value];
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

				if (condition && instance._isBinaryCondition(index)) {
					value = [condition.operands[1].type];
				}

				var field = instance.createSelectField(
					{
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

				if (condition && instance._isBinaryCondition(index)) {
					instance._updateSecondOperandType(condition.operator, index);

					if (condition.operands[0].type === 'user') {
						field.set('value', [condition.operands[1].value]);
					}
				}
			},

			_setVisibleToOperandField: function(field) {
				if (field) {
					field.set('visible', false);
				}
			},

			_toggleDeleteConditionButton: function() {
				var instance = this;

				var contentBox = instance.get('contentBox');

				var conditionList = contentBox.one('.liferay-ddl-form-builder-rule-condition-list');

				var conditionItems = conditionList.all('.timeline-item');

				conditionList.toggleClass(CSS_CAN_REMOVE_ITEM, conditionItems.size() > 2);
			},

			_updateLogicOperatorEnableState: function() {
				var instance = this;

				var logicOperatorNode = instance.get('boundingBox').one('.liferay-ddl-form-builder-rule-condition-list').one('.dropdown button');

				if (instance._conditionsIndexes.length > 1) {
					logicOperatorNode.removeAttribute('disabled');
				}
				else {
					logicOperatorNode.setAttribute('disabled', '');
				}
			},

			_updateOperatorList: function(dataType, conditionIndex) {
				var instance = this;

				var operator = instance._getOperator(conditionIndex);

				var operatorTypes = Settings.functionsMetadata;

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
				else if (dataType === 'integer') {
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

				instance._hideSecondOperandField(index);

				var secondOperandType = instance._getSecondOperandType(index);

				if (secondOperandType.get('visible')) {
					var secondOperandTypeValue = instance._getSecondOperandTypeValue(index);

					var secondOperandFields = instance._getSecondOperand(index, 'fields');

					var secondOperandOptions = instance._getSecondOperand(index, 'options');

					if (secondOperandTypeValue === 'field') {
						secondOperandFields.set('visible', true);
						secondOperandOptions.cleanSelect();
					}
					else if (secondOperandTypeValue === 'constant') {
						var options = instance._getFieldOptions(instance._getFirstOperandValue(index));

						if (options.length > 0 && instance._getFieldType(instance._getFirstOperandValue(index)) !== 'text') {
							secondOperandOptions.set('options', options);
							secondOperandOptions.set('visible', true);

							secondOperandFields.cleanSelect();
						}
						else {
							var secondOperand = instance._getSecondOperand(index, 'input');

							if (secondOperand) {
								secondOperand.set('visible', true);
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
								value: 'constant'
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