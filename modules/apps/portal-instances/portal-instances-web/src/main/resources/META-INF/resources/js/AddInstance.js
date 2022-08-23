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

import {fetch, openToast} from 'frontend-js-web';

export default function ({namespace}) {
	const form = document.getElementById(`${namespace}fm`);

	const content = document.querySelector('.add-instance-content');
	const loading = document.querySelector('.add-instance-loading');

	const onSubmit = (event) => {
		event.preventDefault();

		const formData = new FormData(form);

		content.classList.add('d-none');
		content.classList.remove('d-block');
		loading.classList.add('d-flex');

		fetch(form.action, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((response) => {
				const opener = Liferay.Util.getOpener();

				const alertContainer = document.querySelector(
					'.add-instance-alert-container'
				);

				if (alertContainer.hasChildNodes()) {
					alertContainer.firstChild.remove();
				}

				if (!response.error) {
					opener.Liferay.fire('closeModal', {
						id: `${namespace}addSiteDialog`,
						redirect: opener.location.href,
					});
				}
				else {
					content.classList.add('d-block');
					loading.classList.add('d-none');
					loading.classList.remove('d-flex');

					openToast({
						autoClose: false,
						container: document.querySelector(
							'.add-instance-alert-container'
						),
						message: response.error,
						toastProps: {
							onClose: null,
						},
						type: 'danger',
						variant: 'stripe',
					});
				}
			});
	};

	form.addEventListener('submit', onSubmit);

	return {
		dispose() {
			form.removeEventListener('submit', onSubmit);
		},
	};
}
