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

import {errorToast} from './utils/toast';

export default function({checkDestinationURL, namespace}) {
	var form = document[`${namespace}fm`];
	form.addEventListener('submit', saveRedirectEntry);

	function saveRedirectEntry() {
		var destinationURL = form.elements[`${namespace}destinationURL`];
		var sourceURL = form.elements[`${namespace}sourceURL`];

		if (destinationURL.value && sourceURL.value) {
			Liferay.Util.fetch(checkDestinationURL, {
				body: Liferay.Util.objectToFormData({
					[`${namespace}sourceURL`]: sourceURL.value,
				}),
				method: 'POST',
			})
				.then(response => {
					return response.json();
				})
				.then(response => {
					if (response.success) {
						submitForm(form);
					}
					else {
						showModal();
					}
				})
				.catch(() => {
					errorToast();
				});
		}
		else {
			destinationURL.focus();
			destinationURL.blur();
		}
	}

	function showModal() {
		Liferay.componentReady(`${namespace}RedirectsChainedRedirections`).then(
			ChainedRedirections => {
				ChainedRedirections.open(updateReferences => {
					Liferay.Util.postForm(form, {
						data: {
							updateReferences,
						},
					});
				});
			}
		);
	}
}
