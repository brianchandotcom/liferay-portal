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

/**
 * Modifies the url to include parameters.
 * @param {string} url The base url to fetch.
 * @param {Object} params The url parameters to be included in the request.
 * @returns {string} The modified url.
 */
export default function addParams(url, params) {
	const fetchURL = new URL(url, Liferay.ThemeDisplay.getPortalURL());

	Object.keys(params).forEach((key) => {
		if (params[key] !== null) {
			fetchURL.searchParams.append(key, params[key]);
		}
	});

	return fetchURL;
}
