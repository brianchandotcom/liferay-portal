/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-get-data-attribute');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-get-data-attribute', rule, {
	invalid: [
		{
			code: `
				el.querySelector('test').getAttribute('data-foo');
				el.getAttribute('data-test');
				el.getAttribute('data-some-other-key');
				one.el.getAttribute('data-some-other-key');
			`,
			errors: [
				{
					message:
						'Use "dataset.foo" instead of "getAttribute(\'data-foo\')"',
					type: 'Identifier',
				},
				{
					message: `Use "dataset.test" instead of "getAttribute('data-test')"`,
					type: 'Identifier',
				},
				{
					message: `Use "dataset.someOtherKey" instead of "getAttribute('data-some-other-key')"`,
					type: 'Identifier',
				},
				{
					message: `Use "dataset.someOtherKey" instead of "getAttribute('data-some-other-key')"`,
					type: 'Identifier',
				},
			],
			output: `
				el.querySelector('test').getAttribute('data-foo');
				el.dataset.test;
				el.dataset.someOtherKey;
				one.el.dataset.someOtherKey;
			`,
		},
	],

	valid: [
		{
			code: `
				el.dataset.test;
				el.dataset.fooBar;
			 `,
		},
	],
});
