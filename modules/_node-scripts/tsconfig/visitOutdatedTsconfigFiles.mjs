/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import getProjectDependencies from '../configuration/getProjectDependencies.mjs';
import getProjectDescription from '../configuration/getProjectDescription.mjs';
import getProjectEntryPoints from '../configuration/getProjectEntryPoints.mjs';
import {SRC_PATH, getProjectDirs, getRootDir} from '../util/constants.mjs';
import fileExists from '../util/fileExists.mjs';
import visitProjectTsconfig from './visitProjectTsconfig.mjs';

export default async function visitOutdatedTsconfigFiles(visitorFunction) {
	const rootDir = await getRootDir();

	const [projectDirs] = await Promise.all([getProjectDirs()]);

	const projectsEntryPoints = await getProjectsEntryPoints(
		projectDirs,
		rootDir
	);

	await Promise.all([
		...projectDirs.map((projectDir) =>
			processProject(projectDir, projectsEntryPoints, visitorFunction)
		),
	]);
}

/**
 * @returns
 * {
 *	 '@liferay/frontend-js-react-web': {
 *		dir: 'modules/apps/frontend-js/frontend-js-react-web',
 *		path: 'src/main/resources/META-INF/resources/js/index.ts'
 *	 },
 *	 ...
 * }
 */
async function getProjectsEntryPoints(projectDirs, rootDir) {
	return projectDirs.reduce((projectsEntryPoints, projectDir) => {
		const {name} = getProjectDescription(projectDir);
		const {typescript} = getProjectEntryPoints(projectDir);

		projectsEntryPoints[name] = {
			dir: path.relative(rootDir, projectDir),
			path: typescript,
		};

		return projectsEntryPoints;
	}, {});
}

async function processProject(
	projectDir,
	projectsEntryPoints,
	visitorFunction
) {
	if (!(await fileExists(path.join(projectDir, SRC_PATH)))) {
		return;
	}

	const [projectDependencies, projectDescription] = await Promise.all([
		getProjectDependencies(projectDir),
		getProjectDescription(projectDir),
	]);

	await visitProjectTsconfig(
		visitorFunction,
		projectsEntryPoints,
		projectDependencies,
		projectDescription,
		projectDir
	);
}
