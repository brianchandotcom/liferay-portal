AUI.add(
	'liferay-restore-entry',
	function(A) {
		var Lang = A.Lang;

		var isString = Lang.isString;

		var RESPONSE_DATA = 'responseData';

		var STR_CHECK_ENTRY_URL = 'checkEntryURL';

		var RestoreEntry = A.Component.create(
			{
				ATTRS: {
					checkEntryURL: {
						validator: isString
					},

					duplicateEntryURL: {
						validator: isString
					},

					namespace: {
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
							var data = instance.ns(
								{
									duplicateEntryId: responseData.duplicateEntryId,
									oldName: responseData.oldName,
									overridable: responseData.overridable,
									trashEntryId: responseData.trashEntryId
								}
							);

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
							var errorMessageContainer = instance.byId('errorMessageContainer');
							var errorMessage = responseData.errorMessage;

							if (errorMessage) {
								errorMessageContainer.html(Liferay.Language.get(responseData.errorMessage));

								errorMessageContainer.show();
							}
							else {
								errorMessageContainer.hide();

								var newName = instance.byId('newName');
								var messageContainer = instance.byId('messageContainer');

								messageContainer.html(Lang.sub(Liferay.Language.get('an-entry-with-name-x-already-exists'), [newName.val()]));
							}
						}
					},

					_checkEntry: function(event) {
						var instance = this;

						var uri = event.uri;

						A.io.request(
							instance.get(STR_CHECK_ENTRY_URL),
							{
								after: {
									failure: A.rbind('_afterCheckEntryFailure', instance),
									success: A.rbind('_afterCheckEntrySuccess', instance)
								},
								'arguments': uri,
								data: instance.ns(
									{
										trashEntryId: event.trashEntryId
									}
								),
								dataType: 'JSON'
							}
						);
					},

					_getPopup: function() {
						var instance = this;

						var popup = instance._popup;

						if (!popup) {
							popup = Liferay.Util.Window.getWindow(
								{
									dialog: {
										cssClass: 'trash-restore-popup'
									},
									title: Liferay.Language.get('warning')
								}
							);

							popup.plug(
								A.Plugin.IO,
								{
									after: {
										success: A.bind('_initializeRestorePopup', instance)
									},
									autoLoad: false
								}
							);

							instance._popup = popup;
						}

						return popup;
					},

					_initializeRestorePopup: function() {
						var instance = this;

						var restoreTrashEntryFm = instance.byId('restoreTrashEntryFm');

						restoreTrashEntryFm.on('submit', instance._onRestoreTrashEntryFmSubmit, instance, restoreTrashEntryFm);

						var closeButton = restoreTrashEntryFm.one('.btn-cancel');

						if (closeButton) {
							closeButton.on('click', instance._popup.hide, instance._popup);
						}

						var rename = instance.byId('rename');
						var newName = instance.byId('newName');

						rename.on('click', A.fn('focusFormField', Liferay.Util, newName));

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
								instance.get(STR_CHECK_ENTRY_URL),
								{
									after: {
										failure: A.rbind('_afterPopupCheckEntryFailure', instance),
										success: A.rbind('_afterPopupCheckEntrySuccess', instance)
									},
									'arguments': form,
									data: instance.ns(
										{
											newName: newName.val(),
											trashEntryId: trashEntryId.val()
										}
									),
									dataType: 'JSON'
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
		requires: ['aui-io-plugin-deprecated', 'aui-io-request', 'liferay-portlet-base', 'liferay-util-window']
	}
);