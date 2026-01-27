/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {GLOBAL_NODE_SCRIPTS_CONFIG_FILE} from '../util/locations.mjs';
import projectScopeRequire from '../util/projectScopeRequire.mjs';

export default async function getGlobalSubmodules() {
	const {submodules} = projectScopeRequire(GLOBAL_NODE_SCRIPTS_CONFIG_FILE);

	return submodules;
}
