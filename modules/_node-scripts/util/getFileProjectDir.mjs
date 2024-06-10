/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import {getRootDir} from './constants.mjs';
import fileExists from './fileExists.mjs';

export default async function getFileProjectDir(filePath) {
	const rootDir = await getRootDir();

	let dir = path.resolve(path.dirname(filePath));

	while (dir !== rootDir) {
		if (await fileExists(path.join(dir, 'build.gradle'))) {
			return dir;
		}

		dir = path.dirname(dir);
	}

	throw new Error(`File does not belong to a project: ${filePath}`);
}
