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

import {Scheduler} from '../../src/main/resources/META-INF/resources/A11yChecker';

declare global {
	namespace NodeJS {
		interface Performance {
			now: () => number;
		}

		interface Global {
			performance?: Performance;
		}
	}
}

type Runtime = {
	advanceTime: (ms: number) => void;
	assertLog: (expected: Array<string>) => void;
	fireIdleCallback: () => Promise<void>;
	isLogEmpty: () => boolean;
	log: (value: string) => void;
};

let runtime: Runtime;
let scheduler: Scheduler<string>;

/**
 * The Scheduler implementation uses browser APIs like `requestIdleCallback`
 * and `cancelIdleCallback` to schedule work on the main thread. Our testing
 * treats this as an implementation detail, however, these APIs are not
 * supported in Node.js or are not very easy to mock, time also varies between
 * browsers and there is no way to accurately measure or rely on time.
 *
 * The test suite for Scheduler mocks all browser APIs used in the
 * implementation. Instead of worrying about when the event will be called or
 * if the host is busy, it simulates behaviors based on events happening in
 * specific cases, think of it as a timeline that we have control, like advance
 * time, pause... we assume control the browser APIs and decide when to call
 * them to simulate situations.
 */

describe('A11yChecker', () => {
	function installMockBrowserRuntime() {
		let hasPendingCallback = false;
		let callback: () => Promise<void>;

		let eventLog: Array<string> = [];

		let currentTime = 0;

		delete global.performance;

		global.performance = {
			now() {
				return currentTime;
			},
		};

		// A simplified mock for requestIdleCallback, this does not implement a queuing
		// system but only simulates scheduling.

		const customRequestIdleCallback = (callbackFn: () => Promise<void>) => {
			if (hasPendingCallback) {
				throw Error('Callback already scheduled');
			}

			log('Request Callback');

			callback = callbackFn;
			hasPendingCallback = true;
		};

		window.requestIdleCallback = customRequestIdleCallback as any;

		window.cancelIdleCallback = () => {
			log('Cancel Callback');

			hasPendingCallback = false;
		};

		function log(value: string) {
			eventLog.push(value);
		}

		function isLogEmpty() {
			return eventLog.length === 0;
		}

		function advanceTime(ms: number) {
			currentTime += ms;
		}

		function fireIdleCallback() {
			if (eventLog.length !== 0) {
				throw Error(
					'Log is not empty. Call assertLog before continuing.'
				);
			}

			if (!hasPendingCallback) {
				throw Error('No callback was scheduled');
			}

			hasPendingCallback = false;
			log('Callback');

			return callback();
		}

		function assertLog(expected: Array<string>) {
			const actual = eventLog;

			eventLog = [];

			expect(actual).toEqual(expected);
		}

		return {
			advanceTime,
			assertLog,
			fireIdleCallback,
			isLogEmpty,
			log,
		};
	}

	describe('Scheduler', () => {
		beforeEach(() => {
			runtime = installMockBrowserRuntime();
			scheduler = new Scheduler();
		});

		afterEach(() => {
			if (!runtime.isLogEmpty()) {
				throw Error('Test exited without clearing log.');
			}
		});

		it('schedule a callback and execute when possible', async () => {
			const callback = () => {
				runtime.log('Task');
			};

			scheduler.scheduleCallback(callback, 'foo');

			runtime.assertLog(['Request Callback']);
			await runtime.fireIdleCallback();
			runtime.assertLog(['Callback', 'Task']);
		});

		it('callbacks are called sequentially', async () => {
			scheduler.scheduleCallback(() => {
				runtime.log('Foo');
			}, 'foo');
			scheduler.scheduleCallback(() => {
				runtime.log('Bar');
			}, 'bar');
			scheduler.scheduleCallback(() => {
				runtime.log('Baz');
			}, 'baz');

			runtime.assertLog(['Request Callback']);
			await runtime.fireIdleCallback();
			runtime.assertLog(['Callback', 'Foo', 'Bar', 'Baz']);
		});

		it('execute many callbacks within the 100ms deadline', async () => {
			scheduler.scheduleCallback(() => {
				runtime.log('Foo');
			}, 'foo');
			scheduler.scheduleCallback(() => {
				runtime.log('Bar');
				runtime.advanceTime(5000);
			}, 'bar');
			scheduler.scheduleCallback(() => {
				runtime.log('Baz');
			}, 'baz');

			runtime.assertLog(['Request Callback']);
			await runtime.fireIdleCallback();
			runtime.assertLog(['Callback', 'Foo', 'Bar', 'Request Callback']);
			await runtime.fireIdleCallback();
			runtime.assertLog(['Callback', 'Request Callback']);
		});

		it('yield to host when the deadline is finished', async () => {
			scheduler.scheduleCallback(() => {
				runtime.log('Foo');
				runtime.advanceTime(5000);
			}, 'foo');
			scheduler.scheduleCallback(() => {
				runtime.log('Bar');
			}, 'bar');

			runtime.assertLog(['Request Callback']);
			await runtime.fireIdleCallback();
			runtime.assertLog(['Callback', 'Foo', 'Request Callback']);
			await runtime.fireIdleCallback();

			// When the deadline expires, the Scheduler gives a fixed time to
			// the host of 100ms.

			runtime.assertLog(['Callback', 'Request Callback']);
			runtime.advanceTime(100);

			await runtime.fireIdleCallback();
			runtime.assertLog(['Callback', 'Bar']);
		});

		it('schedule new task after queue has emptied', async () => {
			scheduler.scheduleCallback(() => {
				runtime.log('Foo');
			}, 'foo');
			scheduler.scheduleCallback(() => {
				runtime.log('Bar');
			}, 'bar');

			runtime.assertLog(['Request Callback']);
			await runtime.fireIdleCallback();
			runtime.assertLog(['Callback', 'Foo', 'Bar']);

			scheduler.scheduleCallback(() => {
				runtime.log('Baz');
			}, 'baz');

			runtime.assertLog(['Request Callback']);
			await runtime.fireIdleCallback();
			runtime.assertLog(['Callback', 'Baz']);
		});

		it('cancel host callback', () => {
			scheduler.scheduleCallback(() => {
				runtime.log('Foo');
			}, 'foo');

			runtime.assertLog(['Request Callback']);

			scheduler.cancelHostCallback();
			runtime.assertLog(['Cancel Callback']);

			expect(() => runtime.fireIdleCallback()).toThrow(
				'No callback was scheduled'
			);
		});
	});
});
