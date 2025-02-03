/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRootDir} from '../util/constants.mjs';
import {createGlobalConfig} from '../util/createGlobalConfig.mjs';
import projectScopeRequire from '../util/projectScopeRequire.mjs';

export async function checkGlobalNodeScriptsConfig() {
	const rootDir = await getRootDir();
	const rootConfig = await projectScopeRequire(
		'./node-scripts.config.js',
		rootDir
	);

	const nextHash = await createGlobalConfig(true);

	if (rootConfig.hash === nextHash) {
		return [];
	}

	return [
		'BAD - node-scripts.config.js is out of date, run `yarn run node-scripts generate:global-config`',
	];
}
