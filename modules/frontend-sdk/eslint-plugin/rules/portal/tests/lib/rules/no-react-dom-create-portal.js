/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-react-dom-create-portal');

const parserOptions = {
	ecmaFeatures: {
		jsx: true,
	},
	ecmaVersion: 6,
	sourceType: 'module',
};

const ruleTester = new MultiTester({parserOptions});

const errors = [
	{
		messageId: 'noReactDOMCreatePortal',
		type: 'CallExpression',
	},
];

ruleTester.run('no-react-dom-create-portal', rule, {
	invalid: [
		{
			code: `
				 import ReactDOM from 'react-dom';
 
				 ReactDOM.createPortal(element, container);
			 `,
			errors,
		},
		{
			code: `
				 import SneakyDOM from 'react-dom';
 
				 SneakyDOM.createPortal(element, container);
			 `,
			errors,
		},
		{
			code: `
				 import * as ReactDOM from 'react-dom';
 
				 ReactDOM.createPortal(element, container);
			 `,
			errors,
		},
		{
			code: `
				 import * as SneakyDOM from 'react-dom';
 
				 SneakyDOM.createPortal(element, container);
			 `,
			errors,
		},
		{
			code: `
				 import {createPortal} from 'react-dom';
 
				 createPortal(element, container);
			 `,
			errors,
		},
		{
			code: `
				 import {createPortal, render} from 'react-dom';
 
				 createPortal(element, container);
				 render(element, container);
			 `,
			errors,
		},
		{
			code: `
				 import {createPortal as sneakyRender} from 'react-dom';
 
				 sneakyRender(element, container);
			 `,
			errors,
		},
		{
			code: `
				 const ReactDOM = require('react-dom');
 
				 ReactDOM.createPortal(element, container);
			 `,
			errors,
		},
		{
			code: `
				 const SneakyDOM = require('react-dom');
 
				 SneakyDOM.createPortal(element, container);
			 `,
			errors,
		},
		{
			code: `
				 const {createPortal} = require('react-dom');
 
				 createPortal(element, container);
			 `,
			errors,
		},
		{
			code: `
				 const {createPortal: sneakyRender} = require('react-dom');
 
				 sneakyRender(element, container);
			 `,
			errors,
		},
		{
			code: `
				 import ReactDOM from 'react-dom';
 
				 export default function outer() {
					 function inner() {
						 if (true) {
							 // Show that we don't get confused by scopes.
							 ReactDOM.createPortal(element, container);
						 }
					 }
				 }
			 `,
			errors,
		},
	],

	valid: [
		{
			code: `
				 import {createPortal} from 'frontend-js-react-web';
 
				 createPortal(element, container);
			 `,
		},
		{
			code: `
				 // No sane person would ever do this, but let's prove that we
				 // can avoid false positives.
				 const ReactDOM = {
					 createPortal(...args) {},
				 };
 
				 ReactDOM.createPortal();
			 `,
		},
		{
			code: `
				 // Again, not something sane, but show that we consider scope
				 // to avoid false positives.
				 import {createPortal} from 'react-dom';
 
				 function scope() {
					 const createPortal = () => {};
 
					 createPortal();
				 }
			 `,
		},
		{
			code: `
				 // Illustrating a limitation: catching this would be too much
				 // work...
				 import ReactDOM from 'react-dom';
 
				 const SneakyDOM = ReactDOM;
 
				 SneakyDOM.createPortal(element, container);
			 `,
		},
		{
			code: `
				 // Regression test.
				 let declaration;
			 `,
		},
	],
});
