/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

import {PORTAL_DIR} from '../../locations.mjs';
import print from '../../print.mjs';
import runGitLsFiles from '../../runGitLsFiles.mjs';

const ALLOWED_FILE_PATTERNS = [

	// Only file and directory (ending with /) patterns are allowed

	'modules/.eslintignore',
	'modules/.prettierignore',
	'modules/.prettierrc.js',
	'modules/.stylelintignore',
	'modules/.stylelintrc.js',
	'modules/apps/frontend-js/frontend-js-clay-web/clay/www/',
	'modules/dxp/apps/osb/',
	'modules/sdk/',
	'workspaces/',
];

const BABEL_CONFIG_FILE_NAME = `'.babelrc.js'`;
const ESLINT_CONFIG_FILE_NAME = `'.eslintrc.js'`;

/* eslint-disable sort-keys */

const DISALLOWED_CONFIG_FILE_NAMES = {

	// https://babeljs.io/docs/en/config-files/

	'.babelrc': BABEL_CONFIG_FILE_NAME,
	'.babelrc.cjs': BABEL_CONFIG_FILE_NAME,
	'.babelrc.json': BABEL_CONFIG_FILE_NAME,
	'.babelrc.mjs': BABEL_CONFIG_FILE_NAME,
	'babel.config.cjs': BABEL_CONFIG_FILE_NAME,
	'babel.config.js': BABEL_CONFIG_FILE_NAME,
	'babel.config.json': BABEL_CONFIG_FILE_NAME,
	'babel.config.mjs': BABEL_CONFIG_FILE_NAME,

	// https://eslint.org/docs/user-guide/configuring

	'.eslintignore': `the global '.eslintignore' file`,
	'.eslintrc': ESLINT_CONFIG_FILE_NAME,
	'.eslintrc.cjs': ESLINT_CONFIG_FILE_NAME,
	'.eslintrc.json': ESLINT_CONFIG_FILE_NAME,
	'.eslintrc.yaml': ESLINT_CONFIG_FILE_NAME,
	'.eslintrc.yml': ESLINT_CONFIG_FILE_NAME,

	// https://prettier.io/docs/en/configuration.html

	'.prettierignore': `the global '.prettierignore' file`,
	'.prettierrc.js': null,
	'.prettierrc.json': null,
	'.prettierrc.toml': null,
	'.prettierrc.yaml': null,
	'.prettierrc.yml': null,
	'prettier.config.js': null,

	// https://stylelint.io/user-guide/configuration

	'.stylelintignore': `the global '.stylelintignore' file`,
	'.stylelintrc.js': null,
	'.stylelintrc': null,
	'.stylelintrc.json': null,
	'.stylelintrc.yml': null,
	'.stylelintrc.yaml': null,
	'stylelint.config.js': null,
};

/* eslint-enable sort-keys */

export default async function formatConfigFileNames() {
	let checksPassed = true;

	print(1, print.subTitle('> Checking configuration file names...\n'));

	const disallowedConfigs = await runGitLsFiles(
		[
			...Object.keys(DISALLOWED_CONFIG_FILE_NAMES).map(
				(fileName) => `${fileName}`
			),
			...Object.keys(DISALLOWED_CONFIG_FILE_NAMES).map(
				(fileName) => `**/${fileName}`
			),
		],
		PORTAL_DIR
	);

	disallowedConfigs.forEach((file) => {
		for (const allowedFilePattern of ALLOWED_FILE_PATTERNS) {
			if (allowedFilePattern.endsWith('/')) {
				if (file.startsWith(allowedFilePattern)) {
					return;
				}
			}
			else {
				if (file === allowedFilePattern) {
					return;
				}
			}
		}

		let suggested = DISALLOWED_CONFIG_FILE_NAMES[path.basename(file)];

		if (suggested) {
			suggested = `use ${suggested} instead`;
		}
		else {
			suggested = `'${path.basename(file)}' is embedded in 'node-scripts' and cannot be overriden`;
		}

		print(
			2,
			print.error('ERROR:'),
			'Invalid configuration file',
			print.underline(file),
			`found (${suggested})\n`
		);

		checksPassed = false;
	});

	return checksPassed;
}
