/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-metal-plugins');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-metal-plugins', rule, {
	invalid: [
		{
			code: "import Affix from 'metal-affix';",
			errors: [
				{
					messageId: 'no-metal-affix',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import Ajax from 'metal-ajax';",
			errors: [
				{
					messageId: 'no-metal-ajax',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import Anim from 'metal-anim';",
			errors: [
				{
					messageId: 'no-metal-anim',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import AOP from 'metal-aop';",
			errors: [
				{
					messageId: 'no-metal-aop',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import assertBoolean from 'metal-assertions';",
			errors: [
				{
					messageId: 'no-metal-assertions',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import Clipboard from 'metal-clipboard';",
			errors: [
				{
					messageId: 'no-metal-clipboard',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import debounce from 'metal-debounce';",
			errors: [
				{
					messageId: 'no-metal-debounce',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import on from 'metal-dom';",
			errors: [
				{
					messageId: 'no-metal-dom',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import 'metal-isomorphic';",
			errors: [
				{
					messageId: 'no-metal-isomorphic',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import KeyboardFocusManager from 'metal-keyboard-focus';",
			errors: [
				{
					messageId: 'no-metal-keyboard-focus',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import MultiMap from 'metal-multimap';",
			errors: [
				{
					messageId: 'no-metal-multimap',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import Pagination from 'metal-pagination';",
			errors: [
				{
					messageId: 'no-metal-pagination',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import Align from 'metal-position';",
			errors: [
				{
					messageId: 'no-metal-position',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import CancellablePromise from 'metal-promise';",
			errors: [
				{
					messageId: 'no-metal-promise',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import Router from 'metal-router';",
			errors: [
				{
					messageId: 'no-metal-router',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import Scrollspy from 'metal-scrollspy';",
			errors: [
				{
					messageId: 'no-metal-scrollspy',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code:
				"import {LocalStorageMechanism, Storage} from 'metal-storage';",
			errors: [
				{
					messageId: 'no-metal-storage',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import MultiMap from 'metal-structs';",
			errors: [
				{
					messageId: 'no-metal-structs',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import throttle from 'metal-throttle';",
			errors: [
				{
					messageId: 'no-metal-throttle',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import Toggler from 'metal-toggler';",
			errors: [
				{
					messageId: 'no-metal-toggler',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import URI from 'metal-uri';",
			errors: [
				{
					messageId: 'no-metal-uri',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import UA from 'metal-useragent';",
			errors: [
				{
					messageId: 'no-metal-useragent',
					type: 'ImportDeclaration',
				},
			],
		},
		{
			code: "import defineWebComponent from 'metal-web-component';",
			errors: [
				{
					messageId: 'no-metal-web-component',
					type: 'ImportDeclaration',
				},
			],
		},
	],

	valid: [],
});
