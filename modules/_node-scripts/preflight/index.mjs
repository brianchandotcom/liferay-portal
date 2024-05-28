/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {checkConfigFileNames} from './checkConfigFileNames.mjs';
import {checkPackageJSONFiles} from './checkPackageJSONFiles.mjs';
import {checkYarnLock} from './checkYarnLock.mjs';

/**
 * Runs the "preflight" checks (basically everything that is not already covered
 * by Prettier or ESLint).
 */
export default async function preflight() {
	const errors = [
		...(await checkConfigFileNames()),
		...(await checkPackageJSONFiles()),
		...(await checkYarnLock()),
	];

	if (errors.length) {
		console.log('Preflight check failed:');

		console.log(...errors);

		throw new Error();
	}
}
