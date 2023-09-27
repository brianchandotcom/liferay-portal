/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, getOpener, openToast} from 'frontend-js-web';

export default function ({namespace}) {
	const loading = document.querySelector('.add-group-loading');
	const container = document.querySelector('.add-group-container');
	const content = document.querySelector(
		'.add-group-form .add-group-content'
	);
	const footer = document.querySelector('.add-group-form .sheet-footer');
	const form = document.getElementById(`${namespace}fm`);
	const formInput = document.getElementById(`${namespace}name`);

	setTimeout(() => {
		formInput.focus();
	}, 100);

	form.addEventListener('submit', (event) => {
		event.preventDefault();
		event.stopPropagation();

		const alertContainer = document.querySelector(
			'.add-group-alert-container'
		);

		if (alertContainer.hasChildNodes()) {
			alertContainer.firstChild.remove();
		}

		content.classList.toggle('d-none');
		loading.classList.add('d-flex');
		loading.classList.remove('d-none');
		footer.classList.toggle('d-none');

		container.classList.add('align-items-center', 'd-flex', 'h-100');

		const formData = new FormData(form);

		fetch(form.action, {
			body: formData,
			method: 'POST',
		})
			.then((response) => {
				return response.json();
			})
			.then((response) => {
				if (response.redirectURL) {
					const redirectURL = new URL(
						response.redirectURL,
						window.location.origin
					);

					redirectURL.searchParams.set('p_p_state', 'normal');

					const opener = getOpener();

					opener.Liferay.fire('closeModal', {
						id: `${namespace}addSiteDialog`,
						redirect: redirectURL.toString(),
					});
				}
				else {
					openToast({
						autoClose: false,
						container: alertContainer,
						message: response.error,
						toastProps: {
							onClose: null,
						},
						type: 'danger',
						variant: 'stripe',
					});

					content.classList.toggle('d-none');
					loading.classList.remove('d-flex');
					loading.classList.add('d-none');
					footer.classList.toggle('d-none');

					container.classList.remove(
						'align-items-center',
						'd-flex',
						'h-100'
					);
				}
			});
	});
}
