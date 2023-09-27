/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import deepFreeze from '../src/main/resources/META-INF/resources/deepFreeze';
import {withEnv} from './helpers';

describe('deepFreeze()', () => {
	let value: any;

	beforeEach(() => {
		value = {
			hello: true,
			nested: [{thing: 1}, 'something'],
		};
	});

	describe('when NODE_ENV is not "development"', () => {
		beforeEach(() => {
			withEnv('production', () => {
				deepFreeze(value);
			});
		});

		it('does nothing', () => {
			expect(value).toEqual({
				hello: true,
				nested: [{thing: 1}, 'something'],
			});

			value.hello = false;
			value.extra = 'property';

			expect(value).toEqual({
				extra: 'property',
				hello: false,
				nested: [{thing: 1}, 'something'],
			});
		});
	});

	describe('when NODE_ENV is "development"', () => {
		beforeEach(() => {
			withEnv('development', () => {
				deepFreeze(value);
			});
		});

		it('prevents properties from being added', () => {
			value.extra = 'added';

			expect(value).toEqual({
				hello: true,
				nested: [{thing: 1}, 'something'],
			});
		});

		it('prevents properties from being mutated', () => {
			value.hello = false;

			expect(value).toEqual({
				hello: true,
				nested: [{thing: 1}, 'something'],
			});

			expect(() => value.nested.push(9000)).toThrow(TypeError);
		});

		it('returns a frozen object', () => {
			expect(Object.isFrozen(value)).toBe(true);
		});

		it('freezes deeply', () => {
			expect(Object.isFrozen(value.nested)).toBe(true);
			expect(Object.isFrozen(value.nested[0])).toBe(true);
		});

		it('gracefully accepts primitive values', () => {
			expect(deepFreeze('foo')).toBe('foo');
			expect(deepFreeze(10)).toBe(10);
			expect(deepFreeze(true)).toBe(true);
			expect(deepFreeze(null)).toBe(null);
			expect(deepFreeze(undefined)).toEqual(undefined);
		});
	});
});
