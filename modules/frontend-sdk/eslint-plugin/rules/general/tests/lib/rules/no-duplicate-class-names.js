/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-duplicate-class-names');

const parserOptions = {
	parserOptions: {
		ecmaFeatures: {
			jsx: true,
		},
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

const message = 'classes in className attribute must be unique';

ruleTester.run('no-duplicate-class-names', rule, {
	invalid: [
		{
			code: '<div className="one one two"></div>',
			errors: [
				{
					message,
					type: 'Literal',
				},
			],
			output: '<div className="one two"></div>',
		},
		{
			code: '<div className="one two one"></div>',
			errors: [
				{
					message,
					type: 'Literal',
				},
			],
			output: '<div className="one two"></div>',
		},
		{
			code: '<CustomPopover triggerClassName="a b b" />',
			errors: [
				{
					message,
					type: 'Literal',
				},
			],
			output: '<CustomPopover triggerClassName="a b" />',
		},
	],

	valid: [
		{code: '<div className="one two"></div>'},

		// Note that we don't check template literals containing expressions.

		{code: '<div className={`one one ${two}`}></div>'},

		// And we don't check "classname-ish" attributes that don't contain
		// strings.

		{code: '<CustomPopover triggerClassName={(1, 1)} />'},
	],
});
