/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import memoize from '../../../src/main/resources/META-INF/resources/liferay/util/memoize';

describe('memoize', () => {
	it('invokes the provided function with the provided arguments', () => {
		const mockFn = jest.fn((...args) => [...args]);

		const memoizedMockFn = memoize(mockFn);

		expect(memoizedMockFn({3: 4}, 4, ['foo', 'bar'], 'baz')).toEqual([
			{3: 4},
			4,
			['foo', 'bar'],
			'baz',
		]);
	});

	it('calls the provided function once, even if the memo is invoked twice', () => {
		const mockFn = jest.fn((...args) => [...args]);

		const memoizedMockFn = memoize(mockFn);

		memoizedMockFn({3: 4}, 4, ['foo', 'bar'], 'baz');
		memoizedMockFn({3: 4}, 4, ['foo', 'bar'], 'baz');

		expect(mockFn).toHaveBeenCalledTimes(1);
	});

	it('returns the same value if invoked with same arguments', () => {
		const mockObject = {
			12: 24,
			array: [1, 2, '3'],
			foo: 'bar',
		};

		const mockFn = jest.fn((object) => object);

		const memoizedMockFn = memoize(mockFn);

		expect(memoizedMockFn(mockObject)).toEqual(memoizedMockFn(mockObject));
	});

	it("doesn't reuse a stale argument", () => {
		const mockFn = jest.fn((object) => object);

		const memoizedMockFn = memoize(mockFn);

		expect(
			memoizedMockFn({
				12: 24,
				array: [1, 2, '3'],
				foo: 'bar',
			})
		).not.toBe(
			mockFn({
				12: 24,
				array: [1, 2, '3'],
				foo: 'bar',
			})
		);
	});

	it("throws an error if the provided argument isn't a function", () => {
		expect(() => memoize({foo: 42})).toThrow(TypeError);
	});
});
