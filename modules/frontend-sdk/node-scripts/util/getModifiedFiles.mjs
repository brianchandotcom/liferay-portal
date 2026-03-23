/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';
import path from 'path';

import getNamedArguments from '../util/getNamedArguments.mjs';
import {MODULES_DIR, PORTAL_DIR} from './locations.mjs';
import print from './print.mjs';
import runGitLsFiles from './runGitLsFiles.mjs';

/**
 * Get the list of modified files based on the `--current-branch` or
 * `--local-changes` arguments.
 *
 * When none of the arguments is provided this method returns `undefined`.
 *
 * @param currentDir
 * @return {Promise<string[]> | undefined}
 */
export default async function getModifiedFiles(currentDir) {
	const {currentBranch, localChanges} = getNamedArguments({
		currentBranch: '--current-branch',
		localChanges: '--local-changes',
	});

	let files;

	if (currentDir === MODULES_DIR) {
		if (currentBranch) {
			files = await runGitUtil('current-branch');
		}
		else if (localChanges) {
			files = await runGitUtil('local-changes');
		}
		else {
			files = undefined;
		}
	}
	else {
		if (currentBranch || localChanges) {
			print(
				0,
				true,
				print.error('\nERROR:'),
				'Arguments --current-branch or --local-changes are not valid when formatting a single project.\n'
			);
			process.exit(2);
		}

		files = await runGitLsFiles(['*', '**/*'], currentDir);
	}

	return files;
}

/**
 * Invokes com.liferay.portal.tools.GitUtil.main() with the given git.type and returns a parsed list
 * of files as per the tool's output.
 *
 * @param gitType one of 'current-branch' or 'local-changes'
 * @returns Promise<string[]>
 */
async function runGitUtil(gitType) {
	if (!['current-branch', 'local-changes'].includes(gitType)) {
		throw new Error(`Invalid git.type: ${gitType}`);
	}

	const portalImplDir = path.resolve(PORTAL_DIR, 'portal-impl');

	const {stdout} = await $({
		cwd: portalImplDir,
		env: {
			ANT_ARGS: '',
		},
	})`ant git-util -Dgit.type=${gitType}`;

	if (process.env['DEBUG_GIT_UTIL']) {

		// eslint-disable-next-line no-console
		console.log(stdout);
	}

	return stdout
		.split('\n')
		.map((line) => line.trim())
		.filter((line) => line.startsWith('[java] '))
		.map((line) => line.replace('[java] ', ''));
}
