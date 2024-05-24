/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import fileExists from './fileExists.mjs';

export const SRC_PATH = path.join(
	'src',
	'main',
	'resources',
	'META-INF',
	'resources'
);
export const SRC_TSCONFIG_PATH = path.join(SRC_PATH, 'tsconfig.json');

export const BUILD_PATH = path.join('build', 'node', 'packageRunBuild');
export const BUILD_RESOURCES_PATH = path.join(BUILD_PATH, 'resources');
export const BUILD_MAIN_EXPORTS_PATH = path.join(
	BUILD_RESOURCES_PATH,
	'__liferay__'
);
export const BUILD_CSS_EXPORTS_PATH = path.join(BUILD_MAIN_EXPORTS_PATH, 'css');
export const BUILD_NPM_EXPORTS_PATH = path.join(
	BUILD_MAIN_EXPORTS_PATH,
	'exports'
);

export const WORK_PATH = path.join('build', 'node-scripts');
export const WORK_EXPORT_PATH = path.join(WORK_PATH, 'export');

const IGNORED_PROJECT_DIRS = ['modules'];
const NO_RECURSE_PROJECT_DIRS = [
	'_node-scripts', 'build', 'classes', 'node_modules', 'osb-faro', 'osb-site-initializer-evp',
	'sdk', 'test'
];

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
		if (dirent.name === 'package.json' && !IGNORED_PROJECT_DIRS.includes(path.basename(dir))) {
			projectDirs.push(path.resolve(dir));
			break;
		}
		else if (NO_RECURSE_PROJECT_DIRS.includes(dirent.name)) {
			continue;
		}
		else if (dirent.isDirectory()) {
			for (const childDir of await getProjectDirs(path.resolve(dir, dirent.name))) {
				projectDirs.push(childDir);
			}
		}
	}

	return projectDirs;
}

let cachedRootDir;

export async function getRootDir() {
	if (cachedRootDir) {
		return cachedRootDir;
	}

	let rootDir = path.resolve('.');
	let found = false;

	while (path.dirname(rootDir) !== rootDir) {
		if (await fileExists(path.join(rootDir, 'yarn.lock'))) {
			found = true;

			break;
		}

		rootDir = path.resolve(rootDir, '..');
	}

	if (!found) {
		throw new Error(
			'Unable to find root project folder (is yarn.lock missing?)'
		);
	}

	cachedRootDir = rootDir;

	return rootDir;
}
