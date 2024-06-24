/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';

import {LIFERAY_WORKING_BRANCH} from './constants.mjs';

const cachedGitModifiedFiles = {};

export default async function getGitModifiedFiles(commit = undefined) {
	if (commit === undefined) {
		const {stdout} = await $`git rev-parse ${LIFERAY_WORKING_BRANCH}`;

		commit = stdout;
	}

	if (!cachedGitModifiedFiles[commit]) {
		const {stdout} = await $`git diff --name-only ${commit} HEAD`;

		cachedGitModifiedFiles[commit] = stdout.split('\n');
	}

	return cachedGitModifiedFiles[commit];
}
