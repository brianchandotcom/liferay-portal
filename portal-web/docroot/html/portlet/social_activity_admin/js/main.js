AUI().add(
	'liferay-social-activity-admin',
	function(A) {

		var Node = A.Node,

		//

		ACTIVE = 'active',
		BOUNDING_BOX = 'boundingBox',
		CONTENT_BOX = 'contentBox',
		CONTRIBUTION = 'Contribution',
		DOT = '.',
		EMPTY_STR = '',
		EVENT_SUBMIT = 'submit';
		FIELD = 'field',
		JSON_SETTINGS = 'jsonSettings',
		PARTICIPATION = 'Participation',
		SPACE = ' ',

		// Attribute names

		LANGUAGE_KEY = 'languageKey',
		ACTIVITY_KEY = 'activityKey',
		LOCALIZED_NAME = 'localizedName',
		MODEL_NAME = 'modelName',
		PARTICIPATION_LIMIT = 'participationLimit',
		PARTICIPATION_LIMIT_TYPE = 'participationLimitType',
		PARTICIPATION_VALUE = 'participationValue',
		CONTRIBUTION_VALUE = 'contributionValue',
		CONTRIBUTION_LIMIT_TYPE = 'contributionLimitType',
		CONTRIBUTION_LIMIT = 'contributionLimit',

		// Localized values

		SOCIAL_ACTIVITY_SETTING_OR = Liferay.Language.get('or').toLowerCase(),
		SOCIAL_ACTIVITY_SETTING_HEADER_LABEL = Liferay.Language.get('social-activity-setting-header-label'),
		SOCIAL_ACTIVITY_SETTING_FIRSTTEXT = Liferay.Language.get('social-activity-setting-firsttext'),
		SOCIAL_ACTIVITY_SETTING_SECONDTEXT = Liferay.Language.get('social-activity-setting-secondtext'),
		SOCIAL_ACTIVITY_SETTING_THIRDTEXT = Liferay.Language.get('social-activity-setting-thirdtext'),
		SOCIAL_ACTIVITY_SETTING_CONTRIBUTION_LIMIT_FIRSTTEXT = A.Lang.sub(Liferay.Language.get('social-activity-setting-limit-firsttext'), [CONTRIBUTION]),
		SOCIAL_ACTIVITY_SETTING_PARTICIPATION_LIMIT_FIRSTTEXT = A.Lang.sub(Liferay.Language.get('social-activity-setting-limit-firsttext'), [PARTICIPATION]),
		TIMES = Liferay.Language.get('times'),
		TIMES_A_DAY = Liferay.Language.get('times-a-day'),
		TIMES_PER_PERIOD = Liferay.Language.get('times-per-period'),

		// CSS selectors

		ACTION_FIELD = 'action-field',
		ACTION_VIEW = 'action-view',
		AUI_HELPER_HIDDEN = 'aui-helper-hidden',
		AUI_SETTINGS_FIELD_CONTENT = 'aui-settings-field-content',
		COMPLEMENTARY_ELEMENT = 'complementary-element',
		COMPONENT = 'component',
		CONTAINER_DROP_BOX = 'container-drop-box',
		CONTENT_FIELD = 'content-field',
		ENABLED_CHECKBOX = 'enabledCheckbox',
		FIELD_CHECKBOX = 'field-checkbox',
		FIELD_INPUT_CHOICE = 'aui-field-input-choice',
		FIELD_TEXT = 'field-text',
		FIELD_VALUES = 'field-values',
		FORM = 'form',
		HELPER_CLEARFIX = 'helper-clearfix',
		SELECTED = 'selected',
		SETTINGS_BUTTON = 'settings-button',
		SETTINGS_BUTTON_CLOSE = 'settings-button-close',
		SETTINGS_BUTTON_HOLDER = 'settings-button-holder',
		SETTINGS_BUTTON_MAXIMIZE = 'settings-button-maximize',
		SETTINGS_BUTTON_MINIMIZE = 'settings-button-minimize',
		SETTINGS_CONTAINER = 'settings-container',
		SETTINGS_LABEL = 'settings-label',
		SETTINGS_LIMIT = 'settings-limit',
		SETTINGS_LIMIT_FIELD = 'settings-limit-field',
		SETTINGS_FIELD = 'settings-field',
		SETTINGS_FIELD_BUTTONS = 'settings-field-buttons',
		SETTINGS_FIELD_CHECKBOX = 'settings-field-checkbox',
		SETTINGS_FIELD_NODE = 'settings-field-node',
		SETTINGS_HEADER = 'settings-header',
		SETTINGS_HEADER_HOLDER = 'settings-header-holder',
		SETTINGS_HEADER_LABEL = 'settings-header-label',
		SETTINGS_ICON = 'settings-icon',
		SETTINGS_ICON_CLOSE = 'settings-icon-close',
		SETTINGS_ICON_MAXIMIZE = 'settings-icon-maximize',
		SETTINGS_ICON_MINIMIZE = 'settings-icon-minimize',
		SETTINGS_ICON_TIP = 'settings-icon-tip',
		SOCIAL_ACTIVITY_ADMIN = 'social-activity-admin',
		SOCIAL_ACTIVITY_ITEM = 'social-activity-item',
		SOCIAL_ACTIVITY_ITEM_CONTENT = 'social-activity-item-content',
		SOCIAL_ACTIVITY_ITEMS = 'social-activity-items',
		UPDATE_SOCIAL_ACTIVITY_FORM = 'update-socialactivity-form',
		WIDGET = 'widget',

		// Limit selectable values

		LIMIT_TYPE = [TIMES_A_DAY, TIMES, TIMES_PER_PERIOD];

		var SocialActivityAdmin = A.Component.create(
			{
				NAME: SOCIAL_ACTIVITY_ADMIN,

				EXTENDS: A.Base,

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._config = config;

						instance.portletId = config.portletId;

						instance._prefixedPortletId = '_' + config.portletId + '_';

						instance._contentBox = A.one(DOT + SOCIAL_ACTIVITY_ITEM_CONTENT);

						instance._socialActivityItems = A.all(DOT + SOCIAL_ACTIVITY_ITEM);

						instance._socialActivityCheckboxes = instance._socialActivityItems.all(DOT + FIELD_INPUT_CHOICE);

						var socialActivityForm = A.one(FORM + DOT + UPDATE_SOCIAL_ACTIVITY_FORM);

						var settingsInput = instance._getItemByName(socialActivityForm, JSON_SETTINGS);

						socialActivityForm.detach(EVENT_SUBMIT);

						socialActivityForm.on(EVENT_SUBMIT, instance._onSocialActivityFormSubmit, instance, socialActivityForm);

						instance._socialActivityForm = socialActivityForm;

						instance._settingsInput = settingsInput;

						var isDoing = false;

						A.each(
							instance._socialActivityItems,
							function(item, index, collection) {
								var itemLabel = item.one(DOT + SETTINGS_LABEL);

								var modelName = instance._getModelNameByItem(item);

								var getSocialActivitySettingMappingCallback = function(result) {
									if (result.length > 0) {
										instance._config.modelName = modelName;

										instance._config.dataSet = result;

										instance._addSettingsContainer(instance._config);
									}
								}

								if (instance._getCheckboxByItem(item).attr('checked') && !isDoing ) {
									instance._getSocialActivitySettingMapping(
										themeDisplay.getScopeGroupId(),
										modelName,
										getSocialActivitySettingMappingCallback
									);

									isDoing = true;

									item.addClass(SELECTED);
								}

								item.on(
									'click',
									function(event) {
										A.each(
											collection,
											function(item) {
												item.removeClass(SELECTED);
											}
										);

										event.currentTarget.addClass(SELECTED);
									}
								);

								itemLabel.on(
									'click',
									function(event) {
										var settingsContainer = instance._contentBox.one(DOT + SETTINGS_CONTAINER);

										if (settingsContainer) {
											settingsContainer.remove();
										}

										instance._getSocialActivitySettingMapping(
											themeDisplay.getScopeGroupId(),
											modelName,
											getSocialActivitySettingMappingCallback
										)
									}
								)
							}
						);

						A.each(
							instance._socialActivityCheckboxes,
							function(item) {
								item.on('click', instance._updateCheckboxStatus, instance);
							}
						);
					},

					_addSettingsContainer: function(config) {
						var instance = this;

						instance.settingsContainer = new SettingsContainer(config);

						instance.settingsContainer.render(instance._contentBox);
					},

					_getItemByName: function(currentForm, name, withoutNamespace) {
						var instance = this;

						var inputName = withoutNamespace ? name : instance._config.portletNamespace + name;

						return currentForm.one('[name=' + inputName + ']');
					},

					_getCheckboxByItem: function(item) {
						return item.one(DOT + FIELD_INPUT_CHOICE);
					},

					_getModelNameByItem: function(item) {
						var instance = this;

						var itemId = instance._getCheckboxByItem(item).attr('id');

						var modelName = itemId.replace(instance._prefixedPortletId, '').replace(DOT + ENABLED_CHECKBOX, '');

						return modelName;
					},

					_getJsonSettings: function(settingsContainer) {
						var instance = this;

						var jsonSettings = {};

						jsonSettings.modelName = instance._config.modelName;

						jsonSettings.actions = settingsContainer.getJSONSettings();

						return jsonSettings;
					},

					_getSocialActivitySettingMapping: function(groupId, modelName, callback) {
						var instance = this;

						Liferay.Service.Social.SocialActivitySetting.getJSONActivityDefinitions(
							{
								groupId: groupId,
								modelName: modelName
							},
							callback
						);
					},

					_onSocialActivityFormSubmit: function(event, form) {
						var instance = this;

						event.halt();

						instance._updateSocialActivitySettings(form);

						submitForm(form)
					},

					_updateCheckboxStatus: function(event) {
						var instance = this;

						var itemId = event.currentTarget.attr('id');

						var modelName = itemId.replace(instance._prefixedPortletId, '').replace(DOT + ENABLED_CHECKBOX, '');

						Liferay.Service.Social.SocialActivitySetting.updateGroupSetting(
								{
									groupId: themeDisplay.getScopeGroupId(),
									modelName: modelName,
									value: event.currentTarget.attr('checked')
								}
							);
					},

					_updateSocialActivitySettings: function(form) {
						var instance = this;

						instance._settingsInput.val(A.JSON.stringify(instance._getJsonSettings(instance.settingsContainer)));
					}
				}
			}
		);

		Liferay.Portlet.SocialActivityAdmin = SocialActivityAdmin;

		var TPL_BOUNDING_BOX = '<div class="' + [WIDGET, COMPONENT, SETTINGS_CONTAINER].join(SPACE) + '"></div>',

		TPL_BUTTON_HOLDER = '<div class="' + [SETTINGS_BUTTON_HOLDER].join(SPACE) + ' aui-button aui-button-submit">' +
								'<span class="aui-button-content">' +
									'<input class="aui-button-input aui-button-input-submit" type="submit" value="Save"/>' +
								'</span>' +
							'</div>',

		TPL_DROP_BOX = '<ul class="' + CONTAINER_DROP_BOX + '"></ul>',

		TPL_HEADER_BOX = '<ul class="' + [SETTINGS_HEADER].join(SPACE) + '"></ul>'

		TPL_HEADER_HOLDER = '<div class="' + [SETTINGS_HEADER_HOLDER].join(SPACE) + '"></div>',

		TPL_SETTINGS_HEADER_LABEL = '<div class="' + SETTINGS_HEADER_LABEL + '">{text}:</div>',

		TPL_COMPLEMENTARY_ELEMENT = '<li class="' + COMPLEMENTARY_ELEMENT + '"><div><span>{text}</span></div></li>';

		var SettingsContainer = A.Component.create(
			{
				NAME: SETTINGS_CONTAINER,

				EXTENDS: A.Widget,

				ATTRS: {

					buttonsNode: {
						valueFn: function() {
							return A.Node.create(TPL_BUTTON_HOLDER);
						}
					},

					modelName: {
						value: EMPTY_STR
					},

					id: {
						value: EMPTY_STR
					}
				},

				HTML_PARSER: {
				
				},

				prototype: {
					BOUNDING_TEMPLATE: TPL_BOUNDING_BOX,

					initializer: function(config) {
						var instance = this;

						instance.set(MODEL_NAME, config.modelName);

						instance._config = config;

						instance.settingsFields = [];
					},

					bindUI: function() {
						var instance = this;

						instance.headerBox.delegate('dblclick', A.bind(instance._putItemToDropBox, instance), DOT + SETTINGS_FIELD);

						instance.dropBox.delegate('click', A.bind(instance._putItemToHeader, instance), DOT + SETTINGS_ICON_CLOSE);
						instance.dropBox.delegate('click', A.bind(instance._maximizeItem, instance), DOT + SETTINGS_ICON_MAXIMIZE);
						instance.dropBox.delegate('click', A.bind(instance._minimizeItem, instance), DOT + SETTINGS_ICON_MINIMIZE);
					},

					renderUI: function() {
						var instance = this;

						var boundingBox = instance.get(BOUNDING_BOX);

						var contentBox = instance.get(CONTENT_BOX);

						var headerLabelNode = Node.create(instance._getSettingsHeaderLabelHTML(SOCIAL_ACTIVITY_SETTING_HEADER_LABEL));

						var headerBox = Node.create(TPL_HEADER_BOX);

						instance.dropBox = Node.create(TPL_DROP_BOX);

						instance.headerHolder = Node.create(TPL_HEADER_HOLDER);

						instance.buttonHolder = Node.create(TPL_BUTTON_HOLDER);

						instance.headerHolder.append(headerLabelNode);
						instance.headerHolder.append(headerBox);

						contentBox.append(instance.headerHolder);
						contentBox.append(instance.dropBox);
						contentBox.append(instance.buttonHolder);

						A.each(
							instance._config.dataSet,
							function(item) {
								var settingsField = new SettingsField(item);

								settingsField._config = instance._config;

								if (settingsField._isActive()) {
									settingsField.set(ACTIVE, true);

									settingsField.render(instance.dropBox);

									settingsField._setDropBoxView(settingsField.get(BOUNDING_BOX));
								}
								else {
									settingsField.render(headerBox);
								}

								instance.settingsFields[settingsField.get(LANGUAGE_KEY)] = settingsField;
							}
						);

						instance.headerBox = headerBox;

						instance._checkComplementaryElement();
					},

					getFieldInstance: function(source) {
						var instance = this;

						var languageKey = source.attr(LANGUAGE_KEY);

						return instance.settingsFields[languageKey];
					},


					getSourceByNode: function(node) {
						var instance = this;

						return node.ancestor('li', true);
					},

					getJSONSettings: function() {
						var instance = this;

						var settingsArray = [];

						for (key in instance.settingsFields) {
							var value = instance.settingsFields[key];

							settingsArray.push(value.getJSONSettings());
						};

						return settingsArray;
					},

					_changeLimitState: function(item, maximized) {
						if (maximized) {
							item.one(DOT + SETTINGS_ICON_MINIMIZE).show();
							item.one(DOT + SETTINGS_ICON_MAXIMIZE).hide();
							item.one(DOT + SETTINGS_LIMIT).show();
						}
						else {
							item.one(DOT + SETTINGS_ICON_MINIMIZE).hide();
							item.one(DOT + SETTINGS_ICON_MAXIMIZE).show();
							item.one(DOT + SETTINGS_LIMIT).hide();
						}
					},

					_checkComplementaryElement: function() {
						var instance = this;

						var headerBox = instance.headerBox;

						var ce =  headerBox.one(DOT + COMPLEMENTARY_ELEMENT);

						if (ce) {
							ce.remove();
						}
						else {
							ce = Node.create(instance._getComplementaryElementHTML(SOCIAL_ACTIVITY_SETTING_OR));
						}

						if (headerBox.get('children').size() > 1) {
							headerBox.insert(ce, headerBox.get('lastChild'));
						}
					},

					_getComplementaryElementHTML: function(text) {
						var instance = this;

						return A.substitute(
							TPL_COMPLEMENTARY_ELEMENT,
							{
								text: text
							}
						)
					},

					_getSettingsHeaderLabelHTML: function(text) {
						var instance = this;

						return A.substitute(
							TPL_SETTINGS_HEADER_LABEL,
							{
								text: text
							}
						)
					},

					_maximizeItem: function(event) {
						var instance = this;

						var target = event.currentTarget;

						var item = target.ancestor(DOT + SETTINGS_FIELD);

						instance._changeLimitState(item, true);
					},

					_minimizeItem: function(event) {
						var instance = this;

						var target = event.currentTarget;

						var item = target.ancestor(DOT + SETTINGS_FIELD);

						instance._changeLimitState(item, false);
					},

					_putItemToDropBox: function(event) {
						var instance = this;

						var item = event.currentTarget;

						item.remove();

						var source = instance.getSourceByNode(item);

						var field = instance.getFieldInstance(source);

						field._setDropBoxView(item);

						instance.dropBox.append(item);

						instance._checkComplementaryElement();
					},

					_putItemToHeader: function(event) {
						var instance = this;

						var target = event.currentTarget;

						var item = target.ancestor(DOT + SETTINGS_FIELD);

						item.one(DOT + SETTINGS_LIMIT).hide();

						item.remove();

						var source = instance.getSourceByNode(item);

						var field = instance.getFieldInstance(source);

						field._setHeaderView(item);
						field._setToDefaultValue();

						instance.headerBox.append(item);

						instance._checkComplementaryElement();

						instance._changeLimitState(item, false);
					}
				}
			}
		);

		var TPL_ACTION_VIEW = '<span class="' + [ACTION_VIEW].join(SPACE) + '">{text}</span>',

		TPL_BOUNDING_BOX = '<li class="' + [WIDGET, COMPONENT, SETTINGS_FIELD,ACTION_FIELD].join(SPACE) + '"></li>',

		TPL_BUTTONS = '<div class="' + [SETTINGS_FIELD_BUTTONS, AUI_HELPER_HIDDEN].join(SPACE) + '">' +
						'<a class="' + [SETTINGS_BUTTON, SETTINGS_BUTTON_MAXIMIZE].join(SPACE) + '" href="javascript:;" title="Maximize">' +
							'<div class="' + [SETTINGS_ICON, SETTINGS_ICON_MAXIMIZE].join(SPACE) + '"></div>' +
						'</a>' +
						'<a class="' + [SETTINGS_BUTTON, SETTINGS_BUTTON_MINIMIZE].join(SPACE) + '" href="javascript:;" title="Minimize">' +
							'<div class="' + [SETTINGS_ICON, SETTINGS_ICON_MINIMIZE, AUI_HELPER_HIDDEN].join(SPACE) + '"></div>' +
						'</a>' +
						'<a class="' + [SETTINGS_BUTTON, SETTINGS_BUTTON_CLOSE].join(SPACE) + '" href="javascript:;" title="Close">' +
							'<div class="' + [SETTINGS_ICON, SETTINGS_ICON_CLOSE].join(SPACE) + '"></div>' +
						'</a>' +
					  '</div>',

		TPL_CHECKBOX = '<input id="{id}" class="' + [SETTINGS_FIELD_NODE, FIELD_CHECKBOX].join(SPACE) + '" name="{name}" type="checkbox" value="{value}" {checked} />'

		TPL_FIELD_TEXT = '<span class="' + [FIELD, FIELD_TEXT].join(SPACE) + '">{text}</span>',

		TPL_FIELD_VALUES = '<div class="' + [FIELD_VALUES, AUI_HELPER_HIDDEN].join(SPACE) + '"></div>',

		TPL_LABEL = '<div class="' + SETTINGS_LABEL + '">{text}</div>',

		TPL_LIMIT = '<table class="' + [SETTINGS_LIMIT, AUI_HELPER_HIDDEN].join(SPACE) + '"></table>',

		TPL_LIMIT_FIELD = '<tr class="' + [SETTINGS_LIMIT_FIELD].join(SPACE) + '"></tr>',

		TPL_LIMIT_CELL_FIELD = '<td></td>',

		TPL_OPTION = '<option value="{value}" title="{name}">{name}</option>',

		TPL_SELECT = '<select  class="' + [SETTINGS_FIELD_NODE].join(SPACE) + '"></select>',

		TPL_TIP_ICON = '<a href="javascript:;" class="' + SETTINGS_ICON_TIP + '"></a>';

		var SettingsField = A.Component.create(
			{
				NAME: SETTINGS_FIELD,

				ATTRS: {

					active: {
						setter: A.DataType.Boolean.parse,
						value: false
					},

					activityKey: {
						value: 0
					},

					languageKey: {
						value: EMPTY_STR
					},

					participationLimit: {
						value: 0
					},

					participationLimitType: {
						value: 1
					},

					participationValue: {
						value: 0
					},

					localizedName: {
						value: EMPTY_STR
					},

					contributionLimit: {
						value: 0
					},

					contributionLimitType: {
						value: 1
					},

					contributionValue: {
						value: 0
					},

					selected: {
						setter: A.DataType.Boolean.parse,
						value: false
					},

					settingsContainer: {
						value: undefined
					},

					tip: {
						value: EMPTY_STR
					}
				},

				prototype: {
					BOUNDING_TEMPLATE: TPL_BOUNDING_BOX,

					initializer: function(dataSet) {
						var instance = this;

						instance.set(ACTIVITY_KEY, dataSet.activityKey);
						instance.set(LANGUAGE_KEY, dataSet.languageKey);
						instance.set(LOCALIZED_NAME, Liferay.Language.get(
							dataSet.modelName.replace(
								'com.liferay.portlet', 'social.activity.admin')
									+ DOT + dataSet.languageKey));

						for (var i = 0; i < dataSet.counters.length; i++) {
							var action = dataSet.counters[i];

							if (action.name == 'participation') {
								instance.set(PARTICIPATION_VALUE, action.increment);
								instance.set(PARTICIPATION_LIMIT, action.limit);
								instance.set(PARTICIPATION_LIMIT_TYPE, action.limitType);
							}
							else if (action.name == 'contribution') {
								instance.set(CONTRIBUTION_VALUE, action.increment);
								instance.set(CONTRIBUTION_LIMIT, action.limit);
								instance.set(CONTRIBUTION_LIMIT_TYPE, action.limitType);
							}
						}

						instance._selectInputs = [];

						instance._dataSet = dataSet;
					},

					bindUI: function() {
						var instance = this;
					},

					getJSONSettings: function() {
						var instance = this;

						var settingsObject = {};

						settingsObject.languageKey = instance.get(LANGUAGE_KEY);
						settingsObject.activityKey = instance.get(ACTIVITY_KEY);

						settingsObject.participationLimit = instance.get(PARTICIPATION_LIMIT);
						settingsObject.participationValue = instance.get(PARTICIPATION_VALUE);
						settingsObject.participationLimitType = instance.get(PARTICIPATION_LIMIT_TYPE);

						settingsObject.contributionLimit = instance.get(CONTRIBUTION_LIMIT);
						settingsObject.contributionValue = instance.get(CONTRIBUTION_VALUE);
						settingsObject.contributionLimitType = instance.get(CONTRIBUTION_LIMIT_TYPE);

						return settingsObject;
					},

					renderUI: function() {
						var instance = this;

						var boundingBox = instance.get(BOUNDING_BOX);

						var buttonsNode = A.Node.create(TPL_BUTTONS);

						var contentBox = instance.get(CONTENT_BOX);

						boundingBox.attr(LANGUAGE_KEY, instance.get(LANGUAGE_KEY));

						instance.actionViewNode = Node.create(instance._getActionViewHTML(instance.get(LOCALIZED_NAME)));

						instance.valuesNode = instance._getBuiltFieldValues();

						instance.limitNode = instance._getLimitNode(instance._dataSet)

						contentBox.append(instance.actionViewNode);
						contentBox.append(instance.valuesNode);
						contentBox.append(buttonsNode);
						contentBox.append(instance.limitNode);
					},

					_addOptionToSelect: function(values, select, actualValue) {
						var instance = this;

						A.each(
							values,
							function(value) {
								var option = Node.create(instance._getOptionHTML(value, value));

								if (value == actualValue) {
									option.attr(SELECTED, 'true');
								}

								select.append(option);
							}
						);
					},

					_addOptionToLimitTypeSelect: function(values, select, actualValue) {
						var instance = this;

						A.each(
							values,
							function(value, index) {
								var option = Node.create(instance._getOptionHTML(value, index + 1));

								if (index + 1 == actualValue) {
									option.attr(SELECTED, 'true');
								}

								select.append(option);
							}
						);
					},

					_getActionViewHTML: function(text) {
						var instance = this;

						return A.substitute(
							TPL_ACTION_VIEW,
							{
								text: text
							}
						)
					},

					_getLimitFieldNode: function(value) {
						var instance = this;

						var languageKey = instance.get(LANGUAGE_KEY);

						var limitField = Node.create(TPL_LIMIT_FIELD);

						var limitCellField = Node.create(TPL_LIMIT_CELL_FIELD);

						var firstText = EMPTY_STR;

						var limitTypeValuesNode = Node.create(TPL_SELECT);

						var limitValuesNode = Node.create(TPL_SELECT);

						if (value == CONTRIBUTION) {
							limitValuesNode.attr('id', languageKey + '_' + CONTRIBUTION_LIMIT);

							limitTypeValuesNode.attr('id', languageKey + '_' + CONTRIBUTION_LIMIT_TYPE);

							instance._addOptionToLimitTypeSelect(LIMIT_TYPE, limitTypeValuesNode,
								instance.get(CONTRIBUTION_LIMIT_TYPE));

							instance._addOptionToSelect(instance._config.selectableContributionLimitValues, limitValuesNode,
								instance.get(CONTRIBUTION_LIMIT));

							firstText = Node.create(instance._getFieldTextHTML(
								SOCIAL_ACTIVITY_SETTING_CONTRIBUTION_LIMIT_FIRSTTEXT));
						}
						else {
							limitValuesNode.attr('id', languageKey + '_' + PARTICIPATION_LIMIT);

							limitTypeValuesNode.attr('id', languageKey + '_' + PARTICIPATION_LIMIT_TYPE);

							instance._addOptionToLimitTypeSelect(LIMIT_TYPE, limitTypeValuesNode,
								instance.get(PARTICIPATION_LIMIT_TYPE));

							instance._addOptionToSelect(instance._config.selectableParticipationLimitValues, limitValuesNode,
								instance.get(PARTICIPATION_LIMIT));

							firstText = Node.create(instance._getFieldTextHTML(
									SOCIAL_ACTIVITY_SETTING_PARTICIPATION_LIMIT_FIRSTTEXT));
						}

						limitValuesNode.on('change', instance._selectOnChange, instance);

						limitTypeValuesNode.on('change', instance._selectOnChange, instance);

						instance._selectInputs.push(limitValuesNode);
						instance._selectInputs.push(limitTypeValuesNode);

						limitField.append(limitCellField.append(firstText));

						limitCellField = Node.create(TPL_LIMIT_CELL_FIELD);

						limitCellField.append(limitValuesNode);

						limitField.append(limitCellField);

						limitCellField = Node.create(TPL_LIMIT_CELL_FIELD);

						limitCellField.append(limitTypeValuesNode);

						limitField.append(limitCellField);

						return limitField;
					},

					_getLimitNode: function(values) {
						var instance = this;

						var limitNode = Node.create(TPL_LIMIT);

						var contributionNode = instance._getLimitFieldNode(CONTRIBUTION);

						var participationNode = instance._getLimitFieldNode(PARTICIPATION);

						limitNode.append(contributionNode);
						limitNode.append(participationNode);

						return limitNode;
					},

					_getFieldTextHTML: function(text) {
						var instance = this;

						return A.substitute(
							TPL_FIELD_TEXT,
							{
								text: text
							}
						)
					},

					_getLabelHTML: function(text) {
						var instance = this;

						return A.substitute(
							TPL_LABEL,
							{
								text: text
							}
						)
					},

					_getOptionHTML: function(name, value) {
						var instance = this;

						return A.substitute(
							TPL_OPTION,
							{
								name: name,
								value: value
							}
						)
					},

					_getBuiltFieldValues: function() {
						var instance = this;

						var languageKey = instance.get(LANGUAGE_KEY);

						var fieldValues = Node.create(TPL_FIELD_VALUES);

						var labelNode = Node.create(instance._getLabelHTML(instance.get(LOCALIZED_NAME)));
						var firstText = Node.create(instance._getFieldTextHTML(SOCIAL_ACTIVITY_SETTING_FIRSTTEXT));
						var secondText = Node.create(instance._getFieldTextHTML(SOCIAL_ACTIVITY_SETTING_SECONDTEXT));
						var thirdText = Node.create(instance._getFieldTextHTML(SOCIAL_ACTIVITY_SETTING_THIRDTEXT));

						var participationValuesNode = Node.create(TPL_SELECT);
						var contributionValuesNode = Node.create(TPL_SELECT);

						participationValuesNode.attr('id', languageKey + '_' + PARTICIPATION_VALUE);
						participationValuesNode.on('change', instance._selectOnChange, instance);

						contributionValuesNode.attr('id', languageKey + '_' + CONTRIBUTION_VALUE);
						contributionValuesNode.on('change', instance._selectOnChange, instance);

						instance._addOptionToSelect(instance._config.selectableContributionValues, contributionValuesNode,
							instance.get(CONTRIBUTION_VALUE));

						instance._addOptionToSelect(instance._config.selectableParticipationValues, participationValuesNode,
							instance.get(PARTICIPATION_VALUE));

						fieldValues.append(labelNode);
						fieldValues.append(firstText);
						fieldValues.append(participationValuesNode);
						fieldValues.append(secondText);
						fieldValues.append(contributionValuesNode);
						fieldValues.append(thirdText);

						instance._selectInputs.push(participationValuesNode);
						instance._selectInputs.push(contributionValuesNode);

						return fieldValues;
					},

					_isActive: function() {
						var instance = this;

						if (instance.get(CONTRIBUTION_VALUE) > 0 || instance.get(PARTICIPATION_VALUE) > 0) {
							return true;
						}
					},

					_selectOnChange: function(event) {
						var instance = this;

						var selectId = event.currentTarget.attr('id');

						var languageKey = instance.get(LANGUAGE_KEY);

						selectId = selectId.replace(languageKey + '_', EMPTY_STR);

						instance.set(selectId, event.currentTarget.val());
					},

					_setDropBoxView: function(item) {
						var instance = this;

						item.removeClass(ACTION_FIELD);

						item.addClass(CONTENT_FIELD);

						item.one(DOT + ACTION_VIEW).hide();
						item.one(DOT + FIELD_VALUES).show();
						item.one(DOT + SETTINGS_FIELD_BUTTONS).show();

						instance.set(ACTIVE, false);
					},

					_setHeaderView: function(item) {
						var instance = this;

						item.removeClass(CONTENT_FIELD);

						item.addClass(ACTION_FIELD);

						item.one(DOT + ACTION_VIEW).show();
						item.one(DOT + FIELD_VALUES).hide();
						item.one(DOT + SETTINGS_FIELD_BUTTONS).hide();

						instance.set(ACTIVE, true);
					},

					_setToDefaultValue: function() {
						var instance = this;

						instance.set(CONTRIBUTION_VALUE, 0);
						instance.set(CONTRIBUTION_LIMIT, 0);
						instance.set(CONTRIBUTION_LIMIT_TYPE, 1);
						instance.set(PARTICIPATION_VALUE, 0);
						instance.set(PARTICIPATION_LIMIT, 0);
						instance.set(PARTICIPATION_LIMIT_TYPE, 1);

						A.each(
							instance._selectInputs,
							function(item) {
								item.set('selectedIndex', 0);
							}
						)
					}
				}
			}
		)
	},
	'',
	{
		requires: ['aui-base', 'aui-datatype']
	}
);