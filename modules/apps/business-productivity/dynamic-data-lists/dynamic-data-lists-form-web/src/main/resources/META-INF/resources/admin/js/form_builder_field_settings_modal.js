AUI.add(
	'liferay-ddl-form-builder-field-settings-modal',
	function(A) {
		var CSS_BTN_PRIMARY = A.getClassName('btn', 'primary');

		var CSS_FIELD_SETTINGS = A.getClassName('form', 'builder', 'field', 'settings');

		var CSS_FIELD_SETTINGS_CANCEL = A.getClassName('form', 'builder', 'field', 'settings', 'cancel');

		var CSS_FIELD_SETTINGS_SAVE = A.getClassName('form', 'builder', 'field', 'settings', 'save');

		var CSS_MODAL_TITLE = A.getClassName('modal', 'title');

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

					show: function() {
						var instance = this;

						FormBuilderFieldSettingsModal.superclass.show.apply(instance, arguments);

						instance._modal.syncHeight();
					},

					_create: function() {
						var instance = this;

						var modal = new Liferay.DDL.FormBuilderModal(
							{
								after: {
									visibleChange: A.bind(instance._afterModalVisibleChange, instance)
								},
								cssClass: CSS_FIELD_SETTINGS,
								draggable: false,
								dynamicContentHeight: true,
								headerContent: instance.TPL_FIELD_SETTINGS_HEAD_CONTENT,
								modal: true,
								portletNamespace: instance.get('portletNamespace'),
								resizable: false,
								topFixed: true
							}
						).render();

						modal.addToolbar(
							[
								{
									cssClass: [CSS_BTN_PRIMARY, CSS_FIELD_SETTINGS_SAVE].join(' '),
									label: Liferay.Language.get('save'),
									on: {
										click: A.bind(instance._save, instance)
									}
								},
								{
									cssClass: CSS_FIELD_SETTINGS_CANCEL,
									label: Liferay.Language.get('cancel'),
									on: {
										click: A.bind(instance.hide, instance)
									}
								}
							],
							'footer'
						);

						instance._modal = modal;
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