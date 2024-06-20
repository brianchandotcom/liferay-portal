/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';

import fileExists from './fileExists.mjs';

const BLANK_LINES = /^\s*$/;
const COMMENTS = /^\s*#/;
const LINE_SEPARATORS = /[\n\r]+/;

/**
 * Loose implementation of an "ignore-file" reader based on `man 5 gitignore`.
 *
 * Differences include:
 *
 * - Both trailing and leading whitespace are ignored, allowing lines to be
 *   indented to form sections (in Git, only trailing whitespace is ignored).
 * - Backslash-escaping of special characeters (eg. "#") is not implemented.
 */
export async function readIgnoreFile(file) {
	if (await fileExists(file)) {
		const contents = await fs.readFile(file, 'utf8');

		return contents
			.split(LINE_SEPARATORS)
			.filter((line) => {
				return !COMMENTS.test(line) && !BLANK_LINES.test(line);
			})
			.map((line) => line.trim());
	}

	return [];
}
