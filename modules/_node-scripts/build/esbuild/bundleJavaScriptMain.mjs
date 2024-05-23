/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import {BUILD_MAIN_EXPORTS_PATH} from '../../util/constants.mjs';
import getExternals from './getExternals.mjs';
import getCssLoaderPlugin from './plugins/getCssLoaderPlugin.mjs';
import getExactAliasPlugin from './plugins/getExactAliasPlugin.mjs';
import getImportBridgesPlugin from './plugins/getImportBridgesPlugin.mjs';
import getScssLoaderPlugin from './plugins/getScssLoaderPlugin.mjs';
import runEsbuild from './runEsbuild.mjs';

export default async function bundleJavaScriptMain(
	globalImports,
	overridenPackageSymbols,
	projectMain,
	projectWebContextPath
) {
	if (!projectMain) {
		return;
	}

	const esbuildConfig = {
		bundle: true,
		entryNames: 'index',
		entryPoints: [path.resolve(projectMain)],
		external: getExternals(globalImports, 'main'),
		format: 'esm',
		loader: {
			'.js': 'jsx',
			'.png': 'empty',
			'.scss': 'css',
		},
		outdir: BUILD_MAIN_EXPORTS_PATH,
		plugins: [
			getCssLoaderPlugin(globalImports, 'main'),
			getExactAliasPlugin(globalImports, 'main'),
			getImportBridgesPlugin(globalImports, overridenPackageSymbols),
			getScssLoaderPlugin(projectWebContextPath),
		],
		sourcemap: true,
		target: ['es2020'],
	};

	return runEsbuild(esbuildConfig, 'main');
}
