/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

// eslint-disable-next-line no-undef
module.exports = {
	collectCoverageFrom: [
		'**/*.{js,jsx}',
		'!**/node_modules/**',
		'!**/vendor/**',
	],
	moduleFileExtensions: ['js', 'jsx', 'html'],
	modulePathIgnorePatterns: [
		'<rootDir>[/\\\\](build|docs|node_modules|deploy|scripts)[/\\\\]',
	],
	setupFilesAfterEnv: ['./setupTests.js'],
	testMatch: ['<rootDir>/**/*.spec.js', '<rootDir>/**/*.spec.jsx'],
	transform: {
		'^.+\\.(js|jsx)?$': 'babel-jest',
		'^.+\\.html?$': 'html-loader-jest',
	},
	verbose: true,
};
