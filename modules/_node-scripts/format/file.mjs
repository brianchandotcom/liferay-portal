/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import {getRootDir} from '../util/constants.mjs';
import format from './format.mjs';

export default async function main() {
	const filePath = process.argv[3];

	if (!filePath) {
		console.error('❌ Missing path of file to be formatted');
		process.exit(2);
	}

	const rootDir = await getRootDir();
	const portalDir = path.resolve(rootDir, '..');

	await format(true, [path.relative(portalDir, path.resolve(filePath))]);
}
