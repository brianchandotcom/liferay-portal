/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/destructure-requires');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('destructure-requires', rule, {
	invalid: [
		{
			code: "const init = require('foo').init;",
			errors: [
				{
					message: 'require() statements should use destructuring',
					type: 'VariableDeclarator',
				},
			],
			output: "const {init} = require('foo');",
		},
		{
			code: "const alias = require('bar').thing;",
			errors: [
				{
					message: 'require() statements should use destructuring',
					type: 'VariableDeclarator',
				},
			],
			output: "const {thing: alias} = require('bar');",
		},
		{
			code: "const {nested} = require('baz').that;",
			errors: [
				{
					message: 'require() statements should use destructuring',
					type: 'VariableDeclarator',
				},
			],
			output: "const {that: {nested}} = require('baz');",
		},
	],
	valid: [
		{
			code: "const {init} = require('foo');",
		},
		{
			code: "const {thing: alias} = require('bar');",
		},
		{
			code: "const {that: {nested}} = require('baz');",
		},
	],
});
