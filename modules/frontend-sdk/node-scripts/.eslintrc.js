/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

const config = require(path.join(__dirname, '..', '..', '.eslintrc.js'));

config.ignorePatterns = [
	...config.ignorePatterns,

	// Ignore files with top level await (not supported until eslint v8)

	'util/sass/util/runSass.mjs',

	// Ignore Jest plugin files

	'util/jest/getJestConfig.js',
	'util/jest/globalSetup.js',
	'util/jest/resolver.js',
	'util/jest/setup.js',
	'util/jest/setupAfterEnv.js',
	'util/jest/transformSass.js',
	'util/jest/mocks/Headers.js',
	'util/jest/mocks/Liferay.js',
];

config.rules = {
	...config.rules,
	'@liferay/no-dynamic-require': 'off',
	'@liferay/no-extraneous-dependencies': 'off',
	'@liferay/portal/no-global-fetch': 'off',
	'no-console': 'off',
};

module.exports = config;
