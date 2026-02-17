/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';
import Sonda from 'sonda/esbuild';

import {
	BUILD_LANGUAGE_JSON_PATH,
	BUILD_MAIN_EXPORTS_PATH,
	BUNDLE_REPORTS_PATH,
} from '../../util/locations.mjs';
import objectSF from '../../util/objectSF.mjs';
import getLiferayLanguageGetPlugin from './plugins/getLiferayLanguageGetPlugin.mjs';
import getLinkerPlugin from './plugins/getLinkerPlugin.mjs';
import relocateSourcemap from './util/relocateSourcemap.mjs';
import runEsbuild from './util/runEsbuild.mjs';

export default async function bundleJavaScriptMain(
	globalImports,
	languageJSON,
	overridenPackageSymbols,
	projectAlias,
	projectDescription,
	projectEntryPoints,
	projectWebContextPath
) {
	const {main: mainEntryPoint, submodules = {}} = projectEntryPoints;

	if (!mainEntryPoint) {
		return;
	}

	const esbuildConfig = {
		alias: projectAlias,
		bundle: true,
		entryNames: '[dir]/[name].([hash])',
		entryPoints: [
			...Object.keys(submodules).map((submoduleName) => ({
				in: path.resolve(submodules[submoduleName]),
				out: submoduleName,
			})),
			{in: path.resolve(mainEntryPoint), out: 'index'},
		],
		format: 'esm',
		loader: {
			'.js': 'jsx',
			'.png': 'empty',
			'.scss': 'css',
		},
		outdir: BUILD_MAIN_EXPORTS_PATH,
		plugins: [
			getLiferayLanguageGetPlugin(projectWebContextPath, languageJSON),
			getLinkerPlugin(
				globalImports,
				overridenPackageSymbols,
				projectWebContextPath,
				'main',
				projectEntryPoints
			),
		],
		sourcemap: true,
		target: ['es2022'],
	};

	if (process.env.CREATE_BUNDLE_REPORTS) {
		esbuildConfig.plugins.push(
			Sonda({
				brotli: false,
				detailed: false,
				enabled: true,
				filename: path.join(BUNDLE_REPORTS_PATH, `index.js.html`),
				format: 'html',
				gzip: true,
				open: false,
				sources: false,
			}),
			Sonda({
				brotli: false,
				detailed: false,
				enabled: true,
				filename: path.join(BUNDLE_REPORTS_PATH, `index.js.json`),
				format: 'json',
				gzip: true,
				open: false,
			})
		);
	}

	const {metafile} = await runEsbuild(esbuildConfig, 'main');
	const {outputs} = metafile;

	await Promise.all([
		...Object.keys(outputs).map(async (output) => {
			if (output.endsWith('.map')) {
				return relocateSourcemap(
					path.join(output),
					projectWebContextPath
				);
			}
		}),
		writeLanguageJSON(languageJSON),
	]);
}

async function writeLanguageJSON(languageJSON) {
	if (!languageJSON.keys.length) {
		return;
	}

	// Dedupe language keys before writing them

	languageJSON.keys = [...new Set(languageJSON.keys)];

	await fs.writeFile(
		BUILD_LANGUAGE_JSON_PATH,
		objectSF(languageJSON),
		'utf-8'
	);
}
