/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-modal');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-modal', rule, {
	invalid: [
		{
			code: `var modal = new A.Modal({modalOptions});`,
			errors: [
				{
					message: 'use Liferay.Util.openModal instead',
					type: 'MemberExpression',
				},
			],
		},
	],
	valid: [
		{
			code: `Liferay.Util.openModal({modalOptions});`,
		},
	],
});
