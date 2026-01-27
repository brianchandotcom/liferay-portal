/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getFlatName from '../../../util/getFlatName.mjs';

export const URLType = {
	CSS_EXPORT: 0,
	CSS_EXPORT_LOADER_MODULE: 1,
	NPM_EXPORT: 2,
	PROJECT_DEFAULT_EXPORT: 3,
	PROJECT_SUBMODULE_EXPORT: 4,
	SASS_CSS_FILE: 5,
};

/**
 * This function composes URLs as they will appear in the final .js bundles.
 *
 * The getURL() function needs to use one prefix or another (eg: '../..' in
 * project exports vs '../../..' in npm exports) depending on what's being
 * bundled.
 *
 * You can pass an empty prefix if you don't want to use relative URLs at all
 * and rely instead in import maps at runtime to convert bare identifiers into
 * real paths.
 *
 * The web context path must start with '/' (eg: '/frontend-js-web').
 *
 * Depending on the type of URL that function may receive more or less
 * parameters:
 *
 * For CSS_EXPORT:
 *     `moduleName` is the path of the CSS file (in Node syntax)
 *     `hash` is the hash of the CSS file (omit it to avoid using a hash)
 *
 * For CSS_EXPORT_LOADER_MODULE:
 *     `moduleName` is the path of the CSS file (in Node syntax)
 *
 * For NPM_EXPORT:
 *     `moduleName` is the name of the module (in Node syntax)
 *
 * For PROJECT_DEFAULT_EXPORT:
 *     `moduleName` is not necessary
 *
 * For PROJECT_SUBMODULE_EXPORT:
 *     `moduleName` is the name of the submodule
 *
 * For SAAS_CSS_FILE:
 *     `moduleName` is the path of the CSS file relative to the web context
 *     `hash` is the hash of the CSS file (omit it to avoid using a hash)
 *
 */
export default function getURL(
	type,
	prefix,
	webContextPath,
	moduleName = undefined,
	hash = undefined
) {
	if (prefix.endsWith('/')) {
		throw new Error('Prefix must not end in /');
	}
	if (!webContextPath.startsWith('/')) {
		throw new Error('Web context path must start with /');
	}

	switch (type) {
		case URLType.CSS_EXPORT: {
			const baseModuleName = moduleName.replace(/\.css$/, '');

			return hash
				? `${prefix}${webContextPath}/__liferay__/css/${getFlatName(baseModuleName)}.(${hash}).css`
				: `${prefix}${webContextPath}/__liferay__/css/${getFlatName(baseModuleName, 'css')}`;
		}

		case URLType.CSS_EXPORT_LOADER_MODULE:
			return `${prefix}${webContextPath}/__liferay__/exports/${getFlatName(moduleName, 'js')}`;

		case URLType.NPM_EXPORT:
			return `${prefix}${webContextPath}/__liferay__/exports/${getFlatName(moduleName, 'js')}`;

		case URLType.PROJECT_DEFAULT_EXPORT:
			return `${prefix}${webContextPath}/__liferay__/index.js`;

		case URLType.PROJECT_SUBMODULE_EXPORT:
			return `${prefix}${webContextPath}/__liferay__/${moduleName}.js`;

		case URLType.SASS_CSS_FILE: {
			const baseModuleName = moduleName.replace(/\.css$/, '');

			return hash
				? `${prefix}${webContextPath}/${baseModuleName}.(${hash}).css`
				: `${prefix}${webContextPath}/${baseModuleName}.css`;
		}

		default:
			throw new Error(`Unknown type: ${type}`);
	}
}
