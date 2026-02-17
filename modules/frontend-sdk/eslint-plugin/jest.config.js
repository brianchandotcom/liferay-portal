/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

module.exports = {
	setupFilesAfterEnv: ['<rootDir>/scripts/setupJest.js'],
	testMatch: [
		'<rootDir>/rules/*/tests/lib/rules/*.js',
		'<rootDir>/test/**/*.js',
	],
};
