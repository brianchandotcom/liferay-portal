AUI.add(
	'liferay-ddl-form-builder-render-rule',
	function(A) {
		var CSS_CAN_REMOVE_ITEM = A.getClassName('can', 'remove', 'item');

		var SoyTemplateUtil = Liferay.DDM.SoyTemplateUtil;

		var FormBuilderRenderRule = A.Component.create(
			{
				ATTRS: {
					fields: {
						value: []
					},

					getDataProviderParametersSettingsURL: {
						value: ''
					},

					getDataProviderInstancesURL: {
						value: ''
					},


					getDataProviderParametersSettingsURL: {
						value: ''
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

					portletNamespace: {
						value: ''
					},

					strings: {
						value: {
							and: Liferay.Language.get('and'),
							autofill: Liferay.Language.get('autofill'),
							cancel: Liferay.Language.get('cancel'),
							description: Liferay.Language.get('define-condition-and-action-to-change-fields-and-elements-on-the-form'),
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

				AUGMENTS: [
					Liferay.DDL.FormBuilderRenderRuleCondition
				],

				NAME: 'liferay-ddl-form-builder-render-rule',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._actions = {};

						instance._conditions = {};

						instance._actionFactory = new Liferay.DDL.FormBuilderActionFactory(
							{
								fields: instance.get('fields'),
								getDataProviderInstancesURL: instance.get('getDataProviderInstancesURL'),
								getDataProviderParametersSettingsURL: instance.get('getDataProviderParametersSettingsURL'),
								pages: instance.get('pages'),
								portletNamespace: instance.get('portletNamespace')
							}
						);
					},

					bindUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						boundingBox.delegate('click', A.bind(instance._handleAddActionClick, instance), '.form-builder-rule-add-action');
						boundingBox.delegate('click', A.bind(instance._handleCancelClick, instance), '.form-builder-rule-settings-cancel');
						boundingBox.delegate('click', A.bind(instance._handleDeleteActionClick, instance), '.action-card-delete');

						boundingBox.delegate('click', A.bind(instance._handleSaveClick, instance), '.form-builder-rule-settings-save');

						instance.after(instance._toggleShowRemoveButton, instance, '_addAction');

						instance.after('fieldsChange', A.bind(instance._afterFieldsChange, instance));
						instance.after('pagesChange', A.bind(instance._afterPagesChange, instance));

						instance.on('*:valueChange', A.bind(instance._handleActionChange, instance));
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

					_afterFieldsChange: function(event) {
						var instance = this;

						instance._actionFactory.set('fields', event.newVal);
					},

					_afterPagesChange: function(event) {
						var instance = this;

						instance._actionFactory.set('pages', event.newVal);
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

						var container = contentBox.one('.form-builder-rule-action-container-' + index);

						container.one('.target-' + index).empty();

						container.one('.additional-info-' + index).empty();

						var target = instance._actionFactory.createAction(type, index, action, container);

						if (action && action.target) {
							target.set('value', action.target);
						}

						target.render(container);

						instance._actions[index + '-action'] = target;
					},

					_getActionOptions: function() {
						var instance = this;

						var strings = instance.get('strings');

						return [
							{
								label: strings.autofill,
								value: 'auto-fill'
							},
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

					_getFieldDataType: function(fieldName) {
						var instance = this;

						var field = instance.get('fields').find(
							function(field) {
								return field.value === fieldName;
							}
						);

						return field.dataType;
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

					_handleActionChange: function(event) {
						var instance = this;

						var field = event.target;

						var fieldName = field.get('fieldName');

						var index = fieldName.split('-')[0];

						if (fieldName.match('-target')) {
							instance._createTargetSelect(index, event.newVal[0]);
						}
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
							var action = instance._actions[index + '-action'];

							if (action) {
								instance._actions[index + '-action'].destroy();
							}
							else {
								instance._actions[index + '-target'].destroy();

								instance.get('boundingBox').one('.form-builder-rule-action-container-' + index).remove(true);
							}

							delete instance._actions[index + '-action'];
							delete instance._actions[index + '-target'];

							var actionIndex = instance._actionsIndexes.indexOf(Number(index));

							if (actionIndex > -1) {
								instance._actionsIndexes.splice(actionIndex, 1);
							}
						}

						instance._toggleShowRemoveButton();
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

					_toggleShowRemoveButton: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var conditionList = contentBox.one('.liferay-ddl-form-builder-rule-condition-list');

						var actionList = contentBox.one('.liferay-ddl-form-builder-rule-action-list');

						var conditionItems = conditionList.all('.timeline-item');

						var actionItems = actionList.all('.timeline-item');

						conditionList.toggleClass(CSS_CAN_REMOVE_ITEM, conditionItems.size() > 2);

						actionList.toggleClass(CSS_CAN_REMOVE_ITEM, actionItems.size() > 2);
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderRenderRule = FormBuilderRenderRule;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-render-rule-condition', 'liferay-ddl-form-builder-rule-template', 'liferay-ddm-form-renderer-field']
	}
);