/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import projectScopeRequire from '../util/projectScopeRequire.mjs';

/**
 * @returns
 * An object containing the project relative path of the possible entry points, like this:
 *
 * {
 *   main: 'src/main/resources/META-INF/resources/index.js',
 *   typescript: 'src/main/resources/META-INF/resources/index.d.ts'
 * }
 */
export default function getProjectEntryPoints(projectDir = '.') {
	const {main, typescript} = projectScopeRequire(
		'./node-scripts.config.js',
		projectDir
	);

	const entryPoints = {};

	if (main) {
		entryPoints.main = main;
		entryPoints.typescript = main;
	}

	if (typescript && typescript.main) {
		entryPoints.typescript = typescript.main;
	}

	return entryPoints;
}
