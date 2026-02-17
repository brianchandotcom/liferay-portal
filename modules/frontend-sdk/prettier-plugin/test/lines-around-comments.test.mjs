/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

import assert from 'node:assert';
import {describe, test} from 'node:test';
import {format} from 'prettier';

import * as liferayPrettierPlugin from '../index.mjs';

const baseConfig = {
	bracketSpacing: false,
	plugins: [liferayPrettierPlugin],
	singleQuote: true,
	tabWidth: 4,
	useTabs: true,
};

const babelConfig = {
	...baseConfig,
	parser: 'babel',
};

const tsConfig = {
	...baseConfig,
	parser: 'typescript',
};

const fixtures = [
	{
		_name: 'inline comment',
		code: `const foo = 'foo';
		// test
		const bar = 'bar';`,
		expected: `const foo = 'foo';

// test

const bar = 'bar';
`,
	},
	{
		_name: 'block comment',
		code: `const foo = 'foo';
/*
 * blah
 */
const bar = 'bar';`,
		expected: `const foo = 'foo';

/*
 * blah
 */
const bar = 'bar';
`,
	},
	{
		_name: 'multiple inline comments',
		code: `const foo = 'foo';
// foo
// bar
const bar = 'bar';`,
		expected: `const foo = 'foo';

// foo
// bar

const bar = 'bar';
`,
	},
	{
		_name: 'doesnt format already correct code',
		code: `const foo = 'foo';

// foo
// bar

const bar = 'bar';`,
		expected: `const foo = 'foo';

// foo
// bar

const bar = 'bar';
`,
	},
	{
		_name: 'shebang directive next to inline comment',
		code: `#!/usr/bin/env
// test`,
		expected: `#!/usr/bin/env
// test
`,
	},
	{
		_name: 'shebang directive next to block comment',
		code: `#!/usr/bin/env
/*
 * foo
 */`,
		expected: `#!/usr/bin/env
/*
 * foo
 */
`,
	},
	{
		_name: 'tab indented comments',
		code: `if (true) {
	// foo
	// bar
	return false;
}`,
		expected: `if (true) {

	// foo
	// bar

	return false;
}
`,
	},
	{
		_name: 'tab indented with inline comments',
		code: `if (true) {
var foo = 'test'; // foo
// bar
return false;
}`,
		expected: `if (true) {
	var foo = 'test'; // foo

	// bar

	return false;
}
`,
	},
	{
		_name: 'disable line after if it is an "eslint-disable" comment',
		code: `if (true) {

// eslint-disable-next-line
var foo = 'test'; // foo
return false;
}`,
		expected: `if (true) {

	// eslint-disable-next-line
	var foo = 'test'; // foo
	return false;
}
`,
	},
	{
		_name: 'disable line after if it is an "eslint-disable" block comment',
		code: `if (true) {

/* eslint-disable */
var foo = 'test'; // foo
return false;
}`,
		expected: `if (true) {

	/* eslint-disable */
	var foo = 'test'; // foo
	return false;
}
`,
	},
	{
		_name: 'ignore triple slash references in TS',
		code: `/// <reference path="foo.d.ts" />
/// <reference path="bar.d.ts" />
`,
		expected: `/// <reference path="foo.d.ts" />
/// <reference path="bar.d.ts" />
`,
	},
	{
		_config: {
			commentIgnorePatterns: ['ignore-this-comment'],
		},
		_name: 'respects commentIgnorePatterns option',
		code: `var foo = 'bar';
// ignore-this-comment
var bar = 'foo';
`,
		expected: `var foo = 'bar';
// ignore-this-comment
var bar = 'foo';
`,
	},
	{
		_config: {
			commentIgnorePatterns: ['do-ignore-.*-comment'],
		},
		_name: 'respects commentIgnorePatterns option with regex',
		code: `var foo = 'bar';
// do-ignore-this-comment
// do-not-ignore-this-comment
var bar = 'foo';
`,
		expected: `var foo = 'bar';
// do-ignore-this-comment
// do-not-ignore-this-comment

var bar = 'foo';
`,
	},
	{
		_config: {
			commentIgnorePatterns: ['mylint-disable-next-line.*'],
		},
		_name: 'respects commentIgnorePatterns option with regex 2',
		code: `// mylint-disable-next-line some-rule
var foo = 'bar';
// mylint-disable
var bar = 'foo';
`,
		expected: `// mylint-disable-next-line some-rule
var foo = 'bar';

// mylint-disable

var bar = 'foo';
`,
	},
	{
		_config: {
			commentIgnoreAfterPatterns: ['disable-line-after'],
			commentIgnoreBeforePatterns: ['disable-line-before'],
			commentIgnorePatterns: ['disable-both-lines'],
		},
		_name: 'respects commentIgnorePatterns option with regex 3',
		code: `var foo = 'bar';
// disable-both-lines
var bar = 'foo';
// disable-line-after
var test = 'test';
// disable-line-before
var baz = 'baz';
`,
		expected: `var foo = 'bar';
// disable-both-lines
var bar = 'foo';

// disable-line-after
var test = 'test';
// disable-line-before

var baz = 'baz';
`,
	},
];

describe('babel', () => {
	test('prettier runs', async () => {
		const code = `if (foo) {}`;

		assert.ok(await format(code, babelConfig));
	});

	fixtures.forEach((fixture) => {
		const {_config = {}, _name, code, expected} = fixture;

		test(_name, async () => {
			assert.equal(
				await format(code, {
					...babelConfig,
					..._config,
				}),
				expected
			);
		});
	});
});

describe('typescript', () => {
	test('prettier runs', async () => {
		const code = `if (foo) {}`;
		assert.ok(await format(code, tsConfig));
	});
	fixtures.forEach((fixture) => {
		const {_config = {}, _name, code, expected} = fixture;

		test(_name, async () => {
			assert.equal(
				await format(code, {...tsConfig, ..._config}),
				expected
			);
		});
	});
});
