/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import {getRootDir} from '../util/constants.mjs';
import projectScopeRequire from '../util/projectScopeRequire.mjs';

/**
 * @returns
 * Something like:
 *
 * {
 *   'dom-align': {
 *      external: true,
 *		webContextPath: 'frontend-js-dependencies-web'
 *	 },
 *   '@liferay/frontend-js-dependencies-web': {
 *      external: false,
 *		webContextPath: 'frontend-js-dependencies-web',
 *	  }
 * }
 */
export default async function getGlobalImports() {
	const rootDir = await getRootDir();

	const {imports} = projectScopeRequire(
		path.join(rootDir, 'node-scripts.config.js')
	);

	const externalImports = {};
	const rawProjectImports = {};

	for (const providerName of Object.keys(imports)) {
		rawProjectImports[providerName] = {
			external: false,
			webContextPath: getWebContextPath(providerName),
		};

		for (const packageName of imports[providerName]) {
			externalImports[packageName] = {
				external: true,
				webContextPath: getWebContextPath(providerName),
			};
		}
	}

	return {
		...externalImports,
		...rawProjectImports,
	};
}

function getWebContextPath(packageName) {

	//
	// We cannot guarantee that the web context is the same as the package name without @liferay
	// because we don't have any SF requiring that.
	//
	// However, doing it the safe way requires a lot of horse power to compute (need to read every
	// project in liferay-portal and parse its bnd.bnd file).
	//
	// Additionally, nearly all deps live in frontend-js-dependencies-web, which makes the need to
	// make this safer even more superfluous.
	//

	if (packageName.startsWith('@liferay')) {
		return packageName.replace('@liferay/', '');
	}

	return packageName;
}
