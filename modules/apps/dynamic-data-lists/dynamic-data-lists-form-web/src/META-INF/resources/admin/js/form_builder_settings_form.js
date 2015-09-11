AUI.add(
	'liferay-ddl-form-builder-settings-form',
	function(A) {
		var CSS_FIELD_SETTINGS_SAVE = A.getClassName('form', 'builder', 'field', 'settings', 'save');

		var TPL_SETTINGS_FORM = '<form action="javascript:;"></form>';

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

						instance._eventHandlers.push(
							instance.after('render', instance._afterSettingsFormRender)
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

						container.append('<button class="hide" type="submit" />');

						instance._renderModeToggler();

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

						var nameSettingsField = instance.getField('name');

						var sameNameField = builder.getField(nameSettingsField.getValue());

						if (!!sameNameField && sameNameField !== field) {
							nameSettingsField.addValidationMessage(Liferay.Language.get('field-name-already-in-use'));
							nameSettingsField.showValidationStatus();

							nameSettingsField.focus();

							hasErrors = true;
						}

						return hasErrors;
					},

					_onClickModeToggler: function() {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						var field = instance.get('field');

						var settingsModal = field.getSettingsModal();

						advancedSettingsNode.toggleClass('active');

						settingsModal._modal.align();

						instance._syncModeToggler();
					},

					_onDOMSubmitForm: function(event) {
						var instance = this;

						event.preventDefault();

						instance.submit();
					},

					_renderModeToggler: function() {
						var instance = this;

						var basicSettingsNode = instance.getPageNode(1);

						instance.modeToggler = A.Node.create('<a class="settings-toggler" href="javascript:;"></a>');

						instance.modeToggler.on('click', A.bind(instance._onClickModeToggler, instance));

						basicSettingsNode.insert(instance.modeToggler, 'after');

						instance._syncModeToggler();
					},

					_syncModeToggler: function() {
						var instance = this;

						var advancedSettingsNode = instance.getPageNode(2);

						if (advancedSettingsNode.hasClass('active')) {
							instance.modeToggler.html(Liferay.Language.get('hide-advanced-options'));
						}
						else {
							instance.modeToggler.html(Liferay.Language.get('show-advanced-options'));
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
		requires: ['liferay-ddm-form-renderer']
	}
);