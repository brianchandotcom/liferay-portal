AUI.add(
	'liferay-ddl-form-builder-settings-modal',
	function(A) {
		var CSS_BTN_LG = A.getClassName('btn', 'lg');

		var CSS_BTN_LINK = A.getClassName('btn', 'link');

		var CSS_BTN_PRIMARY = A.getClassName('btn', 'primary');

		var CSS_CLOSE = A.getClassName('close');

		var CSS_FIELD_SETTINGS = A.getClassName('form', 'builder', 'field', 'settings');

		var CSS_FIELD_SETTINGS_CANCEL = A.getClassName('lfr', 'ddl', 'field', 'settings', 'cancel');

		var CSS_FIELD_SETTINGS_CONFIRMATION_MESSAGE = A.getClassName('lfr', 'ddl', 'field', 'settings', 'confirmation', 'message');

		var CSS_FIELD_SETTINGS_MODAL = A.getClassName('lfr', 'ddl', 'field', 'settings', 'modal');

		var CSS_FIELD_SETTINGS_NO = A.getClassName('lfr', 'ddl', 'field', 'settings', 'no');

		var CSS_FIELD_SETTINGS_SAVE = A.getClassName('lfr', 'ddl', 'field', 'settings', 'save');

		var CSS_FIELD_SETTINGS_YES = A.getClassName('lfr', 'ddl', 'field', 'settings', 'yes');

		var CSS_MODAL_TITLE = A.getClassName('modal', 'title');

		var TPL_CONFIRMATION_MESSAGE = '<p class="' + CSS_FIELD_SETTINGS_CONFIRMATION_MESSAGE + ' text-muted">' + Liferay.Language.get('cancel-without-saving') + '</p>';

		var TPL_LOADING = '<div class="loading-animation"></div>';

		var FormBuilderFieldSettingsModal = A.Component.create(
			{
				ATTRS: {
					field: {
					},

					portletNamespace: {
					},

					settingsForm: {
					}
				},

				EXTENDS: A.FormBuilderSettingsModal,

				NAME: 'liferay-ddl-form-builder-settings-modal',

				prototype: {
					TPL_FIELD_SETTINGS_HEAD_CONTENT: '<h4 class="' + CSS_MODAL_TITLE + '"></h4>',

					initializer: function() {
						var instance = this;

						instance._create();

						instance._confirmationMessageNode = A.Node.create(TPL_CONFIRMATION_MESSAGE);
						instance._loadingNode = A.Node.create(TPL_LOADING);

						var bodyNode = instance._getBodyNode();

						bodyNode.append(instance._confirmationMessageNode);
						bodyNode.append(instance._loadingNode);

						instance.on('save', instance._onSave);
					},

					align: function() {
						var instance = this;

						instance._modal.syncHeight();
						instance._modal.align();
					},

					hide: function() {
						var instance = this;

						FormBuilderFieldSettingsModal.superclass.hide.apply(instance, arguments);

						instance.fire('hide');
					},

					setTitle: function(title) {
						var instance = this;

						var boundingBox = instance._modal.get('boundingBox');

						boundingBox.one('.' + CSS_MODAL_TITLE).set('text', title);
					},

					show: function(field, title) {
						var instance = this;

						instance.set('field', field);

						instance._toggleConfirmationMessage(false);
						instance._toggleLoadingAnimation(true);

						var footerNode = instance._getFooterNode();

						footerNode.hide();

						instance._modal.show();
						instance.align();

						instance.setTitle(title);

						field.loadSettingsForm().then(
							function(settingsForm) {
								var container = settingsForm.get('container');

								instance.set('settingsForm', settingsForm);

								container.appendTo(instance._getBodyNode());

								settingsForm.after('render', instance._afterRenderSettingsForm, instance);
								settingsForm.render();

								var settings = field.getSettings(settingsForm);

								instance._previousSettings = JSON.stringify(settings);
							}
						);
					},

					_afterRenderSettingsForm: function(event) {
						var instance = this;

						var footerNode = instance._getFooterNode();

						var settingsForm = event.target;

						instance._toggleLoadingAnimation(true);
						instance._toggleFormContainer(false);

						settingsForm.evaluate(
							function() {
								settingsForm.hideErrorMessages();

								instance._showSettingsForm();

								footerNode.show();

								var labelField = settingsForm.getField('label');

								labelField.focus();

								instance.align();
							}
						);
					},

					_create: function() {
						var instance = this;

						var modal = new Liferay.DDL.FormBuilderModal(
							{
								cssClass: CSS_FIELD_SETTINGS,
								draggable: false,
								dynamicContentHeight: true,
								headerContent: instance.TPL_FIELD_SETTINGS_HEAD_CONTENT,
								modal: true,
								on: {
									visibleChange: A.bind(instance._onModalVisibleChange, instance)
								},
								portletNamespace: instance.get('portletNamespace'),
								resizable: false,
								toolbars: {
									footer: [
										{
											cssClass: [CSS_BTN_LG, CSS_BTN_PRIMARY, CSS_FIELD_SETTINGS_SAVE].join(' '),
											labelHTML: Liferay.Language.get('save'),
											on: {
												click: A.bind('_onClickModalSave', instance)
											}
										},
										{
											cssClass: [CSS_BTN_LG, CSS_BTN_LINK, CSS_FIELD_SETTINGS_CANCEL].join(' '),
											labelHTML: Liferay.Language.get('cancel'),
											on: {
												click: A.bind('hide', instance)
											}
										},
										{
											cssClass: [CSS_BTN_LG, CSS_BTN_PRIMARY, CSS_FIELD_SETTINGS_YES].join(' '),
											labelHTML: Liferay.Language.get('yes-cancel'),
											on: {
												click: A.bind('hide', instance)
											}
										},
										{
											cssClass: [CSS_BTN_LG, CSS_BTN_LINK, CSS_FIELD_SETTINGS_NO].join(' '),
											labelHTML: Liferay.Language.get('dismiss'),
											on: {
												click: A.bind('_showSettingsForm', instance)
											}
										}
									],
									header: [
										{
											cssClass: CSS_CLOSE,
											labelHTML: Liferay.Util.getLexiconIconTpl('times'),
											on: {
												click: A.bind('hide', instance)
											}
										}
									]
								},
								visible: false
							}
						).render();

						instance._modal = modal;

						modal.get('boundingBox').addClass(CSS_FIELD_SETTINGS_MODAL);
					},

					_getBodyNode: function() {
						var instance = this;

						return instance._modal.getStdModNode(A.WidgetStdMod.BODY);
					},

					_getFooterNode: function() {
						var instance = this;

						return instance._modal.getStdModNode(A.WidgetStdMod.FOOTER);
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

						var settingsForm = instance.get('settingsForm');

						if (!event.newVal && !instance._confirmationToolbarVisible) {
							var field = instance.get('field');

							var settings = JSON.stringify(field.getSettings(settingsForm));

							if (instance._previousSettings !== settings) {
								instance._showConfirmationMessage();

								event.preventDefault();
							}
							else {
								settingsForm.destroy();
							}
						}
						else if (!event.newVal) {
							settingsForm.destroy();
						}
					},

					_onSave: function(event) {
						var instance = this;

						var settingsForm = instance.get('settingsForm');

						instance._previousSettings = JSON.stringify(event.field.getSettings(settingsForm));
					},

					_showConfirmationMessage: function() {
						var instance = this;

						instance._showConfirmationToolbar();
						instance._toggleConfirmationMessage(true);
						instance._toggleFormContainer(false);

						instance._modal.align();
					},

					_showConfirmationToolbar: function() {
						var instance = this;

						instance._toggleConfirmationToolbar(true);
						instance._toggleDefaultToolbar(false);
					},

					_showDefaultToolbar: function() {
						var instance = this;

						var field = instance.get('field');

						var label = Liferay.Language.get('save');

						if (field.isAdding()) {
							label = Liferay.Language.get('add');
						}

						var footerToolbar = instance._modal.getToolbar('footer');

						footerToolbar.item(0).set('label', label);

						instance._toggleConfirmationToolbar(false);
						instance._toggleDefaultToolbar(true);
					},

					_showSettingsForm: function() {
						var instance = this;

						instance._toggleFormContainer(true);
						instance._toggleConfirmationMessage(false);
						instance._toggleLoadingAnimation(false);
						instance._showDefaultToolbar();
					},

					_toggleConfirmationMessage: function(display) {
						var instance = this;

						instance._confirmationMessageNode.toggle(display);
					},

					_toggleConfirmationToolbar: function(display) {
						var instance = this;

						var footerNode = instance._getFooterNode();

						var yesButton = footerNode.one('.' + CSS_FIELD_SETTINGS_YES);

						footerNode.one('.' + CSS_FIELD_SETTINGS_NO).toggle(display);

						yesButton.toggle(display);

						if (display) {
							yesButton.focus();
						}

						instance._confirmationToolbarVisible = !!display;
					},

					_toggleDefaultToolbar: function(display) {
						var instance = this;

						var footerNode = instance._getFooterNode();

						footerNode.one('.' + CSS_FIELD_SETTINGS_SAVE).toggle(display);
						footerNode.one('.' + CSS_FIELD_SETTINGS_CANCEL).toggle(display);
					},

					_toggleFormContainer: function(display) {
						var instance = this;

						var settingsForm = instance.get('settingsForm');

						var container = settingsForm.get('container');

						if (display) {
							container.setStyle('height', 'auto');
						}
						else {
							container.setStyle('height', '0');
						}
					},

					_toggleLoadingAnimation: function(display) {
						var instance = this;

						instance._loadingNode.toggle(display);
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderFieldSettingsModal = FormBuilderFieldSettingsModal;
	},
	'',
	{
		requires: ['form-builder-settings-modal', 'liferay-ddl-form-builder-modal']
	}
);