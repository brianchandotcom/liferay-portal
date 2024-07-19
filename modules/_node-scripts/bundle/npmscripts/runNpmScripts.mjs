/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';
import {constants, mkdirSync, renameSync} from 'fs';
import * as fs from 'fs/promises';
import path from 'path';

import {WORK_PATH} from '../../util/constants.mjs';
import onExit from '../../util/onExit.mjs';

const DISABLE_BUILD_CONFIGS = [
	'babel',
	'bundler',
	'exports',
	'main',
	'sass',
	'tsc',
];

export default async function runNpmScripts(projectNpmScriptsConfig) {
	if (!projectNpmScriptsConfig) {
		return;
	}

	const start = performance.now();

	await writeNpmScriptsConfig(projectNpmScriptsConfig);

	const {stdout} = await $`liferay-npm-scripts build`;

	const lapse = performance.now() - start;

	console.log(
		`⌛ Legacy build (liferay-npm-scripts) took: ${(lapse / 1000).toFixed(3)} s\n\n${stdout}`
	);
}

async function writeNpmScriptsConfig(projectNpmScriptsConfig) {
	if (!projectNpmScriptsConfig.build) {
		projectNpmScriptsConfig.build = {};
	}

	for (const config of DISABLE_BUILD_CONFIGS) {
		if (projectNpmScriptsConfig.build[config]) {
			console.warn(
				`WARNING: ignoring build.${config} configuration for npmscripts`
			);
		}

		projectNpmScriptsConfig.build[config] = false;
	}

	try {
		await fs.access('npmscripts.config.js', constants.F_OK);

		await fs.rename('npmscripts.config.js', 'npmscripts.config.js.$$$');

		await fs.writeFile(
			'npmscripts.config.js',
			`module.exports = ${JSON.stringify(
				projectNpmScriptsConfig,
				null,
				2
			)};`,
			'utf-8'
		);

		onExit(restoreNpmScriptsConfig);
	}
	catch (error) {
		if (error.code !== 'ENOENT') {
			throw error;
		}

		await fs.writeFile(
			'npmscripts.config.js',
			`module.exports = ${JSON.stringify(
				projectNpmScriptsConfig,
				null,
				2
			)};`,
			'utf-8'
		);

		onExit(moveNpmScriptsConfig);
	}
}

function moveNpmScriptsConfig() {
	try {
		mkdirSync(WORK_PATH, {recursive: true});
		renameSync(
			'npmscripts.config.js',
			path.join(WORK_PATH, 'npmscripts.config.js')
		);
	}
	catch (error) {

		// ignore

	}
}

function restoreNpmScriptsConfig() {
	moveNpmScriptsConfig();

	try {
		renameSync('./npmscripts.config.js.$$$', './npmscripts.config.js');
	}
	catch (error) {

		// ignore

	}
}
