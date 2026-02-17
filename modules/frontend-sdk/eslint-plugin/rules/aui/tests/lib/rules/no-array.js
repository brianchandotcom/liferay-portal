/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-array');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-array', rule, {
	invalid: [
		{
			code: `A.Array.remove(array, itemIndex);`,
			errors: [
				{
					message: 'use the native Array class methods instead',
					type: 'MemberExpression',
				},
			],
		},
	],
	valid: [
		{
			code: `array.splice(itemIndex, 1);`,
		},
	],
});
