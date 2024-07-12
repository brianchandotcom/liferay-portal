/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import esbuild from 'esbuild';
import fs from 'fs/promises';
import path from 'path';

export default async function runEsbuild(esbuildConfig, configName) {
	await Promise.all([
		doRunEsbuild(esbuildConfig, configName),
		writeDebugEsbuildConfig(esbuildConfig, configName),
	]);
}

async function doRunEsbuild(esbuildesbuildConfig, configName) {
	const start = performance.now();

	try {
		await esbuild.build({
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
