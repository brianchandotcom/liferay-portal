AUI.add(
	'liferay-ddl-form-builder-rule-builder',
	function(A) {
		var ddl = window.ddl;

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
							delete: Liferay.Language.get('delete'),
							edit: Liferay.Language.get('edit'),
							emptyListText: Liferay.Language.get('there-are-no-rules-yet-click-on-plus-icon-bellow-to-add-the-first')
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

						var rulesBuilder = ddl.rule_builder(
							{
								plusIcon: Liferay.Util.getLexiconIconTpl('plus', 'icon-monospaced')
							}
						);

						instance.get('contentBox').setHTML(rulesBuilder);

						instance._renderCards(instance.get('rules'));
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
										label: field.get('label'),
										options: field.get('options'),
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

						var rulesList = instance.get('boundingBox').one('.form-builder-rule-builder-rules-list');

						rulesList.setHTML(ddl.rule_list({kebab: Liferay.Util.getLexiconIconTpl('ellipsis-v', 'icon-monospaced'), rules: rules, strings: instance.get('strings')}));
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