/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PLAYWRIGHT_DIR} from '../locations.mjs';
import formatSourceFiles from './formatters/formatSourceFiles.mjs';
import formatTypeScript from './formatters/formatTypeScript.mjs';

export default async function formatPlaywright(check, files) {
	let checksPassed = true;

	if (!(await formatSourceFiles(check, files))) {
		checksPassed = false;
	}

	if (!(await formatTypeScript([PLAYWRIGHT_DIR]))) {
		checksPassed = false;
	}

	return checksPassed;
}
