/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import createGlobalConfig from '../../createGlobalConfig.mjs';
import {MODULES_DIR} from '../../locations.mjs';
import print from '../../print.mjs';
import projectScopeRequire from '../../projectScopeRequire.mjs';

export default async function formatGlobalNodeScriptsConfig(check) {
	let checksPassed = true;

	print(
		1,
		print.subTitle(
			`> ${check ? 'Checking' : 'Formatting'} global 'node-scripts.config.js' file...\n`
		)
	);

	const globalConfig = await projectScopeRequire(
		'./node-scripts.config.js',
		MODULES_DIR
	);

	const {config: newGlobalConfig, hash: newHash} = await createGlobalConfig();

	if (globalConfig.hash !== newHash) {
		if (check) {
			print(
				2,
				print.error('ERROR:'),
				'Global',
				print.underline(`'node-scripts.config.js'`),
				'file is outdated\n'
			);

			checksPassed = false;
		}
		else {
			const globalConfigPath = path.join(
				MODULES_DIR,
				'node-scripts.config.js'
			);

			await fs.writeFile(globalConfigPath, newGlobalConfig, 'utf-8');

			print(
				2,
				print.success('SUCCESS:'),
				'Updated global',
				print.underline(`'node-scripts.config.js'`),
				'file\n'
			);
		}
	}

	return checksPassed;
}
