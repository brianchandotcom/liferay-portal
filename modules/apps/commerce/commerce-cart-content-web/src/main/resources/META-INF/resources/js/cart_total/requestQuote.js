/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace}) {
	Liferay.provide(
		window,
		`${namespace}requestQuote`,
		(event) => {
			event.preventDefault();

			const requestQuoteNote = document.getElementById(
				`${namespace}requestQuoteNote`
			);

			const form = document.getElementById(`${namespace}requestQuoteFm`);

			requestQuoteNote.classList.remove('hide');

			requestQuoteNote.style.display = 'block';

			const dialog = Liferay.Util.Window.getWindow({
				dialog: {
					bodyContent: form,
					height: 400,
					resizable: false,
					toolbars: {
						footer: [
							{
								cssClass: 'btn-primary mr-2',
								label: Liferay.Language.get('submit'),
								on: {
									click() {
										submitForm(form);
									},
								},
							},
							{
								cssClass: 'btn-cancel',
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
						],
						header: [
							{
								cssClass: 'close',
								discardDefaultButtonCssClasses: true,
								labelHTML:
									'<span aria-hidden="true">&times;</span>',
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
					width: 720,
				},
				title: Liferay.Language.get('request-a-quote'),
			});
		},
		['liferay-util-window']
	);
}
