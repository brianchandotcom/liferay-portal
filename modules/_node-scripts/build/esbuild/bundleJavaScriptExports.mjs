/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {BUILD_MAIN_EXPORTS_PATH} from '../../util/constants.mjs';
import getFlatName from '../../util/getFlatName.mjs';
import getEntryPoint from './getEntryPoint.mjs';
import getExternals from './getExternals.mjs';
import getExactAliasPlugin from './plugins/getExactAliasPlugin.mjs';
import getImportBridgesPlugin from './plugins/getImportBridgesPlugin.mjs';
import runEsbuild from './runEsbuild.mjs';
import writeExportBridge from './writeExportBridge.mjs';

export default async function bundleJavaScriptExports(
	globalImports,
	overridenPackageSymbols,
	projectExports
) {
	if (!projectExports.length) {
		return;
	}

	await Promise.all(
		projectExports
			.filter((moduleName) => !moduleName.endsWith('.css'))
			.map((moduleName) =>
				bundle(globalImports, overridenPackageSymbols, moduleName)
			)
	);
}

async function bundle(globalImports, overridenPackageSymbols, moduleName) {
	const esbuildConfig = {
		bundle: true,
		entryPoints: [getEntryPoint(moduleName)],
		external: getExternals(globalImports, 'exports'),
		format: 'esm',
		outdir: BUILD_MAIN_EXPORTS_PATH,
		plugins: [
			getExactAliasPlugin(globalImports, 'exports', [moduleName]),
			getImportBridgesPlugin(globalImports, overridenPackageSymbols),
		],
		sourcemap: true,
		target: ['es2020'],
	};

	await writeExportBridge(overridenPackageSymbols, moduleName);

	return runEsbuild(esbuildConfig, getFlatName(moduleName));
}
