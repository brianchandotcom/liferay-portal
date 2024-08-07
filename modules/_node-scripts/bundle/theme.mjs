/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';
import path from 'path';

import {getRootDir} from '../util/constants.mjs';

const MODULES_DIR = '<RootDir>';
const BUILD_ARGS = {
	'--css-common-path': path.normalize('build_gradle/frontend-css-common'),
	'--sass-include-paths': path.normalize(`${MODULES_DIR}/node_modules`),
	'--styled-path': path.normalize(
		`${MODULES_DIR}/apps/frontend-theme/frontend-theme-styled/src/main/resources/META-INF/resources/_styled`
	),
	'--unstyled-path': path.normalize(
		`${MODULES_DIR}/apps/frontend-theme/frontend-theme-unstyled/src/main/resources/META-INF/resources/_unstyled`
	),
};

/**
 * Wrapper to run `gulp` tasks for a theme in the liferay-portal repo.
 */
export default async function run() {
	const rootDir = await getRootDir();

	const args = [];

	Object.keys(BUILD_ARGS).forEach((key) => {
		const value = BUILD_ARGS[key];
		args.push(key, value.replace(MODULES_DIR, rootDir));
	});

	await $({stdio: 'inherit'})('gulp', ['build', ...args]);
}
