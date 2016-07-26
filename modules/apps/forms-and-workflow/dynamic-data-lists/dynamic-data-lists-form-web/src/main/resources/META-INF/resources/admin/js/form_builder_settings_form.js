AUI.add(
	'liferay-ddl-form-builder-settings-form',
	function(A) {
		var CSS_FIELD_SETTINGS_SAVE = A.getClassName('lfr', 'ddl', 'field', 'settings', 'save');

		var TPL_SETTINGS_FORM = '<form action="javascript:;"></form>';

		var TPL_SETTINGS_TOGGLER = '<button class="btn settings-toggler" type="button"><span class="settings-toggle-label"></span><span class="settings-toggle-icon"></span></button>';

		var TPL_SUBMIT_BUTTON = '<button class="hide" type="submit" />';

		var RendererUtil = Liferay.DDM.Renderer.Util;

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

				NAME: 'liferay-ddl-form-builder-settings-form',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.after('render', instance._afterSettingsFormRender),
							instance.on('*:addOption', instance._afterAddOption),
							instance.on('*:removeOption', instance.alignModal)
						);

						instance._fieldEventHandlers = [];
					},

					alignModal: function() {
						var instance = this;

						var field = instance.get('field');

						var settingsModal = field.getSettingsModal();

						settingsModal.align();
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

					getSubmitButton: function() {
						var instance = this;

						var footerNode = instance._getModalStdModeNode(A.WidgetStdMod.FOOTER);

						return footerNode.one('.' + CSS_FIELD_SETTINGS_SAVE);
					},

					submit: function(callback) {
						var instance = this;

						instance.validateSettings(
							function(hasErrors) {
								if (!hasErrors) {
									var field = instance.get('field');

									var settingsModal = field.getSettingsModal();

									field.saveSettings(instance);

									settingsModal.fire(
										'save',
										{
											field: field
										}
									);

									settingsModal.hide();
								}

								if (callback) {
									callback.apply(instance, arguments);
								}
							}
						);
					},

					validateSettings: function(callback) {
						var instance = this;

						instance.validate(
							function(hasErrors) {
								hasErrors = instance._handleValidationResponse(hasErrors);

								if (callback) {
									callback.call(instance, hasErrors);
								}
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

						instance.alignModal();
					},

					_afterLabelFieldNormalizeKey: function(key) {
						var instance = this;

						return new A.Do.AlterReturn(null, instance._generateFieldName(A.Do.originalRetVal));
					},

					_afterSettingsFormRender: function() {
						var instance = this;

						var container = instance.get('container');

						container.append(TPL_SUBMIT_BUTTON);

						instance._createModeToggler();
						instance._syncModeToggler();

						var labelField = instance.getField('label');
						var nameField = instance.getField('name');

						(new A.EventHandle(instance._fieldEventHandlers)).detach();

						instance._fieldEventHandlers.push(
							labelField.on('keyChange', A.bind('_onLabelFieldKeyChange', instance)),
							labelField.after(A.bind('_afterLabelFieldNormalizeKey', instance), labelField, 'normalizeKey')
						);

						labelField.set('key', labelField.normalizeKey(nameField.getValue()));
						labelField.set('keyInputEnabled', instance.get('editMode'));
					},

					_createModeToggler: function() {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						var settingsTogglerNode = A.Node.create(TPL_SETTINGS_TOGGLER);

						advancedSettingsNode.placeBefore(settingsTogglerNode);

						settingsTogglerNode.on('click', A.bind('_onClickModeToggler', instance));

						instance.settingsTogglerNode = settingsTogglerNode;
					},

					_generateFieldName: function(key) {
						var instance = this;

						var counter = 0;

						var field = instance.get('field');

						var builder = field.get('builder');

						var existingField;

						if (!key) {
							key = field.get('type');
						}

						var name = key;

						if (name) {
							do {
								if (counter > 0) {
									name = key + counter;
								}

								existingField = builder.getField(name);

								counter++;
							}
							while (existingField !== undefined && existingField !== field);
						}

						return name;
					},

					_getModalStdModeNode: function(mode) {
						var instance = this;

						var field = instance.get('field');

						var settingsModal = field.getSettingsModal();

						return settingsModal._modal.getStdModNode(mode);
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

					_onClickModeToggler: function(event) {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						advancedSettingsNode.toggleClass('active');

						instance.alignModal();

						instance._syncModeToggler();
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

						instance.submit();
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
		requires: ['liferay-ddm-form-renderer', 'liferay-ddm-form-renderer-util', 'liferay-form']
	}
);