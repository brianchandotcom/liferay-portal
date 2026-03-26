/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fg from 'fast-glob';
import fs from 'fs/promises';
import path from 'path';

import getYarnWorkspaceProjects from '../util/getYarnWorkspaceProjects.mjs';
import {BUNDLE_REPORTS_PATH} from '../util/locations.mjs';
import print from '../util/print.mjs';

export default async function main() {
	const projectDirectories = await getYarnWorkspaceProjects();

	const dependencyFolders = new Set();

	const rootInputs = await getRootInputs(projectDirectories);

	Object.entries(rootInputs).forEach(([projectDir, rootInputs]) => {
		const rootInputPaths = rootInputs.map((rootInput) =>
			path.join(projectDir, rootInput)
		);

		const packageFolders = rootInputPaths.map((rootInputPath) => {
			const i = rootInputPath.indexOf('/node_modules/');

			const parts = rootInputPath.substring(i + 14).split('/');

			let packageName;

			if (parts[0].startsWith('@')) {
				packageName = `${parts[0]}/${parts[1]}`;
			}
			else {
				packageName = parts[0];
			}

			return `${rootInputPath.substring(0, i + 13)}/${packageName}`;
		});

		for (const packageFolder of packageFolders) {
			dependencyFolders.add(packageFolder);
		}
	});

	const dependencies = new Set();

	for (const dependencyFolder of dependencyFolders) {
		const json = JSON.parse(
			await fs.readFile(
				path.join(dependencyFolder, 'package.json'),
				'utf-8'
			)
		);

		dependencies.add({
			name: json.name,
			version: json.version,
		});
	}

	const csvFile = 'dependencies.csv';
	const lines = ['PACKAGE;VERSION'];

	[...dependencies]
		.sort((l, r) => l.name.localeCompare(r.name))
		.forEach(({name, version}) => lines.push(`${name};${version}`));

	await fs.writeFile(csvFile, lines.join('\n'));

	print(0, print.info('\nINFO:'), `Wrote report file: ${csvFile}\n`);
}

async function getRootInputs(projectDirectories) {
	const rootInputs = {};

	for (const projectDir of projectDirectories) {
		rootInputs[projectDir] = await getProjectRootInputs(projectDir);
	}

	return rootInputs;
}

async function getProjectRootInputs(projectDir) {
	const bundleReportsPath = path.join(projectDir, BUNDLE_REPORTS_PATH);

	const files = await fg('**/*.json', {
		cwd: bundleReportsPath,
	});

	let jsons;

	try {
		jsons = await Promise.all(
			files.map(async (file) => {
				return JSON.parse(
					await fs.readFile(
						path.join(bundleReportsPath, file),
						'utf-8'
					)
				);
			})
		);
	}
	catch (error) {
		if (error.code !== 'ENOENT') {
			throw error;
		}

		return null;
	}

	const rootProjectInputs = [];

	for (const {inputs} of jsons) {
		const rootInputs = Object.entries(inputs).filter(
			([inputPath, data]) =>
				inputPath.includes('/node_modules/') &&
				(data.belongsTo === null ||
					!data.belongsTo.includes('/node_modules/'))
		);

		rootProjectInputs.push(...rootInputs.map(([inputPath]) => inputPath));
	}

	return rootProjectInputs;
}
