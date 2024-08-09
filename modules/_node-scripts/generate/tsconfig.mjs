/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import visitOutdatedTsconfigFiles from '../tsconfig/visitOutdatedTsconfigFiles.mjs';
import {getRootDir} from '../util/constants.mjs';

export default async function main() {
	const rootDir = await getRootDir();
	const cwd = path.resolve('.');

	if (cwd !== rootDir) {
		console.error(
			`❌ Command generate:tsconfig can only be run from 'modules' directory.`
		);
		process.exit(2);
	}

	console.log('✍️ Regenerating outdated tsconfig.json files...');

	await visitOutdatedTsconfigFiles(async (filePath, json) => {
		await fs.writeFile(filePath, json, 'utf-8');

		console.log(`✅ Regenerated ${path.relative(rootDir, filePath)}`);
	});
}
