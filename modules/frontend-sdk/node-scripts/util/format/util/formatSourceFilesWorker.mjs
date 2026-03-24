/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import formatSourceFile from './formatSourceFile.mjs';

process.on('message', async ({fileIndex, filePath, options, skip}) => {
	process.send({
		fileIndex,
		result: await formatSourceFile(filePath, skip, options),
	});
});
