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

const KEY1 = '0123456789';
const KEY2 = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
const KEY3 = 'abcdefghijklmnopqrstuvwxyz';

export function getRandomString(length: number = 8): string {
	let randomString = '';
	const chars = KEY1 + KEY2 + KEY3;
	const charLength = chars.length;

	for (let i = 0; i < length; i++) {
		randomString += chars.charAt(Math.floor(Math.random() * charLength));
	}

	return randomString;
}

export async function zipFolder(folderPath: string) {
	const tempFilePath = path.join(os.tmpdir(), path.basename(folderPath));
	await zip(folderPath, tempFilePath);

	return tempFilePath;
}
