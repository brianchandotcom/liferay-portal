AUI.add(
	'liferay-ddl-form-builder-field-support',
	function(A) {
		var Lang = A.Lang;

		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var FormBuilderUtil = Liferay.DDL.FormBuilderUtil;

		var CSS_FIELD = A.getClassName('form', 'builder', 'field');

		var CSS_FIELD_CONTENT_TOOLBAR = A.getClassName('form', 'builder', 'field', 'content', 'toolbar');

		var CSS_FIELD_REPEATABLE_TOOLBAR = A.getClassName('lfr', 'ddm', 'form', 'field', 'repeatable', 'toolbar');

		var CSS_FIELD_TOOLBAR_CONTAINER = A.getClassName('form', 'builder', 'field', 'toolbar', 'container');

		var CSS_FIELD_SETTINGS_CONFIRMATION_MESSAGE = A.getClassName('lfr', 'ddl', 'field', 'settings', 'confirmation', 'message');

		var CSS_FIELD_SETTINGS_MODAL = A.getClassName('lfr', 'ddl', 'field', 'settings', 'modal');

		var CSS_FIELD_SETTINGS_SAVE = A.getClassName('lfr', 'ddl', 'field', 'settings', 'save');

		var CSS_FORM_GROUP = A.getClassName('form', 'group');

		var TPL_CONFIRMATION_MESSAGE = '<div class="' + CSS_FIELD_SETTINGS_CONFIRMATION_MESSAGE + '">' + Liferay.Language.get('cancel-without-saving') + '</div>';

		var FormBuilderSettingsSupport = function() {
		};

		FormBuilderSettingsSupport.ATTRS = {
			builder: {
				value: null
			},

			dataProviders: {
			},

			content: {
				getter: function() {
					var instance = this;

					return instance.get('container');
				}
			},

			settingsForm: {
				valueFn: '_valueSettingsForm'
			},

			toolbarFooter: {
				getter: '_manipulateFooterToolbar',
				lazyAdd: true,
				setter: '_manipulateFooterToolbar'
			}
		};

		FormBuilderSettingsSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._confirmationMessage = A.Node.create(TPL_CONFIRMATION_MESSAGE);

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

			validate: Lang.emptyFn,

			_bindModalUI: function(settingsModal) {
				var instance = this;

				instance._modalEventHandlers = [
					settingsModal.on('xyChange', instance._onModalXYChange),
					settingsModal.on('visibleChange', A.bind('_onModalVisibleChange', instance))
				];
			},

			_manipulateFooterToolbar: function(toolbarItem) {
				var instance = this;

				var toolbar = [
					{
						cssClass: ['btn-lg btn-primary', CSS_FIELD_SETTINGS_SAVE].join(' '),
						label: Liferay.Language.get('save'),
						on: {
							click: A.bind('_onClickModalSave', instance)
						}
					},
					{
						cssClass: 'btn-lg btn-link',
						label: Liferay.Language.get('cancel'),
						on: {
							click: A.bind('_onClickModalClose', instance)
						}
					}
				];

				if (toolbarItem) {
					A.Array.each(
						toolbar,
						function(item, index) {
							if (toolbarItem[index]) {
								toolbar[index] = A.merge(item, toolbarItem[index]);
							}
						}
					);
				}

				return toolbar;
			},

			_onClickModalClose: function() {
				var instance = this;

				instance.hideSettingsModal();
			},

			_onClickModalSave: function() {
				var instance = this;

				var settingsForm = instance.get('settingsForm');

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

					(new A.EventHandle(instance._modalEventHandlers)).detach();
				}
			},

			_onModalXYChange: function(event) {
				event.newVal = FormBuilderUtil.normalizeModalXY(event.newVal);
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

				var portletNode = A.one('#p_p_id' + instance.get('portletNamespace'));

				settingsModal.set('centered', portletNode);
				settingsModal.set('zIndex', Liferay.zIndex.OVERLAY);

				instance._bindModalUI(settingsModal);

				instance._previousSettings = JSON.stringify(instance.getSettings());

				instance._showDefaultToolbar();

				var closeButton = settingsModal.toolbars.header.item(0);

				closeButton.set('labelHTML', Liferay.Util.getLexiconIconTpl('times'));
			},

			_showConfirmationToolbar: function() {
				var instance = this;

				var settingsModal = instance.getSettingsModal()._modal;

				settingsModal.addToolbar(
					[
						{
							cssClass: 'btn-lg btn-primary',
							label: Liferay.Language.get('yes'),
							on: {
								click: function() {
									instance._confirmationMessage.remove();

									instance.hideSettingsModal();
								}
							}
						},
						{
							cssClass: 'btn-lg btn-link',
							label: Liferay.Language.get('no'),
							on: {
								click: function() {
									instance._confirmationMessage.remove();

									instance._showDefaultToolbar();
								}
							}
						}
					],
					'footer'
				);

				var footerNode = settingsModal.getStdModNode(A.WidgetStdMod.FOOTER);

				footerNode.prepend(instance._confirmationMessage);
			},

			_showDefaultToolbar: function(label) {
				var instance = this;

				var settingsModal = instance.getSettingsModal()._modal;

				settingsModal.addToolbar(instance.get('toolbarFooter'), 'footer');
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