/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {SRC_PATH, getProjectDirs, getRootDir} from '../util/constants.mjs';

/**
 * @returns
 * {
 *	 '@liferay/frontend-js-react-web': {
 *		base: 'modules/apps/frontend-js/frontend-js-react-web', 
 *		main: 'src/main/resources/META-INF/resources/js/index.ts'
 *	 },
 *	 ...
 * }
 */
export default async function getMainEntryPoints() {
	const projectDirs = await getProjectDirs();

	const projectDirAndConfigs = await Promise.all(
		// TODO: use reduce instead of getProjectDirAndConfig
		projectDirs.map(getProjectDirAndConfig)
	);

	const mainEntryPoints = {};
	const rootDir = await getRootDir();

	for (const {config, projectDir} of projectDirAndConfigs) {
		if (!config.projectMain) {
			continue;
		}

		mainEntryPoints[config.projectName] = {
			base: path.relative(rootDir, projectDir),
			main: config.projectMain
		};
	}

	return mainEntryPoints;

}

// TODO: use getProjectMains if possible
async function getProjectDirAndConfig(projectDir) {
	try {
		let projectMain;

		const packageJson =
			JSON.parse(await fs.readFile(path.join(projectDir, 'package.json'), 'utf-8'));

		if (packageJson.main) {
			projectMain = `${SRC_PATH}/${packageJson.main}`;
		}

		try {
			const config = await import(path.join(projectDir, 'node-scripts.config.js'));

			const {typescript} = config.default;

			if (typescript && typescript.main) {
				projectMain = typescript.main;
			}
		}
		catch(error) {
			if (error.code !== 'ENOENT') {
				throw error
			}
		}

		return {
			config: {
				projectMain,
				projectName: packageJson.name,
			},
			projectDir,
		};
	}
	catch(error) {
		if (error.code !== 'ENOENT') {
			throw error;
		}

		return {};
	}
}
