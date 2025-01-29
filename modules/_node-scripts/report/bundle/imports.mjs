/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {parse} from 'acorn';
import escodegen from 'escodegen';
import estraverse from 'estraverse';
import fs from 'fs/promises';
import path from 'path';

import {BUILD_RESOURCES_PATH, getRootDir} from '../../util/constants.mjs';
import getNamedArguments from '../../util/getNamedArguments.mjs';
import getYarnWorkspaceProjects from '../../util/getYarnWorkspaceProjects.mjs';
import getBundleSizes from './getBundleSizes.mjs';

export default async function main() {
	const {withSymbols} = getNamedArguments({
		withSymbols: '--with-symbols',
	});

	const [projectDirectories, rootDir] = await Promise.all([
		getYarnWorkspaceProjects(),
		getRootDir(),
	]);

	const bundleSizes = await getBundleSizes(projectDirectories);

	const bundleImports = await getBundleImports(bundleSizes);

	let csvFile;
	let lines;

	if (withSymbols) {
		csvFile = 'bundle-imports-with-symbols.csv';
		lines = ['BUNDLE;IMPORT;SYMBOLS'];

		const bundleImportsSymbols =
			await getBundleImportsSymbols(bundleImports);

		Object.entries(bundleImportsSymbols).forEach(
			([bundle, importsSymbols]) => {
				const bundlePath = path
					.relative(rootDir, bundle)
					.replace(`${BUILD_RESOURCES_PATH}${path.sep}`, '');

				Object.entries(importsSymbols).forEach(
					([importPath, symbols]) => {
						lines.push(
							`"${bundlePath}";"${importPath}";"${[...symbols].join(',')}"`
						);
					}
				);
			}
		);
	}
	else {
		csvFile = 'bundle-imports.csv';
		lines = ['BUNDLE;IMPORT'];

		Object.entries(bundleImports)
			.sort(([a], [b]) => a.localeCompare(b))
			.forEach(([bundle, imports]) => {
				const bundlePath = path
					.relative(rootDir, bundle)
					.replace(`${BUILD_RESOURCES_PATH}${path.sep}`, '');

				imports.sort().forEach((importPath) => {
					lines.push(`"${bundlePath}";"${importPath}"`);
				});
			});
	}

	await fs.writeFile(csvFile, lines.join('\n'));

	console.log(`
ℹ️  The report has been created at: ${csvFile}
`);
}

async function getBundleImportsSymbols(bundleImports) {
	const bundleImportsSymbols = {};

	const bundles = Object.keys(bundleImports);

	const sources = await Promise.all(
		bundles.map((bundle) => fs.readFile(bundle, 'utf-8'))
	);

	bundles.forEach((bundle, i) => {
		const ast = parse(sources[i], {
			ecmaVersion: 2022,
			sourceType: 'module',
		});

		bundleImportsSymbols[bundle] = {};

		estraverse.traverse(ast, {
			enter: (node) => {
				let importPath;
					const symbols = new Set();

				switch (node.type) {
					case 'ImportDeclaration': {
						importPath = node.source.value;

						node.specifiers.forEach((specifier) => {
							switch (specifier.type) {
								case 'ImportDefaultSpecifier':
									symbols.add('default');
									break;

								case 'ImportNamespaceSpecifier':
									symbols.add('*');
									break;

								case 'ImportSpecifier':
									symbols.add(specifier.imported.name);
									break;

								default:
									throw new Error(
										`Unexpected import specifier: ${specifier.type}`
									);
							}
						});
						break;
					}

					case 'ImportExpression': {
						importPath = `import(${escodegen.generate(node.source)})`;
						break;
					}
				}

				if (!importPath) {
					return;
				}

				if (!bundleImportsSymbols[bundle][importPath]) {
					bundleImportsSymbols[bundle][importPath] = new Set();
				}

				symbols.forEach((symbol) =>
					bundleImportsSymbols[bundle][importPath].add(symbol)
				);
			},

			fallback: 'iteration',
		});
	});

	return bundleImportsSymbols;
}

async function getBundleImports(bundleSizes) {
	const bundleImports = {};

	for (const stat of bundleSizes) {
		const {projectDir, sizes} = stat;

		for (const [bundle, {inputs}] of Object.entries(sizes)) {
			const bundlePath = path.join(projectDir, bundle);

			if (bundlePath.endsWith('.css')) {
				continue;
			}

			const ast = parse(await fs.readFile(bundlePath, 'utf-8'), {
				ecmaVersion: 2022,
				sourceType: 'module',
			});

			const set = new Set();

			estraverse.traverse(ast, {
				enter: (node) => {
					switch (node.type) {
						case 'ImportDeclaration': {
							set.add(node.source.value);
							break;
						}

						case 'ImportExpression': {
							set.add(
								`import(${escodegen.generate(node.source)})`
							);
							break;
						}
					}
				},

				fallback: 'iteration',
			});

			bundleImports[bundlePath] = [...set];
		}
	}

	return bundleImports;
}
