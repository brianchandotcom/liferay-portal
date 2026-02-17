/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-anonymous-exports');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-anonymous-exports', rule, {
	invalid: [
		{
			code: `export const test = () => {}`,
			errors: [
				{
					message:
						"Use named function for export instead of arrow function. Example: 'function fooBar() {}'",
					type: 'ExportNamedDeclaration',
				},
			],
		},
		{
			code: `export default () => {}`,
			errors: [
				{
					message:
						"Use named function for export. Example: 'function fooBar() {}'",
					type: 'ExportDefaultDeclaration',
				},
			],
		},
	],
	valid: [
		{
			code: `
				export function test() {}
				export default function fooBar() {}
			`,
		},
	],
});
