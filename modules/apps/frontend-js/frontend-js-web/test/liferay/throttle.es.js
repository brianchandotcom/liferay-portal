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

import throttle from '../../src/main/resources/META-INF/resources/liferay/throttle.es';

describe('throttle()', () => {
	let mockFunction;

	beforeEach(() => {
		jest.useFakeTimers();

		mockFunction = jest.fn();
	});

	it('does nothing if the throttled function is not called', () => {
		throttle(mockFunction, 100);

		expect(mockFunction).not.toBeCalled();
	});

	it('calls the throttled function as soon as it is invoked', () => {
		const throttled = throttle(mockFunction, 100);

		throttled(1);

		expect(mockFunction).toBeCalledWith(1);
	});

	it('calls the throttled function only once', () => {
		const throttled = throttle(mockFunction, 100);

		throttled(1);
		throttled(2);

		expect(mockFunction).toBeCalledWith(1);
		expect(mockFunction.mock.calls.length).toBe(1);
	});

	it('calls the throttled function on the trailing edge as well', () => {
		const throttled = throttle(mockFunction, 100);

		throttled(1);

		mockFunction.mockClear();

		throttled(2);

		jest.runAllTimers();

		expect(mockFunction).toBeCalledWith(2);
	});

	it('uses the last-passed arguments when throttling multiple calls', () => {
		const throttled = throttle(mockFunction, 100);

		throttled(1);

		mockFunction.mockClear();

		throttled(2);
		throttled(3);

		jest.runAllTimers();

		expect(mockFunction).toBeCalledWith(3);
	});

	it('uses the last-employed context when throttling multiple calls', () => {
		let context;

		const throttled = throttle(function() {
			context = this;
		}, 100);

		const context1 = {};
		const context2 = {};
		const context3 = {};

		throttled.call(context1);
		throttled.call(context2);
		throttled.call(context3);

		jest.runAllTimers();

		expect(context).toBe(context3);
	});
});
