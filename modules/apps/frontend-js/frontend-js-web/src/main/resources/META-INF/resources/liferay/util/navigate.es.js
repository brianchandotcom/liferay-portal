/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
