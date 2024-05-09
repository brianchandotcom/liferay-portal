/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import projectScopeRequire from '../util/projectScopeRequire.mjs';

/**
 * @returns
 * Something like:
 *
 * [
 *   'html-to-image',
 *   'jspdf',
 *   '@liferay/js-api',
 *   '@liferay/js-api/data-set',
 * ]
 */
export default function getProjectExports() {
	const {exports} = projectScopeRequire('./node-scripts.config.js');

	if (exports === undefined) {
		return [];
	}

	return exports;
}
