/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import {getRootDir} from '../util/constants.mjs';
import projectScopeRequire from '../util/projectScopeRequire.mjs';

export default async function getGlobalSubmodules() {
	const rootDir = await getRootDir();

	const {submodules} = projectScopeRequire(
		path.join(rootDir, 'node-scripts.config.js')
	);

	return submodules;
}
