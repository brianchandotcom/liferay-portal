 AUI.add(
	'liferay-trash',
	function(A) {
		var Lang = A.Lang;

		var RESPONSE_DATA = 'responseData';

		var STR_CHECKENTRY_URL = 'checkEntryURL';

		var Trash = A.Component.create(
			{
				ATTRS: {
					checkEntryURL: {
						validator: Lang.isString
					},

					namespace: {
						validator: Lang.isString
					},

					restoreEntryURL: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'trash',

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

					_afterCheckEntrySuccess: function(ioRequest, event) {
						var instance = this;

						var responseData = ioRequest.get(RESPONSE_DATA);

						if (responseData.success) {
							submitForm(instance._hrefFm, uri);
						}
						else {
							var data = {
								duplicateEntryId: responseData.duplicateEntryId,
								oldName: responseData.oldName,
								trashEntryId: responseData.trashEntryId
							};

							instance._showPopup(data, instance.get('restoreEntryURL'));
						}
					},

					_afterPopUpCheckEntryFailure: function(event, form) {
						var instance = this;

						submitForm(form);
					},

					_afterPopUpCheckEntrySuccess: function(ioRequest, form, event) {
						var instance = this;

						var responseData = ioRequest.get(RESPONSE_DATA);

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

						var ioRequest = A.io.request(
							instance.get(STR_CHECKENTRY_URL),
							{
								autoLoad: false,
								data: {
									entryId: event.entryId
								},
								dataType: 'json'
							}
						);

						ioRequest.after('success', A.bind(instance._afterCheckEntrySuccess, instance, ioRequest));
						ioRequest.after('failure', A.rbind(instance._afterCheckEntryFailure, instance, event.uri));

						ioRequest.start();
					},

					_initializeRestorePopup: function () {
						var instance = this;

						var restoreTrashEntryFm = instance.byId('restoreTrashEntryFm');

						restoreTrashEntryFm.on('submit', instance._onRestoreTrashEntryFmSubmit, instance, restoreTrashEntryFm);

						var closeButton = restoreTrashEntryFm.one('.aui-button-input-cancel');

						closeButton.on('click', instance._popUp.hide, instance._popUp);

						var rename = instance.byId('rename');

						var newName = instance.byId('newName');

						rename.on('click', Liferay.Util.focusFormField, Liferay.Util, newName);

						newName.on(
							'focus',
							function(event) {
								rename.set('checked', true);
							}
						);
					},

					_getPopup: function() {
						var instance = this;

						if (!instance._popUp) {
							instance._popUp = new A.Dialog(
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
									autoLoad: false
								}
							).render();

							instance._popUp.io.after('success', instance._initializeRestorePopup, instance);
						}

						return instance._popUp;
					},

					_onRestoreTrashEntryFmSubmit: function(event, form) {
						var instance = this;

						var newName = instance.byId('newName');

						var override = instance.byId('override');

						var trashEntryId = instance.byId('trashEntryId');

						if (override.attr('checked') || (!override.attr('checked') && newName.val() == '')) {
							submitForm(form);
						}
						else {
							var ioRequest = A.io.request(
								instance.get(STR_CHECKENTRY_URL),
								{
									autoLoad: false,
									data: {
										entryId: trashEntryId.val(),
										newName: newName.val()
									},
									dataType: 'json'
								}
							);

							ioRequest.after('success', A.bind(instance._afterPopUpCheckEntrySuccess, instance, ioRequest, form));
							ioRequest.after('failure', A.rbind(instance._afterPopUpCheckEntryFailure, instance, form));

							ioRequest.start();
						}
					},

					_showPopup: function(data, uri) {
						var instance = this;

						var popup = instance._getPopup();

						popup.show();

						popup.io.set('data', data);

						popup.io.set('uri', uri);

						popup.io.start();
					}
				}
			}
		);

		Liferay.Portlet.Trash = Trash;
	},
	'',
	{
		requires: ['aui-dialog', 'aui-io-request', 'liferay-portlet-base']
	}
);