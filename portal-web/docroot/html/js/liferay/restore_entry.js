AUI.add(
	'liferay-restore-entry',
	function(A) {
		var Lang = A.Lang;

		var isString = Lang.isString;

		var RESPONSE_DATA = 'responseData';

		var STR_RESTORE_ENTRY_URL = 'restoreEntryURL';

		var RestoreEntry = A.Component.create(
			{
				ATTRS: {
					duplicateEntryURL: {
						validator: isString
					},

					namespace: {
						validator: isString
					},

					overrideMessage:{
						validator:isString
					},

					renameMessage:{
						validator: isString
					},

					restoreEntryURL: {
						validator: isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'restoreentry',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._eventCheckEntry = instance.ns('checkEntry');

						instance._hrefFm = A.one('#hrefFm');

						var eventHandles = [
							Liferay.on(instance._eventCheckEntry, instance._checkEntry, instance)
						];

						instance._eventHandles = eventHandles;
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');
					},

					_afterCheckEntryFailure: function(event, uri) {
						var instance = this;

						submitForm(instance._hrefFm, uri);
					},

					_afterCheckEntrySuccess: function(event, id, xhr, uri) {
						var instance = this;

						var responseData = event.currentTarget.get(RESPONSE_DATA);

						if (responseData.success) {
							submitForm(instance._hrefFm, uri);
						}
						else {
							var data = {
								duplicateEntryId: responseData.duplicateEntryId,
								oldName: responseData.oldName,
								overrideMessage: instance.get('overrideMessage'),
								renameMessage: instance.get('renameMessage'),
								trashEntryId: responseData.trashEntryId
							};

							instance._showPopup(data, instance.get('duplicateEntryURL'));
						}
					},

					_afterPopupCheckEntryFailure: function(event, id, xhr, form) {
						var instance = this;

						submitForm(form);
					},

					_afterPopupCheckEntrySuccess: function(event, id, xhr, form) {
						var instance = this;

						var responseData = event.currentTarget.get(RESPONSE_DATA);

						if (responseData.success) {
							submitForm(form);
						}
						else {
							var newName = instance.byId('newName');
							var messageContainer = instance.byId('messageContainer');

							messageContainer.html(Lang.sub(Liferay.Language.get('an-entry-with-name-x-already-exists'), [newName.val()]));
						}
					},

					_checkEntry: function(event) {
						var instance = this;

						var uri = event.uri;

						A.io.request(
							instance.get(STR_RESTORE_ENTRY_URL),
							{
								after: {
									failure: A.rbind(instance._afterCheckEntryFailure, instance),
									success: A.rbind(instance._afterCheckEntrySuccess, instance)
								},
								arguments: uri,
								data: {
									trashEntryId: event.trashEntryId
								},
								dataType: 'json'
							}
						);
					},

					_getPopup: function() {
						var instance = this;

						var popup = instance._popup;

						if (!popup) {
							popup = new A.Dialog(
								{
									align: Liferay.Util.Window.ALIGN_CENTER,
									cssClass: 'trash-restore-popup',
									modal: true,
									title: Liferay.Language.get('warning'),
									width: 500
								}
							).plug(
								A.Plugin.IO,
								{
									after: {
										success: A.bind(instance._initializeRestorePopup, instance)
									},
									autoLoad: false
								}
							).render();

							instance._popup = popup;
						}

						return popup;
					},

					_initializeRestorePopup: function() {
						var instance = this;

						var restoreTrashEntryFm = instance.byId('restoreTrashEntryFm');

						restoreTrashEntryFm.on('submit', instance._onRestoreTrashEntryFmSubmit, instance, restoreTrashEntryFm);

						var closeButton = restoreTrashEntryFm.one('.aui-button-input-cancel');

						closeButton.on('click', instance._popup.hide, instance._popup);

						var rename = instance.byId('rename');
						var newName = instance.byId('newName');

						rename.on('click', Liferay.Util.focusFormField, Liferay.Util, newName);

						newName.on(
							'focus',
							function(event) {
								rename.attr('checked', true);
							}
						);
					},

					_onRestoreTrashEntryFmSubmit: function(event, form) {
						var instance = this;

						var newName = instance.byId('newName');
						var override = instance.byId('override');
						var trashEntryId = instance.byId('trashEntryId');

						if (override.attr('checked') || (!override.attr('checked') && !newName.val())) {
							submitForm(form);
						}
						else {
							A.io.request(
								instance.get(STR_RESTORE_ENTRY_URL),
								{
									after: {
										failure: A.rbind(instance._afterPopupCheckEntryFailure, instance),
										success: A.rbind(instance._afterPopupCheckEntrySuccess, instance)
									},
									arguments: form,
									data: {
										trashEntryId: trashEntryId.val(),
										newName: newName.val()
									},
									dataType: 'json'
								}
							);
						}
					},

					_showPopup: function(data, uri) {
						var instance = this;

						var popup = instance._getPopup();

						popup.show();

						var popupIO = popup.io;

						popupIO.set('data', data);
						popupIO.set('uri', uri);

						popupIO.start();
					}
				}
			}
		);

		Liferay.RestoreEntry = RestoreEntry;
	},
	'',
	{
		requires: ['aui-dialog', 'aui-io-request', 'liferay-portlet-base']
	}
);