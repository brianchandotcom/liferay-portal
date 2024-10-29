/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fg from 'fast-glob';
import path from 'path';

import {getRootDir} from '../util/constants.mjs';
import getNamedArguments from '../util/getNamedArguments.mjs';
import gitUtil from '../util/gitUtil.mjs';
import format from './format.mjs';

export default async function main() {
	const {all, check, currentBranch, emitSuppressed, localChanges} =
		getNamedArguments({
			all: '--all',
			check: '--check',
			currentBranch: '--current-branch',
			emitSuppressed: '--emit-suppressed',
			localChanges: '--local-changes',
		});

	const cwd = path.resolve('.');
	const rootDir = await getRootDir();
	const portalDir = path.resolve(rootDir, '..');

	let files;

	if (cwd === rootDir) {
		if (currentBranch) {
			files = await gitUtil('current-branch');
		}
		else if (localChanges) {
			files = await gitUtil('local-changes');
		}
		else {
			if (!all) {
				console.log(`
⚠️ Formatting all files takes long, you may want to use --local-changes or --current-branch arguments
`);
			}

			files = undefined;
		}
	}
	else {
		if (currentBranch || localChanges) {
			console.error(`
❌ Arguments --current-branch or --local-changes are not valid when formatting a single project.
`);

			process.exit(2);
		}

		files = await fg(['**/*'], {
			cwd,
			dot: true,
			ignore: ['node_modules/**'],
		});

		files = files
			.map((file) => path.resolve(cwd, file))
			.map((file) => path.relative(portalDir, file));
	}

	console.log('📝 Running format...\n');

	const formatOutput = await format(!check, files, {
		emitSuppressed,
	});

	if (check && formatOutput) {
		console.error(formatOutput);
		process.exit(1);
	}
	else if (formatOutput) {
		console.log(formatOutput);
	}
	else {
		console.log('ℹ️ Nothing needed to be formatted (no changes detected).');
	}
}
