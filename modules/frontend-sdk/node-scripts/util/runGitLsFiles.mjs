/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';
import path from 'path';

import fileExists from './fileExists.mjs';
import {PORTAL_DIR} from './locations.mjs';

/**
 * Run `git ls-files`, but removing deleted (but tracked files) from the
 * results.
 *
 * @param {string[]} globs
 * List of glob arguments to pass to `git ls-files`. Each glob will be
 * surrounded by quotes.
 *
 * @param {string} cwd
 * Directory where `git ls-files` will be launched.
 *
 * @return {Promise<string[]>}
 * List of file paths relative to PORTAL_DIR (even if `cwd` is a different dir).
 */
export default async function runGitLsFiles(globs, cwd = PORTAL_DIR) {
	const {stdout: trackedResult} = await $({
		cwd,
		shell: true,
	})`git ls-files ${globs.map((glob) => `"${glob}"`).join(' ')}`;

	const {stdout: untrackedResult} = await $({
		cwd,
		shell: true,
	})`git ls-files --others --exclude-standard ${globs.map((glob) => `"${glob}"`).join(' ')}`;

	let files = [
		...trackedResult.split('\n').filter((line) => !!line.trim().length),
		...untrackedResult.split('\n').filter((line) => !!line.trim().length),
	];

	if (cwd !== PORTAL_DIR) {
		files = files.map((file) =>
			path.relative(PORTAL_DIR, path.resolve(cwd, file))
		);
	}

	// Remove deleted files still tracked by git

	const filesCopy = files;
	files = [];

	for (const file of filesCopy) {
		if (await fileExists(path.resolve(PORTAL_DIR, file))) {
			files.push(file);
		}
	}

	return files;
}
