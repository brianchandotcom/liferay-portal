/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {NODE_SCRIPTS_PATH} from '../util/constants.mjs';
import digestNodeScripts from '../util/digestNodeScripts.mjs';

export async function checkNodeScriptsHash() {
	const packageJSON = JSON.parse(
		await fs.readFile(path.join(NODE_SCRIPTS_PATH, 'package.json'), 'utf-8')
	);

	const expectedHash = await digestNodeScripts();

	if (packageJSON['com.liferay']['sha256'] === expectedHash) {
		return [];
	}

	return [
		'BAD - node-scripts sha256 field is not up-to-date: run yarn format at modules/_node-scripts',
	];
}
