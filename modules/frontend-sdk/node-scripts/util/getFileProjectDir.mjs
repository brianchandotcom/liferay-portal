/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import fileExists from './fileExists.mjs';
import {MODULES_DIR} from './locations.mjs';

export default async function getFileProjectDir(filePath) {
	let dir = path.resolve(path.dirname(filePath));

	while (dir !== MODULES_DIR) {
		if (await fileExists(path.join(dir, 'build.gradle'))) {
			return dir;
		}

		dir = path.dirname(dir);
	}

	throw new Error(`File does not belong to a project: ${filePath}`);
}
