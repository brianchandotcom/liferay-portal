/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';
import path from 'path';

import {getRootDir} from '../util/constants.mjs';

/**
 * Invokes com.liferay.portal.tools.GitUtil.main() with the given git.type and returns a parsed list
 * of files as per the tool's output.
 *
 * @param gitType one of 'current-branch' or 'local-changes'
 * @returns string[]
 */
export default async function gitUtil(gitType) {
	if (!['current-branch', 'local-changes'].includes(gitType)) {
		throw new Error(`Invalid git.type: ${gitType}`);
	}

	const rootDir = await getRootDir();
	const portalDir = path.resolve(rootDir, '..', 'portal-impl');

	const {stdout} = await $({
		cwd: portalDir,
	})`ant git-util -Dgit.type=${gitType}`;

	if (process.env['DEBUG_GIT_UTIL']) {
		console.log(stdout);
	}

	return stdout
		.split('\n')
		.map((line) => line.trim())
		.filter((line) => line.startsWith('[java] '))
		.map((line) => line.replace('[java] ', ''));
}
