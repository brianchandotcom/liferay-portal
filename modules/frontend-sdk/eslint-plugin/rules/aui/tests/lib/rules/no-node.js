/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-node');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-node', rule, {
	invalid: [
		{
			code: `var newButton = A.Node.create('button');`,
			errors: [
				{
					message:
						'use native methods instead, mostly found inside the Document interface',
					type: 'MemberExpression',
				},
			],
		},
	],
	valid: [
		{
			code: `const newButton = document.createElement('button');`,
		},
		{
			code: `
				AUI.add(
					'liferay-test',
					function (A) {
						A.Node.create('button');
					}
				);
			`,
		},
	],
});
