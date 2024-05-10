import * as childProcess from 'child_process';
import {constants, mkdirSync, renameSync} from 'fs';
import * as fs from 'fs/promises';
import resolve from 'resolve';

import {WORK_PATH} from '../util/constants.mjs';
import onExit from '../util/onExit.mjs';

const DISABLE_BUILD_CONFIGS = [
	'babel',
	'bundler',
	'exports',
	'main'
];

export default async function runNpmScripts(projectNpmScriptsConfig) {
	if (!projectNpmScriptsConfig) {
		return;
	}

	await writeNpmScriptsConfig(projectNpmScriptsConfig);

	const npmScriptsPath =
		resolve.sync('@liferay/npm-scripts/bin/liferay-npm-scripts.js', {basedir: '.'});

	const argv = [
		'build',
	];

	if (process.argv.includes('--clean')) {
		argv.push('--clean');
	}

	const child = childProcess.spawn(
		npmScriptsPath,
		argv,
		{
			stdio: 'inherit'
		}
	);

	let killChild = true;

	onExit(() => {
		if (killChild) {
			child.kill();
		}
	});

	return new Promise((resolve, reject) => {
		child.on('exit', (code, signal) => {
			killChild = false;

			if (code === 0) {
				resolve();
			}
			else if (code !== null) {
				reject(new Error(`Error: liferay-npm-scripts finished with status ${code}`));
			}
			else {
				reject(new Error(`Error: liferay-npm-scripts finished due to signal ${signal}`));
			}
		});

		child.on('error', reject);
	});
}

async function writeNpmScriptsConfig(projectNpmScriptsConfig) {
	if (!projectNpmScriptsConfig.build) {
		projectNpmScriptsConfig.build = {};
	}

	for (const config of DISABLE_BUILD_CONFIGS) {
		if (projectNpmScriptsConfig.build[config]) {
			console.warn(`WARNING: ignoring build.${config} configuration for npmscripts`);
		}

		projectNpmScriptsConfig.build[config] = false;
	}

	try {
		await fs.access('npmscripts.config.js', constants.F_OK);

		await fs.rename('npmscripts.config.js', 'npmscripts.config.js.$$$');

		await fs.writeFile(
			'npmscripts.config.js',
			`module.exports = ${JSON.stringify(projectNpmScriptsConfig, null, 2)};`,
			'utf-8'
		);

		onExit(restoreNpmScriptsConfig);
	}
	catch(error) {
		if (error.code !== 'ENOENT') {
			throw error;
		}

		await fs.writeFile(
			'npmscripts.config.js',
			`module.exports = ${JSON.stringify(projectNpmScriptsConfig, null, 2)};`,
			'utf-8'
		);

		onExit(moveNpmScriptsConfig);
	}
}

function moveNpmScriptsConfig() {
	try {
		mkdirSync(WORK_PATH, {recursive: true});
		renameSync('npmscripts.config.js', path.join(WORK_PATH, 'npmscripts.config.js'));
	}
	catch(error) {
		// ignore
	}
}

function restoreNpmScriptsConfig() {
	moveNpmScriptsConfig();

	try {
		renameSync('./npmscripts.config.js.$$$', './npmscripts.config.js');
	}
	catch(error) {
		// ignore
	}
}
