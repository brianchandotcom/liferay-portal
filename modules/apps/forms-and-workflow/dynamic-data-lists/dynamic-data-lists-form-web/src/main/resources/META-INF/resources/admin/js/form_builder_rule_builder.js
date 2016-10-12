AUI.add(
	'liferay-ddl-form-builder-rule-builder',
	function(A) {
		var SoyTemplateUtil = Liferay.DDL.SoyTemplateUtil;

		var FormBuilderRuleBuilder = A.Component.create(
			{
				ATTRS: {
					formBuilder: {
						value: null
					},

					rules: {
						value: []
					},

					strings: {
						value: {
							contains: Liferay.Language.get('contains'),
							delete: Liferay.Language.get('delete'),
							edit: Liferay.Language.get('edit'),
							emptyListText: Liferay.Language.get('there-are-no-rules-yet-click-on-plus-icon-bellow-to-add-the-first'),
							'equals-to': Liferay.Language.get('is-equal-to'),
							'not-contains': Liferay.Language.get('does-not-contain'),
							'not-equals-to': Liferay.Language.get('is-not-equal-to'),
							ruleBuilder: Liferay.Language.get('rule-builder')
						}
					}
				},

				NAME: 'liferay-ddl-form-builder-rule-builder',

				prototype: {
					bindUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						instance.on('rulesChange', A.bind(instance._onRulesChange, instance));
						instance.on('*:saveRule', A.bind(instance._handleSaveRule, instance));
						instance.on('*:cancelRule', A.bind(instance._handleCancelRule, instance));

						instance._eventHandlers = [
							boundingBox.delegate('click', A.bind(instance._handleAddRuleClick, instance), '.form-builder-rule-builder-add-rule-button-icon'),
							boundingBox.delegate('click', A.bind(instance._handleEditCardClick, instance), '.rule-card-edit'),
							boundingBox.delegate('click', A.bind(instance._handleDeleteCardClick, instance), '.rule-card-delete')
						];
					},

					syncUI: function() {
						var instance = this;

						var ruleBuilderTemplateRenderer = SoyTemplateUtil.getTemplateRenderer('ddl.rule_builder');

						var rulesBuilder = ruleBuilderTemplateRenderer(
							{
								plusIcon: Liferay.Util.getLexiconIconTpl('plus', 'icon-monospaced'),
								strings: instance.get('strings')
							}
						);

						instance.get('contentBox').setHTML(rulesBuilder);

						var rules = instance.get('rules');

						rules.forEach(
							function(rule) {
								rule.conditions.forEach(
									function(condition) {
										condition.operands.forEach(
											function(operand) {
												operand.label = instance._getFieldLabel(operand.value);
											}
										);
									}
								);

								rule.actions.forEach(
									function(action) {
										action.label = instance._getFieldLabel(action.target);
									}
								);
							}
						);

						instance._renderCards(rules);
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					getFields: function() {
						var instance = this;

						var fields = [];

						instance.get('formBuilder').eachFields(
							function(field) {
								fields.push(
									{
										label: field.get('label') || field.get('fieldName'),
										options: field.get('options'),
										type: field.get('type'),
										value: field.get('fieldName')
									}
								);
							}
						);

						return fields;
					},

					hide: function() {
						var instance = this;

						FormBuilderRuleBuilder.superclass.hide.apply(instance, arguments);

						instance.syncUI();
					},

					renderRule: function(rule) {
						var instance = this;

						if (!instance._ruleClasses) {
							instance._ruleClasses = new Liferay.DDL.FormBuilderRule(
								{
									boundingBox: instance.get('boundingBox'),
									bubbleTargets: [instance],
									contentBox: instance.get('contentBox'),
									fields: instance.getFields()
								}
							);
						}

						instance._ruleClasses.set('fields', instance.getFields());

						instance._ruleClasses.render(rule);
					},

					_getFieldLabel: function(fieldValue) {
						var instance = this;

						var fields = instance.getFields();

						var fieldLabel;

						for (var index in fields) {
							if (fields[index].value === fieldValue) {
								fieldLabel = fields[index].label;
							}
						}

						return fieldLabel;
					},

					_handleAddRuleClick: function() {
						var instance = this;

						instance.renderRule();
					},

					_handleCancelRule: function() {
						var instance = this;

						instance.syncUI();
					},

					_handleDeleteCardClick: function(event) {
						var instance = this;

						var rules = instance.get('rules');

						rules.splice(event.currentTarget.getData('card-id'), 1);

						instance.set('rules', rules);
					},

					_handleEditCardClick: function(event) {
						var instance = this;

						var target = event.currentTarget;

						var ruleId = target.getData('card-id');

						instance._currentRuleId = ruleId;

						instance.renderRule(instance.get('rules')[ruleId]);
					},

					_handleSaveRule: function(event) {
						var instance = this;

						var rules = instance.get('rules');

						var rule = {
							actions: event.actions,
							conditions: event.condition,
							'logical-operator': event['logical-operator']
						};

						if (instance._currentRuleId) {
							rules[instance._currentRuleId] = rule;
						}
						else {
							rules.push(rule);
						}

						instance.syncUI();

						instance._currentRuleId = null;
					},

					_onRulesChange: function(val) {
						var instance = this;

						instance._renderCards(val.newVal);
					},

					_renderCards: function(rules) {
						var instance = this;

						var rulesList = instance.get('boundingBox').one('.liferay-ddl-form-rule-rules-list-container');

						var ruleListTemplateRenderer = SoyTemplateUtil.getTemplateRenderer('ddl.rule_list');

						rulesList.setHTML(
							ruleListTemplateRenderer(
								{
									kebab: Liferay.Util.getLexiconIconTpl('ellipsis-v', 'icon-monospaced'),
									rules: rules,
									strings: instance.get('strings')
								}
							)
						);
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderRuleBuilder = FormBuilderRuleBuilder;
	},
	'',
	{
		requires: ['aui-popover', 'event-outside', 'liferay-ddl-form-builder-rule']
	}
);