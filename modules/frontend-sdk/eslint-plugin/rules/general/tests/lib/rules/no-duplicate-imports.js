/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-duplicate-imports');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-duplicate-imports', rule, {
	invalid: [
		{
			code: `
				import {g, z} from 'one';
				import x from './x';
				import {a} from 'one';
			`,
			errors: [
				{
					message:
						'modules must be imported only once ' +
						'(duplicate import: "one")',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: `
				import type {g, z} from 'two';
				import x from './x';
				import type {a} from 'two';
			`,
			errors: [
				{
					message:
						'modules must be imported only once ' +
						'(duplicate import: "two")',
					type: 'ImportDeclaration',
				},
			],

			// espree doesn't know how to parse TypeScript imports.

			skip: ['espree'],
		},
	],

	valid: [
		{
			code: `
				import {a, g, z} from 'one';
				import x from './x';
			`,
		},
		{
			code: `
				// A non-type + a type import aren't considered duplicates.
				import thing from 'thing';
				import type {Example} from 'thing';
			`,

			// espree doesn't know how to parse TypeScript imports.

			skip: ['espree'],
		},
	],
});
