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

import {fetch} from 'frontend-js-web';

/**
 * Returns a new formData built from the given object.
 * @param {{ portletNamespace: string }} config Application configuration constants
 * @param {object} body
 * @return {FormData}
 * @review
 */
function getFormData({portletNamespace}, body) {
	const formData = new FormData();

	Object.entries(body).forEach(([key, value]) => {
		formData.append(`${portletNamespace}${key}`, value);
	});

	return formData;
}

/**
 * Performs a POST request to the given url and parses an expected object response.
 * If the response status is over 400, or there is any "error" or "exception"
 * properties on the response object, it rejects the promise with an Error object.
 * @param {object} config Application configuration constants
 * @param {string} url
 * @param {object} [body={}]
 * @private
 * @return {Promise<object>}
 * @review
 */
export default function serviceFetch(config, url, body = {}) {
	return fetch(url, {
		body: getFormData(config, body),
		method: 'POST'
	})
		.then(
			response =>
				new Promise((resolve, reject) => {
					response
						.clone()
						.json()
						.then(body => resolve([response, body]))
						.catch(() => response.clone().text())
						.then(body => resolve([response, body]))
						.catch(reject);
				})
		)
		.then(([response, body]) => {
			if (typeof body === 'object') {
				if ('exception' in body) {
					throw new Error(body.exception);
				} else if ('error' in body) {
					throw new Error(body.error);
				}
			}

			if (response.status >= 400) {
				throw new Error(`${response.status} ${body}`);
			}

			return body;
		});
}
