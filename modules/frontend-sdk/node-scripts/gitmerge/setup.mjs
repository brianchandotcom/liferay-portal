/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {PORTAL_DIR} from '../util/locations.mjs';
import print from '../util/print.mjs';

export default async function main() {
	const gitConfigPath = path.resolve(PORTAL_DIR, '.git', 'config');

	let contents = await fs.readFile(gitConfigPath, 'utf-8');

	if (contents.includes('[merge "node-scripts"]')) {
		print(
			0,
			true,
			print.warning('\nWARNING:'),
			'Git merge driver for node-scripts is already configured: doing nothing.\n'
		);

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
	driver = build/node/bin/node modules/frontend-sdk/node-scripts/bin.js gitmerge:self --current=%A --base=%B --other=%O
	recursive = text
`,
		'utf-8'
	);

	print(
		0,
		true,
		print.info('\nINFO:'),
		'Added Git merge driver for node-scripts to .git/config file\n'
	);
}
