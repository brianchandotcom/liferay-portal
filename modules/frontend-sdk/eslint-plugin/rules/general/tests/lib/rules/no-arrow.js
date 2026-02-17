/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-arrow');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

const message = 'arrow functions are not allowed';

ruleTester.run('no-arrow', rule, {
	invalid: [
		{
			code: `
				const x = () => {
					console.log('Hi');
				};
			`,
			errors: [
				{
					message,
					type: 'ArrowFunctionExpression',
				},
			],
		},
		{
			code: `
				const add = (a, b) => a + b;
			`,
			errors: [
				{
					message,
					type: 'ArrowFunctionExpression',
				},
			],
		},
		{
			code: `
				register((event) => event.preventDefault());
			`,
			errors: [
				{
					message,
					type: 'ArrowFunctionExpression',
				},
			],
		},
	],

	valid: [
		{
			code: `
				const x = function () {
					console.log('Hi');
				};
			`,
		},
		{
			code: `
				const add = function (a, b) {
					return a + b;
				};
			`,
		},
		{
			code: `
				register(function (event) {
					event.preventDefault();
				});
			`,
		},
	],
});
