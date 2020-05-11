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

export default function ({getRedirectEntryChainCauseURL, namespace}) {
	var form = document[`${namespace}fm`];
	form.addEventListener('submit', saveRedirectEntry);

	function saveRedirectEntry() {
		var destinationURL = form.elements[`${namespace}destinationURL`];
		var sourceURL = form.elements[`${namespace}sourceURL`];

		if (destinationURL.value && sourceURL.value) {
			Liferay.Util.fetch(
				Liferay.Util.PortletURL.createResourceURL(
					getRedirectEntryChainCauseURL,
					{
						destinationURL: destinationURL.value,
						sourceURL: sourceURL.value,
					}
				)
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
						title: `${Liferay.Language.get('error')}:`,
						type: 'danger',
					});
				});
		}
		else {
			destinationURL.focus();
			destinationURL.blur();
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
