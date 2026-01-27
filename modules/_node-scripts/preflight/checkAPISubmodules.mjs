/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fg from 'fast-glob';
import path from 'path';

import {MODULES_DIR} from '../util/locations.mjs';

const REGEX_API_DIR = /\/resources\/js\/api\/api\.(js|ts)$/;

/**
 * Runs checks against node-scripts.config.js files; detects:
 *
 * - invalid 'api' submodule paths
 *
 * Returns a (possibly empty) array of error messages.
 */
export async function checkAPISubmodules() {
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

	return configsWithAPISubmodule
		.map(({config, path}) => {
			if (!REGEX_API_DIR.test(config.submodules.api)) {
				return `BAD - 'api' submodule must be located in /resources/js/api/api.js (or .ts). See '${path}'`;
			}
		})
		.filter(Boolean);
}
