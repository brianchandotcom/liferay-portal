/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';

import fileExists from './fileExists.mjs';

export default async function emptyDir(dir) {
	if (await fileExists(dir)) {
		await fs.rmdir(dir, {recursive: true});
	}

	await fs.mkdir(dir, {recursive: true});
}
