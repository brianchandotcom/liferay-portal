/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import crypto from 'crypto';
import fg from 'fast-glob';
import fs from 'fs/promises';
import path from 'path';

import {SRC_PATH, SRC_TSCONFIG_PATH, getRootDir} from '../util/constants.mjs';
import fileExists from '../util/fileExists.mjs';
import objectSF from '../util/objectSF.mjs';
import baseTsconfig from './baseTsconfig.mjs';

const GENERATED = '@generated';

export default async function visitProjectTsconfig(
	visitorFunction,
	projectsEntryPoints,
	projectDependencies,
	projectDescription,
	projectDir = '.',
	testConfig = false
) {
	const rootDir = await getRootDir();

	const srcPath = testConfig
		? path.join(projectDir, 'test')
		: path.join(projectDir, SRC_PATH);

	if (!(await fileExists(srcPath))) {
		return;
	}

	const tsTests = await fg('**/*.{ts,tsx}', {cwd: srcPath});

	if (!tsTests.length) {
		return false;
	}

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
			`${projectDescription.name}${testConfig ? '-test' : ''}.tsbuildinfo`
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

	const currentProject = projectsEntryPoints[projectDescription.name];

	if (currentProject.path.submodules) {
		Object.entries(currentProject.path.submodules).forEach(
			([submoduleName, subModulePath]) => {
				paths[`${projectDescription.name}/${submoduleName}`] = [
					'./' +
						path.posix.relative(
							srcPath,
							path.join(projectDir, subModulePath)
						),
				];
			}
		);
	}

	for (const dependency of Object.keys(projectDependencies)) {
		const projectEntryPoint = projectsEntryPoints[dependency];

		if (!projectEntryPoint) {
			continue;
		}

		const projectMainEntryPointPath = path.join(
			rootDir,
			...`${projectEntryPoint.dir}/${projectEntryPoint.path.main}`.split(
				'/'
			)
		);

		const projectSubmodulesEntryPointsPaths = Object.entries(
			projectEntryPoint.path.submodules ?? {}
		).reduce((map, [entryPointName, entryPointPath]) => {
			map[entryPointName] = path.join(
				rootDir,
				...`${projectEntryPoint.dir}/${entryPointPath}`.split('/')
			);

			return map;
		}, {});

		paths[dependency] = [
			path.posix.relative(srcPath, projectMainEntryPointPath),
		];

		Object.entries(projectSubmodulesEntryPointsPaths).forEach(
			([entryPointName, entryPointPath]) => {
				paths[`${dependency}/${entryPointName}`] = [
					path.posix.relative(srcPath, entryPointPath),
				];
			}
		);

		const projectPath = path.posix.relative(
			srcPath,
			path.join(rootDir, projectEntryPoint.dir)
		);

		references.push({path: `${projectPath}/${SRC_TSCONFIG_PATH}`});
	}

	const include = ['**/*.ts', '**/*.tsx', globalDTsFileProjectRelativePath];

	if (testConfig) {
		include.push('../src/**/*.ts', '../src/**/*.tsx');
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
		include,
		references,
	};

	json[GENERATED] = hash(json);

	let contents = '';

	const configPath = path.join(srcPath, 'tsconfig.json');

	if (await fileExists(configPath)) {
		contents = await fs.readFile(configPath, 'utf8');
	}

	const previousConfig = JSON.parse(contents.trim() ? contents : '{}');

	if (
		hash(previousConfig) !== previousConfig[GENERATED] ||
		json[GENERATED] !== previousConfig[GENERATED]
	) {
		await visitorFunction(configPath, objectSF(json));
	}
}

function hash(config) {
	const shasum = crypto.createHash('sha1');

	const configCopy = {
		...config,
	};

	delete configCopy[GENERATED];

	shasum.update(objectSF(configCopy));

	return shasum.digest('hex');
}
