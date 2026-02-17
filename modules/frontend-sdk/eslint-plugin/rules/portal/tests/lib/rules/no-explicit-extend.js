/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-explicit-extend');

const ruleTester = new MultiTester();

const filename = '/data/liferay-portal/modules/apps/a/b/.eslintrc.js';

ruleTester.run('no-explicit-extend', rule, {
	invalid: [
		{

			// As a naked string (liferay/portal).

			code: `
				module.exports = {
					extends: 'liferay/portal'
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'Property',
				},
			],
			filename,
			output: `
				module.exports = {};
			`,
		},
		{

			// As a naked string (liferay/react).

			code: `
				module.exports = {
					extends: 'liferay/react'
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'Property',
				},
			],
			filename,
			output: `
				module.exports = {};
			`,
		},
		{

			// As a naked string before other configuration.

			code: `
				module.exports = {
					extends: 'liferay/portal',
					rules: []
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'Property',
				},
			],
			filename,
			output: `
				module.exports = {
					rules: []
				};
			`,
		},
		{

			// As a naked string in the middle of other configuration.

			code: `
				module.exports = {
					preset: 'fancy',
					extends: 'liferay/portal',
					rules: []
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'Property',
				},
			],
			filename,
			output: `
				module.exports = {
					preset: 'fancy',
					rules: []
				};
			`,
		},
		{

			// As a naked string after other configuration.

			code: `
				module.exports = {
					debug: true,
					extends: 'liferay/portal'
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'Property',
				},
			],
			filename,
			output: `
				module.exports = {
					debug: true
				};
			`,
		},
		{

			// At the beginning, on one line.

			code: `
				module.exports = {
					extends: ['liferay/portal', 'other']
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'ArrayExpression',
				},
			],
			filename,
			output: `
				module.exports = {
					extends: ['other']
				};
			`,
		},
		{

			// In the middle, on one line.

			code: `
				module.exports = {
					extends: ['special', 'liferay/portal', 'other']
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'ArrayExpression',
				},
			],
			filename,
			output: `
				module.exports = {
					extends: ['special', 'other']
				};
			`,
		},
		{

			// At the end, on one line.

			code: `
				module.exports = {
					extends: ['something', 'liferay/portal']
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'ArrayExpression',
				},
			],
			filename,
			output: `
				module.exports = {
					extends: ['something']
				};
			`,
		},
		{

			// Both liferay/portal and liferay/react together (at start).

			code: `
				module.exports = {
					extends: [
						'liferay/portal',
						'liferay/react',
						'trailing'
					]
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'ArrayExpression',
				},
			],
			filename,
			output: `
				module.exports = {
					extends: [
						'trailing'
					]
				};
			`,
		},
		{

			// Both liferay/portal and liferay/react together (at end).

			code: `
				module.exports = {
					extends: ['foo', 'liferay/portal', 'liferay/react']
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'ArrayExpression',
				},
			],
			filename,
			output: `
				module.exports = {
					extends: ['foo']
				};
			`,
		},
		{

			// Both liferay/portal and liferay/react together (in the middle).

			code: `
				module.exports = {
					extends: ['foo', 'liferay/portal', 'liferay/react', 'bar']
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'ArrayExpression',
				},
			],
			filename,
			output: `
				module.exports = {
					extends: ['foo', 'bar']
				};
			`,
		},
		{

			// Both liferay/portal and liferay/react together (with something in
			// between).

			code: `
				module.exports = {
					extends: [
						'foo',
						'liferay/portal',
						'bar',
						'liferay/react',
						'baz'
					]
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'ArrayExpression',
				},
			],
			filename,
			output: `
				module.exports = {
					extends: [
						'foo',
						'bar',
						'baz'
					]
				};
			`,
		},
		{

			// Alone.

			code: `
				module.exports = {
					extends: ['liferay/portal']
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'Property',
				},
			],
			filename,
			output: `
				module.exports = {};
			`,
		},
		{

			// Alone with a trailing comma.

			code: `
				module.exports = {
					extends: ['liferay/portal'],
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'Property',
				},
			],
			filename,
			output: `
				module.exports = {};
			`,
		},
		{

			// Alone with a trailing comma across multiple lines.

			code: `
				module.exports = {
					extends: [
						'liferay/portal',
					],
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'Property',
				},
			],
			filename,
			output: `
				module.exports = {};
			`,
		},
		{

			// In a .babelrc.js file.

			code: `
				module.exports = {
					presets: [
						'fancy-preset',
						'@babel/preset-env',
					],
					overrides: [{
						test: 'some/path',
						presets: ['@babel/preset-react'],
					}]
				};
			`,
			errors: [
				{
					messageId: 'noExplicitPreset',
					type: 'ArrayExpression',
				},
				{
					messageId: 'noExplicitPreset',
					type: 'Property',
				},
			],
			filename: '.babelrc.js',
			output: `
				module.exports = {
					presets: [
						'fancy-preset',
					],
					overrides: [{
						test: 'some/path',
					}]
				};
			`,
		},
		{

			// Regression.
			// See: https://github.com/liferay/liferay-portal-ee/pull/18545#issuecomment-600614052

			code: `
				module.exports = {
					extends: ['liferay/react'],
					globals: {
						AlloyEditor: true,
						process: true
					},
					rules: {
						'react/no-string-refs': 'off'
					}
				};
			`,
			errors: [
				{
					messageId: 'noExplicitExtend',
					type: 'Property',
				},
			],
			filename,
			output: `
				module.exports = {
					globals: {
						AlloyEditor: true,
						process: true
					},
					rules: {
						'react/no-string-refs': 'off'
					}
				};
			`,
		},
	],

	valid: [
		{
			code: `
				module.exports = {
					extends: ['liferay/metal']
				};
			`,
			filename,
		},
		{
			code: `
				module.exports = {
					extends: 'liferay/metal'
				};
			`,
			filename,
		},
		{
			code: `
				module.exports = {
					extends: []
				};
			`,
			filename,
		},
		{

			// Would be invalid, but not in an .eslintrc.js file.

			code: `
				module.exports = {
					extends: 'liferay/portal'
				};
			`,
			filename: '/tmp/not-an-eslintrc.js',
		},
		{

			// Not invalid, but not under an "extends" property.

			code: `
				module.exports = {
					extendz: 'liferay/portal'
				};
			`,
			filename,
		},
		{

			// Not invalid, because not in an object.

			code: `
				var array = ['liferay/portal'];
			`,
			filename,
		},
		{

			// Would be invalid, but not in a .babelrc.js file.

			code: `
				module.exports = {
					presets: ['@babel/preset-react']
				};
			`,
			filename: '/tmp/not-a-babelrc.js',
		},
	],
});
