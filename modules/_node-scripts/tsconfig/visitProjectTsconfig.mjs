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

		// await fs.writeFile(configPath, objectSF(json), 'utf-8');

		await visitorFunction(configPath, objectSF(json));
	}

	await visitProjectTestsTsconfig(visitorFunction, projectDir);
}

async function visitProjectTestsTsconfig(visitorFunction, projectDir) {
	const tsTests = await fg('test/**/*.{ts,tsx}', {cwd: projectDir});

	if (!tsTests.length) {
		return false;
	}

	const tsConfig = {
		'@readonly': '** AUTO-GENERATED: DO NOT EDIT **',
		'compilerOptions': {
			allowSyntheticDefaultImports: true,
			baseUrl: '.',
			checkJs: false,
			composite: true,
			jsx: 'react',
			module: 'ESNext',
			moduleResolution: 'node',
			rootDir: '../',
			strict: true,
			target: 'es2020',
			typeRoots: ['../../../../node_modules/@types'],
		},
		'include': ['**/*.ts', '**/*.tsx', '../src/**/*.ts', '../src/**/*.tsx'],
	};

	tsConfig[GENERATED] = hash(tsConfig);

	let contents = '';

	const testConfigPath = path.join(projectDir, 'test', 'tsconfig.json');

	if (await fileExists(testConfigPath)) {
		contents = await fs.readFile(testConfigPath, 'utf8');
	}

	const previousConfig = JSON.parse(contents.trim() ? contents : '{}');

	if (
		hash(previousConfig) !== previousConfig[GENERATED] ||
		tsConfig[GENERATED] !== previousConfig[GENERATED]
	) {

		// await fs.writeFile(testConfigPath, objectSF(tsConfig), 'utf-8');

		await visitorFunction(testConfigPath, objectSF(tsConfig));

		return true;
	}
	else {
		return false;
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
