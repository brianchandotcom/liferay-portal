/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-conditional-object-keys');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

const message = 'Object.keys({}) always evaluates to truthy';
const negationMessage = '!Object.keys({}) always evaluates to false';
const doubleNegationMessage = '!!Object.keys({}) always evaluates to true';

ruleTester.run('no-conditional-object-keys', rule, {
	invalid: [
		{
			code: `
				if (Object.keys({foo: 'bar'})) {}
			`,
			errors: [
				{
					message,
					type: 'CallExpression',
				},
			],
		},
		{
			code: `
				if (!Object.keys({foo: 'bar'})) {}
			`,
			errors: [
				{
					message: negationMessage,
					type: 'CallExpression',
				},
			],
		},
		{
			code: `
				if (!!Object.keys({foo: 'bar'})) {}
			`,
			errors: [
				{
					message: doubleNegationMessage,
					type: 'CallExpression',
				},
			],
		},
		{
			code: `
				!Object.keys({foo: 'bar'})
			`,
			errors: [
				{
					message: negationMessage,
					type: 'CallExpression',
				},
			],
		},
		{
			code: `
				!!Object.keys({foo: 'bar'})
			`,
			errors: [
				{
					message: doubleNegationMessage,
					type: 'CallExpression',
				},
			],
		},
		{
			code: `
				Object.keys({foo: 'bar'}) && 'test'
			`,
			errors: [
				{
					message,
					type: 'CallExpression',
				},
			],
		},
		{
			code: `
				!Object.keys({foo: 'bar'}) && 'test'
			`,
			errors: [
				{
					message: negationMessage,
					type: 'CallExpression',
				},
			],
		},
		{
			code: `
				!!Object.keys({foo: 'bar'}) && 'test'
			`,
			errors: [
				{
					message: doubleNegationMessage,
					type: 'CallExpression',
				},
			],
		},
		{
			code: `
				const keysNegated = !Object.keys({foo: 'bar'});
			`,
			errors: [
				{
					message: negationMessage,
					type: 'CallExpression',
				},
			],
		},
		{
			code: `
				const keysDoubleNegated = !!Object.keys({foo: 'bar'});
			`,
			errors: [
				{
					message: doubleNegationMessage,
					type: 'CallExpression',
				},
			],
		},
	],
	valid: [
		{
			code: `
				if (Object.keys({foo: 'bar'}).length) {}
			`,
		},
		{
			code: `
				if (!Object.keys({foo: 'bar'}).length) {}
			`,
		},
		{
			code: `
				!Object.keys({foo: 'bar'}).length;
			`,
		},
		{
			code: `
				!!Object.keys({foo: 'bar'}).length;
			`,
		},
		{
			code: `
				Object.keys({foo: 'bar'}).length && 'test';
			`,
		},
		{
			code: `
				!Object.keys({foo: 'bar'}).length && 'test';
			`,
		},
		{
			code: `
				!!Object.keys({foo: 'bar'}).length && 'test';
			`,
		},
		{
			code: `
				typeof Object.keys({foo: 'bar'});
			`,
		},
		{
			code: `
				Object.keys({foo: 'bar'}).find(i => true);
			`,
		},
		{
			code: `
				if (Object.keys({foo: 'bar'}).find(i => true)) {};
			`,
		},
		{
			code: `
				const keys = Object.keys({foo: 'bar'});
			`,
		},
	],
});
