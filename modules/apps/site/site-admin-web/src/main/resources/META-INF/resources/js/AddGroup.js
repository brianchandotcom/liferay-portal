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

import {openToast} from 'frontend-js-web';

export default function ({namespace}) {
	const loading = document.querySelector('.add-group-loading');
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

		const formData = new FormData(form);

		Liferay.Util.fetch(form.action, {
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

					const opener = Liferay.Util.getOpener();

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
				}
			});
	});
}
