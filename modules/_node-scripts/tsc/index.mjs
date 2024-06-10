/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import os from 'os';
import path from 'path';
import resolve from 'resolve';

import {
	SRC_TSCONFIG_PATH,
	getProjectDirs,
	getRootDir,
} from '../util/constants.mjs';
import fileExists from '../util/fileExists.mjs';
import forkModule from '../util/forkModule.mjs';
import getFileProjectDir from '../util/getFileProjectDir.mjs';
import getGitModifiedFiles from '../util/getGitModifiedFiles.mjs';
import getNamedArguments from '../util/getNamedArguments.mjs';

export default async function main() {
	const {modifiedSince} = getNamedArguments({
		modifiedSince: '--modified-since',
	});

	const cwd = path.resolve('.');
	const rootDir = await getRootDir();

	if (cwd === rootDir) {
		const cpuCount = os.cpus().length;

		console.log(
			`ℹ️ A total of ${cpuCount} CPUs were detected: launching tsc in groups of ${cpuCount} projects`
		);

		let projectDirs;

		if (modifiedSince) {
			projectDirs = await getGitModifiedProjectDirs(modifiedSince);

			console.log(
				`ℹ️ Going to check ${projectDirs.length} modified projects`
			);
		}
		else {
			projectDirs = await getProjectDirs();

			console.log(`ℹ️ Going to check ${projectDirs.length} projects`);
		}

		const projectGroups = [[]];

		for (const projectDir of projectDirs) {
			if (!(await fileExists(path.join(projectDir, SRC_TSCONFIG_PATH)))) {
				continue;
			}

			let group = projectGroups[projectGroups.length - 1];

			if (group.length === cpuCount) {
				group = [];

				projectGroups.push(group);
			}

			group.push(projectDir);
		}

		for (const projectGroup of projectGroups) {
			await Promise.all(
				projectGroup.map((projectDir) => {
					console.log(
						`🕵️ Checking ${path.relative(rootDir, projectDir)}`
					);

					return runTsc(projectDir);
				})
			);
		}
	}
	else {
		if (modifiedSince) {
			console.error(`
❌ Argument --modified-since can only be given when checking the whole liferay-portal from modules
   directory.
`);

			process.exit(2);
		}

		await runTsc(cwd);
	}
}

async function getGitModifiedProjectDirs(commit) {
	let files = await getGitModifiedFiles(commit);

	files = files.filter(
		(file) => file.endsWith('.ts') || file.endsWith('.tsx')
	);
	files = files.filter((file) => file.startsWith('modules/'));
	files = files.map((file) => file.substring(8));
	files = files.filter(
		(file) => file.startsWith('apps/') || file.startsWith('dxp/')
	);

	const projectDirs = new Set();

	for (const file of files) {
		projectDirs.add(await getFileProjectDir(file));
	}

	return [...projectDirs];
}

async function runTsc(cwd) {
	const tscPath = resolve.sync('typescript/bin/tsc', {basedir: '.'});

	await forkModule(
		tscPath,
		[
			'-b',
			path.join(
				'src',
				'main',
				'resources',
				'META-INF',
				'resources',
				'tsconfig.json'
			),
		],
		{
			cwd,
			stdio: 'inherit',
		}
	);
}
