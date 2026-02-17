/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/prefer-length-check');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

const message = 'prefer using .length';

ruleTester.run('prefer-length-check', rule, {
	invalid: [
		{
			code: `if (foo.length === 0) {}`,
			errors: [
				{
					message,
					type: 'BinaryExpression',
				},
			],
			output: `if (!foo.length) {}`,
		},
		{
			code: `if (foo.length > 0) {}`,
			errors: [
				{
					message,
					type: 'BinaryExpression',
				},
			],
			output: `if (!!foo.length) {}`,
		},
		{
			code: `if (0 < foo.length) {}`,
			errors: [
				{
					message,
					type: 'BinaryExpression',
				},
			],
			output: `if (!!foo.length) {}`,
		},
		{
			code: `if (0 === foo.length) {}`,
			errors: [
				{
					message,
					type: 'BinaryExpression',
				},
			],
			output: `if (!foo.length) {}`,
		},
	],
	valid: [
		{
			code: `if (foo.length) {}`,
		},
		{
			code: `if (!foo.length) {}`,
		},
		{
			code: `if (!!foo.length) {}`,
		},
	],
});
