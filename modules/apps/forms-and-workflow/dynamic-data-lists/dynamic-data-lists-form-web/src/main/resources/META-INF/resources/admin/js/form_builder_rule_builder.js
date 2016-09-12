AUI.add(
	'liferay-ddl-form-builder-rule-builder',
	function(A) {
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
							emptyListText: Liferay.Language.get('there-are-no-rules-yet-click-on-plus-icon-bellow-to-add-the-first'),
							showHide: Liferay.Language.get('show-hide')
						}
					}
				},

				AUGMENTS: [],

				NAME: 'liferay-ddl-form-builder-rule-builder',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._ruleClasses = {};
					},

					renderUI: function() {
						var instance = this;

						instance._renderPopover();
					},

					bindUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						instance.on('rulesChange', A.bind(instance._onRulesChange, instance));
						instance.on('*:saveRule', A.bind(instance._handleSaveRule, instance));
						instance.on('*:cancelRule', A.bind(instance._handleCancelRule, instance));

						boundingBox.delegate('click', A.bind(instance._handleEditCardClick, instance), '.rule-card-edit');
						boundingBox.delegate('click', A.bind(instance._handleDeleteCardClick, instance), '.rule-card-delete');
					},

					syncUI: function() {
						var instance = this;

						var rulesBuilder = ddl.rule_builder(
							{
								plusIcon: Liferay.Util.getLexiconIconTpl('plus', 'icon-monospaced')
							}
						);

						instance.get('boundingBox').setHTML(rulesBuilder);

						instance._renderCards(instance.get('rules'));
					},

					getFields: function() {
						var instance = this;

						if (!instance._fields) {
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

							instance._fields = fields;
						}

						return instance._fields;
					},

					getRuleType: function(type) {
						var instance = this;

						var ruleType = type.toLowerCase();

						var ruleClassInstance = instance._ruleClasses[ruleType];

						if (!ruleClassInstance) {
							ruleClassInstance = new Liferay.DDL.Rules[ruleType](
								{
									boundingBox: instance.get('boundingBox'),
									bubbleTargets: [instance],
									fields: instance.getFields()
								}
							);

							instance._ruleClasses[ruleType] = ruleClassInstance;
						}

						return ruleClassInstance;
					},

					_handleCancelRule: function(event) {
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

						instance._renderRuleSettings(instance.get('rules')[ruleId]);
					},

					_handlePopoverClick: function(event) {
						var instance = this;

						instance.getRuleType(event.currentTarget.getData('rule-type')).render();
					},

					_handleSaveRule: function(event) {
						var instance = this;

						var rules = instance.get('rules');

						var rule = {
							actions: event.actions,
							conditions: event.condition,
							type: event.type
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
					},

					_renderPopover: function() {
						var instance = this;

						var popover = new A.Popover(
							{
								align: {
									node: '.form-builder-rule-builder-add-rule-button-icon'
								},
								animated: true,
								bodyContent: ddl.rule_types(
									{
										strings: instance.get('strings')
									}
								),
								cssClass: 'form-builder-rulles-builder-popover',
								duration: 0.25,
								hideOn: [{
									eventName: 'click',
									node: A.one(document)
								}],
								position: 'bottom',
								visible: false,
								zIndex: Liferay.zIndex.TOOLTIP
							}
						).render();

						instance.get('boundingBox').delegate('click', A.bind(popover.show, popover), '.form-builder-rule-builder-add-rule-button-icon');
						// popover.get('boundingBox').delegate('click', A.bind(instance._handlePopoverClick, instance), 'a');
					},

					_renderRuleSettings: function(rule) {
						var instance = this;

						instance.getRuleType(rule.type).render(rule);
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