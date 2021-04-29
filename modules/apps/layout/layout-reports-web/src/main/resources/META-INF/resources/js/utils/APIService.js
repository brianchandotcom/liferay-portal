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

export default {
	getLayoutReportsIssues(layoutReportsIssuesURL, namespace) {
		return fetchWithError(layoutReportsIssuesURL, {
			body: getFormDataRequest({}, namespace),
			method: 'POST',
		});
	},
};

/**
 *
 *
 * @param {Object} body
 * @param {string} prefix
 * @param {FormData} [formData=new FormData()]
 * @returns {FormData}
 */
function getFormDataRequest(body, prefix, formData = new FormData()) {
	Object.entries(body).forEach(([key, value]) => {
		formData.append(`${prefix}${key}`, value);
	});

	return formData;
}

/**
 * Wrapper to `fetch` function throwing an error when `error` is present in the response
 */
function fetchWithError(url, options = {}) {
	return fetch(url, options)
		.then((response) => response.json())
		.then((objectResponse) => {
			if (objectResponse.error) {
				throw objectResponse.error;
			}

			return objectResponse;
		});
}
