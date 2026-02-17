/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-it-should');

const ruleTester = new MultiTester();

ruleTester.run('no-it-should', rule, {
	invalid: [
		{
			code: "it('should do the right thing')",
			errors: [
				{
					message: 'it() strings should not start with "should"',
					type: 'Literal',
				},
			],
		},
		{
			code: "it('Should do the right thing')",
			errors: [
				{
					message: 'it() strings should not start with "should"',
					type: 'Literal',
				},
			],
		},
	],

	valid: [
		{
			code: 'it("behaves")',
		},
		{
			code: `it('has a checkbox with label "should notify"')`,
		},
	],
});
