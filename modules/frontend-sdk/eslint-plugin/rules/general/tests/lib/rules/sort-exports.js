/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/sort-exports');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('sort-exports', rule, {
	invalid: [
		{

			// Basic example.

			code: `
				export {Foo} from './Foo';
				export {Bar} from './Bar';
			`,
			errors: [
				{
					message:
						'exports must be sorted by module name ' +
						'(expected: "./Bar" << "./Foo")',
					type: 'ExportNamedDeclaration',
				},
			],
			output: `
				export {Bar} from './Bar';
				export {Foo} from './Foo';
			`,
		},
	],

	valid: [
		{

			// Well-sorted exports.

			code: `
				export {Bar} from './Bar';
				export {Foo} from './Foo';
			`,
		},
	],
});
