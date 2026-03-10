/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import crypto from 'crypto';
import path from 'path';

export default function hashPathForVariable(filePath) {
	const normalizedFilePath = filePath.split(path.sep).join('');

	// Prefixing the string with 'a' since variables can't start with an integer

	return (
		'a' +
		crypto.createHash('sha256').update(normalizedFilePath).digest('hex')
	);
}
