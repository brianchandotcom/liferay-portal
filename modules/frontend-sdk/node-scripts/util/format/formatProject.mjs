/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import formatSourceFiles from './formatters/formatSourceFiles.mjs';
import formatTypeScript from './formatters/formatTypeScript.mjs';

export default async function formatProject(check, files, currentDir) {
	const typeScriptFilesPresent =
		!files ||
		!!files.find((file) => file.endsWith('.ts') || file.endsWith('.tsx'));

	let checksPassed = true;

	if (!(await formatSourceFiles(check, files))) {
		checksPassed = false;
	}

	if (typeScriptFilesPresent && !(await formatTypeScript([currentDir]))) {
		checksPassed = false;
	}

	return checksPassed;
}
