/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

function getJestConfig({rootDir = '<rootDir>'}) {
	let moduleNameMapper = {};

	if (process.env.USE_REACT_16 === 'true') {
		moduleNameMapper = {

			// Testing dependencies

			'^@testing-library/dom((\\/.*)?)$': '@testing-library/dom-8.11.1$1',
			'^@testing-library/jest-dom((\\/.*)?)$':
				'@testing-library/jest-dom-4.2.4$1',
			'^@testing-library/react((\\/.*)?)$':
				'@testing-library/react-12.1.2$1',
			'^@testing-library/react-hooks((\\/.*)?)$':
				'@testing-library/react-hooks-3.4.2$1',
			'^@testing-library/user-event((\\/.*)?)$':
				'@testing-library/user-event-4.2.4$1',

			// React Dependencies

			'^react$': 'react-16',
			'^react-dom$': 'react-dom-16',
			'^react-dom/client$': 'react-dom/client',
			'^react-dom/server$': 'react-dom-16/server',
			'^react-dom/test-utils$': 'react-dom-16/test-utils',
		};
	}

	return {
		coverageDirectory: 'build/coverage',
		globalSetup: path.join(__dirname, 'globalSetup.js'),
		moduleNameMapper,
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
