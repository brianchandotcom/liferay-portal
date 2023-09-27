/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function setURLParameters(parameters = {}, baseUrl) {
	const url = new URL(baseUrl);
	const search_params = url.searchParams;

	Object.entries(parameters).forEach(([key, value]) => [
		search_params.set(key, value),
	]);

	url.search = search_params.toString();

	return url.toString();
}
