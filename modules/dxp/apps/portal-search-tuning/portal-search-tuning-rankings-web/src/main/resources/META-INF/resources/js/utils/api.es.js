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

import {isNull} from './util.es';

/**
 * Fetches basic response given the url and parameters.
 * @param {string} url The base url to fetch.
 * @param {Object} params The url parameters to be included in the request.
 * @returns {Promise} The fetch request promise.
 */
export function fetchResponse(url, params) {
	const fetchUrl = new URL(url);

	Object.keys(params).forEach(property => {
		if (!isNull(params[property])) {
			fetchUrl.searchParams.set(property, params[property]);
		}
	});

	return fetch(fetchUrl).then(response => response.json());
}

/**
 * Fetches documents and maps the data response to the expected object shape
 * of {items: [{}, {}, ...], total: 10}.
 * @param {string} url The base url to fetch documents.
 * @param {Object} params The url parameters to be included in the request.
 * @returns {Promise} The fetch request promise.
 */
export function fetchDocuments(url, params) {
	return fetchResponse(url, params).then(data => ({
		items: data.documents,
		total: data.total
	}));
}
