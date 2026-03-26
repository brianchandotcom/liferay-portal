/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';
import stylelint from 'stylelint';

import {MODULES_DIR, PORTAL_DIR} from '../../locations.mjs';
import printEslintErrors from '../util/printEslintErrors.mjs';

const {default: STYLELINT_CONFIG} = await import(
	'file://' + path.join(MODULES_DIR, '.stylelintrc.js')
);

export default async function formatWithStylelint(input, filePath) {
	const extName = path.extname(filePath);

	// NOTE: Overriding the base configuration is not supported because we've
	// never needed. If we wanted to support it in the future we would need to
	// manually load cascaded configuration files from here based on file path.

	const {output, results} = await stylelint.lint({
		code: input,
		codeFilename: filePath,
		config: STYLELINT_CONFIG,
		fix: true,
		syntax: extName.replace('.', ''),
	});

	if (results?.length) {
		const errors = [];

		results.forEach((result) => {
			if (result.warnings.length) {
				errors.push(
					...result.warnings.map(({column, line, rule, text}) => ({
						column,
						filepath: filePath,
						line,
						message: text,
						ruleId: rule,
						severity: 2,
					}))
				);
			}
		});

		printEslintErrors(path.relative(PORTAL_DIR, filePath), errors);

		return {
			errorsPresent: !!errors.length,
			output: output.endsWith('\n') ? output : `${output}\n`,
		};
	}

	return {
		errorsPresent: false,
		output: input,
	};
}
