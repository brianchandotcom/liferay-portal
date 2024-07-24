/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getNamedArguments from '../util/getNamedArguments.mjs';
import maybeUpdateNodeScriptsHash from '../util/maybeUpdateNodeScriptsHash.mjs';
import mainBase from './index.mjs';

/**
 * Executes the standard format tasks but then checks if node-scripts version number must be bumped
 * according to https://liferay.atlassian.net/browse/LPD-25771
 */
export default async function main() {
	const {check} = getNamedArguments({
		check: '--check',
	});

	await mainBase();

	const newHash = await maybeUpdateNodeScriptsHash();

	if (!newHash) {
		return;
	}

	if (check) {
		console.log(
			`❌ Expected hash for node-scripts and 'com.liferay/sha256' field in package.json file differ

Expected SHA-256 hash is: ${newHash}

Please update the hash field of the package.json file.
`
		);

		process.exit(1);
	}
}
