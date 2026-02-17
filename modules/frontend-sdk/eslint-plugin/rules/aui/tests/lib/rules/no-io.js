/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-io');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-io', rule, {
	invalid: [
		{
			code: `A.io.request('url');`,
			errors: [
				{
					message: 'use Liferay.Util.fetch instead',
					type: 'MemberExpression',
				},
			],
		},
	],
	valid: [
		{
			code: `Liferay.Util.fetch('url').then((response) => response.json());`,
		},
	],
});
