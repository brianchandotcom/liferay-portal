AUI().add(
	'liferay-social-activity-admin',
	function(A) {
		var Lang = A.Lang;

		var Node = A.Node;

		var CSS_SETTINGS_FIELD_BUTTONS = 'settings-field-buttons';

		var CSS_SETTINGS_ICON = 'settings-icon';

		var CSS_SETTINGS_ICON_CLOSE = 'settings-icon-close';

		var CSS_SETTINGS_ICON_MAXIMIZE = 'settings-icon-maximize';

		var CSS_SETTINGS_ICON_MINIMIZE = 'settings-icon-minimize';

		var COL_LIMIT_TYPE = [Liferay.Language.get('times-a-day'), Liferay.Language.get('times'), Liferay.Language.get('times-per-period')];

		var STR_ACTION_FIELD = 'action-field';

		var STR_ACTION_VIEW = 'action-view';

		var STR_ACTIVE = 'active';

		var STR_ACTIVITY_TYPE = 'activityType';

		var STR_AUI_HELPER_HIDDEN = 'aui-helper-hidden';

		var STR_BLANK = '';

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_COMPLEMENTARY_ELEMENT = 'complementary-element';

		var STR_COMPONENT = 'component';

		var STR_CONTENT_BOX = 'contentBox';

		var STR_CONTENT_FIELD = 'content-field';

		var STR_CONTRIBUTION = 'Contribution';

		var STR_CONTRIBUTION_LIMIT_PERIOD = 'contributionLimitPeriod';

		var STR_CONTRIBUTION_LIMIT_VALUE = 'contributionLimitValue';

		var STR_CONTRIBUTION_VALUE = 'contributionValue';

		var STR_DATA_MODEL_NAME = 'data-modelName';

		var STR_DOT = '.';

		var STR_EVENT_SUBMIT = 'submit';

		var STR_FIELD = 'field';

		var STR_FIELD_TEXT = 'field-text';

		var STR_FIELD_VALUES = 'field-values';

		var STR_JSON_SETTINGS = 'jsonSettings';

		var STR_LANGUAGE_KEY = 'languageKey';

		var STR_LOCALIZED_NAME = 'localizedName';

		var STR_PARTICIPATION = 'Participation';

		var STR_PARTICIPATION_LIMIT_PERIOD = 'participationLimitPeriod';

		var STR_PARTICIPATION_LIMIT_VALUE = 'participationLimitValue';

		var STR_PARTICIPATION_VALUE = 'participationValue';

		var STR_SELECTED = 'selected';

		var STR_SETTINGS = 'settings';

		var STR_SETTINGS_BUTTON = 'settings-button';

		var STR_SETTINGS_BUTTON_CLOSE = 'settings-button-close';

		var STR_SETTINGS_BUTTON_HOLDER = 'settings-button-holder';

		var STR_SETTINGS_BUTTON_MAXIMIZE = 'settings-button-maximize';

		var STR_SETTINGS_BUTTON_MINIMIZE = 'settings-button-minimize';

		var STR_SETTINGS_CONTAINER = 'settings-container';

		var STR_SETTINGS_FIELD = 'settings-field';

		var STR_SETTINGS_LABEL = 'settings-label';

		var STR_SETTINGS_LIMIT = 'settings-limit';

		var STR_SPACE = ' ';

		var SELECTOR_COMPLEMENTARY_ELEMENT = STR_DOT + STR_COMPLEMENTARY_ELEMENT;

		var SELECTOR_FIELD_INPUT_CHOICE = '.aui-field-input-choice';

		var SELECTOR_SETTINGS_CONTAINER = STR_DOT + STR_SETTINGS_CONTAINER;

		var SELECTOR_SETTINGS_FIELD = STR_DOT + STR_SETTINGS_FIELD;

		var SELECTOR_SETTINGS_ICON_CLOSE = STR_DOT + CSS_SETTINGS_ICON_CLOSE;

		var SELECTOR_SETTINGS_ICON_MAXIMIZE = STR_DOT + CSS_SETTINGS_ICON_MAXIMIZE;

		var SELECTOR_SETTINGS_ICON_MINIMIZE = STR_DOT + CSS_SETTINGS_ICON_MINIMIZE;

		var SELECTOR_SETTINGS_LABEL = STR_DOT + STR_SETTINGS_LABEL;

		var SELECTOR_SETTINGS_LIMIT = STR_DOT + STR_SETTINGS_LIMIT;

		var SELECTOR_SOCIAL_ACTIVITY_ITEM = '.social-activity-item';

		var SELECTOR_SOCIAL_ACTIVITY_ITEM_CONTENT = '.social-activity-item-content';

		var SELECTOR_UPDATE_SOCIAL_ACTIVITY_FORM = 'form.update-socialactivity-form';

		var TPL_BUTTONS = '<div class="' + [CSS_SETTINGS_FIELD_BUTTONS, STR_AUI_HELPER_HIDDEN].join(STR_SPACE) + '">' +
				'<a class="' + [STR_SETTINGS_BUTTON, STR_SETTINGS_BUTTON_MAXIMIZE].join(STR_SPACE) + '" href="javascript:;" title="Maximize">' +
					'<div class="' + [CSS_SETTINGS_ICON, CSS_SETTINGS_ICON_MAXIMIZE].join(STR_SPACE) + '"></div>' +
				'</a>' +
				'<a class="' + [STR_SETTINGS_BUTTON, STR_SETTINGS_BUTTON_MINIMIZE].join(STR_SPACE) + '" href="javascript:;" title="Minimize">' +
					'<div class="' + [CSS_SETTINGS_ICON, CSS_SETTINGS_ICON_MINIMIZE, STR_AUI_HELPER_HIDDEN].join(STR_SPACE) + '"></div>' +
				'</a>' +
				'<a class="' + [STR_SETTINGS_BUTTON, STR_SETTINGS_BUTTON_CLOSE].join(STR_SPACE) + '" href="javascript:;" title="Close">' +
					'<div class="' + [CSS_SETTINGS_ICON, CSS_SETTINGS_ICON_CLOSE].join(STR_SPACE) + '"></div>' +
				'</a>' +
			'</div>';

		var TPL_BUTTON_HOLDER = '<div class="' + [STR_SETTINGS_BUTTON_HOLDER].join(STR_SPACE) + ' aui-button aui-button-submit">' +
				'<span class="aui-button-content">' +
					'<input class="aui-button-input aui-button-input-submit" type="submit" value="Save"/>' +
				'</span>' +
			'</div>';

		var TPL_DROP_BOX = '<ul class="container-drop-box"></ul>';

		var TPL_HEADER_BOX = '<ul class="settings-header"></ul>';

		var TPL_HEADER_HOLDER = '<div class="settings-header-holder"></div>';

		var TPL_SETTINGS_HEADER_LABEL = '<div class="settings-header-label">{text}:</div>';

		var TPL_COMPLEMENTARY_ELEMENT = '<li class="' + STR_COMPLEMENTARY_ELEMENT + '"><div><span>{text}</span></div></li>';

		var TPL_ACTION_VIEW = '<span class="' + [STR_ACTION_VIEW].join(STR_SPACE) + '">{text}</span>';

		var TPL_BOUNDING_BOX_SETTINGS_CONTAINER = '<div class="' + [STR_COMPONENT, STR_SETTINGS_CONTAINER].join(STR_SPACE) + '"></div>';

		var TPL_BOUNDING_BOX_SETTINGS_FIELD = '<li class="' + [STR_COMPONENT, STR_SETTINGS_FIELD, STR_ACTION_FIELD].join(STR_SPACE) + '"></li>';

		var TPL_FIELD_TEXT = '<span class="' + [STR_FIELD, STR_FIELD_TEXT].join(STR_SPACE) + '">{text}</span>';

		var TPL_FIELD_VALUES = '<div class="' + [STR_FIELD_VALUES, STR_AUI_HELPER_HIDDEN].join(STR_SPACE) + '"></div>';

		var TPL_LABEL = '<div class="' + STR_SETTINGS_LABEL + '">{text}</div>';

		var TPL_LIMIT = '<table class="' + [STR_SETTINGS_LIMIT, STR_AUI_HELPER_HIDDEN].join(STR_SPACE) + '"></table>';

		var TPL_LIMIT_CELL_FIELD = '<td></td>';

		var TPL_LIMIT_FIELD = '<tr class="settings-limit-field"></tr>';

		var TPL_OPTION = '<option value="{value}" title="{name}">{name}</option>';

		var TPL_SELECT = '<select  class="settings-field-node"></select>';

		var SocialActivityAdmin = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'socialactivityadmin',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._contentBox = A.one(SELECTOR_SOCIAL_ACTIVITY_ITEM_CONTENT);

						var socialActivityForm = A.one(SELECTOR_UPDATE_SOCIAL_ACTIVITY_FORM);

						var settingsInput = instance._getItemByName(socialActivityForm, STR_JSON_SETTINGS);

						var settings = instance.byId(STR_SETTINGS);

						var socialActivityItems = A.all(SELECTOR_SOCIAL_ACTIVITY_ITEM);

						socialActivityForm.detach(STR_EVENT_SUBMIT);

						socialActivityForm.on(STR_EVENT_SUBMIT, instance._onSocialActivityFormSubmit, instance);

						instance._socialActivityForm = socialActivityForm;

						instance._settingsInput = settingsInput;

						var getSocialActivitySettingMappingCallback = function(result, modelName) {
							if (result.length > 0) {
								config.modelName = modelName;

								config.dataSet = result;

								instance._addSettingsContainer(config);
							}
						};

						settings.delegate(
							'click',
							function(event) {
								var currentTarget = event.currentTarget;

								if (currentTarget.test(SELECTOR_SETTINGS_LABEL)) {
									var settingsContainer = instance._contentBox.one(SELECTOR_SETTINGS_CONTAINER);

									var container = currentTarget.ancestor(SELECTOR_SOCIAL_ACTIVITY_ITEM);

									var modelName = container.attr(STR_DATA_MODEL_NAME);

									if (settingsContainer) {
										settingsContainer.remove();
									}

									socialActivityItems.removeClass(STR_SELECTED);

									container.addClass(STR_SELECTED);

									instance._getSocialActivitySettingMapping(
										themeDisplay.getScopeGroupId(),
										modelName,
										function(result) {
											getSocialActivitySettingMappingCallback(result, modelName);
										}
									);
								}
								else if (currentTarget.test(SELECTOR_FIELD_INPUT_CHOICE)) {
									instance._updateCheckboxStatus(event);
								}
							},
							[SELECTOR_SETTINGS_LABEL, SELECTOR_SOCIAL_ACTIVITY_ITEM, SELECTOR_FIELD_INPUT_CHOICE].join()
						);

						A.some(
							socialActivityItems,
							function(item, index, collection) {
								var modelName = item.attr(STR_DATA_MODEL_NAME);

								var checked = item.one(SELECTOR_FIELD_INPUT_CHOICE).attr('checked');

								if (checked) {
									instance._getSocialActivitySettingMapping(
										themeDisplay.getScopeGroupId(),
										modelName,
										function(result) {
											getSocialActivitySettingMappingCallback(result, modelName);
										}
									);

									item.addClass(STR_SELECTED);
								}

								return checked;
							}
						);
					},

					_addSettingsContainer: function(config) {
						var instance = this;

						instance.settingsContainer = new SettingsContainer(config).render(instance._contentBox);
					},

					_getItemByName: function(currentForm, name, ignoreNamespace) {
						var instance = this;

						var inputName = name;

						if (!ignoreNamespace) {
							inputName = instance.NS + name;
						}

						return currentForm.one('[name=' + inputName + ']');
					},

					_getJsonSettings: function(settingsContainer) {
						var instance = this;

						return {
							actions: settingsContainer.getJSONSettings(),
							modelName: instance._originalConfig.modelName
						};
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

					_onSocialActivityFormSubmit: function(event) {
						var instance = this;

						var form = event.currentTarget;

						event.halt();

						instance._updateSocialActivitySettings(form);

						submitForm(form);
					},

					_updateCheckboxStatus: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var modelName = currentTarget.ancestor(SELECTOR_SOCIAL_ACTIVITY_ITEM).attr(STR_DATA_MODEL_NAME);

						Liferay.Service.Social.SocialActivitySetting.updateActivitySetting(
								{
									groupId: themeDisplay.getScopeGroupId(),
									modelName: modelName,
									value: currentTarget.attr('checked')
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

		var SettingsContainer = A.Component.create(
			{
				NAME: STR_SETTINGS_CONTAINER,

				ATTRS: {

					buttonsNode: {
						valueFn: function() {
							return A.Node.create(TPL_BUTTON_HOLDER);
						}
					},

					modelName: {
						value: STR_BLANK
					},

					id: {
						value: STR_BLANK
					}
				},

				prototype: {
					BOUNDING_TEMPLATE: TPL_BOUNDING_BOX_SETTINGS_CONTAINER,

					initializer: function(config) {
						var instance = this;

						instance.settingsFields = [];
					},

					renderUI: function() {
						var instance = this;

						var contentBox = instance.get(STR_CONTENT_BOX);

						var headerLabelTPL = Lang.sub(
							TPL_SETTINGS_HEADER_LABEL,
							{
								text: Liferay.Language.get('social-activity-setting-header-label')
							}
						);

						var headerLabelNode = Node.create(headerLabelTPL);

						var headerBox = Node.create(TPL_HEADER_BOX);

						var dropBox = Node.create(TPL_DROP_BOX);

						var headerHolder = Node.create(TPL_HEADER_HOLDER);

						var buttonHolder = Node.create(TPL_BUTTON_HOLDER);

						headerHolder.append(headerLabelNode);
						headerHolder.append(headerBox);

						contentBox.append(headerHolder);
						contentBox.append(dropBox);
						contentBox.append(buttonHolder);

						var originalConfig = instance._originalConfig;

						A.each(
							originalConfig.dataSet,
							function(item, index, collection) {
								var strings = originalConfig.strings;

								item.localizedName = Liferay.Language.get(strings[item.modelName][item.languageKey]);

								var settingsField = new SettingsField(item);

								settingsField._counterSettings = originalConfig.counterSettings;

								if (settingsField.get(STR_ACTIVE)) {
									settingsField.render(dropBox);

									settingsField._setDropBoxView(settingsField.get(STR_BOUNDING_BOX));
								}
								else {
									settingsField.render(headerBox);
								}

								instance.settingsFields[settingsField.get(STR_LANGUAGE_KEY)] = settingsField;
							}
						);

						instance.buttonHolder = buttonHolder;
						instance.dropBox = dropBox;
						instance.headerBox = headerBox;
						instance.headerHolder = headerHolder;

						instance._checkComplementaryElement();
					},

					bindUI: function() {
						var instance = this;

						instance.headerBox.delegate('dblclick', instance._putItemToDropBox, SELECTOR_SETTINGS_FIELD, instance);

						instance.dropBox.delegate(
							'click',
							function(event) {
								var currentTarget = event.currentTarget;

								if (currentTarget.test(SELECTOR_SETTINGS_ICON_CLOSE)) {
									instance._putItemToHeader(event);
								}
								else {
									var maximizeItem = currentTarget.test(SELECTOR_SETTINGS_ICON_MAXIMIZE);

									instance._changeLimitState(currentTarget.ancestor(SELECTOR_SETTINGS_FIELD), maximizeItem);
								}
							},
							[SELECTOR_SETTINGS_ICON_CLOSE, SELECTOR_SETTINGS_ICON_MAXIMIZE, SELECTOR_SETTINGS_ICON_MINIMIZE].join()
						);
					},

					getFieldInstance: function(source) {
						var instance = this;

						var languageKey = source.attr(STR_LANGUAGE_KEY);

						return instance.settingsFields[languageKey];
					},

					getJSONSettings: function() {
						var instance = this;

						var jsonSettings = [];

						for (var i in instance.settingsFields) {
							var value = instance.settingsFields[i];

							jsonSettings.push(value.getJSONSettings());
						}

						return jsonSettings;
					},

					getSourceByNode: function(node) {
						var instance = this;

						return node.ancestor('li', true);
					},

					_changeLimitState: function(item, maximized) {
						var instance = this;

						item.one(SELECTOR_SETTINGS_ICON_MINIMIZE).toggle(maximized);
						item.one(SELECTOR_SETTINGS_ICON_MAXIMIZE).toggle(!maximized);
						item.one(SELECTOR_SETTINGS_LIMIT).toggle(maximized);
					},

					_checkComplementaryElement: function() {
						var instance = this;

						var headerBox = instance.headerBox;

						var complementaryElement =  headerBox.one(SELECTOR_COMPLEMENTARY_ELEMENT);

						if (!complementaryElement) {
							var complementaryElementTPL = Lang.sub(
								TPL_COMPLEMENTARY_ELEMENT,
								{
									text: Liferay.Language.get('or').toLowerCase()
								}
							);

							complementaryElement = Node.create(complementaryElementTPL);
						}

						var children = headerBox.get('children');

						if (children.size() > 1) {
							headerBox.insert(complementaryElement, children.last());
						}
					},

					_putItemToDropBox: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						currentTarget.remove();

						var source = instance.getSourceByNode(currentTarget);

						var field = instance.getFieldInstance(source);

						field._setDropBoxView(currentTarget);

						instance.dropBox.append(currentTarget);

						instance._checkComplementaryElement();
					},

					_putItemToHeader: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var item = currentTarget.ancestor(SELECTOR_SETTINGS_FIELD);

						item.one(SELECTOR_SETTINGS_LIMIT).hide();

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

		var SettingsField = A.Component.create(
			{
				NAME: STR_SETTINGS_FIELD,

				ATTRS: {

					active: {
						getter: '_getActive',
						setter: A.DataType.Boolean.parse,
						value: false
					},

					activityType: {
						value: 0
					},

					contributionLimitPeriod: {
						value: 1
					},

					contributionLimitValue: {
						value: 0
					},

					contributionValue: {
						value: 0
					},

					languageKey: {
						value: STR_BLANK
					},

					localizedName: {
						value: STR_BLANK
					},

					participationLimitPeriod: {
						value: 1
					},

					participationLimitValue: {
						value: 0
					},

					participationValue: {
						value: 0
					},

					selected: {
						setter: A.DataType.Boolean.parse,
						value: false
					},

					settingsContainer: {
						value: null
					}
				},

				prototype: {
					BOUNDING_TEMPLATE: TPL_BOUNDING_BOX_SETTINGS_FIELD,

					initializer: function(config) {
						var instance = this;

						instance.set(STR_ACTIVITY_TYPE, config.activityType);
						instance.set(STR_LANGUAGE_KEY, config.languageKey);
						instance.set(STR_LOCALIZED_NAME, config.localizedName);

						var counters = config.counters;

						var valueKey = STR_BLANK;
						var limitValueKey = STR_BLANK;
						var limitPeriodKey = STR_BLANK;

						for (var i = 0; i < counters.length; i++) {
							var action = counters[i];

							valueKey = STR_CONTRIBUTION_VALUE;
							limitValueKey = STR_CONTRIBUTION_LIMIT_VALUE;
							limitPeriodKey = STR_CONTRIBUTION_LIMIT_PERIOD;

							if (action.name == 'participation') {
								valueKey = STR_PARTICIPATION_VALUE;
								limitValueKey = STR_PARTICIPATION_LIMIT_VALUE;
								limitPeriodKey = STR_PARTICIPATION_LIMIT_PERIOD;
							}
							else if (action.name != 'contribution') {
								continue;
							}

							instance.set(valueKey, action.increment);
							instance.set(limitValueKey, action.limitValue);
							instance.set(limitPeriodKey, action.limitPeriod);
						}

						instance._selectInputs = [];
					},

					renderUI: function() {
						var instance = this;

						var buttonsNode = A.Node.create(TPL_BUTTONS);

						var contentBox = instance.get(STR_CONTENT_BOX);

						instance.get(STR_BOUNDING_BOX).attr(STR_LANGUAGE_KEY, instance.get(STR_LANGUAGE_KEY));

						var actionViewTPL = Lang.sub(
							TPL_ACTION_VIEW,
							{
								text: instance.get(STR_LOCALIZED_NAME)
							}
						);

						instance.actionViewNode = Node.create(actionViewTPL);

						instance.valuesNode = instance._getBuiltFieldValues();

						instance.limitNode = instance._getLimitNode(instance._dataSet);

						contentBox.append(instance.actionViewNode);
						contentBox.append(instance.valuesNode);
						contentBox.append(buttonsNode);
						contentBox.append(instance.limitNode);
					},

					getJSONSettings: function() {
						var instance = this;

						return {
							activityType: instance.get(STR_ACTIVITY_TYPE),
							contributionLimitValue: instance.get(STR_CONTRIBUTION_LIMIT_VALUE),
							contributionLimitPeriod: instance.get(STR_CONTRIBUTION_LIMIT_PERIOD),
							contributionValue: instance.get(STR_CONTRIBUTION_VALUE),
							languageKey: instance.get(STR_LANGUAGE_KEY),
							participationLimitValue: instance.get(STR_PARTICIPATION_LIMIT_VALUE),
							participationLimitPeriod: instance.get(STR_PARTICIPATION_LIMIT_PERIOD),
							participationValue: instance.get(STR_PARTICIPATION_VALUE)
						};
					},

					_addOptionToLimitPeriodSelect: function(values, select, actualValue) {
						var instance = this;

						var buffer = [];

						var selectedIndex = null;

						A.each(
							values,
							function(item, index, collection) {
								var i = index + 1;

								buffer.push(instance._getOptionHTML(item, index + 1));

								if (index + 1 == actualValue) {
									selectedIndex = index;
								}
							}
						);

						select.append(buffer.join(STR_BLANK));

						select.attr('selectedIndex', selectedIndex);
					},

					_addOptionToSelect: function(values, select, actualValue) {
						var instance = this;

						var buffer = [];

						var selectedIndex = null;

						A.each(
							values,
							function(item, index, collection) {
								buffer.push(instance._getOptionHTML(item, item));

								if (item == actualValue) {
									selectedIndex = index;
								}
							}
						);

						select.append(buffer.join(STR_BLANK));

						select.attr('selectedIndex', selectedIndex);
					},

					_getActive: function() {
						var instance = this;

						return (instance.get(STR_CONTRIBUTION_VALUE) > 0 || instance.get(STR_PARTICIPATION_VALUE) > 0);
					},

					_getBuiltFieldValues: function() {
						var instance = this;

						var languageKey = instance.get(STR_LANGUAGE_KEY);

						var fieldValues = Node.create(TPL_FIELD_VALUES);

						var labelTPL = Lang.sub(
							TPL_LABEL,
							{
								text: instance.get(STR_LOCALIZED_NAME)
							}
						);

						var labelNode = Node.create(labelTPL);

						var firstText = Node.create(instance._getFieldTextHTML(Liferay.Language.get('social-activity-setting-firsttext')));
						var secondText = Node.create(instance._getFieldTextHTML(Liferay.Language.get('social-activity-setting-secondtext')));
						var thirdText = Node.create(instance._getFieldTextHTML(Liferay.Language.get('social-activity-setting-thirdtext')));

						var participationValuesNode = Node.create(TPL_SELECT);
						var contributionValuesNode = Node.create(TPL_SELECT);

						var counterSettings = instance._counterSettings;

						participationValuesNode.attr('id', languageKey + '_' + STR_PARTICIPATION_VALUE);

						participationValuesNode.on('change', instance._selectOnChange, instance);

						contributionValuesNode.attr('id', languageKey + '_' + STR_CONTRIBUTION_VALUE);

						contributionValuesNode.on('change', instance._selectOnChange, instance);

						instance._addOptionToSelect(
							counterSettings.contributionValues,
							contributionValuesNode,
							instance.get(STR_CONTRIBUTION_VALUE)
						);

						instance._addOptionToSelect(
							counterSettings.participationValues,
							participationValuesNode,
							instance.get(STR_PARTICIPATION_VALUE)
						);

						fieldValues.append(labelNode);
						fieldValues.append(firstText);
						fieldValues.append(participationValuesNode);
						fieldValues.append(secondText);
						fieldValues.append(contributionValuesNode);
						fieldValues.append(thirdText);

						instance._selectInputs.push(participationValuesNode, contributionValuesNode);

						return fieldValues;
					},

					_getFieldTextHTML: function(text) {
						var instance = this;

						return Lang.sub(
							TPL_FIELD_TEXT,
							{
								text: text
							}
						);
					},

					_getLimitFieldNode: function(value) {
						var instance = this;

						var languageKey = instance.get(STR_LANGUAGE_KEY);

						var limitField = Node.create(TPL_LIMIT_FIELD);

						var limitCellField = Node.create(TPL_LIMIT_CELL_FIELD);

						var firstText = Liferay.Language.get('social-activity-setting-limit-firsttext');

						var limitTypeValuesNode = Node.create(TPL_SELECT);

						var limitValuesNode = Node.create(TPL_SELECT);

						var languagePrefix = languageKey + '_';

						var firstTextType = STR_PARTICIPATION;
						var limitPeriodKey = STR_PARTICIPATION_LIMIT_PERIOD;
						var limitValueKey = STR_PARTICIPATION_LIMIT_VALUE;

						var counterSettings = instance._counterSettings;

						var limitValues = counterSettings.participationLimitValues;

						if (value == STR_CONTRIBUTION) {
							firstTextType = STR_CONTRIBUTION;
							limitPeriodKey = STR_CONTRIBUTION_LIMIT_PERIOD;
							limitValueKey = STR_CONTRIBUTION_LIMIT_VALUE;

							limitValues = counterSettings.contributionLimitValues;
						}

						limitValuesNode.attr('id', languagePrefix + limitValueKey);
						limitTypeValuesNode.attr('id', languagePrefix + limitPeriodKey);

						instance._addOptionToLimitPeriodSelect(
							COL_LIMIT_TYPE,
							limitTypeValuesNode,
							instance.get(limitPeriodKey)
						);

						instance._addOptionToSelect(
							limitValues,
							limitValuesNode,
							instance.get(limitValueKey)
						);

						limitValuesNode.on('change', instance._selectOnChange, instance);
						limitTypeValuesNode.on('change', instance._selectOnChange, instance);

						instance._selectInputs.push(limitValuesNode, limitTypeValuesNode);

						var firstTextTPL = instance._getFieldTextHTML(Lang.sub(firstText, [firstTextType]));

						limitField.append(limitCellField.append(firstTextTPL));

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

						limitNode.append(instance._getLimitFieldNode(STR_CONTRIBUTION));
						limitNode.append(instance._getLimitFieldNode(STR_PARTICIPATION));

						return limitNode;
					},

					_getOptionHTML: function(name, value) {
						var instance = this;

						var data = {
							name: name,
							value: value
						};

						return Lang.sub(TPL_OPTION, data);
					},

					_selectOnChange: function(event) {
						var instance = this;

						var selectId = event.currentTarget.attr('id');

						var languageKey = instance.get(STR_LANGUAGE_KEY);

						selectId = selectId.replace(languageKey + '_', STR_BLANK);

						instance.set(selectId, event.currentTarget.val());
					},

					_setDropBoxView: function(item) {
						var instance = this;

						instance._toggleView(item, false);
					},

					_setHeaderView: function(item) {
						var instance = this;

						instance._toggleView(item, true);
					},

					_setToDefaultValue: function() {
						var instance = this;

						instance.reset(STR_CONTRIBUTION_VALUE);
						instance.reset(STR_CONTRIBUTION_LIMIT_VALUE);
						instance.reset(STR_CONTRIBUTION_LIMIT_PERIOD);
						instance.reset(STR_PARTICIPATION_VALUE);
						instance.reset(STR_PARTICIPATION_LIMIT_VALUE);
						instance.reset(STR_PARTICIPATION_LIMIT_PERIOD);
					},

					_toggleView: function(item, active) {
						var instance = this;

						var endCssClass = STR_CONTENT_FIELD;
						var startCssClass = STR_ACTION_FIELD;

						if (active) {
							endCssClass = STR_ACTION_FIELD;
							startCssClass = STR_CONTENT_FIELD;
						}

						item.replaceClass(startCssClass, endCssClass);

						item.one(STR_DOT + STR_ACTION_VIEW).toggle(active);
						item.one(STR_DOT + STR_FIELD_VALUES).toggle(!active);
						item.one(STR_DOT + CSS_SETTINGS_FIELD_BUTTONS).toggle(!active);

						instance.set(STR_ACTIVE, active);
					}
				}
			}
		);

		var SocialActivity = Liferay.namespace('Portlet.SocialActivity');

		SocialActivity.Admin = SocialActivityAdmin;
		SocialActivity.SettingsContainer = SettingsContainer;
		SocialActivity.SettingsField = SettingsField;
	},
	'',
	{
		requires: ['aui-base', 'aui-datatype', 'liferay-portlet-base']
	}
);