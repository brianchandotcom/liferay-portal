/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';

import digestNodeScripts from '../util/digestNodeScripts.mjs';
import getNamedArguments from '../util/getNamedArguments.mjs';
import objectSF from '../util/objectSF.mjs';

export default async function main() {
	const {current} = getNamedArguments({
		base: '--base',
		current: '--current',
		other: '--other',
	});

	const contents = await fs.readFile(current, 'utf-8');
	const lines = contents.split('\n');

	let inConflict = false;
	const nonConflictLines = [];

	for (const line of lines) {
		const trimLine = line.trim();

		if (trimLine.startsWith('<<<<<<<')) {
			inConflict = true;
		}
		else if (trimLine.startsWith('>>>>>>>')) {
			inConflict = false;
		}
		else if (inConflict) {
			if (
				!trimLine.startsWith('=======') &&
				!trimLine.startsWith('"sha256": "')
			) {

				// At least one conflict line is not related to hash so fail merge

				process.exit(1);
			}
		}
		else {
			nonConflictLines.push(line);
		}
	}

	const json = JSON.parse(nonConflictLines.join('\n'));

	json['com.liferay']['sha256'] = await digestNodeScripts();

	await fs.writeFile(current, objectSF(json), 'utf-8');

	process.exit(0);
}
