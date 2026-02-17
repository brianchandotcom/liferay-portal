/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-localhost-reference');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

const message = 'do not make explicit references to localhost.';

ruleTester.run('no-localhost-reference', rule, {
	invalid: [
		{
			code: `
				const foo = 'localhost:8080';
			`,
			errors: [
				{
					message,
					type: 'Literal',
				},
			],
		},
		{
			code: `
				const a = {
					url: 'localhost' 
				}
			`,
			errors: [
				{
					message,
					type: 'Literal',
				},
			],
		},
		{
			code: `
				test('localhost:8080')
			`,
			errors: [
				{
					message,
					type: 'Literal',
				},
			],
		},
	],
	valid: [
		{
			code: "const localhost = 'foo';",
		},
		{
			code: '// localhost:3000',
		},
	],
});
