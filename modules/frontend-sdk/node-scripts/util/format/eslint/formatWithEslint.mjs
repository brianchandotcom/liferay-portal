/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ESLint} from 'eslint';
import fs from 'fs/promises';
import path from 'path';

import {MODULES_DIR, PORTAL_DIR} from '../../locations.mjs';
import printEslintErrors from '../util/printEslintErrors.mjs';

const SUPPRESSED_ERRORS = await fs.readFile(
	path.join(import.meta.dirname, 'suppressed_errors.txt'),
	'utf-8'
);

// Eslint reads configuration files based on the filePath so we don't need to
// pass it here.

const esLint = new ESLint({
	fix: true,
	resolvePluginsRelativeTo: MODULES_DIR,
});

export default async function formatWithEslint(
	input,
	filePath,
	emitSuppressed
) {
	const [lintResult = {}] = await esLint.lintText(input, {
		filePath,
	});

	const {messages = [], output} = lintResult;

	const portalRelativePath = path.relative(PORTAL_DIR, filePath);

	const filteredErrors = emitSuppressed
		? messages
		: messages.filter(
				(item) =>
					!SUPPRESSED_ERRORS.includes(
						`${portalRelativePath}:${item.message}\n`
					)
			);

	if (filteredErrors?.length) {
		if (emitSuppressed) {
			const set = new Set();

			filteredErrors.forEach((error) => {
				set.add(`${portalRelativePath}:${error.message}`);
			});

			set.forEach((item) => {
				process.stdout.write(`${item}\n`);
			});
		}
		else {
			printEslintErrors(portalRelativePath, filteredErrors);
		}
	}

	return {
		errorsPresent: !filteredErrors?.length,
		output: output ?? input,
	};
}
