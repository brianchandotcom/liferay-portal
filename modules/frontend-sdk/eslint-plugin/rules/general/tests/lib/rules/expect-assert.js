/**
 * SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/expect-assert');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('expect-assert', rule, {
	invalid: [
		{
			code: `
				expect(someVal);
				expect(getByText('report'));
			`,
			errors: [
				{
					message: 'every `expect()` should assert something.',
					type: 'CallExpression',
				},
				{
					message: 'every `expect()` should assert something.',
					type: 'CallExpression',
				},
			],
		},
	],
	valid: [
		{
			code: `
				expect().isEqual();
				expect(getByText('report')).exists();
			`,
		},
	],
});
