/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {getRootDir} from '../util/constants.mjs';

export default async function main() {
	const rootDir = await getRootDir();
	const gitConfigPath = path.resolve(rootDir, '..', '.git', 'config');

	let contents = await fs.readFile(gitConfigPath, 'utf-8');

	if (contents.includes('[merge "node-scripts"]')) {
		console.log(`
⚠️ Git merge driver for node-scripts is already configured: doing nothing 🦥
`);
		process.exit(3);
	}

	if (contents.endsWith('\n')) {
		contents = contents.substring(0, contents.length - 1);
	}

	await fs.writeFile(
		gitConfigPath,
		`${contents}
[merge "node-scripts"]
	name = node-scripts conflicts merger
	driver = build/node/bin/node modules/_node-scripts/bin.js gitmerge:self --current=%A --base=%B --other=%O
	recursive = text
`,
		'utf-8'
	);

	console.log(`
✅ Added Git merge driver for node-scripts to .git/config file 🎉
`);
}
