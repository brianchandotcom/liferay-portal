/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {readdirSync, statSync} from 'fs';
import path from 'path';

import {checkFolderInZip} from '../../../../utils/zip';

export async function unzipAndCheckFolder(
	tempDir: string,
	folderName: string = 'adaptive-media'
): Promise<boolean | null> {
	const files = readdirSync(tempDir)
		.filter((file) => file.endsWith('.lar'))
		.map((file) => ({
			file,
			time: statSync(path.join(tempDir, file)).mtime.getTime(),
		}))
		.sort((a, b) => b.time - a.time);

	if (!files.length) {
		return null;
	}

	const mostRecentFilePath = path.join(tempDir, files[0].file);

	try {
		const hasFolder = await checkFolderInZip(
			mostRecentFilePath,
			folderName
		);

		return hasFolder;
	}
	catch (error) {
		console.error(`Error reading file ${files[0].file}: ${error}`);
	}

	return null;
}
