/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-absolute-import');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-absolute-import', rule, {
	invalid: [
		{
			code: `
				const thing = require(\`/etc/thing\`);
				const other = require('/home/me/other');
				import {x} from '/tmp/x';
			`,
			errors: [
				{
					message: 'import sources should not use absolute paths',
					type: 'TemplateLiteral',
				},
				{
					message: 'import sources should not use absolute paths',
					type: 'Literal',
				},
				{
					message: 'import sources should not use absolute paths',
					type: 'Literal',
				},
			],
		},
		{
			code: `import type {MapT} from '/usr/share/types/Map';`,
			errors: [
				{
					message: 'import sources should not use absolute paths',
					type: 'Literal',
				},
			],

			// espree doesn't know how to parse TypeScript imports.

			skip: ['espree'],
		},
	],

	valid: [
		{
			code: `
				const thing = require(\`../../etc/thing\`);
				const other = require('other');
				import {x} from 'x';
			`,
		},
		{
			code: `import type {MapT} from './Map';`,

			// espree doesn't know how to parse TypeScript imports.

			skip: ['espree'],
		},
	],
});
