/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-default-export-from-frontend-js-web');

const parserOptions = {
	ecmaVersion: 6,
	sourceType: 'module',
};

const ruleTester = new MultiTester({parserOptions});

ruleTester.run('no-default-export-from-frontend-js-web', rule, {
	invalid: [
		{
			code: "import someDefault from 'frontend-js-web';",
			errors: [
				{
					message:
						'frontend-js-web contains no default export, you need to import named items.',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import someDefault from '@liferay/frontend-js-web';",
			errors: [
				{
					message:
						'frontend-js-web contains no default export, you need to import named items.',
					type: 'ImportDeclaration',
				},
			],
		},
	],

	valid: [
		{
			code: "import {someNameImport} from 'frontend-js-web'",
		},
		{
			code: "import {someNameImport} from '@liferay/frontend-js-web'",
		},
	],
});
