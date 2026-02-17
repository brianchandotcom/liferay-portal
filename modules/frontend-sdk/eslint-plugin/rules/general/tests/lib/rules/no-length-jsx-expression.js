/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-length-jsx-expression');

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

ruleTester.run('no-length-jsx-expression', rule, {
	invalid: [
		{
			code: `
				const test = () => {
					return (
						<div>
							{items.length && <div></div>}
						</div>
					)
				}
			`,
			errors: [
				{
					message: `Checking for length in JSX can result in rendering a literal '0'`,
					type: 'LogicalExpression',
				},
			],
			output: `
				const test = () => {
					return (
						<div>
							{!!items.length && <div></div>}
						</div>
					)
				}
			`,
		},
		{
			code: `
				const test = () => {
					return (
						<div>
							{someVal && items.length && <div></div>}
						</div>
					)
				}
			`,
			errors: [
				{
					message: `Checking for length in JSX can result in rendering a literal '0'`,
					type: 'LogicalExpression',
				},
			],
			output: `
				const test = () => {
					return (
						<div>
							{someVal && !!items.length && <div></div>}
						</div>
					)
				}
			`,
		},
	],
	valid: [
		{
			code: `
				const test = () => {
					return (
						<div>
							{!!items.length && <div></div>}
						</div>
					)
				}
			`,
		},
		{
			code: `
				const test = () => {
					return (
						<div>
							{items.length ? <div></div> : null}
						</div>
					)
				}
			`,
		},
		{
			code: `
				const test = () => {
					return (
						<div>
							{items && items.length}
						</div>
					)
				}
			`,
		},
	],
});
