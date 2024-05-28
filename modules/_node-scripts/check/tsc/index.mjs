/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import os from 'os';
import path from 'path';
import resolve from 'resolve';

import {
	SRC_TSCONFIG_PATH,
	getProjectDirs,
	getRootDir,
} from '../../util/constants.mjs';
import fileExists from '../../util/fileExists.mjs';
import forkModule from '../../util/forkModule.mjs';

export default async function main() {
	const cwd = path.resolve('.');
	const rootDir = await getRootDir();

	if (cwd === rootDir) {
		const projectGroups = [[]];

		const cpuCount = os.cpus().length;

		for (const projectDir of await getProjectDirs()) {
			if (!(await fileExists(path.join(projectDir, SRC_TSCONFIG_PATH)))) {
				continue;
			}

			let group = projectGroups[projectGroups.length - 1];

			if (group.length === cpuCount) {
				group = [];

				projectGroups.push(group);
			}

			group.push(projectDir);
		}

		console.log(
			`ℹ️ ${cpuCount} CPUs detected: launching tsc in groups of ${cpuCount} projects`
		);

		for (const projectGroup of projectGroups) {
			await Promise.all(
				projectGroup.map((projectDir) => {
					console.log(
						`🕵️ Checking ${path.relative(rootDir, projectDir)}`
					);

					return runTsc(projectDir);
				})
			);
		}
	}
	else {
		await runTsc(cwd);
	}
}

async function runTsc(cwd) {
	const tscPath = resolve.sync('typescript/bin/tsc', {basedir: '.'});

	await forkModule(
		tscPath,
		[
			'-b',
			path.join(
				'src',
				'main',
				'resources',
				'META-INF',
				'resources',
				'tsconfig.json'
			),
			...process.argv.slice(3),
		],
		{
			cwd,
			stdio: 'inherit',
		}
	);
}
