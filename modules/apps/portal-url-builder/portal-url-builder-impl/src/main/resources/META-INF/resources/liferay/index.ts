/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

Liferay.FrontendESM = {
	buildURL(
		callerScriptURL: string,
		contextPath: string,
		submodule: string
	): string {
		const baseURL = new URL(`${callerScriptURL}`);

		baseURL.search = '';
		baseURL.hash = '';

		const prefix = getPrefix(baseURL.toString());

		return `${baseURL.toString()}${prefix}/${contextPath}/__liferay__/${submodule}.js`;
	},
};

function getPrefix(baseURL: string): string {
	if (baseURL.includes('/combo/')) {
		return '../o';
	}
	else if (baseURL.includes('/combo')) {
		return '/../o';
	}

	let index;
	let startPartsCount;

	if (baseURL.includes('/o/js/-/')) {
		index = baseURL.indexOf('/o/js/-/');
		startPartsCount = 3;
	}
	else if (baseURL.includes('/o/')) {
		index = baseURL.indexOf('/o/');
		startPartsCount = 1;
	}
	else {
		throw new Error(`Invalid base URL: ${baseURL}`);
	}

	let prefix = '';

	let depth = baseURL.substring(index + 1).split('/').length - 1;

	depth -= startPartsCount - 1;

	for (let i = 0; i < depth; i++) {
		prefix += prefix.length ? '/..' : '..';
	}

	return `/${prefix}`;
}
