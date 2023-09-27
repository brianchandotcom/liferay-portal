/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function composeAPI(version, APIs, baseEndpoint) {
	if (version in APIs) {
		return Object.values(APIs[version]).reduce(
			(api, collectMethods) =>
				Object.assign(api, collectMethods(baseEndpoint)),
			{}
		);
	}

	throw new Error('The API version was not specified');
}
