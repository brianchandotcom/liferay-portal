/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/deprecation');

const ruleTester = new MultiTester();

ruleTester.run('deprecation', rule, {
	invalid: [
		{
			code: `
				// @deprecated As of Mueller (7.2.x), with no direct replacement
			`,
			errors: [
				{
					messageId: 'blockCommentsOnly',
					type: 'Line',
				},
			],
		},
		{
			code: `
				// Really anything with @deprecated in it will be flagged...
			`,
			errors: [
				{
					messageId: 'blockCommentsOnly',
					type: 'Line',
				},
			],
		},
		{
			code: `
				/**
				 * This is a splendid comment, but it has a dark secret.
				 *
				 * @deprecated As of 7.1.
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
			output: `
				/**
				 * This is a splendid comment, but it has a dark secret.
				 *
				 * @deprecated As of Judson (7.1.x)
				 */
			`,
		},
		{

			// Shorthand version: 7.2 should be 7.2.x.

			code: `
				/**
				 * @deprecated As of Mueller (7.2), replaced by XYZ
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
			output: `
				/**
				 * @deprecated As of Mueller (7.2.x), replaced by XYZ
				 */
			`,
		},
		{

			// Missing comma.

			code: `
				/**
				 * @deprecated As of Mueller (7.2.x) replaced by XYZ
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
			output: `
				/**
				 * @deprecated As of Mueller (7.2.x), replaced by XYZ
				 */
			`,
		},
		{

			// Missing "direct".

			code: `
				/**
				 * @deprecated As of Mueller (7.2.x), with no replacement
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
			output: `
				/**
				 * @deprecated As of Mueller (7.2.x), with no direct replacement
				 */
			`,
		},
		{

			// Missing parentheses.

			code: `
				/**
				 * @deprecated As of Mueller 7.2.x, with no direct replacement
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
			output: `
				/**
				 * @deprecated As of Mueller (7.2.x), with no direct replacement
				 */
			`,
		},
		{

			// Bad capitalization.

			code: `
				/**
				 * @deprecated as of MUELLER (7.2.x), With NO direct replacement
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
			output: `
				/**
				 * @deprecated As of Mueller (7.2.x), with no direct replacement
				 */
			`,
		},
		{

			// Wrong conjunction ("From" instead of "As of"):

			code: `
				/**
				 * @deprecated From Mueller (7.2.x), with no direct replacement
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
			output: `
				/**
				 * @deprecated As of Mueller (7.2.x), with no direct replacement
				 */
			`,
		},
		{

			// Wrong conjunction ("Since" instead of "As of"):

			code: `
				/**
				 * @deprecated Since Mueller (7.2.x), with no direct replacement
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
			output: `
				/**
				 * @deprecated As of Mueller (7.2.x), with no direct replacement
				 */
			`,
		},
		{

			// Bad whitespace.

			code: `
				/**
				 *@deprecated As of Mueller(7.2.x),with no direct replacement
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
			output: `
				/**
				 * @deprecated As of Mueller (7.2.x), with no direct replacement
				 */
			`,
		},
		{

			// Various things wrong at once.

			code: `
				/**
				 *@deprecated As OF JUDSON 7.1 Replaced by Liferay.Bar
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
			output: `
				/**
				 * @deprecated As of Judson (7.1.x), replaced by Liferay.Bar
				 */
			`,
		},
		{

			// Invalid because of unwanted trailer.

			code: `
				/**
				 * @deprecated As of Mueller (7.2.x), with no direct replacement AFAIK
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
		},
		{

			// Invalid because of missing trailer.

			code: `
				/**
				 * @deprecated As of Mueller (7.2.x), replaced by
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
		},
		{

			// Invalid because of non-standard conjunction:

			code: `
				/**
				 * @deprecated Around Mueller (7.2.x), replaced by Liferay.Foo
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
		},
		{

			// Invalid because name and version mismatch:

			code: `
				/**
				 * @deprecated As of Mueller (7.0), replaced by XYZ
				 */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
		},
		{

			// Invalid because it is not multiline:

			code: `
				/* @deprecated As of Mueller (7.2.x), replaced by XYZ */
			`,
			errors: [
				{
					messageId: 'badFormat',
					type: 'Block',
				},
			],
		},
	],

	valid: [
		{code: '// Just a comment.'},
		{code: '/* Intentionally left blank. */'},
		{code: '/** Continued overleaf. */'},
		{code: '// Not a note about being deprecated.'},
		{code: '/* Also nothing deprecated. */'},
		{code: '/** Is it deprecated? Who knows? */'},
		{
			code: `
				/**
				 * @deprecated As of Bunyan (6.0.x)
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Bunyan (6.0.x), with no direct replacement
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Bunyan (6.0.x), replaced by Liferay.Foo
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Paton (6.1.x)
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Paton (6.1.x), with no direct replacement
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Paton (6.1.x), replaced by Liferay.Foo
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Newton (6.2.x)
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Newton (6.2.x), with no direct replacement
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Newton (6.2.x), replaced by Liferay.Foo
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Wilberforce (7.0.x)
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Wilberforce (7.0.x), replaced by Liferay.Foo
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Judson (7.1.x)
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Judson (7.1.x), with no direct replacement
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Judson (7.1.x), replaced by Liferay.Bar
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Mueller (7.2.x)
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Mueller (7.2.x), with no direct replacement
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Mueller (7.2.x), replaced by Liferay.Baz
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Athanasius (7.3.x)
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Athanasius (7.3.x), with no direct replacement
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Athanasius (7.3.x), replaced by Liferay.Qux
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Cavanaugh (7.4.x)
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
				 */
			`,
		},
		{
			code: `
				/**
				 * @deprecated As of Cavanaugh (7.4.x), replaced by Liferay.Qux
				 */
			`,
		},
		{
			code: `
				/**
				 * Note: It doesn't make any sense to have more than one
				 * deprecation notice in a single comment, but this example is
				 * just to show that we process these annotations line-by-line
				 *
				 * @deprecated As of Judson (7.1.x)
				 * @deprecated As of Mueller (7.2.x)
				 * @deprecated As of Athanasius (7.3.x)
				 */
			`,
		},
	],
});
