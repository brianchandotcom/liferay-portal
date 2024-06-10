/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {BUILD_MAIN_EXPORTS_PATH} from '../../util/constants.mjs';
import getFlatName from '../../util/getFlatName.mjs';
import getEntryPoint from './getEntryPoint.mjs';
import runEsbuild from './runEsbuild.mjs';

export default async function bundleCSSExports(projectExports) {
	if (!projectExports.length) {
		return;
	}

	await Promise.all(
		projectExports
			.filter((moduleName) => moduleName.endsWith('.css'))
			.map((moduleName) => bundle(moduleName))
	);
}

async function bundle(moduleName) {
	const esbuildConfig = {
		entryPoints: [getEntryPoint(moduleName)],
		loader: {
			'.png': 'empty',
		},
		outdir: BUILD_MAIN_EXPORTS_PATH,
		sourcemap: true,
	};

	return runEsbuild(esbuildConfig, getFlatName(moduleName));
}
