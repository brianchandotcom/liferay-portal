/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import getNamedArguments from '../util/getNamedArguments.mjs';
import getYarnWorkspaceProjects from '../util/getYarnWorkspaceProjects.mjs';
import runConcurrentTasks, {
	MAX_CONCURRENT_TASKS,
} from '../util/runConcurrentTasks.mjs';
import runJest from './jest/runJest.mjs';

export default async function () {
	const {sync} = getNamedArguments({
		sync: '--sync',
	});

	const originalNodeEnv = process.env.NODE_ENV;

	process.env.NODE_ENV = 'test';

	const args = process.argv.slice(3);

	/**
	 * When using 'yarn run ...' it sets the cwd to the nearest package.json
	 */
	let cwd = process.env.INIT_CWD;

	if (!cwd) {
		cwd = process.cwd();
	}

	const projects = await getYarnWorkspaceProjects();

	/**
	 * Map containing the path to the project and the environment variables
	 * to be used when running the tests.
	 */
	const testableProjectsMap = new Map();

	/**
	 * Filter out projects that do not have `node-scripts test`
	 */
	for (const projectPath of projects) {

		// Check if deeply nested passed a project root or check if shallowly
		// nested before several project roots

		if (
			cwd.includes(projectPath) ||
			projectPath.includes(process.env.INIT_CWD)
		) {
			const packageJson = path.join(projectPath, 'package.json');
			const pkgJsonContents = await fs.readFile(packageJson, 'utf8');

			if (pkgJsonContents.includes('node-scripts test')) {
				const pkgJson = JSON.parse(pkgJsonContents);

				testableProjectsMap.set(
					projectPath,
					getEnvVars(pkgJson.scripts.test)
				);
			}
		}
	}

	const totalTestableProjects = testableProjectsMap.size;

	if (totalTestableProjects === 1) {
		const [[projectPath, envObj]] = testableProjectsMap.entries();

		await runJest({
			cliFlags: args,
			cwd: projectPath,
			execaConfig: {
				env: envObj,
				stdio: 'inherit',
			},
		});
	}
	else {
		console.log(
			`ℹ️ Testing ${totalTestableProjects} projects in ${sync ? 'series' : 'parallel'}.`
		);

		const asyncItems = [];

		for (const [projectPath, envObj] of testableProjectsMap) {
			asyncItems.push(async (stdio = 'pipe') => {
				console.log(`🧪 Testing ${path.basename(projectPath)}`);

				const {all, failed} = await runJest({
					cliFlags: args,
					cwd: projectPath,
					execaConfig: {
						all: true,
						env: envObj,
						reject: false,
						stdio,
					},
				});

				console.log(
					`${!failed ? '✅ PASSED' : '❌ FAILED'} ${path.basename(projectPath)}`
				);

				return all;
			});
		}

		if (sync) {
			for (const task of asyncItems) {
				await task('inherit');
			}
		}
		else {
			console.log(`> Running in groups of ${MAX_CONCURRENT_TASKS}.`);

			const results = await runConcurrentTasks(asyncItems);

			console.log(results.join('\n'));
		}
	}

	process.env.NODE_ENV = originalNodeEnv;
}

function getEnvVars(value) {
	return value
		.split(' ')
		.filter((part) => part.includes('='))
		.reduce((acc, part) => {
			const [key, value] = part.split('=');
			acc[key] = value;

			return acc;
		}, {});
}
