/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import checkProject from '../tsc/checkProject.mjs';
import checkProjects from '../tsc/checkProjects.mjs';
import extractProjectDirs from '../tsc/extractProjectDirs.mjs';
import {getProjectDirs, getRootDir} from '../util/constants.mjs';
import getNamedArguments from '../util/getNamedArguments.mjs';
import gitUtil from '../util/gitUtil.mjs';

export default async function main() {
	const {all, currentBranch, localChanges} = getNamedArguments({
		all: '--all',
		currentBranch: '--current-branch',
		localChanges: '--local-changes',
	});

	const cwd = path.resolve('.');

	if (cwd === (await getRootDir())) {
		let projectDirs;

		if (currentBranch) {
			projectDirs = await extractProjectDirs(
				await gitUtil('current-branch')
			);
		}
		else if (localChanges) {
			projectDirs = await extractProjectDirs(
				await gitUtil('local-changes')
			);
		}
		else {
			if (!all) {
				console.log(`
⚠️ Checking all projects takes long, you may want to use --local-changes or --current-branch arguments
`);
			}

			projectDirs = await getProjectDirs();
		}

		console.log(`ℹ️ Going to check ${projectDirs.length} projects`);

		await checkProjects(projectDirs, false);
	}
	else {
		if (currentBranch || localChanges) {
			console.error(`
❌ Arguments --current-branch or --local-changes are not valid when checking a single project.
`);

			process.exit(2);
		}

		await checkProject(cwd, false);
	}
}
