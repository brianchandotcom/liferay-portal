/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-merge');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-merge', rule, {
	invalid: [
		{
			code: `var mergedObject = A.merge(object1, object2);`,
			errors: [
				{
					message: 'use Object.assign instead',
					type: 'MemberExpression',
				},
			],
		},
	],
	valid: [
		{
			code: `const mergedObject = Object.assign(object1, object2);`,
		},
	],
});
