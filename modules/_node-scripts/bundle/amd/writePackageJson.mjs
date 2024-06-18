/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {BUILD_RESOURCES_PATH} from '../../util/constants.mjs';

export default async function writePackageJson(
	projectDescription,
	projectEntryPoints,
	projectExports
) {
	if (!projectEntryPoints.main && !projectExports.length) {
		return;
	}

	const filePath = path.join(BUILD_RESOURCES_PATH, 'package.json');

	const {name, version} = projectDescription;

	const json = {
		main: 'index.js',
		name,
		version,
	};

	await fs.mkdir(path.dirname(filePath), {recursive: true});
	await fs.writeFile(filePath, JSON.stringify(json, null, '\t'), 'utf-8');
}
