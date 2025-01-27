/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';
import Sonda from 'sonda/esbuild';

import {
	BUILD_MAIN_EXPORTS_PATH,
	BUNDLE_REPORTS_PATH,
} from '../../util/constants.mjs';
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
	const entryPoint = getEntryPoint(moduleName);

	const esbuildConfig = {
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

	return runEsbuild(esbuildConfig, getFlatName(moduleName));
}
