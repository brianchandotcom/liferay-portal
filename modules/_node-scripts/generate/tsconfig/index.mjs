/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import getMainEntryPoints from '../../configuration/getMainEntryPoints.mjs';
import getProjectDependencies from '../../configuration/getProjectDependencies.mjs';
import getProjectDescription from '../../configuration/getProjectDescription.mjs';
import {getProjectDirs, getRootDir, SRC_PATH} from '../../util/constants.mjs';
import fileExists from '../../util/fileExists.mjs';
import writeProjectTsconfig from './writeProjectTsconfig.mjs';

export default async function main() {
	const rootDir = await getRootDir();

	if (path.resolve('.') !== rootDir) {
		console.error(`
                                 ⚠️    WARNING    ⚠️

Since generate:tsconfig is a global task, it will be run from the root of liferay-portal even
though you have invoked it from a project directory.
`);
	}

	const [
		mainEntryPoints,
		projectDirs,
	] = await Promise.all([
		getMainEntryPoints(),
		getProjectDirs()
	]);

	await Promise.all([
		...projectDirs.map(
			projectDir => processProject(projectDir, mainEntryPoints)
		)
	]);
}

async function processProject(projectDir, mainEntryPoints) {
	if (!await fileExists(path.join(projectDir, SRC_PATH))) {
		return;
	}

	const [
		projectDependencies,
		projectDescription,
	] = await Promise.all([
		getProjectDependencies(projectDir),
		getProjectDescription(projectDir),
	]);

	await writeProjectTsconfig(mainEntryPoints, projectDependencies, projectDescription, projectDir);
}
