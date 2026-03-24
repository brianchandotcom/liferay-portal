/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';

import fileExists from '../../fileExists.mjs';

/**
 *
 * @param {string} ignoreFileName path to ignore file
 * @return {Promise<string[]>} list of ignore patterns as accepted by `micromatch`
 */
export default async function readIgnorePatterns(ignoreFileName) {
	if (!(await fileExists(ignoreFileName))) {
		return [];
	}

	const contents = await fs.readFile(ignoreFileName, 'utf8');

	const lines = contents
		.split('\n')
		.map((line) => line.trim())
		.filter((line) => !!line.length && !line.startsWith('#'));

	const patterns = [];

	for (let line of lines) {
		let negated = false;
		let rules = [];

		if (line.startsWith('!')) {
			negated = true;
			line = line.substring(1);
		}

		if (!line.includes('/')) {
			rules.push(`**/${line}`);
			rules.push(`**/${line}/**`);
		}
		else {
			line = line.startsWith('/') ? line.substring(1) : line;

			if (line.endsWith('/')) {
				rules.push(`${line}**`);
			}
			else {
				rules.push(`${line}`);
				rules.push(`${line}/**`);
			}
		}

		if (negated) {
			rules = rules.map((rule) => `!${rule}`);
		}

		patterns.push(...rules);
	}

	return patterns;
}
