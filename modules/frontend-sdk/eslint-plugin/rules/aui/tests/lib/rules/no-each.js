/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-each');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-each', rule, {
	invalid: [
		{
			code: `A.each(array, (item) => console.log(item));`,
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
			code: `array.forEach((item) => console.log(item));`,
		},
	],
});
