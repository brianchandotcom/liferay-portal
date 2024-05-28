/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

const config = require(path.join(__dirname, '..', '.eslintrc.js'));

config.rules = {
	...config.rules,
	'@liferay/no-dynamic-require': 'off',
	'@liferay/no-extraneous-dependencies': [
		'error',
		['child_process', 'crypto', 'fs', 'module', 'path', 'os', 'url'],
	],
	'no-console': 'off',
};

module.exports = config;
