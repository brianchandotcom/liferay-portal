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
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-restore-entry
 */

AUI.add(
	'liferay-restore-entry',
	(A) => {
		const Lang = A.Lang;

		const isString = Lang.isString;

		const STR_CHECK_ENTRY_URL = 'checkEntryURL';

		const RestoreEntry = A.Component.create({
			ATTRS: {
				checkEntryURL: {
					validator: isString,
				},

				duplicateEntryURL: {
					validator: isString,
				},

				namespace: {
					validator: isString,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'restoreentry',

			prototype: {
				_afterCheckEntryFailure(uri) {
					const instance = this;

					submitForm(instance._hrefFm, uri);
				},

				_afterCheckEntrySuccess(response, uri) {
					const instance = this;

					if (response.success) {
						submitForm(instance._hrefFm, uri);
					}
					else {
						const data = instance.ns({
							duplicateEntryId: response.duplicateEntryId,
							oldName: response.oldName,
							overridable: response.overridable,
							trashEntryId: response.trashEntryId,
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
					const instance = this;

					if (response.success) {
						submitForm(form);
					}
					else {
						const errorMessage = response.errorMessage;

						const errorMessageContainer = instance.byId(
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

							const messageContainer = instance.byId(
								'messageContainer'
							);
							const newName = instance.byId('newName');

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
					const instance = this;

					const uri = event.uri;

					const data = {
						trashEntryId: event.trashEntryId,
					};

					Liferay.Util.fetch(instance.get(STR_CHECK_ENTRY_URL), {
						body: Liferay.Util.objectToFormData(instance.ns(data)),
						method: 'POST',
					})
						.then((response) => response.json())
						.then((response) => {
							instance._afterCheckEntrySuccess(response, uri);
						})
						.catch(() => {
							instance._afterCheckEntryFailure(uri);
						});
				},

				_getPopup() {
					const instance = this;

					let popup = instance._popup;

					if (!popup) {
						popup = Liferay.Util.Window.getWindow({
							dialog: {
								cssClass: 'trash-restore-popup',
							},
							title: Liferay.Language.get('warning'),
						});

						popup.plug(A.Plugin.IO, {
							after: {
								success: A.bind(
									'_initializeRestorePopup',
									instance
								),
							},
							autoLoad: false,
						});

						instance._popup = popup;
					}

					return popup;
				},

				_initializeRestorePopup() {
					const instance = this;

					const restoreTrashEntryFm = instance.byId(
						'restoreTrashEntryFm'
					);

					restoreTrashEntryFm.on(
						'submit',
						instance._onRestoreTrashEntryFmSubmit,
						instance,
						restoreTrashEntryFm
					);

					const closeButton = restoreTrashEntryFm.one('.btn-cancel');

					if (closeButton) {
						closeButton.on(
							'click',
							instance._popup.hide,
							instance._popup
						);
					}

					const newName = instance.byId('newName');
					const rename = instance.byId('rename');

					rename.on(
						'click',
						A.fn('focusFormField', Liferay.Util, newName)
					);

					newName.on('focus', () => {
						rename.attr('checked', true);
					});
				},

				_onRestoreTrashEntryFmSubmit(_event, form) {
					const instance = this;

					const newName = instance.byId('newName');
					const override = instance.byId('override');
					const trashEntryId = instance.byId('trashEntryId');

					if (
						override.attr('checked') ||
						(!override.attr('checked') && !newName.val())
					) {
						submitForm(form);
					}
					else {
						const data = {
							newName: newName.val(),
							trashEntryId: trashEntryId.val(),
						};

						Liferay.Util.fetch(instance.get(STR_CHECK_ENTRY_URL), {
							body: Liferay.Util.objectToFormData(
								instance.ns(data)
							),
							method: 'POST',
						})
							.then((response) => response.json())
							.then((response) => {
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
					const instance = this;

					const popup = instance._getPopup();

					popup.show();

					const popupIO = popup.io;

					popupIO.set('data', data);
					popupIO.set('uri', uri);

					popupIO.start();
				},

				destructor() {
					const instance = this;

					A.Array.invoke(instance._eventHandles, 'detach');
				},

				initializer() {
					const instance = this;

					instance._eventCheckEntry = instance.ns('checkEntry');

					instance._hrefFm = A.one('#hrefFm');

					const eventHandles = [
						Liferay.on(
							instance._eventCheckEntry,
							instance._checkEntry,
							instance
						),
					];

					instance._eventHandles = eventHandles;
				},
			},
		});

		Liferay.RestoreEntry = RestoreEntry;
	},
	'',
	{
		requires: [
			'aui-io-plugin-deprecated',
			'liferay-portlet-base',
			'liferay-util-window',
		],
	}
);
