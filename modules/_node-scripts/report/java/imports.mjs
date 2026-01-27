/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fg from 'fast-glob';
import fs from 'fs/promises';
import path from 'path';

import getYarnWorkspaceProjects from '../../util/getYarnWorkspaceProjects.mjs';
import {PORTAL_DIR} from '../../util/locations.mjs';

export default async function main() {
	const projectDirectories = await getYarnWorkspaceProjects();

	// Go back one dir because we need to search for occurrences in non OSGI code

	projectDirectories.push(path.join(PORTAL_DIR, 'portal-impl'));
	projectDirectories.push(path.join(PORTAL_DIR, 'portal-kernel'));
	projectDirectories.push(path.join(PORTAL_DIR, 'portal-web'));
	projectDirectories.push(path.join(PORTAL_DIR, 'util-taglib'));

	const results = await Promise.all(
		projectDirectories.map(async (projectDir) => {
			const files = await fg(`${projectDir}/**/*.{java,jsp,jspf}`, {
				ignore: ['**/build/**', '**/classes/**', '**/test/**'],
			});

			const sources = await Promise.all(
				files.map((file) => fs.readFile(file, 'utf-8'))
			);

			const importsSymbols = {};

			for (const source of sources) {
				findStaticImports(source, importsSymbols);
				findDynamicImports(source, importsSymbols);
			}

			for (const importPath of Object.keys(importsSymbols)) {
				importsSymbols[importPath] = [...importsSymbols[importPath]];
			}

			return [projectDir, importsSymbols];
		})
	);

	const projectImportsSymbols = results.reduce(
		(projectImportsSymbols, [projectDir, importsSymbols]) => {
			projectImportsSymbols[projectDir] = importsSymbols;

			return projectImportsSymbols;
		},
		{}
	);

	const csvFile = 'java-imports.csv';
	const lines = ['BUNDLE;IMPORT;SYMBOLS'];

	Object.entries(projectImportsSymbols).forEach(
		([projectDir, importsSymbols]) => {
			const projectRelDir = path.relative(PORTAL_DIR, projectDir);

			Object.entries(importsSymbols).forEach(([importPath, symbols]) => {
				lines.push(
					`"${projectRelDir}";"${importPath}";"${[...symbols].sort().join(',')}"`
				);
			});
		}
	);

	await fs.writeFile(csvFile, lines.join('\n'));

	console.log(`
ℹ️  The report has been created at: ${csvFile}
`);
}

function findStaticImports(source, importsSymbols) {
	const matches = [...source.matchAll(/"{[^}]+} from [^"]+"/g)].map((match) =>
		match.toString()
	);

	if (!matches.length) {
		return;
	}

	for (const match of matches) {
		const parts = match.split(' from ');

		const importPath = parts[1].substring(0, parts[1].length - 1);

		if (!importsSymbols[importPath]) {
			importsSymbols[importPath] = new Set();
		}

		const symbols = parts[0]
			.substring(2, parts[0].length - 1)
			.split(',')
			.map((symbol) => symbol.trim());

		symbols.forEach((symbol) => importsSymbols[importPath].add(symbol));
	}
}

function findDynamicImports(source, importsSymbols) {
	const matches = [
		...source.matchAll(/import\((.+)\).then\(/g),
		...source.matchAll(/await import\((.+\))/g),
	];

	if (!matches.length) {
		return;
	}

	for (const match of matches) {
		const importPath = match[1];

		if (!importsSymbols[importPath]) {
			importsSymbols[importPath] = new Set();
		}
	}
}
