/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

function getJestConfig({rootDir = '<rootDir>'}) {
	return {
		coverageDirectory: 'build/coverage',
		globalSetup: path.join(__dirname, 'globalSetup.js'),
		modulePathIgnorePatterns: ['/__fixtures__/', '/build/', '/classes/'],
		prettierPath: null,
		resolver: path.join(__dirname, 'resolver.js'),
		setupFiles: [path.join(__dirname, 'setup.js')],
		setupFilesAfterEnv: [path.join(__dirname, 'setupAfterEnv.js')],
		testEnvironment: 'jest-environment-jsdom-thirteen',
		testEnvironmentOptions: {
			url: 'http://localhost',
		},
		testMatch: [`${rootDir}/test/**/*.{js,ts,tsx}`],
		testPathIgnorePatterns: ['/node_modules/', `${rootDir}/test/stories/`],
		testResultsProcessor: '@liferay/jest-junit-reporter',
		transform: {

			/* eslint-disable sort-keys */
			'\\.scss$': path.join(__dirname, 'transformSass.js'),
			'.+': path.join(__dirname, 'transformBabel.js'),

			/* eslint-enable sort-keys */
		},

		transformIgnorePatterns: ['/node_modules/'],
	};
}

module.exports = getJestConfig;
