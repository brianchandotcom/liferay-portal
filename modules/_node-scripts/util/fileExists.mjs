/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';

export default async function fileExists(filePath) {
	try {
		await fs.stat(filePath);

		return true;
	}
	catch (error) {
		if (error.code !== 'ENOENT' && error.code !== 'ENOTDIR') {
			throw error;
		}

		return false;
	}
}
