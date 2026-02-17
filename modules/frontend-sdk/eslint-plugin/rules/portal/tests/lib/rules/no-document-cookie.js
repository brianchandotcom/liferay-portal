/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-document-cookie');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-document-cookie', rule, {
	invalid: [
		{

			// Assignment expression.

			code: `
                 function doSomething(name, value) {
                     return document.cookie = \`\${name}=\${value}\`;
                 }
              `,
			errors: [
				{
					messageId: 'noDocumentCookie',
					type: 'MemberExpression',
				},
			],
		},
		{

			// Property access expression.

			code: `
                 function doSomething() {
                     return document.cookie.split(";").length;
                 }
              `,
			errors: [
				{
					messageId: 'noDocumentCookie',
					type: 'MemberExpression',
				},
			],
		},
	],

	valid: [
		{

			// Named import from frontend-js-web

			code: `
                 import {setCookie, COOKIE_TYPES} from 'frontend-js-web';

                 function doSomething() {
                     return setCookie("name", "value", COOKIE_TYPES.NECESSARY);
                 }
              `,
		},
		{

			// Namespaced from Liferay.Util

			code: `
                  function doSomething() {
                      return Liferay.Util.Cookie.set("name", "value", Liferay.Util.Cookie.TYPES.PERFORMANCE);
                  }
              `,
		},
	],
});
