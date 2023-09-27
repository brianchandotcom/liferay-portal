/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {DEFAULT_ERROR} from '../errorMessages';

export const DEFAULT_HEADERS_OBJECT = {
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
};

export const DEFAULT_HEADERS = new Headers(DEFAULT_HEADERS_OBJECT);

/**
 * A wrapper around frontend-js-web's `fetch` function with commonly used
 * default `init` options.
 * @see {@link https://github.com/liferay/liferay-portal/blob/master/modules/apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/util/fetch.es.js}
 * @param {!string|!Request} resource The URL to the resource, or a Resource
 * object.
 * @param {Object=} init An optional object containing custom configuration.
 * @return {Promise} A Promise that resolves to a Response object.
 */
export default function fetchData(resource = '', {headers, ...init} = {}) {
	return fetch(resource, {
		headers: new Headers({...DEFAULT_HEADERS_OBJECT, ...headers}),
		method: 'GET',
		...init,
	})
		.then((response) => {
			if (!response.ok) {
				throw DEFAULT_ERROR;
			}

			return response.json();
		})
		.catch((error) => {
			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}

			throw error;
		});
}
