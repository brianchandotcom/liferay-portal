/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fg from 'fast-glob';
import path from 'path';

const BABEL_CONFIG_FILE_NAME = '.babelrc.js';
const ESLINT_CONFIG_FILE_NAME = '.eslintrc.js';
const PRETTIER_CONFIG_FILE_NAME = '.prettierrc.js';
const STYLELINT_CONFIG_FILE_NAME = '.stylelintrc.js';

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

	'.eslintrc': ESLINT_CONFIG_FILE_NAME,
	'.eslintrc.cjs': ESLINT_CONFIG_FILE_NAME,
	'.eslintrc.json': ESLINT_CONFIG_FILE_NAME,
	'.eslintrc.yaml': ESLINT_CONFIG_FILE_NAME,
	'.eslintrc.yml': ESLINT_CONFIG_FILE_NAME,

	// https://prettier.io/docs/en/configuration.html

	'.prettierrc': PRETTIER_CONFIG_FILE_NAME,
	'.prettierrc.json': PRETTIER_CONFIG_FILE_NAME,
	'.prettierrc.toml': PRETTIER_CONFIG_FILE_NAME,
	'.prettierrc.yaml': PRETTIER_CONFIG_FILE_NAME,
	'.prettierrc.yml': PRETTIER_CONFIG_FILE_NAME,
	'prettier.config.js': PRETTIER_CONFIG_FILE_NAME,

	// https://stylelint.io/user-guide/configuration

	'.stylelintrc': STYLELINT_CONFIG_FILE_NAME,
	'.stylelintrc.json': STYLELINT_CONFIG_FILE_NAME,
	'.stylelintrc.yml': STYLELINT_CONFIG_FILE_NAME,
	'.stylelintrc.yaml': STYLELINT_CONFIG_FILE_NAME,
	'stylelint.config.js': STYLELINT_CONFIG_FILE_NAME,
};

/* eslint-enable sort-keys */

/**
 * Checks that config files use standard names.
 *
 * Returns a (possibly empty) array of error messages.
 */
export async function checkConfigFileNames() {
	const disallowedConfigs = await fg(
		Object.keys(DISALLOWED_CONFIG_FILE_NAMES)
	);

	return disallowedConfigs.map((file) => {
		const suggested = DISALLOWED_CONFIG_FILE_NAMES[path.basename(file)];

		return `${file}: BAD - use ${suggested} instead`;
	});
}
