/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as os from 'node:os'; // eslint-disable-line @liferay/no-extraneous-dependencies
import * as path from 'path';
import {zip} from 'zip-a-folder';

export function getRandomInt(): number {
	return Math.floor(Math.random() * 9999999999);
}

export async function zipFolder(folderPath: string) {
	const tempFilePath = path.join(os.tmpdir(), path.basename(folderPath));
	await zip(folderPath, tempFilePath);

	return tempFilePath;
}
