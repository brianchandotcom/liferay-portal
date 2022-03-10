/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {fetch} from 'frontend-js-web';

import {DEFAULT_ERROR} from './constants';
import {openErrorToast} from './toasts';

const DEFAULT_HEADERS = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
});

const DEFAULT_METHOD = 'GET';

/**
 * A wrapper around frontend-js-web's `fetch` function with commonly used
 * default `init` options.
 * @see {@link https://github.com/liferay/liferay-portal/blob/master/modules/apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/util/fetch.es.js}
 * @param {!string|!Request} resource The URL to the resource, or a Resource
 * object.
 * @param {Object=} init An optional object containing custom configuration.
 * @return {Promise} A Promise that resolves to a Response object.
 */
export function fetchData(resource = '', init) {
	return fetch(resource, {
		headers: DEFAULT_HEADERS,
		method: DEFAULT_METHOD,
		...init,
	})
		.then((response) => {
			if (!response.ok) {
				throw DEFAULT_ERROR;
			}

			return response.json();
		})
		.catch((error) => {
			openErrorToast();

			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}

			throw error;
		});
}

/**
 * Modifies the url to include parameters.
 * @param {string} url The base url to fetch.
 * @param {Object} params The url parameters to be included in the request.
 * @returns {string} The modified url.
 */
export function addParams(url, params) {
	const fetchURL = new URL(url, Liferay.ThemeDisplay.getPortalURL());

	Object.keys(params).forEach((key) => {
		if (params[key] !== null) {
			fetchURL.searchParams.append(key, params[key]);
		}
	});

	return fetchURL;
}

/**
 * A wrapper function to fetch data for the preview sidebar. This was split into
 * a separate function primarily to make it easier to mock in tests.
 * @param {object} urlParameters The parameters to be added to the url.
 * @param {object} options Additional fetch options. For example, `{body: ...}`
 * @returns
 */
export function fetchPreviewSearch(urlParameters, options) {
	return fetch(
		addParams('/o/search-experiences-rest/v1.0/search', urlParameters),
		{
			headers: DEFAULT_HEADERS,
			method: 'POST',
			...options,
		}
	);
}
