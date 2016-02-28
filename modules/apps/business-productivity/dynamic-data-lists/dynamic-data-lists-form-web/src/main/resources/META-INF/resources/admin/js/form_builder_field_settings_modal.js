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
				ATTRS: {},

				EXTENDS: A.FormBuilderSettingsModal,

				NAME: 'liferay-ddl-form-builder-field-settings-modal',

				prototype: {
					TPL_FIELD_SETTINGS_HEAD_CONTENT: '<h4 class="' + CSS_MODAL_TITLE + '"></h4>',

					show: function() {
						var instance = this;

						FormBuilderFieldSettingsModal.superclass.show.apply(instance, arguments);

						this._modal.syncHeight();
					},

					_create: function() {
						this._modal = new Liferay.DDL.FormBuilderModal(
							{
								centered: true,
								cssClass: CSS_FIELD_SETTINGS,
								draggable: false,
								dynamicContentHeight: true,
								headerContent: this.TPL_FIELD_SETTINGS_HEAD_CONTENT,
								modal: true,
								resizable: false,
								topFixed: true,
								zIndex: 4
							}
						).render();

						this._modal.addToolbar(
							[
								{
									cssClass: [CSS_BTN_PRIMARY, CSS_FIELD_SETTINGS_SAVE].join(' '),
									label: 'Save',
									on: {
										click: A.bind(this._save, this)
									},
									render: true
								},
								{
									cssClass: CSS_FIELD_SETTINGS_CANCEL,
									label: 'Cancel',
									on: {
										click: A.bind(this.hide, this)
									},
									render: true
								}
							],
							'footer'
						);

						this._modal.after('visibleChange', A.bind(this._afterModalVisibleChange, this));
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