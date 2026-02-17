/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/import-extensions');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 2018,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

const badExport = {
	messageId: 'badExport',
	type: 'Literal',
};

const badImport = {
	messageId: 'badImport',
	type: 'Literal',
};

const badRequire = {
	messageId: 'badRequire',
	type: 'Literal',
};

ruleTester.run('import-extensions', rule, {
	invalid: [
		{
			code: `import templates from './Something.soy.js';`,
			errors: [badImport],
			output: `import templates from './Something.soy';`,
		},
		{
			code: `import {Util} from './Util.es.js';`,
			errors: [badImport],
			output: `import {Util} from './Util.es';`,
		},
		{
			code: `import * as Billboard from './billboard.js';`,
			errors: [badImport],
			output: `import * as Billboard from './billboard';`,
		},
		{
			code: `
				import type {T} from './Helpers.ts';
				import List from './List.tsx';
				import type {ListItemT} from './ListItem.tsx';
				import other from './other.ts';
			`,
			errors: [badImport, badImport, badImport, badImport],
			output: `
				import type {T} from './Helpers';
				import List from './List';
				import type {ListItemT} from './ListItem';
				import other from './other';
			`,

			// espree doesn't know how to parse TypeScript imports.

			skip: ['espree'],
		},
		{
			code: `export * from './Other.es.js';`,
			errors: [badExport],
			output: `export * from './Other.es';`,
		},
		{
			code: `export * from './Other.js';`,
			errors: [badExport],
			output: `export * from './Other';`,
		},
		{
			code: `export * from './Other.ts';`,
			errors: [badExport],
			output: `export * from './Other';`,
		},
		{
			code: `export * from './Other.tsx';`,
			errors: [badExport],
			output: `export * from './Other';`,
		},
		{
			code: `export * as UsefulStuff from './UsefulStuff.es.js';`,
			errors: [badExport],
			output: `export * as UsefulStuff from './UsefulStuff.es';`,

			// "* as name" syntax is not currently supported by espree.

			skip: ['espree'],
		},
		{
			code: `export * as UsefulStuff from './UsefulStuff.js';`,
			errors: [badExport],
			output: `export * as UsefulStuff from './UsefulStuff';`,

			// "* as name" syntax is not currently supported by espree.

			skip: ['espree'],
		},
		{
			code: `export {a as b, c, d} from './Letters.es.js';`,
			errors: [badExport],
			output: `export {a as b, c, d} from './Letters.es';`,
		},
		{
			code: `export {a as b, c, d} from './Letters.js';`,
			errors: [badExport],
			output: `export {a as b, c, d} from './Letters';`,
		},
		{
			code: `const templates = require('./Something.soy.js');`,
			errors: [badRequire],
			output: `const templates = require('./Something.soy');`,
		},
		{
			code: `const {Util} = require('./Util.es.js');`,
			errors: [badRequire],
			output: `const {Util} = require('./Util.es');`,
		},
		{
			code: `const Billboard = require('./billboard.js');`,
			errors: [badRequire],
			output: `const Billboard = require('./billboard');`,
		},
		{

			// Double quote delimiters are preserved.

			code: `const Billboard = require("./billboard.js");`,
			errors: [badRequire],
			output: `const Billboard = require("./billboard");`,
		},
		{

			// Backtick delimiters are preserved.

			code: `const Billboard = require(\`./billboard.js\`);`,
			errors: [
				{
					...badRequire,
					type: 'TemplateLiteral',
				},
			],
			output: `const Billboard = require(\`./billboard\`);`,
		},
	],

	valid: [
		{
			code: `import templates from './Something.soy';`,
		},
		{
			code: `import {Util} from './Util.es';`,
		},
		{

			// OK because "billboard.js" is the name of an NPM package:

			code: `import {Data} from 'billboard.js';`,
		},
		{
			code: `export {default} from './Other';`,
		},
		{
			code: `const templates = require('./Something.soy');`,
		},
		{
			code: `const {Util} = require('./Util.es');`,
		},
		{
			code: `const {Data} = require('billboard.js');`,
		},
		{

			// Note double quotes.

			code: `const {Data} = require("billboard.js");`,
		},
		{

			// Note backticks...

			code: `const {Data} = require(\`billboard.js\`);`,
		},
		{

			// ...but we don't look at backticks if they contain interpolation.

			code: `const {Data} = require(\`./\${something}.js\`);`,
		},
		{
			code: `const billboard = not_a_require('billboard.js');`,
		},
		{

			// Regression discovered here:
			//
			// https://github.com/liferay/liferay-npm-tools/pull/429#issuecomment-608451060
			//
			// Can't assume that all exports have a source.

			code: `
				const foo = 1;

				export {foo};
			`,
		},
	],
});
