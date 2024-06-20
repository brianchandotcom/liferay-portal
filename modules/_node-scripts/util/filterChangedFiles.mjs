/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';
import path from 'path';

/**
 * In the context of liferay-portal, we may want to run against a subset of
 * eligible files (eg. files changed on the current branch). To achieve this, we
 * check the LIFERAY_NPM_SCRIPTS_WORKING_BRANCH_NAME environment variable and if
 * it is set, filter the `files` list to contain only files changed with respect
 * to that branch (usually "master", but may also be "master-private").
 *
 * If the variable is not set, the we default to 'master'
 *
 * One important exception to the above: if the top-level `package.json`
 * changes (which happens rarely), this may indicate a change of the
 * @liferay/npm-scripts version, and in that case we want to run against
 * the entire unfiltered `files` list.
 *
 * @param {Array<string>} files List of files relative to the current directory.
 */
export default async function filterChangedFiles(files) {
	const upstream =
		process.env.LIFERAY_NPM_SCRIPTS_WORKING_BRANCH_NAME || 'master';

	const {stdout: currentBranch} = await $`git rev-parse --abbrev-ref HEAD`;

	const atSameBranch = currentBranch === upstream;

	if (upstream === undefined || atSameBranch) {
		if (atSameBranch) {
			console.log(
				`⚠️ You are already on '${currentBranch}' branch. Skipping diff filter.`
			);
		}

		return files;
	}

	console.log(
		`ℹ️ Only running against files changed between '${upstream}' and '${currentBranch}' branches. Use flag '--all' to run against all files.`
	);

	const {stdout: topLevel} = await $`git rev-parse --show-toplevel`;

	const {stdout: mergeBase} = await $`git merge-base HEAD ${upstream}`;

	const {stdout: changedFiles} =
		await $`git diff -z --diff-filter=ACMR --name-only ${mergeBase} HEAD`;

	const set = new Set(
		changedFiles
			.split(/\0/)
			.map((file) => {
				return file ? path.join(topLevel, file) : file;
			})
			.filter(Boolean)
	);

	return files.filter((file) => {
		return set.has(file);
	});
}
