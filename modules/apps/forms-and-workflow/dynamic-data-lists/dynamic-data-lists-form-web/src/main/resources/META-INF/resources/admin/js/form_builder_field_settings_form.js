AUI.add(
	'liferay-ddl-form-builder-field-settings-form',
	function(A) {
		var TPL_SETTINGS_FORM = '<form action="javascript:;"></form>';

		var TPL_SETTINGS_TOGGLER = '<button class="btn settings-toggler" type="button"><span class="settings-toggle-label"></span><span class="settings-toggle-icon"></span></button>';

		var SoyTemplateUtil = Liferay.DDM.SoyTemplateUtil;

		var FormBuilderSettingsForm = A.Component.create(
			{
				ATTRS: {
					editMode: {
						value: false
					},

					field: {
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Form,

				NAME: 'liferay-ddl-form-builder-field-settings-form',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.after('render', instance._afterSettingsFormRender),
							instance.on('*:addOption', instance._afterAddOption),
							instance.on('*:removeOption', instance._afterRemoveOption)
						);

						instance._fieldEventHandlers = [];
					},

					getEvaluationPayload: function() {
						var instance = this;

						var field = instance.get('field');

						return A.merge(
							FormBuilderSettingsForm.superclass.getEvaluationPayload.apply(instance, arguments),
							{
								type: field.get('type')
							}
						);
					},

					showLoadingFeedback: function() {
						var instance = this;

						FormBuilderSettingsForm.superclass.showLoadingFeedback.apply(instance, arguments);

						instance.get('alert').hide();
					},

					_afterAddOption: function(event) {
						var instance = this;

						var optionsField = event.target;

						event.option.transient = true;

						optionsField.eachOption(
							function(option) {
								option.set('keyInputEnabled', option.transient);
							}
						);
					},

					_afterFieldValueChange: function(event) {
						var instance = this;

						var field = event.target;
						var formBuilderField = instance.get('field');

						var localizedValue = field.get('context.localizedValue');

						if (localizedValue) {
							var locale = formBuilderField.get('locale');

							localizedValue[locale] = event.newVal;
						}

						formBuilderField.set('context.settingsContext', instance.get('context'));

						instance._saveSettings();
					},

					_afterLabelFieldNormalizeKey: function(key) {
						var instance = this;

						return new A.Do.AlterReturn(null, instance.get('field').generateFieldName(A.Do.originalRetVal));
					},

					_afterRemoveOption: function(event) {
						var instance = this;

						var formBuilderField = instance.get('field');

						formBuilderField.set('context.settingsContext', instance.get('context'));

						formBuilderField.saveSettings();
					},

					_afterSettingsFormRender: function() {
						var instance = this;

						instance._createSettingsFormEventHandlers();
						instance._createAutocompleteElements();

						instance._updateFormFieldProperties();

						instance._eventHandlers.push(
							instance.after('*:valueChange', instance._afterFieldValueChange)
						);
					},

					_afterTabViewSelectionChange: function() {
						var instance = this;

						if (instance.get('container').one('.tab-pane.active')) {
							instance._showLastActivatedPage();
							instance._hideAutoCompletePage();
						}
					},

					_createAutocompleteButton: function() {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						advancedSettingsNode.append(instance._getAutocompleteCardActionTemplate());

						advancedSettingsNode.one('.autocomplete-action-panel').on('click', A.bind('_onClickAutocompleteButton', instance));
					},

					_createAutocompleteContainer: function() {
						var instance = this;

						var emptyPageRenderer = SoyTemplateUtil.getTemplateRenderer('ddm.tabbed_form_frame');

						var emptyPageNode = A.Node.create(emptyPageRenderer());

						var sidebarBody = A.one('.sidebar-body');

						var dataSourceTypeContainer = instance.getField('dataSourceType').get('container');

						var ddmDataProviderInstanceIdContainer = instance.getField('ddmDataProviderInstanceId').get('container');

						var ddmDataProviderInstanceOutputContainer = instance.getField('ddmDataProviderInstanceOutput').get('container');

						var optionsContainer = instance.getField('options').get('container');

						var tabView = instance.getTabView();

						emptyPageNode.setHTML(instance._getAutocompleteContainerTemplate());

						tabView.get('panelNode').append(emptyPageNode);

						var autocompleteBody = sidebarBody.one('.autocomplete-body');

						autocompleteBody.append(dataSourceTypeContainer);
						autocompleteBody.append(ddmDataProviderInstanceIdContainer);
						autocompleteBody.append(ddmDataProviderInstanceOutputContainer);
						autocompleteBody.append(optionsContainer);

						sidebarBody.one('.autocomplete-header-back').on('click', A.bind('_onClickAutocompleteHeaderBack', instance));
						tabView.after('selectionChange', A.bind('_afterTabViewSelectionChange', instance));
					},

					_createAutocompleteElements: function() {
						var instance = this;

						var formBuilderFieldType = instance._getFormBuilderFieldType();

						if (formBuilderFieldType === 'text') {
							instance._createAutocompleteButton();
							instance._createAutocompleteContainer();
						}
					},

					_createModeToggler: function() {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						var settingsTogglerNode = A.Node.create(TPL_SETTINGS_TOGGLER);

						advancedSettingsNode.placeBefore(settingsTogglerNode);

						settingsTogglerNode.on('click', A.bind('_onClickModeToggler', instance));

						instance.settingsTogglerNode = settingsTogglerNode;
					},

					_createSettingsFormEventHandlers: function() {
						var instance = this;

						var labelField = instance.getField('label');

						var fieldEventHandlers = new A.EventHandle(instance._fieldEventHandlers);

						fieldEventHandlers.detach();

						instance._fieldEventHandlers.push(
							labelField.on('keyChange', A.bind('_onLabelFieldChange', instance)),
							labelField.after(A.bind('_afterLabelFieldNormalizeKey', instance), labelField, 'normalizeKey')
						);
					},

					_getAutocompleteCardActionTemplate: function() {
						var instance = this;

						var actionPanelRenderer = SoyTemplateUtil.getTemplateRenderer('ddl.autocomplete.actionPanel');

						return actionPanelRenderer(
							{
								addAutoCompleteButton: Liferay.Util.getLexiconIconTpl('angle-right'),
								label: Liferay.Language.get('autocomplete')
							}
						);
					},

					_getAutocompleteContainerTemplate: function() {
						var instance = this;

						var autocompleteContainerRenderer = SoyTemplateUtil.getTemplateRenderer('ddl.autocomplete.container');

						var autocompleteContainer = autocompleteContainerRenderer(
							{
								backButton: Liferay.Util.getLexiconIconTpl('angle-left', 'icon-monospaced'),
								label: Liferay.Language.get('autocomplete')
							}
						);

						return autocompleteContainer;
					},

					_getFormBuilderFieldType: function() {
						var instance = this;

						var formBuilderField = instance.get('field');

						return formBuilderField.get('type');
					},

					_handleValidationResponse: function(hasErrors) {
						var instance = this;

						var field = instance.get('field');

						var builder = field.get('builder');

						var nameField = instance.getField('name');

						var sameNameField = builder.getField(nameField.getValue());

						if (!!sameNameField && sameNameField !== field) {
							nameField.showErrorMessage(Liferay.Language.get('field-name-is-already-in-use'));

							hasErrors = true;
						}

						return hasErrors;
					},

					_hideActivatedPage: function() {
						var instance = this;

						instance.get('container').one('.tab-pane.active').hide();
					},

					_hideAutoCompletePage: function() {
						var instance = this;

						A.one('.sidebar-body').one('.autocomplete-container').ancestor().removeClass('active');
					},

					_onClickAutocompleteButton: function() {
						var instance = this;

						instance._hideActivatedPage();

						A.one('.sidebar-body').one('.autocomplete-container').ancestor().addClass('active');
					},

					_onClickAutocompleteHeaderBack: function() {
						var instance = this;

						instance._showLastActivatedPage();

						instance._hideAutoCompletePage();
					},

					_onClickModeToggler: function(event) {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						advancedSettingsNode.toggleClass('active');

						instance._syncModeToggler();
					},

					_onLabelFieldChange: function(event) {
						var instance = this;

						var nameField = instance.getField('name');

						var formBuilderField = instance.get('field');

						var locale = formBuilderField.get('locale');

						if (locale === themeDisplay.getDefaultLanguageId()) {
							nameField.set('value', event.newVal);
							formBuilderField.set('context.fieldName', event.newVal);
						}

						instance._saveSettings();
					},

					_onSubmitForm: function(event) {
						var instance = this;

						event.preventDefault();
					},

					_saveSettings: function() {
						var instance = this;

						var field = instance.get('field');

						field.saveSettings();
					},

					_showLastActivatedPage: function() {
						var instance = this;

						instance.get('container').one('.tab-pane.active').show();
					},

					_syncModeToggler: function() {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						var settingsTogglerNode = instance.settingsTogglerNode;

						var settingsTogglerIconNode = settingsTogglerNode.one('.settings-toggle-icon');
						var settingsTogglerLabelNode = settingsTogglerNode.one('.settings-toggle-label');

						var active = advancedSettingsNode.hasClass('active');

						if (active) {
							settingsTogglerIconNode.html(Liferay.Util.getLexiconIconTpl('angle-up'));
							settingsTogglerLabelNode.html(Liferay.Language.get('hide-options'));
						}
						else {
							settingsTogglerIconNode.html(Liferay.Util.getLexiconIconTpl('angle-down'));
							settingsTogglerLabelNode.html(Liferay.Language.get('show-more-options'));
						}

						settingsTogglerNode.toggleClass('active', active);
					},

					_updateFormFieldProperties: function() {
						var instance = this;

						instance._updateTypeField();
						instance._updateLabelField();
						instance._updateOptionsFields();
					},

					_updateLabelField: function() {
						var instance = this;

						var editMode = instance.get('editMode');

						var labelField = instance.getField('label');
						var nameField = instance.getField('name');

						var name = nameField.getValue();

						if (!name) {
							var formBuilderField = instance.get('field');

							labelField.set('key', formBuilderField.generateFieldName(''));
						}
						else {
							labelField.set('key', nameField.getValue());
						}

						labelField.set('keyInputEnabled', !editMode);
						labelField.set('generationLocked', editMode);
					},

					_updateOptionsField: function(optionsField) {
						var instance = this;

						var editMode = instance.get('editMode');

						if (editMode) {
							optionsField.set('editable', false);
						}
					},

					_updateOptionsFields: function() {
						var instance = this;

						instance.eachField(
							function(field) {
								var type = field.get('type');

								if (type === 'options') {
									instance._updateOptionsField(field);
								}
							}
						);
					},

					_updateTypeField: function() {
						var instance = this;

						var typeField = instance.getField('type');

						typeField.set('value', instance._getFormBuilderFieldType());
					},

					_valueContainer: function() {
						var instance = this;

						return A.Node.create(TPL_SETTINGS_FORM);
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderSettingsForm = FormBuilderSettingsForm;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer', 'liferay-ddm-form-renderer-util', 'liferay-ddm-soy-template-util', 'liferay-form']
	}
);