/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {spawnSync} from 'child_process';
import {
	copyFileSync,
	existsSync,
	readdirSync,
	readFileSync,
	writeFileSync,
} from 'fs';
import {basename, join, resolve, dirname} from 'path';
import {fileURLToPath} from 'url';

const __dirname = dirname(fileURLToPath(import.meta.url));

main();

function main() {
	const testDir = resolve(__dirname, '..', 'tests', process.argv[2]);

	if (!existsSync(testDir)) {
		console.error(`❌ Please provide the name of a project to setup.`);

		process.exit(1);
	}

	const portalSourceDir = resolve(__dirname, '..', '..', '..', '..');
	const bundlesDir = resolve(portalSourceDir, '..', 'bundles');

	console.log('💡 Setting up local DXP for tests...');

	setupTestProject(portalSourceDir, bundlesDir, 'playwright');

	setupTestProject(portalSourceDir, bundlesDir, testDir);

	tweakPortalExtProperties(bundlesDir, ['playwright', basename(testDir)]);

	console.log('🎉 Setup complete!');
}

function setupTestProject(portalSourceDir, bundlesDir, projectDir) {
	projectDir =
		projectDir == 'playwright' ? resolve(__dirname, '..') : projectDir;

	const projectName = basename(projectDir);
	const envDir = join(projectDir, 'env');

	console.log(`⚙️ Setting up project ${projectName}:`);

	const portalExtPropertiesFile = join(envDir, 'portal-ext.properties');

	copy(
		bundlesDir,
		portalExtPropertiesFile,
		`portal-ext.${projectName}.properties`
	);

	const deployDir = join(envDir, 'deploy');

	if (existsSync(deployDir)) {
		const fileNames = readdirSync(deployDir);

		fileNames.forEach((fileName) =>
			copy(
				bundlesDir,
				join(deployDir, fileName),
				join('deploy', fileName)
			)
		);
	}

	const osgiModulesListFile = join(envDir, 'osgi-modules.list');

	if (existsSync(osgiModulesListFile)) {
		const projectDirs = parseListFile(osgiModulesListFile);

		projectDirs.forEach((projectDir) =>
			deployOSGiModule(portalSourceDir, projectDir)
		);
	}

	const clientExtensionsListFile = join(envDir, 'client-extensions.list');

	if (existsSync(clientExtensionsListFile)) {
		const projectNames = parseListFile(clientExtensionsListFile);

		projectNames.forEach((projectName) =>
			deployClientExtension(portalSourceDir, bundlesDir, projectName)
		);
	}
}

function copy(bundlesDir, from, to) {
	if (!existsSync(from)) {
		return;
	}

	process.stdout.write(`      Deploying file: ${to}`);

	copyFileSync(from, join(bundlesDir, to));

	console.log(' ✅');
}

function deployClientExtension(portalSourceDir, bundlesDir, projectName) {
	process.stdout.write(`      Deploying client extension: ${projectName}`);

	const {error, status, stderr, stdout} = spawnSync(
		'gradlew',
		['deploy', '-a'],
		{
			cwd: join(
				portalSourceDir,
				'workspace',
				'liferay-sample-workspace',
				'client-extensions',
				projectName
			),
			stdio: 'pipe',
		}
	);

	if (error) {
		throw new Error(
			`Failed to deploy client extension ${projectName}:\n\n` +
				`${error.toString()}`
		);
	}

	if (status !== 0) {
		throw new Error(
			`Failed to deploy client extension ${projectName}:\n\n` +
				`${stdout.toString()}\n${stderr.toString()}`
		);
	}

	copyFileSync(
		join(
			portalSourceDir,
			'workspaces',
			'liferay-sample-workspace',
			'bundles',
			'osgi',
			'client-extensions',
			`${projectName}.zip`
		),
		join(bundlesDir, 'deploy')
	);

	console.log(' ✅');
}

function deployOSGiModule(portalSourceDir, projectDir) {
	process.stdout.write(`      Deploying module: ${projectDir}`);

	const {error, status, stderr, stdout} = spawnSync(
		'gradlew',
		['deploy', '-a'],
		{
			cwd: join(portalSourceDir, projectDir),
			stdio: 'pipe',
		}
	);

	if (error) {
		throw new Error(
			`Failed to deploy OSGi module ${projectDir}:\n\n` +
				`${error.toString()}`
		);
	}

	if (status !== 0) {
		throw new Error(
			`Failed to deploy OSGi module ${projectDir}:\n\n` +
				`${stdout.toString()}\n${stderr.toString()}`
		);
	}

	console.log(' ✅');
}

function parseListFile(listFile) {
	return readFileSync(listFile, 'utf-8')
		.split('\n')
		.map((line) => line.trim())
		.filter((line) => !!line.length);
}

function tweakPortalExtProperties(bundlesDir, fileQualifiers) {
	console.log(`⚙️ Tweaking portal-ext.properties:`);

	const portalExtPropertiesFile = join(bundlesDir, 'portal-ext.properties');
	const lines = readFileSync(portalExtPropertiesFile, 'utf-8').split('\n');

	fileQualifiers.forEach((fileQualifier) => {
		const fileName = `portal-ext.${fileQualifier}.properties`;

		if (!existsSync(join(bundlesDir, fileName))) {
			return;
		}

		const property = `include-and-override=${fileName}`;

		const found = lines
			.map((line) => line.trim())
			.find((line) => line === property);

		if (found) {
			return;
		}

			console.log(`      Adding ${fileName} as include-and-override ✅`);

			lines.push(property);
	});

	if (lines[lines.length - 1] !== '') {
		lines.push('');
	}

	writeFileSync(portalExtPropertiesFile, lines.join('\n'), 'utf-8');
}
