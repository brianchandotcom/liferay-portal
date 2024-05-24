/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';
import resolve from 'resolve';

import {getProjectDirs, getRootDir, SRC_TSCONFIG_PATH} from '../../util/constants.mjs';
import fileExists from '../../util/fileExists.mjs';
import forkModule from '../../util/forkModule.mjs';

export default async function main() {
	const cwd = path.resolve('.');
	const rootDir = await getRootDir();

	if (cwd === rootDir) {
		for (const projectDir of await getProjectDirs()) {
			if (!await fileExists(path.join(projectDir, SRC_TSCONFIG_PATH ))) {
				continue;
			}

			console.log(`🕵️ Checking ${path.relative(rootDir, projectDir)}`);

			await runTsc(projectDir);
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
			path.join('src', 'main', 'resources', 'META-INF', 'resources', 'tsconfig.json'),
			...process.argv.slice(3)
		], 
		{
			cwd: cwd,
			stdio: 'inherit',
		}
	);
}
