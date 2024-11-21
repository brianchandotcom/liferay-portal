/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

const CONFIG_FILES = [
	'**/.babelrc.js',
	'**/.eslintrc.js',
	'**/.prettierrc.js',
	'**/.stylelintrc.js',
	'**/gulpfile.js',
	'**/liferay-npm-bundler.config.js',
	'**/npmscripts.config.js',
	'**/webpack.config.dev.js',
	'**/webpack.config.js',
	'**/node-scripts.config.js',
];

const config = {
	env: {
		browser: true,
		es2021: true,
	},
	extends: ['plugin:@liferay/portal'],
	globals: {
		AUI: true,
		CKEDITOR: true,
		Liferay: true,
		MODULE_PATH: true,
		process: true,
		submitForm: true,
		svg4everybody: true,
		themeDisplay: true,
	},
	ignorePatterns: ['!*'],
	overrides: [
		{
			env: {
				node: true,
			},
			files: CONFIG_FILES,
		},
		{
			env: {
				jest: true,
				node: true,
			},
			files: ['**/test/**/*.{js,ts,tsx}'],
		},
	],
	parserOptions: {
		ecmaFeatures: {
			jsx: true,
		},
		ecmaVersion: 2023,
	},
	plugins: ['@liferay', 'eslint-plugin-react-compiler'],
	root: true,
	rules: {
		'@liferay/import-extensions': 'off',
		'@liferay/no-extraneous-dependencies': [
			'error',
			[
				'@liferay/npm-scripts',
				'@testing-library/dom',
				'@testing-library/jest-dom',
				'@testing-library/react-hooks',
				'@testing-library/react',
				'@testing-library/user-event',
				'alloy-ui',
				'buffer',
				'fs',
				'path',
				'process',
				'webpack',
				'~',
			],
		],
		'@liferay/no-get-data-attribute': 'off',
		'@liferay/portal/no-document-cookie': 'off',
		'@liferay/portal/no-global-storage': 'off',
		'no-empty': ['error', {allowEmptyCatch: true}],
		'notice/notice': [
			'error',
			{
				nonMatchingTolerance: 0.95,
				onNonMatchingHeader: 'replace',
				templateFile: path.join(__dirname, 'copyright.js'),
			},
		],
		'promise/catch-or-return': 'off',
		'react-compiler/react-compiler': 'error',
	},
};

module.exports = config;
