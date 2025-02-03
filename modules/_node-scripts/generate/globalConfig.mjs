/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import format from '../format/format.mjs';
import {getRootDir} from '../util/constants.mjs';
import {createGlobalConfig} from '../util/createGlobalConfig.mjs';

export default async function main() {
	const rootDir = await getRootDir();

	const config = await createGlobalConfig();

	const globalConfigPath = path.join(rootDir, 'node-scripts.config.js');

	await fs.writeFile(globalConfigPath, config);

	await format(true, [
		path.relative(
			path.resolve(rootDir, '..'),
			path.resolve(globalConfigPath)
		),
	]);
}
