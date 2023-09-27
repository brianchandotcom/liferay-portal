/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		const throttled = throttle(function () {
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
