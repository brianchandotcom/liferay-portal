/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import esbuild from 'esbuild';
import fs from 'fs/promises';
import path from 'path';

export default async function runEsbuild(esbuildConfig, configName) {
	const [result] = await Promise.all([
		doRunEsbuild(esbuildConfig, configName),
		writeDebugEsbuildConfig(esbuildConfig, configName),
	]);

	return result;
}

async function doRunEsbuild(esbuildesbuildConfig, configName) {
	let result;

	const start = performance.now();

	try {
		result = await esbuild.build({
			define: {

				// Flag to use React 16 instead of React 18. See render.tsx in frontend-js-react-web.

				'process.env.USE_REACT_16': process.env.USE_REACT_16
					? 'true'
					: 'false',
			},
			metafile: true,
			minify: process.env.NODE_ENV === 'production',
			...esbuildesbuildConfig,
		});
	}
	catch (error) {
		throw new Error(`Esbuild command failed: ${error}`);
	}

	const lapse = performance.now() - start;

	console.log(
		`⌛ Esbuild for ${configName} took: ${(lapse / 1000).toFixed(3)} s`
	);

	return result;
}

async function writeDebugEsbuildConfig(esbuildConfig, configName) {
	const configFilePath = path.join(
		'build',
		'node-build',
		`${configName}.esbuild.config.json`
	);

	await fs.mkdir(path.dirname(configFilePath), {recursive: true});
	await fs.writeFile(
		configFilePath,
		JSON.stringify(esbuildConfig, null, '\t'),
		'utf-8'
	);
}
