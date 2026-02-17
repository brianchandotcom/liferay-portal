/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-typeof-object');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-typeof-object', rule, {
	invalid: [
		{
			code: `
				typeof x === 'object';

				someVal && typeof x === 'object';
			`,
			errors: [
				{
					message:
						'checking "typeof x === \'object\'" can be falsy if null. Use "x !== null && typeof x === \'object\'"',
					type: 'BinaryExpression',
				},
				{
					message:
						'checking "typeof x === \'object\'" can be falsy if null. Use "x !== null && typeof x === \'object\'"',
					type: 'BinaryExpression',
				},
			],
			output: `
				x !== null && typeof x === 'object';

				someVal && x !== null && typeof x === 'object';
			`,
		},
	],

	valid: [
		{
			code: `
				!x && typeof x === 'object';
				
				x !== null && typeof x === 'object';
			`,
		},
	],
});
