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
import getNamedArguments from '../util/getNamedArguments.mjs';
import gitUtil from '../util/gitUtil.mjs';
import runConcurrentTasks from '../util/runConcurrentTasks.mjs';

export default async function main() {
	const {all, currentBranch, localChanges} = getNamedArguments({
		all: '--all',
		currentBranch: '--current-branch',
		localChanges: '--local-changes',
	});

	const cwd = path.resolve('.');

	if (cwd === (await getRootDir())) {
		let projectDirs;

		if (currentBranch) {
			projectDirs = await extractProjectDirs(
				await gitUtil('current-branch')
			);
		}
		else if (localChanges) {
			projectDirs = await extractProjectDirs(
				await gitUtil('local-changes')
			);
		}
		else {
			if (!all) {
				console.log(`
⚠️ Checking all projects takes long, you may want to use --local-changes or --current-branch arguments
`);
			}

			projectDirs = await getProjectDirs();
		}

		console.log(`ℹ️ Going to check ${projectDirs.length} projects`);

		await checkProjects(projectDirs);
	}
	else {
		if (currentBranch || localChanges) {
			console.error(`
❌ Arguments --current-branch or --local-changes are not valid when checking a single project.
`);

			process.exit(2);
		}

		await runTsc(cwd, false);
	}
}

async function checkProjects(projectDirs) {
	const cpuCount = os.cpus().length;

	console.log(
		`ℹ️ A total of ${cpuCount} CPUs were detected: launching tsc using ${cpuCount} workers`
	);

	const rootDir = await getRootDir();

	await runConcurrentTasks(
		projectDirs.map((projectDir) => async () => {
			if (!(await fileExists(path.join(projectDir, SRC_TSCONFIG_PATH)))) {
				return;
			}

			let icon = '✅';
			let output = await runTsc(projectDir, true);

			output = output.trim();

			if (output) {
				icon = '❌';
				output = `\n${output
					.split('\n')
					.map((line) => `   ${line}`)
					.join('\n')}\n`;
			}

			console.log(
				`${icon} Checked ${path.relative(rootDir, projectDir)}${output}`
			);
		})
	);
}

async function extractProjectDirs(modifiedFiles) {
	modifiedFiles = modifiedFiles.filter(
		(file) => file.endsWith('.ts') || file.endsWith('.tsx')
	);
	modifiedFiles = modifiedFiles.filter((file) => file.startsWith('modules/'));
	modifiedFiles = modifiedFiles.map((file) => file.substring(8));
	modifiedFiles = modifiedFiles.filter(
		(file) => file.startsWith('apps/') || file.startsWith('dxp/')
	);

	const projectDirs = new Set();

	for (const file of modifiedFiles) {
		projectDirs.add(await getFileProjectDir(file));
	}

	return [...projectDirs];
}

async function runTsc(cwd, capture) {
	const tscPath = resolve.sync('typescript/bin/tsc', {basedir: '.'});

	const configPath = path.join(
		'src',
		'main',
		'resources',
		'META-INF',
		'resources',
		'tsconfig.json'
	);

	const {all} = await $({
		all: true,
		cwd,
		reject: false,
		stdout: capture ? 'pipe' : 'inherit',
	})`${tscPath} -b ${configPath}`;

	return all;
}
