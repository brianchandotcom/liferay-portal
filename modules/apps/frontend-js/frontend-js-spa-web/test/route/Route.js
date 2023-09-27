/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Route from '../../src/main/resources/META-INF/resources/route/Route';

describe('Route', () => {
	describe('Constructor', () => {
		it('throws error when path and handler not specified', () => {
			expect(() => {
				new Route();
			}).toThrow();
		});

		it('throws error when path is null', () => {
			expect(() => {
				new Route(null, jest.fn());
			}).toThrow();
		});

		it('throws error when path is undefined', () => {
			expect(() => {
				new Route(undefined, jest.fn());
			}).toThrow();
		});

		it('throws error when handler not specified', () => {
			expect(() => {
				new Route('/path');
			}).toThrow();
		});

		it('throws error when handler not a function', () => {
			expect(() => {
				new Route('/path', {});
			}).toThrow();
		});

		it('does not throw error when handler is a function', () => {
			expect(() => {
				new Route('/path', jest.fn());
			}).not.toThrow();
		});

		it('sets path and handler from constructor', () => {
			const handler = jest.fn();
			const route = new Route('/path', handler);
			expect(route.getPath()).toBe('/path');
			expect(route.getHandler()).toEqual(handler);
		});
	});

	describe('Matching', () => {
		it('matches route by string path', () => {
			const route = new Route('/path', jest.fn());
			expect(route.matchesPath('/path')).toBeTruthy();
		});

		it('matches route by string path with params', () => {
			const route = new Route('/path/:foo(\\d+)', jest.fn());
			expect(route.matchesPath('/path/10')).toBeTruthy();
			expect(route.matchesPath('/path/10/')).toBeTruthy();
			expect(!route.matchesPath('/path/abc')).toBeTruthy();
			expect(!route.matchesPath('/path')).toBeTruthy();
		});

		it('matches route by regex path', () => {
			const route = new Route(/\/path/, jest.fn());
			expect(route.matchesPath('/path')).toBeTruthy();
		});

		it('matches route by function path', () => {
			const route = new Route((path) => {
				return path === '/path';
			}, jest.fn());
			expect(route.matchesPath('/path')).toBeTruthy();
		});

		it('does not match any route', () => {
			const route = new Route('/path', jest.fn());
			expect(!route.matchesPath('/invalid')).toBeTruthy();
		});

		it('does not match any route for invalid path', () => {
			const route = new Route({}, jest.fn());
			expect(!route.matchesPath('/invalid')).toBeTruthy();
		});
	});

	describe('Extracting params', () => {
		it('extracts params from path matching route', () => {
			const route = new Route('/path/:foo(\\d+)/:bar(\\w+)', jest.fn());
			const params = route.extractParams('/path/123/abc');
			expect(params).toEqual({
				bar: 'abc',
				foo: '123',
			});
		});

		it('returns null if try to extract params from non matching route', () => {
			const route = new Route('/path/:foo(\\d+)/:bar(\\w+)', jest.fn());
			const params = route.extractParams('/path/abc/123');
			expect(params).toBeNull();
		});

		it('returns empty object if trying to extract params from path given as function', () => {
			const route = new Route(jest.fn(), jest.fn());
			const params = route.extractParams('/path/123/abc');
			expect(params).toEqual({});
		});
	});
});
