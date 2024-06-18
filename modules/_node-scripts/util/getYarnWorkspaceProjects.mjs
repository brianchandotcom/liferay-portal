/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fg from 'fast-glob';
import fs from 'fs';
import path from 'path';

import {getRootDir} from './constants.mjs';

/**
 * Returns a list of workspaces.
 *
 * These are directories containing "package.json" files in locations
 * that match the top-level "workspaces" globs defined in
 * "modules/package.json".
 */
export default async function getYarnWorkspaceProjects() {
	const root = await getRootDir();

	if (root) {
		const cwd = process.cwd();

		try {
			process.chdir(root);

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
				path.join(root, path.dirname(project))
			);
		}
		catch (error) {
			console.log(`getYarnWorkspaceProjects(): error \`${error}\``);
		}
		finally {
			process.chdir(cwd);
		}
	}

	return [];
}
