/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import {getRootDir} from '../util/constants.mjs';
import projectScopeRequire from '../util/projectScopeRequire.mjs';

/**
 * @returns
 * Something like:
 *
 * {
 *   '@clayui/charts': ['__esModule', 'bb', 'default']
 * }
 */
export default async function getOverridenPackageSymbols() {
	const rootDir = await getRootDir();

	const {symbols} = projectScopeRequire(
		path.join(rootDir, 'node-scripts.config.js')
	);

	return symbols || {};
}
