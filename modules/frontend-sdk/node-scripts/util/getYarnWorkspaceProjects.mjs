/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fg from 'fast-glob';
import fs from 'fs';
import path from 'path';

import {MODULES_DIR} from './locations.mjs';
import print from './print.mjs';

/**
 * Returns a list of workspaces.
 *
 * These are directories containing "package.json" files in locations
 * that match the top-level "workspaces" globs defined in
 * "modules/package.json".
 */
export default async function getYarnWorkspaceProjects() {
	const cwd = process.cwd();

	try {
		process.chdir(MODULES_DIR);

		const {workspaces} = JSON.parse(
			fs.readFileSync('package.json', 'utf8')
		);

		const projects = await fg(
			workspaces.packages.map((item) => `${item}/package.json`),
			{
				ignore: [
					'**/node_modules/**',
					'**/.releng/**',
					'**/build',
					'**/classes',
					'**/src',
					'**/test',
				],
			}
		);

		return projects.map((project) =>
			path.join(MODULES_DIR, path.dirname(project))
		);
	}
	catch (error) {
		print(
			0,
			true,
			print.error('\nERROR:'),
			`Could not retrieve the list of yarn workspace projects: ${error}.\n`
		);
	}
	finally {
		process.chdir(cwd);
	}

	return [];
}
