/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fg from 'fast-glob';
import fs from 'fs/promises';

import getNamedArguments from '../../getNamedArguments.mjs';
import {TSC_BUILDINFO_DIR} from '../../locations.mjs';
import print, {printDuration} from '../../print.mjs';
import checkProjects from '../tsc/checkProjects.mjs';

export default async function formatTypeScript(projectDirs) {
	const start = Date.now();

	const {ignoreTypescript} = getNamedArguments({
		ignoreTypescript: '--ignore-typescript',
	});

	if (ignoreTypescript) {
		return true;
	}

	let checksPassed = true;

	print(
		1,
		print.subTitle(
			`> Checking ${projectDirs.length} TypeScript projects...\n`
		)
	);

	if (process.env.CI) {
		const stats = await getTsBuildInfoStats();

		if (stats) {
			print(
				2,
				print.info('INFO:'),
				`Oldest '.tsbuildinfo' file is from`,
				print.bold(stats.oldestMtime),
				`\n      Newest '.tsbuildinfo' file is from`,
				print.bold(stats.newestMtime),
				'\n'
			);
		}
		else {
			print(
				2,
				print.warning('WARNING:'),
				`No '.tsbuildinfo' files could be found; expect TypeScript checks to take a long time\n`
			);
		}
	}

	if (!(await checkProjects(projectDirs))) {
		checksPassed = false;
	}

	printDuration(start, 1, 'TypeScript checks');

	return checksPassed;
}

async function getTsBuildInfoStats() {
	let oldestMtime;
	let newestMtime;

	const tsBuildInfoFiles = await fg(['*.tsbuildinfo', '**/*.tsbuildinfo'], {
		absolute: true,
		cwd: TSC_BUILDINFO_DIR,
	});

	for (const tsBuildInfoFile of tsBuildInfoFiles) {
		const {mtime} = await fs.stat(tsBuildInfoFile);

		if (!oldestMtime) {
			oldestMtime = mtime;
			newestMtime = mtime;

			continue;
		}

		if (mtime < oldestMtime) {
			oldestMtime = mtime;
		}

		if (mtime > newestMtime) {
			newestMtime = mtime;
		}
	}

	return oldestMtime
		? {
				newestMtime,
				oldestMtime,
			}
		: undefined;
}
