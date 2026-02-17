/**
 * SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const parser = require('@typescript-eslint/parser');
const {Linter} = require('eslint');
const {default: diff} = require('jest-diff');

/**
 * Integration tests that show how rules interact when applied together by
 * activating all the rules in a plugin at once.
 */
describe('plugin:@liferay/general', () => {
	const config = 'general';

	it('formats comments with lines-around-comment', () => {
		expect(config).toAutofix({
			code: `
				function add() {
					const x = arguments[0];
					const y = arguments[1];

					// Actually do the math.
					return x + y;
				}
			`,
			output: `
				function add() {
					const x = arguments[0];
					const y = arguments[1];

					// Actually do the math.

					return x + y;
				}
			`,
		});
	});

	it('formats imports', () => {

		// This shows how these rules work together to format imports:
		//
		// - group-imports (keeps imports in groups)
		// - import-extensions (strips unnecessary extensions)
		// - sort-imports (sorts imports by module name)
		// - sort-import-destructures (sorts destructured items in import
		//   specifiers)

		expect(config).toAutofix({
			code: `
				import b from 'b';
				import type {B} from 'b';
				import type {T} from './local';
				import type {D, C} from 'x';
				import a from 'a';
				import {d, c} from 'c';
				import thing from './thing.js';
				import another from './another';
				import type {U} from './first';
				import 'for-side-effects';
				import type {A} from 'a';
			`,
			output: `
				import a from 'a';
				import b from 'b';
				import {c, d} from 'c';

				import another from './another';
				import thing from './thing';

				import 'for-side-effects';

				import type {A} from 'a';
				import type {B} from 'b';
				import type {C, D} from 'x';

				import type {U} from './first';
				import type {T} from './local';
			`,
		});
	});

	it('handles comments in between imports', () => {

		// Regression test for issue reported here:
		//
		// https://github.com/liferay-frontend/liferay-portal/pull/992#issuecomment-827803097

		expect(config).toAutofix({
			code: `
				import ClayTooltip from '@clayui/tooltip';
				import {render, useTimeout} from '@liferay/frontend-js-react-web';

				// Hack to get around frontend-js-web not being in TS
				// @ts-ignore
				import {ALIGN_POSITIONS as POSITIONS, align, delegate} from 'frontend-js-web';
				import React, {
					useEffect,
					useLayoutEffect,
					useReducer,
					useRef,
					useState,
				} from 'react';
			`,
			output: `
				import ClayTooltip from '@clayui/tooltip';
				import {render, useTimeout} from '@liferay/frontend-js-react-web';

				// Hack to get around frontend-js-web not being in TS
				// @ts-ignore

				import {ALIGN_POSITIONS as POSITIONS, align, delegate} from 'frontend-js-web';
				import React, {
					useEffect,
					useLayoutEffect,
					useReducer,
					useRef,
					useState,
				} from 'react';
			`,
		});
	});

	it('formats require statements', () => {

		// This shows how these rules work together to format require
		// statements:
		//
		// - group-imports (keeps require statements in groups)
		// - import-extensions (strips unnecessary extensions)
		// - sort-imports (sorts require statements by module name)
		//
		// Not in effect here:
		//
		// - sort-import-destructures (sorts destructured items in import
		//   specifiers)
		//
		// because sorting destructured items in require statements is actually
		// handled by the third-party rule:
		//
		// - sort-destructure-keys/sort-destructure-keys

		expect(config).toAutofix({
			code: `
				const b = require('b');
				const a = require('a');
				const {d, c} = require('c');
				const thing = require('./thing.js');
				const another = require('./another');
				require('for-side-effects');
				const property = require('./other').property;
			`,
			output: `
				const a = require('a');
				const b = require('b');
				const {d, c} = require('c');

				const another = require('./another');
				const thing = require('./thing');

				require('for-side-effects');

				const {property} = require('./other');
			`,
		});
	});

	it('formats class names in JSX', () => {

		// This shows how these rules work together to format class names in
		// JSX:
		//
		// - no-duplicate-class-names
		// - sort-class-names
		// - trim-class-names (trims whitespace)

		expect(config).toAutofix({
			code: `
				<div>
					<div className="   one two one three  "></div>

					<CustomPopover triggerClassName="   foo foo bar  " />
				</div>
			`,
			output: `
				<div>
					<div className="one three two"></div>

					<CustomPopover triggerClassName="bar foo" />
				</div>
			`,
		});
	});
});

describe('plugin:@liferay/portal', () => {
	const config = 'portal';

	// Not much to test here (yet), because most of the rules currently
	// in this plugin don't have autofixes. This test is included as a
	// placeholder reserved for future expansion.

	it('formats deprecation notices', () => {
		expect(config).toAutofix({
			code: `
				/**
				 * @deprecated As of Mueller (7.2) replaced by Foo
				 */
			`,
			output: `
				/**
				 * @deprecated As of Mueller (7.2.x), replaced by Foo
				 */
			`,
		});
	});
});

// Memoize linters so that we don't have to recreate them for each `it()` block.

const linters = {};

expect.extend({
	toAutofix(configName, {code, output}) {
		const linter =
			linters[configName] ||
			(function () {
				const linter = new Linter();

				linter.defineParser('@typescript-eslint/parser', parser);

				const config = {
					parser: '@typescript-eslint/parser',
					parserOptions: {
						ecmaFeatures: {
							jsx: true,
						},
					},
					rules: {},
				};

				const baseRules = linter.getRules();

				const baseConfig = require('../configs/general');

				function addRule(name) {
					let rule;

					const configs = [baseConfig];

					const baseName = name.replace(
						'@liferay/eslint-plugin/',
						''
					);

					if (name.startsWith('@liferay/eslint-plugin/portal/')) {
						rule = require('../rules/portal')[baseName];

						configs.unshift(require('../configs/react'));
					}
					else if (name.startsWith('@liferay/')) {
						rule = require('../rules/general')[baseName];
					}
					else {
						rule = baseRules.get(name);
					}

					linter.defineRule(name, rule);

					const found = configs.some(({rules}) => {
						if (rules[name]) {
							config.rules[name] = rules[name];

							return true;
						}
					});

					if (!found) {
						config.rules[name] = 'error';
					}
				}

				if (configName === 'general') {
					[
						...Object.keys(require('../configs/general').rules),
						...Object.keys(require('../rules/general')).map(
							(name) => {
								return `@liferay/eslint-plugin/${name}`;
							}
						),
					].forEach(addRule);
				}
				else if (configName === 'portal') {
					[
						...Object.keys(require('../configs/general').rules),
						...Object.keys(require('../configs/portal').rules),
						...Object.keys(require('../rules/portal')).map(
							(name) => {
								return `@liferay/eslint-plugin/${name}`;
							}
						),
					].forEach(addRule);
				}
				else {
					throw new Error(`Unknown plugin: ${configName}`);
				}

				linters[configName] = {
					verifyAndFix(code) {
						return linter.verifyAndFix(code, config);
					},
				};

				return linters[configName];
			})();

		const {output: actual} = linter.verifyAndFix(code);

		return {
			actual,
			message: () => {
				const diffString = diff(output, actual, {
					expand: this.expand,
				});

				return (
					this.utils.matcherHint('toAutofix') +
					'\n\n' +
					(diffString && diffString.includes('- Expect')
						? `Difference:\n\n${diffString}`
						: `Expected: ${this.utils.printExpected(output)}\n` +
						  `Received: ${this.utils.printReceived(actual)}`)
				);
			},
			pass: output === actual,
		};
	},
});
