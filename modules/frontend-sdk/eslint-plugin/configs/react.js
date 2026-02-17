/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

'use strict';

const config = {
	extends: [require.resolve('./general')],
	parserOptions: {
		ecmaFeatures: {
			jsx: true,
		},
	},
	plugins: ['react', 'react-hooks'],
	rules: {
		'@liferay/no-duplicate-class-names': 'error',
		'@liferay/sort-class-names': 'error',
		'@liferay/trim-class-names': 'error',

		/**
		 * @see https://github.com/yannickcr/eslint-plugin-react
		 */
		'react/forbid-foreign-prop-types': 'error',
		'react/jsx-curly-brace-presence': [
			'error',
			{children: 'never', props: 'never'},
		],
		'react/jsx-fragments': 'error',
		'react/jsx-key': 'error',
		'react/jsx-no-comment-textnodes': 'error',
		'react/jsx-no-duplicate-props': 'error',
		'react/jsx-no-undef': 'error',
		'react/jsx-sort-props': 'error',
		'react/jsx-uses-react': 'error',
		'react/jsx-uses-vars': 'error',
		'react/no-children-prop': 'error',
		'react/no-danger-with-children': 'error',
		'react/no-direct-mutation-state': 'error',
		'react/no-is-mounted': 'error',
		'react/no-render-return-value': 'error',
		'react/no-string-refs': 'error',
		'react/no-unescaped-entities': 'error',
		'react/no-unknown-property': 'error',
		'react/require-render-return': 'error',

		/**
		 * @see https://reactjs.org/docs/hooks-rules.html
		 */
		'react-hooks/exhaustive-deps': 'warn',
		'react-hooks/rules-of-hooks': 'error',
	},
	settings: {
		react: {
			version: 'detect',
		},
	},
};

module.exports = config;
