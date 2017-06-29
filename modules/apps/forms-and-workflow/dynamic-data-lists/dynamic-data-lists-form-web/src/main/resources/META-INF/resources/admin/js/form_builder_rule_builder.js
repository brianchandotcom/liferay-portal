AUI.add(
	'liferay-ddl-form-builder-rule-builder',
	function(A) {
		var Settings = Liferay.DDL.Settings;

		var SoyTemplateUtil = Liferay.DDM.SoyTemplateUtil;

		var MAP_ACTION_DESCRIPTIONS = {
			'auto-fill': 'auto-fill',
			calculate: 'calculate-field',
			enable: 'enable-field',
			'jump-to-page': 'jump-to-page',
			require: 'require-field',
			show: 'show-field'
		};

		var FormBuilderRuleBuilder = A.Component.create(
			{
				ATTRS: {
					formBuilder: {
						value: null
					},

					roles: {
						value: []
					},

					rules: {
						setter: '_setRules',
						value: []
					},

					strings: {
						value: {
							and: Liferay.Language.get('and'),
							'auto-fill': Liferay.Language.get('autofill-x-from-data-provider-x'),
							'belongs-to': Liferay.Language.get('belongs-to'),
							'calculate-field': Liferay.Language.get('calculate-field-x-as-x'),
							constant: Liferay.Language.get('constant'),
							contains: Liferay.Language.get('contains'),
							delete: Liferay.Language.get('delete'),
							edit: Liferay.Language.get('edit'),
							emptyListText: Liferay.Language.get('there-are-no-rules-yet-click-on-plus-icon-below-to-add-the-first'),
							'enable-field': Liferay.Language.get('enable-x'),
							'equals-to': Liferay.Language.get('is-equal-to'),
							field: Liferay.Language.get('field'),
							'greater-than': Liferay.Language.get('is-greater-than'),
							'greater-than-equals': Liferay.Language.get('is-greater-than-or-equal-to'),
							if: Liferay.Language.get('if'),
							'is-empty': Liferay.Language.get('is-empty'),
							'jump-to-page': Liferay.Language.get('jump-to-page-x'),
							'less-than': Liferay.Language.get('is-less-than'),
							'less-than-equals': Liferay.Language.get('is-less-than-or-equal-to'),
							'not-contains': Liferay.Language.get('does-not-contain'),
							'not-equals-to': Liferay.Language.get('is-not-equal-to'),
							'not-is-empty': Liferay.Language.get('is-not-empty'),
							or: Liferay.Language.get('or'),
							'require-field': Liferay.Language.get('require-x'),
							ruleBuilder: Liferay.Language.get('rule-builder'),
							'show-field': Liferay.Language.get('show-x')
						}
					}
				},

				NAME: 'liferay-ddl-form-builder-rule-builder',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._getUserRoles();
					},

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
										dataType: field.get('dataType'),
										label: field.get('label') || field.get('fieldName'),
										options: field.get('options'),
										pageIndex: instance.getPageIndex(field),
										type: field.get('type'),
										value: field.get('fieldName')
									}
								);
							}
						);

						return fields;
					},

					getPageIndex: function(field) {
						var instance = this;

						var formBuilder = instance.get('formBuilder');

						var layouts = formBuilder.get('layouts');

						for (var h = 0; h < layouts.length; h++) {
							var rows = layouts[h].get('rows');

							for (var i = 0; i < rows.length; i++) {
								var cols = rows[i].get('cols');

								for (var j = 0; j < cols.length; j++) {
									var fieldList = cols[j].get('value');

									if (fieldList) {
										var fields = fieldList.get('fields');

										for (var k = 0; k < fields.length; k++) {
											if (fields[k].get('label') === field.get('label')) {
												return h;
											}
										}
									}
								}
							}
						}
					},

					getPages: function() {
						var instance = this;

						var pages;

						var formBuilder = instance.get('formBuilder');

						var pagesTitles = formBuilder.getPagesTitle();

						var pagesQuantity = formBuilder.get('layouts').length;

						pages = new Array(pagesQuantity);

						for (var i = 0; i < pagesQuantity; i++) {
							pages[i] = {
								label: pagesTitles[i] ? (i + 1).toString() + ' ' + pagesTitles[i] : (i + 1).toString(),
								value: i.toString()
							};
						}

						return pages;
					},

					renderRule: function(rule) {
						var instance = this;

						if (!instance._ruleClasses) {
							instance._ruleClasses = new Liferay.DDL.FormBuilderRenderRule(
								{
									boundingBox: instance.get('boundingBox'),
									bubbleTargets: [instance],
									contentBox: instance.get('contentBox'),
									fields: instance.getFields(),
									getDataProviders: instance._dataProviders,
									pages: instance.getPages(),
									roles: instance.get('roles')
								}
							);
						}

						instance._ruleClasses.set('fields', instance.getFields());
						instance._ruleClasses.set('pages', instance.getPages());

						instance._ruleClasses.render(rule);
					},

					show: function() {
						var instance = this;

						FormBuilderRuleBuilder.superclass.show.apply(instance, arguments);

						if (!instance._dataProviders) {
							instance._fillDataProviders();
						}
						else {
							instance.syncUI();
						}
					},

					_fillDataProviders: function() {
						var instance = this;

						A.io.request(
							Settings.getDataProviderInstancesURL,
							{
								method: 'GET',
								on: {
									success: function(event, id, xhr) {
										var result = JSON.parse(xhr.responseText);

										instance._dataProviders = result;

										instance.syncUI();
									}
								}
							}
						);
					},

					_getActionDescription: function(type, action) {
						var instance = this;

						var actionDescription = '';

						var strings = instance.get('strings');

						var badgeTemplate = SoyTemplateUtil.getTemplateRenderer('ddl.badge');

						var actionKey = MAP_ACTION_DESCRIPTIONS[type];

						var pages = instance.getPages();

						if (actionKey) {
							var data;

							if (type === 'jump-to-page') {
								data = [
									badgeTemplate(
										{
											content: pages[action.target].label
										}
									)
								];
							}
							else if (type === 'auto-fill') {
								data = [];

								var fieldListDescription = [];

								for (var output in action.outputs) {
									fieldListDescription.push(
										badgeTemplate(
											{
												content: action.outputs[output]
											}
										)
									);
								}

								data.push(fieldListDescription.join(', '));

								data.push(
									badgeTemplate(
										{
											content: instance._getDataProviderLabel(action.ddmDataProviderInstanceUUID)
										}
									)
								);
							}
							else if (type === 'calculate') {
								data = [
									badgeTemplate(
										{
											content: action.expression.replace(/\[|\]/g, '')
										}
									),
									badgeTemplate(
										{
											content: instance._getFieldLabel(action.target)
										}
									)
								];
							}
							else {
								data = [
									badgeTemplate(
										{
											content: action.label
										}
									)
								];
							}

							actionDescription = A.Lang.sub(strings[actionKey], data);
						}

						return actionDescription;
					},

					_getActionsDescription: function(actions) {
						var instance = this;

						var actionsDescription = [];

						var actionDescription = '';

						for (var i = 0; i < actions.length; i++) {
							actionDescription = instance._getActionDescription(actions[i].action, actions[i]);

							actionsDescription.push(actionDescription);
						}

						return actionsDescription;
					},

					_getDataProviderLabel: function(dataProviderUUID) {
						var instance = this;

						if (instance._dataProviders) {
							for (var i = 0; i < instance._dataProviders.length; i++) {
								if (dataProviderUUID === instance._dataProviders[i].uuid) {
									return instance._dataProviders[i].name;
								}
							}
						}
					},

					_getFieldLabel: function(fieldValue) {
						var instance = this;

						if (fieldValue === 'user') {
							return Liferay.Language.get('user');
						}

						var fields = instance.getFields();

						var fieldLabel;

						for (var index in fields) {
							if (fields[index].value === fieldValue) {
								fieldLabel = fields[index].label;
							}
						}

						return fieldLabel;
					},

					_getRulesDescription: function(rules) {
						var instance = this;

						var rulesDescription = [];

						for (var i = 0; i < rules.length; i++) {
							rulesDescription.push(
								{
									actions: instance._getActionsDescription(rules[i].actions),
									conditions: rules[i].conditions,
									logicOperator: rules[i]['logical-operator'].toLowerCase()
								}
							);
						}

						return rulesDescription;
					},

					_getUserRoles: function() {
						var instance = this;

						var roles = instance.get('roles');

						if (!roles.length) {
							A.io.request(
								Settings.getRolesURL,
								{
									method: 'GET',
									on: {
										success: function(event, id, xhr) {
											var result = JSON.parse(xhr.responseText);

											instance._parseDataUserRoles(result);
										}
									}
								}
							);
						}
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
							conditions: event.conditions,
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

					_parseDataUserRoles: function(result) {
						var instance = this;

						var roles = [];

						for (var i = 0; i < result.length; i++) {
							roles.push(
								{
									label: result[i].name,
									value: result[i].name
								}
							);
						}

						instance.set('roles', roles);
					},

					_renderCards: function(rules) {
						var instance = this;

						var rulesList = instance.get('boundingBox').one('.liferay-ddl-form-rule-rules-list-container');

						var ruleListTemplateRenderer = SoyTemplateUtil.getTemplateRenderer('ddl.rule_list');

						var rulesDescription = instance._getRulesDescription(rules);

						rulesList.setHTML(
							ruleListTemplateRenderer(
								{
									kebab: Liferay.Util.getLexiconIconTpl('ellipsis-v', 'icon-monospaced'),
									rules: rulesDescription,
									strings: instance.get('strings')
								}
							)
						);
					},

					_setRules: function(rules) {
						rules.forEach(
							function(rule) {
								rule.conditions.forEach(
									function(condition) {
										if (condition.operator === 'belongs-to') {
											condition.operands.unshift(
												{
													label: 'User',
													type: 'user',
													value: 'user'
												}
											);
										}
									}
								);
							}
						);

						return rules;
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