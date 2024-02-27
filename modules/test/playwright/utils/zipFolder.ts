/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as os from 'node:os'; // eslint-disable-line @liferay/no-extraneous-dependencies
import * as path from 'path';
import {open} from 'yauzl';
import {zip} from 'zip-a-folder';

export async function zipFolder(folderPath: string) {
	const tempFilePath = path.join(os.tmpdir(), path.basename(folderPath));
	await zip(folderPath, tempFilePath);

	return tempFilePath;
}

export async function unzipFile(
	callback: any,
	filePath: string,
	id: string,
	json: JSON
) {
	open(filePath, {lazyEntries: true}, async (error, zip) => {
		zip.readEntry();
		zip.on('entry', (entry) => {
			if (/\/$/.test(entry.fileName)) {
				zip.readEntry();
			}
			else {
				zip.openReadStream(entry, callback(id, json, zip));
			}
		});
	});
}

export async function streamToJson(stream) {
	const chunks = [];

	for await (const chunk of stream) {
		chunks.push(Buffer.from(chunk));
	}

	return JSON.parse(Buffer.concat(chunks).toString());
}
