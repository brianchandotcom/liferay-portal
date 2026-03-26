/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';

import {YARN_LOCK_FILE} from '../../locations.mjs';
import print from '../../print.mjs';

const LOCAL_REGISTRY_REGEX = /^\s+resolved\s".*(localhost|127.0.0.1).*$/gm;

export default async function formatYarnLock() {
	let checksPassed = true;

	print(1, print.subTitle(`> Checking global 'yarn.lock' file...\n`));

	const yarnLockContent = await fs.readFile(YARN_LOCK_FILE, 'utf8');

	if (LOCAL_REGISTRY_REGEX.test(yarnLockContent)) {
		print(
			2,
			print.error('ERROR:'),
			'Global',
			print.underline(`'yarn.lock'`),
			'file contains references to a local npm registry\n'
		);

		checksPassed = false;
	}

	return checksPassed;
}
