/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/sort-import-destructures');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('sort-import-destructures', rule, {
	invalid: [
		{
			code: `import {z, g} from 'a';`,
			errors: [
				{
					message: 'destructured names in imports must be sorted',
				},
			],
			output: `import {g, z} from 'a';`,
		},
		{
			code: `import {b as bar, a as foo} from 'b';`,
			errors: [
				{
					message: 'destructured names in imports must be sorted',
				},
			],
			output: `import {a as foo, b as bar} from 'b';`,
		},
		{
			code: `import thing, {k, h, j} from 'c';`,
			errors: [
				{
					message: 'destructured names in imports must be sorted',
				},
			],
			output: `import thing, {h, j, k} from 'c';`,
		},
		{

			// Same as previous, but with line breaks.

			code: `
				import thing, {
					k,
					h,
					j
				} from 'c';
			`,
			errors: [
				{
					message: 'destructured names in imports must be sorted',
				},
			],
			output: `
				import thing, {
					h,
					j,
					k
				} from 'c';
			`,
		},
		{

			// Note that trailing commas are preserved.

			code: `
				import {
					z,
					y,
					x,
				} from 'file';
			`,
			errors: [
				{
					message: 'destructured names in imports must be sorted',
				},
			],
			output: `
				import {
					x,
					y,
					z,
				} from 'file';
			`,
		},
		{

			// Works with type-only imports too.

			code: `import type {C, B, A} from 'thing';`,
			errors: [
				{
					message: 'destructured names in imports must be sorted',
				},
			],
			output: `import type {A, B, C} from 'thing';`,
			skip: ['espree'],
		},
	],

	valid: [
		{
			code: `import thing from 'thing';`,
		},
		{
			code: `import {gizmo} from 'gizmo';`,
		},
		{
			code: `import {g, z} from 'a';`,
		},
		{
			code: `import {a as foo, b as bar} from 'b';`,
		},
		{
			code: `import thing, {h, j, k} from 'c';`,
		},
		{

			// We don't touch the sort order if there are comments anywhere.

			code: `
				import {
					// Comment.
					zoo,
					school
				} from 'places';

				import {
					zzz, // Comment.
					xxx
				} from 'letters';

				import {
					three,
					two,
					one // Comment.
				} from 'numbers';
			`,
		},
		{

			// Regression test. This was being incorrectly flagged as unsorted.

			code: `
				import {
					addFragmentEntryLinkReducer,
					deleteFragmentEntryLinkCommentReducer,
					duplicateFragmentEntryLinkReducer,
					moveFragmentEntryLinkReducer,
					removeFragmentEntryLinkReducer,
					toggleShowResolvedCommentsReducer,
					updateEditableValueReducer,
					updateFragmentEntryKeysReducer,
					updateFragmentEntryLinkCommentReducer,
					updateFragmentEntryLinkConfigReducer,
					updateFragmentEntryLinkContentReducer
				} from './fragments.es';
			`,
		},
	],
});
