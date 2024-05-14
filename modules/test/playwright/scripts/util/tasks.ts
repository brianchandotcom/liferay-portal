/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable no-console */

import {spawnSync} from 'child_process';
import {
	copyFileSync,
	existsSync,
	readFileSync,
	realpathSync,
	unlinkSync,
	writeFileSync,
} from 'fs';
import {join} from 'path';

function copyFile(
	setup: boolean,
	bundlesDir: string,
	from: string,
	to: string
) {
	if (setup && !existsSync(from)) {
		return;
	}

	const toPath = join(bundlesDir, to);

	if (!setup && !existsSync(toPath)) {
		return;
	}

	if (setup) {
		process.stdout.write(`      Deploying file: ${to}`);

		copyFileSync(from, toPath);
	}
	else {
		process.stdout.write(`      Undeploying file: ${to}`);

		unlinkSync(toPath);
	}

	console.log(' ✅');
}

function deployClientExtension(
	setup: boolean,
	portalSourceDir: string,
	bundlesDir: string,
	projectPath: string
) {
	const projectParts = projectPath.split('/');
	const workspacePath = projectParts.slice(0, 2).join('/');
	const projectName = projectParts[projectParts.length - 1];

	if (setup) {
		process.stdout.write(
			`      Deploying client extension: ${projectName}`
		);

		runCommand(
			join(portalSourceDir, projectPath),
			join(portalSourceDir, workspacePath, 'gradlew'),
			['deploy', '-a']
		);

		const portalDeployPath = join(bundlesDir, 'deploy');
		const workspaceDeployPath = join(
			portalSourceDir,
			workspacePath,
			'bundles',
			'osgi',
			'client-extensions'
		);

		if (
			realpathSync(portalDeployPath) !== realpathSync(workspaceDeployPath)
		) {
			copyFileSync(
				join(workspaceDeployPath, `${projectName}.zip`),
				join(portalDeployPath, `${projectName}.zip`)
			);
		}
	}
	else {
		process.stdout.write(
			`      Undeploying client extension: ${projectName}`
		);

		unlinkSync(
			join(bundlesDir, 'osgi', 'client-extensions', `${projectName}.zip`)
		);
	}

	console.log(' ✅');
}

function deployOSGiModule(
	setup: boolean,
	portalSourceDir: string,
	projectDir: string
) {
	if (setup) {
		process.stdout.write(`      Deploying module: ${projectDir}`);

		runCommand(
			join(portalSourceDir, projectDir),
			join(portalSourceDir, 'gradlew'),
			['deploy']
		);
	}
	else {
		process.stdout.write(`      Undeploying module: ${projectDir}`);

		runCommand(
			join(portalSourceDir, projectDir),
			join(portalSourceDir, 'gradlew'),
			['clean']
		);
	}

	console.log(' ✅');
}

function tweakPortalExtProperties(
	setup: boolean,
	bundlesDir: string,
	fileQualifiers: string[]
) {
	console.log(`⚙️ Tweaking portal-ext.properties:`);

	const portalExtPropertiesFile = join(bundlesDir, 'portal-ext.properties');
	const lines = readFileSync(portalExtPropertiesFile, 'utf-8').split('\n');

	if (setup) {
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
	}
	else {
		fileQualifiers.forEach((fileQualifier) => {
			const fileName = `portal-ext.${fileQualifier}.properties`;

			const property = `include-and-override=${fileName}`;

			const index = lines
				.map((line) => line.trim())
				.findIndex((line) => line === property);

			if (index === -1) {
				return;
			}

			console.log(
				`      Removing ${fileName} as include-and-override ✅`
			);

			lines.splice(index, 1);
		});
	}

	writeFileSync(portalExtPropertiesFile, lines.join('\n'), 'utf-8');
}

function runCommand(workDir: string, cmd: string, args: string[]) {
	const {error, status, stderr, stdout} = spawnSync(cmd, args, {
		cwd: workDir,
		stdio: 'pipe',
	});

	if (error) {
		throw new Error(
			`Failed to run command '${cmd} ${args.join(
				' '
			)}' (at ${workDir}}:\n\n` + `${error.toString()}`
		);
	}

	if (status !== 0) {
		throw new Error(
			`Failed to run command '${cmd} ${args.join(
				' '
			)}' (at ${workDir}}:\n\n` +
				`STDOUT:\n${stdout.toString()}\n\nSTDERR:\n${stderr.toString()}`
		);
	}

	return {stderr, stdout};
}

export default {
	setup: {
		copyFile: (bundlesDir: string, from: string, to: string) =>
			copyFile(true, bundlesDir, from, to),
		deployClientExtension: (
			portalSourceDir: string,
			bundlesDir: string,
			projectName: string
		) =>
			deployClientExtension(
				true,
				portalSourceDir,
				bundlesDir,
				projectName
			),
		deployOSGiModule: (portalSourceDir: string, projectDir: string) =>
			deployOSGiModule(true, portalSourceDir, projectDir),
		tweakPortalExtProperties: (
			bundlesDir: string,
			fileQualifiers: string[]
		) => tweakPortalExtProperties(true, bundlesDir, fileQualifiers),
	},
	teardown: {
		copyFile: (bundlesDir: string, from: string, to: string) =>
			copyFile(false, bundlesDir, from, to),
		deployClientExtension: (
			portalSourceDir: string,
			bundlesDir: string,
			projectName: string
		) =>
			deployClientExtension(
				false,
				portalSourceDir,
				bundlesDir,
				projectName
			),
		deployOSGiModule: (portalSourceDir: string, projectDir: string) =>
			deployOSGiModule(false, portalSourceDir, projectDir),
		tweakPortalExtProperties: (
			bundlesDir: string,
			fileQualifiers: string[]
		) => tweakPortalExtProperties(false, bundlesDir, fileQualifiers),
	},
};
