/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs';
import path from 'path';

import getYarnWorkspaceProjects from '../../util/getYarnWorkspaceProjects.mjs';

/**
 * Where source really lives, relative to the directory containing the
 * "package.json" file.
 */
const SRC_PATH = ['src', 'main', 'resources', 'META-INF', 'resources'];

/**
 * Returns a Jest "moduleNameMapper" configuration that enables tests to
 * `import` modules from other projects.
 *
 * For example, in order for "segments/segments-web" to `import
 * {something} from 'frontend-js-web'`, we need mappings like:
 *
 *    "^frontend-js-web$": "<rootDir>../../frontend-js/frontend-js-web/src/main/resources/META-INF/resources/index.es.js"
 *
 * and:
 *
 *    "^frontend-js-web/(.*)": "<rootDir>../../frontend-js/frontend-js-web/src/main/resources/META-INF/resources/$1"
 *
 * We create such mappings by:
 *
 *    1. Iterating over projects identified by the Yarn workspace globs
 *       defined in the top-level "modules/package.json".
 *    2. Selecting only projects which have a "package.json" with a "main"
 *       property that points to an existing file under
 *       "src/main/resources/META-INF/resources".
 *
 * @see https://jestjs.io/docs/en/configuration#modulenamemapper-object-string-string
 */
async function getJestModuleNameMapper({cwd = process.cwd()}) {

	// Note a limitation here: when running on a project under
	// "modules/private", the `root` will be "modules", and only projects under
	// "modules/apps" (not "modules/private/apps"), will be considered. This
	// means that, for now, tests in projects in "modules/private" can import
	// from projects under "modules/apps" but not from those under
	// "modules/private/apps".

	const mappings = {};

	const projects = await getYarnWorkspaceProjects();

	projects.forEach((project) => {
		const packageJson = path.join(project, 'package.json');
		const {main, name} = JSON.parse(fs.readFileSync(packageJson, 'utf8'));
		if (main) {
			const entry = path.join(project, ...SRC_PATH, main);

			// Handle typical formats for "main":
			//
			// - index        -> index.js
			// - index.es     -> index.es.js
			// - index.es.js  -> index.es.js
			// - index.js     -> index.js
			// - index.js     -> index.ts

			const candidates = [entry];
			if (entry.endsWith('.js')) {
				candidates.push(entry.replace(/\.js$/, '.ts'));
			}
			else {
				candidates.push(entry + '.js');
			}
			for (let i = 0; i < candidates.length; i++) {
				const candidate = candidates[i];
				if (fs.existsSync(candidate)) {
					const resources = path.relative(
						cwd,
						path.join(project, ...SRC_PATH)
					);
					mappings[`^${name}$`] = candidate;
					mappings[`^${name}/(.*)`] =
						`${path.join(cwd, resources)}/$1`;
					break;
				}
			}
		}
	});

	return mappings;
}
export default getJestModuleNameMapper;
