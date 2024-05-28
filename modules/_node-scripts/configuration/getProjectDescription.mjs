/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import projectScopeRequire from '../util/projectScopeRequire.mjs';

/**
 * @returns
 * {
 *	 name: 'xxx',
 *	 version: 'x.y.z',
 * }
 */
export default function getProjectDescription(projectDir = '.') {
	const {main, name, version} = projectScopeRequire(
		'./package.json',
		projectDir
	);

	return {main, name, version};
}
