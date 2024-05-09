/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

export const BUILD_PATH = path.join('build', 'node', 'packageRunBuild');
export const BUILD_RESOURCES_PATH = path.join(BUILD_PATH, 'resources');
export const BUILD_MAIN_EXPORTS_PATH = path.join(
	BUILD_RESOURCES_PATH,
	'__liferay__'
);
export const BUILD_CSS_EXPORTS_PATH = path.join(BUILD_MAIN_EXPORTS_PATH, 'css');
export const BUILD_NPM_EXPORTS_PATH = path.join(
	BUILD_MAIN_EXPORTS_PATH,
	'exports'
);

export const SRC_PATH = path.join(
	'src',
	'main',
	'resources',
	'META-INF',
	'resources'
);

export const WORK_PATH = path.join('build', 'node-scripts');
export const WORK_EXPORT_PATH = path.join(WORK_PATH, 'export');
