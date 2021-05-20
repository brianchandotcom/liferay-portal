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

// A simplified mock for requestIdleCallback, this does not implement a queuing
// system but only simulates scheduling over timeout.

window.requestIdleCallback = (callback: Function) =>
	setTimeout(() => callback(), 40);
window.cancelIdleCallback = (handle: number) => clearTimeout(handle);

const sleep = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

describe('A11yChecker', () => {
	describe('Scheduler', () => {
		let scheduler: Scheduler<string>;

		afterEach(() => {
			scheduler.cancelHostCallback();
		});

		beforeEach(() => {
			scheduler = new Scheduler();
		});

		it('schedule a callback and execute when possible', (done) => {
			const fn = jest.fn((target: string) => {
				expect(target).toBe('foo');
				done();
			});

			scheduler.scheduleCallback(fn, 'foo');
		});

		it('callbacks are called sequentially', (done) => {
			const callbacksCalled: Array<string> = [];

			const fn = jest.fn((target: string) => {
				callbacksCalled.push(target);

				if (callbacksCalled.length === 3) {
					expect(callbacksCalled).toEqual(['foo', 'bar', 'baz']);
					done();
				}
			});

			scheduler.scheduleCallback(fn, 'foo');
			scheduler.scheduleCallback(fn, 'bar');
			scheduler.scheduleCallback(fn, 'baz');
		});

		it('execute many callbacks within the 100ms deadline', (done) => {
			const callbacksCalled: Array<{
				deadline: number;
				target: string;
			}> = [];

			const fn = jest.fn(async (target: string) => {
				callbacksCalled.push({deadline: scheduler.deadline, target});

				await sleep(80);

				if (callbacksCalled.length === 3) {
					const [foo, bar, baz] = callbacksCalled;

					// Callbacks that were executed within the 100ms deadline.

					expect(foo.deadline).toBe(bar.deadline);

					// Callback executed in different deadlines is because they
					// were executed in different frames.

					expect(bar.deadline).not.toBe(baz.deadline);
					done();
				}
			});

			scheduler.scheduleCallback(fn, 'foo');
			scheduler.scheduleCallback(fn, 'bar');
			scheduler.scheduleCallback(fn, 'baz');
		});

		it('yield to host when the deadline is finished', (done) => {
			const callbacksCalled: Array<{
				deadline: number;
				start: number;
				target: string;
			}> = [];

			const fn = jest.fn(async (target: string) => {
				callbacksCalled.push({
					deadline: scheduler.deadline,
					start: performance.now(),
					target,
				});

				await sleep(100);

				if (callbacksCalled.length === 2) {
					const [foo, bar] = callbacksCalled;

					// The next execution frame is started after running 100ms.

					expect(bar.start - foo.deadline).toBeGreaterThan(100);
					done();
				}
			});

			scheduler.scheduleCallback(fn, 'foo');
			scheduler.scheduleCallback(fn, 'bar');
		});
	});
});
