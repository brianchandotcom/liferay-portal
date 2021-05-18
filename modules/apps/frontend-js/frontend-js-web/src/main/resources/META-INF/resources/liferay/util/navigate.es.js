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

/**
 * Performs navigation to the given url. If SPA is enabled, it will route the
 * request through the SPA engine. If not, it will simple change the document
 * location.
 * @param {string | URL} url Destination URL to navigate
 * @param {?object} listeners Object with key-value pairs with callbacks for
 * specific page lifecycle events
 * @review
 */

export default function (url, listeners) {
	let urlString = url;

	if (url?.constructor?.name === 'URL') {
		urlString = String(url);
	}

	if (Liferay.SPA?.app?.canNavigate(urlString)) {
		Liferay.SPA.app.navigate(urlString);

		if (listeners) {
			Object.keys(listeners).forEach((key) => {
				Liferay.once(key, listeners[key]);
			});
		}
	}
	else if (isValidURL(urlString)) {
		window.location.href = urlString;
	}
}

function isValidURL(url) {
	let urlObject;

	try {
		if (url.startsWith('/')) {
			urlObject = new URL(url, window.location.origin);
		}
		else {
			urlObject = new URL(url);
		}
	}
	catch (error) {
		return false;
	}

	return urlObject.protocol === 'http:' || urlObject.protocol === 'https:';
}
