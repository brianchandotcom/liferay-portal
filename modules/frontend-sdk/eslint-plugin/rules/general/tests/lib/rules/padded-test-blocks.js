/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/padded-test-blocks');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

const messageId = 'paddedTestBlocks';

const errors = [
	{
		messageId,
		type: 'CallExpression',
	},
];

ruleTester.run('padded-test-blocks', rule, {
	invalid: [
		{
			code: `
				it('is this', () => {});
				it('is that', () => {});
			`,
			errors,
			output: `
				it('is this', () => {});

				it('is that', () => {});
			`,
		},
		{
			code: `
				it('is this', () => {});
				it.only('is that', () => {});
			`,
			errors,
			output: `
				it('is this', () => {});

				it.only('is that', () => {});
			`,
		},
		{
			code: `
				it.only('is this', () => {});
				it('is that', () => {});
			`,
			errors,
			output: `
				it.only('is this', () => {});

				it('is that', () => {});
			`,
		},
		{
			code: `
				it.only.each();
				it('is that', () => {});
			`,
			errors,
			output: `
				it.only.each();

				it('is that', () => {});
			`,
		},
		{
			code: `
				it.skip.each\`table\`();
				it('is that', () => {});
			`,
			errors,
			output: `
				it.skip.each\`table\`();

				it('is that', () => {});
			`,
		},
		{
			code: `
				describe.only();
				it();
			`,
			errors,
			output: `
				describe.only();

				it();
			`,
		},
		{
			code: `
				describe(() => {
					it();
					test.todo();
				});
			`,
			errors,
			output: `
				describe(() => {
					it();

					test.todo();
				});
			`,
		},
	],

	valid: [
		{
			code: "it('is alone', () => {})",
		},
		{
			code: `
				describe('thing', () => {
					it('is this', () => {});

					it('is that', () => {});
				});
			`,
		},
		{
			code: `
				// Doesn't touch these ones because they aren't Jest API.
				it.skip\`table\`();
				it.skipz();
				it();
			`,
		},
		{
			code: `
				// We do nothing when there are comments around...
				// it's probably not worth the effort.
				it('is this', () => {});
				// This is a comment.
				it('is that', () => {});
			`,
		},
	],
});
