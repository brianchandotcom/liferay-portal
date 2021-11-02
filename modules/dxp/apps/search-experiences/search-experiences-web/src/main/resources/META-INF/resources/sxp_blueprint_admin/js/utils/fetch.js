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

export const fetchData = (url = '', parameters, successFcn, errorFcn) => {
	fetch(url, parameters)
		.then((response) => {
			if (!response.ok) {
				throw DEFAULT_ERROR;
			}

			return response.json();
		})
		.then((responseContent) => {
			successFcn(responseContent);
		})
		.catch((error) => {
			openErrorToast();

			if (process.env.NODE_ENV === 'development') {
				console.error(error);
			}

			errorFcn(error);
		});
};

/**
 * Modifies the url to include parameters.
 * @param {string} url The base url to fetch.
 * @param {Object} params The url parameters to be included in the request.
 * @returns {string} The modified url.
 */
export function addParams(url, params) {
	url += '?';

	Object.keys(params).forEach((property) => {
		if (params[property] !== null) {
			url += `&${property}=${params[property]}`;
		}
	});

	return url;
}
