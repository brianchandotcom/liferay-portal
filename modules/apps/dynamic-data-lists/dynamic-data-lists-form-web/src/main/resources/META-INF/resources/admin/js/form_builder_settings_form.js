AUI.add(
	'liferay-ddl-form-builder-settings-form',
	function(A) {
		var CSS_FIELD_SETTINGS_SAVE = A.getClassName('form', 'builder', 'field', 'settings', 'save');

		var TPL_MODE_TOGGLER = '<a class="settings-toggler" href="javascript:;"></a>';

		var TPL_SETTINGS_FORM = '<form action="javascript:;"></form>';

		var TPL_SUBMIT_BUTTON = '<button class="hide" type="submit" />';

		var FormBuilderSettingsForm = A.Component.create(
			{
				ATTRS: {
					field: {
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Form,

				NAME: 'liferay-ddl-form-builder-settings-form',

				prototype: {
					initializer: function() {
						var instance = this;

						var labelField = instance.getField('label');

						instance._eventHandlers.push(
							instance.after('render', instance._afterSettingsFormRender),
							labelField.on('keyChange', A.bind('_onLabelFieldKeyChange', instance)),
							labelField.on(A.bind('_onLabelFieldNormalizeKey', instance), labelField, 'normalizeKey')
						);
					},

					submit: function(callback) {
						var instance = this;

						instance.validateSettings(
							function(hasErrors) {
								if (!hasErrors) {
									var field = instance.get('field');

									var settingsModal = field.getSettingsModal();

									field.saveSettings();

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

					_afterSettingsFormRender: function() {
						var instance = this;

						var container = instance.get('container');

						container.append(TPL_SUBMIT_BUTTON);

						instance._createModeToggler();

						instance._syncModeToggler();

						var formName = A.guid();

						container.attr('id', formName);
						container.attr('name', formName);

						Liferay.Form.register(
							{
								id: formName
							}
						);

						var bodyNode = instance._getModalStdModeNode(A.WidgetStdMod.BODY);

						container.appendTo(bodyNode);

						instance._eventHandlers.push(
							container.on('submit', A.bind('_onDOMSubmitForm', instance))
						);

						var labelField = instance.getField('label');
						var nameField = instance.getField('name');

						labelField.set('key', nameField.getValue());
					},

					_createModeToggler: function() {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						var modeToggler = A.Node.create(TPL_MODE_TOGGLER);

						advancedSettingsNode.placeBefore(modeToggler);

						modeToggler.on('click', A.bind('_onClickModeToggler', instance));

						instance.modeToggler = modeToggler;
					},

					_generateFieldName: function(key) {
						var instance = this;

						var counter = 0;

						var field = instance.get('field');

						var builder = field.get('builder');

						var existingField;

						var name = key;

						do {
							if (counter > 0) {
								name = key + counter;
							}

							existingField = builder.getField(name);

							counter++;
						}
						while (existingField !== undefined && existingField !== field);

						return name;
					},

					_getModalStdModeNode: function(mode) {
						var instance = this;

						var field = instance.get('field');

						var settingsModal = field.getSettingsModal();

						return settingsModal._modal.getStdModNode(mode);
					},

					_getSubmitButton: function() {
						var instance = this;

						var footerNode = instance._getModalStdModeNode(A.WidgetStdMod.FOOTER);

						return footerNode.one('.' + CSS_FIELD_SETTINGS_SAVE);
					},

					_handleValidationResponse: function(hasErrors) {
						var instance = this;

						var field = instance.get('field');

						var builder = field.get('builder');

						var nameField = instance.getField('name');

						var sameNameField = builder.getField(nameField.getValue());

						if (!!sameNameField && sameNameField !== field) {
							nameField.showErrorMessage(Liferay.Language.get('field-name-is-already-in-use'));
							nameField.showValidationStatus();

							nameField.focus();

							hasErrors = true;
						}

						return hasErrors;
					},

					_onClickModeToggler: function(event) {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						advancedSettingsNode.toggleClass('active');

						var field = instance.get('field');

						var settingsModal = field.getSettingsModal();

						settingsModal._modal.align();

						instance._syncModeToggler();
					},

					_onDOMSubmitForm: function(event) {
						var instance = this;

						event.preventDefault();

						instance.submit();
					},

					_onLabelFieldKeyChange: function(event) {
						var instance = this;

						var nameField = instance.getField('name');

						nameField.setValue(event.newVal);
					},

					_onLabelFieldNormalizeKey: function(key) {
						var instance = this;

						return new A.Do.AlterArgs(null, [instance._generateFieldName(key)]);
					},

					_syncModeToggler: function() {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						var modeToggler = instance.modeToggler;

						if (advancedSettingsNode.hasClass('active')) {
							modeToggler.addClass('active');
							modeToggler.html(Liferay.Language.get('hide-options'));
						}
						else {
							modeToggler.removeClass('active');
							modeToggler.html(Liferay.Language.get('show-more-options'));
						}
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
		requires: ['liferay-ddm-form-renderer', 'liferay-form']
	}
);