/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-env node */

'use strict';

const esbuild = require('esbuild');
const fs = require('fs');
const path = require('path');

function getFlagValue(flagName) {
	const flag = process.argv.find((value) => value.startsWith(`${flagName}=`));

	if (!flag) {
		return undefined;
	}

	return flag.slice(flagName.length + 1);
}

function getSourceFiles(directoryPath) {
	const entries = fs.readdirSync(directoryPath, {withFileTypes: true});
	const sourceFiles = [];

	entries.forEach((entry) => {
		const entryPath = path.join(directoryPath, entry.name);

		if (entry.isDirectory()) {
			if (entry.name !== '__tests__') {
				sourceFiles.push(...getSourceFiles(entryPath));
			}

			return;
		}

		if (entry.name.endsWith('.ts') || entry.name.endsWith('.tsx')) {
			sourceFiles.push(entryPath);
		}
	});

	return sourceFiles;
}

async function build() {
	const format = getFlagValue('--format');
	const outdir = getFlagValue('--outdir');

	if (!format || !outdir) {
		throw new Error('Expected --format and --outdir flags.');
	}

	const sourceDirectory = path.resolve(process.cwd(), 'src');
	const entryPoints = getSourceFiles(sourceDirectory);

	if (!entryPoints.length) {
		throw new Error(`No source files found in ${sourceDirectory}`);
	}

	await esbuild.build({
		entryPoints,
		format,
		outbase: 'src',
		outdir,
		target: 'es2022',
	});

	// eslint-disable-next-line no-console
	console.log(`Built ${entryPoints.length} files to ${outdir} (${format})`);
}

build().catch((error) => {

	// eslint-disable-next-line no-console
	console.error(error);
	process.exit(1);
});
