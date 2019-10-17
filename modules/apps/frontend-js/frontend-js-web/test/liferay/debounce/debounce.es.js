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

import {
	cancelDebounce,
	debounce
} from '../../../src/main/resources/META-INF/resources/liferay/debounce/debounce.es';

describe('debounce', () => {
	it('only calls received function with the last called args after a delay', () => {
		jest.useFakeTimers();

		const fn = jest.fn();

		const debounced = debounce(fn, 200);

		debounced(1, 2, 3);
		debounced(4, 5, 6);

		setTimeout(() => {
			debounced(7, 8, 9);
			debounced(10, 11, 12);

			setTimeout(() => {
				expect(fn).toHaveBeenCalledTimes(1);
				expect(fn).toHaveBeenCalledWith(10, 11, 12);
			}, 210);
		}, 100);

		jest.runAllTimers();
	});

	it('calls original function with its original context', () => {
		jest.useFakeTimers();

		const expectedContext = {};
		let context;

		const fn = function() {
			context = this;
		};

		const debounced = debounce(fn.bind(expectedContext), 200);

		debounced(1, 2, 3);

		setTimeout(() => {
			expect(expectedContext).toBe(context);
		}, 200);

		jest.runAllTimers();
	});

	it('cancels the debounced function call', () => {
		jest.useFakeTimers();

		const fn = jest.fn();

		const debounced = debounce(fn, 200);

		debounced(1, 2, 3);

		cancelDebounce(debounced);

		setTimeout(() => {
			expect(fn).not.toHaveBeenCalled();
		}, 210);

		jest.runAllTimers();
	});
});
