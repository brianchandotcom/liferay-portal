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
import getYarnWorkspaceProjects from '../../util/getYarnWorkspaceProjects.mjs';
import getBundleSizes from './getBundleSizes.mjs';

export default async function main() {
	const [projectDirectories, rootDir] = await Promise.all([
		getYarnWorkspaceProjects(),
		getRootDir(),
	]);

	const bundleSizes = await getBundleSizes(projectDirectories);

	const bundleImports = await getBundleImports(bundleSizes);

	const csvFile = 'bundle-imports.csv';
	const lines = ['BUNDLE;IMPORT'];

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

	await fs.writeFile(csvFile, lines.join('\n'));

	console.log(`
ℹ️  The report has been created at: ${csvFile}
`);
}

async function getBundleImports(bundleSizes) {
	const bundleImports = {};

	for (const stat of bundleSizes) {
		const {projectDir, sizes} = stat;

		for (const [bundle] of Object.entries(sizes)) {
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

						default:
							break;
					}
				},

				fallback: 'iteration',
			});

			bundleImports[bundlePath] = [...set];
		}
	}

	return bundleImports;
}
