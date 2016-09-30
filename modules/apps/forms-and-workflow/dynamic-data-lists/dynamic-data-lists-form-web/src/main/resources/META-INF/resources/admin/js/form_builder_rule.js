AUI.add(
	'liferay-ddl-form-builder-rule',
	function(A) {
		var CSS_CAN_REMOVE_ITEM = A.getClassName('can', 'remove', 'item');

		var SoyTemplateUtil = Liferay.DDL.SoyTemplateUtil;

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

		var FormBuilderRule = A.Component.create(
			{
				ATTRS: {
					fields: {
						value: []
					},
					logicOperator: {
						setter: function(val) {
							return val.toUpperCase();
						},
						validator: function(val) {
							var instance = this;

							if (A.Lang.isString(val)) {
								var valUpperCase = val.toUpperCase();

								return valUpperCase === instance.get('strings').or || val.toUpperCase() === instance.get('strings').and;
							}

							return false;
						},
						value: Liferay.Language.get('or')
					},
					strings: {
						value: {
							and: Liferay.Language.get('and'),
							cancel: Liferay.Language.get('cancel'),
							description: Liferay.Language.get('define-here-a-condition-to-change-fields-and-elements-from-your-current-form'),
							enable: Liferay.Language.get('enable'),
							if: Liferay.Language.get('if'),
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

				NAME: 'liferay-ddl-form-builder-rule',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._conditions = {};
						instance._actions = {};
					},

					bindUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						boundingBox.delegate('click', A.bind(instance._handleSaveClick, instance), '.form-builder-rule-settings-save');
						boundingBox.delegate('click', A.bind(instance._handleCancelClick, instance), '.form-builder-rule-settings-cancel');

						boundingBox.delegate('click', A.bind(instance._handleDeleteConditionClick, instance), '.condition-card-delete');
						boundingBox.delegate('click', A.bind(instance._handleDeleteActionClick, instance), '.action-card-delete');

						boundingBox.delegate('click', A.bind(instance._handleAddConditionClick, instance), '.form-builder-rule-add-condition');
						boundingBox.delegate('click', A.bind(instance._handleAddActionClick, instance), '.form-builder-rule-add-action');

						boundingBox.delegate('click', A.bind(instance._handleLogicOperatorChange, instance), '.logic-operator');

						instance.after(instance._toggleShowRemoveButton, instance, '_addAction');
						instance.after(instance._toggleShowRemoveButton, instance, '_addCondition');

						instance.on('*:valueChanged', A.bind(instance._handleFieldValueChanged, instance));
						instance.on('logicOperatorChange', A.bind(instance._onLogicOperatorChanged, instance));
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

						return FormBuilderRule.superclass.render.apply(instance, []);
					},

					_addAction: function(index, action) {
						var instance = this;

						var contentBox = instance.get('contentBox');

						instance._createActionSelect(index, action, contentBox.one('.action-' + index));
						instance._createTargetSelect(index, action, contentBox.one('.target-' + index));

						instance._actionsIndexes.push(Number(index));
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

					_createActionSelect: function(index, action, container) {
						var instance = this;

						var value;

						if (action && action.action) {
							value = action.action;
						}

						var field = new Liferay.DDM.Field.Select(
							{
								fieldName: index + '-target',
								options: instance._getActionOptions(),
								showLabel: false,
								value: value,
								visible: true
							}
						);

						field.render(container);

						instance._actions[index + '-target'] = field;
					},

					_createTargetSelect: function(index, action, container) {
						var instance = this;

						var value;

						if (action && action.target) {
							value = action.target;
						}

						var field = new Liferay.DDM.Field.Select(
							{
								fieldName: index + '-action',
								label: Liferay.Language.get('the'),
								options: instance.get('fields'),
								showLabel: false,
								value: value,
								visible: true
							}
						);

						field.render(container);

						instance._actions[index + '-action'] = field;
					},

					_getActionOptions: function() {
						var instance = this;

						var options;

						var strings = instance.get('strings');

						options = [
							{
								label: strings.show,
								value: 'show'
							},
							{
								label: strings.enable,
								value: 'enable'
							},
							{
								label: strings.require,
								value: 'require'
							}
						];

						return options;
					},

					_getActions: function() {
						var instance = this;

						var actions = [];

						for (var i = instance._actionsIndexes.length - 1; i >= 0; i--) {
							var index = instance._actionsIndexes[i];

							var action = {
								action: instance._actions[index + '-target'].getValue(),
								label: instance._getFieldLabel(instance._actions[index + '-action'].getValue()),
								target: instance._actions[index + '-action'].getValue()
							};

							actions.push(action);
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

						var fields = instance.get('fields');

						var fieldLabel;

						for (var index in fields) {
							if (fields[index].value === fieldValue) {
								fieldLabel = fields[index].label;
							}
						}

						return fieldLabel;
					},

					_getFieldOptions: function(fieldName) {
						var instance = this;

						var fields = instance.get('fields');

						var options = [];

						for (var i = 0; i < fields.length; i++) {
							if (fields[i].value === fieldName) {
								options = fields[i].options;

								break;
							}
						}

						return options;
					},

					_getFieldType: function(fieldValue) {
						var instance = this;

						var fields = instance.get('fields');

						var fieldType;

						for (var index in fields) {
							if (fields[index].value === fieldValue) {
								fieldType = fields[index].type;
							}
						}

						return fieldType;
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

						var options = field.get('options');

						var optionLabel;

						for (var index in options) {
							if (options[index].value === optionValue) {
								optionLabel = options[index].label;
							}
						}

						return optionLabel;
					},

					_getRuleContainerTemplate: function(rule) {
						var instance = this;

						var ruleSettingsContainer;

						var settingsTemplateRenderer = SoyTemplateUtil.getTemplateRenderer('ddl.rule.settings');

						ruleSettingsContainer = settingsTemplateRenderer(
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

						return ruleSettingsContainer;
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

						if (instance._conditionsIndexes.length > 1) {
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

							instance.get('boundingBox').one('.form-builder-rule-condition-container-' + index).remove(true);

							var conditionIndex = instance._conditionsIndexes.indexOf(Number(index));

							if (conditionIndex > -1) {
								instance._conditionsIndexes.splice(conditionIndex, 1);
							}
						}

						instance._toggleShowRemoveButton();
					},

					_handleFieldValueChanged: function(event) {
						var instance = this;

						var field = event.field;

						var condition = field.get('fieldName');

						var index = field.get('fieldName').split('-')[0];

						if (condition.match('-condition-first-operand')) {
							instance._updateSecondOperandFieldVisibility(index);
						}
						else if (condition.match('-condition-operator')) {
							instance._updateTypeFieldVisibility(index);
							instance._updateSecondOperandFieldVisibility(index);
						}
						else if (condition.match('-condition-second-operand-type')) {
							instance._updateSecondOperandFieldVisibility(index);
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

						return instance._getFieldOptions(field.getValue()).length > 0 && instance._getFieldType(field.getValue()) !== 'text';
					},

					_isUnaryCondition: function(index) {
						var instance = this;

						var value = instance._getOperatorValue(index);

						return value === 'is-email-address' || value === 'is-url';
					},

					_onLogicOperatorChanged: function(event) {
						var instance = this;

						if (event.newVal === 'or') {
							A.all('.operator .panel-body').each(
								function(operatorNode) {
									operatorNode.set('text', instance.get('strings').or);
								}
							);
						}
						else {
							A.all('.operator .panel-body').each(
								function(operatorNode) {
									operatorNode.set('text', instance.get('strings').and);
								}
							);
						}
					},

					_renderActions: function(actions) {
						var instance = this;

						var actionsQuant = actions.length;

						for (var i = 0; i < actionsQuant; i++) {
							instance._addAction(i, actions[i]);
						}

						if (instance._actionsIndexes.length === 0) {
							instance._addAction(0);
						}
					},

					_renderConditions: function(conditions) {
						var instance = this;

						var conditionsQuant = conditions.length;

						for (var i = 0; i < conditionsQuant; i++) {
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

						var visible = instance._getSecondOperandTypeValue(index) === 'constant' &&
							!instance._isFieldList(instance._getFirstOperand(index));

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

						if (instance._getSecondOperandType(index).get('visible') && instance._getSecondOperandTypeValue(index)) {
							if (instance._getSecondOperandTypeValue(index) === 'field') {
								instance._getSecondOperand(index, 'fields').set('visible', true);

								instance._getSecondOperand(index, 'options').cleanSelect();
							}
							else {
								var options = instance._getFieldOptions(instance._getFirstOperandValue(index));

								if (options.length > 0 && instance._getFieldType(instance._getFirstOperandValue(index)) !== 'text') {
									instance._getSecondOperand(index, 'options').set('options', options);
									instance._getSecondOperand(index, 'options').set('visible', true);

									instance._getSecondOperand(index, 'fields').cleanSelect();
								}
								else {
									instance._getSecondOperand(index, 'input').set('visible', true);

									instance._getSecondOperand(index, 'fields').cleanSelect();
									instance._getSecondOperand(index, 'options').cleanSelect();
								}
							}
						}
					},

					_updateTypeFieldVisibility: function(index) {
						var instance = this;

						if (instance._getFirstOperandValue(index) && instance._getOperatorValue(index) && !instance._isUnaryCondition(index)) {
							instance._getSecondOperandType(index).set('visible', true);
						}
						else {
							instance._getSecondOperand(index, 'fields').set('value', '');
							instance._getSecondOperandType(index).set('visible', false);
						}
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderRule = FormBuilderRule;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-rule-template', 'liferay-ddm-form-renderer-field']
	}
);