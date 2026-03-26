/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import {MODULES_DIR, PORTAL_DIR} from '../../locations.mjs';
import print from '../../print.mjs';
import runGitLsFiles from '../../runGitLsFiles.mjs';

export default async function formatIgnoreFilePatterns() {
	const checksPassed = true;

	print(
		1,
		print.subTitle('> Checking ignored file patterns configuration...\n')
	);

	const eslintrcFiles = await runGitLsFiles(
		['.eslintrc.js', '**/.eslintrc.js'],
		MODULES_DIR
	);

	for (const file of eslintrcFiles) {
		const {default: config} = await import(path.resolve(PORTAL_DIR, file));

		if (config.ignores || config.ignorePatterns) {
			print(
				2,
				print.error('ERROR:'),
				'File',
				print.underline(file),
				`contains an 'ignores' entry (use '.eslintignore' file instead)\n`
			);
		}
	}

	const stylelintrcFiles = await runGitLsFiles(
		['.stylelintrc.js', '**/.stylelintrc.js'],
		MODULES_DIR
	);

	for (const file of stylelintrcFiles) {
		const {default: config} = await import(path.resolve(PORTAL_DIR, file));

		if (config.ignoreFiles) {
			print(
				2,
				print.error('ERROR:'),
				'File',
				print.underline(file),
				`contains an 'ignoreFiles' entry (use '.stylelintignore' file instead)\n`
			);
		}
	}

	// NOTE: Prettier config files are not allowed, so we don't check them

	return checksPassed;
}
