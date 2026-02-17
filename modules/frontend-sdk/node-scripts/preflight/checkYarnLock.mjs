/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {readFileSync} from 'fs';

import {YARN_LOCK_FILE} from '../util/locations.mjs';

const LOCAL_REGISTRY_REGEX = /^\s+resolved\s".*(localhost|127.0.0.1).*$/gm;

export async function checkYarnLock() {
	const yarnLockContent = readFileSync(YARN_LOCK_FILE, 'utf8');

	return LOCAL_REGISTRY_REGEX.test(yarnLockContent)
		? ['Error: `yarn.lock` contains references to local npm registry.']
		: [];
}
