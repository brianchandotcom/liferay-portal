/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import writeExportBridges from '../util/amd/writeExportBridges.mjs';
import writeMainBridge from '../util/amd/writeMainBridge.mjs';
import writeManifestJson from '../util/amd/writeManifestJson.mjs';
import writePackageJson from '../util/amd/writePackageJson.mjs';
import getGlobalImports from '../util/configuration/getGlobalImports.mjs';
import getLanguageJSON from '../util/configuration/getLanguageJSON.mjs';
import getOverriddenPackageSymbols from '../util/configuration/getOverriddenPackageSymbols.mjs';
import getProjectAlias from '../util/configuration/getProjectAlias.mjs';
import getProjectDescription from '../util/configuration/getProjectDescription.mjs';
import getProjectEntryPoints from '../util/configuration/getProjectEntryPoints.mjs';
import getProjectExports from '../util/configuration/getProjectExports.mjs';
import getProjectWebContextPath from '../util/configuration/getProjectWebContextPath.mjs';
import processCSSFiles from '../util/css/processCSSFiles.mjs';
import emptyDir from '../util/emptyDir.mjs';
import bundleCSSExports from '../util/esbuild/bundleCSSExports.mjs';
import bundleJavaScriptExports from '../util/esbuild/bundleJavaScriptExports.mjs';
import bundleJavaScriptMain from '../util/esbuild/bundleJavaScriptMain.mjs';
import {
	BUILD_MAIN_EXPORTS_PATH,
	BUILD_SASS_CACHE_PATH,
} from '../util/locations.mjs';
import processSassFiles from '../util/sass/processSassFiles.mjs';
import writeTimings from '../util/writeTimings.mjs';

export default async function main() {
	const start = Date.now();

	const [
		globalImports,
		languageJSON,
		overriddenPackageSymbols,
		projectAlias,
		projectDescription,
		projectEntryPoints,
		projectExports,
		projectWebContextPath,
	] = await Promise.all([
		getGlobalImports(),
		getLanguageJSON(),
		getOverriddenPackageSymbols(),
		getProjectAlias(),
		getProjectDescription(),
		getProjectEntryPoints(),
		getProjectExports(),
		getProjectWebContextPath(),
	]);

	const endConfig = Date.now();

	//
	// Empty some output dirs so that we don't find leftovers of previous
	// builds. See https://liferay.atlassian.net/browse/LPD-74323.
	//

	await Promise.all([
		emptyDir(BUILD_MAIN_EXPORTS_PATH),
		emptyDir(BUILD_SASS_CACHE_PATH),
	]);

	await Promise.all([processCSSFiles(), processSassFiles()]);

	await Promise.all([

		// JavaScript exports bundling

		bundleJavaScriptMain(
			globalImports,
			languageJSON,
			overriddenPackageSymbols,
			projectAlias,
			projectDescription,
			projectEntryPoints,
			projectWebContextPath
		),
		bundleJavaScriptExports(
			globalImports,
			overriddenPackageSymbols,
			projectAlias,
			projectExports,
			projectWebContextPath
		),

		// CSS exports bundling

		bundleCSSExports(projectExports, projectWebContextPath),

		// AMD bridging

		writeMainBridge(
			projectDescription,
			projectEntryPoints,
			projectWebContextPath
		),
		writeExportBridges(
			projectDescription,
			projectExports,
			projectWebContextPath
		),
		writeManifestJson(
			projectDescription,
			projectEntryPoints,
			projectExports
		),
		writePackageJson(
			projectDescription,
			projectEntryPoints,
			projectExports
		),
	]);

	await writeTimings(start, endConfig);
}
