/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createResourceURL, fetch, openToast, postForm} from 'frontend-js-web';

export default function ({getRedirectEntryChainCauseURL, namespace}) {
	const form = document[`${namespace}fm`];
	form.addEventListener('submit', saveRedirectEntry);

	function saveRedirectEntry() {
		const destinationURL = form.elements[`${namespace}destinationURL`];
		const sourceURL = form.elements[`${namespace}sourceURL`];

		if (!sourceURL.value) {
			sourceURL.focus();
		}
		else if (
			!destinationURL.value ||
			destinationURL
				.closest('.form-group')
				.classList.contains('has-error')
		) {
			destinationURL.focus();
			destinationURL.blur();
		}
		else {
			fetch(
				createResourceURL(getRedirectEntryChainCauseURL, {
					destinationURL: destinationURL.value,
					sourceURL: sourceURL.value,
				})
			)
				.then((response) => {
					return response.json();
				})
				.then((response) => {
					if (response.redirectEntryChainCause) {
						showModal(response.redirectEntryChainCause);
					}
					else {
						submitForm(form);
					}
				})
				.catch(() => {
					openToast({
						message: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						type: 'danger',
					});
				});
		}
	}

	function showModal(redirectEntryChainCause) {
		Liferay.componentReady(`${namespace}RedirectsChainedRedirections`).then(
			(ChainedRedirections) => {
				ChainedRedirections.open(
					redirectEntryChainCause,
					(updateChainedRedirectEntries) => {
						postForm(form, {
							data: {
								updateChainedRedirectEntries,
							},
						});
					}
				);
			}
		);
	}
}
