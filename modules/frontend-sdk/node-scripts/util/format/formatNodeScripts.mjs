/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import formatNodeScriptsHash from './formatters/formatNodeScriptsHash.mjs';
import formatSourceFiles from './formatters/formatSourceFiles.mjs';

/**
 * Executes the standard format tasks and then checks if node-scripts version
 * number must be bumped according to
 * https://liferay.atlassian.net/browse/LPD-25771
 */
export default async function formatNodeScripts(check, files) {
	let checksPassed = true;

	if (!(await formatSourceFiles(check, files))) {
		checksPassed = false;
	}

	if (!(await formatNodeScriptsHash(check))) {
		checksPassed = false;
	}

	return checksPassed;
}
