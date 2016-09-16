AUI.add(
	'liferay-ddl-form-builder-field-settings-form',
	function(A) {
		var TPL_SETTINGS_FORM = '<form action="javascript:;"></form>';

		var TPL_SETTINGS_TOGGLER = '<button class="btn settings-toggler" type="button"><span class="settings-toggle-label"></span><span class="settings-toggle-icon"></span></button>';

		var RendererUtil = Liferay.DDM.Renderer.Util;

		var SoyTemplateUtil = Liferay.DDL.SoyTemplateUtil;

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

						var evaluator = instance.get('evaluator');

						instance._eventHandlers.push(
							evaluator.after('evaluationStarted', A.bind('_saveSettings', instance)),
							instance.after('render', instance._afterSettingsFormRender),
							instance.on('*:addOption', instance._afterAddOption),
							instance.on('*:removeOption', instance.alignModal)
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

					_afterAddOption: function(event) {
						var instance = this;

						var optionsField = event.target;

						var field = instance.get('field');

						var builder = field.get('builder');

						var definition = builder.get('definition');

						var searchResults = RendererUtil.searchFieldsByKey(definition, field.get('fieldName'), 'fieldName');

						if (searchResults.length) {
							var definitionOptions = searchResults[0].options || [];

							optionsField.eachOption(
								function(option) {
									var existingOption = definitionOptions.find(
										function(definitionOption) {
											return definitionOption.value === option.get('key');
										}
									);

									option.set('keyInputEnabled', !existingOption);
								}
							);
						}
					},

					_afterLabelFieldNormalizeKey: function(key) {
						var instance = this;

						return new A.Do.AlterReturn(null, instance.get('field').generateFieldName(A.Do.originalRetVal));
					},

					_afterSettingsFormRender: function() {
						var instance = this;

						var editModeValue = instance.get('editMode');

						var labelField = instance.getField('label');

						var nameField = instance.getField('name');

						(new A.EventHandle(instance._fieldEventHandlers)).detach();

						instance._fieldEventHandlers.push(
							labelField.bindContainerEvent('keyup', A.bind('_onKeyUpKeyValueInput', instance, labelField), '.key-value-input'),
							labelField.on('keyChange', A.bind('_onLabelFieldKeyChange', instance)),
							labelField.after(A.bind('_afterLabelFieldNormalizeKey', instance), labelField, 'normalizeKey')
						);

						labelField.set('key', nameField.getValue());
						labelField.set('keyInputEnabled', editModeValue);
						labelField.set('generationLocked', !editModeValue);

						if (instance.get('field').get('type') === 'text') {
							instance._createAutocompleteButton();
							instance._createAutocompleteContainer();
						}
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

						var optionsContainer = instance.getField('options').get('container');

						var tabView = instance.getTabView();

						emptyPageNode.setHTML(instance._getAutocompleteContainerTemplate());

						tabView.get('panelNode').append(emptyPageNode);

						sidebarBody.one('.autocomplete-body').append(dataSourceTypeContainer);
						sidebarBody.one('.autocomplete-body').append(ddmDataProviderInstanceIdContainer);
						sidebarBody.one('.autocomplete-body').append(optionsContainer);

						sidebarBody.one('.autocomplete-header-back').on('click', A.bind('_onClickAutocompleteHeaderBack', instance));
						tabView.after('selectionChange', A.bind('_afterTabViewSelectionChange', instance));
					},

					_createModeToggler: function() {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						var settingsTogglerNode = A.Node.create(TPL_SETTINGS_TOGGLER);

						advancedSettingsNode.placeBefore(settingsTogglerNode);

						settingsTogglerNode.on('click', A.bind('_onClickModeToggler', instance));

						instance.settingsTogglerNode = settingsTogglerNode;
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

					_onKeyUpKeyValueInput: function() {
						var instance = this;

						instance._saveSettings();
					},

					_onLabelFieldKeyChange: function(event) {
						var instance = this;

						var nameField = instance.getField('name');

						nameField.setValue(event.newVal);
					},

					_onNameChange: function(event) {
						var instance = this;

						var labelField = instance.getField('label');

						labelField.set('key', event.newVal);
					},

					_onSubmitForm: function(event) {
						var instance = this;

						event.preventDefault();
					},

					_saveSettings: function() {
						var instance = this;

						var field = instance.get('field');

						field.saveSettings(instance);
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
		requires: ['liferay-ddl-soy-template-util', 'liferay-ddm-form-renderer', 'liferay-ddm-form-renderer-util', 'liferay-form']
	}
);