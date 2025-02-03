/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import projectScopeRequire from '../util/projectScopeRequire.mjs';

/**
 * @returns
 * An object containing the project relative path of the possible entry points, like this:
 *
 * {
 *   main: 'src/main/resources/META-INF/resources/index.js',
 *   submodules: {
 *   	foo: 'src/main/resources/META-INF/resources/foo.js'
 *   },
 *   typescript: {
 *   	main: 'src/main/resources/META-INF/resources/index.d.ts'
 *   }
 * }
 */
export default function getProjectEntryPoints(projectDir = '.') {
	const {main, submodules, typescript} = projectScopeRequire(
		'./node-scripts.config.js',
		projectDir
	);

	const projectDirectory = path.basename(projectDir);

	const entryPoints = {};

	if (main) {
		verifyResourcePath(main, 'main', projectDirectory);

		entryPoints.main = main;
		entryPoints.typescript = main;
	}

	if (typescript && typescript.main) {
		verifyResourcePath(typescript.main, 'typescript', projectDirectory);

		entryPoints.typescript = typescript.main;
	}

	if (submodules) {
		Object.values(submodules).forEach((submodulePath) => {
			verifyResourcePath(submodulePath, 'submodule', projectDirectory);
		});

		entryPoints.submodules = submodules;
	}

	return entryPoints;
}

function verifyResourcePath(resourcePath, type, projectDirectory) {
	if (
		resourcePath.startsWith('./src/main/resources/META-INF/resources') ||
		resourcePath.startsWith('./src/node/')
	) {
		return true;
	}

	throw Error(
		`❌ '${type}' path '${resourcePath}' is not allowed for '${projectDirectory}', it must be located under './src/main/resources/META-INF/resources/*'.`
	);
}
