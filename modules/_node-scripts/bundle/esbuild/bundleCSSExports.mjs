/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';
import Sonda from 'sonda/esbuild';

import {
	BUILD_MAIN_EXPORTS_PATH,
	BUNDLE_REPORTS_PATH,
} from '../../util/constants.mjs';
import getFlatName from '../../util/getFlatName.mjs';
import calculateFileHash from '../util/calculateFileHash.mjs';
import getCSSExportLoaderModuleJavaScript from './util/getCSSExportLoaderModuleJavaScript.mjs';
import getCSSExportLoaderModulePath from './util/getCSSExportLoaderModulePath.mjs';
import runEsbuild from './util/runEsbuild.mjs';

export default async function bundleCSSExports(
	projectExports,
	projectWebContextPath
) {
	await Promise.all(
		projectExports
			.filter((moduleName) => moduleName.endsWith('.css'))
			.map((moduleName) => bundle(projectWebContextPath, moduleName))
	);
}

async function bundle(webContextPath, moduleName) {
	const entryPoint = {
		in: moduleName,
		out: `css/${getFlatName(moduleName).replace(/\.css$/, '')}`,
	};

	const esbuildConfig = {
		entryNames: '[dir]/[name].([hash])',
		entryPoints: [entryPoint],
		loader: {
			'.png': 'empty',
		},
		outdir: BUILD_MAIN_EXPORTS_PATH,
		plugins: [],
		sourcemap: true,
	};

	if (process.env.CREATE_BUNDLE_REPORTS) {
		esbuildConfig.plugins.push(
			Sonda({
				brotli: false,
				detailed: false,
				enabled: true,
				filename: path.join(
					BUNDLE_REPORTS_PATH,
					`${entryPoint.out}.html`
				),
				format: 'html',
				gzip: true,
				open: false,
				sources: false,
			}),
			Sonda({
				brotli: false,
				detailed: false,
				enabled: true,
				filename: path.join(
					BUNDLE_REPORTS_PATH,
					`${entryPoint.out}.json`
				),
				format: 'json',
				gzip: true,
				open: false,
			})
		);
	}

	await runEsbuild(esbuildConfig, getFlatName(moduleName));

	await writeCSSExportLoaderModule(webContextPath, moduleName);
}

async function writeCSSExportLoaderModule(webContextPath, moduleName) {
	const source = await getCSSExportLoaderModuleJavaScript(
		'../../..',
		webContextPath,
		moduleName
	);

	const cssLoaderPath = getCSSExportLoaderModulePath(
		moduleName,
		await calculateFileHash(source)
	);

	await fs.mkdir(path.dirname(cssLoaderPath), {recursive: true});
	await fs.writeFile(cssLoaderPath, source);
}
