/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

'use strict';

const config = {
	extends: [require.resolve('./react')],
	plugins: ['react'],
	rules: {

		/**
		 * @see https://github.com/yannickcr/eslint-plugin-react
		 */

		// In Metal, string refs are the only kind of refs.

		'react/no-string-refs': 'off',

		// In Metal, common property names like "class" will cause this rule to
		// fire (because React expects "className").

		'react/no-unknown-property': 'off',
	},
	settings: {
		react: {
			version: 'detect',
		},
	},
};

module.exports = config;
