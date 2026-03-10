/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import doFormat from '../util/format/doFormat.mjs';
import gitUtil from '../util/gitUtil.mjs';
import {MODULES_DIR} from '../util/locations.mjs';
import doPreflight from '../util/preflight/doPreflight.mjs';
import checkProjects from '../util/tsc/checkProjects.mjs';
import extractProjectDirs from '../util/tsc/extractProjectDirs.mjs';
import visitOutdatedTsconfigFiles from '../util/tsconfig/visitOutdatedTsconfigFiles.mjs';

export default async function main() {
	const cwd = path.resolve('.');

	if (cwd !== MODULES_DIR) {
		console.error(
			`❌ Command check:ci can only be run from 'modules' directory.`
		);
		process.exit(2);
	}

	let checksPassed = true;

	// Preflight checks

	console.log('\n⚙️ Running preflight checks...');

	const preflightErrors = await doPreflight();

	if (preflightErrors.length) {
		console.error(
			preflightErrors.map((error) => `   · ${error}`).join('\n')
		);

		checksPassed = false;
	}

	// Tsconfig checks

	console.log('\n⚙️ Checking outdated tsconfig.json files ...');

	await visitOutdatedTsconfigFiles((filePath) => {
		console.error(
			`   · ${path.relative(MODULES_DIR, filePath)} is outdated`
		);

		checksPassed = false;
	});

	// TypeScript checks

	console.log('\n⚙️ Running TypeScript checks on modified files...');

	const tscProjectDirs = await extractProjectDirs(
		await gitUtil('current-branch')
	);

	if (!(await checkProjects(tscProjectDirs, false))) {
		checksPassed = false;
	}

	// SF checks

	console.log('\n⚙️ Running format checks on modified files...');

	const formatFiles = await gitUtil('current-branch');

	const formatOutput = await doFormat(false, formatFiles);

	if (formatOutput) {
		console.error(
			formatOutput
				.split('\n')
				.map((line) => `   ${line}`)
				.join('\n')
		);
		checksPassed = false;
	}

	// Finalization

	if (!checksPassed) {
		console.error('❌ CI checks failed.');
		process.exit(1);
	}
}
