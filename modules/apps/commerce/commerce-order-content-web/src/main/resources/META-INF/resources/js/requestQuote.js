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
