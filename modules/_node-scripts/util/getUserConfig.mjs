/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cosmiconfig} from 'cosmiconfig';

import {getRootDir} from '../util/constants.mjs';

/**
 * Helper to get configuration via `cosmiconfig`
 * @param {string} moduleName Name of user config file
 * @param {object} whether to walk up looking for config if needed
 */
export default async function (
	moduleName,
	{cwd = process.cwd(), upwards} = {}
) {
	const stopDir = (upwards && getRootDir()) || cwd;

	const explorer = await cosmiconfig(moduleName, {
		stopDir,
	});

	const result = await explorer.search(cwd);

	return result ? result.config : {};
}
