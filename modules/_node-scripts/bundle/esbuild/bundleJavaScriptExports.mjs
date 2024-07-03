/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import {BUILD_MAIN_EXPORTS_PATH} from '../../util/constants.mjs';
import getFlatName from '../../util/getFlatName.mjs';
import getEntryPoint from './getEntryPoint.mjs';
import getExternals from './getExternals.mjs';
import getExactAliasPlugin from './plugins/getExactAliasPlugin.mjs';
import getImportBridgesPlugin from './plugins/getImportBridgesPlugin.mjs';
import relocateSourcemap from './relocateSourcemap.mjs';
import runEsbuild from './runEsbuild.mjs';
import writeExportBridge from './writeExportBridge.mjs';

export default async function bundleJavaScriptExports(
	globalImports,
	overridenPackageSymbols,
	projectExports,
	projectWebContextPath
) {
	if (!projectExports.length) {
		return;
	}

	await Promise.all(
		projectExports
			.filter((moduleName) => !moduleName.endsWith('.css'))
			.map((moduleName) =>
				bundle(
					globalImports,
					overridenPackageSymbols,
					projectWebContextPath,
					moduleName
				)
			)
	);
}

async function bundle(
	globalImports,
	overridenPackageSymbols,
	projectWebContextPath,
	moduleName
) {
	const esbuildConfig = {
		bundle: true,
		entryPoints: [getEntryPoint(moduleName)],
		external: getExternals(globalImports, projectWebContextPath, 'exports'),
		format: 'esm',
		outdir: BUILD_MAIN_EXPORTS_PATH,
		plugins: [
			getExactAliasPlugin(globalImports, 'exports', [moduleName]),
			getImportBridgesPlugin(globalImports, overridenPackageSymbols),
		],
		sourcemap: true,
		target: ['es2022'],
	};

	await writeExportBridge(overridenPackageSymbols, moduleName);

	const flatModuleName = getFlatName(moduleName);

	await runEsbuild(esbuildConfig, flatModuleName);

	await relocateSourcemap(
		path.join(
			BUILD_MAIN_EXPORTS_PATH,
			'exports',
			`${flatModuleName}.js.map`
		),
		projectWebContextPath
	);
}
