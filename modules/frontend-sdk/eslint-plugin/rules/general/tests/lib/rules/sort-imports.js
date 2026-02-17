/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/sort-imports');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('sort-imports', rule, {
	invalid: [
		{

			// Basic example.

			code: `
				import {g, z} from 'one';
				import x from './x';
				import {a} from 'other';
				import * as s from '../s';
			`,
			errors: [
				{
					message:
						'imports must be sorted by module name ' +
						'(expected: "other" << "../s" << "./x")',
					type: 'ImportDeclaration',
				},
			],
			output: `
				import {g, z} from 'one';
				import {a} from 'other';
				import * as s from '../s';
				import x from './x';
			`,
		},
		{

			// Example with trailing comments.

			code: `
				import {g, z} from 'one';
				import x from './x';
				import {a} from 'other'; // Comment will move with the import.
				import * as s from '../s';
			`,
			errors: [
				{
					message:
						'imports must be sorted by module name ' +
						'(expected: "other" << "../s" << "./x")',
					type: 'ImportDeclaration',
				},
			],
			output: `
				import {g, z} from 'one';
				import {a} from 'other'; // Comment will move with the import.
				import * as s from '../s';
				import x from './x';
			`,
		},
		{

			// Example with leading comments.

			code: `
				import {g, z} from 'one';
				import x from './x';
				import {a} from 'other';
				// Comment on "../s" stays with "../s".
				import * as s from '../s';
			`,
			errors: [
				{
					message:
						'imports must be sorted by module name ' +
						'(expected: "other" << "../s" << "./x")',
					type: 'ImportDeclaration',
				},
			],
			output: `
				import {g, z} from 'one';
				import {a} from 'other';
				// Comment on "../s" stays with "../s".
				import * as s from '../s';
				import x from './x';
			`,
		},
		{

			// Example showing that we don't move header comments.

			code: `
				/**
				 * © 2017 Liferay, Inc. <https://liferay.com>
				 *
				 * SPDX-License-Identifier: MIT
				 */
				import z from 'z';
				import a from 'a';
			`,
			errors: [
				{
					message:
						'imports must be sorted by module name ' +
						'(expected: "a" << "z")',
					type: 'ImportDeclaration',
				},
			],
			output: `
				/**
				 * © 2017 Liferay, Inc. <https://liferay.com>
				 *
				 * SPDX-License-Identifier: MIT
				 */
				import a from 'a';
				import z from 'z';
			`,
		},
		{

			// Example showing that we don't sort across boundaries.

			code: `
				import x from './x';
				import {a} from 'other';
				import 'a-side-effectful-import'; // Boundary here.
				import * as s from '../s';
				import {g, z} from 'one';
			`,
			errors: [
				{
					message:
						'imports must be sorted by module name ' +
						'(expected: "other" << "./x" << "a-side-effectful-import" << "one" << "../s")',
					type: 'ImportDeclaration',
				},
			],
			output: `
				import {a} from 'other';
				import x from './x';
				import 'a-side-effectful-import'; // Boundary here.
				import {g, z} from 'one';
				import * as s from '../s';
			`,
		},
		{

			// Basic example with `require` calls.

			code: `
				const {g, z} = require('one');
				const x = require('./x');
				const {a} = require('other');
				const s = require('../s');
			`,
			errors: [
				{
					message:
						'imports must be sorted by module name ' +
						'(expected: "other" << "../s" << "./x")',
					type: 'VariableDeclaration',
				},
			],
			output: `
				const {g, z} = require('one');
				const {a} = require('other');
				const s = require('../s');
				const x = require('./x');
			`,
		},
		{

			// Example with type-only imports.

			code: `
				import type {U} from './Records';
				import type {V} from 'very-external';
				import ReactDOM from 'react-dom';
				import type {T} from './Frisbee';
				import React from 'react';
				import Record from './Records';
				import 'side-effects';
			`,
			errors: [
				{
					message:
						'imports must be sorted by module name ' +
						'(expected: "react" << "react-dom" << "./Records" << "side-effects" << (type) "very-external" << (type) "./Frisbee" << (type) "./Records")',
					type: 'ImportDeclaration',
				},
			],
			output: `
				import React from 'react';
				import ReactDOM from 'react-dom';
				import Record from './Records';
				import 'side-effects';
				import type {V} from 'very-external';
				import type {T} from './Frisbee';
				import type {U} from './Records';
			`,

			// espree doesn't know how to parse TypeScript imports.

			skip: ['espree'],
		},
		{

			// Regression test: two imports from the same module were causing
			// duplication.

			code: `
				 import {Config} from 'metal-state';
				 import {debounce} from 'frontend-js-web';
				 import {PortletBase} from 'frontend-js-web';
				 import Soy from 'metal-soy';

				 import templates from './FragmentPreview.soy';
			`,
			errors: [
				{
					message:
						'imports must be sorted by module name ' +
						'(expected: "frontend-js-web" << "frontend-js-web" << "metal-soy" << "metal-state")',
					type: 'ImportDeclaration',
				},
			],
			output: `
				 import {PortletBase} from 'frontend-js-web';
				 import {debounce} from 'frontend-js-web';
				 import Soy from 'metal-soy';
				 import {Config} from 'metal-state';

				 import templates from './FragmentPreview.soy';
			`,
		},
		{

			// Regression test: `MemberExpression` here was preventing "gulp"
			// import from being properly recognized.
			//
			// https://github.com/liferay/eslint-config-liferay/issues/94

			code: `
				var fs = require('fs-extra');
				var os = require('os');
				var Gulp = require('gulp').Gulp;
			`,
			errors: [
				{
					message:
						'imports must be sorted by module name ' +
						'(expected: "gulp" << "os")',
					type: 'VariableDeclaration',
				},
			],
			output: `
				var fs = require('fs-extra');
				var Gulp = require('gulp').Gulp;
				var os = require('os');
			`,
		},
		{

			// Regression test: Immediate `CallExpression` here was preventing
			// "gulp-load-plugins" import from being properly recognized.
			//
			// https://github.com/liferay/eslint-config-liferay/issues/94

			code: `
				const replace = require('gulp-replace-task');
				const plugins = require('gulp-load-plugins')();
				const path = require('path');
			`,
			errors: [
				{
					message:
						'imports must be sorted by module name ' +
						'(expected: "gulp-load-plugins" << "gulp-replace-task")',
					type: 'VariableDeclaration',
				},
			],
			output: `
				const plugins = require('gulp-load-plugins')();
				const replace = require('gulp-replace-task');
				const path = require('path');
			`,
		},
		{

			// Regression test: "metal-dom" was sorting before "metal".

			code: `
				import dom from 'metal-dom';
				import core from 'metal';
			`,
			errors: [
				{
					message:
						'imports must be sorted by module name ' +
						'(expected: "metal" << "metal-dom")',
					type: 'ImportDeclaration',
				},
			],
			output: `
				import core from 'metal';
				import dom from 'metal-dom';
			`,
		},
		{

			// Regression test: "." alone was not considered to be relative.
			// https://github.com/liferay/liferay-frontend-projects/issues/129

			code: `
				import {Project} from '.';
				import prop from 'dot-prop';
			`,
			errors: [
				{
					message:
						'imports must be sorted by module name ' +
						'(expected: "dot-prop" << ".")',
					type: 'ImportDeclaration',
				},
			],
			output: `
				import prop from 'dot-prop';
				import {Project} from '.';
			`,
		},
	],

	valid: [
		{

			// Well-sorted imports.

			code: `
				import {g, z} from 'one';
				import {a} from 'other';
				import * as s from '../s';
				import x from './x';
			`,
		},
		{

			// Well-sorted requires.

			code: `
				const {g, z} = require('one');
				const {a} = require('other');
				const s = require('../s');
				const x = require('./x');
			`,
		},
		{

			// Proof that non-top-level requires are ignored.

			code: `
				const {g, z} = require('one');
				const {a} = require('other');

				// It's not good practice to intermix require calls and
				// other VariableDeclarations, but doing this to show
				// things like this aren't considered when ordering:
				const b = () => require('b');

				const s = require('../s');
				const x = require('./x');
			`,
		},
	],
});
