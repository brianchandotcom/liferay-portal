AUI.add(
	'liferay-ddm-form-builder-rule-builder',
	function(A) {
		var Lang = A.Lang;

		var SoyTemplateUtil = Liferay.DDM.SoyTemplateUtil;

		var ACTION_TRUNCATE = '<span class="text-truncate">{content}</span>';

		var ACTION_TRUNCATE_INLINE = '<span class="text-truncate-inline">' + ACTION_TRUNCATE + '</span>';

		var ACTION_LABEL = '<span class="label label-lg label-secondary">' + ACTION_TRUNCATE_INLINE + '</span>';

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

					ruleDraft: {
						value: {}
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
							'show-field': Liferay.Language.get('show-x'),
							value: Liferay.Language.get('value')
						}
					}
				},

				NAME: 'liferay-ddm-form-builder-rule-builder',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._createBadgeTooltip();

						instance._getUserRoles();
					},

					bindUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						instance.on('rulesChange', A.bind(instance._onRulesChange, instance));
						instance.on('*:saveRule', A.bind(instance._handleSaveRule, instance));
						instance.on('*:saveRuleDraft', A.bind(instance._handleSaveRuleDraft, instance));
						instance.on('*:cancelRule', A.bind(instance._handleCancelRule, instance));

						instance._eventHandlers = [
							A.one('body').delegate('click', A.bind(instance._handleAddRuleClick, instance), '.lfr-ddm-add-rule'),
							boundingBox.delegate('click', A.bind(instance._handleEditCardClick, instance), '.rule-card-edit'),
							boundingBox.delegate('click', A.bind(instance._handleDeleteCardClick, instance), '.rule-card-delete')
						];
					},

					syncUI: function() {
						var instance = this;

						var ruleBuilderTemplateRenderer = SoyTemplateUtil.getTemplateRenderer('DDMRuleBuilder.render');

						var container = document.createDocumentFragment();

						new ruleBuilderTemplateRenderer(
							{
								plusIcon: Liferay.Util.getLexiconIconTpl('plus', 'icon-monospaced'),
								strings: instance.get('strings')
							},
							container
						);

						instance.get('contentBox').setHTML(container.firstChild.outerHTML);

						var rules = instance.get('rules');

						rules.forEach(
							function(rule) {
								rule.conditions.forEach(
									function(condition) {
										condition.operands.forEach(
											function(operand) {
												operand.label = instance._getFieldLabel(operand.value);

												if (!operand.label) {
													operand.label = operand.value;
												}
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

						if (instance._tooltip) {
							instance._tooltip.destroy();
						}
					},

					getFields: function() {
						var instance = this;

						var fields = [];

						instance.get('formBuilder').eachFormBuilderField(
							function(field) {
								if (field.get('dataType')) {
									fields.push(
										{
											dataType: field.get('dataType'),
											label: field.get('label') || field.get('fieldName'),
											options: field.get('options'),
											pageIndex: instance.getPageIndex(field),
											repeatable: field.get('repeatable'),
											type: field.get('type'),
											value: field.get('fieldName')
										}
									);
								}
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

					isRuleDraftEmpty: function(ruleDraft) {
						var instance = this;

						var ruleDraftIsEmpty = [];
						var ruleDraftKeys = A.Object.keys(ruleDraft);
						var ruleDraftValues = A.Object.values(ruleDraft);

						if (!(ruleDraftKeys.length === 0)) {
							ruleDraftIsEmpty = ruleDraftValues.filter(
								function(ruleDraftValue) {
									if ((typeof (ruleDraftValue) !== 'string') && ruleDraftValue.length > 0) {
										return ruleDraftValue;
									}
								}
							);
						}
						return ruleDraftIsEmpty.length == 0;
					},

					renderRule: function(rule) {
						var instance = this;

						var formBuilder = instance.get('formBuilder');

						var ruleDraft = instance.get('ruleDraft');

						if (!instance._ruleClasses) {
							instance._ruleClasses = new Liferay.DDM.FormBuilderRenderRule(
								{
									boundingBox: instance.get('boundingBox'),
									bubbleTargets: [instance],
									builder: formBuilder,
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

						if (!instance.isRuleDraftEmpty(ruleDraft)) {
							instance._ruleClasses.render(ruleDraft);
						}
						else {
							instance._ruleClasses.render(rule);
						}
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

					_createBadgeTooltip: function() {
						var instance = this;

						instance._tooltip = new A.TooltipDelegate(
							{
								position: 'bottom',
								trigger: '.label',
								triggerHideEvent: ['blur', 'mouseleave'],
								triggerShowEvent: ['focus', 'mouseover'],
								visible: false
							}
						);
					},

					_fillDataProviders: function() {
						var instance = this;

						var formBuilder = instance.get('formBuilder');

						var payload = {
							languageId: formBuilder.get('defaultLanguageId'),
							scopeGroupId: themeDisplay.getScopeGroupId()
						};

						A.io.request(
							Liferay.DDM.Settings.getDataProviderInstancesURL,
							{
								data: payload,
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
						var actionKey = MAP_ACTION_DESCRIPTIONS[type];

						if (actionKey) {
							if (type === 'auto-fill') {
								var fieldList = action.outputs;
								var fieldListLabels = [];

								for (var output in fieldList) {
									fieldListLabels.push(
										Lang.sub(
											ACTION_LABEL,
											{
												content: fieldList[output]
											}
										)
									);
								}

								actionDescription = Lang.sub(
									Liferay.Language.get('autofill-x-from-data-provider-x'),
									[
										fieldListLabels,
										instance._getDataProviderLabel(action.ddmDataProviderInstanceUUID)
									]
								);
							}
							else if (type === 'calculate') {
								actionDescription = Lang.sub(
									Liferay.Language.get('calculate-field-x-as-x'),
									[
										Lang.sub(
											ACTION_LABEL,
											{
												content: action.expression.replace(/\[|\]/g, '')
											}
										),
										Lang.sub(
											ACTION_LABEL,
											{
												content: instance._getFieldLabel(action.target)
											}
										)
									]
								);
							}
							else if (type === 'enable') {
								actionDescription = Lang.sub(
									Liferay.Language.get('enable-x'),
									[
										Lang.sub(
											ACTION_LABEL,
											{
												content: action.label
											}
										)
									]
								);
							}
							else if (type === 'jump-to-page') {
								var pageIndex = action.target;
								var pages = instance.getPages();

								var page = pages[pageIndex];

								var pageLabel = page ? page.label : (parseInt(pageIndex) + 1);

								actionDescription = Lang.sub(
									Liferay.Language.get('jump-to-page-x'),
									[
										Lang.sub(
											ACTION_LABEL,
											{
												content: pageLabel.toString()
											}
										)
									]
								);
							}
							else if (type === 'require') {
								actionDescription = Lang.sub(
									Liferay.Language.get('require-x'),
									[
										Lang.sub(
											ACTION_LABEL,
											{
												content: action.label
											}
										)
									]
								);
							}
							else if (type === 'show') {
								actionDescription = Lang.sub(
									Liferay.Language.get('show-x'),
									[
										Lang.sub(
											ACTION_LABEL,
											{
												content: action.label
											}
										)
									]
								);
							}
						}

						return window.DDMRuleBuilder.render.Soy.toIncDom(actionDescription);
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

						var formBuilder = instance.get('formBuilder');

						var roles = instance.get('roles');

						var payload = {
							languageId: formBuilder.get('defaultLanguageId'),
							scopeGroupId: themeDisplay.getScopeGroupId()
						};

						if (!roles.length) {
							A.io.request(
								Liferay.DDM.Settings.getRolesURL,
								{
									data: payload,
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

						instance._hideAddRuleButton();
					},

					_handleCancelRule: function() {
						var instance = this;

						instance.set('ruleDraft', {});

						instance.syncUI();

						instance._showAddRuleButton();
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

						var rule = instance.get('rules')[ruleId];

						instance._currentRuleId = ruleId;

						instance._hideAddRuleButton();

						instance.set('ruleDraft', rule);

						instance.renderRule(rule);
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

						var ruleDraft = {
							actions: {},
							condition: {},
							'logical-operator': ''
						};

						instance.set('ruleDraft', ruleDraft);

						instance.syncUI();

						instance._currentRuleId = null;

						instance._showAddRuleButton();
					},

					_handleSaveRuleDraft: function(event) {
						var instance = this;

						var rule = {
							actions: event.actions,
							conditions: event.conditions,
							'logical-operator': event['logical-operator']
						};

						instance.set('ruleDraft', rule);
					},

					_hideAddRuleButton: function() {
						var instance = this;

						A.one('.lfr-ddm-add-rule').addClass('hide');
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

						var rulesList = instance.get('boundingBox').one('.liferay-ddm-form-rule-rules-list-container');

						var rulesDescription = instance._getRulesDescription(rules);

						var ruleListTemplateRenderer = SoyTemplateUtil.getTemplateRenderer('DDMRuleBuilder.rule_list');

						var container = document.createDocumentFragment();

						new ruleListTemplateRenderer(
							{
								kebab: Liferay.Util.getLexiconIconTpl('ellipsis-v', 'icon-monospaced'),
								rules: rulesDescription,
								strings: instance.get('strings')
							},
							container
						);

						rulesList.setHTML(container.firstChild.outerHTML);
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
					},

					_showAddRuleButton: function() {
						var instance = this;

						A.one('.lfr-ddm-add-rule').removeClass('hide');
					}
				}
			}
		);

		Liferay.namespace('DDM').FormBuilderRuleBuilder = FormBuilderRuleBuilder;
	},
	'',
	{
		requires: ['aui-popover', 'event-outside', 'liferay-ddm-form-builder-rule']
	}
);