/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';

import runTscChecks from '../tsc/runTscChecks.mjs';
import generateTscConfig from '../tsconfig/index.mjs';
import {getRootDir} from '../util/constants.mjs';

export async function checkTsc() {
	console.log('📜 Generating tsconfig files...');

	await generateTscConfig();

	const rootDir = await getRootDir();

	console.log('🕵️ Checking modified typescript files...');

	let commitHash = 'master';

	if (process.env.LIFERAY_NPM_SCRIPTS_WORKING_BRANCH_NAME) {
		const {stdout} =
			await $`git rev-parse ${process.env.LIFERAY_NPM_SCRIPTS_WORKING_BRANCH_NAME}`;

		commitHash = stdout;
	}

	return await runTscChecks({baseDir: rootDir, commitHash});
}
