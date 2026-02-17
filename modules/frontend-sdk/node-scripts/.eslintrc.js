/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

const config = require(path.join(__dirname, '..', '.eslintrc.js'));

config.ignorePatterns = [
	...config.ignorePatterns,

	// Ignore files with top level await (not supported until eslint v8)

	'bundle/sass/runSass.mjs',
];

config.rules = {
	...config.rules,
	'@liferay/no-dynamic-require': 'off',
	'@liferay/no-extraneous-dependencies': 'off',
	'@liferay/portal/no-global-fetch': 'off',
	'no-console': 'off',
};

module.exports = config;
