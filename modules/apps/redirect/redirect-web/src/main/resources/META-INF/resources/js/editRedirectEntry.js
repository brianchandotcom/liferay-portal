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

import {createResourceURL, openToast} from 'frontend-js-web';

export default function ({
	getRedirectEntryChainCauseURL,
	initialDestinationURL,
	initialIsPermanent,
	namespace,
}) {
	const form = document[`${namespace}fm`];
	form.addEventListener('submit', saveRedirectEntry);
	const typeInfoAlert = document.getElementById(`${namespace}typeInfoAlert`);
	const destinationURLInput = document.getElementById(
		`${namespace}destinationURL`
	);
	const permanentSelect = document.getElementById(`${namespace}permanent`);

	if (typeInfoAlert && initialIsPermanent) {
		destinationURLInput.addEventListener('input', showTypeInfoAlert);
		permanentSelect.addEventListener('input', showTypeInfoAlert);
	}

	function showTypeInfoAlert() {
		typeInfoAlert.classList.toggle(
			'hide',
			permanentSelect.value === 'true' &&
				destinationURLInput.value === initialDestinationURL
		);
	}

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
			Liferay.Util.fetch(
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
						Liferay.Util.postForm(form, {
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
