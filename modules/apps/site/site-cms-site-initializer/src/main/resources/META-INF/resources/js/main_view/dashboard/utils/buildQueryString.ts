/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const DEFAULT_FILTER_VALUE = 'all';

function formatQueryParam(param: string, value?: string) {
	if (!value?.length || value === DEFAULT_FILTER_VALUE) {
		return;
	}

	return `${encodeURIComponent(param)}=${encodeURIComponent(value)}`;
}

function buildQueryString(params: Record<string, string>) {
	const queryParams = Object.keys(params).sort();

	const queryString = queryParams
		.map((key) => formatQueryParam(key, params[key]))
		.filter(Boolean)
		.join('&');

	return `?${queryString}`;
}

export {buildQueryString};
