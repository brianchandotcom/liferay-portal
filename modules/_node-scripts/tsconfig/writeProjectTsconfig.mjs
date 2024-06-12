/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import crypto from 'crypto';
import fs from 'fs/promises';
import path from 'path';

import {SRC_PATH, SRC_TSCONFIG_PATH, getRootDir} from '../util/constants.mjs';
import objectSF from '../util/objectSF.mjs';
import baseTsconfig from './baseTsconfig.mjs';

const GENERATED = '@generated';

export default async function writeProjectTsconfig(
	projectsEntryPoints,
	projectDependencies,
	projectDescription,
	projectDir = '.'
) {
	const rootDir = await getRootDir();
	const srcPath = path.join(projectDir, SRC_PATH);

	const globalDTsFileProjectRelativePath = path.posix.relative(
		srcPath,
		path.join(rootDir, 'global.d.ts')
	);

	const rootDirProjectRelativePath = path.posix.relative(
		srcPath,
		path.join(rootDir)
	);

	const tsBuildInfoFile = path.posix.relative(
		srcPath,
		path.join(
			rootDir,
			'.tsc',
			'buildinfo',
			`${projectDescription.name}.tsbuildinfo`
		)
	);

	const tscTypesDirProjectRelativePath = path.posix.relative(
		srcPath,
		path.join(rootDir, '.tsc', 'types')
	);

	const typesDirProjectRelativePath = path.posix.relative(
		srcPath,
		path.join(rootDir, 'node_modules', '@types')
	);

	const paths = {};
	const references = [];

	for (const dependency of Object.keys(projectDependencies)) {
		const projectEntryPoint = projectsEntryPoints[dependency];

		if (!projectEntryPoint) {
			continue;
		}

		const projectEntryPointPath = path.join(
			rootDir,
			...`${projectEntryPoint.dir}/${projectEntryPoint.path}`.split('/')
		);

		paths[dependency] = [
			path.posix.relative(srcPath, projectEntryPointPath),
		];

		const projectPath = path.posix.relative(
			srcPath,
			path.join(rootDir, projectEntryPoint.dir)
		);

		references.push({path: `${projectPath}/${SRC_TSCONFIG_PATH}`});
	}

	const json = {
		...baseTsconfig,
		compilerOptions: {
			...baseTsconfig.compilerOptions,
			declarationDir: tscTypesDirProjectRelativePath,
			paths,
			rootDir: rootDirProjectRelativePath,
			tsBuildInfoFile,
			typeRoots: [typesDirProjectRelativePath],
		},
		include: ['**/*.ts', '**/*.tsx', globalDTsFileProjectRelativePath],
		references,
	};

	json[GENERATED] = hash(json);

	const contents = await fs.readFile(
		path.join(srcPath, 'tsconfig.json'),
		'utf8'
	);

	const previousConfig = JSON.parse(contents.trim() ? contents : '{}');

	if (json[GENERATED] !== previousConfig[GENERATED]) {
		const configPath = path.join(srcPath, 'tsconfig.json');

		await fs.writeFile(configPath, objectSF(json), 'utf-8');

		console.log(`Generated new tsconfig.json at ${configPath}`);
	}
}

function hash(config) {
	const shasum = crypto.createHash('sha1');

	shasum.update(
		JSON.stringify({
			...config,
			[GENERATED]: null,
		})
	);

	return shasum.digest('hex');
}
