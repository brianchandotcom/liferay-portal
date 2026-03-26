/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import digestNodeScripts from '../../digestNodeScripts.mjs';
import {NODE_SCRIPTS_DIR} from '../../locations.mjs';
import objectSF from '../../objectSF.mjs';
import print from '../../print.mjs';

export default async function formatNodeScriptsHash(check) {
	let checksPassed = true;

	print(
		1,
		print.subTitle(
			`> ${check ? 'Checking' : 'Formatting'} '@liferay/node-scripts' hash...\n`
		)
	);

	const expectedHash = await digestNodeScripts();

	const packageJSONPath = path.join(NODE_SCRIPTS_DIR, 'package.json');

	const packageJSON = JSON.parse(await fs.readFile(packageJSONPath, 'utf-8'));

	if (packageJSON['com.liferay']['sha256'] !== expectedHash) {
		if (check) {
			print(
				2,
				print.error('ERROR:'),
				`Incorrect value for 'com.liferay.sha256' field of`,
				print.underline(`'@liferay/node-scripts'`),
				'\n'
			);

			checksPassed = false;
		}
		else {
			packageJSON['com.liferay']['sha256'] = expectedHash;

			await fs.writeFile(packageJSONPath, objectSF(packageJSON), 'utf-8');

			print(
				2,
				print.success('SUCCESS:'),
				"Formatted 'com.liferay.sha256' field\n"
			);
		}
	}

	return checksPassed;
}
