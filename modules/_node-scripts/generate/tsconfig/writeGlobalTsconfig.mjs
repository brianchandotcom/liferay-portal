/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fs from 'fs/promises';
import path from 'path';

import {SRC_PATH, SRC_TSCONFIG_PATH, getRootDir} from '../../util/constants.mjs';
import sortObjectKeys from '../../util/sortObjectKeys.mjs';
import fileExists from '../../util/fileExists.mjs';
import baseTsconfig from './baseTsconfig.mjs';

export default async function writeGlobalTsconfig(mainEntryPoints) {
	const rootDir = await getRootDir();

	const paths = {};
	const references = [];
	const projectIncludeGlobs = [];

	for (const [project, mainEntryPoint] of Object.entries(mainEntryPoints)) {
		if (!await fileExists(path.join(mainEntryPoint.base, SRC_TSCONFIG_PATH))) {
			continue;
		}

		const mainEntryPointPath = path.join(
			...`${mainEntryPoint.base}/${mainEntryPoint.main}`.split('/')
		);

		paths[project] = [mainEntryPointPath];

		references.push({path: `${mainEntryPoint.base}/${SRC_TSCONFIG_PATH}`});

		projectIncludeGlobs.push(
			`${mainEntryPoint.base}/${SRC_PATH}/**/*.ts`,
			`${mainEntryPoint.base}/${SRC_PATH}/**/*.tsx`,
		);
	}

	const json = {
		...baseTsconfig,
		compilerOptions: {
			...baseTsconfig.compilerOptions,
			declarationDir: '.tsc/types',
			paths, 			
			rootDir: '.',
			tsBuildInfoFile: '.tsc/buildinfo/modules.tsbuildinfo',
			typeRoots: [
				'./node_modules/@types'
			]
		},
		include: [
			...projectIncludeGlobs,
			'./global.d.ts',
		],
		references
	};

	sortObjectKeys(json);

	await fs.writeFile(
		path.join(rootDir, 'tsconfig.json'), 
		JSON.stringify(json, null, '\t'),
		'utf-8'
	);
}
