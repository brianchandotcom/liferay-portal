/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		memoizedMockFn({foo: 'bar'});
		memoizedMockFn({foo: 'bar'});

		expect(mockFn).toHaveBeenCalledTimes(1);
	});

	it('returns the same value if invoked with same arguments', () => {
		const mockFn = jest.fn((object) => object);

		const memoizedMockFn = memoize(mockFn);

		expect(memoizedMockFn({foo: 'bar'})).toEqual(
			memoizedMockFn({foo: 'bar'})
		);
	});

	it("doesn't reference the non-memoized object", () => {
		const mockFn = jest.fn((object) => object);

		const memoizedMockFn = memoize(mockFn);

		expect(memoizedMockFn({foo: 'bar'})).not.toBe(mockFn({foo: 'bar'}));
	});

	it("throws an error if the provided argument isn't a function", () => {
		expect(() => memoize({foo: 'baar'})).toThrow(TypeError);
	});
});
