/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	env: {
		browser: true,
		es6: true,
		jest: true,
		node: true
	},
	extends: [
		'liferay',
		'plugin:jsx-a11y/recommended',
		'plugin:prettier/recommended'
	],
	globals: {
		analytics: true,
		FARO_DEV_MODE: true,
		FARO_ENV: true,
		IncrementalDOM: true,
		Liferay: true,
		pendo: true
	},
	parser: '@typescript-eslint/parser',
	parserOptions: {
		ecmaFeatures: {
			allowImportExportEverywhere: true,
			experimentalObjectRestSpread: true,
			jsx: true,
			legacyDecorators: true
		},
		ecmaVersion: 2020,
		sourceType: 'module'
	},
	plugins: [
		'react',
		'sort-destructure-keys',
		'sort-imports-es6-autofix',
		'jsx-a11y',
		'@typescript-eslint'
	],
	root: true,
	rules: {
		'@typescript-eslint/no-unused-vars': [
			'error',
			{
				args: 'after-used',
				ignoreRestSiblings: false,
				vars: 'all'
			}
		],
		'arrow-body-style': [2, 'as-needed'],
		'comma-dangle': 0,
		'default-case': 2,
		'jsx-a11y/label-has-associated-control': 2,
		'jsx-a11y/label-has-for': 0,
		'jsx-a11y/no-autofocus': [
			2,
			{
				ignoreNonDOM: true
			}
		],
		'max-len': 0,
		'new-cap': [
			2,
			{
				capIsNew: false
			}
		],
		'no-console': 2,
		'no-import-assign': 0,
		'no-mixed-spaces-and-tabs': [2, 'smart-tabs'],
		'no-return-assign': [2, 'always'],
		'no-undef': 2,
		'no-unused-vars': 0,
		'object-shorthand': 2,
		'prefer-const': 2,
		'prefer-template': 2,
		'quote-props': [2, 'as-needed'],
		quotes: [2, 'single', 'avoid-escape'],
		'react/jsx-boolean-value': 2,
		'react/jsx-curly-brace-presence': [
			2,
			{
				children: 'ignore',
				props: 'never'
			}
		],
		'react/jsx-fragments': [2, 'syntax'],
		'react/jsx-handler-names': 2,
		'react/jsx-key': 2,
		'react/jsx-no-literals': 2,
		'react/jsx-sort-props': [
			2,
			{
				ignoreCase: true
			}
		],
		'react/jsx-uses-react': 2,
		'react/jsx-uses-vars': 2,
		'react/sort-comp': [
			2,
			{
				groups: {
					rendering: ['/^render.+$/', 'render']
				},
				order: [
					'static-variables',
					'contextType',
					'contextTypes',
					'childContextType',
					'defaultProps',
					'propTypes',
					'state',
					'static-methods',
					'instance-variables',
					'constructor',
					'lifecycle',
					'everything-else',
					'render'
				]
			}
		],
		'require-jsdoc': 0,
		'sort-destructure-keys/sort-destructure-keys': 2,
		'sort-imports-es6-autofix/sort-imports-es6': [
			2,
			{
				ignoreCase: true,
				ignoreMemberSort: false,
				memberSyntaxSortOrder: ['none', 'all', 'single', 'multiple']
			}
		],
		'sort-keys': [
			2,
			'asc',
			{
				caseSensitive: false
			}
		],
		'sort-vars': 2,
		'valid-jsdoc': 0
	},
	settings: {
		react: {
			version: 'detect'
		}
	}
};
