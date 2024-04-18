/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace}) {
	Liferay.provide(
		window,
		`${namespace}transition`,
		(event) => {
			const link = event.target.closest('.transition-link');

			const workflowTaskId = parseInt(link.dataset.workflowtaskid, 10);

			const form = document.getElementById(`${namespace}transitionFm`);

			document.getElementById(
				`${namespace}transitionCommerceOrderId`
			).value = link.dataset.commerceorderid;

			document.getElementById(
				`${namespace}workflowTaskId`
			).value = workflowTaskId;
			document.getElementById(`${namespace}transitionName`).value =
				link.dataset.transitionname;

			if (workflowTaskId <= 0) {
				submitForm(form);

				return;
			}

			const transitionComments = document.getElementById(
				`${namespace}transitionComments`
			);

			transitionComments.classList.remove('hide');

			transitionComments.style.display = 'block';

			const dialog = Liferay.Util.Window.getWindow({
				dialog: {
					bodyContent: form,
					height: 400,
					resizable: false,
					toolbars: {
						footer: [
							{
								cssClass: 'btn-primary mr-2',
								label: Liferay.Language.get('done'),
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
				title: link.innerText,
			});
		},
		['liferay-util-window']
	);
}
