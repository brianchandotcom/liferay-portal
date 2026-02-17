/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-all');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-all', rule, {
	invalid: [
		{
			code: `var elements = A.all('selector');`,
			errors: [
				{
					message: 'use document.querySelectorAll instead',
					type: 'MemberExpression',
				},
			],
		},
	],
	valid: [
		{
			code: `const elements = document.querySelectorAll('selector');`,
		},
	],
});
