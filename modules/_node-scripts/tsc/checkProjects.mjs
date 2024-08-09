/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import os from 'os';
import path from 'path';

import {SRC_TSCONFIG_PATH, getRootDir} from '../util/constants.mjs';
import fileExists from '../util/fileExists.mjs';
import runConcurrentTasks from '../util/runConcurrentTasks.mjs';
import checkProject from './checkProject.mjs';

/**
 * Run TypeScript checks in several projects in parallel.
 *
 * @returns object|boolean
 * An object containing project directories as keys and outputs of tsc as values if captureOutput is
 * passed as true or a boolean specifying if all checks succeeded otherwise.
 */
export default async function checkProjects(projectDirs, captureOutput) {
	const cpuCount = os.cpus().length;

	console.log(
		`ℹ️ A total of ${cpuCount} CPUs were detected: launching tsc using ${cpuCount} workers`
	);

	const rootDir = await getRootDir();

	const outputs = {};
	let allChecksPassed = true;

	await runConcurrentTasks(
		projectDirs.map((projectDir) => async () => {
			if (!(await fileExists(path.join(projectDir, SRC_TSCONFIG_PATH)))) {
				return;
			}

			let icon = '✅';
			let output = await checkProject(projectDir, true);

			output = output.trim();

			if (output) {
				allChecksPassed = false;
				icon = '❌';
				output = `\n${output
					.split('\n')
					.map((line) => `   ${line}`)
					.join('\n')}\n`;
			}

			if (captureOutput) {
				outputs[projectDir] = output;
			}
			else {
				console.log(
					`${icon} Checked ${path.relative(rootDir, projectDir)}${output}`
				);
			}
		})
	);

	return captureOutput ? outputs : allChecksPassed;
}
