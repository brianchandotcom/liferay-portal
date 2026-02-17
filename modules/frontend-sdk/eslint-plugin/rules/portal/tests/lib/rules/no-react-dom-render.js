/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-react-dom-render');

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
		messageId: 'noReactDOMRender',
		type: 'CallExpression',
	},
];

ruleTester.run('no-react-dom-render', rule, {
	invalid: [
		{
			code: `
				import ReactDOM from 'react-dom';

				ReactDOM.render(element, container);
			`,
			errors,
		},
		{
			code: `
				import SneakyDOM from 'react-dom';

				SneakyDOM.render(element, container);
			`,
			errors,
		},
		{
			code: `
				import * as ReactDOM from 'react-dom';

				ReactDOM.render(element, container);
			`,
			errors,
		},
		{
			code: `
				import * as SneakyDOM from 'react-dom';

				SneakyDOM.render(element, container);
			`,
			errors,
		},
		{
			code: `
				import {render} from 'react-dom';

				render(element, container);
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
				import {render as sneakyRender} from 'react-dom';

				sneakyRender(element, container);
			`,
			errors,
		},
		{
			code: `
				const ReactDOM = require('react-dom');

				ReactDOM.render(element, container);
			`,
			errors,
		},
		{
			code: `
				const SneakyDOM = require('react-dom');

				SneakyDOM.render(element, container);
			`,
			errors,
		},
		{
			code: `
				const {render} = require('react-dom');

				render(element, container);
			`,
			errors,
		},
		{
			code: `
				const {render: sneakyRender} = require('react-dom');

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
							ReactDOM.render(element, container);
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
				import {render} from 'frontend-js-react-web';

				render(element, container);
			`,
		},
		{
			code: `
				import {createPortal} from 'react-dom';

				createPortal(element, container)
			`,
		},
		{
			code: `
				import {createPortal as create} from 'react-dom';

				create(child, container)
			`,
		},
		{
			code: `
				// No sane person would ever do this, but let's prove that we
				// can avoid false positives.
				const ReactDOM = {
					render(...args) {},
				};

				ReactDOM.render();
			`,
		},
		{
			code: `
				// Again, not something sane, but show that we consider scope
				// to avoid false positives.
				import {render} from 'react-dom';

				function scope() {
					const render = () => {};

					render();
				}
			`,
		},
		{
			code: `
				// One more crazy example...
				import ReactDOM from 'react-dom';

				function fn() {
					const ReactDOM = {
						render() {},
					};

					ReactDOM.render();
				}
			`,
		},
		{
			code: `
				// Illustrating a limitation: catching this would be too much
				// work...
				import ReactDOM from 'react-dom';

				const SneakyDOM = ReactDOM;

				SneakyDOM.render(element, container);
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
