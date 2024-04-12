/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as path from 'path';
import {open} from 'yauzl';
import {zip} from 'zip-a-folder';

import {streamToString} from './stream';
import {getTempDir} from './temp';

type ZipOptions = {
	destPath?: string;
};

export async function zipFolder(folderPath: string, zipOptions?: ZipOptions) {
	const tempFilePath = path.join(getTempDir(), path.basename(folderPath));
	await zip(folderPath, tempFilePath, zipOptions);

	return tempFilePath;
}

export async function unzipFile(filePath: string): Promise<string> {
	return new Promise((resolve, reject) => {
		open(filePath, {lazyEntries: true}, async (error, zip) => {
			zip.readEntry();
			zip.on('entry', (entry) => {
				if (/\/$/.test(entry.fileName)) {
					zip.readEntry();
				}
				else {
					zip.openReadStream(entry, async (error, stream) => {
						if (error) {
							reject(error);
						}
						stream.on('end', () => {
							zip.readEntry();
						});
						resolve(await streamToString(stream));
					});
				}
			});
		});
	});
}
