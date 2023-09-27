/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, getOpener, openToast} from 'frontend-js-web';

import {checkFriendlyURL} from './checkFriendlyURL';

interface Options {
	getFriendlyURLWarningURL: string;
	namespace: string;
	shouldCheckFriendlyURL: boolean;
}

export default function AddLayout({
	getFriendlyURLWarningURL,
	namespace,
	shouldCheckFriendlyURL,
}: Options) {
	const addButton = document.getElementById(
		`${namespace}addButton`
	) as HTMLButtonElement;

	const form = document.getElementById(`${namespace}fm`) as HTMLFormElement;

	const onSubmit = async (event: Event) => {
		event.preventDefault();
		event.stopPropagation();

		if (addButton.disabled) {
			return;
		}

		addButton.disabled = true;

		const formData = new FormData(form);

		if (shouldCheckFriendlyURL) {
			const {shouldSubmit} = await checkFriendlyURL(
				getFriendlyURLWarningURL,
				formData
			);

			if (!shouldSubmit) {
				addButton.disabled = false;

				return;
			}
		}

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
						id: 'addLayoutDialog',
						redirect: redirectURL.toString(),
					});
				}
				else {
					addButton.disabled = false;

					if (form.querySelector('.alert')) {
						return;
					}

					const alertWrapper = document.createElement('div');

					form.prepend(alertWrapper);

					openToast({
						autoClose: false,
						container: alertWrapper,
						message: response.errorMessage,
						type: 'danger',
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
