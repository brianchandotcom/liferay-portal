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

import addParams from './add_params';
import {DEFAULT_HEADERS} from './fetch_data';

/**
 * A wrapper function to fetch data for the preview sidebar. This was split into
 * a separate function primarily to make it easier to mock in tests.
 * @param {object} urlParameters The parameters to be added to the url.
 * @param {object} options Additional fetch options. For example, `{body: ...}`
 * @returns
 */
export default function fetchPreviewSearch(urlParameters, options) {
	return fetch(
		addParams('/o/search-experiences-rest/v1.0/search', urlParameters),
		{
			headers: DEFAULT_HEADERS,
			method: 'POST',
			...options,
		}
	);
}
