/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-url');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-url', rule, {
	invalid: [
		{
			code: `var url = new A.Url('url');`,
			errors: [
				{
					message: 'use the vanilla URL class instead of A.Url',
					type: 'NewExpression',
				},
			],
		},
		{
			code: `var url = A.Url('url');`,
			errors: [
				{
					message: 'use the vanilla URL class instead of A.Url',
					type: 'CallExpression',
				},
			],
		},
	],
	valid: [
		{
			code: `const url = new URL('url');`,
		},
	],
});
