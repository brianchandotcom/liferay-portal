/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
