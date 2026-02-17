/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-use-strict-in-module');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-use-strict-in-module', rule, {
	invalid: [
		{
			code: `
				'use strict'

				import {test} from 'test';
			 `,
			errors: [
				{
					message: `'use strict' is unnecessary inside of modules`,
					type: 'Literal',
				},
			],
		},
		{
			code: `
				'use strict'

				function test() {
					'use strict'

					return 'test';
				}
				export default test;
			 `,
			errors: [
				{
					message: `'use strict' is unnecessary inside of modules`,
					type: 'Literal',
				},
				{
					message: `'use strict' is unnecessary inside of modules`,
					type: 'Literal',
				},
			],
		},
		{
			code: `
				'use strict'

				function test() {}
				export {test};
			 `,
			errors: [
				{
					message: `'use strict' is unnecessary inside of modules`,
					type: 'Literal',
				},
			],
		},
	],

	valid: [
		{
			code: `
				import {test} from 'test';
			 `,
		},
		{
			code: `
				'use strict'

				module.export = function test() {}
			 `,
		},
		{
			code: `
				'use strict'

				console.log('foo');
			 `,
		},
	],
});
