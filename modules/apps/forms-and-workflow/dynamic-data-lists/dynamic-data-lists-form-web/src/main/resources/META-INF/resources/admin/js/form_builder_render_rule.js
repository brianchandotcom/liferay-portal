AUI.add(
	'liferay-ddl-form-builder-render-rule',
	function(A) {
		var CSS_CAN_REMOVE_ITEM = A.getClassName('can', 'remove', 'item');

		var SoyTemplateUtil = Liferay.DDM.SoyTemplateUtil;

		var textOperators = [
			{
				label: Liferay.Language.get('is-equal-to'),
				value: 'equals-to'
			},
			{
				label: Liferay.Language.get('is-not-equal-to'),
				value: 'not-equals-to'
			},
			{
				label: Liferay.Language.get('contains'),
				value: 'contains'
			},
			{
				label: Liferay.Language.get('does-not-contain'),
				value: 'not-contains'
			}
		];

		var FormBuilderRenderRule = A.Component.create(
			{
				ATTRS: {
					fields: {
						value: []
					},
					logicOperator: {
						setter: function(val) {
							return val.toUpperCase();
						},
						validator: '_isValidLogicOperator',
						value: Liferay.Language.get('or')
					},
					pages: {
						value: 0
					},
					strings: {
						value: {
							and: Liferay.Language.get('and'),
							cancel: Liferay.Language.get('cancel'),
							description: Liferay.Language.get('define-here-a-condition-to-change-fields-and-elements-from-your-current-form'),
							enable: Liferay.Language.get('enable'),
							if: Liferay.Language.get('if'),
							jumpToPage: Liferay.Language.get('jump-to-page'),
							or: Liferay.Language.get('or'),
							otherField: Liferay.Language.get('other-field'),
							require: Liferay.Language.get('require'),
							save: Liferay.Language.get('save'),
							show: Liferay.Language.get('show'),
							the: Liferay.Language.get('the'),
							title: Liferay.Language.get('rule'),
							value: Liferay.Language.get('value')
						}
					}
				},

				AUGMENTS: [],

				NAME: 'liferay-ddl-form-builder-render-rule',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._actions = {};
						instance._conditions = {};
						instance._actionFactory = new Liferay.DDL.FormBuilderActionFactory(
							{
								fields: instance.get('fields'),
								pages: instance.get('pages')
							}
						);
					},

					bindUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						boundingBox.delegate('click', A.bind(instance._handleAddActionClick, instance), '.form-builder-rule-add-action');
						boundingBox.delegate('click', A.bind(instance._handleAddConditionClick, instance), '.form-builder-rule-add-condition');
						boundingBox.delegate('click', A.bind(instance._handleCancelClick, instance), '.form-builder-rule-settings-cancel');
						boundingBox.delegate('click', A.bind(instance._handleDeleteActionClick, instance), '.action-card-delete');
						boundingBox.delegate('click', A.bind(instance._handleDeleteConditionClick, instance), '.condition-card-delete');
						boundingBox.delegate('click', A.bind(instance._handleLogicOperatorChange, instance), '.logic-operator');
						boundingBox.delegate('click', A.bind(instance._handleSaveClick, instance), '.form-builder-rule-settings-save');

						instance.after(instance._toggleShowRemoveButton, instance, '_addAction');
						instance.after(instance._toggleShowRemoveButton, instance, '_addCondition');

						instance.on('*:valueChange', A.bind(instance._handleFieldValueChange, instance));
						instance.on('logicOperatorChange', A.bind(instance._onLogicOperatorChange, instance));
					},

					render: function(rule) {
						var instance = this;

						var contentBox = instance.get('contentBox');

						if (!rule) {
							rule = {
								actions: [],
								conditions: []
							};
						}

						instance.set('logicOperator', rule['logical-operator']);

						contentBox.setHTML(instance._getRuleContainerTemplate(rule));

						instance._conditionsIndexes = [];
						instance._actionsIndexes = [];

						instance._renderConditions(rule.conditions);
						instance._renderActions(rule.actions);

						return FormBuilderRenderRule.superclass.render.apply(instance, []);
					},

					_addAction: function(index, action) {
						var instance = this;

						var contentBox = instance.get('contentBox');

						instance._createActionSelect(index, action, contentBox.one('.action-' + index));

						instance._actionsIndexes.push(A.Lang.toInt(index, 10));
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

					_createActionSelect: function(index, action, container) {
						var instance = this;

						var value;

						if (action && action.action) {
							value = action.action;
						}

						var field = new Liferay.DDM.Field.Select(
							{
								bubbleTargets: [instance],
								fieldName: index + '-target',
								options: instance._getActionOptions(),
								showLabel: false,
								value: value,
								visible: true
							}
						);

						field.render(container);

						if (value) {
							instance._createTargetSelect(index, value, action);
						}

						instance._actions[index + '-target'] = field;
					},

					_createTargetSelect: function(index, type, action) {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var container = contentBox.one('.target-' + index);

						var target = instance._actionFactory.createAction(type, index, action, container);

						if (action && action.target) {
							target.set('value', action.target);
						}

						target.render(container);

						instance._actions[index + '-action'] = target;
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

					_getActionOptions: function() {
						var instance = this;

						var strings = instance.get('strings');

						return [
							{
								label: strings.show,
								value: 'show'
							},
							{
								label: strings.enable,
								value: 'enable'
							},
							{
								label: strings.jumpToPage,
								value: 'jump-to-page'
							},
							{
								label: strings.require,
								value: 'require'
							}
						];
					},

					_getActions: function() {
						var instance = this;

						var actions = [];

						var indexes = instance._actionsIndexes;

						for (var i = indexes.length - 1; i >= 0; i--) {
							var currentIndex = indexes[i];

							var action = instance._actions[currentIndex + '-action'];

							actions.push(action.getValue());
						}

						return actions;
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

					_getOptionsLabel: function(field, optionValue) {
						var instance = this;

						var option = field.get('options').find(
							function(currentOption) {
								return currentOption.value === optionValue;
							}
						);

						return option && option.label;
					},

					_getRuleContainerTemplate: function(rule) {
						var instance = this;

						var settingsTemplateRenderer = SoyTemplateUtil.getTemplateRenderer('ddl.rule.settings');

						return settingsTemplateRenderer(
							{
								actions: rule ? rule.actions : [],
								conditions: rule ? rule.conditions : [],
								deleteIcon: Liferay.Util.getLexiconIconTpl('trash', 'icon-monospaced'),
								logicalOperator: instance.get('logicOperator'),
								plusIcon: Liferay.Util.getLexiconIconTpl('plus', 'icon-monospaced'),
								showLabel: false,
								strings: instance.get('strings')
							}
						);
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

					_handleAddActionClick: function() {
						var instance = this;

						var actionListNode = instance.get('boundingBox').one('.liferay-ddl-form-builder-rule-action-list');

						var index = instance._actionsIndexes[instance._actionsIndexes.length - 1] + 1;

						var actionTemplateRenderer = SoyTemplateUtil.getTemplateRenderer('ddl.rule.action');

						actionListNode.append(
							actionTemplateRenderer(
								{
									deleteIcon: Liferay.Util.getLexiconIconTpl('trash', 'icon-monospaced'),
									index: index
								}
							)
						);

						instance._addAction(index);
					},

					_handleAddConditionClick: function() {
						var instance = this;

						var conditionListNode = instance.get('boundingBox').one('.liferay-ddl-form-builder-rule-condition-list');

						var index = instance._conditionsIndexes[instance._conditionsIndexes.length - 1] + 1;

						var conditionTemplateRenderer = SoyTemplateUtil.getTemplateRenderer('ddl.rule.condition');

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

					_handleCancelClick: function() {
						var instance = this;

						instance.fire(
							'cancelRule'
						);
					},

					_handleDeleteActionClick: function(event) {
						var instance = this;

						var index = event.currentTarget.getData('card-id');

						if (instance._actionsIndexes.length > 1) {
							instance._actions[index + '-action'].destroy();
							instance._actions[index + '-target'].destroy();

							delete instance._actions[index + '-action'];
							delete instance._actions[index + '-target'];

							instance.get('boundingBox').one('.form-builder-rule-action-container-' + index).remove(true);

							var actionIndex = instance._actionsIndexes.indexOf(Number(index));

							if (actionIndex > -1) {
								instance._actionsIndexes.splice(actionIndex, 1);
							}
						}

						instance._toggleShowRemoveButton();
					},

					_handleDeleteConditionClick: function(event) {
						var instance = this;

						var index = event.currentTarget.getData('card-id');

						if (instance._canDeleteCondition()) {
							instance._deleteCondition(index);
						}

						instance._toggleShowRemoveButton();
					},

					_handleFieldValueChange: function(event) {
						var instance = this;

						var field = event.target;

						var fieldName = field.get('fieldName');

						var index = fieldName.split('-')[0];

						if (fieldName.match('-condition-first-operand')) {
							instance._updateSecondOperandFieldVisibility(index);
						}
						else if (fieldName.match('-condition-operator')) {
							instance._updateTypeFieldVisibility(index);
							instance._updateSecondOperandFieldVisibility(index);
						}
						else if (fieldName.match('-condition-second-operand-type')) {
							instance._updateSecondOperandFieldVisibility(index);
						}
						else if (fieldName.match('-target')) {
							instance._createTargetSelect(index, event.newVal[0]);
						}
					},

					_handleLogicOperatorChange: function(event) {
						var instance = this;

						event.preventDefault();

						instance.set('logicOperator', event.currentTarget.get('text'));

						A.one('.dropdown-toggle-operator .dropdown-toggle-selected-value').setHTML(event.currentTarget.get('text'));
					},

					_handleSaveClick: function() {
						var instance = this;

						instance.fire(
							'saveRule',
							{
								actions: instance._getActions(),
								condition: instance._getConditions(),
								'logical-operator': instance.get('logicOperator')
							}
						);
					},

					_hideSecondOperandField: function(index) {
						var instance = this;

						instance._getSecondOperand(index, 'fields').set('visible', false);
						instance._getSecondOperand(index, 'options').set('visible', false);
						instance._getSecondOperand(index, 'input').set('visible', false);
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

					_renderActions: function(actions) {
						var instance = this;

						var actionsLength = actions.length;

						for (var i = 0; i < actionsLength; i++) {
							instance._addAction(i, actions[i]);
						}

						if (instance._actionsIndexes.length === 0) {
							instance._addAction(0);
						}
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

						var value;

						if (condition) {
							value = condition.operator;
						}

						var field = new Liferay.DDM.Field.Select(
							{
								bubbleTargets: [instance],
								fieldName: index + '-condition-operator',
								options: textOperators,
								showLabel: false,
								value: value,
								visible: true
							}
						);

						field.render(container);

						instance._conditions[index + '-condition-operator'] = field;
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

					_toggleShowRemoveButton: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var conditionList = contentBox.one('.liferay-ddl-form-builder-rule-condition-list');

						var actionList = contentBox.one('.liferay-ddl-form-builder-rule-action-list');

						var conditionItems = conditionList.all('.timeline-item');

						var actionItems = actionList.all('.timeline-item');

						conditionList.toggleClass(CSS_CAN_REMOVE_ITEM, conditionItems.size() > 2);

						actionList.toggleClass(CSS_CAN_REMOVE_ITEM, actionItems.size() > 2);
					},

					_updateSecondOperandFieldVisibility: function(index) {
						var instance = this;

						instance._hideSecondOperandField(index);

						var secondOperandType = instance._getSecondOperandType(index);

						var secondOperandTypeValue = secondOperandType.getValue();

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

						if (instance._getFirstOperandValue(index) && instance._getOperatorValue(index) && !instance._isUnaryCondition(index)) {
							secondOperandType.set('visible', true);
						}
						else {
							instance._getSecondOperand(index, 'fields').set('value', '');
							secondOperandType.set('visible', false);
						}
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderRenderRule = FormBuilderRenderRule;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-rule-template', 'liferay-ddm-form-renderer-field']
	}
);