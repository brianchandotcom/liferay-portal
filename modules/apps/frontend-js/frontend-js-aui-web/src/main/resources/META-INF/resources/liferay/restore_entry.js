/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/**
 * The Restore Entry Component.
 *
 * @deprecated since 7.2, unused
 * @module liferay-restore-entry
 */

AUI.add(
	'liferay-restore-entry',
	A => {
		var Lang = A.Lang;

		var isString = Lang.isString;

		var STR_CHECK_ENTRY_URL = 'checkEntryURL';

		var RestoreEntry = A.Component.create({
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
				_afterCheckEntryFailure(uri) {
					var instance = this;

					submitForm(instance._hrefFm, uri);
				},

				_afterCheckEntrySuccess(response, uri) {
					var instance = this;

					if (response.success) {
						submitForm(instance._hrefFm, uri);
					}
					else {
						var data = instance.ns({
							duplicateEntryId: response.duplicateEntryId,
							oldName: response.oldName,
							overridable: response.overridable,
							trashEntryId: response.trashEntryId
						});

						instance._showPopup(
							data,
							instance.get('duplicateEntryURL')
						);
					}
				},

				_afterPopupCheckEntryFailure(form) {
					submitForm(form);
				},

				_afterPopupCheckEntrySuccess(response, form) {
					var instance = this;

					if (response.success) {
						submitForm(form);
					}
					else {
						var errorMessage = response.errorMessage;

						var errorMessageContainer = instance.byId(
							'errorMessageContainer'
						);

						if (errorMessage) {
							errorMessageContainer.html(
								Liferay.Language.get(response.errorMessage)
							);

							errorMessageContainer.show();
						}
						else {
							errorMessageContainer.hide();

							var messageContainer = instance.byId(
								'messageContainer'
							);
							var newName = instance.byId('newName');

							messageContainer.html(
								Lang.sub(
									Liferay.Language.get(
										'an-entry-with-name-x-already-exists'
									),
									[newName.val()]
								)
							);
						}
					}
				},

				_checkEntry(event) {
					var instance = this;

					var uri = event.uri;

					var data = {
						trashEntryId: event.trashEntryId
					};

					Liferay.Util.fetch(instance.get(STR_CHECK_ENTRY_URL), {
						body: Liferay.Util.objectToFormData(instance.ns(data)),
						method: 'POST'
					})
						.then(response => response.json())
						.then(response => {
							instance._afterCheckEntrySuccess(response, uri);
						})
						.catch(() => {
							instance._afterCheckEntryFailure(uri);
						});
				},

				_getPopup() {
					var instance = this;

					var popup = instance._popup;

					if (!popup) {
						popup = Liferay.Util.Window.getWindow({
							dialog: {
								cssClass: 'trash-restore-popup'
							},
							title: Liferay.Language.get('warning')
						});

						popup.plug(A.Plugin.IO, {
							after: {
								success: A.bind(
									'_initializeRestorePopup',
									instance
								)
							},
							autoLoad: false
						});

						instance._popup = popup;
					}

					return popup;
				},

				_initializeRestorePopup() {
					var instance = this;

					var restoreTrashEntryFm = instance.byId(
						'restoreTrashEntryFm'
					);

					restoreTrashEntryFm.on(
						'submit',
						instance._onRestoreTrashEntryFmSubmit,
						instance,
						restoreTrashEntryFm
					);

					var closeButton = restoreTrashEntryFm.one('.btn-cancel');

					if (closeButton) {
						closeButton.on(
							'click',
							instance._popup.hide,
							instance._popup
						);
					}

					var newName = instance.byId('newName');
					var rename = instance.byId('rename');

					rename.on(
						'click',
						A.fn('focusFormField', Liferay.Util, newName)
					);

					newName.on('focus', () => {
						rename.attr('checked', true);
					});
				},

				_onRestoreTrashEntryFmSubmit(_event, form) {
					var instance = this;

					var newName = instance.byId('newName');
					var override = instance.byId('override');
					var trashEntryId = instance.byId('trashEntryId');

					if (
						override.attr('checked') ||
						(!override.attr('checked') && !newName.val())
					) {
						submitForm(form);
					}
					else {
						var data = {
							newName: newName.val(),
							trashEntryId: trashEntryId.val()
						};

						Liferay.Util.fetch(instance.get(STR_CHECK_ENTRY_URL), {
							body: Liferay.Util.objectToFormData(
								instance.ns(data)
							),
							method: 'POST'
						})
							.then(response => response.json())
							.then(response => {
								instance._afterPopupCheckEntrySuccess(
									response,
									form
								);
							})
							.catch(() => {
								instance._afterPopupCheckEntryFailure(form);
							});
					}
				},

				_showPopup(data, uri) {
					var instance = this;

					var popup = instance._getPopup();

					popup.show();

					var popupIO = popup.io;

					popupIO.set('data', data);
					popupIO.set('uri', uri);

					popupIO.start();
				},

				destructor() {
					var instance = this;

					A.Array.invoke(instance._eventHandles, 'detach');
				},

				initializer() {
					var instance = this;

					instance._eventCheckEntry = instance.ns('checkEntry');

					instance._hrefFm = A.one('#hrefFm');

					var eventHandles = [
						Liferay.on(
							instance._eventCheckEntry,
							instance._checkEntry,
							instance
						)
					];

					instance._eventHandles = eventHandles;
				}
			}
		});

		Liferay.RestoreEntry = RestoreEntry;
	},
	'',
	{
		requires: [
			'aui-io-plugin-deprecated',
			'liferay-portlet-base',
			'liferay-util-window'
		]
	}
);
