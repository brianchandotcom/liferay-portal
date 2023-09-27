/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

export default {
	getLayoutReportsIssues(layoutReportsIssuesURL, refreshCache) {
		return fetchWithError(layoutReportsIssuesURL, {
			body: getFormDataRequest({refreshCache}),
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
function getFormDataRequest(body, formData = new FormData()) {
	Object.entries(body).forEach(([key, value]) => {
		formData.append(`${key}`, value);
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
			if (objectResponse?.error) {
				throw objectResponse;
			}

			return objectResponse;
		});
}
