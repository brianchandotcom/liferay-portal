/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-kaleo-designer-dialogs',
	(A) => {
		const KaleoDesignerDialogs = {
			_duplicationDialog: null,
			_forms: {},

			confirmBeforeDuplicateDialog(
				_,
				actionUrl,
				title,
				randomId,
				portletNamespace
			) {
				const instance = this;

				let form = A.one('#' + portletNamespace + randomId + 'form');

				if (form && !instance._forms[randomId]) {
					instance._forms[randomId] = form;
				}
				else if (!form && instance._forms[randomId]) {
					form = instance._forms[randomId];
				}

				if (form) {
					form.setAttribute('action', actionUrl);
					form.setAttribute('method', 'POST');
				}

				const duplicationDialog = instance._duplicationDialog;

				if (duplicationDialog) {
					duplicationDialog.destroy();
				}

				const dialog = Liferay.Util.Window.getWindow({
					dialog: {
						bodyContent: form,
						height: 325,
						toolbars: {
							footer: [
								{
									cssClass: 'btn btn-secondary',
									discardDefaultButtonCssClasses: true,
									label: Liferay.Language.get('cancel'),
									on: {
										click() {
											if (form) {
												form.reset();
											}

											dialog.hide();
										},
									},
								},
								{
									cssClass: 'btn btn-primary',
									discardDefaultButtonCssClasses: true,
									label: Liferay.Language.get('duplicate'),
									on: {
										click() {
											if (form) {
												submitForm(form);
											}

											dialog.hide();
										},
									},
								},
							],
							header: [
								{
									cssClass: 'close',
									discardDefaultButtonCssClasses: true,
									labelHTML:
										'<svg class="lexicon-icon" focusable="false"><use href="' +
										Liferay.Icons.spritemap +
										'#times" /><title>' +
										Liferay.Language.get('close') +
										'</title></svg>',
									on: {
										click() {
											if (form) {
												form.reset();
											}

											dialog.hide();
										},
									},
								},
							],
						},
						width: 500,
					},
					title,
				});

				instance._duplicationDialog = dialog;
			},
		};

		const openConfirmDeleteDialog = function (title, message, actionUrl) {
			const dialog = Liferay.Util.Window.getWindow({
				dialog: {
					bodyContent: message,
					destroyOnHide: true,
					height: 200,
					resizable: false,
					toolbars: {
						footer: [
							{
								cssClass: 'btn btn-primary mr-2',
								discardDefaultButtonCssClasses: true,
								label: Liferay.Language.get('delete'),
								on: {
									click() {
										window.location.assign(actionUrl);
									},
								},
							},
							{
								cssClass: 'btn btn-secondary',
								discardDefaultButtonCssClasses: true,
								label: Liferay.Language.get('cancel'),
								on: {
									click() {
										dialog.destroy();
									},
								},
							},
						],
						header: [
							{
								cssClass: 'close',
								discardDefaultButtonCssClasses: true,
								labelHTML:
									'<svg class="lexicon-icon" focusable="false"><use href="' +
									Liferay.Icons.spritemap +
									'#times" /><title>' +
									Liferay.Language.get('close') +
									'</title></svg>',
								on: {
									click(event) {
										dialog.destroy();

										event.domEvent.stopPropagation();
									},
								},
							},
						],
					},
					width: 600,
				},
				title,
			});
		};

		const showActionUndoneSuccessMessage = function () {
			Liferay.Util.openToast({
				container: document.querySelector('.lfr-alert-container'),
				message: Liferay.Language.get('action-undone'),
			});
		};

		const showDefinitionImportSuccessMessage = function (namespace) {
			const undo = Liferay.Language.get('undo');

			const undoEvent = "'" + namespace + "undoDefinition'";

			const undoLink =
				'<a href="javascript:void(0);" onclick=Liferay.fire(' +
				undoEvent +
				'); class="alert-link">' +
				undo +
				'</a>';

			let message = Liferay.Language.get(
				'definition-imported-successfully'
			);

			message += undoLink;

			Liferay.Util.openToast({
				container: document.querySelector('.lfr-alert-container'),
				message,
			});
		};

		KaleoDesignerDialogs.openConfirmDeleteDialog = openConfirmDeleteDialog;

		KaleoDesignerDialogs.showActionUndoneSuccessMessage = showActionUndoneSuccessMessage;

		KaleoDesignerDialogs.showDefinitionImportSuccessMessage = showDefinitionImportSuccessMessage;

		Liferay.KaleoDesignerDialogs = KaleoDesignerDialogs;
	},
	'',
	{
		requires: ['liferay-util-window'],
	}
);
