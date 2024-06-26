/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {$} from 'execa';
import os from 'os';
import path from 'path';
import resolve from 'resolve';

import {
	SRC_TSCONFIG_PATH,
	getProjectDirs,
	getRootDir,
} from '../util/constants.mjs';
import fileExists from '../util/fileExists.mjs';
import getFileProjectDir from '../util/getFileProjectDir.mjs';
import getGitModifiedFiles from '../util/getGitModifiedFiles.mjs';
import runConcurrentTasks from '../util/runConcurrentTasks.mjs';

export default async function runTscChecks(
	{baseDir, commitHash} = {baseDir: '.', commitHash: undefined}
) {
	const cwd = path.resolve(baseDir);
	const rootDir = await getRootDir();

	const results = [];

	if (cwd === rootDir) {
		const cpuCount = os.cpus().length;

		console.log(
			`ℹ️ A total of ${cpuCount} CPUs were detected: launching tsc using ${cpuCount} workers`
		);

		let projectDirs;

		if (commitHash) {
			projectDirs = await getGitModifiedProjectDirs(commitHash);

			console.log(
				`ℹ️ Going to check ${projectDirs.length} modified projects`
			);
		}
		else {
			projectDirs = await getProjectDirs();

			console.log(`ℹ️ Going to check ${projectDirs.length} projects`);
		}

		const tscResults = await runConcurrentTasks(
			projectDirs.map((projectDir) => async () => {
				if (
					!(await fileExists(
						path.join(projectDir, SRC_TSCONFIG_PATH)
					))
				) {
					return;
				}

				console.log(
					`🕵️ Checking ${path.relative(rootDir, projectDir)}`
				);

				return await runTsc(projectDir);
			})
		);

		results.push(...tscResults);
	}
	else {
		if (commitHash) {
			console.error(`
❌ Argument --modified-since can only be given when checking the whole liferay-portal from modules
   directory.
`);

			process.exit(2);
		}

		results.push(await runTsc(cwd));
	}

	return results.filter(Boolean);
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

	const configPath = path.join(
		'src',
		'main',
		'resources',
		'META-INF',
		'resources',
		'tsconfig.json'
	);

	const res = await $({
		all: true,
		cwd,
		reject: false,
		stdout: ['inherit', 'pipe'],
	})`${tscPath} -b ${configPath}`;

	const {failed, stdout} = res;

	if (failed) {
		return stdout;
	}
}
