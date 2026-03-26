/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import formatNodeScripts from '../util/format/formatNodeScripts.mjs';
import formatPlaywright from '../util/format/formatPlaywright.mjs';
import formatPortal from '../util/format/formatPortal.mjs';
import formatProject from '../util/format/formatProject.mjs';
import getModifiedFiles from '../util/getModifiedFiles.mjs';
import getNamedArguments from '../util/getNamedArguments.mjs';
import {
	MODULES_DIR,
	NODE_SCRIPTS_DIR,
	PLAYWRIGHT_DIR,
} from '../util/locations.mjs';
import print, {printDuration} from '../util/print.mjs';

export default async function main() {
	const start = Date.now();

	const {check} = getNamedArguments({
		check: '--check',
	});

	const currentDir = path.resolve('.');

	const files = await getModifiedFiles(currentDir);

	print(
		0,
		print.title(
			`\n> ${check ? 'Checking' : 'Formatting'} ${files ? files.length : 'ALL'} files...\n`
		)
	);

	let checksPassed;

	if (currentDir === MODULES_DIR) {
		checksPassed = await formatPortal(check, files);
	}
	else if (currentDir === NODE_SCRIPTS_DIR) {
		checksPassed = await formatNodeScripts(check, files);
	}
	else if (currentDir === PLAYWRIGHT_DIR) {
		checksPassed = await formatPlaywright(check, files);
	}
	else if (path.relative(MODULES_DIR, currentDir).length) {
		checksPassed = await formatProject(check, files, currentDir);
	}
	else {
		checksPassed = false;

		print(
			1,
			print.error('ERROR:'),
			'Directory',
			print.underline(currentDir),
			'is unknown to node-scripts\n'
		);
	}

	printDuration(start, 0, 'Formatting');

	if (checksPassed) {
		print(
			0,
			print.success('SUCCESS:'),
			'Everything is correctly formatted.\n'
		);
	}
	else {
		print(
			0,
			print.error('ERROR:'),
			'Some errors could not be fixed automatically.\n'
		);
	}

	process.exit(checksPassed ? 0 : 1);
}
