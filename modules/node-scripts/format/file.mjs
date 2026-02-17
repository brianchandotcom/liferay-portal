/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import {PORTAL_DIR} from '../util/locations.mjs';
import format from './format.mjs';

export default async function main() {
	const filePath = process.argv[3];

	if (!filePath) {
		console.error('❌ Missing path of file to be formatted');
		process.exit(2);
	}

	await format(true, [path.relative(PORTAL_DIR, path.resolve(filePath))]);
}
