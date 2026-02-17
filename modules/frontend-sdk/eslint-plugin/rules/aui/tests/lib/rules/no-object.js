/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-object');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-object', rule, {
	invalid: [
		{
			code: `var objectKeys = A.Object.keys({foo: 'bar'});`,
			errors: [
				{
					message: 'use the native Object class instead',
					type: 'MemberExpression',
				},
			],
		},
	],
	valid: [
		{
			code: `const objectKeys = Object.keys({foo: 'bar'});`,
		},
	],
});
