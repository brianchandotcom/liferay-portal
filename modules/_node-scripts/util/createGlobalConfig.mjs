/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import crypto from 'crypto';

import {getProjectDirs} from './constants.mjs';
import objectSF from './objectSF.mjs';
import projectScopeRequire from './projectScopeRequire.mjs';

export async function createGlobalConfig(hashOnly = false) {
	const projectDirs = await getProjectDirs();

	let allDependencies = {};
	let allSymbols = {};
	let allImports = {};

	for (const projectDir of projectDirs) {
		const {
			exports = [],
			submodules,
			symbols,
		} = projectScopeRequire('./node-scripts.config.js', projectDir);

		const {dependencies, name} = projectScopeRequire(
			'./package.json',
			projectDir
		);

		for (const symbolPackage in symbols) {
			if (!exports.includes(symbolPackage)) {
				throw Error(
					`❌ 'node-scripts.config.js' is invalid. Only declare symbols for packages your module 'exports'. Reading '${symbolPackage}'.`
				);
			}
		}

		allDependencies = {...allDependencies, ...dependencies};
		allImports[name] = exports;

		if (submodules) {
			allImports[name].push(
				...Object.keys(submodules).map((item) => `./${item}`)
			);
		}

		if (symbols) {
			allSymbols = {...allSymbols, ...symbols};
		}
	}

	// Filters down modules to only those explicitly imported or if provides an export

	allImports = Object.keys(allImports).reduce((acc, moduleName) => {
		const val = allImports[moduleName];

		if (val.length || allDependencies[moduleName]) {
			acc[moduleName] = val;
		}

		return acc;
	}, {});

	const sha256 = crypto.createHash('sha256');

	const hash = sha256
		.update(JSON.stringify(allImports) + JSON.stringify(allSymbols))
		.digest('hex');

	return hashOnly ? hash : template(allImports, allSymbols, hash);
}

function template(imports, symbols, hash) {
	return `/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/** AUTO-GENERATED: DO NOT EDIT 
 * 
 * This is generated based on each module's \`node-scripts.config.js\`
 * 
*/

module.exports = {
	hash: '${hash}',
	imports: ${objectSF(imports)},
	
	// The following symbols override the actual symbols that listed packages export. This is
	// sometimes necessary when:
	//
	// 1. The build cannot infer the exported symbols because the package cannot be required from
	//    Node.js (eg: if it references \`window\` or any other browser API not available).
	// 2. The inferred exported symbols are wrong (eg: polymorphic packages).
	// 3. The package must re-export everything as \`default\` so that it can be directly imported
	//    using ES syntax (eg: \`react\` since it can be imported as \`import React from 'react';\` even
	//    though it is a CJS package that doesn't really export any \`default\` symbol).
	//
	// For number 3 note that tools like \`webpack\` sometimes rely on the \`__esModule\` symbol to
	// mimic that behavior. However we prefer to make it explicit in this file due to how much
	// headaches the \`__esModule\` inferences usually cause when they don't work correctly.
	//
	// The way to obtain these symbols is different for each package but it usually starts with a
	// runtime error in the browser and a following investigation on what the package is really
	// exporting when used from a browser.
	//

	symbols: ${objectSF(symbols)},
};`;
}
