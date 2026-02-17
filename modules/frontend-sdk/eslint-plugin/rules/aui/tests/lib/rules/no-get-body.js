/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-get-body');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-get-body', rule, {
	invalid: [
		{
			code: `var body = A.getBody;`,
			errors: [
				{
					message:
						"this is a shortcut to A.one('body'), use document.body",
					type: 'MemberExpression',
				},
			],
		},
	],
	valid: [
		{
			code: `const body = document.body;`,
		},
	],
});
