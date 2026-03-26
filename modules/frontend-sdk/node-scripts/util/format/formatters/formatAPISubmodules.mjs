/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fg from 'fast-glob';
import path from 'path';

import {MODULES_DIR} from '../../locations.mjs';
import print from '../../print.mjs';

const REGEX_API_DIR = /\/resources\/js\/api\/api\.(js|ts)$/;

export default async function formatAPISubmodules() {
	let checksPassed = true;

	print(1, print.subTitle('> Checking API submodules...\n'));

	const nodeScriptConfigs = await fg('**/node-scripts.config.js', {
		ignore: ['**/build', '**/classes', '**/node_modules'],
	});

	const configs = await Promise.all(
		nodeScriptConfigs.map(async (configPath) => {
			const module = await import(path.join(MODULES_DIR, configPath));

			return {config: module.default, path: configPath};
		})
	);

	const configsWithAPISubmodule = configs.filter(({config}) => {
		return config?.submodules?.api;
	});

	configsWithAPISubmodule.forEach(({config, path}) => {
		if (!REGEX_API_DIR.test(config.submodules.api)) {
			print(
				2,
				print.error('ERROR:'),
				'Invalid API module found at path',
				print.underline(path),
				'should be located at /resources/js/api/api.{js|ts})\n'
			);

			checksPassed = false;
		}
	});

	return checksPassed;
}
