/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetchFromHeadless} from '../utils/fetch';

export const COUNTRIES_ROOT_ENDPOINT =
	'/o/headless-admin-address/v1.0/countries';

export function getCountries(pageSize = -1) {
	const url = new URL(
		`${themeDisplay.getPathContext()}${COUNTRIES_ROOT_ENDPOINT}`,
		themeDisplay.getPortalURL()
	);

	url.searchParams.append('pageSize', pageSize);

	return fetchFromHeadless(url);
}
