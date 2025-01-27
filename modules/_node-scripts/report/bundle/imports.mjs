/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {parse} from 'acorn';
import estraverse from 'estraverse';
import fs from 'fs/promises';
import path from 'path';

import {BUILD_RESOURCES_PATH, getRootDir} from '../../util/constants.mjs';
import getNamedArguments from '../../util/getNamedArguments.mjs';
import getYarnWorkspaceProjects from '../../util/getYarnWorkspaceProjects.mjs';
import launchSoffice from '../../util/launchSoffice.mjs';
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

	const bundleImports = getBundleImports(bundleSizes);

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
							`${bundlePath};${importPath};"${[...symbols].join(',')}"`
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
					lines.push(`${bundlePath};${importPath}`);
				});
			});
	}

	await fs.writeFile(csvFile, lines.join('\n'));

	await launchSoffice(csvFile);
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
				if (node.type === 'ImportDeclaration') {
					const importPath = node.source.value;

					let set = bundleImportsSymbols[bundle][importPath];

					if (!set) {
						bundleImportsSymbols[bundle][importPath] = set =
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
	});

	return bundleImportsSymbols;
}

function getBundleImports(bundleSizes) {
	const bundleImports = {};

	bundleSizes.forEach((stat) => {
		const {projectDir, sizes} = stat;

		Object.entries(sizes).forEach(([bundle, {inputs}]) => {
			const bundlePath = path.join(projectDir, bundle);

			Object.entries(inputs).forEach(([objectPath]) => {
				if (!objectPath.includes('/$/')) {
					return;
				}

				if (!bundleImports[bundlePath]) {
					bundleImports[bundlePath] = [];
				}

				objectPath = objectPath.replace(
					/.*\/\$\/bridge\/for\/main\//,
					''
				);
				objectPath = objectPath.replace(/.*\/\$\/css\//, '');

				bundleImports[bundlePath].push(objectPath);
			});
		});
	});

	return bundleImports;
}
