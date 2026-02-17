/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-one');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-one', rule, {
	invalid: [
		{
			code: `
				  var element = A.one('selector');
			   `,
			errors: [
				{
					message: 'use document.querySelector instead',
					type: 'MemberExpression',
				},
			],
		},
	],
	valid: [
		{
			code: `const element = document.querySelector('selector');`,
		},
	],
});
