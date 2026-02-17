/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/group-imports');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('group-imports', rule, {
	invalid: [
		{
			code: `
				import abc from 'abc';
				import {g, z} from 'one'; // Correct.

				import {a} from 'other'; // 1 excess blank line before.


				import w from 'w'; // 2 excess blank lines before.
				// Comment describing the next import.
				import stuff from 'stuff';
				import 'side-effect';
				import x from './x';
			`,
			errors: [
				{
					message:
						'imports must be grouped ' +
						'(unexpected blank line before: "other")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(unexpected blank line before: "w")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(expected blank line before: "stuff")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(expected blank line before: "side-effect")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(expected blank line before: "./x")',
					type: 'ImportDeclaration',
				},
			],
			output: `
				import abc from 'abc';
				import {g, z} from 'one'; // Correct.
				import {a} from 'other'; // 1 excess blank line before.
				import w from 'w'; // 2 excess blank lines before.

				// Comment describing the next import.
				import stuff from 'stuff';

				import 'side-effect';

				import x from './x';
			`,
		},
		{
			code: `
				import abc from 'abc';
				import type {U} from 'other';
				import {g, z} from 'one'; // Correct.

				import {a} from 'other'; // 1 excess blank line before.


				import w from 'w'; // 2 excess blank lines before.
				import type {X, Z} from 'zzz';
				// Comment describing the next import.
				import stuff from 'stuff';
				import 'side-effect';
				import type {T} from 'one';
				import type {V} from './local';
				import x from './x';
			`,
			errors: [
				{
					message:
						'imports must be grouped ' +
						'(expected blank line before: "other")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(expected blank line before: "one")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(unexpected blank line before: "other")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(unexpected blank line before: "w")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(expected blank line before: "zzz")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(expected blank line before: "stuff")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(expected blank line before: "side-effect")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(expected blank line before: "one")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(expected blank line before: "./local")',
					type: 'ImportDeclaration',
				},
				{
					message:
						'imports must be grouped ' +
						'(expected blank line before: "./x")',
					type: 'ImportDeclaration',
				},
			],
			output: `
				import abc from 'abc';

				import type {U} from 'other';

				import {g, z} from 'one'; // Correct.
				import {a} from 'other'; // 1 excess blank line before.
				import w from 'w'; // 2 excess blank lines before.

				import type {X, Z} from 'zzz';

				// Comment describing the next import.
				import stuff from 'stuff';

				import 'side-effect';

				import type {T} from 'one';

				import type {V} from './local';

				import x from './x';
			`,

			// espree doesn't know how to parse TypeScript imports.

			skip: ['espree'],
		},
	],

	valid: [
		{
			code: `
				import {g, z} from 'one';
				import {a} from 'other';

				// Comment describing the next import.
				import stuff from 'stuff';

				import 'side-effect';

				import x from './x';
			`,

			// espree doesn't know how to parse TypeScript imports.

			skip: ['espree'],
		},
		{
			code: `
				import {g, z} from 'one';
				import {a} from 'other';

				// Comment describing the next import.
				import stuff from 'stuff';

				import 'side-effect';

				import x from './x';

				import type {T} from 'one';
				import type {U} from 'other';
				import type {X, Z} from 'zzz';
			`,

			// espree doesn't know how to parse TypeScript imports.

			skip: ['espree'],
		},
		{

			// Regression test: `MemberExpression` here was forcing a blank
			// line between "gulp" and "os" imports.
			//
			// https://github.com/liferay/eslint-config-liferay/issues/94

			code: `
				var del = require('del');
				var fs = require('fs-extra');
				var Gulp = require('gulp').Gulp;
				var os = require('os');
				var path = require('path');
			`,
		},
		{

			// Regression test: Immediate `CallExpression` here was
			// forcing a blank line between "gulp-load-plugins" and
			// the following import.
			//
			// https://github.com/liferay/eslint-config-liferay/issues/94

			code: `
				const del = require('del');
				const fs = require('fs-extra');
				const _ = require('lodash');
				const path = require('path');
				const plugins = require('gulp-load-plugins')();
				const replace = require('gulp-replace-task');
				const through = require('through2');
				const PluginError = require('plugin-error');
			`,
		},
		{

			// Regression test: input like this would cause:
			//
			//      TypeError: Cannot read property 'range' of null
			//

			code: `
				const merge = require('webpack-merge'),
					webpackBase = require('./webpack.config.js');
			`,
			errors: [],
			output: `
				const merge = require('webpack-merge'),
					webpackBase = require('./webpack.config.js');
			`,
		},
	],
});
