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

import fetch from './fetch.es';

const TOKEN_SERIALIZE = 'serialize://';

function getSessionClickFormData(cmd) {
	const doAsUserIdEncoded = Liferay.ThemeDisplay.getDoAsUserIdEncoded();

	const formData = new FormData();

	formData.append('cmd', cmd);
	formData.append('p_auth', Liferay.authToken);

	if (doAsUserIdEncoded) {
		formData.append('doAsUserId', doAsUserIdEncoded);
	}

	return formData;
}

function getSessionClickURL() {
	return `${Liferay.ThemeDisplay.getPortalURL()}${Liferay.ThemeDisplay.getPathMain()}/portal/session_click`;
}

/**
 * Gets the Store utility fetch value for given key
 * @param {String} key string for fetch request
 * @param {Object} options (currently only useHttpSession, defaulting to false)
 * @return {Promise}
 * @review
 */
export function getSessionValue(key, options = {}) {
	const formData = getSessionClickFormData('get');

	formData.append('key', key);

	if (options.useHttpSession) {
		formData.append('useHttpSession', true);
	}

	return fetch(getSessionClickURL(), {
		body: formData,
		method: 'POST',
	})
		.then((response) => response.text())
		.then((responseText) => {
			if (responseText.startsWith(TOKEN_SERIALIZE)) {
				const value = responseText.substring(TOKEN_SERIALIZE.length);

				responseText = JSON.parse(value);
			}

			return responseText;
		});
}

/**
 * Sets the Store utility fetch value
 * @param {String} key of the formData
 * @param {Object|String} value of the key for the formData
 * @param {Object} options (currently only useHttpSession, defaulting to false)
 * @return {Promise}
 * @review
 */
export function setSessionValue(key, value, options = {}) {
	const formData = getSessionClickFormData('set');

	if (value && typeof value === 'object') {
		value = TOKEN_SERIALIZE + JSON.stringify(value);
	}

	formData.append(key, value);

	if (options.useHttpSession) {
		formData.append('useHttpSession', true);
	}

	return fetch(getSessionClickURL(), {
		body: formData,
		method: 'POST',
	});
}
