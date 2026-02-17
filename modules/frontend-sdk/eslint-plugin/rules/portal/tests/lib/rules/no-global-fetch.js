/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-global-fetch');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-global-fetch', rule, {
	invalid: [
		{

			// As a global fetch without an import.

			code: `
				function doSomething(url) {
					return fetch(url);
				};
			`,
			errors: [
				{
					messageId: 'noGlobalFetch',
					type: 'CallExpression',
				},
			],
		},
		{

			// As a global fetch without an import.

			code: `
				import fetch from 'whatwg-fetch';

				fetch(url);
			`,
			errors: [
				{
					messageId: 'noGlobalFetch',
					type: 'CallExpression',
				},
			],
		},
	],

	valid: [
		{

			// Named import from frontend-js-web

			code: `
				import {fetch} from 'frontend-js-web';

				function doSomething(url) {
					return fetch(url);
				}
			`,
		},
		{

			// Unnamed import from '../fetch'

			code: `
				import fetch from '../fetch';

				function doSomething(url) {
					return fetch(url);
				}
			`,
		},
		{

			// Unnamed import from '../util/fetch'

			code: `
				import fetch from '../util/fetch';

				function doSomething(url) {
					return fetch(url);
				}
			`,
		},
		{

			// Unnamed import from '../fetch.es'

			code: `
				import fetch from '../fetch.es';

				function doSomething(url) {
					return fetch(url);
				}
			`,
		},
		{

			// Unnamed import from '../util/fetch.es'

			code: `
				import fetch from '../util/fetch.es';

				function doSomething(url) {
					return fetch(url);
				}
			`,
		},
		{

			// Namespaced from Liferay.Util

			code: `
				function doSomething(url) {
					return Liferay.Util.fetch(url);
				}
			`,
		},
		{

			// Accessed via prototype

			code: `
				class CreateContentForm extends PortletBase {
					doSomething() {
						this.fetch(url);
					}
				}
			`,
		},
	],
});
