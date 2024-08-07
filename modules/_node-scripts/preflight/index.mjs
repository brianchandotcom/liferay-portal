/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {checkConfigFileNames} from './checkConfigFileNames.mjs';
import {checkNodeScriptsHash} from './checkNodeScriptsHash.mjs';
import {checkPackageJSONFiles} from './checkPackageJSONFiles.mjs';
import {checkYarnLock} from './checkYarnLock.mjs';

/**
 * Run "lightweight" global checks not implemented by ESLint or Prettier.
 *
 * Since preflight checks are global and there are not switches to choose subsets of things to
 * check, they must not take a long time to run.
 *
 * Any long check (eg: TypeScript) must be moved to its own command and invoked explicitly from the
 * outer layer.
 */
export default async function preflight() {
	const results = await Promise.all([
		checkConfigFileNames(),
		checkPackageJSONFiles(),
		checkYarnLock(),
		checkNodeScriptsHash(),
	]);

	const errors = results.flat();

	if (errors.length) {
		console.error(`
❌ Preflight check failed:
${errors.map((error) => `   · ${error}`).join('\n')}
`);

		throw new Error();
	}
}
