/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as fs from 'fs';
import * as path from 'path';

const TMP_DIR = `tmp/${process.pid}`;

export function createTempFile(name: string, content: string = ''): string {
	fs.mkdirSync(TMP_DIR, {recursive: true});

	const filePath = path.join(TMP_DIR, name);

	if (fs.existsSync(filePath)) {
		throw new Error(`Temporary file ${name} already exists`);
	}

	fs.writeFileSync(filePath, content, 'utf-8');

	return filePath;
}

export function getRandomInt(): number {
	return Math.floor(Math.random() * 9999999999);
}

export function readTempFile(name: string): string {
	const filePath = path.join(TMP_DIR, name);

	try {
		return fs.readFileSync(filePath, 'utf-8');
	}
	catch (error) {
		throw new Error(`Cannot read temporary file ${name}: ${error}`);
	}
}
