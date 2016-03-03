AUI.add(
	'liferay-ddl-form-builder-field-support',
	function(A) {
		var Lang = A.Lang;

		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var CSS_FIELD = A.getClassName('form', 'builder', 'field');

		var CSS_FIELD_CONTENT_TOOLBAR = A.getClassName('form', 'builder', 'field', 'content', 'toolbar');

		var CSS_FIELD_REPEATABLE_TOOLBAR = A.getClassName('lfr', 'ddm', 'form', 'field', 'repeatable', 'toolbar');

		var CSS_FIELD_TOOLBAR_CONTAINER = A.getClassName('form', 'builder', 'field', 'toolbar', 'container');

		var CSS_FIELD_SETTINGS_CONFIRMATION_MESSAGE = A.getClassName('lfr', 'ddl', 'field', 'settings', 'confirmation', 'message');

		var CSS_FIELD_SETTINGS_MODAL = A.getClassName('lfr', 'ddl', 'field', 'settings', 'modal');

		var CSS_FIELD_SETTINGS_CANCEL = A.getClassName('lfr', 'ddl', 'field', 'settings', 'cancel');

		var CSS_FIELD_SETTINGS_NO = A.getClassName('lfr', 'ddl', 'field', 'settings', 'no');

		var CSS_FIELD_SETTINGS_SAVE = A.getClassName('lfr', 'ddl', 'field', 'settings', 'save');

		var CSS_FIELD_SETTINGS_YES = A.getClassName('lfr', 'ddl', 'field', 'settings', 'yes');

		var CSS_FORM_GROUP = A.getClassName('form', 'group');

		var TPL_CONFIRMATION_MESSAGE = '<div class="' + CSS_FIELD_SETTINGS_CONFIRMATION_MESSAGE + '">' + Liferay.Language.get('cancel-without-saving') + '</div>';

		var FormBuilderSettingsSupport = function() {
		};

		FormBuilderSettingsSupport.ATTRS = {
			builder: {
				value: null
			},

			content: {
				getter: function() {
					var instance = this;

					return instance.get('container');
				}
			},

			dataProviders: {
			},

			settingsForm: {
				valueFn: '_valueSettingsForm'
			}
		};

		FormBuilderSettingsSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._footerToolbar = Liferay.Language.get('save');

				instance._eventHandlers.push(
					instance.after(instance._renderFormBuilderField, instance, 'render')
				);
			},

			destructor: function() {
				var instance = this;

				instance.get('settingsForm').destroy();
			},

			getSettings: function() {
				var instance = this;

				var settings = {};

				var settingsForm = instance.get('settingsForm');

				var fieldSettingsJSON = settingsForm.toJSON();

				fieldSettingsJSON.fieldValues.forEach(
					function(item) {
						settings[item.name] = item.value;
					}
				);

				settings.type = instance.get('type');

				return settings;
			},

			getSettingsModal: function() {
				var instance = this;

				var builder = instance.get('builder');

				var settingsModal = builder._fieldSettingsModal;

				return settingsModal;
			},

			hideSettingsModal: function() {
				var instance = this;

				var settingsModal = instance.getSettingsModal();

				settingsModal.hide();
			},

			renderSettingsPanel: function() {
				var instance = this;

				var settingsForm = instance.get('settingsForm');

				instance._updateSettingsFormValues();

				settingsForm.render();

				instance._renderSettingsModal();
			},

			saveSettings: function() {
				var instance = this;

				var builder = instance.get('builder');

				instance.setAttrs(instance.getSettings());

				instance.render();

				builder.appendChild(instance);

				instance.fire(
					'field:saveSettings',
					{
						field: instance
					}
				);

				instance._previousSettings = JSON.stringify(instance.getSettings());
			},

			setPrimaryButtonLabel: function(label) {
				var instance = this;

				instance._footerToolbar = label;
			},

			validate: Lang.emptyFn,

			_appendFooterToolbar: function() {
				var instance = this;

				var settingsModal = instance.getSettingsModal()._modal;

				var footerNode = settingsModal.getStdModNode(A.WidgetStdMod.FOOTER);

				if (!footerNode.one('.' + CSS_FIELD_SETTINGS_CONFIRMATION_MESSAGE)) {
					settingsModal.addToolbar(
						[
							{
								cssClass: ['btn-lg btn-primary', CSS_FIELD_SETTINGS_SAVE].join(' '),
								labelHTML: Liferay.Language.get('save'),
								on: {
									click: A.bind('_onClickModalSave', instance)
								}
							},
							{
								cssClass: ['btn-lg btn-link', CSS_FIELD_SETTINGS_CANCEL].join(' '),
								labelHTML: Liferay.Language.get('cancel'),
								on: {
									click: A.bind('_onClickModalClose', instance)
								}
							},
							{
								cssClass: ['btn-lg btn-primary', CSS_FIELD_SETTINGS_YES].join(' '),
								labelHTML: Liferay.Language.get('yes'),
								on: {
									click: function() {
										instance.hideSettingsModal();
									}
								}
							},
							{
								cssClass: ['btn-lg btn-link', CSS_FIELD_SETTINGS_NO].join(' '),
								labelHTML: Liferay.Language.get('no'),
								on: {
									click: function() {
										instance._showDefaultToolbar();
									}
								}
							}
						],
						'footer'
					);

					footerNode.prepend(A.Node.create(TPL_CONFIRMATION_MESSAGE));
				}
			},

			_bindModalUI: function(settingsModal) {
				var instance = this;

				instance._modalEventHandlers = [
					settingsModal.on('visibleChange', A.bind('_onModalVisibleChange', instance))
				];
			},

			_onClickModalClose: function() {
				var instance = this;

				instance.hideSettingsModal();
			},

			_onClickModalSave: function() {
				var instance = this;

				var settingsModal = instance.getSettingsModal();

				var field = settingsModal._fieldBeingEdited;

				var settingsForm = field.get('settingsForm');

				settingsForm.clearValidationStatus();
				settingsForm.hideErrorMessages();

				settingsForm.submit();
			},

			_onModalVisibleChange: function(event) {
				var instance = this;

				if (!event.newVal) {
					var settings = JSON.stringify(instance.getSettings());

					if (instance._previousSettings !== settings) {
						instance._showConfirmationToolbar();

						event.preventDefault();
					}
					else {
						(new A.EventHandle(instance._modalEventHandlers)).detach();	
					}
				}
			},

			_renderFormBuilderField: function() {
				var instance = this;

				var container = instance.get('container');

				container.addClass(CSS_FIELD);

				container.setData('field-instance', instance);

				var wrapper = container.one('.' + CSS_FORM_GROUP);

				wrapper.append('<div class="' + CSS_FIELD_TOOLBAR_CONTAINER + '"></div>');

				wrapper.addClass(CSS_FIELD_CONTENT_TOOLBAR);

				if (instance.get('repeatable')) {
					var toolbar = container.one('.' + CSS_FIELD_REPEATABLE_TOOLBAR);

					if (toolbar) {
						toolbar.hide();
					}
				}
			},

			_renderSettingsModal: function() {
				var instance = this;

				var settingsModal = instance.getSettingsModal()._modal;

				var settingsModalBoundingBox = settingsModal.get('boundingBox');

				settingsModalBoundingBox.addClass(CSS_FIELD_SETTINGS_MODAL);

				instance._bindModalUI(settingsModal);

				instance._previousSettings = JSON.stringify(instance.getSettings());

				instance._appendFooterToolbar();

				instance._showDefaultToolbar(instance._footerToolbar);

				var closeButton = settingsModal.toolbars.header.item(0);

				closeButton.set('labelHTML', Liferay.Util.getLexiconIconTpl('times'));
			},

			_showConfirmationToolbar: function() {
				var instance = this;

				instance._toggleDefaultToolbar(false);
				instance._toggleConfirmationToolbar(true);
			},

			_showDefaultToolbar: function(label) {
				var instance = this;

				var footerNode = instance.getSettingsModal()._modal.getStdModNode(A.WidgetStdMod.FOOTER);

				footerNode.one('.' + CSS_FIELD_SETTINGS_SAVE).set('innerHTML', label || Liferay.Language.get('save'));

				instance._toggleConfirmationToolbar(false);
				instance._toggleDefaultToolbar(true);
			},

			_toggleConfirmationToolbar: function(display) {
				var instance = this;

				var settingsModal = instance.getSettingsModal()._modal;

				var footerNode = settingsModal.getStdModNode(A.WidgetStdMod.FOOTER);

				footerNode.one('.' + CSS_FIELD_SETTINGS_CONFIRMATION_MESSAGE).toggle(display);
				footerNode.one('.' + CSS_FIELD_SETTINGS_YES).toggle(display);
				footerNode.one('.' + CSS_FIELD_SETTINGS_NO).toggle(display);
			},

			_toggleDefaultToolbar: function(display) {
				var instance = this;

				var settingsModal = instance.getSettingsModal()._modal;

				var footerNode = settingsModal.getStdModNode(A.WidgetStdMod.FOOTER);

				footerNode.one('.' + CSS_FIELD_SETTINGS_SAVE).toggle(display);
				footerNode.one('.' + CSS_FIELD_SETTINGS_CANCEL).toggle(display);
			},

			_updateSettingsFormValues: function() {
				var instance = this;

				var settingsForm = instance.get('settingsForm');

				settingsForm.get('fields').forEach(
					function(item, index) {
						item.set('value', instance.get(item.get('name')));
					}
				);
			},

			_valueSettingsForm: function() {
				var instance = this;

				var fieldType = FieldTypes.get(instance.get('type'));

				return new Liferay.DDL.FormBuilderSettingsForm(
					{
						dataProviders: instance.get('dataProviders'),
						definition: fieldType.get('settings'),
						field: instance,
						layout: fieldType.get('settingsLayout'),
						portletNamespace: instance.get('portletNamespace'),
						templateNamespace: 'ddm.settings_form'
					}
				);
			}
		};

		Liferay.namespace('DDL').FormBuilderSettingsSupport = FormBuilderSettingsSupport;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-settings-form', 'liferay-ddl-form-builder-util']
	}
);