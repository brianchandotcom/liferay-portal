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
		const mockFn = jest.fn((object, number, array, string) => [
			object,
			number,
			array,
			string,
		]);

		const memoizedMockFn = memoize(mockFn);

		expect(memoizedMockFn({3: 4}, 4, ['foo', 'bar'], 'baz')).toEqual([
			{3: 4},
			4,
			['foo', 'bar'],
			'baz',
		]);
	});

	it("caches the argument if it isn't already cached", () => {
		const mockFn = jest.fn((object, number, array, string) => [
			object,
			number,
			array,
			string,
		]);

		const memoizedMockFn = memoize(mockFn);

		memoizedMockFn({3: 4}, 4, ['foo', 'bar'], 'baz');

		expect(memoizedMockFn.getCache().size).toBe(1);
	});

	it("throws an error if the provided argument isn't a function", () => {
		expect(() => memoize({foo: 42})).toThrow(TypeError);
	});
});
