/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import getFileProjectDir from './getFileProjectDir.mjs';
import {NO_RECURSE_PROJECT_DIRS} from './getProjectDirs.mjs';
import {MODULES_DIR} from './locations.mjs';

export default async function getTypeScriptProjectDirs(modifiedFiles) {
	modifiedFiles = modifiedFiles.filter(
		(file) =>
			file.endsWith('.ts') ||
			file.endsWith('.tsx') ||
			file.endsWith('package.json') ||
			file.endsWith('tsconfig.json')
	);
	modifiedFiles = modifiedFiles.filter((file) => file.startsWith('modules/'));
	modifiedFiles = modifiedFiles.map((file) => file.substring(8));
	modifiedFiles = modifiedFiles.filter(
		(file) => file.startsWith('apps/') || file.startsWith('dxp/')
	);

	const projectDirs = new Set();

	for (const file of modifiedFiles) {
		const projectDir = await getFileProjectDir(file);

		const parts = path.relative(MODULES_DIR, projectDir).split(path.sep);

		if (parts.some((part) => NO_RECURSE_PROJECT_DIRS.includes(part))) {
			continue;
		}

		projectDirs.add(projectDir);
	}

	return [...projectDirs];
}
