/**
 * SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/empty-line-between-elements');

const parserOptions = {
	parserOptions: {
		ecmaFeatures: {
			jsx: true,
		},
		ecmaVersion: 6,
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('empty-line-between-elements', rule, {
	invalid: [
		{
			code: `
			<div>
				<Invalid1 />
				<Invalid2 />
			</div>
			`,
			errors: [
				{
					message: 'Expected an empty line between sibling elements.',
					type: 'JSXElement',
				},
			],
			output: `
			<div>
				<Invalid1 />

				<Invalid2 />
			</div>
			`,
		},
	],
	valid: [
		{
			code: `
			<div>
				<Valid1 />

				<Valid2 />
			</div>
			`,
		},
	],
});
