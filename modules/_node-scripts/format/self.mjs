/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import crypto from 'crypto';
import fs from 'fs/promises';
import path from 'path';
import url from 'url';

import expandGlobs from '../util/expandGlobs.mjs';
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

	await mainBase();

	const __dirname = path.dirname(url.fileURLToPath(import.meta.url));
	const nodeScriptsPath = path.resolve(__dirname, '..');
	const packageJSONPath = path.join(nodeScriptsPath, 'package.json');

	const packageJSON = JSON.parse(await fs.readFile(packageJSONPath, 'utf-8'));

	const expectedHash = await digestNodeScripts(nodeScriptsPath);

	if (packageJSON['com.liferay']['sha256'] === expectedHash) {
		return;
	}

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
	}
}

async function digestNodeScripts(nodeScriptsPath) {
	const sha256 = crypto.createHash('sha256');

	let files = await expandGlobs(
		['**/*.mjs', '**/*.js'],
		['bundle/sass/binary/**', 'node_modules/**'],
		{
			baseDir: nodeScriptsPath,
		}
	);

	files = files.filter((file) => !path.basename(file).startsWith('.'));

	files.sort();

	const fileContents = await Promise.all(
		files.map(async (file) => {
			return await fs.readFile(file, 'utf-8');
		})
	);

	for (const fileContent of fileContents) {
		sha256.update(fileContent);
	}

	return sha256.digest('hex');
}
