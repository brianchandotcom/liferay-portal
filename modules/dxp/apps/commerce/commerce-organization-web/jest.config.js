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

module.exports = {
	verbose: true,
	setupFilesAfterEnv: ['./setupTests.js'],
	moduleFileExtensions: ['js', 'jsx', 'html'],
	transform: {
		'^.+\\.(js|jsx)?$': 'babel-jest',
		'^.+\\.html?$': 'html-loader-jest',
	},
	testMatch: ['<rootDir>/**/*.spec.js', '<rootDir>/**/*.spec.jsx'],
	modulePathIgnorePatterns: [
		'<rootDir>[/\\\\](build|docs|node_modules|deploy|scripts)[/\\\\]',
	],
	collectCoverageFrom: [
		'**/*.{js,jsx}',
		'!**/node_modules/**',
		'!**/vendor/**',
	],
};
