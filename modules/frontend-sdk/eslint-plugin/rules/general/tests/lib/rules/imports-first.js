/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/imports-first');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('imports-first', rule, {
	invalid: [
		{
			code: `
				const ok = require('ok');

				const NAME = 'thing';

				import x from './x';

				require('thing');

				const final = require('./final');
			`,
			errors: [
				{
					message:
						'import of "./x" must come before other statements',
					type: 'ImportDeclaration',
				},
				{
					message:
						'import of "thing" must come before other statements',
					type: 'ExpressionStatement',
				},
				{
					message:
						'import of "./final" must come before other statements',
					type: 'VariableDeclaration',
				},
			],
		},
		{
			code: `
				const NAME: Thing = {};

				import type {Thing} from './Thing';
			`,
			errors: [
				{
					message:
						'import of "./Thing" must come before other statements',
					type: 'ImportDeclaration',
				},
			],

			// espree doesn't know how to parse TypeScript imports.

			skip: ['espree'],
		},
		{

			// Regression test: the second require here wasn't being flagged
			// because we were choking (silently) on the directive:

			code: `
				'use strict';

				var {Gulp} = require('gulp');

				var gulp = new Gulp();

				var {getArgv} = require('../../lib/util');
			`,
			errors: [
				{
					message:
						'import of "../../lib/util" must come before other statements',
					type: 'VariableDeclaration',
				},
			],
		},
		{

			// Note that directives are only ignored when they are at the top.

			code: `
				const a = require('a');

				'use strict';

				const x = require('x');
			`,
			errors: [
				{
					message: 'import of "x" must come before other statements',
					type: 'VariableDeclaration',
				},
			],
		},
	],

	valid: [
		{
			code: `
				const z = require('z');

				import x from './x';

				const NAME = 'thing';

				function init() {
					// This require() is fine, because
					// it is not at the top-level.
					require('other')();
				}
			`,
		},
		{
			code: `
				import type {Thing} from './Thing';

				const NAME: Thing = {};
			`,

			// espree doesn't know how to parse TypeScript imports.

			skip: ['espree'],
		},
		{

			// Regression test: `MemberExpression` here made us fail to
			// recognize "gulp" as an import.
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

			// Regression test: Immediate `CallExpression` here made us fail to
			// recognize "gulp-load-plugins" as an import.
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
			code: `
				'use strict';

				const a = require('a');
				const b = require('b');
			`,
		},
	],
});
