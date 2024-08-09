/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import format from '../format/format.mjs';
import runPreflight from '../preflight/runPreflight.mjs';
import checkProjects from '../tsc/checkProjects.mjs';
import extractProjectDirs from '../tsc/extractProjectDirs.mjs';
import visitOutdatedTsconfigFiles from '../tsconfig/visitOutdatedTsconfigFiles.mjs';
import {getRootDir} from '../util/constants.mjs';
import gitUtil from '../util/gitUtil.mjs';

export default async function main() {
	const rootDir = await getRootDir();
	const cwd = path.resolve('.');

	if (cwd !== rootDir) {
		console.error(
			`❌ Command check:ci can only be run from 'modules' directory.`
		);
		process.exit(2);
	}

	let checksPassed = true;

	// Preflight checks

	console.log('\n⚙️ Running preflight checks...');

	const preflightErrors = await runPreflight();

	if (preflightErrors.length) {
		console.error(
			preflightErrors.map((error) => `   · ${error}`).join('\n')
		);

		checksPassed = false;
	}

	// Tsconfig checks

	console.log('\n⚙️ Checking outdated tsconfig.json files ...');

	await visitOutdatedTsconfigFiles((filePath) => {
		console.error(`   · ${path.relative(rootDir, filePath)} is outdated`);

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

	const formatOutput = await format(false, formatFiles);

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
