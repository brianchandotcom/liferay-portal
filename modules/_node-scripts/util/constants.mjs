/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';
import url from 'url';

import fileExists from './fileExists.mjs';

const __dirname = path.dirname(url.fileURLToPath(import.meta.url));

export const SRC_PATH = path.join(
	'src',
	'main',
	'resources',
	'META-INF',
	'resources'
);
export const SRC_LANGUAGE_JSON_PATH = path.join(SRC_PATH, 'language.json');
export const SRC_TSCONFIG_PATH = path.join(SRC_PATH, 'tsconfig.json');

export const BUILD_PATH = path.join('build', 'node', 'packageRunBuild');
export const BUILD_RESOURCES_PATH = path.join(BUILD_PATH, 'resources');
export const BUILD_MAIN_EXPORTS_PATH = path.join(
	BUILD_RESOURCES_PATH,
	'__liferay__'
);
export const BUILD_CSS_EXPORTS_PATH = path.join(BUILD_MAIN_EXPORTS_PATH, 'css');
export const BUILD_LANGUAGE_JSON_PATH = path.join(
	BUILD_RESOURCES_PATH,
	'language.json'
);
export const BUILD_NPM_EXPORTS_PATH = path.join(
	BUILD_MAIN_EXPORTS_PATH,
	'exports'
);
export const BUILD_SASS_CACHE_PATH = path.join(
	BUILD_RESOURCES_PATH,
	'.sass-cache'
);

export const NODE_SCRIPTS_PATH = path.resolve(__dirname, '..');

export const WORK_PATH = path.join('build', 'node-scripts');
export const WORK_EXPORT_PATH = path.join(WORK_PATH, 'export');
export const WORK_IMPORT_PATH = path.join(WORK_PATH, 'import');

export const BUNDLE_REPORTS_PATH = path.join('build', 'bundle-reports');

const IGNORED_PROJECT_DIRS = ['modules'];
const NO_RECURSE_PROJECT_DIRS = [
	'clay',
	'_node-scripts',
	'build',
	'classes',
	'node_modules',
	'osb-faro',
	'sdk',
	'test',
	'vercel',
];

export async function getBuildPropertiesPath() {
	return path.resolve(await getRootDir(), '..', 'build.properties');
}

let cachedProjectDirs;

export async function getProjectDirs(dir = undefined) {
	if (dir === undefined) {
		if (!cachedProjectDirs) {
			cachedProjectDirs = await getProjectDirs(await getRootDir());
		}

		return cachedProjectDirs;
	}

	const projectDirs = [];

	for (const dirent of await fs.readdir(dir, {withFileTypes: true})) {
		if (
			dirent.name === 'package.json' &&
			!IGNORED_PROJECT_DIRS.includes(path.basename(dir))
		) {
			projectDirs.push(path.resolve(dir));
			break;
		}
		else if (NO_RECURSE_PROJECT_DIRS.includes(dirent.name)) {
			continue;
		}
		else if (dirent.isDirectory()) {
			for (const childDir of await getProjectDirs(
				path.resolve(dir, dirent.name)
			)) {
				projectDirs.push(childDir);
			}
		}
	}

	return projectDirs;
}

let cachedRootDir;

export async function getRootDir() {
	if (!cachedRootDir) {
		const __dirname = path.dirname(url.fileURLToPath(import.meta.url));

		cachedRootDir = path.resolve(__dirname, '..', '..');

		if (!(await fileExists(path.join(cachedRootDir, 'yarn.lock')))) {
			throw new Error(
				`Root project folder is no longer at ${cachedRootDir}. Please check if yarn.lock` +
					' has been deleted or modules/_node-scripts/util/constants.js has been moved.'
			);
		}
	}

	return cachedRootDir;
}
