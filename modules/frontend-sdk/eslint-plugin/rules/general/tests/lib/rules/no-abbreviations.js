/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-abbreviations');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-abbreviations', rule, {
	invalid: [
		{
			code: `
				e.preventDefault();
			`,
			errors: [
				{
					message:
						'Avoid "e" abbreviation, preferred alternatives are ["event","error"]',
					type: 'Identifier',
				},
			],
		},

		{
			code: `
				const btn = document.getElementById('button');
			`,
			errors: [
				{
					message:
						'Avoid "btn" abbreviation, preferred alternative is "button"',
					type: 'Identifier',
				},
			],
		},

		{
			code: `
				trigger.addEventListener('click', (e) => {
					console.log('lint error');
				});
			`,
			errors: [
				{
					message:
						'Avoid "e" abbreviation, preferred alternatives are ["event","error"]',
					type: 'Identifier',
				},
			],
		},
	],

	valid: [
		{
			code: `(error) => error.toString()`,
		},
		{
			code: `this.obj = 'this is valid'`,
		},
		{
			code: `const a = {img: '/foo/bar'};`,
		},
	],
});
