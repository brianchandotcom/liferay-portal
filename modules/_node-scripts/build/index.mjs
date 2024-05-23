/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getGlobalImports from '../configuration/getGlobalImports.mjs';
import getOverridenPackageSymbols from '../configuration/getOverridenPackageSymbols.mjs';
import getProjectDescription from '../configuration/getProjectDescription.mjs';
import getProjectExports from '../configuration/getProjectExports.mjs';
import getProjectMain from '../configuration/getProjectMain.mjs';
import getProjectNpmScriptsConfig from '../configuration/getProjectNpmScriptsConfig.mjs';
import getProjectWebContextPath from '../configuration/getProjectWebContextPath.mjs';
import writeAMD2ESMBridges from './amd/writeAMD2ESMBridges.mjs';
import writeManifestJson from './amd/writeManifestJson.mjs';
import writePackageJson from './amd/writePackageJson.mjs';
import writeCSSExportsLoaderModules from './cssLoad/writeCSSExportsLoaderModules.mjs';
import bundleCSSExports from './esbuild/bundleCSSExports.mjs';
import bundleJavaScriptExports from './esbuild/bundleJavaScriptExports.mjs';
import bundleJavaScriptMain from './esbuild/bundleJavaScriptMain.mjs';
import runNpmScripts from './npmscripts/runNpmScripts.mjs';
import writeTimings from './writeTimings.mjs';

export default async function main() {
	const start = Date.now();

	const [
		globalImports,
		overridenPackageSymbols,
		projectDescription,
		projectExports,
		projectMain,
		projectNpmScriptsConfig,
		projectWebContextPath,
	] = await Promise.all([
		getGlobalImports(),
		getOverridenPackageSymbols(),
		getProjectDescription(),
		getProjectExports(),
		getProjectMain(),
		getProjectNpmScriptsConfig(),
		getProjectWebContextPath(),
	]);

	const endConfig = Date.now();

	await Promise.all([
		bundleJavaScriptMain(
			globalImports,
			overridenPackageSymbols,
			projectMain,
			projectWebContextPath
		),
		bundleJavaScriptExports(
			globalImports,
			overridenPackageSymbols,
			projectExports
		),
		bundleCSSExports(projectExports),
		writeCSSExportsLoaderModules(projectExports, projectWebContextPath),
		writePackageJson(projectDescription),
		writeManifestJson(projectDescription),
		writeAMD2ESMBridges(projectDescription, projectWebContextPath),
		runNpmScripts(projectNpmScriptsConfig),
	]);

	await writeTimings(start, endConfig);
}
