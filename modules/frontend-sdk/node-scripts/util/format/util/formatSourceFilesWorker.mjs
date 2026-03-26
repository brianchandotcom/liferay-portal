/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import print from '../../print.mjs';
import formatSourceFile from './formatSourceFile.mjs';

process.on('message', async ({fileIndex, filePath, options, skip}) => {
	let result;

	try {
		result = await formatSourceFile(filePath, skip, options);
	}
	catch (error) {
		print(
			1,
			print.error('ERROR:'),
			`Worker failed formatting ${filePath}: ${error}`
		);

		result = false;
	}

	process.send({
		fileIndex,
		result,
	});
});
