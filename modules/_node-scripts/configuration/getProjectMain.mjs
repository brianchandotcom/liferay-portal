/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import projectScopeRequire from '../util/projectScopeRequire.mjs';

/**
 * @returns the proejct relative path of the main entry point
 */
export default function getProjectMain() {
	const {main} = projectScopeRequire('./node-scripts.config.js');

	return main;
}
