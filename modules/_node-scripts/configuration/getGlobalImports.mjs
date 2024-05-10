import path from 'path';

import getRootDir from '../util/getRootDir.mjs';
import require from '../util/require.mjs';

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

	const {imports} = require(path.join(rootDir, 'node-scripts.config.js'));

	const externalImports = {};
	const rawProjectImports = {};

	for (const providerName of Object.keys(imports)) {
		rawProjectImports[providerName] = {
			external: false,
			webContextPath: getWebContextPath(providerName)
		};

		for (const packageName of imports[providerName]) {
			externalImports[packageName] = {
				external: true,
				webContextPath: getWebContextPath(providerName)
			};
		}
	}

	return {
		...externalImports,
		...rawProjectImports
	};
}

function getWebContextPath(packageName) {
	if (packageName.startsWith('@liferay')) {
		return packageName.replace('@liferay/', '');
	}
	
	return packageName;
}
