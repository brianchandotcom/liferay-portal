/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {BUILD_RESOURCES_PATH} from '../../util/constants.mjs';
import projectScopeRequire from '../../util/projectScopeRequire.mjs';
import getNamespacedPackageName from './util/getNamespacedPackageName.mjs';
import splitProjectExport from './util/splitProjectExport.mjs';

export default async function writeManifestJson(
	projectDescription,
	projectEntryPoints,
	projectExports
) {
	if (!projectEntryPoints.main && !projectExports.length) {
		return;
	}

	const filePath = path.join(BUILD_RESOURCES_PATH, 'manifest.json');

	const manifest = {
		packages: {
			'/': {
				dest: {
					dir: '.',
					id: '/',
					name: projectDescription.name,
					version: projectDescription.version,
				},
				modules: {
					'index.js': {
						flags: {
							esModule: true,
							useESM: true,
						},
					},
				},
				src: {
					id: '/',
					name: projectDescription.name,
					version: projectDescription.version,
				},
			},
		},
	};

	const groupedProjectExports = projectExports.reduce(
		(groupedProjectExports, projectExport) => {
			const {name, scope} = splitProjectExport(projectExport);
			const packageName = `${scope ? `${scope}/` : ''}${name}`;

			if (!groupedProjectExports[packageName]) {
				groupedProjectExports[packageName] = [];
			}

			groupedProjectExports[packageName].push(projectExport);

			return groupedProjectExports;
		},
		{}
	);

	for (const [packageName, projectExports] of Object.entries(
		groupedProjectExports
	)) {
		const {version} = projectScopeRequire(`${packageName}/package.json`);
		const namespacedPackageName = getNamespacedPackageName(
			packageName,
			projectDescription.name
		);
		const namespacedPackageId = `${namespacedPackageName}@${version}`;

		manifest.packages[namespacedPackageId] = {
			dest: {
				dir: '.',
				id: namespacedPackageId,
				name: namespacedPackageName,
				version,
			},
			modules: {},
			src: {
				id: `${packageName}@${version}`,
				name: packageName,
				version,
			},
		};

		for (const projectExport of projectExports) {
			const {modulePath} = splitProjectExport(projectExport);

			manifest.packages[namespacedPackageId].modules[
				`${modulePath.substring(1)}.js`
			] = {
				flags: {
					esModule: true,
					useESM: true,
				},
			};
		}
	}

	await fs.mkdir(path.dirname(filePath), {recursive: true});
	await fs.writeFile(filePath, JSON.stringify(manifest, null, '\t'), 'utf-8');
}
