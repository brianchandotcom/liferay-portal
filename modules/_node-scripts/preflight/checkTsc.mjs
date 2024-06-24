/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';

import runTscChecks from '../tsc/runTscChecks.mjs';
import generateTscConfig from '../tsconfig/index.mjs';
import {LIFERAY_WORKING_BRANCH, getRootDir} from '../util/constants.mjs';

export async function checkTsc(all) {
	console.log('📜 Generating tsconfig files...');

	let commitHash;

	if (!all) {
		const {stdout} = await $`git rev-parse ${LIFERAY_WORKING_BRANCH}`;

		commitHash = stdout;
	}

	console.log('📜 Validating tsconfig files...');

	await generateTscConfig();

	console.log(`🕵️ Checking ${all ? 'all' : 'modified'} typescript files...`);

	const rootDir = await getRootDir();

	return await runTscChecks({baseDir: rootDir, commitHash});
}
