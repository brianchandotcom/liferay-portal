/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-global-storage');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-global-storage', rule, {
	invalid: [
		{

			// As a global localStorage without an import.

			code: `
                function doSomething() {
                    return localStorage.setItem("key", "value");
                }
             `,
			errors: [
				{
					messageId: 'noGlobalStorage',
					type: 'Identifier',
				},
			],
		},
		{

			// As a global sessionStorage without an import.

			code: `
                function doSomething() {
                    return sessionStorage.setItem("key", "value");
                }
             `,
			errors: [
				{
					messageId: 'noGlobalStorage',
					type: 'Identifier',
				},
			],
		},
	],

	valid: [
		{

			// Named import from frontend-js-web

			code: `
                import {localStorage} from 'frontend-js-web';

                function doSomething() {
                    return localStorage.setItem("key", "value", localStorage.TYPES.NECESSARY);
                }
             `,
		},
		{

			// Named import from frontend-js-web

			code: `
                import {sessionStorage} from 'frontend-js-web';
                
                function doSomething() {
                    return sessionStorage.getItem("key", sessionStorage.TYPES.FUNCTIONAL)
                }
             `,
		},
		{

			// Unnamed import from '../local_storage'

			code: `
                 import localStorage from '../local_storage';
 
                 function doSomething() {
                     return localStorage.setItem("key", "value", localStorage.TYPES.NECESSARY);
                 }
             `,
		},
		{

			// Unnamed import from '../session_storage'

			code: `
                 import sessionStorage from '../session_storage';
 
                 function doSomething() {
                     return sessionStorage.setItem("key", "value", sessionStorage.TYPES.NECESSARY);
                 }
             `,
		},
		{

			// Unnamed import from './util/local_storage'

			code: `
                 import localStorage from './util/local_storage';
 
                 function doSomething() {
                     return localStorage.setItem("key", "value", localStorage.TYPES.NECESSARY);
                 }
             `,
		},
		{

			// Unnamed import from './util/session_storage'

			code: `
                 import sessionStorage from './util/session_storage';
 
                 function doSomething() {
                     return sessionStorage.setItem("key", "value", sessionStorage.TYPES.NECESSARY);
                 }
             `,
		},
		{

			// Namespaced from Liferay.Util

			code: `
                 function doSomething() {
                     return Liferay.Util.LocalStorage.getItem("key", Liferay.Util.LocalStorage.TYPES.NECESSARY);
                 }
             `,
		},
		{

			// Namespaced from Liferay.Util

			code: `
                 function doSomething() {
                     return Liferay.Util.SessionStorage.setItem("key", "value", Liferay.Util.SessionStorage.TYPES.NECESSARY);
                 }
             `,
		},
	],
});
