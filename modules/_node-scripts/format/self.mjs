/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {NODE_SCRIPTS_PATH} from '../util/constants.mjs';
import digestNodeScripts from '../util/digestNodeScripts.mjs';
import getNamedArguments from '../util/getNamedArguments.mjs';
import objectSF from '../util/objectSF.mjs';
import mainBase from './index.mjs';

/**
 * Executes the standard format tasks but then checks if node-scripts version number must be bumped
 * according to https://liferay.atlassian.net/browse/LPD-25771
 */
export default async function main() {
	const {check} = getNamedArguments({
		check: '--check',
	});

	// Invoke base SF

	await mainBase();

	// Check node-scripts hash

	const expectedHash = await digestNodeScripts();

	const packageJSONPath = path.join(NODE_SCRIPTS_PATH, 'package.json');

	const packageJSON = JSON.parse(await fs.readFile(packageJSONPath, 'utf-8'));

	if (packageJSON['com.liferay']['sha256'] !== expectedHash) {
		if (check) {
			console.log(
				`❌ Expected hash for node-scripts and 'com.liferay/sha256' field in package.json file differ

Expected SHA-256 hash is: ${expectedHash}

Please update the hash field of the package.json file.
`
			);

			process.exit(1);
		}
		else {
			packageJSON['com.liferay']['sha256'] = expectedHash;

			await fs.writeFile(packageJSONPath, objectSF(packageJSON), 'utf-8');

			console.log('🔐 Updated sha256 field of package.json');
		}
	}
}
