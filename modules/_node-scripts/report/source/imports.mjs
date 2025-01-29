/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as acorn from 'acorn';
import tsPlugin from 'acorn-typescript';
import estraverse from 'estraverse';
import fg from 'fast-glob';
import fs from 'fs/promises';
import path from 'path';

import {SRC_PATH, getRootDir} from '../../util/constants.mjs';
import getYarnWorkspaceProjects from '../../util/getYarnWorkspaceProjects.mjs';

export default async function main() {
	const [projectDirectories, rootDir] = await Promise.all([
		getYarnWorkspaceProjects(),
		getRootDir(),
	]);

	const results = await Promise.all(
		projectDirectories.map(async (projectDir) => {
			const files = await fg(
				`${path.join(projectDir, SRC_PATH)}/**/*.{js,jsx,ts,tsx}`
			);

			const sources = await Promise.all(
				files.map((file) => fs.readFile(file, 'utf-8'))
			);

			const importsSymbols = {};

			sources.forEach((source, i) => {
				let inImports = false;
				let inImport = false;
				const importLines = [];

				const lines = source.split('\n').map((line) => line.trim());

				for (const line of lines) {
					if (!inImports && !line.startsWith('import ')) {
						continue;
					}

					inImports = true;

					if (line.startsWith('import ')) {
						importLines.push(line);

						if (!line.endsWith(';')) {
							inImport = true;
						}
					}
					else if (inImport) {
						importLines.push(line);

						if (line.endsWith(';')) {
							inImport = false;
						}
					}
					else {
						break;
					}
				}

				try {
					const ast = acorn.Parser.extend(tsPlugin()).parse(
						importLines.join('\n'),
						{
							ecmaVersion: 2022,
							locations: true,
							sourceType: 'module',
						}
					);

					estraverse.traverse(ast, {
						enter: (node) => {
							if (node.type === 'ImportDeclaration') {
								const importPath = node.source.value;

								if (importPath.startsWith('.')) {
									return;
								}

								let set = importsSymbols[importPath];

								if (!set) {
									importsSymbols[importPath] = set =
										new Set();
								}

								node.specifiers.forEach((specifier) => {
									switch (specifier.type) {
										case 'ImportDefaultSpecifier':
											set.add('default');
											break;

										case 'ImportNamespaceSpecifier':
											set.add('*');
											break;

										case 'ImportSpecifier':
											set.add(specifier.imported.name);
											break;

										default:
											throw new Error(
												`Unexpected import specifier: ${specifier.type}`
											);
									}
								});
							}
						},

						fallback: 'iteration',
					});
				}
				catch (error) {
					console.error(`Cannot parse file: ${files[i]}`);
					console.error('Source code:');
					console.error(importLines.join('\n'));
					console.error(error);
					process.exit(1);
				}
			});

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

	const csvFile = 'source-imports.csv';
	const lines = ['BUNDLE;IMPORT;SYMBOLS'];

	Object.entries(projectImportsSymbols).forEach(
		([projectDir, importsSymbols]) => {
			const projectRelDir = path.relative(rootDir, projectDir);

			Object.entries(importsSymbols).forEach(([importPath, symbols]) => {
				lines.push(
					`${projectRelDir};${importPath};"${[...symbols].sort().join(',')}"`
				);
			});
		}
	);

	await fs.writeFile(csvFile, lines.join('\n'));

	console.log(`
ℹ️  The report has been created at: ${csvFile}
`);
}
