AUI.add(
	'liferay-ddl-form-builder-field-settings-modal',
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

		var FormBuilderFieldSettingsModal = A.Component.create(
			{
				ATTRS: {
					portletNamespace: {
					}
				},

				EXTENDS: A.FormBuilderSettingsModal,

				NAME: 'liferay-ddl-form-builder-field-settings-modal',

				prototype: {
					TPL_FIELD_SETTINGS_HEAD_CONTENT: '<h4 class="' + CSS_MODAL_TITLE + '"></h4>',

					initializer: function() {
						var instance = this;

						instance.on('save', instance._onSave);
					},

					hide: function() {
						var instance = this;

						FormBuilderFieldSettingsModal.superclass.hide.apply(instance, arguments);

						instance.fire('hide');
					},

					show: function() {
						var instance = this;

						FormBuilderFieldSettingsModal.superclass.show.apply(instance, arguments);

						instance._getBodyNode().append(A.Node.create(TPL_CONFIRMATION_MESSAGE).toggle(false));

						var field = instance._fieldBeingEdited;

						var settingsForm = field.get('settingsForm');

						var container = settingsForm.get('container');

						container.appendTo(instance._getBodyNode());

						instance._showDefaultToolbar();

						settingsForm.render();

						var modal = instance._modal;

						modal.syncHeight();
						modal.align();

						instance._previousSettings = JSON.stringify(field.getSettings());
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
												click: A.bind('_showFormContainer', instance)
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
								}
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

						var field = instance._fieldBeingEdited;

						var settingsForm = field.get('settingsForm');

						settingsForm.clearValidationStatus();
						settingsForm.hideErrorMessages();

						settingsForm.submit();
					},

					_onModalVisibleChange: function(event) {
						var instance = this;

						if (!event.newVal && !instance._confirmationToolbarVisible) {
							var field = instance._fieldBeingEdited;

							var settings = JSON.stringify(field.getSettings());

							if (instance._previousSettings !== settings) {

								instance._showConfirmationMessage();

								event.preventDefault();
							}
						}
					},

					_onSave: function(event) {
						var instance = this;

						instance._previousSettings = JSON.stringify(event.field.getSettings());
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

						var field = instance._fieldBeingEdited;

						var footerNode = instance._getFooterNode();

						var label = Liferay.Language.get('save');

						if (field.isNew()) {
							label = Liferay.Language.get('add');
						}

						footerNode.one('.' + CSS_FIELD_SETTINGS_SAVE).set('innerHTML', label);

						instance._toggleConfirmationToolbar(false);
						instance._toggleDefaultToolbar(true);
					},

					_showFormContainer: function() {
						var instance = this;

						instance._toggleFormContainer(true);
						instance._toggleConfirmationMessage(false);
						instance._showDefaultToolbar();
						instance._modal.align();
					},

					_toggleConfirmationMessage: function(display) {
						var instance = this;

						var bodyNode = instance._getBodyNode();

						bodyNode.one('.' + CSS_FIELD_SETTINGS_CONFIRMATION_MESSAGE).toggle(display);
					},

					_toggleConfirmationToolbar: function(display) {
						var instance = this;

						var footerNode = instance._getFooterNode();

						footerNode.one('.' + CSS_FIELD_SETTINGS_YES).toggle(display);
						footerNode.one('.' + CSS_FIELD_SETTINGS_NO).toggle(display);

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

						var field = instance._fieldBeingEdited;

						var container = field.get('settingsForm').get('container');

						container.toggle(display);
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